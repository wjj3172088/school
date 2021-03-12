package com.qh.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qh.common.core.annotation.Excel;
import com.qh.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 机构管理对象 t_sys_org
 *
 * @author 汪俊杰
 * @date 2020-11-12
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sys_org")
public class SysOrg extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 组织Id
     */
    @TableId(value = "org_id", type = IdType.ID_WORKER_STR)
    private String orgId;

    /**
     * 组织名称
     */
    @Excel(name = "组织名称")
    private String orgName;

    /**
     * 组织代码
     */
    @Excel(name = "组织代码")
    private String orgCode;

    /**
     * 组织类型
     */
    @Excel(name = "组织类型")
    private String orgType;

    /**
     * 上级组织Id
     */
    @Excel(name = "上级组织Id")
    private String upOrgId;

    /**
     * 省份代码
     */
    @Excel(name = "省份代码")
    private String provinceCode;

    /**
     * 辖区代码
     */
    @Excel(name = "辖区代码")
    private String prefectureCode;

    /**
     * 县级代码
     */
    @Excel(name = "县级代码")
    private String countyCode;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Long orgSort;

    /**
     * 删除标志(Y:正常, N:无效,  D:删除)
     */
    @Excel(name = "删除标志(Y:正常, N:无效,  D:删除)")
    private String stateMark;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 创建日期
     */
    @Excel(name = "创建日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 修改日期
     */
    @Excel(name = "修改日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;

    /** 子菜单 */
    @TableField(exist = false)
    private List<SysOrg> children = new ArrayList<SysOrg>();
}
