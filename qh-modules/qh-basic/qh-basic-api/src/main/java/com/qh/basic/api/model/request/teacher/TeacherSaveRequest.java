package com.qh.basic.api.model.request.teacher;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/23 10:33
 * @Description: 教师保存请求
 */
@Data
public class TeacherSaveRequest {
    /**
     * 教师Id
     */
    private String teacId;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String teacName;

    /**
     * 身份证号
     */
    @NotBlank(message = "身份证号不能为空")
    private String idCard;

    /**
     * 授课科目(1:语文、2:数学、3:英语)
     */
    @NotNull(message = "授课科目不能为空")
    private String specialtyName;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    /**
     * 老师岗位(1.教师，2.高级教师，3.校长)
     */
    @NotNull(message = "老师岗位不能为空")
    private int jobTitle;

    /**
     * 班级id
     */
    @NotNull(message = "班级不能为空")
    private String classId;

    /**
     * 是否班主任
     */
    @NotNull(message = "是否班主任不能为空")
    private String direct;

    /**
     * 机动车牌号
     */
    private String motorNum;

    /**
     * 非机动车牌号
     */
    private String notMotorNum;

    /**
     * 机动车标签
     */
    private String motorTagNum;

    /**
     * 非机动车标签
     */
    private String notMotorTagNum;

    /**
     * 人脸图片
     */
    private String faceImage;
}
