package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.qh.basic.domain.ScDutyRecord;
import com.qh.basic.domain.vo.DutyRecordPeopleVo;
import com.qh.basic.mapper.ScDutyRecordMapper;
import com.qh.basic.service.IScDutyRecordService;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.JsonMapper;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.List;

/**
 * 值班上报记录Service业务层处理
 *
 * @author huangdaoquan
 * @date 2021-01-19
 */
@Service
@Slf4j
public class ScDutyRecordServiceImpl extends ServiceImpl<ScDutyRecordMapper, ScDutyRecord> implements IScDutyRecordService {

    /**
     * 分页查询值班上报记录集合
     *
     * @param page         分页信息
     * @param scDutyRecord 操作值班上报记录对象
     * @return 操作值班上报记录集合
     */
    @Override
    public IPage<ScDutyRecord> selectScDutyRecordListByPage(IPage<ScDutyRecord> page, ScDutyRecord scDutyRecord) {
        try {
            //创建时间入参 时间转换为时间戳
            if(StringUtils.isNotBlank(scDutyRecord.getBeginTime()) ) {
                scDutyRecord.setBeginTime(DateUtil.dateToStamp(scDutyRecord.getBeginTime()+" 00:00:00"));
            }
            if(StringUtils.isNotBlank(scDutyRecord.getEndTime()) ) {
                scDutyRecord.setEndTime(DateUtil.dateToStamp(scDutyRecord.getEndTime()+" 23:59:59"));
            }
            scDutyRecord.setOrgId(SecurityUtils.getOrgId());
            return this.page(page, getQuery(scDutyRecord));
        }catch (ParseException ex){
            throw new BizException(CodeEnum.SQL_ORDER_BY_INVALID);
        }
    }


    /**
     * 查询值班记录集合导出
     *
     * @param scDutyRecord 操作值班上报记录对象
     * @return 操作值班上报记录集合
     */
    @Override
    public List<ScDutyRecord> selectScDutyRecordList(ScDutyRecord scDutyRecord) {
        try {
            //创建时间入参 时间转换为时间戳
            if(StringUtils.isNotBlank(scDutyRecord.getBeginTime()) ) {
                scDutyRecord.setBeginTime(DateUtil.dateToStamp(scDutyRecord.getBeginTime()+" 00:00:00"));
            }
            if(StringUtils.isNotBlank(scDutyRecord.getEndTime()) ) {
                scDutyRecord.setEndTime(DateUtil.dateToStamp(scDutyRecord.getEndTime()+" 23:59:59"));
            }
            scDutyRecord.setOrgId(SecurityUtils.getOrgId());
            List<ScDutyRecord > dutyRecordList = this.list(getQuery(scDutyRecord));
            for (ScDutyRecord dutyRecord :dutyRecordList) {
                try {
                    //解析带班人
                    List<DutyRecordPeopleVo> leaderPeopleVoList = JsonMapper.defaultMapper().fromJson(dutyRecord.getLeader()
                            , new TypeReference<List<DutyRecordPeopleVo>>() {});
                    StringBuffer leaderToStr = new StringBuffer();
                    if (!CollectionUtils.isEmpty(leaderPeopleVoList)) {
                        for (DutyRecordPeopleVo leaderPeople :leaderPeopleVoList) {
                            leaderToStr.append(getNewPeopleToString(leaderPeople.getCode(),leaderPeople.getName()));
                            leaderToStr.append("\n");
                        }
                    }
                    dutyRecord.setLeader(leaderToStr.toString());
                    //解析值班人
                    List<DutyRecordPeopleVo> dutyStaffPeopleVoList = JsonMapper.defaultMapper().fromJson(dutyRecord.getDutyStaff()
                            , new TypeReference<List<DutyRecordPeopleVo>>() {});
                    StringBuffer dutyStaffToStr = new StringBuffer();
                    if (!CollectionUtils.isEmpty(dutyStaffPeopleVoList)) {
                        for (DutyRecordPeopleVo dutyStaffPeopleVo :dutyStaffPeopleVoList) {
                            dutyStaffToStr.append(getNewPeopleToString(dutyStaffPeopleVo.getCode(),dutyStaffPeopleVo.getName()));
                            dutyStaffToStr.append("\n");
                        }
                    }
                    dutyRecord.setDutyStaff(dutyStaffToStr.toString());
                } catch (Exception e) {
                    log.error("查询值班记录集合导出带班人值班人格式转换出错："+e.getMessage()+";dutyRecord.getLeader():"
                            +dutyRecord.getLeader()+";dutyRecord.getDutyStaff()"+dutyRecord.getDutyStaff());
                    continue;
                }
            }
            return dutyRecordList;
        }catch (ParseException ex){
            throw new BizException(CodeEnum.SQL_ORDER_BY_INVALID);
        }
    }

    private String getNewPeopleToString(String code,String userName){
        return userName+":"+code+";";
    }

    /**
     * 查询值班上报记录参数拼接
     */
    private QueryWrapper<ScDutyRecord> getQuery(ScDutyRecord scDutyRecord) {
        QueryWrapper<ScDutyRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.between(StringUtils.isNotBlank(scDutyRecord.getBeginTime())&& StringUtils.isNotBlank(scDutyRecord.getEndTime())
        ,"record_date",scDutyRecord.getBeginTime(),scDutyRecord.getEndTime());
        queryWrapper.eq(StringUtils.isNotBlank(scDutyRecord.getOrgId()), "org_id", scDutyRecord.getOrgId());
        queryWrapper.eq(scDutyRecord.getRecordType() != null, "record_type", scDutyRecord.getRecordType());
        queryWrapper.eq(scDutyRecord.getStateMark() != null, "state_mark", scDutyRecord.getStateMark());
        // 默认排序主键Id赋值
        queryWrapper.orderByDesc("create_date");
        return queryWrapper;

    }
}