package com.qh.basic.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qh.common.core.annotation.Excel;
import com.qh.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 三防保安信息对象 t_sc_security_staff
 *
 * @author 汪俊杰
 * @date 2021-01-21
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_security_staff")
public class SecurityStaff extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(value = "staff_id")
    private String staffId;

    /**
     * 保安姓名
     */
    private String name;

    /**
     * 所属学校id
     */
    private String orgId;

    /**
     * 所属学校名称
     */
    private String orgName;

    /**
     * 性别
     */
    private Boolean sex;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 保安证编号
     */
    private String staffNumber;

    /**
     * 保安公司
     */
    private String company;

    /**
     * 政治面貌
     */
    private Integer politicalFace;

    /**
     * 聘用性质
     */
    private Integer recruitType;

    /**
     * 从事保安工作时间
     */
    private Long staffYear;

    /**
     * 聘用日期
     */
    @Excel(name = "聘用日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date workTime;

    /**
     * 合同有效期(年)
     */
    private Long contractExpire;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 学历
     */
    private Integer educationType;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 创建时间
     */
    private Integer createDate;

    /**
     * 修改时间
     */
    private Integer updateDate;
}
