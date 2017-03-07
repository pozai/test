/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * description:
 * 
 * @author huaye
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 2011-6-27
 */
public abstract class MD5Util {
	/**
	 * 默认的密码字符串组合，apache校验下载的文件的正确性用的就是默认的这个组合
	 */
	protected static char			hexDigits[]		= { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	protected static MessageDigest	messagedigest	= null;
	protected static String	charset	= "UTF-8";
	
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsaex) {
			System.err.println(MD5Util.class.getName() + "初始化失败，MessageDigest不支持MD5Util。");
			nsaex.printStackTrace();
		}
	}

	/**
	 * 适用于上G大的文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		try {
			FileChannel ch = in.getChannel();
			try {
				MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
				messagedigest.update(byteBuffer);
			}
			finally {
				ch.close();
			}
		}
		finally {
			in.close();
		}
		return bufferToHex(messagedigest.digest());
	}

	public static String getMD5String(String s) {
		try {
			return getMD5String(s.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static boolean checkPassword(String password, String md5PwdStr) {
		String s = getMD5String(password);
		return s.equals(md5PwdStr);
	}
	
	public static void main(String[] args) {
//	    String str = "body=11111"
//	            + "&buyer_email=clh_17173@hotmail.com"
//	            + "&buyer_id=2088002205003994"
//	            + "&exterface=create_direct_pay_by_user"
//	            + "&is_success=T"
//	            + "&notify_id=3efe1656ec32d5af8e82e3823c5d364d09"
//	            + "&notify_time=2012-10-15 07:03:04"
//	            + "&notify_type=trade_status_sync"
//	            + "&out_trade_no=1210150336270002"
//	            + "&payment_type=1"
//	            + "&seller_email=leoway@3595.com"
//	            + "&seller_id=2088701562678992"
//	            + "&subject=111"
//	            + "&total_fee=1.00"
//	            + "&trade_no=2012101579189399"
//	            + "&trade_status=TRADE_SUCCESS"
//	            + "tjhg0ru0565nf6p1vv3lsfsykusje5uw";
//	    try {
//            str = new String(str.getBytes("ISO-8859-1"), "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }3126f852328b459fe13056e3df97690f
//		cmd=checkopenid=7CF696A77C949AFB18866AE89F0CE610ts=1395346460version=V3contractid=1101233539T320140307180939step=3payitem=billno=serverId=1272103f05b6f9a7d7a61025d1bc8df04e  
//		cmd=checkopenid=52B37E00DEEE3869A2618268D7B3D3F9ts=1395346460version=V3contractid=1101233539T320140307180939step=3payitem=billno=serverId=1272103f05b6f9a7d7a61025d1bc8df04e
		System.out.println(getMD5String("146101461001zhaoyanlong1202014102021231044d9da15416977f0ddc3d293ec5e8bca"));
	}
}
