package com.qh.basic.service;

import com.qh.basic.domain.Attachment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.common.core.enums.BizType;

import java.util.List;

/**
 * 附件Service接口
 *
 * @author 汪俊杰
 * @date 2020-11-26
 */
public interface IAttachmentService extends IService<Attachment> {
    /**
     * 批量新增
     *
     * @param list    图片列表
     * @param accId   用户id
     * @param bizId   业务id
     * @param bizType 业余类型
     * @return
     */
    String batchInsert(List<String> list, String accId, String bizId, String bizType);
}
