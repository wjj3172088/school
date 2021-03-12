package com.qh.system.utils;

import com.alibaba.fastjson.JSON;
import com.qh.common.core.utils.Bases;
import com.qh.common.core.web.domain.HttpResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Map.Entry;

@Slf4j
public class RequestHelperUtil {
	// utf-8字符编码
	public static final String CHARSET_UTF_8 = "utf-8";
	// HTTP内容类型
	public static final String CONTENT_TYPE_TEXT_HTML = "text/xml";
	// HTTP内容类型。相当于form表单的形式，提交数据
	public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";
	// HTTP内容类型。相当于form表单的形式，提交数据
	public static final String CONTENT_TYPE_JSON_URL = "application/json;charset=utf-8";
	// 连接管理器
	private static PoolingHttpClientConnectionManager pool;
	// 请求配置
	private static RequestConfig requestConfig;
	static {
		// System.out.println("初始化HttpClientTest~~~开始");
		SSLConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
		// 配置同时支持 HTTP 和 HTPPS
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
		// 初始化连接管理器
		pool = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		// 将最大连接数增加到200，实际项目最好从配置文件中读取这个值
		pool.setMaxTotal(200);
		// 设置最大路由
		pool.setDefaultMaxPerRoute(20);
		// 根据默认超时限制初始化requestConfig
		int socketTimeout = 10000;
		int connectTimeout = 10000;
		int connectionRequestTimeout = 10000;
		requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout).setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
		// 设置请求超时时间
		// requestConfig =
		// RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000).setConnectionRequestTimeout(50000).build();
	}
	private static CloseableHttpClient httpClient = null;

	public static CloseableHttpClient getHttpClient() {
		if (httpClient == null) {
			httpClient = HttpClients.custom()
					// 设置请求配置
					.setDefaultRequestConfig(requestConfig)
					// 设置重试次数
					.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
					// 设置连接池管理
					.setConnectionManager(pool).build();
		}
		return httpClient;
	}

	public static HttpResponseResult requestPost(HttpPost httpPost) {
		CloseableHttpResponse response = null;
		try {
			response = getHttpClient().execute(httpPost);
//			if (//log.getLoggerParent().isDebugEnabled())
//				//log.debug("response=" + response);
			// 得到响应实例
			HttpEntity entity = response.getEntity();
//			if (//log.getLoggerParent().isDebugEnabled())
//				//log.debug("httpEntity=" + entity);
			return setResponse(response);
		} catch (Exception e) {
//			//log.error("requestPost",e);
		} finally {
			try {
				// 释放资源
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
//				//log.error(e);
			}
		}
		return null;
	}

	/**
	 * 发送Post请求
	 * 
	 * @param httpPost
	 * @return
	 */
	private static String sendHttpPost(HttpPost httpPost) {
		HttpResponseResult response = requestPost(httpPost);
		if (response == null)
			return null;
		return response.getResponseBody();
	}

	/**
	 * 
	 * @Title:			发送JSON类型的请求  application/json
	 * @Description: TODO
	 * @param httpUrl
	 * @param jsonData
	 * @return  
	 * @return String
	 * @throws
	 */
	private static String sendHttpPostJson(String httpUrl, String jsonData) {
		HttpPost httpPost = new HttpPost(httpUrl);
		//log.info("httpPost=" + httpPost);
		try {
			if (jsonData != null && jsonData.trim().length() > 0) {
//				if (//log.getLoggerParent().isDebugEnabled())
					//log.debug("jsonData=" + jsonData);
				StringEntity stringEntity = new StringEntity(jsonData, "UTF-8");
//				if (//log.getLoggerParent().isDebugEnabled())
//					//log.debug("stringEntity=" + stringEntity);
				stringEntity.setContentType("application/json;charset=utf-8");
//				if (//log.getLoggerParent().isDebugEnabled())
//					//log.debug("contentType=");
				httpPost.setEntity(stringEntity);
			}
		} catch (Exception e) {
//			//log.error(e);
		}
		return sendHttpPost(httpPost);
	}
	
	public static HttpResponseResult sendHttpPostJson(String httpUrl, String jsonData,Map<String,String> headers) {
		HttpPost httpPost = new HttpPost(httpUrl);
//		//log.info("httpPost=" + httpPost);
		try {
			if (jsonData != null && jsonData.trim().length() > 0) {
//				if (//log.getLoggerParent().isDebugEnabled())
//					//log.debug("jsonData=" + jsonData);
				StringEntity stringEntity = new StringEntity(jsonData, "UTF-8");
//				if (//log.getLoggerParent().isDebugEnabled())
//					//log.debug("stringEntity=" + stringEntity);
				stringEntity.setContentType("application/json;charset=utf-8");
//				if (//log.getLoggerParent().isDebugEnabled())
//					//log.debug("contentType=");
				httpPost.setEntity(stringEntity);				
			}
			if (headers != null && headers.size()>0) {
				for (String key : headers.keySet()) {
					httpPost.addHeader(key, headers.get(key));
				}
			}
			return requestPost(httpPost);
		} catch (Exception e) {
			//log.error(e);
		}
		return null;
	}
	
	public static String sendHttpPostFormdata(String httpUrl,Map<String, String> map) {
		HttpPost httpPost = new HttpPost(httpUrl);
//		//log.info("sendHttpPostFormdata httpPost = " + httpPost);
		try {
			httpPost.setHeader("Connection","Keep-Alive");
			httpPost.setHeader("Charset",CHARSET_UTF_8);
			httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			List<NameValuePair> params = new ArrayList<>();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
				params.add(pair);
			}
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		} catch (Exception e) {
//			//log.error("sendHttpPostFormdata",e);
		}
		return sendHttpPost(httpPost);
	}

	/**\
	 * 
	 * @Title:		发送JSON类型的请求  application/json
	 * @Description: TODO
	 * @param str
	 * @param map
	 * @param string
	 * @return  
	 * @return String
	 * @throws
	 */
	public static String sendPostWithJson(String str, Map<String, String> map, String string) {
		String jsonData = JSON.toJSONString(map);
		if ("POST".equals(string)) {
//			if (//log.getLoggerParent().isDebugEnabled()) {
//				//log.debug("true");
//				//log.debug("result=" + sendHttpPostJson(str, jsonData));
//			}
			return sendHttpPostJson(str, jsonData);
		}
		return "";
	}

	public static String sendHttpGet(String url, String method) {
		if (method.equals("GET")) {
			return sendHttpGet(url);
		} else {
			return "";
		}
	}

	public static String httpGet(String url) {
		return sendHttpGet(url);
	}

	private static HttpResponseResult setResponse(CloseableHttpResponse response) throws Exception {
		HttpResponseResult responseBean = HttpResponseResult.getHttpResponseResult();
		// 判断响应状态
		if (response.getStatusLine().getStatusCode() >= 300) {
			throw new Exception("HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());
		}
		responseBean.setStatusCode(response.getStatusLine().getStatusCode());
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			// 得到响应实例
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity, CHARSET_UTF_8);
			EntityUtils.consume(entity);
			responseBean.setResponseBody(content);
		}
		return responseBean;
	}

	public static HttpResponseResult requestGett(String url) {
		CloseableHttpResponse response = null;
		try {
			if (!Bases.IsStringNotNull(url))
				return null;
//			if (//log.getLoggerParent().isDebugEnabled())
//				//log.debug(url);
			// 执行请求
			response = getHttpClient().execute(new HttpGet(url));
			return setResponse(response);
		} catch (Exception exception) {
//			//log.error(String.format("url:%s", url), exception);
		} finally {
			try {
				if (response != null)
					response.close();
			} catch (IOException ioException) {
//				//log.error("", ioException);
			}
		}
		return null;
	}
	public static HttpResponseResult requestPost(String url, Map<String, String> param,LinkedHashMap<String, String> headers) {
		try {
//			if (//log.getLoggerParent().isDebugEnabled())
//				//log.debug("url=" + url);
			// 发送post请求
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> postData = new ArrayList<NameValuePair>();
			Iterator<Entry<String, String>> piter = param.entrySet().iterator();
			while (piter.hasNext()) {
				Entry<String, String> entry = piter.next();
//				if (//log.getLoggerParent().isDebugEnabled())
//					//log.debug("entry=" + entry);
				postData.add(new BasicNameValuePair(String.valueOf(entry.getKey()), String.valueOf(entry.getValue())));
//				if (//log.getLoggerParent().isDebugEnabled())
//					//log.debug(String.format("%s:%s", String.valueOf(entry.getKey()), String.valueOf(entry.getValue())));
			}
			if (headers != null && headers.size()>0) {
				for (String key : headers.keySet()) {
					httpPost.addHeader(key, headers.get(key));
				}
			}
			// HttpEntity
			httpPost.setEntity(new UrlEncodedFormEntity(postData, CHARSET_UTF_8));
			
			// 执行请求
			return requestPost(httpPost);
		} catch (Exception exception) {
//			//log.error("", exception);
		} 
		return null;
	}
	/**
	 * 发送get请求 @Title: sendHttpGet @Description: TODO @param: @param
	 * httpGet @param: @return @return: String @throws
	 */
	private static String sendHttpGet(String url) {
		HttpResponseResult response = requestGett(url);
		if (response == null)
			return null;
		return response.getResponseBody();
	}

	/**
	 * 发送post请求 @Title: post @Description: TODO @param: @param url
	 * 请求url @param: @param param @param: @return @return: String @throws
	 */
	public static HttpResponseResult requestPost(String url, Map<String, String> param) {
		return requestPost(url,param,null);
	}

	public static String post(String url, Map<String, String> param) {
		HttpResponseResult response = requestPost(url, param);
		if (response == null)
			return null;
		return response.getResponseBody();
	}

	public static String postFileLoad(String url, Map<String, String> param) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		// 响应内容
		String responseContent = null;
		try {
//			//log.info("url=" + url);
			// 发送post请求
			HttpPost httpPost = new HttpPost(url);
//			//log.info("httpPost=" + httpPost);
			// 创建默认的httpClient实例.
			httpClient = getHttpClient();
//			//log.info("httpClient=" + httpClient);
			// 配置请求信息
			httpPost.setConfig(requestConfig);
			// 设置请求头
			LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
			headers.put("Host", "");
			headers.put("Accept", "*/*");
			headers.put("Content-Type", "multipart/form-data");
			headers.put("Content-Length", "");
			if (headers != null) {
				for (String key : headers.keySet()) {
					httpPost.setHeader(key, headers.get(key));
				}
			}

			// 设置请求参数
			List<NameValuePair> postData = new ArrayList<NameValuePair>();
			Iterator<Entry<String, String>> piter = param.entrySet().iterator();
//			//log.info("piter=" + piter);
			while (piter.hasNext()) {
				Entry<String, String> entry = piter.next();
				//log.info("entry=" + entry);
				postData.add(new BasicNameValuePair(String.valueOf(entry.getKey()), String.valueOf(entry.getValue())));
				//log.debug(String.format("%s:%s", String.valueOf(entry.getKey()), String.valueOf(entry.getValue())));
			}
			// HttpEntity
			StringEntity entity1 = new UrlEncodedFormEntity(postData, CHARSET_UTF_8);
			httpPost.setEntity(entity1);
			// 执行请求
			response = httpClient.execute(httpPost);
			//log.info("response=" + response);
			// 得到响应实例
			HttpEntity entity = response.getEntity();
			//log.info("entity=" + entity);
			// 请求发送成功并得到响应
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				entity.getContent();
				String line = "";
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), CHARSET_UTF_8));
				if ((line = bufferedReader.readLine()) != null) {
					responseContent += line;
				}
				// responseContent = EntityUtils.toString(entity1,
				// CHARSET_UTF_8);
				// EntityUtils.consume(entity1);
			} else {
				throw new Exception("HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());
			}
		} catch (Exception exception) {
			//log.error("", exception);
		} finally {
			try {
				// 释放资源
				if (response != null) {
					response.close();
				}
			} catch (IOException ioException) {
				//log.error("", ioException);
			}
		}
		return responseContent;
	}

}
