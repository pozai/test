/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.net;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.eversharp.commons.encrypt.MD5Util;

/**
 * description:httpclient的请求
 * 
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 2012-7-10
 */
public class HttpClient {

	// -------------------------------------------------------------- Constants

	// ----------------------------------------------------- Instance Variables
	private org.apache.commons.httpclient.HttpClient	httpClient;

	// ------------------------------------------------------------ Constructor
	public HttpClient() {
		httpClient = new org.apache.commons.httpclient.HttpClient();
		HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
		// 设置连接超时时间(单位毫秒)
		managerParams.setConnectionTimeout(10000);
		// 设置读数据超时时间(单位毫秒)
		managerParams.setSoTimeout(12000);
	}

	// --------------------------------------------------------- Public Methods

	/**
	 * get请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public String httpGetRequest(String url, Map<String, String> params) {
		// 设置参数

		GetMethod getMethod = new GetMethod(url);
		try {
			if (params != null) {

				NameValuePair[] namePairs = new NameValuePair[params.size()];
				int index = 0;
				HttpMethodParams clientParams = new HttpMethodParams();

				for (Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator(); iter.hasNext();) {
					Map.Entry<String, String> entry = iter.next();
					String key = entry.getKey();
					String value = entry.getValue();
					clientParams.setParameter(key, value);

					NameValuePair namePair = new NameValuePair(key, value);
					namePairs[index] = namePair;

					index++;

				}

				getMethod.setQueryString(namePairs);
			}

			httpClient.executeMethod(getMethod);
			// 判断状态
			return new String(getMethod.getResponseBody(), Charset.forName("UTF-8"));
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
     * get请求
     * 
     * @param url
     * @param params
     * @return
     */
    public String httpGetRequest(String url) {
        GetMethod getMethod = new GetMethod(url);
        try {
            
            httpClient.executeMethod(getMethod);
            // 判断状态
            if(getMethod.getStatusCode() != 200){
                return "请求失败！";
            }else{
                return new String(getMethod.getResponseBody(), Charset.forName("UTF-8"));
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

	/**
	 * post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public String httpPostRequest(String url, Map<String, String> params) {
		PostMethod postMethod = new PostMethod(url);
		NameValuePair[] namePairs = new NameValuePair[params.size()];
		int index = 0;

		for (Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String value = entry.getValue();

			NameValuePair namePair = new NameValuePair(key, value);
			namePairs[index] = namePair;

			index++;
		}

		// set http params
		postMethod.setRequestBody(namePairs);

		try {
			httpClient.executeMethod(postMethod);
			// 判断状态
			return postMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public String httpPostRequest(String url, Map<String, String> params, String charset) {
		PostMethod postMethod = new PostMethod(url);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
		postMethod.addRequestHeader("Content-Type", "text/html;charset=" + charset);
		postMethod.setRequestHeader("Content-Type", "text/html;charset=" + charset);
		NameValuePair[] namePairs = new NameValuePair[params.size()];
		int index = 0;

		for (Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String value = entry.getValue();

			NameValuePair namePair = new NameValuePair(key, value);
			namePairs[index] = namePair;

			index++;
		}

		// set http params
		postMethod.setRequestBody(namePairs);

		try {
			httpClient.executeMethod(postMethod);
			// 判断状态
			return postMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public String httpPostRequest2(String url, Map<String, String> params, String charset) {
		PostMethod postMethod = new PostMethod(url);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
		NameValuePair[] namePairs = new NameValuePair[params.size()];
		int index = 0;

		for (Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String value = entry.getValue();

			NameValuePair namePair = new NameValuePair(key, value);
			namePairs[index] = namePair;

			index++;
		}

		// set http params
		postMethod.setRequestBody(namePairs);

		try {
			httpClient.executeMethod(postMethod);
			// 判断状态
			return postMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public BufferedImage httpGetImage(String url) {
		// 设置参数

		GetMethod getMethod = new GetMethod(url);
		try {
			httpClient.executeMethod(getMethod);
			ByteArrayInputStream in = new ByteArrayInputStream(getMethod.getResponseBody());
			BufferedImage bi = ImageIO.read(in);
			return bi;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取cookie
	 * 
	 * @param urlPath
	 * @return
	 */
	public Map<String, String> getHttpCookies(String urlPath) {
		Map<String, String> cookiesMap = new HashMap<String, String>();
		Cookie[] cookies = httpClient.getState().getCookies();

		for (Cookie cookie : cookies) {
			if (cookie.getPath().equalsIgnoreCase(urlPath)) cookiesMap.put(cookie.getName().toUpperCase(), cookie.getValue());
		}

		return cookiesMap;
	}

	public org.apache.commons.httpclient.HttpClient getHttpClient() {

		return httpClient;
	}

	// ------------------------------------------------------- Protected Methods

	// --------------------------------------------------------- Private Methods

	
	public static void main(String[] args) {

		String pid = "10010";
		String key = "7A82127C6A7B4263B5A041A2DAC18C83";

		HttpClient client = new HttpClient();

		Map<String, String> params = new HashMap<String, String>();

		// ========================test1
		// params.put("c", "5");
		// params.put("serverId", "5008");
		// params.put("spid", "4104");
		// params.put("pid", pid);
		// String md5 = MD5Util.getMD5String("550084104" + key);
		// params.put("sign", md5);
		// String url = "http://www.jiepu.local/OpenAPI/RoleCount.ashx";
		// String str = client.httpPostRequest(url, params);
		// System.out.println(str);

		// ========================test2
		params.put("gameId", "5");
		params.put("serverId", "5008");
		params.put("spid", "4104");
		params.put("pid", pid);
		String md5 = MD5Util.getMD5String("550084104" + key);
		System.out.println(md5);
		params.put("sign", md5);
		String url = "http://www.jiepu.local/OpenAPI/GetPresents.ashx";
		String str = client.httpPostRequest(url, params);
		System.out.println(str);

		// ==============test3
		// params.put("id", "45277");
		// //params.put("name", URLEncoder.encode("测试礼包1","utf-8"));
		// params.put("name", "test1");
		// params.put("spid", "4104");
		// params.put("num", "3");
		// params.put("gameId", "5");
		// params.put("serverId", "5008");
		// params.put("expireDate", "365");
		// params.put("onlyone", "0");
		// params.put("pid", pid);
		// String md5Str = params.get("id") + params.get("name") + params.get("num") + params.get("spid") + params.get("gameId") + params.get("serverId") +
		// params.get("expireDate")
		// + params.get("onlyone") + key;
		// System.out.println(md5Str);
		// String md5 = MD5Util.getMD5String(md5Str);
		// params.put("sign", md5);
		// System.out.println(md5);
		// String url = "http://www.jiepu.local/OpenAPI/CreatePresents.ashx";
		// //url = URLEncoder.encode("测试礼包1","utf-8");
		// System.out.println(url);
		// String str = client.httpPostRequest(url, params);
		// System.out.println(str);

		// =====================test4
		params.put("gameId", "5");
		params.put("serverId", "5001");
		params.put("userIds", "ntc02,chickenujs");
		params.put("createTime", "2012-01-01 12:00:00");
		params.put("endTime", "2012-12-20 12:00:00");
		params.put("pid", pid);
		// String md5Str = params.get("gameId") + params.get("serverId") +
		// params.get("userIds") + params.get("createTime") +
		// params.get("endTime") + key;
		// System.out.println(md5Str);
		// String md5 = MD5Util.getMD5String(md5Str);
		// params.put("sign", md5);
		// System.out.println(md5);
		// String url = "http://www.jiepu.local/OpenAPI/RoleArrives.ashx";
		// String str = client.httpPostRequest(url, params);
		// System.out.println(str);

		// =================test5
		params.put("gameId", "5");
		params.put("serverId", "5001");
		params.put("spId", "4040");
		params.put("presentname", "测试礼包1");
		params.put("pid", pid);
		// String md5Str = params.get("gameId") + params.get("serverId") +
		// params.get("spId") + params.get("presentname") + key;
		// System.out.println(md5Str);
		// String md5 = MD5Util.getMD5String(md5Str);
		// params.put("sign", md5);
		// System.out.println(md5);
		// String url = "http://www.jiepu.local/OpenAPI/GetPresentKeys.ashx";
		// String str = client.httpPostRequest(url, params);
		// System.out.println(str);

	}
}
