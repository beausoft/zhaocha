package com.hjp.zhaocha.utils;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import com.hjp.zhaocha.User32Api;
import com.hjp.zhaocha.User32Api.LPRECT;
import com.sun.jna.Pointer;

/**
 * 游戏窗口
 * @author hjp
 *
 */
public class GameWindowUtil {
	
	public static Rectangle findGameWindowRect(String windowTitle) {
		try {
			Pointer hWnd = User32Api.INSTANCE.FindWindowA(null, (windowTitle + "\0").getBytes("gbk"));
			if(hWnd == Pointer.NULL) {
				throw new RuntimeException("没有找到窗口");
			} else {
				LPRECT lpRect = new LPRECT();
				if(User32Api.INSTANCE.GetWindowRect(hWnd, lpRect)) {
					return new Rectangle(lpRect.left, lpRect.top, lpRect.right - lpRect.left, lpRect.bottom-lpRect.top);
				} else {
					throw new RuntimeException("获取rect失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static BufferedImage findGameWindowShot(Rectangle rect) {
		try {
			Robot robot = new Robot();
			BufferedImage bfImage = robot.createScreenCapture(rect);
			return bfImage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
