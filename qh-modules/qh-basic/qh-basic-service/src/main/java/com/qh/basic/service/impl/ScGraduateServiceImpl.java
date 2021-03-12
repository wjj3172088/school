package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.ScGraduate;
import com.qh.basic.enums.OperUserTypeEnum;
import com.qh.basic.mapper.ScGraduateMapper;
import com.qh.basic.service.IScGraduateService;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.DateUtils;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.DateUtil;
import com.qh.common.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * 毕业总览Service业务层处理
 *
 * @author 汪俊杰
 * @date 2020-12-28
 */
@Service
public class ScGraduateServiceImpl extends ServiceImpl<ScGraduateMapper, ScGraduate> implements IScGraduateService {
    /**
     * 查询毕业总览集合
     *
     * @param page       分页信息
     * @param scGraduate 操作毕业总览对象
     * @return 操作毕业总览集合
     */
    @Override
    public IPage<ScGraduate> selectScGraduateListByPage(IPage<ScGraduate> page, ScGraduate scGraduate) {
        return this.page(page, getQuery(scGraduate));
    }

    /**
     * 新增毕业总览信息
     *
     * @param orgId   学校id
     * @param orgName 学校名称
     * @param count   学生人数
     */
    @Override
    public void add(String orgId, String orgName, Long count) {
        int year = Integer.valueOf(DateUtils.getSysYear());
        boolean result = this.exsitByOrgIdAndYear(orgId, year);
        if (result) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "本年度已做过毕业操作");
        }

        ScGraduate graduate = new ScGraduate();
        graduate.setOrgId(orgId);
        graduate.setOrgName(orgName);
        graduate.setYear(year);
        graduate.setCount(count);
        graduate.setOperId(SecurityUtils.getUserId().toString());
        graduate.setOperName(SecurityUtils.getUsername());
        graduate.setOperUserType(OperUserTypeEnum.BACKEND.getCode());
        graduate.setCreateDate(DateUtil.getSystemSeconds());
        graduate.setModifyDate(DateUtil.getSystemSeconds());
        super.baseMapper.insert(graduate);
    }

    /**
     * 查询毕业总览参数拼接
     */
    private QueryWrapper<ScGraduate> getQuery(ScGraduate scGraduate) {
        QueryWrapper<ScGraduate> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("org_id", SecurityUtils.getOrgId());
        queryWrapper.like(StringUtils.isNotBlank(scGraduate.getOrgName()), "org_name", scGraduate.getOrgName());
        queryWrapper.eq(scGraduate.getYear() != null, "year", scGraduate.getYear());
        queryWrapper.like(StringUtils.isNotBlank(scGraduate.getOperName()), "oper_name", scGraduate.getOperName());
        queryWrapper.eq(scGraduate.getOperUserType() != null, "oper_user_type", scGraduate.getOperUserType());
        // 默认排序主键Id赋值
        queryWrapper.orderByDesc("create_date");
        return queryWrapper;
    }

    /**
     * 根据学校id和年份判断是否存在
     *
     * @param orgId 学校id
     * @param year  年份
     * @return
     */
    private boolean exsitByOrgIdAndYear(String orgId, int year) {
        QueryWrapper<ScGraduate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_id", orgId);
        queryWrapper.eq("year", year);
        return super.baseMapper.selectCount(queryWrapper) > 0;
    }
}