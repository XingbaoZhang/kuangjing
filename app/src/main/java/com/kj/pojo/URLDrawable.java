package com.kj.pojo;

/**
 * Created by Administrator on 2018/6/6 0006.
 */
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
public class URLDrawable extends BitmapDrawable {
    private Drawable drawable;

    @Override
    public void draw(Canvas canvas) {
        if (drawable != null) {
            drawable.draw(canvas);
        }
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

}
