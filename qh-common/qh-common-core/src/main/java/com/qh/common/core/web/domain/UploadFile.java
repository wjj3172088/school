package com.qh.common.core.web.domain;

public class UploadFile {
	
	private String absoluteFileName;

	private String bizTypeFileName;

	public String getAbsoluteFileName() {
		return absoluteFileName;
	}

	public void setAbsoluteFileName(String absoluteFileName) {
		this.absoluteFileName = absoluteFileName;
	}

	public String getBizTypeFileName() {
		return bizTypeFileName;
	}

	public void setBizTypeFileName(String bizTypeFileName) {
		this.bizTypeFileName = bizTypeFileName;
	}

	@Override
	public String toString() {
		return "UploadFile [absoluteFileName=" + absoluteFileName + ", bizTypeFileName=" + bizTypeFileName + "]";
	}

}