package com.yahacode.yagami.base;

import java.text.MessageFormat;

import com.yahacode.yagami.base.common.PropertiesUtils;

/**
 * 框架业务异常
 * 
 * @copyright THINKEQUIP
 * @author zengyongli
 * @date 2017年3月18日
 */
public class BizfwServiceException extends Exception {

	private static final long serialVersionUID = -3233376165570816079L;

	private String errorCode;

	private String errorMsg;

	public BizfwServiceException(String errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
		this.errorMsg = PropertiesUtils.getErrorMsg(errorCode);
	}

	public BizfwServiceException(String errorCode, Object... param) {
		super(errorCode);
		this.errorCode = errorCode;
		String msg = PropertiesUtils.getErrorMsg(errorCode);
		this.errorMsg = MessageFormat.format(msg, param);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
