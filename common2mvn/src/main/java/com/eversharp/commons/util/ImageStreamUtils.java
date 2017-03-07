/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.PixelGrabber;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;

import net.jmge.gif.Gif89Encoder;

import com.sun.imageio.plugins.bmp.BMPImageReader;
import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.jpeg.JPEGImageReader;
import com.sun.imageio.plugins.png.PNGImageReader;

/**
 * 图片处理工具类：<br>
 * 功能：缩放图像、切割图像、图像类型转换、彩色转黑白、文字水印、图片水印等
 * 
 * @author qingpozhang
 */
public class ImageStreamUtils {

	/**
	 * 几种常见的图片格式
	 */
	public static String	IMAGE_TYPE_GIF				= "gif";	// 图形交换格式
	public static String	IMAGE_TYPE_JPG				= "jpg";	// 联合照片专家组
	public static String	IMAGE_TYPE_JPEG				= "jpeg";	// 联合照片专家组
	public static String	IMAGE_TYPE_BMP				= "bmp";	// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
	public static String	IMAGE_TYPE_PNG				= "png";	// 可移植网络图形
	public static String	IMAGE_TYPE_PSD				= "psd";	// Photoshop的专用格式Photoshop
	public static String	IMAGE_TYPE_NOT_AVAILABLE	= "na";

	/**
	 * 缩放图像（按比例缩放）
	 * 
	 * @param srcImageFile 源图像文件地址
	 * @param result 缩放后的图像地址
	 * @param scale 缩放比例
	 * @param flag 缩放选择:true 放大; false 缩小;
	 */
	public final static void scale(String srcImageFile, String result, float scale, boolean flag) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长
			if (flag) {// 放大
				width = (int) (width * scale);
				height = (int) (height * scale);
			} else {// 缩小
				width = (int) (width/scale);
				height = (int) (height/scale);
			}
			Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * 缩放图像（按高度和宽度缩放）
     * 
     * @param srcImageFile 源图像文件地址
     * @param scale 缩放比例
     * @param height 缩放后的高度
     * @param width 缩放后的宽度
     */
    public final static BufferedImage scale(BufferedImage src, int height, int width) {
        Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = tag.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        if (width == image.getWidth(null)) g.drawImage(image, 0, (height - image.getHeight(null)) / 2, image.getWidth(null), image.getHeight(null),
                Color.white, null);
        else g.drawImage(image, (width - image.getWidth(null)) / 2, 0, image.getWidth(null), image.getHeight(null), Color.white, null);
        g.dispose();
        return tag;
        
    }

	/**
	 * 缩放图像（按高度和宽度缩放）
	 * 
	 * @param srcImageFile 源图像文件地址
	 * @param result 缩放后的图像地址
	 * @param height 缩放后的高度
	 * @param width 缩放后的宽度
	 * @param bb 比例不对时是否需要补白：true为补白; false为不补白;
	 */
	public final static void scale(String srcImageFile, String result, int height, int width, boolean bb) {
		try {
			double ratio = 0.0; // 缩放比例
			File f = new File(srcImageFile);
			BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
			// 计算比例
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				if (bi.getHeight() > bi.getWidth()) {
					ratio = (new Integer(height)).doubleValue() / bi.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);
			}
			if (bb) {// 补白
				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null)) g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null),
						Color.white, null);
				else g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
				g.dispose();
				itemp = image;
			}
			ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
     * 缩放图像（按高度和宽度缩放）
     * 
     * @param srcImageFile 源图像文件地址
     * @param result 缩放后的图像地址
     * @param height 缩放后的高度
     * @param width 缩放后的宽度
     * @param bb 比例不对时是否需要补白：true为补白; false为不补白;
     */
    public final static void scale(String srcImageFile, String result, int height, int width) {
        try {
            double ratio = 0.0; // 缩放比例
            File f = new File(srcImageFile);
            BufferedImage bi = ImageIO.read(f);
            Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
            for(int i=1;i<=13;i++){
                BufferedImage tag = new BufferedImage(width, height, i);
                Graphics2D g = tag.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null)) g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null),
                        Color.white, null);
                else g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
                g.dispose();
                ImageIO.write((BufferedImage) tag, "JPEG", new File("F:/"+i+".JPEG"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	
	/**
	 * 缩放图像（按高度和宽度缩放）
	 * 
	 * @param srcImageFile 源图像文件地址
	 * @param result 缩放后的图像地址
	 * @param height 缩放后的高度
	 * @param width 缩放后的宽度
	 * @param bb 比例不对时是否需要补白：true为补白; false为不补白;
	 */
	public final static void scale(InputStream srcImageStream, File destImage, int height, int width) {
		try {
			BufferedImage bi = ImageIO.read(srcImageStream);
			Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);

			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, width, height);
			if (width == itemp.getWidth(null)) g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null),
					Color.white, null);
			else g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
			g.dispose();
			itemp = image;

			ImageIO.write((RenderedImage) itemp, "JPEG", destImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 缩放图像（按高度和宽度缩放）
	 * 
	 * @param srcImageFile 源图像文件地址
	 * @param result 缩放后的图像地址
	 * @param height 缩放后的高度
	 * @param width 缩放后的宽度
	 * @param bb 比例不对时是否需要补白：true为补白; false为不补白;
	 */
	public final static void scale(BufferedImage bi, String result, int height, int width, boolean bb) {
		try {
			double ratio = 0.0; // 缩放比例
			Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
			// 计算比例
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				if (bi.getHeight() > bi.getWidth()) {
					ratio = (new Integer(height)).doubleValue() / bi.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);
			}
			if (bb) {// 补白
				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null)) g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null),
						Color.white, null);
				else g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
				g.dispose();
				itemp = image;
			}
			ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像切割(按指定起点坐标和宽高切割)
	 * 
	 * @param srcImageFile 源图像地址
	 * @param result 切片后的图像地址
	 * @param x 目标切片起点坐标X
	 * @param y 目标切片起点坐标Y
	 * @param width 目标切片宽度
	 * @param height 目标切片高度
	 */
	public final static void cut(String srcImageFile, String result, int x, int y, int width, int height) {
		try {
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > 0 && srcHeight > 0) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				// 四个参数分别为图像起点坐标和宽高
				// 即: CropImageFilter(int x,int y,int width,int height)
				ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
				Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
				g.dispose();
				// 输出为文件
				ImageIO.write(tag, "JPEG", new File(result));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像切割（指定切片的行数和列数）
	 * 
	 * @param srcImageFile 源图像地址
	 * @param descDir 切片目标文件夹
	 * @param rows 目标切片行数。默认2，必须是范围 [1, 20] 之内
	 * @param cols 目标切片列数。默认2，必须是范围 [1, 20] 之内
	 */
	public final static void cut2(String srcImageFile, String descDir, int rows, int cols) {
		try {
			if (rows <= 0 || rows > 20) rows = 2; // 切片行数
			if (cols <= 0 || cols > 20) cols = 2; // 切片列数
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > 0 && srcHeight > 0) {
				Image img;
				ImageFilter cropFilter;
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				int destWidth = srcWidth; // 每张切片的宽度
				int destHeight = srcHeight; // 每张切片的高度
				// 计算切片的宽度和高度
				if (srcWidth % cols == 0) {
					destWidth = srcWidth / cols;
				} else {
					destWidth = (int) Math.floor(srcWidth / cols) + 1;
				}
				if (srcHeight % rows == 0) {
					destHeight = srcHeight / rows;
				} else {
					destHeight = (int) Math.floor(srcWidth / rows) + 1;
				}
				// 循环建立切片
				// 改进的想法:是否可用多线程加快切割速度
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// 四个参数分别为图像起点坐标和宽高
						// 即: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);
						img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
						BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						// 输出为文件
						ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i + "_c" + j + ".jpg"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像切割（指定切片的宽度和高度）
	 * 
	 * @param srcImageFile 源图像地址
	 * @param descDir 切片目标文件夹
	 * @param destWidth 目标切片宽度。默认200
	 * @param destHeight 目标切片高度。默认150
	 */
	public final static void cut3(String srcImageFile, String descDir, int destWidth, int destHeight) {
		try {
			if (destWidth <= 0) destWidth = 200; // 切片宽度
			if (destHeight <= 0) destHeight = 150; // 切片高度
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > destWidth && srcHeight > destHeight) {
				Image img;
				ImageFilter cropFilter;
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				int cols = 0; // 切片横向数量
				int rows = 0; // 切片纵向数量
				// 计算切片的横向和纵向数量
				if (srcWidth % destWidth == 0) {
					cols = srcWidth / destWidth;
				} else {
					cols = (int) Math.floor(srcWidth / destWidth) + 1;
				}
				if (srcHeight % destHeight == 0) {
					rows = srcHeight / destHeight;
				} else {
					rows = (int) Math.floor(srcHeight / destHeight) + 1;
				}
				// 循环建立切片
				// 改进的想法:是否可用多线程加快切割速度
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// 四个参数分别为图像起点坐标和宽高
						// 即: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);
						img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
						BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						// 输出为文件
						ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i + "_c" + j + ".jpg"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
	 * 
	 * @param srcImageFile 源图像地址
	 * @param formatName 包含格式非正式名称的 String：如JPG、JPEG、GIF等
	 * @param destImageFile 目标图像地址
	 */
	public final static void convert(String srcImageFile, String formatName, String destImageFile) {
		try {
			File f = new File(srcImageFile);
			f.canRead();
			f.canWrite();
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, formatName, new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 彩色转为黑白
	 * 
	 * @param srcImageFile 源图像地址
	 * @param destImageFile 目标图像地址
	 */
	public final static void gray(String srcImageFile, String destImageFile) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile));
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
			src = op.filter(src, null);
			ImageIO.write(src, "JPEG", new File(destImageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给图片添加文字水印
	 * 
	 * @param pressText 水印文字
	 * @param srcImageFile 源图像地址
	 * @param destImageFile 目标图像地址
	 * @param fontName 水印的字体名称
	 * @param fontStyle 水印的字体样式
	 * @param color 水印的字体颜色
	 * @param fontSize 水印的字体大小
	 * @param x 修正值
	 * @param y 修正值
	 * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 */
	public final static void pressText(String pressText, String srcImageFile, String destImageFile, String fontName, int fontStyle, Color color, int fontSize,
			int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			// 在指定坐标绘制水印文字
			g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));// 输出到文件流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给图片添加文字水印
	 * 
	 * @param pressText 水印文字
	 * @param srcImageFile 源图像地址
	 * @param destImageFile 目标图像地址
	 * @param fontName 字体名称
	 * @param fontStyle 字体样式
	 * @param color 字体颜色
	 * @param fontSize 字体大小
	 * @param x 修正值
	 * @param y 修正值
	 * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 */
	public final static void pressText2(String pressText, String srcImageFile, String destImageFile, String fontName, int fontStyle, Color color, int fontSize,
			int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			// 在指定坐标绘制水印文字
			g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给图片添加图片水印
	 * 
	 * @param pressImg 水印图片
	 * @param srcImageFile 源图像地址
	 * @param destImageFile 目标图像地址
	 * @param x 修正值。 默认在中间
	 * @param y 修正值。 默认在中间
	 * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 */
	public final static void pressImage(String pressImg, String srcImageFile, String destImageFile, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			// 水印文件
			Image src_biao = ImageIO.read(new File(pressImg));
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao, null);
			// 水印文件结束
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 旋转图片为指定角度
	 * 
	 * @param srcImageFile 源图像文件地址
	 * @param result 旋转后的图像地址
	 * @param degree 旋转角度
	 * @return
	 */
	public final static void rotateImage(String srcImageFile, String result, int degree) {

		BufferedImage src;
		try {
			src = ImageIO.read(new File(srcImageFile));
			// 读入文件
			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长

			Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = tag.createGraphics();

			g.rotate(Math.toRadians(degree), width / 2, height / 2);

			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 旋转图片为指定角度
	 * 
	 * @param srcImageFile 源图像文件地址
	 * @param result 旋转后的图像地址
	 * @param degree 旋转角度
	 * @return
	 */
	public final static void rotateImage(String srcImageFile, String result, boolean direction) {

		BufferedImage src;
		try {
			src = ImageIO.read(new File(srcImageFile));
			// 读入文件
			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长

			Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = tag.createGraphics();

			if (direction) {
				g.rotate(Math.toRadians(90), width / 2, height / 2);
			} else {
				g.rotate(Math.toRadians(-90), width / 2, height / 2);
			}

			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图片水平翻转
	 * 
	 * @param srcImageFile 源图像文件地址
	 * @param result 翻转后的图像地址
	 * @param direction 方向 翻转方向 true 顺时针 false为逆时针
	 * @return
	 */
	public final static void flipImage(String srcImageFile, String result) {

		BufferedImage src;
		try {
			src = ImageIO.read(new File(srcImageFile));
			// 读入文件
			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长

			Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = tag.createGraphics();
			g.drawImage(image, 0, 0, width, height, width, 0, 0, height, null); // 翻转后的图片
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 计算text的长度（一个中文算两个字符）
	 * 
	 * @param text
	 * @return
	 */
	public final static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}

	private final static ColorModel getColorModel(Image image) throws InterruptedException, IllegalArgumentException {
		PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
		if (!pg.grabPixels()) throw new IllegalArgumentException();
		return pg.getColorModel();
	}

	private static void loadImage(Image image) throws InterruptedException, IllegalArgumentException {
		Component dummy = new Component() {
			private static final long	serialVersionUID	= 1L;
		};
		MediaTracker tracker = new MediaTracker(dummy);
		tracker.addImage(image, 0);
		tracker.waitForID(0);
		if (tracker.isErrorID(0)) throw new IllegalArgumentException();
	}

	public static BufferedImage createBufferedImage(Image image) throws InterruptedException, IllegalArgumentException {
		loadImage(image);
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		ColorModel cm = getColorModel(image);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		BufferedImage bi = gc.createCompatibleImage(w, h, cm.getTransparency());
		Graphics2D g = bi.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return bi;
	}

	public static BufferedImage readImage(InputStream is) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}

	public static void encodeGIF(BufferedImage image, OutputStream out) throws IOException {
		Gif89Encoder encoder = new Gif89Encoder(image);
		encoder.encode(out);
	}

	/**
	 * @param bi
	 * @param type
	 * @param out
	 */
	public static void printImage(BufferedImage bi, String type, OutputStream out) {
		try {
			if (type.equals(IMAGE_TYPE_GIF)) encodeGIF(bi, out);
			else ImageIO.write(bi, type, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Get image type from byte[]
	 * 
	 * @param textObj image byte[]
	 * @return String image type
	 */
	public static String getImageType(byte[] textObj) {
		String type = IMAGE_TYPE_NOT_AVAILABLE;
		ByteArrayInputStream bais = null;
		MemoryCacheImageInputStream mcis = null;
		try {
			bais = new ByteArrayInputStream(textObj);
			mcis = new MemoryCacheImageInputStream(bais);
			Iterator<ImageReader> itr = ImageIO.getImageReaders(mcis);
			while (itr.hasNext()) {
				ImageReader reader = (ImageReader) itr.next();
				if (reader instanceof GIFImageReader) {
					type = IMAGE_TYPE_GIF;
				} else if (reader instanceof JPEGImageReader) {
					type = IMAGE_TYPE_JPG;
				} else if (reader instanceof PNGImageReader) {
					type = IMAGE_TYPE_PNG;
				} else if (reader instanceof BMPImageReader) {
					type = IMAGE_TYPE_BMP;
				}
				reader.dispose();
			}
		}
		finally {
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			if (mcis != null) {
				try {
					mcis.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
		return type;
	}

	/**
	 * 程序入口：用于测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 1-缩放图像：
		// 方法一：按比例缩放
		// ImageStreamUtils.scale("e:/image/abc.jpg", "e:/image/abc_scale.jpg", 2, true);// 测试OK
		// 方法二：按高度和宽度缩放
//		ImageStreamUtils.scale("F:/0.jpg", "F:/2.jpg", 106, 226);// 测试OK
	    
		ImageStreamUtils.scale("F:/0.jpg", "F:/3.jpg", 1, true);// 测试OK

		// // 2-切割图像：
		// // 方法一：按指定起点坐标和宽高切割
		// ImageStreamUtils.cut("e:/image/abc.jpg", "e:/image/abc_cut.jpg", 0, 0, 400,
		// 400);// 测试OK
		// // 方法二：指定切片的行数和列数
		// ImageStreamUtils.cut2("e:/image/abc.jpg", "e:/", 2, 2);// 测试OK
		// // 方法三：指定切片的宽度和高度
		// ImageStreamUtils.cut3("e:/image/abc.jpg", "e:/", 300, 300);// 测试OK
		//
		// // 3-图像类型转换：
		// ImageStreamUtils.convert("e:/image/abc.jpg", "GIF",
		// "e:/image/abc_convert.gif");// 测试OK
		//
		// // 4-彩色转黑白：
		// ImageStreamUtils.gray("e:/image/abc.jpg", "e:/image/abc_gray.jpg");// 测试OK
		//
		// // 5-给图片添加文字水印：
		// // 方法一：
		// ImageStreamUtils.pressText("我是水印文字", "e:/image/abc.jpg",
		// "e:/image/abc_pressText.jpg", "宋体", Font.BOLD, Color.white, 80,
		// 0, 0, 0.5f);// 测试OK
		// // 方法二：
		// ImageStreamUtils.pressText2("我也是水印文字", "e:/image/abc.jpg",
		// "e:/image/abc_pressText2.jpg", "黑体", 36, Color.white, 80, 0, 0,
		// 0.5f);// 测试OK
		//
		// // 6-给图片添加图片水印：
		// // ImageUtils.pressImage("e:/image/abc2.jpg",
		// // "e:/image/abc.jpg","e:/image/abc_pressImage.jpg", 0, 0, 0.5f);//测试OK
		//
		// // 7-旋转图片
		// // 方法一：任意角度旋转
		// ImageStreamUtils.rotateImage("e:/image/abc.jpg", "e:/image/abc_rotate_1.jpg",
		// 90);
		// // 方法二：固定顺时针或者逆时针90度旋转
		// ImageStreamUtils.rotateImage("e:/image/abc.jpg", "e:/image/abc_rotate_2.jpg",
		// true);
		// ImageStreamUtils.rotateImage("e:/image/abc.jpg", "e:/image/abc_rotate_3.jpg",
		// false);
		// // 8-图片翻转
		// ImageStreamUtils.flipImage("e:/image/abc.jpg", "e:/image/abc_flip.jpg");
	}

}