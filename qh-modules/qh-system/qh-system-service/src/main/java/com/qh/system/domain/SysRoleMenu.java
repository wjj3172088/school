package com.qh.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色和菜单关联 sys_role_menu
 * 
 * @author 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_basic_sys_role_menu")
public class SysRoleMenu
{
    /** 角色ID */
    private Long roleId;
    
    /** 菜单ID */
    private Long menuId;
}
