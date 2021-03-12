package com.qh.common.core.web.domain;

import com.qh.common.core.enums.StatusEnum;
import com.qh.common.core.utils.file.SysFileUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 附件实体
 * <br>
 * 对应表：t_attachment
* @版权 广州万物信息科技有限公司
* @author Loren 
* @DateTime 2017年7月5日 上午9:30:43 
* @Comment
 */
public class Attachment implements Serializable {

	private static final long serialVersionUID = 1L;

	private String attId; //主键
	
	private String bizId; //对应关联的业务ID
	
	/**
	 * 关联业务类型，{@link BizType}
	 */
	private String bizType;
	
	private String sourceFileName; //源文件名称
	
	private String fileType; //文件类型
	
	private String filePath; //相对文件，例子: student/20180505/xxx.jpg
	
	private String delMark = StatusEnum.normal.value(); //删除标志
	
	
	/**
	 * 根目录,对应配置文件中file.path的配置
	 */
	private String rootPath = SysFileUtils.filePath;
	
	/**
	 * 完整文件路径，由 rootPath + filePath 组成
	 */
	private String fullFilePath;
	
	/**
     * 创建日期
     */
    private Date createDate = new Date();

    /**
     * 修改日期
     */
    private Date modifyDate = new Date();
    
	public String getFullFilePath() {
		return this.rootPath + "/" + this.filePath;
	}

	public void setFullFilePath(String fullFilePath) {
		this.fullFilePath = fullFilePath;
	}
	
	public String getAttId() {
		return attId;
	}

	public void setAttId(String attId) {
		this.attId = attId;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDelMark() {
		return delMark;
	}

	public void setDelMark(String delMark) {
		this.delMark = delMark;
	}


	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Override
	public String toString() {
		return "Attachment [attId=" + attId + ", bizId=" + bizId + ", bizType=" + bizType + ", sourceFileName="
				+ sourceFileName + ", fileType=" + fileType + ", filePath=" + filePath + ", delMark=" + delMark
				+ ", rootPath=" + rootPath + ", fullFilePath=" + fullFilePath + "]";
	}

}
