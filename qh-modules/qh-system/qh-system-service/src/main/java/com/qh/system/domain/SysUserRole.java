package com.qh.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户和角色关联 sys_user_role
 * 
 * @author 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_basic_sys_user_role")
public class SysUserRole
{
    /** 用户ID */
    private Long userId;
    
    /** 角色ID */
    private Long roleId;
}
