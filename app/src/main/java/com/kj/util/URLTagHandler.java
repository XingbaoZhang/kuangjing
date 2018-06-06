package com.kj.util;

/**
 * Created by Administrator on 2018/6/6 0006.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.xml.sax.XMLReader;

import java.util.Locale;

public class URLTagHandler implements TagHandler {

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {

    }
}
/*
* git init
git add README.md
git commit -m "first commit"
git remote add origin git@github.com:evernightking/textview-load-html.git
git push -u origin master
*
* */