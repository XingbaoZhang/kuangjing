package com.kj.util;

/**
 * Created by Administrator on 2018/6/6 0006.
 */
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.kj.pojo.URLImageGetter;

/**
 * Created by shucheng.qu on 2017/9/21.
 */

public class HtmlUtils {

    public static Spanned getHtml(Context context, TextView textView, String string) {
//        textView.setMovementMethod(ScrollingMovementMethod.getInstance());// 滚动
        textView.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页//click must
        return Html.fromHtml(string, new URLImageGetter(textView, context), null);
    }

}