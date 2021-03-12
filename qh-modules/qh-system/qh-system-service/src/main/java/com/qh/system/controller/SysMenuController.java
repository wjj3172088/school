package com.qh.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.qh.common.core.constant.Constants;
import com.qh.common.core.constant.UserConstants;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.AjaxResult;
import com.qh.common.core.web.domain.R;
import com.qh.common.log.annotation.Log;
import com.qh.common.log.enums.BusinessType;
import com.qh.common.security.annotation.CheckBackendToken;
import com.qh.common.security.domain.LoginUser;
import com.qh.common.security.utils.SecurityUtils;
import com.qh.system.domain.SysMenu;
import com.qh.system.domain.vo.FastMenu;
import com.qh.system.domain.vo.RouterVo;
import com.qh.system.domain.vo.TreeSelect;
import com.qh.system.service.ISysMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单信息
 *
 * @author
 */
@RestController
@RequestMapping("/menu")
@CheckBackendToken
public class SysMenuController extends BaseController {
    @Autowired
    private ISysMenuService menuService;

    /**
     * 获取菜单列表
     */
    @PreAuthorize("@ss.hasPermi('system:menu:list')")
    @GetMapping("/list")
    public R<List<SysMenu>> list(SysMenu menu) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId();
        List<SysMenu> menus = menuService.selectMenuList(menu, userId);
        return R.ok(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @GetMapping(value = "/{menuId}")
    public R<SysMenu> getInfo(@PathVariable Long menuId) {
        return R.ok(menuService.selectMenuById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public R<List<TreeSelect>> treeselect(SysMenu menu) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId();
        List<SysMenu> menus = menuService.selectMenuList(menu, userId);
        return R.ok(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public R<JSONObject> roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId();
        List<SysMenu> menus = menuService.selectMenuList(userId);
        JSONObject data = new JSONObject();
        data.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        data.put("menus", menuService.buildMenuTreeSelect(menus));
        return R.ok(data);
    }

    /**
     * 新增菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:add')")
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R add(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return R.fail("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame())
                && !StringUtils.startsWithAny(menu.getPath(), Constants.HTTP, Constants.HTTPS)) {
            return R.fail("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        menu.setCreateBy(SecurityUtils.getUsername());
        return toResult(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:edit')")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R edit(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return R.fail("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame())
                && !StringUtils.startsWithAny(menu.getPath(), Constants.HTTP, Constants.HTTPS)) {
            return R.fail("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        } else if (menu.getMenuId().equals(menu.getParentId())) {
            return R.fail("新增菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menu.setUpdateBy(SecurityUtils.getUsername());
        return toResult(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:remove')")
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuId}")
    public R remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return R.fail("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId)) {
            return R.fail("菜单已分配,不允许删除");
        }
        return toResult(menuService.deleteMenuById(menuId));
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public R<List<RouterVo>> getRouters() {
        Long userId = SecurityUtils.getLoginUser().getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return R.ok(menuService.buildMenus(menus));
    }

    /**
     * 查询所有的快捷菜单列表
     *
     * @return
     */
    @GetMapping("/fast/list")
    public AjaxResult fastAll() {
        return AjaxResult.success(menuService.selectFastMenuTreeAll());
    }

    /**
     * 获取当前登陆用户的快捷菜单
     *
     * @return
     */
    @GetMapping("/myfast")
    public AjaxResult myMenu() {
        return AjaxResult.success(menuService.selectMyFastMenu(SecurityUtils.getUserId()));
    }

    /**
     * 设置当前用户的快捷方式
     *
     * @param menuIdList 设置的菜单id列表
     * @return
     */
    @PostMapping("/myfast/save")
    public AjaxResult saveMyMenu(@RequestBody List<Long> menuIdList) {
        menuService.saveFastMenu(menuIdList, SecurityUtils.getUserId());
        return AjaxResult.success(1);
    }

    /**
     * 修改快捷方式菜单
     *
     * @param fastMenu 快捷方式列表
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:menu:fast:edit')")
    @Log(title = "菜单管理", businessType = BusinessType.OTHER)
    @PutMapping("/fast")
    public R updateFastMenu(@Validated @RequestBody FastMenu fastMenu) {
        SysMenu menu = new SysMenu();
        BeanUtils.copyProperties(fastMenu, menu);
        menu.setUpdateBy(SecurityUtils.getUsername());
        return toResult(menuService.updateFastMenu(menu));
    }
}