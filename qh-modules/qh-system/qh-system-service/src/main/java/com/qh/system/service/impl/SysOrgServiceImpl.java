package com.qh.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.common.core.utils.StringUtils;
import com.qh.system.domain.SysOrg;
import com.qh.system.domain.vo.TreeSelect;
import com.qh.system.mapper.SysOrgMapper;
import com.qh.system.service.ISysOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/12 15:27
 * @Description:学校
 */
@Service
public class SysOrgServiceImpl extends ServiceImpl<SysOrgMapper, SysOrg> implements ISysOrgService {
    @Autowired
    private SysOrgMapper sysOrgMapper;

    /**
     * 获取省市地区和学校级联信息
     *
     * @return
     */
    @Override
    public List<TreeSelect> selectTree() {
        List<SysOrg> orgs = sysOrgMapper.selectOrgAll();
        List<SysOrg> deptTrees = this.buildOrgTree(orgs);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据学校id查询学校名称
     *
     * @param orgId 学校id
     * @return 学校名称
     */
    @Override
    public String selectNameById(String orgId) {
        SysOrg sysOrg = sysOrgMapper.selectById(orgId);
        if (sysOrg != null) {
            return sysOrg.getOrgName();
        }
        return null;
    }

    /**
     * 构建前端所需要树结构
     *
     * @param orgs 列表
     * @return 树结构列表
     */
    private List<SysOrg> buildOrgTree(List<SysOrg> orgs) {
        List<SysOrg> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        for (SysOrg org : orgs) {
            tempList.add(org.getOrgId());
        }
        for (Iterator<SysOrg> iterator = orgs.iterator(); iterator.hasNext(); ) {
            SysOrg org = iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(org.getUpOrgId())) {
                recursionFn(orgs, org);
                returnList.add(org);
            }
        }
        if (returnList.isEmpty()) {
            returnList = orgs;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysOrg> list, SysOrg t) {
        // 得到子节点列表
        List<SysOrg> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysOrg tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<SysOrg> it = childList.iterator();
                while (it.hasNext()) {
                    SysOrg n = (SysOrg) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysOrg> getChildList(List<SysOrg> list, SysOrg t) {
        List<SysOrg> tlist = new ArrayList<>();
        Iterator<SysOrg> it = list.iterator();
        while (it.hasNext()) {
            SysOrg n = it.next();
            if (StringUtils.isNotNull(n.getUpOrgId()) && n.getUpOrgId().equals(t.getOrgId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysOrg> list, SysOrg t) {
        return getChildList(list, t).size() > 0;
    }
}
