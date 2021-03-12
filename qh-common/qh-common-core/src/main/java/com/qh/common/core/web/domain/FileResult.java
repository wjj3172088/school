package com.qh.common.core.web.domain;

/**
 * 文件上传成功后，返回的文件结果
* @版权 广州万物信息科技有限公司
* @author Loren 
* @DateTime 2017年7月6日 上午10:38:19 
* @Comment
 */
public class FileResult {

	private String sourceFileName; //源文件名称
	
	/**
	 * 包含业务烈性的相对路径，例子：student/20180626/xxx.jpg
	 */
	private String newFile; 
	
	private String url;
	

	public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public String getNewFile() {
		return newFile;
	}

	public void setNewFile(String newFile) {
		this.newFile = newFile;
	}

	@Override
	public String toString() {
		return "FileResult [sourceFileName=" + sourceFileName + ", newFile=" + newFile + "]";
	}

}
