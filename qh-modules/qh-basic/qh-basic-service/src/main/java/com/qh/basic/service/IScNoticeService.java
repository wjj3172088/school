package com.qh.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.basic.domain.ScNotice;
import com.qh.basic.domain.vo.NoticeViewVo;
import com.qh.basic.model.request.notice.NoticeAddRequest;
import com.qh.basic.model.request.notice.NoticeModifyRequest;
import com.qh.common.security.domain.LoginUser;

/**
 * 学校公告记录Service接口
 *
 * @author 汪俊杰
 * @date 2020-11-25
 */
public interface IScNoticeService extends IService<ScNotice> {


    /**
     * 查询学校公告记录集合
     *
     * @param page     分页信息
     * @param scNotice 操作学校公告记录对象
     * @return 操作学校公告记录集合
     */
    IPage<ScNotice> selectScNoticeListByPage(IPage<ScNotice> page, ScNotice scNotice);

    /**
     * 分页查询已读未读人员列表
     *
     * @param page  分页
     * @param bizId 公告id
     * @param look  已读未读
     * @return
     */
    IPage<NoticeViewVo> selectViewListByPage(IPage<NoticeViewVo> page, String bizId, Integer look);

    /**
     * 根据公告id获取详情
     *
     * @param noticeId 公告id
     * @return
     */
    ScNotice selectDetail(String noticeId);

    /**
     * 新增
     *
     * @param request   新增请求
     * @param loginUser 登录用户信息
     */
    void addNotice(NoticeAddRequest request, LoginUser loginUser);

    /**
     * 修改
     *
     * @param request
     * @param loginUser
     */
    void modifyNotice(NoticeModifyRequest request, LoginUser loginUser);

    /**
     * 删除
     *
     * @param noticeId 公告id
     */
    void deleteNotice(String noticeId);
}
