package com.qh.basic.model.request.teacher;

import com.qh.common.core.annotation.Excel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/24 08:34
 * @Description: 教师导入请求
 */
@Data
public class TeacherImportRequest {
    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Excel(name = "老师姓名")
    private String teacName;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Excel(name = "老师手机号码")
    private String mobile;

    /**
     * 身份证号
     */
    @NotBlank(message = "身份证号不能为空")
    @Excel(name = "老师身份证")
    private String idCard;

    /**
     * 老师岗位(1.教师，2.高级教师，3.校长)
     */
    @NotBlank(message = "老师岗位不能为空")
    @Excel(name = "老师岗位")
    private String jobTitle;

    /**
     * 老师授课科目(1.语文，2.数学，3.英语，4.体育，5.美术，6.音乐，7.信息技术，8.科学，9.幼教全科)
     */
    @NotNull(message = "老师授课科目不能为空")
    @Excel(name = "老师授课科目")
    private String specialtyName;

    /**
     * 年级
     */
    @NotNull(message = "年级不能为空")
    @Excel(name = "年级")
    private String gradeName;

    /**
     * 年级
     */
    @NotNull(message = "班级不能为空")
    @Excel(name = "班级")
    private String classNumName;

    /**
     * 是否班主任
     */
    @NotNull(message = "是否班主任不能为空")
    @Excel(name = "是否班主任")
    private String direct;
}
