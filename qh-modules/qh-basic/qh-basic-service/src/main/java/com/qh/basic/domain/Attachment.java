package com.qh.basic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.qh.common.core.utils.poi.ExcelUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.qh.common.core.annotation.Excel;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.qh.common.core.web.domain.BaseEntity;

/**
 * 附件对象 t_attachment
 *
 * @author 汪俊杰
 * @date 2020-11-26
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_attachment")
public class Attachment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 附件Id
     */
    @TableId(value = "att_id", type = IdType.AUTO)
    private String attId;

    /**
     * 业务Id
     */
    @Excel(name = "业务Id")
    private String bizId;

    /**
     * 关联的业务类型
     */
    @Excel(name = "关联的业务类型")
    private String bizType;

    /**
     * 源文件名
     */
    @Excel(name = "源文件名")
    private String sourceFileName;

    /**
     * 文件类型
     */
    @Excel(name = "文件类型")
    private String fileType;

    /**
     * 文件路径
     */
    @Excel(name = "文件路径")
    private String filePath;

    /**
     * 图片标志，NULL-未关联业务, Y--正常，C--注销，删除直接从物理删除，并且把文件删除
     */
    @Excel(name = "图片标志，NULL-未关联业务, Y--正常，C--注销，删除直接从物理删除，并且把文件删除")
    private String delMark;

    /**
     * 创建日期
     */
    @Excel(name = "创建日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 修改日期
     */
    @Excel(name = "修改日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;

    /**
     * 根路径
     */
    @Excel(name = "根路径")
    private String rootPath;

    /**
     * 附件上传用户acc_id
     */
    @Excel(name = "附件上传用户acc_id")
    private String accId;
}
