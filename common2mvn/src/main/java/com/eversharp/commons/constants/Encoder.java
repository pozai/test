package com.eversharp.commons.constants;

/**
 * description:用来定义编码值
 * 
 * @author huaye
 * @version 1.0
 * @date 2012-7-10
 */
public enum Encoder{
	
	/** UTF-8编码 **/
	UTF8("UTF-8"),
	
	/** GBK编码 **/
	GBK("GBK"),
	
	/** GB2312编码 **/
	GB2312("GB2312");
	
	private final String value;
	
	Encoder(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
}