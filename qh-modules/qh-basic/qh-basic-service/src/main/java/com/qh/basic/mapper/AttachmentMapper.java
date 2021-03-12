package com.qh.basic.mapper;

import com.qh.basic.domain.Attachment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qh.basic.domain.ScNewsNew;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 附件Mapper接口
 *
 * @author 汪俊杰
 * @date 2020-11-26
 */
public interface AttachmentMapper extends BaseMapper<Attachment> {
    /**
     * 批量新增
     *
     * @param attachmentList 附件
     * @return
     */
    int batchInsertAttachment(@Param("list") List<Attachment> attachmentList);
}
