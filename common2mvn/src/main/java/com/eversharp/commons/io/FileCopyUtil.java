/*
 *Copyright(C) 2011 www.advanceself.com
 *All right reserved.
 */
package com.eversharp.commons.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import com.eversharp.commons.util.Assert;

/**
 * description:文件拷贝常用类
 * 
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 2011-7-25
 */
public abstract class FileCopyUtil {

	public static final int	BUFFER_SIZE	= 4096;

	public static int copy(File in, File out) throws IOException {
		Assert.notNull(in, "input File 的实例是空的!!!");
		Assert.notNull(out, "output File 的实例是空的!!!");
		return copy(new BufferedInputStream(new FileInputStream(in)), new BufferedOutputStream(new FileOutputStream(out)));
	}

	public static void copy(byte[] in, File out) throws IOException {
		Assert.notNull(in, "input 字节数组实例是空的!!!");
		Assert.notNull(out, "output File 的实例是空的!!!");
		ByteArrayInputStream inStream = new ByteArrayInputStream(in);
		OutputStream outStream = new BufferedOutputStream(new FileOutputStream(out));
		copy(inStream, outStream);
	}

	public static byte[] copyToByteArray(File in) throws IOException {
		Assert.notNull(in, "input File 的实例是空的!!!");
		return copyToByteArray(new BufferedInputStream(new FileInputStream(in)));
	}

	public static int copy(InputStream in, OutputStream out) throws IOException {
		Assert.notNull(in, "InputStream 的实例是空的!!!");
		Assert.notNull(out, "OutputStream 的实例是空的!!!");
		try {
			int byteCount = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		}
		finally {
			try {
				in.close();
			} catch (IOException ex) {}
			try {
				out.close();
			} catch (IOException ex) {}
		}
	}

	public static void copy(byte[] in, OutputStream out) throws IOException {
		Assert.notNull(in, "in的字节数组 的实例是空的!!!");
		Assert.notNull(out, "OutputStream 的实例是空的!!!");
		try {
			out.write(in);
		}
		finally {
			try {
				out.close();
			} catch (IOException ex) {}
		}
	}

	public static byte[] copyToByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
		copy(in, out);
		return out.toByteArray();
	}

	public static int copy(Reader in, Writer out) throws IOException {
		Assert.notNull(in, "Reader 的实例是空的!!!");
		Assert.notNull(out, "Writer 的实例是空的!!!");
		try {
			int byteCount = 0;
			char[] buffer = new char[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		}
		finally {
			try {
				in.close();
			} catch (IOException ex) {}
			try {
				out.close();
			} catch (IOException ex) {}
		}
	}

	public static void copy(String in, Writer out) throws IOException {
		Assert.notNull(in, "字符串 的实例是空的!!!");
		Assert.notNull(out, "Writer 的实例是空的!!!");
		try {
			out.write(in);
		}
		finally {
			try {
				out.close();
			} catch (IOException ex) {}
		}
	}

	public static String copyToString(Reader in) throws IOException {
		StringWriter out = new StringWriter();
		copy(in, out);
		return out.toString();
	}

}
