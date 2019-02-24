package com.hjp.zhaocha;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

/**
 * Windows user32 api
 * @author hjp
 *
 */
public interface User32Api extends StdCallLibrary {
	
	User32Api INSTANCE = (User32Api)Native.loadLibrary("user32", User32Api.class);
	
	long MessageBoxA(Pointer hWnd,byte[] lpText,byte[] lpCaption,long wType);
	Pointer FindWindowA(byte[] lpClassName,byte[] lpWindowName);
	boolean GetWindowRect(Pointer hWnd,LPRECT lpRect);
	
	public static class LPRECT extends Structure {
		public int left = 0;
		public int top = 0;
		public int right = 0;
		public int bottom = 0;
		
		protected List<String> getFieldOrder() {
			return Arrays.asList(new String [] {"left","top","right","bottom"});
		}
	}
}
