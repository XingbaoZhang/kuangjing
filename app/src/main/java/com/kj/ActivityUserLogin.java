package com.kj;

import android.view.View;
import android.widget.ImageView;

import com.kj.base.MyBaseActivity;

/**
 * Created by Administrator on 2017-12-14.
 */

public class ActivityUserLogin extends MyBaseActivity{
    private ImageView regist,login;//登录注册按钮
    @Override
    protected void initUI() {
        setContentView(R.layout.activity_user_login);
        regist=(ImageView)findViewById(R.id.regist);
        login=(ImageView)findViewById(R.id.login);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(MainActivity.class);
            }
        });
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ActivityUserRegist.class);
            }
        });

    }
}
