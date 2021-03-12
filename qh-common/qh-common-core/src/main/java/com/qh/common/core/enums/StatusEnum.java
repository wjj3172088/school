package com.qh.common.core.enums;

/**
 * 
 *  擎海万物状态
 *  @author mds
 *  @DateTime 2016-6-2 上午10:08:36
 */
public enum StatusEnum {

	/**
	 * 正常
	 */
	normal("Y", "正常"),
	/**
	 * 停用
	 */
	disenable("N", "停用"),
	/**
	 * 删除
	 */
	delete("D", "删除"),
	
	/**
	 * 注销状态
	 */
	cancel("C", "注销");
	
	
	private String _value;
    private String _name;

    private StatusEnum(String value, String name)
    {
       _value = value;
        _name = name;
    }

    public String value()
    {
        return _value;
    }

    public String getName()
    {
        return _name;
     }
}
