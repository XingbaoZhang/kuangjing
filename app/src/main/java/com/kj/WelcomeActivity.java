package com.kj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.kj.base.MyBaseActivity;


/**
 * 欢迎界面
 */
public class WelcomeActivity extends MyBaseActivity {
    Context con = WelcomeActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initUI() {
        // TODO Auto-generated method stub
        setContentView(R.layout.activity_welcome);

    }

    @Override
    protected void initData() {
        // TODO Auto-generated method stub
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(con, ActivityUserLogin.class));
                finish();
            }
        }, 2000);
    }

    @Override
    protected void initListener() {
        // TODO Auto-generated method stub

    }

}
