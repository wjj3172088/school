package com.qh.common.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: APP上传图片属性实体
 * @Author: huangdaoquan
 * @Date: 2020/12/8 15:46
 *
 * @return
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentImgVo {
	private String attId;
	private String bucket;
	private String filename;
	private String format;
}
