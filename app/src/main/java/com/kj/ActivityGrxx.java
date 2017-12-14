package com.kj;

import android.view.View;
import android.widget.LinearLayout;

import com.kj.base.MyBaseActivity;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class ActivityGrxx extends MyBaseActivity{
    LinearLayout back;
    @Override
    protected void initUI() {
        setContentView(R.layout.activity_grxx);
        back=(LinearLayout)findViewById(R.id.back);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
    }
}
