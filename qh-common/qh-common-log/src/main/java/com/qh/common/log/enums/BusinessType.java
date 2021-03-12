package com.qh.common.log.enums;

/**
 * 业务操作类型
 * 
 * @author
 */
public enum BusinessType
{
    /**
     * 其它
     */
    OTHER(0, "其它"),

    /**
     * 新增
     */
    INSERT(1, "新增"),

    /**
     * 修改
     */
    UPDATE(2, "修改"),

    /**
     * 删除
     */
    DELETE(3, "删除"),

    /**
     * 授权
     */
    GRANT(4, "授权"),

    /**
     * 导出
     */
    EXPORT(5, "导出"),

    /**
     * 导入
     */
    IMPORT(6, "导入"),

    /**
     * 强退
     */
    FORCE(7, "强退"),

    /**
     * 生成代码
     */
    GENCODE(8, "生成代码"),

    /**
     * 清空数据
     */
    CLEAN(9, "清空数据"),

    /**
     * 发送短信日志
     */
    SMSLOG(10, "发送短信日志"),

    /**
     * 转班
     */
    TRANSFER(11, "转班"),

    /**
     * 升学
     */
    UPGRADE(12, "升学"),

    /**
     * 毕业
     */
    GRADUATE(13, "毕业");

    private final int code;
    private final String info;

    BusinessType(int code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public int getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
