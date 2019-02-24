package com.hjp.zhaocha;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.hjp.zhaocha.utils.GameWindowUtil;
import com.hjp.zhaocha.utils.Util;
import com.hjp.zhaocha.window.WarmingDialog;

/**
 * 图像分析
 * @author hjp
 *
 */
public class ImageOperater {
	BufferedImage screenShotImage;
	BufferedImage leftImage;
	BufferedImage rightImage;
	
	boolean isInit = false;
	
	int [][] comparedResult;

	//因为截图固定了，所以设置图像坐标为常量
	final int leftImageX = 10
			,leftImageY = 186
			,rightImageX= 403
			,rightImageY= 186;
	
	final int IMG_WIDTH = 380, IMG_HEIGHT = 285;
	
	Rectangle gameRect;
	
	public ImageOperater() {
		init();
	}
	
	private void init() {
		gameRect = GameWindowUtil.findGameWindowRect("大家来找茬");		//寻找游戏窗口
		if(gameRect == null) {
			WarmingDialog.showDlg("没有找到游戏窗口，确认游戏是否在运行", "警告提示");
			return;
		}
		
		screenShotImage = GameWindowUtil.findGameWindowShot(gameRect);
		if(screenShotImage == null) {
			WarmingDialog.showDlg("获取游戏图片失败", "警告提示");
			return;
		}
		
		leftImage = Util.cutImage(screenShotImage, leftImageX, leftImageY, IMG_WIDTH, IMG_HEIGHT);
		rightImage = Util.cutImage(screenShotImage, rightImageX, rightImageY, IMG_WIDTH, IMG_HEIGHT);
		
		compareImage(leftImage, rightImage);
		isInit = true;
	}
	
	public int[][] compareImage(BufferedImage bfImg1, BufferedImage bfImg2) {
		int[][] img1RGB = Util.getImageRGB(bfImg1);
		int[][] img2RGB = Util.getImageRGB(bfImg2);
		comparedResult = new int[IMG_HEIGHT][IMG_WIDTH];
		int pxXOR = 0;
		for(int y=0; y<IMG_HEIGHT; y++) {
			for(int x=0; x<IMG_WIDTH; x++) {
				pxXOR = img1RGB[y][x]^img2RGB[y][x];
				if(pxXOR!=0) {
					comparedResult[y][x] = 1;
				}
			}
		}
		return comparedResult;
	}
	
	public int[][] getDifferentData() {
		return this.comparedResult;
	}
	
	public int getLeftImgOffsetX() {
		return this.leftImageX;
	}
	
	public int getLeftImgOffsetY() {
		return this.leftImageY;
	}

	public int getRightImgOffsetX() {
		return this.rightImageX;
	}
	
	public int getRightImgOffsetY() {
		return this.rightImageY;
	}
	
	public Rectangle getGameRect() {
		return this.gameRect;
	}
	
	/**
	 * 是否发现游戏窗口 
	 * @return
	 */
	public boolean isGameOpened() {
		return isInit;
	}
	
	/**
	 * 成员变量释放
	 */
	public void release() {
		screenShotImage = null;
		leftImage = null;
		rightImage = null;
		comparedResult = null;
	}
	
}
