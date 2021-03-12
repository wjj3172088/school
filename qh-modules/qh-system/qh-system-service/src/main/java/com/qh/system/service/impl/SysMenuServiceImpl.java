package com.qh.system.service.impl;

import com.qh.common.core.constant.UserConstants;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.redis.service.RedisService;
import com.qh.common.security.utils.SecurityUtils;
import com.qh.system.api.constant.SystemKeyConstants;
import com.qh.system.api.domain.SysUser;
import com.qh.system.config.SystemCacheExpireConfig;
import com.qh.system.domain.SysMenu;
import com.qh.system.domain.vo.*;
import com.qh.system.enums.SystemCodeEnum;
import com.qh.system.enums.TypeMenu;
import com.qh.system.mapper.SysMenuMapper;
import com.qh.system.mapper.SysRoleMenuMapper;
import com.qh.system.service.ISysConfigService;
import com.qh.system.service.ISysMenuService;
import com.qh.system.utils.CacheRedisKeyConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单 业务层处理
 *
 * @author
 */
@Service
@Slf4j
public class SysMenuServiceImpl implements ISysMenuService {
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ISysConfigService sysConfigService;

    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(Long userId) {
        return selectMenuList(new SysMenu(), userId);
    }

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
        List<SysMenu> menuList = null;
        // 管理员显示所有菜单信息
        if (SysUser.isAdmin(userId)) {
            menuList = menuMapper.selectMenuList(menu);
        } else {
            menu.getParams().put("userId", userId);
            menuList = menuMapper.selectMenuListByUserId(menu);
        }
        return menuList;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户名称
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuTreeByUserId(Long userId) {
        List<SysMenu> menus = null;
        if (SecurityUtils.isAdmin(userId)) {
            menus = menuMapper.selectMenuTreeAll();
        } else {
            menus = menuMapper.selectMenuTreeByUserId(userId);
        }
        return getChildPerms(menus, 0);
    }

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    @Override
    public List<Integer> selectMenuListByRoleId(Long roleId) {
        return menuMapper.selectMenuListByRoleId(roleId);
    }

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenu menu : menus) {
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
            List<SysMenu> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && cMenus.size() > 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            } else if (isMeunFrame(menu)) {
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = menus.iterator(); iterator.hasNext(); ) {
            SysMenu t = (SysMenu) iterator.next();
            // 根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == 0) {
                recursionFn(menus, t);
                returnList.add(t);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus) {
        List<SysMenu> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public SysMenu selectMenuById(Long menuId) {
        return menuMapper.selectMenuById(menuId);
    }

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public boolean hasChildByMenuId(Long menuId) {
        int result = menuMapper.hasChildByMenuId(menuId);
        return result > 0 ? true : false;
    }

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public boolean checkMenuExistRole(Long menuId) {
        int result = roleMenuMapper.checkMenuExistRole(menuId);
        return result > 0 ? true : false;
    }

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int insertMenu(SysMenu menu) {
        return menuMapper.insertMenu(menu);
    }

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int updateMenu(SysMenu menu) {
        return menuMapper.updateMenu(menu);
    }

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int deleteMenuById(Long menuId) {
        return menuMapper.deleteMenuById(menuId);
    }

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public String checkMenuNameUnique(SysMenu menu) {
        Long menuId = StringUtils.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
        SysMenu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (StringUtils.isNotNull(info) && info.getMenuId().longValue() != menuId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(SysMenu menu) {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMeunFrame(menu)) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && UserConstants.NO_FRAME.equals(menu.getIsFrame())) {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMeunFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenu menu) {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMeunFrame(menu)) {
            component = menu.getComponent();
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMeunFrame(SysMenu menu) {
        return menu.getParentId().intValue() == 0 && UserConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(UserConstants.NO_FRAME);
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext(); ) {
            SysMenu t = (SysMenu) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 查询所有的快捷菜单列表
     *
     * @return 菜单列表
     */
    @Override
    public List<FastMenu> selectFastMenuTreeAll() {
        Long userId = SecurityUtils.getUserId();
        //获取所有的快捷方式
        List<FastMenu> list;
        if (SecurityUtils.isAdmin(userId)) {
            list = menuMapper.selectAdminFastMenuAll();
        } else {
            list = menuMapper.selectFastMenuAll(userId);
        }

        //用户添加的快捷方式列表
        List<FastMenu> myFastMenuList = this.selectMyFastMenu(SecurityUtils.getUserId());

        //用户没有添加快捷方式的列表
        if (!CollectionUtils.isEmpty(myFastMenuList)) {
            for (FastMenu fastMenu : list) {
                for (FastMenu myFastMenu : myFastMenuList) {
                    if (myFastMenu.getMenuId().equals(fastMenu.getMenuId())) {
                        fastMenu.setIsMyFast(String.valueOf(TypeMenu.YES.getCode()));
                    }
                }
            }
        }
        return list;
    }

    /**
     * 设置当前用户的快捷方式
     *
     * @param menuIdList 设置的菜单id列表
     */
    @Override
    @CacheEvict(cacheNames = SystemCacheExpireConfig.MY_FAST, key = "'" + SystemCacheExpireConfig.MY_FAST + "userId" + ":'+#userId")
    public void saveFastMenu(List<Long> menuIdList, Long userId) {
        //对传入的菜单id做去重处理
        List<Long> myMenuIdList = menuIdList.stream().distinct().collect(Collectors.toList());
        //快捷方式个数限制判断
        int count = myMenuIdList.size();
        Integer menuCountLimit = sysConfigService.selectIntByKey(SystemKeyConstants.FAST_MENU_COUNT_LIMIT);
        if (menuCountLimit == null) {
            throw new BizException(SystemCodeEnum.NOT_CONFIG_FAST_MENU_COUNT_LIMIT);
        }
        if (count > menuCountLimit) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "快捷方式个数最多只能设置" + menuCountLimit + "个");
        }

        MyFastMenu myFastMenu = new MyFastMenu();
        myFastMenu.setType(TypeMenu.NO.getCode());
        if (!CollectionUtils.isEmpty(myMenuIdList)) {
            //传入菜单id不为空，则去查询对应的菜单信息；如果传入为空，则清空我的快捷菜单列表
            List<FastMenu> fastMenuList;
            if (SecurityUtils.isAdmin(userId)) {
                fastMenuList = menuMapper.selectAdminFastMenuByMenuId(myMenuIdList);
            } else {
                fastMenuList = menuMapper.selectFastMenuByMenuId(myMenuIdList, userId);
            }
            this.calcFastMenu(fastMenuList);
            myFastMenu.setFastMenuList(fastMenuList);
        }
        redisService.setCacheObject(String.format(CacheRedisKeyConfigure.SSCL_MY_MENU_LIST, userId), myFastMenu);
    }

    /**
     * 获取当前登陆用户的快捷菜单
     *
     * @return
     */
    @Override
    @Cacheable(cacheNames = SystemCacheExpireConfig.MY_FAST, key = "'" + SystemCacheExpireConfig.MY_FAST + "userId" + ":'+#userId")
    public List<FastMenu> selectMyFastMenu(Long userId) {
        MyFastMenu myFastMenu = redisService.getCacheObject(String.format(CacheRedisKeyConfigure.SSCL_MY_MENU_LIST, userId));
        List<FastMenu> mylist = null;
        if (myFastMenu == null || myFastMenu.getType() == TypeMenu.YES.getCode()) {
            //如果没有初始值，或者type为初始值，则表明用户没有自定义过快捷方式，返回默认的快捷方式
            if (SecurityUtils.isAdmin(userId)) {
                mylist = menuMapper.selectAdminFastDefaultMenu();
                log.info("selectMyFastMenu:默认:admin:" + mylist);
            } else {
                mylist = menuMapper.selectFastDefaultMenu(userId);
                log.info("selectMyFastMenu:默认:normal:" + mylist);
            }
        } else {
            List<FastMenu> list = myFastMenu.getFastMenuList();
            log.info("selectMyFastMenu:不是默认:" + list);
            if (!CollectionUtils.isEmpty(list)) {
                List<Long> menuIdList = list.stream().map(FastMenu::getMenuId).collect(Collectors.toList());
                if (SecurityUtils.isAdmin(userId)) {
                    mylist = menuMapper.selectAdminFastMenuByMenuId(menuIdList);
                    log.info("selectMyFastMenu:不是默认:admin:" + mylist);
                } else {
                    mylist = menuMapper.selectFastMenuByMenuId(menuIdList, userId);
                    log.info("selectMyFastMenu:不是默认:normal:" + mylist);
                }
            }
        }
        log.info("selectMyFastMenu:" + mylist);
        if (!CollectionUtils.isEmpty(mylist)) {
            this.calcFastMenu(mylist);
        }
        return mylist;
    }

    /**
     * 修改保存快捷菜单信息
     *
     * @param menu 快捷菜单信息
     * @return 结果
     */
    @Override
    @CacheEvict(value = SystemCacheExpireConfig.MY_FAST, allEntries = true)
    public int updateFastMenu(SysMenu menu) {
        return menuMapper.updateFastMenu(menu);
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<SysMenu> it = childList.iterator();
                while (it.hasNext()) {
                    SysMenu n = (SysMenu) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext()) {
            SysMenu n = (SysMenu) it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 将菜单集合设置完整的路由地址
     *
     * @param fastMenuList 菜单集合
     */
    private void calcFastMenu(List<FastMenu> fastMenuList) {
        for (FastMenu fastMenu : fastMenuList) {
            List<String> pathList = new ArrayList<>();
            //获取路由地址集合
            this.doCalcFastMenu(fastMenu.getMenuId(), pathList);
            //将获取到的路由地址集合倒序
            Collections.reverse(pathList);
            //将路由地址集合拼接成完成地址
            String path = pathList.stream().collect(Collectors.joining("/"));
            fastMenu.setPath(path);
        }
    }

    /**
     * 递归获取路由地址
     *
     * @param parentId 父菜单id
     * @param pathList 路由地址集合
     */
    private void doCalcFastMenu(Long parentId, List<String> pathList) {
        SysMenu menu = this.menuMapper.selectMenuById(parentId);
        if (menu != null) {
            pathList.add(menu.getPath());
            if (parentId != 0) {
                this.doCalcFastMenu(menu.getParentId(), pathList);
            }
        }
    }
}
