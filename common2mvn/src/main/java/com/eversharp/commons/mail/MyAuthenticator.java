/*
 *Copyright(C) 2012 www.eversharp.com
 *All right reserved.
 */
package com.eversharp.commons.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * description:用户信息验证
 * 
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 2012-7-27
 */
public class MyAuthenticator extends Authenticator {
	String	userName	= null;
	String	password	= null;

	public MyAuthenticator() {}

	public MyAuthenticator(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}
