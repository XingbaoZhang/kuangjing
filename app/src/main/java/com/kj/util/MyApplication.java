package com.kj.util;

import android.app.Application;

import org.litepal.LitePal;

public class MyApplication extends Application {

    private static MyApplication app;

    public static MyApplication getApp() {
        if (app == null) {
            app = new MyApplication();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //实例化数据库
        LitePal.initialize(this);

        ///////////////////////////////////////环信聊天/////////////////////////////////////
     /*   EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
//初始化
        EMClient.getInstance().init(getApplicationContext(), options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
// 默认添加好友时，是不需要验证的，改成需要验证
        EaseUI.getInstance().init(getApplicationContext(), null);*/
    }

}
