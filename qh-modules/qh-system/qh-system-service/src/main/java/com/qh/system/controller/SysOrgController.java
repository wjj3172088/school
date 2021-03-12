package com.qh.system.controller;

import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.R;
import com.qh.common.security.annotation.CheckBackendToken;
import com.qh.system.domain.SysOrg;
import com.qh.system.domain.vo.TreeSelect;
import com.qh.system.service.ISysOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/12 15:26
 * @Description: 学校
 */
@RestController
@RequestMapping("/school")
@CheckBackendToken
public class SysOrgController extends BaseController {
    @Autowired
    private ISysOrgService sysOrgService;

    /**
     * 获取学校下拉树列表
     */
    @GetMapping("/schoolTree")
    public R<List<TreeSelect>> schoolTreeSelect() {
        return R.ok(sysOrgService.selectTree());
    }

    /**
     * 根据OrgId获取学校名称 OrgName
     */
    @GetMapping("/selectOrgNameById")
    public String selectNameById(String orgId) {
        return sysOrgService.selectNameById(orgId);
    }

    /**
     * 根据OrgId获取学校信息
     */
    @GetMapping("/getSysOrgById")
    public R<SysOrg> getSysOrgById(String orgId) {
        return R.ok(sysOrgService.getById(orgId));
    }
}
