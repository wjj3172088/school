package com.qh.common.core.web.domain;

public class HttpResponseResult {
	private int statusCode;
	private String responseBody;

	public static HttpResponseResult getHttpResponseResult() {
		return new HttpResponseResult();
	}
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	@Override
	public String toString() {
		return "HttpResponseBean [statusCode=" + statusCode + ", responseBody=" + responseBody + "]";
	}

}