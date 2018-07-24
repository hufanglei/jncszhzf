package com.java.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.java.core.util.CodeUtil;

public class CodeTranseTag extends TagSupport {

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
			String codeValue = CodeUtil.getCodeName(codeName, codeId);
			if(codeValue==null){
				codeValue="";
			}
			JspWriter out=pageContext.getOut();
			out.print(codeValue);
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

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

}
