package com.qh.system.domain.vo;

import lombok.Data;

/**
 * @Description: 图片返回实体
 * @Author: huangdaoquan
 * @Date: 2020/12/2 16:43
 *
 * @return
 */
@Data
public class ImageResult {
	
	/**请求状态 SUCCESS
	 */
	private String state;
	
	/**
	 * 图片全路径 如：http://scs-app-cs0.oss-cn-hangzhou.aliyuncs.com/testUeditor/UEI-20201202163246024o5.png
	 *
	 */
	private String url;
	
	/**
	 * 图片除文件名外的前缀 如：http://scs-app-cs0.oss-cn-hangzhou.aliyuncs.com/testUeditor/
	 */
	private String title;

	/**
	 * 图片文件名 如：UEI-20201202163246024o5.png
	 */
	private String original;

	/**
	 * 初始文件名 如：UEI-20201202163246024o5.png
	 */
	private String fileName;
}
