package com.java.activiti.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DocumentHandler {
	private Configuration configuration = null;

	public DocumentHandler() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
	}

	public ByteArrayOutputStream createDoc(String ftlName,Map varMap) {
		
		
		Constants.logger.info("ftlName---"+ftlName);
		Constants.logger.info("varMap---"+varMap);
		
		//设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
		//这里我们的模板是放在com.havenliu.document.template包下面
		configuration.setClassForTemplateLoading(this.getClass(), "/template");
		Template t=null;
		try {
			//test.ftl为要装载的模板
			t = configuration.getTemplate(ftlName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//输出文档路径及名称
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter oStreamWriter = null;
		try {
			oStreamWriter = new OutputStreamWriter(baos,"utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
        try {
			t.process(varMap, oStreamWriter);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		/*File outFile = new File("D:/temp/shoulidan.doc");
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		 
        try {
			t.process(varMap, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
        
        return baos;
	}
	

	

}
