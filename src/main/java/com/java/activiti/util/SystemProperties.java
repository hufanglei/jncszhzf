package com.java.activiti.util;

import java.io.InputStream;
import java.util.Properties;

public class SystemProperties {

	private static final String RESOURCE_NAME = "/system.properties";
	
	static {
		loadConfFile();
	}
	
	synchronized static public void loadConfFile() {

		if (tianque_appKey == null) {
			InputStream is = SystemProperties.class.getResourceAsStream(RESOURCE_NAME);
			Properties dbProps = new Properties();
			try {
				dbProps.load(is);
				tianque_appKey=dbProps.getProperty("appKey").trim();
				tianque_userName=dbProps.getProperty("userName").trim();
				tianque_passwordmd5=dbProps.getProperty("passwordmd5").trim();
				
				tianque_sign_host=dbProps.getProperty("sign_host").trim();
				tianque_events_host=dbProps.getProperty("events_host").trim();
				
				tianque_sign_url=dbProps.getProperty("sign_url").trim();
				tianque_feedback_url=dbProps.getProperty("feedback_url").trim();
				tianque_query_url=dbProps.getProperty("query_url").trim();
				fileupload = dbProps.getProperty("fileupload");
			} catch (Exception e) {
				System.err.println("不能读取属性文件. "
						+ "请确保db.properties在CLASSPATH指定的路径中");
			}
		}
	}
	
	public static String getTianque_appKey() {
		return tianque_appKey;
	}
	public static void setTianque_appKey(String tianque_appKey) {
		SystemProperties.tianque_appKey = tianque_appKey;
	}
	public static String getTianque_userName() {
		return tianque_userName;
	}
	public static void setTianque_userName(String tianque_userName) {
		SystemProperties.tianque_userName = tianque_userName;
	}
	public static String getTianque_passwordmd5() {
		return tianque_passwordmd5;
	}
	public static void setTianque_passwordmd5(String tianque_passwordmd5) {
		SystemProperties.tianque_passwordmd5 = tianque_passwordmd5;
	}
	public static String getTianque_sign_host() {
		return tianque_sign_host;
	}
	public static void setTianque_sign_host(String tianque_sign_host) {
		SystemProperties.tianque_sign_host = tianque_sign_host;
	}
	public static String getTianque_events_host() {
		return tianque_events_host;
	}
	public static void setTianque_events_host(String tianque_events_host) {
		SystemProperties.tianque_events_host = tianque_events_host;
	}
	public static String getTianque_sign_url() {
		return tianque_sign_url;
	}
	public static void setTianque_sign_url(String tianque_sign_url) {
		SystemProperties.tianque_sign_url = tianque_sign_url;
	}
	public static String getTianque_feedback_url() {
		return tianque_feedback_url;
	}
	public static void setTianque_feedback_url(String tianque_feedback_url) {
		SystemProperties.tianque_feedback_url = tianque_feedback_url;
	}
	public static String getTianque_query_url() {
		return tianque_query_url;
	}
	public static void setTianque_query_url(String tianque_query_url) {
		SystemProperties.tianque_query_url = tianque_query_url;
	}

	public static String getFileupload() {
		return fileupload;
	}

	public static void setFileupload(String fileupload) {
		SystemProperties.fileupload = fileupload;
	}

	static private String tianque_appKey = null;
	static private String tianque_userName = null;
	static private String tianque_passwordmd5 = null;
	
	static private String tianque_sign_host = null;
	static private String tianque_events_host = null;

	static private String tianque_sign_url = null;
	static private String tianque_feedback_url = null;
	static private String tianque_query_url = null;

	static private String fileupload = "";
	
//	public static void main(String[] args) {
//		SystemProperties.loadConfFile();
//		System.out.println(SystemProperties.getTianque_appKey());
//	}
}
