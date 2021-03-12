package com.qh.common.core.utils.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.util.concurrent.CountDownLatch;

public class HttpClientUtils {
	
	private final static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
	
	static String encoding = "utf-8";
	
	/**
	 * 发送GET请求，URL已经包含需要传递的参数
	* @author Loren 
	* @DateTime 2017年7月8日 上午9:20:46 
	* @serverComment 
	*
	* @param url
	* @return
	 */
	public static String sendGet(String url) {
		String result = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = null;
		try {
			HttpGet httpRequst = new HttpGet(url);
			httpResponse = httpClient.execute(httpRequst);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);// 取出应答字符串
				if(StringUtils.isNotBlank(result)){
					result = result.replaceAll("\r", "");// 去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
				}
			} else
				httpRequst.abort();
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(), e);
			result = e.getMessage().toString();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			result = e.getMessage().toString();
		}finally{
			try {
				if(httpResponse != null){
					httpResponse.close();
				}
				if(httpClient != null){
					httpClient.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}
	
	/**  
	* @author mds 
	* @DateTime 2017年3月1日 下午2:24:31 
	* @serverComment Get 请求
	*
	* @param url
	* @param param
	* @return         
	*/
	public static String sendGet(String url,String param) {
		String result = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = null;
		try {
			HttpGet httpRequst = new HttpGet(url+"?"+param);
			httpResponse = httpClient.execute(httpRequst);
//			 HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);//其中HttpGet是HttpUriRequst的子类
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);// 取出应答字符串
				// 一般来说都要删除多余的字符
				if(StringUtils.isNotBlank(result)){
					result = result.replaceAll("\r", "");// 去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
				}
			} else
				httpRequst.abort();
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(), e);
			result = e.getMessage().toString();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			result = e.getMessage().toString();
		}finally{
			try {
				if(httpResponse != null){
					httpResponse.close();
				}
				if(httpClient != null){
					httpClient.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}
	
	
	/**  
	* @author mds 
	* @DateTime 2017年3月1日 下午2:24:24 
	* @serverComment Post 请求
	*
	* @param url
	* @param jsonParam
	* @param encoding
	* @return
	* @throws ParseException
	* @throws IOException         
	*/
	public static String sendPost(String url, String jsonParam){
		String body = "-1";
		// 创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		// 接收参数json列表
		StringEntity pEntity;
		CloseableHttpResponse response = null;
		HttpPost httpPost = null;
		InputStream instream = null;
		HttpEntity entity = null;
		try {
			// 创建post方式请求对象
			httpPost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom()  
			        .setConnectTimeout(5000)//设置连接超时时间
			        .setConnectionRequestTimeout(1000)//设置从connect Manager获取Connection 超时时间
			        .setSocketTimeout(5000).build(); //请求获取数据的超时时间，单位毫秒
			httpPost.setConfig(requestConfig);
			pEntity = new StringEntity(jsonParam.toString(), encoding);
			pEntity.setContentEncoding("UTF-8");
//			pEntity.setContentType("application/json");
			pEntity.setContentType("text/html");
			httpPost.setEntity(pEntity);
			// 执行请求操作，并拿到结果（同步阻塞）
			response = client.execute(httpPost);
			// 获取结果实体
			entity = response.getEntity();
			if (entity != null) {
				// 按指定编码转换结果实体为String类型
//				body = EntityUtils.toString(entity, encoding);
//				logger.debug("sendPost result "+body);
//				body = formatResult(body);
                instream = entity.getContent();  
                StringBuilder sb = new StringBuilder();  
                char[] tmp = new char[1024];  
                Reader reader = new InputStreamReader(instream,encoding);  
                int l;  
                while ((l = reader.read(tmp)) != -1) {  
                    sb.append(tmp, 0, l);  
                }  
                body = sb.toString();  
                logger.debug("sendPost result "+body);
                body = formatResult(body);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage() + url, e);
		} catch (IOException e) {
			logger.error(e.getMessage() + url, e);
		}finally{
			try {
				if(instream != null){
					instream.close();  
				}
				if(httpPost != null){
					httpPost.clone();
				}
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
				if(entity != null){
					EntityUtils.consume(entity); 
				}
			} catch (IOException e) {
				logger.error(e.getMessage() + url, e);
			} catch (CloneNotSupportedException e) {
				logger.error(e.getMessage() + url, e);
			}
		}
		// 解决中文乱码问题
		return body;
	}
	
	private static String formatResult(String result){
		String res = "-1";
		if(StringUtils.isNotBlank(result)){
			try {
				//{"msg":"success","result":"","code":0}
				JSONObject obj = JSON.parseObject(result);
				res = String.valueOf(obj.getIntValue("code"));
				if(!"0".equals(res)){
					res = "-1";
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return res;
	}
	
	/**  
	* @author mds 
	* @DateTime 2017年3月1日 下午2:24:24 
	* @serverComment Post 请求
	*
	* @param url
	* @param jsonParam
	* @param encoding
	* @return
	* @throws ParseException
	* @throws IOException         
	*/
	public static String sendPut(String url, String jsonParam){
		String body = "";
		// 创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		// 创建post方式请求对象
		 HttpPut HttpPut = new HttpPut(url);
		// 接收参数json列表
		StringEntity pEntity;
		CloseableHttpResponse response = null;
		try {
			pEntity = new StringEntity(jsonParam.toString(), encoding);
		
			pEntity.setContentEncoding("UTF-8");
			pEntity.setContentType("application/json");
			HttpPut.setEntity(pEntity);
	
			// 设置header信息
			// 指定报文头【Content-type】、【User-Agent】
			HttpPut.setHeader("Content-type", "application/x-www-form-urlencoded");
			HttpPut.setHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	
			// 执行请求操作，并拿到结果（同步阻塞）
			response = client.execute(HttpPut);
			// 获取结果实体
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// 按指定编码转换结果实体为String类型
				body = EntityUtils.toString(entity, encoding);
			}
			EntityUtils.consume(entity);
			// 释放链接
			response.close();
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}finally{
			try {
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		// 解决中文乱码问题
		return body;
	}
	
	/**  
	* @author mds 
	* @DateTime 2017年3月1日 下午2:24:24 
	* @serverComment Post 请求
	*
	* @param url
	* @param jsonParam
	* @param encoding
	* @return
	* @throws ParseException
	* @throws IOException         
	*/
	public static String sendPatch(String url, String jsonParam){
		String body = "";
		// 创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		// 创建post方式请求对象
		HttpPatch httpPatch = new HttpPatch(url);
		// 接收参数json列表
		StringEntity pEntity;
		CloseableHttpResponse response = null;
		try {
			pEntity = new StringEntity(jsonParam.toString(), encoding);
		
			pEntity.setContentEncoding("UTF-8");
			pEntity.setContentType("application/json");
			httpPatch.setEntity(pEntity);
	
			// 设置header信息
			// 指定报文头【Content-type】、【User-Agent】
			httpPatch.setHeader("Content-type", "application/x-www-form-urlencoded");
			httpPatch.setHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	
			// 执行请求操作，并拿到结果（同步阻塞）
			response = client.execute(httpPatch);
			// 获取结果实体
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// 按指定编码转换结果实体为String类型
				body = EntityUtils.toString(entity, encoding);
			}
			EntityUtils.consume(entity);
			// 释放链接
			response.close();
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}finally{
			try {
				if(response != null){
					response.close();
				}
				if(client != null){
					client.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		// 解决中文乱码问题
		return body;
	}
	
	public static String sendAsyncPost(String url, String jsonParam){
		String body = "-1";
		RequestConfig requestConfig = RequestConfig.custom()
		.setSocketTimeout(5000).setConnectTimeout(5000).build();
		CloseableHttpAsyncClient client = HttpAsyncClients.custom()
				.setDefaultRequestConfig(requestConfig).build();
		StringEntity pEntity;
		StringBuilder res = new StringBuilder();
		try {
			client.start();
			// 创建post方式请求对象
			HttpPost httpPost = new HttpPost(url);
			pEntity = new StringEntity(jsonParam.toString(), encoding);
			pEntity.setContentEncoding("UTF-8");
			pEntity.setContentType("text/html");
			httpPost.setEntity(pEntity);
			final CountDownLatch latch = new CountDownLatch(1);
			// 执行请求操作，并拿到结果（异步非阻塞）
			client.execute(httpPost, new FutureCallback<HttpResponse>() {
				// 无论完成还是失败都调用countDown()
				@Override
				public void completed(final HttpResponse response) {
					latch.countDown();
					BufferedInputStream bis = null;
					try {
						InputStream input = response.getEntity().getContent();
						bis = new BufferedInputStream(input,2*1024);
				        byte[] byteArray = new byte[1024];
				        int tmp = 0;
				        while((tmp = bis.read(byteArray)) != -1){ 
				        	res.append(new String(byteArray,0,tmp));
				        }
					} catch (UnsupportedOperationException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println(httpPost.getRequestLine() + "->"+ response.getStatusLine());
				}

				@Override
				public void failed(final Exception ex) {
					latch.countDown();
//					System.out.println(httpPost.getRequestLine() + "->" + ex);
				}

				@Override
				public void cancelled() {
					latch.countDown();
//					System.out.println(httpPost.getRequestLine()+ " cancelled");
				}
			});
		   latch.await();
		   System.out.println(res.toString());
		   body = formatResult(res.toString());
		} catch (org.apache.http.ParseException e) {
			logger.error(e.getMessage() + url, e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage() + url, e);
		}finally{
			try {
				if(client != null){
					client.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage() + url, e);
			}
		}
		// 解决中文乱码问题
		return body;
	}
	
	
	public static void main(String[] args) throws Exception{
//		String url = "http://io.post.51dpc.com/Trajectory.ashx";
//		long start = System.currentTimeMillis();
//		for (int i = 0; i < 50; i++) {
//			//sendAsyncPost
//			String ddd = HttpClientUtils.sendPost(url, "sdfsdfds");
//			System.out.println(ddd);
//		}
//		long end = System.currentTimeMillis();
//		System.out.println(end - start);
		//4621
		
		byte[] data = downloadFile("http://125.124.125.50:9000/PhotoRS/showfile?fileType=ExtTrackId&trackId=5000156025");
		FileOutputStream out = new FileOutputStream(new File("D:/test.png"));
		out.write(data);
		out.close();
	}

	/**
	 * 从url下载文件
	 * @author ljs
	 * @param url
	 * @return 
	 * @throws Exception 
	 */
	public static byte[] downloadFile(String url) throws Exception {
		byte[] result = null;
		
		CloseableHttpClient client = HttpClients.createDefault();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream(); 
		try {
			HttpGet get = new HttpGet(url);
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new RuntimeException("文件下载失败");
			} 
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();
			byte[] tmp = new byte[1024];
			int len = -1;
			while ( (len = in.read(tmp)) != -1 ) {
				buffer.write(tmp,0,len);
			}
			
			if (buffer.size() == 0) {
				throw new RuntimeException("图片为空");
			}
			result = buffer.toByteArray();
		}catch (Exception e) {
			throw e;
		} finally {
			buffer.close();
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

}
