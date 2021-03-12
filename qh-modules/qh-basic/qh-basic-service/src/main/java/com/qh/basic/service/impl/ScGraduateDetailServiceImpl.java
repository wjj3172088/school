package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.ScGraduateDetail;
import com.qh.basic.domain.vo.StudentExtendVo;
import com.qh.basic.enums.DictTypeEnum;
import com.qh.basic.mapper.ScGraduateDetailMapper;
import com.qh.basic.service.IScGraduateDetailService;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.DateUtils;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.bean.BeanUtils;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.security.utils.SecurityUtils;
import com.qh.system.api.RemoteDictDataService;
import com.qh.system.api.domain.SysDictData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 毕业详情Service业务层处理
 *
 * @author 汪俊杰
 * @date 2020-12-28
 */
@Service
public class ScGraduateDetailServiceImpl extends ServiceImpl<ScGraduateDetailMapper, ScGraduateDetail> implements IScGraduateDetailService {
    /**
     * 查询毕业详情集合
     *
     * @param page             分页信息
     * @param scGraduateDetail 操作毕业详情对象
     * @return 操作毕业详情集合
     */
    @Override
    public IPage<ScGraduateDetail> selectScGraduateDetailListByPage(IPage<ScGraduateDetail> page, ScGraduateDetail scGraduateDetail) {
        if (scGraduateDetail.getYear() == null) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "届别不能为空");
        }
        return this.page(page, getQuery(scGraduateDetail));
    }

    /**
     * 查询导出
     *
     * @param scGraduateDetail 导出请求
     * @return
     */
    @Override
    public List<ScGraduateDetail> selectExport(ScGraduateDetail scGraduateDetail) {
        if (scGraduateDetail.getYear() == null) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "届别不能为空");
        }
        QueryWrapper<ScGraduateDetail> queryWrapper = this.getQuery(scGraduateDetail);
        return super.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增毕业详情
     *
     * @param studentExtendList 毕业学校列表
     * @param studentExtendList 亲属关系字典列表
     */
    @Override
    public void add(List<StudentExtendVo> studentExtendList, List<SysDictData> guarRelationList) {
        if (CollectionUtils.isEmpty(studentExtendList)) {
            return;
        }

        int year = Integer.valueOf(DateUtils.getSysYear());
        int nowDate = DateUtil.getSystemSeconds();
        List<ScGraduateDetail> graduateDetailList = new ArrayList<>();
        studentExtendList.forEach(x -> {
            ScGraduateDetail detail = new ScGraduateDetail();
            BeanUtils.copyProperties(x, detail);
            // 家属关系
            String guardianRelationName = this.selectNameByCode(x.getGuardianRelation(), guarRelationList, DictTypeEnum.GUAR_RELATION.getName());
            detail.setGuardianRelationName(guardianRelationName);
            detail.setYear(year);
            detail.setTeacherId(x.getTeacId());
            detail.setTeacherName(x.getTeacName());
            detail.setTeacherMobile(x.getTeacMobile());
            detail.setCreateDate(nowDate);
            detail.setModifyDate(nowDate);
            graduateDetailList.add(detail);
        });
        super.baseMapper.batchInsert(graduateDetailList);
    }

    /**
     * 根据字典名字查询值
     *
     * @param code            字典key
     * @param sysDictDataList 字典集合
     * @param tipMsg          提示信息
     * @return
     */
    private String selectNameByCode(Integer code, List<SysDictData> sysDictDataList, String tipMsg) {
        SysDictData dictData =
                sysDictDataList.stream().filter(x -> x.getItemVal().equals(code.toString())).findAny().orElse(null);
        if (dictData == null) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "系统未配置" + tipMsg + "的值(" + code + ")");
        }
        return dictData.getItemName();
    }

    /**
     * 查询毕业详情参数拼接
     */
    private QueryWrapper<ScGraduateDetail> getQuery(ScGraduateDetail scGraduateDetail) {
        QueryWrapper<ScGraduateDetail> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(StringUtils.isNotBlank(scGraduateDetail.getStuName()), "stu_name", scGraduateDetail.getStuName());
        queryWrapper.eq(StringUtils.isNotBlank(scGraduateDetail.getIdCard()), "id_card", scGraduateDetail.getIdCard());
        queryWrapper.eq(scGraduateDetail.getYear() != null, "year", scGraduateDetail.getYear());
        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        queryWrapper.eq(StringUtils.isNotBlank(scGraduateDetail.getGuardianMobile()), "guardian_mobile", scGraduateDetail.getGuardianMobile());
        // 默认排序主键Id赋值
        queryWrapper.orderByDesc("create_date");
        return queryWrapper;
    }
}