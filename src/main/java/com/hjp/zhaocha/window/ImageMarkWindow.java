package com.hjp.zhaocha.window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JWindow;

import com.hjp.zhaocha.ImageOperater;

/**
 * 透明窗口
 * @author hjp
 *
 */
public class ImageMarkWindow extends JWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2627668840071352L;

	public static final int SYSTEM_STUTAS_BAR_HEIGHT = 40;
	
	int alpha = 0; //0代表完全透明，255代表完全不透明
	ImageOperater imgOperater;
	
	int leftImgOffsetX;
	int leftImgOffsetY;
	int rightImgOffsetX;
	int rightImgOffsetY;
	int[][] differentData; //比较运算得到的结果数组
	
	boolean isGameFinded = false;
	
	public ImageMarkWindow() {
		init();
		if(isGameFinded) {
			Rectangle gameRect = imgOperater.getGameRect();
			this.setBounds((int)gameRect.x, (int)gameRect.y, (int)gameRect.getWidth(), (int)gameRect.getHeight());
			//要设置JFrame窗口透明度必须这样先设置窗口无标题栏，不然抛出异常
			//this.setUndecorated(true); 
			this.setBackground(new Color(0, 0, 0, alpha));
			this.setAlwaysOnTop(true);
			this.setVisible(true);
		}else{
			this.dispose();
		}
	}
	
	private void init() {
		imgOperater = new ImageOperater();
		if(!imgOperater.isGameOpened()) {
			return;
		}
		
		//比较两张图片，找出不同地方
		differentData = imgOperater.getDifferentData();
		
		leftImgOffsetX = imgOperater.getLeftImgOffsetX();
		leftImgOffsetY = imgOperater.getLeftImgOffsetY();
		
		rightImgOffsetX = imgOperater.getRightImgOffsetX();
		rightImgOffsetY = imgOperater.getRightImgOffsetY();
		isGameFinded = true;
	}
	
	@Override
	public void paint(Graphics g) {
		if(!isGameFinded) {
			return;
		}
		
		int offsetX = 0;
		int offsetY = 0;
		int leftPaintX = 0;
		int leftPaintY = 0;
		int rightPaintX = 0;
		int rightPaintY = 0;
		for (int y=0; y < differentData.length; y++) {
			for(int x=0; x<differentData[0].length; x++) {
				if(differentData[y][x] != 0) {
					//如果该点四边都有需要绘制的点，则不绘制该点。
					if(y>0 && y<differentData.length-1
							&& x>0 && x<differentData[0].length-1
						    && differentData[y-1][x] != 0
							&& differentData[y][x+1] != 0
							&& differentData[y+1][x] != 0
							&& differentData[y][x-1] != 0) {
						continue;
					}
					leftPaintX = offsetX+x+this.leftImgOffsetX;
					leftPaintY = offsetY+y+this.leftImgOffsetY;
					rightPaintX = offsetX+x+this.rightImgOffsetX;
					rightPaintY = offsetY+y+this.rightImgOffsetY;
					
					g.setColor(new Color(0xFF00FF));
					//左边图片位置上绘制不同点
					g.drawLine(leftPaintX, leftPaintY, leftPaintX, leftPaintY);
					//右边图片位置上绘制不同点
					g.drawLine(rightPaintX, rightPaintY, rightPaintX, rightPaintY);
					//换颜色偏移一个像素各再画一次
					g.setColor(new Color(0xFF00FF));
					g.drawLine(leftPaintX+1, leftPaintY+1, leftPaintX+1, leftPaintY+1);
					g.drawLine(rightPaintX+1, rightPaintY+1, rightPaintX+1, rightPaintY+1);
				}
			}
		}
		
	}
	
	
}
