package com.java.tag;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.java.core.util.CodeUtil;

public class CodeOptionsTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codeName;
	private String codeId;

	@Override
	public int doStartTag() throws JspException {
		int flag = EVAL_BODY_INCLUDE;
		try {
			JspWriter out = pageContext.getOut();
			Map<String, String> map = CodeUtil.getCodeMap(codeName);
			if(map != null){
				if("sishiqk".equals(codeName) || "sansheqk".equals(codeName) || "jzxzryzclx".equals(codeName) ||
					"sszyzlyy".equals(codeName) || "cyglry".equals(codeName) || "bfqk".equals(codeName)
					|| "tjfs".equals(codeName) || "jyfw".equals(codeName)|| "jtqk".equals(codeName)){
					
				}else{
					out.println("<option value=''>请选择</option>");
				}
				
				for (Entry<String, String> entry : map.entrySet()) {
					out.println("<option value='"+ entry.getKey() + "'"
							+ (entry.getKey().equals(codeId) ? " selected='selected'": "")
							+ ">" + entry.getValue() + "</option>");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) throws JspException {
		this.codeId = codeId;
	}

}
