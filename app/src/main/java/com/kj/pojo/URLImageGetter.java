package com.kj.pojo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.kj.R;
import com.kj.view.ImageLoad;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/6 0006.
 */
public class URLImageGetter implements Html.ImageGetter {
    Context c;
    TextView tv_image;
    private List<Target> targets = new ArrayList<>();

    public URLImageGetter(TextView t, Context c) {
        this.tv_image = t;
        this.c = c;
        tv_image.setTag(targets);
    }

    @Override
    public Drawable getDrawable(final String source) {
        final URLDrawable urlDrawable = new URLDrawable();
        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Drawable drawable = new BitmapDrawable(bitmap);
                drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                urlDrawable.setDrawable(drawable);
                urlDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                tv_image.invalidate();
                tv_image.setText(tv_image.getText());
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                errorDrawable.setBounds(0, 0, errorDrawable.getIntrinsicWidth(), errorDrawable.getIntrinsicHeight());
                urlDrawable.setBounds(0, 0, errorDrawable.getIntrinsicWidth(), errorDrawable.getIntrinsicHeight());
                urlDrawable.setDrawable(errorDrawable);
                tv_image.invalidate();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                placeHolderDrawable.setBounds(0, 0, placeHolderDrawable.getIntrinsicWidth(), placeHolderDrawable.getIntrinsicHeight());
                urlDrawable.setBounds(0, 0, placeHolderDrawable.getIntrinsicWidth(), placeHolderDrawable.getIntrinsicHeight());
                urlDrawable.setDrawable(placeHolderDrawable);
                tv_image.invalidate();
            }
        };

        targets.add(target);
        ImageLoad.loadPlaceholder(c, source, target);
        return urlDrawable;
    }





}

