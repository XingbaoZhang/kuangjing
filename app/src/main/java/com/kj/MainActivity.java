package com.kj;

import android.view.View;
import android.widget.LinearLayout;

import com.kj.base.MyBaseActivity;

public class MainActivity extends MyBaseActivity {
    LinearLayout search,bzgl,zdgl,scgl,grzx,xzzx;

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_main);
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
