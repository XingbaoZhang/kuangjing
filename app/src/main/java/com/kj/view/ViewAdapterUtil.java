package com.kj.view;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class ViewAdapterUtil {
	

	

	
    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
	
	/**
	 * 
	 * @Description: 获取当前屏幕属�?
	 * @param @param activity
	 * @param @return
	 * @return
	 * @throws
	 */
	public final static DisplayMetrics getDisplayMetrics(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}
	/**
	 * 
	 * @Description:运营区�?中球宽高适配
	 * @param @param activity
	 * @param @return   height
	 * @return
	 * @throws
	 */
	public final static int getRunAreaBallXY(Activity activity) {
		int wh = getDisplayMetrics(activity).widthPixels;
		if(wh < 500){
			wh = 10;
		}else if (wh < 1000) {
			wh = 15;
		} else if (wh <2000) {
			wh =20;
		} else {
			wh = 25;
		}
		return wh ;
	}
	
	public final static int getScreenWidth(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		return width;
	}
	/**
	 * 
	 * @Description: 获取当前屏幕属�?
	 * @param @param activity
	 * @param @return
	 * @return
	 * @throws
	 */
	public final static int getWidth(Activity activity,int size) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
	    if (width/size < 170)
			return 170;
	    else
	    	return width/size;
	}
}
