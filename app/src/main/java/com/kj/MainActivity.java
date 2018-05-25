package com.kj;

import android.view.View;
import android.widget.LinearLayout;

import com.kj.base.MyBaseActivity;
import com.kj.view.AdView;
import com.kj.view.ImageInfo;

import java.util.ArrayList;

public class MainActivity extends MyBaseActivity {
    LinearLayout search,bzgl,zdgl,scgl,grzx,xzzx;
    private ArrayList<ImageInfo> l;
    private AdView adView;
    @Override
    protected void initUI() {
        setContentView(R.layout.activity_main);
        adView = (AdView) findViewById(R.id.adview);
        l = new ArrayList<ImageInfo>();
        ImageInfo f = new ImageInfo();
        f.setImagepath(R.mipmap.b1);
        l.add(f);
        ImageInfo f2 = new ImageInfo();
        f2.setImagepath(R.mipmap.b2);
        l.add(f2);
        ImageInfo f3 = new ImageInfo();
        f3.setImagepath(R.mipmap.b3);
        l.add(f3);
        adView.setWebDate(MainActivity.this, l);
        search = (LinearLayout) findViewById(R.id.search);
        bzgl = (LinearLayout) findViewById(R.id.bzgl);
        zdgl = (LinearLayout) findViewById(R.id.zdgl);
        scgl = (LinearLayout) findViewById(R.id.scgl);
        grzx = (LinearLayout) findViewById(R.id.grzx);
        xzzx = (LinearLayout) findViewById(R.id.xzzx);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(Activity_Search.class);
            }
        });
        bzgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ActivityBzgl.class);
            }
        });
        zdgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ActivityZdgl.class);
            }
        });
        grzx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ActivityGrzx.class);
            }
        });
        scgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ActivitySc.class);
            }
        });
        xzzx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ActivityXzzx.class);
            }
        });
    }
}
