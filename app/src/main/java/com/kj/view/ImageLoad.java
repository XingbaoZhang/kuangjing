package com.kj.view;

import android.content.Context;
import android.widget.ImageView;

import com.kj.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by shucheng.qu on 2017/8/24.
 */

public class ImageLoad {

    public static void loadPlaceholder(Context context, String url, Target target) {

        Picasso picasso = new Picasso.Builder(context).loggingEnabled(true).build();
        picasso.load(url)
                .placeholder(R.mipmap.bg02)
                .error(R.mipmap.bg02)
                .transform(new ImageTransform())
//                .transform(new CompressTransformation())
                .into(target);
    }

}