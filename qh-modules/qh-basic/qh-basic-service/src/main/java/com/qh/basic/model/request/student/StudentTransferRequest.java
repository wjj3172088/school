package com.qh.basic.model.request.student;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author: 汪俊杰
 * @Date: 2020/12/24 15:29
 * @Description: 转班请求
 */
@Data
public class StudentTransferRequest {
    /**
     * 学生id集合
     */
    private List<String> stuIdList;

    /**
     * 转入的班级id
     */
    @NotBlank(message = "班级id不能为空")
    private String classId;
}
