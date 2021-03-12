package com.qh.basic.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.*;
import com.qh.basic.domain.vo.*;
import com.qh.basic.enums.NoticeTargetEnum;
import com.qh.basic.enums.PushNoticeNewTypeEnum;
import com.qh.basic.enums.PushStateMarkEnum;
import com.qh.basic.mapper.ScNoticeMapper;
import com.qh.basic.model.request.notice.NoticeAddRequest;
import com.qh.basic.model.request.notice.NoticeModifyRequest;
import com.qh.basic.model.request.notice.NoticeTarget;
import com.qh.basic.service.*;
import com.qh.basic.utils.MsgJointUtils;
import com.qh.basic.utils.PushUtils;
import com.qh.common.core.enums.BizType;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.core.utils.http.UUIDG;
import com.qh.common.core.utils.oss.PicUtils;
import com.qh.common.security.domain.LoginUser;
import com.qh.common.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 学校公告记录Service业务层处理
 *
 * @author 汪俊杰
 * @date 2020-11-25
 */
@Service
@Slf4j
public class ScNoticeServiceImpl extends ServiceImpl<ScNoticeMapper, ScNotice> implements IScNoticeService {
    @Autowired
    private IAttachmentService attachmentService;
    @Autowired
    private IScMoveAccService moveAccService;
    @Autowired
    private ScNoticeMapper noticeMapper;
    @Autowired
    private IScNewsSingleService newsSingleService;
    @Autowired
    private IScNewsNewService newsNewService;
    @Autowired
    PushUtils pushService;
    @Autowired
    private IActionHandlerService actionHandlerService;
    @Autowired
    private IScTeacherService teacherService;
    @Autowired
    private IScStaffInfoService staffInfoService;
    @Autowired
    private IScClassService classService;

    @Autowired
    private PicUtils picUtils;

    /**
     * 查询学校公告记录集合
     *
     * @param scNotice 操作学校公告记录对象
     * @return 操作学校公告记录集合
     */
    @Override
    public IPage<ScNotice> selectScNoticeListByPage(IPage<ScNotice> page, ScNotice scNotice) {
        scNotice.setOrgId(SecurityUtils.getOrgId());
        IPage<ScNotice> dataPage = this.page(page, getQuery(scNotice));
        List<ScNotice> list = dataPage.getRecords();
        if (!CollectionUtils.isEmpty(list)) {
            for (ScNotice notice : list) {
                //将图片独享转成图片数组
                List<String> pictureList = picUtils.imageListFristDomainApp(notice.getNoticePicurls());
                notice.setPictureList(pictureList);
            }
        }
        return dataPage;
    }

    /**
     * 分页查询已读未读人员列表
     *
     * @param page  分页
     * @param bizId 公告id
     * @param look  已读未读
     * @return
     */
    @Override
    public IPage<NoticeViewVo> selectViewListByPage(IPage<NoticeViewVo> page, String bizId, Integer look) {
        return noticeMapper.selectViewListByPage(page, bizId, look);
    }

    /**
     * 根据公告id获取详情
     *
     * @param noticeId 公告id
     * @return
     */
    @Override
    public ScNotice selectDetail(String noticeId) {
        ScNotice notice = noticeMapper.selectById(noticeId);
        if (notice == null) {
            throw new BizException(CodeEnum.NOT_EXIST, "该公告");
        }

        //将图片独享转成图片数组
        List<String> pictureList = picUtils.imageListFristDomainApp(notice.getNoticePicurls());
        if (!CollectionUtils.isEmpty(pictureList)) {
            String pictures = String.join(",", pictureList);
            notice.setNoticePicurls(pictures);
        }
        return notice;
    }

    /**
     * 新增
     *
     * @param request
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async
    public void addNotice(NoticeAddRequest request, LoginUser loginUser) {
        ScNotice notice = new ScNotice();
        notice.setNoticeId(UUIDG.generate());
        notice.setOrgId(loginUser.getOrgId());
        notice.setPublisherName(loginUser.getUsername());
        notice.setPublisherId(loginUser.getUserId().toString());
        notice.setNoticeTitle(request.getNoticeTitle());
        notice.setNoticeContent(request.getNoticeContent());
        notice.setStateMark(PushStateMarkEnum.CONFIRMED.getCode());
        notice.setNoticeTarget(0);
        int time = (int) (System.currentTimeMillis() / 1000);
        notice.setCreateDate(time);
        // 图片信息
        String pictures = attachmentService.batchInsert(request.getNoticePictureList(),
                loginUser.getUserId().toString(),
                notice.getNoticeId(), BizType.notice.name());
        notice.setNoticePicurls(pictures);

        //保存并推送公告
        this.pushSchoolNotice(notice, request.getNoticeTargetList(), loginUser.getOrgId(), true);
    }

    /**
     * 修改
     *
     * @param request
     * @param loginUser
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async
    public void modifyNotice(NoticeModifyRequest request, LoginUser loginUser) {
        // 图片信息
        String pictures = attachmentService.batchInsert(request.getNoticePictureList(), loginUser.getUserId().toString(), request.getNoticeId(), BizType.notice.name());

        //公告
        ScNotice sourceNotice = this.selectByNoticeId(request.getNoticeId());
        if (sourceNotice == null) {
            throw new BizException(CodeEnum.NOT_EXIST, "该公告");
        }
        sourceNotice.setNoticePicurls(pictures);
        sourceNotice.setNoticeTitle(request.getNoticeTitle());
        sourceNotice.setNoticeContent(request.getNoticeContent());
        sourceNotice.setNoticeTarget(0);
        int time = (int) (System.currentTimeMillis() / 1000);
        // 设置修改时间
        sourceNotice.setModifyDate(time);
        if (sourceNotice.getStateMark() == PushStateMarkEnum.CONFIRMED.getCode()) {
            this.pushSchoolNotice(sourceNotice, request.getNoticeTargetList(), loginUser.getOrgId(), false);
        }
    }

    /**
     * 删除
     *
     * @param noticeId 公告id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteNotice(String noticeId) {
        noticeMapper.deleteByNoticeId(noticeId);
        newsNewService.deleteNewsNewByBizId(noticeId);
        newsSingleService.deleteNewsSingleByBizId(noticeId);
    }

    /**
     * 根据公告id查询
     *
     * @param noticeId 公告id
     * @return
     */
    private ScNotice selectByNoticeId(String noticeId) {
        QueryWrapper<ScNotice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(noticeId), "notice_id", noticeId);
        return noticeMapper.selectOne(queryWrapper);
    }

    /**
     * 保存公告
     *
     * @param notice 消息
     * @return
     */
    private boolean pushSchoolNotice(ScNotice notice, List<NoticeTarget> noticeTargetList, String orgId, Boolean isAdd) {
        if (notice.getStateMark() == PushStateMarkEnum.UNCONFIRMED.getCode()) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "信息未发布，请发布后在推送");
        }
        if (notice.getStateMark() == PushStateMarkEnum.PUSHING.getCode()) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "信息推送中，请不要重复推送");
        }
        if (notice.getStateMark() == PushStateMarkEnum.PUSH_SUCCESS.getCode()) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "信息推送成功，不需要再次推送");
        }

        // 获取发布者的角色
        ScMoveAcc moveAcc = moveAccService.selectByAccId(notice.getPublisherId());

        //处理推送对象
        List<PushMoveAcc> pushMoveAccList = new ArrayList<>();
        List<PushMoveTeacher> pushMoveTeacherList = new ArrayList<>();
        List<PushMoveStaff> pushMoveStaffList = new ArrayList<>();
        this.calcNoticeTarget(orgId, noticeTargetList, pushMoveAccList, pushMoveTeacherList, pushMoveStaffList);

        //组装推送消息
        NoticePush noticePush = new NoticePush();
        noticePush.setList(pushMoveAccList);
        noticePush.setTeacherList(pushMoveTeacherList);
        noticePush.setStaffList(pushMoveStaffList);
        noticePush.setNotice(notice);
        noticePush.setMoveAcc(moveAcc);
        noticePush.setPushName(notice.getPublisherName());
        noticePush.setNewsType(PushNoticeNewTypeEnum.NOTICE_SCHOOL);

        int needCount = pushMoveAccList.size() + pushMoveTeacherList.size() + pushMoveStaffList.size();
        log.info("needCount = " + needCount + " list.size = " + pushMoveAccList.size() + " teacherList.size = " + pushMoveTeacherList.size() + " staffList.size = " + pushMoveStaffList.size());

        notice.setNeedCount(needCount);
        notice.setConfirmCount(0);
        notice.setUnconfirmedCount(needCount);
        if (isAdd) {
            notice.setCreateDate(DateUtil.getSystemSeconds());
            noticeMapper.insert(notice);
        } else {
            notice.setModifyDate(DateUtil.getSystemSeconds());
            noticeMapper.modify(notice);
        }
        this.calcPushSchoolNotice(noticePush);
        // 完成后计入数据库
        return true;
    }

    /**
     * 组装推送数据
     *
     * @param noticePush
     */
    private void calcPushSchoolNotice(NoticePush noticePush) {
        int delNew = newsNewService.deleteNewsNewByBizId(noticePush.getNotice().getNoticeId());
        log.info("PushSchoolNoticeRunable deleteNewsNew result = " + delNew);

        String noticeTitle = noticePush.getNotice().getNoticeTarget() == 0 ? "学校通知" : "老师公告";
        String subTitle = StringUtils.subString(noticePush.getNotice().getNoticeTitle(), 100);
        String content = StringUtils.subString(noticePush.getNotice().getNoticeContent(), 300);
        String transContent = MsgJointUtils.commandHandle(noticeTitle, content, noticePush.getNewsType().getCode(), "", "", subTitle);

        List<PushParameter> pushList = new ArrayList<PushParameter>();
        List<ScNewsSingle> newsSingleList = new ArrayList<>();
        // 发给家长
        this.calcPushStudent(noticePush, noticeTitle, content, transContent, subTitle, pushList, newsSingleList);

        // 老师端
        this.calcPushTeacher(noticePush, noticeTitle, content, transContent, subTitle, pushList, newsSingleList);

        // 职工端
        this.calcPushStaff(noticePush, noticeTitle, content, transContent, subTitle, pushList, newsSingleList);

        if (!CollectionUtils.isEmpty(newsSingleList)) {
            newsSingleService.batchInsertNewsNew(newsSingleList);
        }

        log.info("开始发送公告---------------------time = " + DateUtil.getSystemSeconds() + " size = " + pushList.size());
        log.info("发送目标 pushList = " + JSON.toJSONString(pushList));
        actionHandlerService.pushBatch(pushList);
    }

    /**
     * 处理学生端
     *
     * @param noticePush     推送信息
     * @param noticeTitle    公告类型
     * @param content        内容
     * @param transContent   拼装消息
     * @param subTitle       标题
     * @param pushList       推送的集合
     * @param newsSingleList 需要保存的消息集合
     */
    private void calcPushStudent(NoticePush noticePush, String noticeTitle, String content, String transContent, String subTitle, List<PushParameter> pushList, List<ScNewsSingle> newsSingleList) {
        if (CollectionUtils.isEmpty(noticePush.getList())) {
            return;
        }
        List<ScNewsNew> schoolNewsNews = new ArrayList<>();
        for (PushMoveAcc e : noticePush.getList()) {
            ScNewsSingle newsSingle = this.addOrUpdateNewsSingle(noticePush, e.getAccId(), e.getStuId());
            newsSingleList.add(newsSingle);

            ScNewsNew schoolNewsNew = new ScNewsNew();
            schoolNewsNew.setNewsId(UUIDG.generate());
            schoolNewsNew.setLook(0);
            schoolNewsNew.setAccId(e.getAccId());
            schoolNewsNew.setBizId(noticePush.getNotice().getNoticeId());
            schoolNewsNew.setCreateDate(new Date());
            schoolNewsNew.setOrgId(noticePush.getNotice().getOrgId());
            schoolNewsNew.setTargetId(e.getStuId());
            schoolNewsNews.add(schoolNewsNew);
            if (StringUtils.isBlank(e.getGtCid())) {
                log.error(String.format("家长公告透传未找到目标标识   accId:%s  accNum:%s title:%s 结果:", e.getAccId(), e.getAccNum()
                        , noticePush.getNotice().getNoticeTitle()));
                continue;
            }

            PushParameter push = pushService.pushTransMultiFirmByCidNewThreeLine(noticeTitle, content,
                    e.getGtCid(), transContent, subTitle);
            pushList.add(push);
        }
        log.info("saveSchoolNewsNews size = " + schoolNewsNews.size());
        newsNewService.batchInsertNewsNew(schoolNewsNews);
    }

    /**
     * 处理老师端
     *
     * @param noticePush     推送信息
     * @param noticeTitle    公告类型
     * @param content        内容
     * @param transContent   拼装消息
     * @param subTitle       标题
     * @param pushList       推送的集合
     * @param newsSingleList 需要保存的消息集合
     */
    private void calcPushTeacher(NoticePush noticePush, String noticeTitle, String content, String transContent, String subTitle, List<PushParameter> pushList, List<ScNewsSingle> newsSingleList) {
        if (CollectionUtils.isEmpty(noticePush.getTeacherList())) {
            return;
        }
        List<ScNewsNew> teacSchoolNewsNews = new ArrayList<>();
        for (PushMoveTeacher e : noticePush.getTeacherList()) {
            ScNewsSingle newsSingle = this.addOrUpdateNewsSingle(noticePush, e.getAccId(), e.getAccId());
            newsSingleList.add(newsSingle);

            ScNewsNew schoolNewsNew = new ScNewsNew();
            schoolNewsNew.setNewsId(UUIDG.generate());
            schoolNewsNew.setLook(0);
            schoolNewsNew.setAccId(e.getAccId());
            schoolNewsNew.setBizId(noticePush.getNotice().getNoticeId());
            schoolNewsNew.setCreateDate(new Date());
            schoolNewsNew.setOrgId(noticePush.getNotice().getOrgId());
            schoolNewsNew.setTargetId(e.getAccId());
            log.debug("PushSchoolNoticeRunable accId = " + e.getAccId() + " publisherId = " + noticePush.getNotice().getPublisherId());
            if (e.getAccId().equals(noticePush.getNotice().getPublisherId())) {
                // 自己的为已经读了
                schoolNewsNew.setLook(1);
                teacSchoolNewsNews.add(schoolNewsNew);
                continue;
            }
            teacSchoolNewsNews.add(schoolNewsNew);

            if (StringUtils.isBlank(e.getGtCid())) {
                log.error(String.format("老师公告透传未找到目标标识   accId:%s  accNum:%s title:%s 结果:", e.getAccId(),
                        e.getAccNum(), noticePush.getNotice().getNoticeTitle()));
                continue;
            }

            PushParameter push = pushService.pushTransMultiFirmByCidNewThreeLine(noticeTitle, content,
                    e.getGtCid(), transContent, subTitle);
            pushList.add(push);
        }
        newsNewService.batchInsertNewsNew(teacSchoolNewsNews);
    }

    /**
     * 处理职工端
     *
     * @param noticePush     推送信息
     * @param noticeTitle    公告类型
     * @param content        内容
     * @param transContent   拼装消息
     * @param subTitle       标题
     * @param pushList       推送的集合
     * @param newsSingleList 需要保存的消息集合
     */
    private void calcPushStaff(NoticePush noticePush, String noticeTitle, String content, String transContent, String subTitle, List<PushParameter> pushList, List<ScNewsSingle> newsSingleList) {
        if (CollectionUtils.isEmpty(noticePush.getStaffList())) {
            return;
        }
        List<ScNewsNew> staffSchoolNewsNews = new ArrayList<>();
        for (PushMoveStaff e : noticePush.getStaffList()) {
            ScNewsSingle newsSingle = this.addOrUpdateNewsSingle(noticePush, e.getAccId(), e.getAccId());
            newsSingleList.add(newsSingle);

            ScNewsNew schoolNewsNew = new ScNewsNew();
            schoolNewsNew.setNewsId(UUIDG.generate());
            schoolNewsNew.setLook(0);
            schoolNewsNew.setAccId(e.getAccId());
            schoolNewsNew.setBizId(noticePush.getNotice().getNoticeId());
            schoolNewsNew.setCreateDate(new Date());
            schoolNewsNew.setOrgId(noticePush.getNotice().getOrgId());
            schoolNewsNew.setTargetId(e.getAccId());
            log.debug("PushSchoolNoticeRunable accId = " + e.getAccId() + " publisherId = " + noticePush.getNotice().getPublisherId());
            if (e.getAccId().equals(noticePush.getNotice().getPublisherId())) {
                // 自己的为已经读了
                schoolNewsNew.setLook(1);
                staffSchoolNewsNews.add(schoolNewsNew);
                continue;
            }
            staffSchoolNewsNews.add(schoolNewsNew);

            if (StringUtils.isBlank(e.getGtCid())) {
                log.error(String.format("老师公告透传未找到目标标识   accId:%s  accNum:%s title:%s 结果:", e.getAccId(),
                        e.getAccNum(), noticePush.getNotice().getNoticeTitle()));
                continue;
            }

            PushParameter push = pushService.pushTransMultiFirmByCidNewThreeLine(noticeTitle, content,
                    e.getGtCid(), transContent, subTitle);
            pushList.add(push);
        }
        newsNewService.batchInsertNewsNew(staffSchoolNewsNews);
    }

    /**
     * 新增修改
     *
     * @param noticePush
     * @return
     */
    private ScNewsSingle addOrUpdateNewsSingle(NoticePush noticePush, String accId, String targetId) {
        ScNotice schoolNotice = noticePush.getNotice();
        int result = newsSingleService.deleteNewsSingleByBizId(schoolNotice.getNoticeId());
        log.info("pushMessageSchoolNoticeServiceImpl deleteNewsSingleByBizId result = " + result);
        ScNewsSingle newsSingle = new ScNewsSingle();
        newsSingle.setTag(noticePush.getTag().getCode());
        newsSingle.setNewsType(noticePush.getNewsType().getCode());
        newsSingle.setNewsTitle(schoolNotice.getNoticeTitle());
        newsSingle.setContent(schoolNotice.getNoticeContent());
        newsSingle.setBizId(schoolNotice.getNoticeId());
        newsSingle.setImage(schoolNotice.getNoticePicurls());
        newsSingle.setCreateDate(new Date());
        newsSingle.setPublisherId(schoolNotice.getPublisherId());
        newsSingle.setPublisherName(schoolNotice.getPublisherName());
        newsSingle.setOrgId(schoolNotice.getOrgId());
        newsSingle.setAccId(accId);
        newsSingle.setTargetId(targetId);
        newsSingle.setVisibleLevel(4);
        return newsSingle;
    }

    /**
     * 查询学校公告记录参数拼接
     */
    private QueryWrapper<ScNotice> getQuery(ScNotice scNotice) {
        QueryWrapper<ScNotice> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(StringUtils.isNotBlank(scNotice.getOrgId()), "org_id", scNotice.getOrgId());
        queryWrapper.eq(StringUtils.isNotBlank(scNotice.getNoticeTitle()), "notice_title", scNotice.getNoticeTitle());
        queryWrapper.eq(scNotice.getStateMark() != null, "state_mark", scNotice.getStateMark());
        queryWrapper.eq(scNotice.getNoticeTarget() != null, "notice_target", scNotice.getNoticeTarget());
        queryWrapper.orderByDesc("create_date");
        return queryWrapper;
    }

    /**
     * 处理推送对象
     *
     * @param orgId               学校id
     * @param noticeTargetList    推送对象请求集合
     * @param pushMoveAccList     推送的学生
     * @param pushMoveTeacherList 推送的老师
     * @param pushMoveStaffList   推送的职工
     */
    private void calcNoticeTarget(String orgId, List<NoticeTarget> noticeTargetList, List<PushMoveAcc> pushMoveAccList, List<PushMoveTeacher> pushMoveTeacherList, List<PushMoveStaff> pushMoveStaffList) {
        //指定推送的老师、学生或职工的列表
        List<NoticeTarget> noticeTargetIdList = noticeTargetList.stream().filter(x -> StringUtils.isNotEmpty(x.getId())).collect(Collectors.toList());
        //针对班级、全部老师或者保安保洁上的人员类型上的推送列表
        List<NoticeTarget> noticeTargetTypeList = noticeTargetList.stream().filter(x -> StringUtils.isEmpty(x.getId())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(noticeTargetIdList)) {
            //将id根据type进行分组
            Map<String, List<String>> idMap =
                    noticeTargetIdList.stream().collect(Collectors.groupingBy(NoticeTarget::getType, Collectors.mapping(NoticeTarget::getId, Collectors.toList())));
            for (String key : idMap.keySet()) {
                this.batchSelect(key, orgId, idMap.get(key), 50, true, pushMoveAccList,
                        pushMoveTeacherList,
                        pushMoveStaffList);
            }
        }
        if (!CollectionUtils.isEmpty(noticeTargetTypeList)) {
            //将id根据type进行分组
            Map<String, List<String>> typeMap = noticeTargetTypeList.stream().collect(Collectors.groupingBy(NoticeTarget::getType, Collectors.mapping(NoticeTarget::getSmallType, Collectors.toList())));
            for (String key : typeMap.keySet()) {
                if (NoticeTargetEnum.CLASS.getCode().equals(key)) {
                    this.batchSelect(key, orgId, typeMap.get(key), 5, false, pushMoveAccList, pushMoveTeacherList, pushMoveStaffList);
                } else if (NoticeTargetEnum.TEACHER.getCode().equals(key)) {
                    List<PushMoveTeacher> teacherList = teacherService.findMovePushTeacher(orgId, null);
                    if (!CollectionUtils.isEmpty(teacherList)) {
                        pushMoveTeacherList.addAll(teacherList);
                    }
                } else if (NoticeTargetEnum.STAFF.getCode().equals(key)) {
                    String jobTitle = String.join(",", typeMap.get(key));
                    List<PushMoveStaff> staffList = staffInfoService.findMovePushStaff(orgId, null, jobTitle);
                    if (!CollectionUtils.isEmpty(staffList)) {
                        pushMoveStaffList.addAll(staffList);
                    }
                } else if (NoticeTargetEnum.ALL_STUDENT.getCode().equals(key)) {
                    List<ScClass> classList = classService.findAllByOrgId(orgId);
                    if (CollectionUtils.isEmpty(classList)) {
                        return;
                    }
                    List<String> idList = classList.stream().map(ScClass::getClassId).collect(Collectors.toList());
                    if (CollectionUtils.isEmpty(idList)) {
                        return;
                    }
                    this.batchSelect(key, orgId, idList, 5, false, pushMoveAccList, pushMoveTeacherList, pushMoveStaffList);
                }
            }
        }
    }

    /**
     * 批量分次查询处理
     *
     * @param idList 需要查询处理的对象集合
     */
    private void batchSelect(String key, String orgId, List<String> idList,
                             int pageSize, boolean flag,
                             List<PushMoveAcc> pushMoveAccList, List<PushMoveTeacher> pushMoveTeacherList, List<PushMoveStaff> pushMoveStaffList) {
        // 每100笔数据批量处理新增
        int count = idList.size();
        // 每次循环真实要处理的数据笔数
        int toIndex = pageSize;
        // 每次循环设定要处理的数据笔数
        int batchNum = pageSize;
        for (int i = 0; i < count; i += batchNum) {
            if (i + batchNum > count) {
                // 最后一次循环如果没有100条数据，剩余的笔数
                toIndex = count - i;
            }
            // 此次循环要处理的数据集合
            List<String> toSelectList = idList.subList(i, i + toIndex);
            //拼接id作为sql条件in
            String id = toSelectList.stream().map(x -> "'" + x + "'").collect(Collectors.joining(","));
            if (flag) {
                if (NoticeTargetEnum.CLASS.getCode().equals(key)) {
                    //选择班级种的学生时
                    List<PushMoveAcc> studentList = noticeMapper.findPushMoveAcc(null, id);
                    if (CollectionUtils.isEmpty(studentList)) {
                        continue;
                    }
                    pushMoveAccList.addAll(studentList);
                } else if (NoticeTargetEnum.TEACHER.getCode().equals(key)) {
                    //选择某位教师
                    List<PushMoveTeacher> teacherList = teacherService.findMovePushTeacher(orgId, id);
                    if (!CollectionUtils.isEmpty(teacherList)) {
                        pushMoveTeacherList.addAll(teacherList);
                    }
                } else if (NoticeTargetEnum.STAFF.getCode().equals(key)) {
                    //选择某个职工
                    List<PushMoveStaff> staffList = staffInfoService.findMovePushStaff(orgId, id, null);
                    if (!CollectionUtils.isEmpty(staffList)) {
                        pushMoveStaffList.addAll(staffList);
                    }
                }
            } else {
                if (NoticeTargetEnum.CLASS.getCode().equals(key) || NoticeTargetEnum.ALL_STUDENT.getCode().equals(key)) {
                    List<PushMoveAcc> studentList = noticeMapper.findPushMoveAcc(id, null);
                    if (!CollectionUtils.isEmpty(studentList)) {
                        pushMoveAccList.addAll(studentList);
                    }
                }
            }
        }
    }
}