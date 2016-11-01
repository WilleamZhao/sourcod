package com.sourcod.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 * 登陆12306工具类
 * 
 * @author zcj
 * @time 2016-10-20 15:34:41
 *
 */
public class Tool12306Util {

	private static final Logger logger = Logger.getLogger(Tool12306Util.class);
	
	/**
	 * 
	 * 设置请求头方法
	 * 
	 * @author zcj
	 * @param hmb GetMethod or PostMethod
	 * @time 2016-10-13 09:51:18
	 * 
	 */
	static void setHeader(HttpMethodBase hmb) {
		hmb.addRequestHeader("Cache-Control", "no-cache");
		hmb.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		hmb.addRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
		hmb.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
		hmb.addRequestHeader("Cache-Control", "max-age=0");
		hmb.addRequestHeader("Connection", "Keep-Alive");
		hmb.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		hmb.addRequestHeader("Host", "kyfw.12306.cn");
		hmb.addRequestHeader("Upgrade-Insecure-Requests", "1");
		hmb.addRequestHeader("Referer", "https://kyfw.12306.cn/otn/login/init");
		hmb.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
	}

	/**
	 * 获取12306 Cookie
	 * 
	 * @author zcj
	 * @time 2016-10-12 16:22:39
	 * 
	 * @return		cookie
	 */
	public static String getCookie(GetMethod get) {
		logger.info("开始获取cookie");
		try {
			String Cookie = "";
			Header h = get.getResponseHeader("Set-Cookie");
			if(StringUtils.isEmpty(h.getValue())){
				logger.info("12306没有返回Set-Cookie信息");
				return "";
			}
			Pattern pattern = Pattern.compile(".*?;");
			Matcher matcher = pattern.matcher(h.getValue());
			boolean jSessionIdIsok = false;
			boolean bIGipServerotnIsok = false;
			while (matcher.find()) {
				String name = matcher.group();
				if (!jSessionIdIsok && name.indexOf("JSESSIONID") != -1) {
					Cookie += "JSESSIONID=" + name.substring(name.indexOf("JSESSIONID") + 11, name.length() - 1) + "; ";
					jSessionIdIsok = true;
				}
				if (!bIGipServerotnIsok && matcher.group().indexOf("BIGipServerotn") != -1) {
					Cookie += "BIGipServerotn=" + name.substring( name.indexOf("BIGipServerotn") + 15, name.length() - 1) + "; ";
					bIGipServerotnIsok = true;
				}
				if(jSessionIdIsok && bIGipServerotnIsok){
					break;
				}
			}
			if (StringUtils.isNotEmpty(Cookie)) {
				Cookie += "current_captcha_type=Z; ";
			}
			logger.info("获取的cookie:" + Cookie);
			return Cookie;
		} catch (Exception e) {
			logger.error("获取12306Cookie异常", e);
			return "";
		}
		
	}

	/**
	 * get请求
	 * 
	 * @author zcj
	 * @Time 2016-10-13 09:44:16
	 * 
	 * @param url 		请求url
	 * @param Cookie 	cookie
	 * 
	 * @return			GetMethod对象
	 */
	public static GetMethod getByHttps(String url, String Cookie) {
		logger.info("开始get请求,请求地址：" + url);
		logger.info("请求cookie信息：" + Cookie);
		HttpClient httpclient = new HttpClient();
		Protocol https = new Protocol("https", new HTTPSSecureProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", https);
		GetMethod get = new GetMethod(url);
		// 设置请求头
		setHeader(get);
		// 设置cookie信息
		get.addRequestHeader("Cookie", Cookie);
		int i = 0;
		do{
			try {
				httpclient.executeMethod(get);
				Protocol.unregisterProtocol("https");
				return get;
			} catch (HttpException e) {
				i++;
				logger.debug("get请求异常", e);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					logger.error("重新访问" + url + "异常", e1);
					return null;
				}
			} catch (IOException e) {
				i++;
				logger.debug("get请求异常", e);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					logger.error("重新访问" + url + "异常", e1);
					return null;
				}
			}
		}while(i < 3);
		// 3次请求失败返回null
		return null;
	}

	/**
	 * 
	 * post请求
	 * 
	 * @author zcj
	 * @time 2016-10-13 19:14:34
	 * @param url 		请求url
	 * @param param 	参数
	 * @param Cookie 	cookie
	 * @return			PostMethod对象
	 * @throws IOException 
	 * @throws HttpException 
	 * 
	 */
	public static PostMethod getPostByHttpClient(String url, NameValuePair[] param, String Cookie){
		logger.info("开始post请求，请求地址：" + url);
		HttpClient httpclient = new HttpClient();
		Protocol https = new Protocol("https", new HTTPSSecureProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", https);
		PostMethod post = new PostMethod(url);
		// 设置请求头信息
		setHeader(post);
		logger.info("请求cookie信息：" + Cookie);
		post.addRequestHeader("Cookie", Cookie);
		post.setRequestBody(param);
		int i = 0;
		do{
			try {
				httpclient.executeMethod(post);
				Protocol.unregisterProtocol("https");
				return post;
			} catch (HttpException e) {
				i++;
				logger.error("post请求异常", e);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					logger.error("重新访问" + url + "异常", e1);
					return null;
				}
			} catch (IOException e) {
				i++;
				logger.error("post请求异常", e);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					logger.error("重新访问" + url + "异常", e1);
					return null;
				}
			}
		}while(i < 3);
		// 3次请求失败返回null
		return null;
	}
	
	/**
	 * 
	 * post请求
	 * 
	 * @author zcj
	 * @time 2016-10-13 19:14:34
	 * @param url 		请求url
	 * @param param 	参数
	 * @param Cookie 	cookie
	 * @return			PostMethod对象
	 * @throws IOException 
	 * @throws HttpException 
	 * 
	 */
	public static PostMethod getPostByHttpClient1(String url, NameValuePair[] param, String Cookie){
		logger.info("开始post请求，请求地址：" + url);
		HttpClient httpclient = new HttpClient();
		Protocol https = new Protocol("https", new HTTPSSecureProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", https);
		PostMethod post = new PostMethod(url);
		// 设置请求头信息
		post.addRequestHeader("Cache-Control", "no-cache");
		post.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		post.addRequestHeader("Accept-Encoding", "gzip, deflate");
		post.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
		post.addRequestHeader("Cache-Control", "max-age=0");
		post.addRequestHeader("Connection", "keep-alive");
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.addRequestHeader("Host", "mobile.12306.cn");
		post.addRequestHeader("Upgrade-Insecure-Requests", "1");
		post.addRequestHeader("Referer", "https://kyfw.12306.cn/otn/payOrder/init");
		post.addRequestHeader("User-Agent", "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_0_2 like Mac OS X; en-us) AppleWebKit/532.9 (KHTML, like Gecko) Version/4.0.5 Mobile/8A400 Safari/6531.22.7");
		logger.info("请求cookie信息：" + Cookie);
		post.addRequestHeader("Cookie", Cookie);
		post.setRequestBody(param);
		int i = 0;
		do{
			try {
				httpclient.executeMethod(post);
				Protocol.unregisterProtocol("https");
				return post;
			} catch (HttpException e) {
				i++;
				logger.error("post请求异常", e);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					logger.error("重新访问" + url + "异常", e1);
					return null;
				}
			} catch (IOException e) {
				i++;
				logger.error("post请求异常", e);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					logger.error("重新访问" + url + "异常", e1);
					return null;
				}
			}
		}while(i < 3);
		// 3次请求失败返回null
		return null;
	}

	/**
	 * 保存文件到本地，返回保存后文件路径
	 * 
	 * @author zcj
	 * @time 2016-10-13 18:14:30
	 * @param in 图片流
	 * @return 保存后文件URL
	 */
	public static String getvalidateCodeImageUrl(InputStream in) {
		logger.info("开始生成图片");

		// 1. 获取保存文件名称(当前日期加8位随机数)
		String validateCodeImageName = new Date().getTime() + "" + (int) (1 + Math.random() * 10000000) + ".jpg";

		// 2. 生成图片保存到本地
		File file = new File(PropReader.getValueByKey("user_img_service_path") + validateCodeImageName);
		byte[] bt = new byte[1024];
		FileOutputStream fop = null;
		BufferedInputStream bis = new BufferedInputStream(in);
		try {
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			fop = new FileOutputStream(file);
			// get the content in bytes
			int len = bis.read(bt);
			if (len == -1 || len == 0) {
				file.delete();
				file = null;
			}
			while (len != -1) {
				fop.write(bt, 0, len);
				len = bis.read(bt);
			}
			fop.flush();
			fop.close();
		} catch (IOException e) {
			logger.error("生成图片异常", e);
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				logger.error("关闭输出图片流异常", e);
			}
		}
		logger.info("生成图片完成,图片本地路径:" + file.getAbsolutePath());
		return validateCodeImageName;
	}

	/**
	 * 上传图片
	 * 
	 * @author zcj
	 * @time 2016-10-18 18:16:28
	 * @param validateCodeImageName
	 * @return 上传后图片地址
	 */
	public static String uploadImage(String validateCodeImageName) {
		String returnUrl = "";
		logger.info("开始上传图片");
		GetMethod getImageUrl = Tool12306Util.getByHttps(PropReader.getValueByKey("SAVEIMAGE_URL") + validateCodeImageName, "");
		try {
			String result = getImageUrl.getResponseBodyAsString();
			logger.info("上传图片返回信息:" + result);
			JSONObject json = stringToJson(result);
			// 上传图片成功
			if ("true".equals(json.get("result").toString())) {
				returnUrl = json.getJSONObject("data").getString("imgUrl");
				logger.info("上传图片成功,返回url:" + returnUrl);
			}
		} catch (IOException e) {
			logger.error("上传图片失败", e);
			return "";
		}
		return returnUrl;
	}

	/**
	 * json字符串转json对象
	 * 
	 * @author zcj
	 * @time 2016-10-20 15:30:35
	 * 
	 * @param 	json字符串
	 * @return 	json对象
	 */
	public static JSONObject stringToJson(String json) {
		JSONObject js = null;
		try {
			js = new JSONObject(json);
			logger.info("转换json成功");
			return js;
		} catch (Exception e) {
			logger.error("字符串转json异常,", e);
			return js;
		}
	}

	/**
	 * Object对象转json字符串
	 * @author zcj
	 * @time 2016-10-20 15:30:59
	 * 
	 * @param o javabean对象
	 * @return 	json字符串
	 */
	public static String objectToJson(Object o) {
		JSONObject json = new JSONObject(o);
		return json.toString();
	}

	/**
	 * 获取真实验证码坐标
	 * @author zcj
	 * @time 2016-10-20 15:31:46
	 * @param 	randCode	验证码坐标(格式:x,y;x1,y1)
	 * @return	实际验证码坐标
	 */
	public static String getRealRandCode(String randCode) {
		if (StringUtils.isEmpty(randCode)) {
			return "";
		}
		String[] randCodes = randCode.split(";");
		randCode = "";
		for (String s : randCodes) {
			// 取x点坐标
			randCode += PropReader.getValueByKey("x" + s.split(",")[0]) + ",";
			// 取y点坐标
			randCode += PropReader.getValueByKey("y" + s.split(",")[1]) + ",";
		}
		return randCode.substring(0, randCode.length() - 1);
	}
	
	
	/**
	 * 验证验证码是否正确
	 * @param url
	 * @param cookie
	 * @param param
	 * @return
	 */
	public static boolean validateCodeParam(String url, NameValuePair[] param, String cookie){
		try {
			PostMethod pm = Tool12306Util.getPostByHttpClient(url, param, cookie);
			String result = pm.getResponseBodyAsString();
			JSONObject json = Tool12306Util.stringToJson(result);
			JSONObject data = json.getJSONObject("data");
			if(!"true".equals(json.get("status").toString()) || !"TRUE".equals(data != null ? data.get("msg").toString() : "")){
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("验证码验证异常", e);
			return false;
		}
	}
	
	/**
	 * 验证用户是否登陆
	 * @author zcj
	 * @time 2016-10-21 10:34:24
	 * @param userid
	 * @param checkUserParam
	 * @param cookie
	 * @return
	 */
	public static boolean checkUser(NameValuePair[] checkUserParam, String cookie){
		String result = "";
		try {
			PostMethod pm = Tool12306Util.getPostByHttpClient(PropReader.getValueByKey("REQ_CHECK_USER_URL"), checkUserParam, cookie);
			result = pm.getResponseBodyAsString();
		} catch (IOException e) {
			logger.error("验证用户是否登陆异常", e);
			return false;
		}
		JSONObject json = Tool12306Util.stringToJson(result);
		JSONObject data = json.getJSONObject("data");
		if(!"true".equals(json.get("status").toString()) && !"true".equals(data != null ? data.getString("flag") : "")){
			return false;
		}
		return true;
	}
	
	/**
	 * 获取验证码图片高度,宽度和问题高度
	 * @param ImageUrl	图片地址
	 * @return	高度,宽度和问题高度
	 * @throws Exception
	 */
	public static String getValidateImageHeight(String ImageUrl) throws Exception {
		BufferedImage img = ImageIO.read(new FileInputStream(ImageUrl));
		// 图片高度
		long height = img.getHeight();
		// 图片宽度
		long width = img.getWidth();
		// 问题高度
		long questionHeight = 0;
		// rgb颜色
		int[] rgb = new int[3];
		int i = 0;
		y : for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				int pixel = img.getRGB(x, y);
				rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                if(rgb[0] == 207 && rgb[1] == 207 && rgb[2] == 207){
                	i++;
                	// 如果一行像素里有10个连续像素颜色为(207,207,207)代表这一行是question结束行
                	if(i > 9){
                		questionHeight = y + 1;
                		break y;
                	}
                }
			}
			i = 0;
		}
		return width + "," + height + "," + questionHeight;
	}
	
	public static boolean isok(){
		
		return true;
	}


	public static void main(String[] args) {
		String a = getRealRandCode("0,1;1,1");
		System.out.println(a);
		String validateCodeImageName = new Date().getTime() + ""
				+ (int) (Math.random() * 10000000) + ".jpg";
		System.out.println(validateCodeImageName);
	}

}
