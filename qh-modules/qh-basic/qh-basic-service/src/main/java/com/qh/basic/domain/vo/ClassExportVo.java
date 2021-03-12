package com.qh.basic.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.qh.common.core.annotation.Excel;
import lombok.Data;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/19 13:46
 * @Description:
 */
@Data
public class ClassExportVo {
    /**
     * 年级
     */
    @TableField(exist = false)
    @Excel(name = "年级")
    private String gradeName;

    /**
     * 班别
     */
    @Excel(name = "班别")
    private String classNum;

    /**
     * 学生数量
     */
    @Excel(name = "学生人数")
    private Long stuCount;

    /**
     * 班主任名称
     */
    @Excel(name = "班主任")
    private String teachName ;

    /**
     * 班主任手机
     */
    @Excel(name = "班主任手机")
    private String teachMobile ;
}
