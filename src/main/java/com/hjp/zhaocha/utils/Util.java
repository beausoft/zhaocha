package com.hjp.zhaocha.utils;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 
 * @author hjp
 *
 */
public class Util {
	/**
	 * 全屏截图
	 * @return 返回BufferedImage
	 */
	public static BufferedImage getFullScreenShot() {
		BufferedImage bfImage = null;
		int width = getScreenWidth();
		int height = getScreenHeight();
		try {
			Robot robot = new Robot();
			bfImage = robot.createScreenCapture(new Rectangle(0, 0, width, height));
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return bfImage;
	}
	
	/**
	 * 全屏截图，保持为文件到指定位置
	 */
    public static void fullScreenShotAsFile(String savePath) {
    	BufferedImage image = getFullScreenShot();
        String format = "png";
        String fileName = "screenshot";
        File path = new File(savePath);
        File file = new File(path, fileName + "." + format);
        try {
            ImageIO.write(image, format, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取包内图片资源
     * @param pathName
     * @return
     */
    public static ImageIcon getPackagedImageIco(String pathName) {
    	ImageIcon imageIco = null;
		imageIco = new ImageIcon(Util.class.getResource(pathName));
		return imageIco;
    }
    
    public static BufferedImage getPackagedBufferedImage(String pathName) {
		BufferedImage bgImage = null;
		try {
			bgImage = ImageIO.read(Util.class.getResource(pathName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bgImage;
    }
    
    /**
     * 图片剪切
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage cutImage(BufferedImage srcBfImg, int x, int y, int width, int height) {
    	BufferedImage bfImage = null;
    	CropImageFilter cropFilter = new CropImageFilter(x, y, width, height);  
        Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(srcBfImg.getSource(), cropFilter));  
        bfImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        Graphics g = bfImage.getGraphics();  
        g.drawImage(img, 0, 0, null);
        g.dispose();  
    	return bfImage;
    }
    
    /**
     * 图片剪切
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public static void cutImageInfoFile(BufferedImage srcBfImg, 
    		int x, int y, int width, int height, 
    		String path, 
    		String formatName) {
    	
    	BufferedImage bfImage = null;
    	CropImageFilter cropFilter = new CropImageFilter(x, y, width, height);  
        Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(srcBfImg.getSource(), cropFilter));  
        bfImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        Graphics g = bfImage.getGraphics();  
        g.drawImage(img, 0, 0, null); // 绘制小图  
        g.dispose();  
        //输出为文件  
        String fileName = "imagecut."+formatName;  
        File f = new File(new File(path), fileName);  
        try {
			ImageIO.write(bfImage, formatName, f);
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
    
	/**
	 * 根据文件路径获取图片RGB数组
	 * @param filePath
	 * @return
	 */
	public static int[][] getImageGRB(String filePath) {
		File file = new File(filePath);
		int[][] result = null;
		if (!file.exists()) {
			return result;
		}
		try {
			BufferedImage bufImg = ImageIO.read(file);
			int width = bufImg.getWidth();
			int height = bufImg.getHeight();
			result = new int[height][width];
			for (int h = 0; h < height; h++) {
				for (int w = 0; w < width; w++) {
					result[h][w] = bufImg.getRGB(w, h) & 0xFFFFFF;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据BufferedImage获取图片RGB数组
	 * @param bfImage
	 * @return
	 */
	public static int[][] getImageRGB(BufferedImage bfImage) {
		int width = bfImage.getWidth();
		int height = bfImage.getHeight();
		int[][] result = new int[height][width];
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				result[h][w] = bfImage.getRGB(w, h) & 0xFFFFFF;
			}
		}
		return result;
	}
    
	
	public static int getScreenWidth() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	}
	
	public static int getScreenHeight() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	}
}
