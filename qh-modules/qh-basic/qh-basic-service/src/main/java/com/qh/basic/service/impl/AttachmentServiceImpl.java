package com.qh.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.basic.domain.Attachment;
import com.qh.basic.enums.AttachStateMarkEnum;
import com.qh.basic.mapper.AttachmentMapper;
import com.qh.basic.service.IAttachmentService;
import com.qh.common.core.config.SystemOssImagesConfig;
import com.qh.common.core.domain.AttachmentImgVo;
import com.qh.common.core.utils.JsonMapper;
import com.qh.common.core.utils.file.SysFileUtils;
import com.qh.common.core.utils.http.UUIDG;
import com.qh.common.core.utils.oss.AliOSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 附件Service业务层处理
 *
 * @author 汪俊杰
 * @date 2020-11-26
 */
@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements IAttachmentService {
    @Autowired
    private AttachmentMapper attachmentMapper;


    @Autowired
    SystemOssImagesConfig systemOssImagesConfig;
    /**
     * 批量新增
     *
     * @param list    图片列表
     * @param accId   用户id
     * @param bizId   业务id
     * @param bizType 业余类型
     */
    @Override
    public String batchInsert(List<String> list, String accId, String bizId, String bizType) {
        //先清理掉之前的附件
        Map<String, Object> deleteMap = new HashMap<>(1);
        deleteMap.put("biz_id", bizId);
        attachmentMapper.deleteByMap(deleteMap);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        //批量新增附件
        List<Attachment> attachmentList = new ArrayList<>();
        List<AttachmentImgVo> attachmentCommonVoList = new ArrayList<>();
        for (String fileName : list) {
            fileName = fileName.replace(systemOssImagesConfig.getBaseImgUrl(), "");
            String prefix = fileName.substring(0, fileName.lastIndexOf("."));
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

            Attachment att = new Attachment();
            att.setAttId(UUIDG.generate());
            att.setBizId(bizId);
            att.setBizType(bizType);
            att.setDelMark(AttachStateMarkEnum.NORMAL.getCode());
            att.setFilePath(prefix);
            att.setRootPath(systemOssImagesConfig.getBucketName());
            att.setCreateDate(new Date());
            att.setAccId(accId);
            att.setFileType(suffix);
            attachmentList.add(att);

            AttachmentImgVo attachmentImgVo = new AttachmentImgVo();
            attachmentImgVo.setAttId(att.getAttId());
            attachmentImgVo.setBucket(att.getRootPath());
            attachmentImgVo.setFilename(fileName);
            attachmentImgVo.setFormat(suffix);
            attachmentCommonVoList.add(attachmentImgVo);
        }
        attachmentMapper.batchInsertAttachment(attachmentList);

        return JsonMapper.defaultMapper().toJson(attachmentCommonVoList);
    }
}