/*
 *Copyright(C) 2012 www.eversharp.com
 *All right reserved.
 */
package com.eversharp.commons.encrypt;

import java.io.IOException;

/**
 * description:
 * 
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 2012-7-27
 */
public class Base64Util {

	// -------------------------------------------------------------- Constants

	// ----------------------------------------------------- Instance Variables

	// ------------------------------------------------------------ Constructor

	// --------------------------------------------------------- Public Methods
	/**
	 * 加密
	 * 
	 * @param filecontent
	 * @return String
	 */
	public static String encode(byte[] bstr) {
		return new sun.misc.BASE64Encoder().encode(bstr);
	}

	/**
	 * 解密
	 * 
	 * @param filecontent
	 * @return string
	 */
	public static byte[] decode(String str) {
		byte[] bt = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bt;
	}

	// ------------------------------------------------------- Protected Methods

	// --------------------------------------------------------- Private Methods
	public static void main(String[] args) {
		String s = "343434343434|343434343434343";

		System.out.println(encode(s.getBytes()));
		System.out.println(new String(decode(encode(s.getBytes()))));
	}
}
