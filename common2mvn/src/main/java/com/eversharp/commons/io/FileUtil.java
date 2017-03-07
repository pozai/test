/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

/**
 * description:文件操作常用类
 * 
 * @author huaye
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 2012-7-10
 */
public class FileUtil {

	/**
	 * 打开文件输入流
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static FileInputStream openInputStream(File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) { throw new IOException("File '" + file + "' exists but is a directory"); }
			if (!file.canRead()) throw new IOException("File '" + file + "' cannot be read");
		} else {
			throw new FileNotFoundException("File '" + file + "' does not exist");
		}
		return new FileInputStream(file);
	}

	/**
	 * 打开文件输出流
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static FileOutputStream openOutputStream(File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) { throw new IOException("File '" + file + "' exists but is a directory"); }
			if (!file.canWrite()) throw new IOException("File '" + file + "' cannot be written to");
		} else {
			File parent = file.getParentFile();
			if ((parent != null) && (!parent.exists()) && (!parent.mkdirs())) { throw new IOException("File '" + file + "' could not be created"); }
		}

		return new FileOutputStream(file);
	}

	/**
	 * 循环目录下的文件
	 * 
	 * @param directory
	 * @return
	 */
	public static Collection<File> listFiles(File directory, FilenameFilter filter) {
		Collection<File> files = new LinkedList<File>();

		innerListFiles(files, directory, filter);

		return files;
	}

	private static void innerListFiles(Collection<File> files, File directory, FilenameFilter filter) {

		if (directory.isDirectory()) {
			File[] found = directory.listFiles(filter);
			if (found != null) {
				for (int i = 0; i < found.length; i++) {
					if (found[i].isDirectory()) {
						innerListFiles(files, found[i], filter);
					} else {
						files.add(found[i]);
					}
				}
			}
		}
	}

	/**
	 * 返回文件类型
	 * 
	 * @param fileType
	 * @return
	 */
	public static String getFileType(String fileType) {
		if (fileType.length() <= 0) { return ""; }
		if ("image/pjpeg".equals(fileType)) { return "jpg"; }
		if ("image/gif".equals(fileType)) { return "gif"; }
		if ("image/bmp".equals(fileType)) { return "bmp"; }
		if ("application/x-shockwave-flash".equals(fileType)) { return "swf"; }
		return "";
	}
	
}
