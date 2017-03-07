package com.eversharp.commons.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * description:使用ImageMagick工具进行图片处理,使用的时候注意linux,windows 的CONVERT_PROG,COMPOSITE_PROG 的路径
 * 
 * @author huaye
 * @version 1.0
 * @date 2012-9－18
 */
public class ImageUtils {
	private static final String CONVERT_PROG = "convert"; //linux
	private static final String COMPOSITE_PROG = "composite"; //linux
//	private static final String CONVERT_PROG = "D:/Program Files/ImageMagick-6.7.7-Q16/im_convert.exe"; //windows
//	private static final String COMPOSITE_PROG = "D:/Program Files/ImageMagick-6.7.7-Q16/composite.exe"; //windows
	
	private static boolean exec(String[] commands) {
		Process proc = null;
		try {
			//System.out.println("Trying to execute command " + Arrays.asList(commands));
			proc = Runtime.getRuntime().exec(commands);
		} catch (IOException e) {
			System.out.println("IOException while trying to execute " + commands);
			return false;
		}
		
		int exitStatus = 0;
		
		while (true) {
			try {
				exitStatus = proc.waitFor();
				break;
			} catch (java.lang.InterruptedException e) {
				System.out.println("Interrupted: Ignoring and waiting");
			}
		}
		if (exitStatus != 0) {
			System.out.println("Error executing command: " + exitStatus);
		}
		return (exitStatus == 0);
	}
	
	public static void mkdirs(String path) {
		File dir = new File(path);
		if (dir != null && !dir.exists()) {
			dir.mkdirs();
		}
	}
	
	// 转换文件，仅使用转换质量参数
	public static boolean convert(File src, File dest, int quality) {
		//System.out.println("convert: " + src.getPath()+ ", " + dest.getPath() + ", " + quality);
		
		if (quality < 0 || quality > 100) {
			quality = 85;
		}
		
		File destDir = dest.getParentFile();
		if (destDir != null && !destDir.exists()) {
			destDir.mkdirs();
		}
		
		String[] cmds = new String[] {
				CONVERT_PROG,
				"-strip",
				"-quality",
				"" + quality,
				src.getAbsolutePath(),
				dest.getAbsolutePath()
		};
		return exec(cmds);
	}
	
	// 转换文件，使用参数：目标文件尺寸和转换质量
	public static boolean convert(File src, File dest, int width, int height, int quality) {
		//System.out.println("convert: " + src.getPath()+ ", " + dest.getPath() + ", " + width + ", " + height + ", " + quality);
		
		if (quality < 0 || quality > 100) {
			quality = 85;
		}
		
		File destDir = dest.getParentFile();
		if (destDir != null && !destDir.exists()) {
			destDir.mkdirs();
		}
		
		String[] cmds = new String[] {
			CONVERT_PROG,
			"-geometry",
			width + "x" + height,
			"-quality",
			"" + quality,
			src.getAbsolutePath(),
			dest.getAbsolutePath()
		};
		
		return exec(cmds);
	}

	// 将srcFiles数组中从下标start开始，总数为len的图片拼接成rows行cols列，每部分大小均为width*height的大图片，并保存为destFile，转换质量由quality指定
	public static void concat(
		File[] srcFiles, int start, int len,
		File destFile, int rows, int cols, int width, int height, int quality
	) {
		int totalWidth = width * cols;
		int totalHeight = height * rows;
		int end = start + len;
		
		File tmpFile = new File("tmp.png");
		
		// prepare graph
		BufferedImage image = new BufferedImage(totalWidth, totalHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graph = (Graphics2D) image.getGraphics();
		
		// draw scaled piece to the graph
		try {
			for (int i=0; i<rows; i++) {
				for (int j=0; j<cols; j++) {
					int k = start + cols * i + j;
					if (k >= end) continue;
					convert(srcFiles[k], tmpFile, width, height, 90);
					graph.drawImage(ImageIO.read(tmpFile), width * j, height * i, width, height, null);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// release resource
		graph.dispose();
		
		// save and convert
		try {
			ImageIO.write(image, "png", tmpFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		convert(tmpFile, destFile, quality);
		
		// cleanup
		tmpFile.delete();
	}
	
	/**
	 * 添加图片水印，指定添加水印的logo图片文件，并设定水印的位置和透明度
	 * wmark_image：水印logo图片
	 * src：原图片文件
	 * dest：最总生成的图片文件
	 * x,y：水印图片在原图片文件的位置
	 * Alpha：水印图片透明度
	 */
	public static boolean waterMarkWithImg(File wmark_image, File src, File dest, int x, int y, int Alpha) {
		if (Alpha < 0) {
			Alpha = 0;
		}
		
		File destDir = dest.getParentFile();
		if (destDir != null && !destDir.exists()) {
			destDir.mkdirs();
		}
		
		String[] cmds = new String[] {
			COMPOSITE_PROG,
			"-dissolve",
			"" + Alpha,
			"-gravity",
			"+" + x + "+" + y,
			wmark_image.getAbsolutePath(),
			src.getAbsolutePath(),
			dest.getAbsolutePath()
		};
		
		return exec(cmds);
	}
	
	/**
	 * 添加图片水印，指定添加水印的logo图片文件，并设定水印的位置和透明度
	 * wmark_image：水印logo图片
	 * src：原图片文件
	 * dest：最总生成的图片文件
	 * gravity：水印图片在原图片文件的位置，用方位表示,如west、northeast...
	 * Alpha：水印图片透明度
	 */
	public static boolean waterMarkWithImg(File wmark_image, File src, File dest, String gravity, int Alpha) {
		if (Alpha < 0) {
			Alpha = 0;
		}
		
		File destDir = dest.getParentFile();
		if (destDir != null && !destDir.exists()) {
			destDir.mkdirs();
		}
		
		String[] cmds = new String[] {
			COMPOSITE_PROG,
			"-dissolve",
			"" + Alpha,
			"-gravity",
			gravity,
			wmark_image.getAbsolutePath(),
			src.getAbsolutePath(),
			dest.getAbsolutePath()
		};
		
//		String cmd = "";
//		for(int i = 0; i < cmds.length; i++){
//			cmd += cmds[i] + " ";
//		}
//		System.out.println("cmd:" + cmd);
		return exec(cmds);
	}
	
	/**
	 * 添加普通文字水印，使用该方法不能处理带有换行标识的字符串，也不能指定文字水印的宽度，若需要使用这些功能可以使用其他的文字水印处理方法
	 * src：原图片文件
	 * dest：处理后生成的目标图片文件
	 * wmark_text：文字内容
	 * wmark_text_font：字体
	 * wmark_text_color：文字颜色，其值的形式有"#RGBA" "#RRGGBBAA"等，"A"为透明度值
	 * wmark_text_pointsize：文字粗细
	 * x,y:文字相对于原图片左上角的x,y位置
	 */
	public static boolean waterMarkWithText(File src, File dest, String wmark_text, String wmark_text_font,
			String wmark_text_color,int wmark_text_pointsize, int x, int y) {
		File destDir = dest.getParentFile();
		if (destDir != null && !destDir.exists()) {
			destDir.mkdirs();
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append('"');
		sb.append("fill ");
		sb.append(wmark_text_color + " text ");
		sb.append(x + "," + y);
		sb.append(" '" + wmark_text + "'");
		sb.append('"');
		
		String[] cmds = new String[] {
			CONVERT_PROG,
			"" + src.getAbsolutePath(),
			"-font",
			wmark_text_font,
			"-pointsize",
			"" + wmark_text_pointsize,
			"-gravity",
			"northwest",
			"-draw",
			sb.toString(),
			dest.getAbsolutePath()
		};
		sb = null;
		return exec(cmds);
	}
	
	/**
	 * 添加文字图片水印，使用时指定添加水印的文字内容，并设定水印的位置等参数
	 * 同名参数如waterMarkWithText()方法说明部分所述
	 * borderColor：字体边框颜色
	 * borderWidth：字体边框宽度，为0时表示不使用边框
	 * width：文字图片的像素宽度，为0时表示文体图片的宽度由文字长度决定
	 * rotate：顺时针旋转度
	 * textHasChinese：表示文字是否含有中文，true-含有 false-不含有， 当含有汉字时汉字统一在一个utf8编码格式的文件中编写水印文字
	 * "@res/chinese_words.utf8"表示res目录下的文本文件，后缀为utf8
	 */
	public static boolean waterMarkWithTextImg(File wmkImgTempFile, File src, File dest, 
			String wmark_text, String wmark_text_font, String wmark_text_color,int wmark_text_pointsize,  
			String borderColor, int borderWidth, 
			String gravity, int width, int rotate, int Alpha, boolean textHasChinese) {	
		Boolean flag = false;
		String tempImgFileName = wmkImgTempFile.getAbsolutePath();
		
		String[] createTextImgCmds = null;
		//wmark_text = textHasChinese ? "@res/chinese_words.utf8" : wmark_text;
		wmark_text = textHasChinese ? "@" + wmark_text : wmark_text;
		//System.out.println("wmark_text:" + wmark_text);
		if(0 == width){
			if(0 == borderWidth){
				createTextImgCmds = new String[] {
						CONVERT_PROG,
						"-background",
						"none",
						"-fill",
						wmark_text_color,
						"-font",
						wmark_text_font,
						"-pointsize",
						"" + wmark_text_pointsize,
						"-gravity",
						"west",
						"-rotate",
						"" + rotate,
						"label:" + wmark_text,
						tempImgFileName
					};
			} else {
				createTextImgCmds = new String[] {
						CONVERT_PROG,
						"-background",
						"none",
						"-fill",
						wmark_text_color,
						"-font",
						wmark_text_font,
						"-pointsize",
						"" + wmark_text_pointsize,
						"-stroke",
						borderColor,
						"-strokewidth",
						"" + borderWidth,
						"-gravity",
						"west",
						"-rotate",
						"" + rotate,
						"label:" + wmark_text,
						tempImgFileName
					};
			}
		} else {
			if(0 == borderWidth){
				createTextImgCmds = new String[] {
						CONVERT_PROG,
						"-background",
						"none",
						"-fill",
						wmark_text_color,
						"-font",
						wmark_text_font,
						"-pointsize",
						"" + wmark_text_pointsize,
						"-size",
						width + "x",
						"-gravity",
						"west",
						"-rotate",
						"" + rotate,
						"caption:" + wmark_text,
						tempImgFileName
					};
			} else {
				createTextImgCmds = new String[] {
						CONVERT_PROG,
						"-background",
						"none",
						"-fill",
						wmark_text_color,
						"-font",
						wmark_text_font,
						"-pointsize",
						"" + wmark_text_pointsize,
						"-size",
						width + "x",
						"-stroke",
						borderColor,
						"-strokewidth",
						"" + borderWidth,
						"-gravity",
						"west",
						"-rotate",
						"" + rotate,
						"caption:" + wmark_text,
						tempImgFileName
					};
			}
		}
		
		flag = exec(createTextImgCmds);
		if(flag && wmkImgTempFile.exists()){
			//waterMarkWithImg(wmkImgTempFile, src, dest, x, y, Alpha);
			waterMarkWithImg(wmkImgTempFile, src, dest, gravity, Alpha);
			
			//若不需要保存处理过程中产生的文字图片文件则可使用下面的语句删除临时文件
			//wmkImgTempFile.delete();
		}
		return flag;
	}
	
	/**
	 * 根据指定宽高缩放图片、可只按高或宽比例来缩放(如只指定宽则高为0即可)
	 * width：缩放的宽度
	 * height：缩放的高度
	 * 
	 */
	public static boolean resizeImage(File src, File dest, int width, int height) {
		File destDir = dest.getParentFile();
		if (destDir != null && !destDir.exists()) {
			destDir.mkdirs();
		}
		String size = "";
		if(0 < width && 0< height ){
			size = width + "x" + height + "!" ;
		}else if( 0 < width ){
			size = String.valueOf(width) ;
		}else if( 0< height ){
			size = String.valueOf(height) ;
		}
		
		String[] cmds = new String[] {
			CONVERT_PROG,
			src.getAbsolutePath(),
			"-resize",
			size,
			dest.getAbsolutePath()
		};
		return exec(cmds);
	}
	 
	/**
	 * 等比缩放图片
	 * ratio：缩放的比率
	 */
	public static boolean resizeImage(File src, File dest, int ratio) {
		File destDir = dest.getParentFile();
		if (destDir != null && !destDir.exists()) {
			destDir.mkdirs();
		}
		String[] cmds = new String[] {
			CONVERT_PROG,
			src.getAbsolutePath(),
			"-resize",
			"" + ratio + "%",
			dest.getAbsolutePath()
		};
		return exec(cmds);
	}
	
	/**
	 * 判断后辍是否是文件
	 * @param imge
	 * @return
	 */
	public static Boolean isImage(String imge) {
		if (imge == null) {
			return false;
		} else {
			imge = imge.toLowerCase();
			// 声明图片后缀名数组
			Map<String, Boolean> map = new HashMap<String, Boolean>();
			map.put(".bmp", true);
			map.put(".dib", true);
			map.put(".gif", true);
			map.put(".jfif", true);
			map.put(".jpe", true);
			map.put(".jpeg", true);
			map.put(".jpg", true);
			map.put(".png", true);
			map.put(".tif", true);
			map.put(".tiff", true);
			map.put(".ico", true);
			return map.containsKey(imge);
		}
	}
	
	public static void main(String args[]) {
//		File wmarkImg = new File("c:/res/logo.png");
		
//		System.out.println(isImage(".bmp"));
//		File wmarkImg = new File("c:/res/logo.png");
//		File src = new File("/data/wwwroot/images.3595.com/2012/nqk/nqk.jpg");
//        File dest = new File("/data/wwwroot/images.3595.com/2012/nqk/nqk_22.jpg");	
        
		File src = new File("d:/data/wwwroot/images.3595.com/2012/www/image/games/common/a0dbsttsmx0totxv.jpg");
        File dest = new File("d:/data/wwwroot/images.3595.com/2012/www/image/games/common/a0dbsttsmx0totxv_226x106.jpg");
        //convert(src,dest,226,106,100);
        resizeImage(src,dest,226,106);
        
//        ImageIcon imgIcon = null;
//        try {
//        	imgIcon = new ImageIcon(src.getAbsolutePath());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		int width = imgIcon.getIconWidth();
//		System.out.println("width" + width);
//		ImageUtils.waterMarkWithImg(wmarkImg, src, dest, "northeast", 90);   
//        ImageUtils.waterMarkWithText(src, dest, "中文!", "SIMYOU", "#0f05", 50, 0, 20);
//        ImageUtils.waterMarkWithTextImg(wmarkImg, src, dest, "C:\\res\\chinese_words.utf8", "黑体", "blue", 50, "green", 0, "southeast", width - 20, 0, 40, true);
        
//          ImageUtils.resizeImage(src, dest, 500, 50); 
//          ImageUtils.resizeImage(src, dest, 5); 
           
//        String[] strArr = new String[]{
//    			CONVERT_PROG,
//    			src.getAbsolutePath(),
//    			"-resize",
//    			"" + 6000,
//    			dest.getAbsolutePath()
//        };
//        ImageUtils.exec(strArr);
	}

}
