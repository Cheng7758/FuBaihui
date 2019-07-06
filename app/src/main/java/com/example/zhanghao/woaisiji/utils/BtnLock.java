package com.example.zhanghao.woaisiji.utils;

/**
 * 按钮锁--通过按钮点击间隔时间，判断是否为连续点击<br>
 */
public class BtnLock {
	/** 按钮最后一次点击的时间 **/
	private static long lastClickTime;


	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 500) {
			return true;
		}
		lastClickTime = time;
		return false;
	}


	public static boolean isFastDoubleClick2() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 300) {
			return true;
		}
		lastClickTime = time;
		return false;
	}


	public static boolean isExitDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 2000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}


	public static boolean isRefreshDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 5000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
}
