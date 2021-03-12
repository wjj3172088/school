package com.qh.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户和岗位关联 sys_user_post
 * 
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_basic_sys_user_post")
public class SysUserPost
{
    /** 用户ID */
    private Long userId;
    
    /** 岗位ID */
    private Long postId;

}
