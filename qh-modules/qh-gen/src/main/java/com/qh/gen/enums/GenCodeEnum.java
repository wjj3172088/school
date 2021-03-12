package com.qh.gen.enums;

import com.qh.common.core.enums.CodeEnum;

public class GenCodeEnum {

    public static final CodeEnum IMPORT_FAIL = getCodeEnum("1001", "导入失败：{0}");
    public static final CodeEnum LOAD_TEMPLATE_FAIL = getCodeEnum("1002", "渲染模板失败，表名：{0}");

    public static final CodeEnum TREE_CODE_EMPTY = getCodeEnum("1003", "树编码字段不能为空");
    public static final CodeEnum TREE_PARENT_CODE_EMPTY = getCodeEnum("1004", "树父编码字段不能为空");
    public static final CodeEnum TREE_NAME_EMPTY = getCodeEnum("1005", "树名称字段不能为空");

    public static CodeEnum getCodeEnum(String code, String msg) {
        return new CodeEnum("GEN" + code, msg);
    }


}
