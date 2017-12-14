package com.kj;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kj.base.MyBaseActivity;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class ActivityGrzx extends MyBaseActivity {
    ImageView grxx;
    LinearLayout xgmm;
    @Override
    protected void initUI() {
        setContentView(R.layout.activity_grzx);
        grxx = (ImageView) findViewById(R.id.grxx);
        xgmm=(LinearLayout)findViewById(R.id.xgmm);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        grxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ActivityGrxx.class);
            }
        });
        xgmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ActivityChangePass.class);
            }
        });
    }
}
