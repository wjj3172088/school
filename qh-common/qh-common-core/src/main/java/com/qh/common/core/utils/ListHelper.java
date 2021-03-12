
package com.qh.common.core.utils;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

/**
 * @project LibTools
 * @package com.dougege.tools
 * @filename ListHelper.java
 * @createtime 2020年8月3日下午5:08:23  
 * @author  fcj2593@163.com
 * @todo   TODO
 */
@Getter
public class ListHelper<T> extends LinkedList<T>   {
	/** 
	* @Fields 	:serialVersionUID : TODO
	*/
	private static final long serialVersionUID = 5122944710664522759L;
	
//	private List<T> list = new LinkedList<>();
	
	public List<T> getList(){
		return this;
	}
	public ListHelper<T> Add(T t) {
		this.add(t);
		return this;
	}
	
	public ListHelper() {
		super();//.new LinkedList<T>();
	}
//	@Override
//	public void close() throws IOException {
//		// TODO Auto-generated method stub
//		this.clear();
//	}
	
}
