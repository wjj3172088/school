package com.qh.system.enums;

import com.qh.common.core.enums.CodeEnum;

public class SystemCodeEnum {

    public static final CodeEnum DATA_ASSIGNED = getCodeEnum("1001", "{0}已分配,不能删除");
    public static final CodeEnum DEPT_DISABLED = getCodeEnum("1002", "部门停用，不允许新增");
    public static final CodeEnum ADMIN_CAN_NOT_OPR = getCodeEnum("1003", "不允许操作超级管理员用户");
    public static final CodeEnum EXPORT_DATA_EMPTY = getCodeEnum("1004", "导入用户数据不能为空！");
    public static final CodeEnum EXPORT_FAIL = getCodeEnum("1005", "导入数据失败");
    public static final CodeEnum NOT_EXIST = getCodeEnum("1006", "{}不存在");
    public static final CodeEnum NOT_CONFIG_FAST_MENU_COUNT_LIMIT = getCodeEnum("1007", "快捷方式个数限制没有配置");

    public static CodeEnum getCodeEnum(String code, String msg) {
        return new CodeEnum("SYS" + code, msg);
    }


}
