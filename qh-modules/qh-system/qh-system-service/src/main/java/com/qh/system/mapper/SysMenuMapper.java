package com.qh.system.mapper;

import java.util.List;

import com.qh.system.domain.SysMenu;
import com.qh.system.domain.vo.FastMenu;
import org.apache.ibatis.annotations.Param;

/**
 * 菜单表 数据层
 *
 * @author
 */
public interface SysMenuMapper {
    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuList(SysMenu menu);

    /**
     * 根据用户所有权限
     *
     * @return 权限列表
     */
    public List<String> selectMenuPerms();

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuListByUserId(SysMenu menu);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public List<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据用户ID查询菜单
     *
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuTreeAll();

    /**
     * 根据用户ID查询菜单
     *
     * @param username 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    public List<Integer> selectMenuListByRoleId(Long roleId);

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    public SysMenu selectMenuById(Long menuId);

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    public int hasChildByMenuId(Long menuId);

    /**
     * 新增菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public int insertMenu(SysMenu menu);

    /**
     * 修改菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public int updateMenu(SysMenu menu);

    /**
     * 修改菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public int updateFastMenu(SysMenu menu);

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    public int deleteMenuById(Long menuId);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menuName 菜单名称
     * @param parentId 父菜单ID
     * @return 结果
     */
    public SysMenu checkMenuNameUnique(@Param("menuName") String menuName, @Param("parentId") Long parentId);

    /**
     * 查询所有的快捷菜单列表
     *
     * @return 菜单列表
     */
    public List<FastMenu> selectFastMenuAll(@Param("userId") Long userId);

    /**
     * 查询所有的快捷菜单列表
     *
     * @return 菜单列表
     */
    List<FastMenu> selectAdminFastMenuAll();

    /**
     * 查询快捷菜单默认列表
     *
     * @return 菜单列表
     */
    List<FastMenu> selectFastDefaultMenu(@Param("userId") Long userId);

    /**
     * 根据菜单id查询快捷方式
     *
     * @return 菜单列表
     */
    List<FastMenu> selectFastMenuByMenuId(@Param("menuIdList") List<Long> menuIdList, @Param("userId") Long userId);

    /**
     * 查询快捷菜单默认列表
     *
     * @return 菜单列表
     */
    List<FastMenu> selectAdminFastDefaultMenu();

    /**
     * 根据菜单id查询快捷方式
     *
     * @return 菜单列表
     */
    List<FastMenu> selectAdminFastMenuByMenuId(@Param("menuIdList") List<Long> menuIdList);
}
