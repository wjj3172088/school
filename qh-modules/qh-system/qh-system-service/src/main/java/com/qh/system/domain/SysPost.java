package com.qh.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qh.common.core.annotation.Excel;
import com.qh.common.core.annotation.Excel.ColumnType;
import com.qh.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 岗位表 sys_post
 * 
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_basic_sys_post")
public class SysPost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 岗位序号 */
    @Excel(name = "岗位序号", cellType = ColumnType.NUMERIC)
    @TableId(type = IdType.AUTO)
    private Long postId;

    /** 岗位编码 */
    @Excel(name = "岗位编码")
    @NotBlank(message = "岗位编码不能为空")
    @Size(max = 64, message = "岗位编码长度不能超过64个字符")
    private String postCode;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    @NotBlank(message = "岗位名称不能为空")
    @Size(max = 50, message = "岗位名称长度不能超过50个字符")
    private String postName;

    /** 岗位排序 */
    @Excel(name = "岗位排序")
    @NotBlank(message = "显示顺序不能为空")
    private String postSort;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 用户是否存在此岗位标识 默认不存在 */
    @TableField(exist = false)
    private boolean flag = false;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;
}
