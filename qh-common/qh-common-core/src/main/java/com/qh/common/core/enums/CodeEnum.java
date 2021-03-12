package com.qh.common.core.enums;

/**
 * 错误码和错误消息
 *
 * @author
 * @since 2020/9/3
 */
public class CodeEnum {

    public static final CodeEnum SUCCESS = new CodeEnum("200", "请求成功");
    public static final CodeEnum FAILURE = new CodeEnum("300", "请求失败");
    public static final CodeEnum FORBIDDEN = new CodeEnum("403", "没有权限，请联系管理员授权");
    public static final CodeEnum NOT_FOUND = new CodeEnum("404", "路径不存在，请检查路径是否正确");

    public static final CodeEnum PAGE_QUERY_OK = new CodeEnum("1000", "分页查询成功");
    public static final CodeEnum UN_LOGIN = new CodeEnum("1001", "未登录或会话已过期，请重新登录");
    public static final CodeEnum UN_REGISTERED = new CodeEnum("1002", "您还未注册");
    public static final CodeEnum HAS_REGISTERED = new CodeEnum("1003", "该手机号码已注册");
    public static final CodeEnum ACCOUNT_DISABLED = new CodeEnum("1004", "您的账号已被禁用");
    public static final CodeEnum DATE_FORMAT_INVALID = new CodeEnum("1006", "日期格式不正确");
    public static final CodeEnum UTIL_ERROR = new CodeEnum("1007", "工具类调用失败");
    public static final CodeEnum USER_NOT_FOUND = new CodeEnum("1008", "登录用户：{0} 不存在");
    public static final CodeEnum SQL_ORDER_BY_INVALID = new CodeEnum("1009", "参数不符合规范，不能进行查询");
    public static final CodeEnum USER_ACCOUNT_DISABLE = new CodeEnum("1010", "对不起，您的账号：{0} 已停用");
    public static final CodeEnum ALREADY_EXIST = new CodeEnum("1011", "{0}已经存在");
    public static final CodeEnum NOT_EXIST = new CodeEnum("1012", "{0}不存在");
    public static final CodeEnum NOT_EMPTY = new CodeEnum("1013", "{0}不能为空");
    public static final CodeEnum INVOKE_INTERFACE_FAIL = new CodeEnum("1014", "{0}");
    public static final CodeEnum LIST_EMPTY = new CodeEnum("1015", "请选择需要删除的数据");

    // 构造方法
    public CodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 成员变量
    private String msg;
    private String code;

    public String getMsg() {
        return msg;
    }

    public CodeEnum setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getCode() {
        return code;
    }
}
