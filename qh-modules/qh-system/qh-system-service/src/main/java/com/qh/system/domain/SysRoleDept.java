package com.qh.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色和部门关联 sys_role_dept
 * 
 * @author 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_basic_sys_role_dept")
public class SysRoleDept
{
    /** 角色ID */
    private Long roleId;
    
    /** 部门ID */
    private Long deptId;

}
