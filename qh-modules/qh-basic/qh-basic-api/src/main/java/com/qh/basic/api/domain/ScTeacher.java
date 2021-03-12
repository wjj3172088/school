package com.qh.basic.api.domain;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 教师信息对象 t_sc_teacher
 *
 * @author 汪俊杰
 * @date 2020-11-18
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_sc_teacher")
public class ScTeacher extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 老师Id
     */
    @TableId(value = "teac_id")
    private String teacId;

    /**
     * 用户ID，t_sc_move_acc acc_num_id 外键
     */
    private String accId;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String teacName;

    /**
     * 性别(M:男，W:女)
     */
    private String sex;

    /**
     * 身份证号
     */
    @Excel(name = "身份证号")
    private String idCard;

    /**
     * 教师号
     */
    private String teacNum;

    /**
     * 职称(1.教师，2.高级教师，3.校长)
     */
    private Integer jobTitle;

    /**
     * 专业(1:语文、2:数学、3:英语)
     */
    @Excel(name = "专业")
    private Integer specialty;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String mobile;

    /**
     * 机构id
     */
    private String orgId;

    /**
     * 删除标志(Y:正常, N:无效,  D:删除，V:未激活)
     */
    private String stateMark;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 修改日期
     */
    private Date modifyDate;

    /**
     * 激活时间
     */
    private Date activityDate;

    /**
     * 人脸图片
     */
    private String faceImage;

    /**
     * 是否班主任（Y:是，N:否）
     */
    @TableField(exist = false)
    private String direct;


    /**
     * 工号
     */
    private Long jobNumber;

    /**
     * 健康码状态
     */
    @TableField(exist = false)
    private Integer healthState;
}
