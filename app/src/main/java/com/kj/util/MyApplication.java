package com.kj.util;

import android.app.Application;
import android.os.Environment;

import com.kj.pojo.User;

import java.io.File;
import org.wlf.filedownloader.FileDownloadConfiguration;
import org.wlf.filedownloader.FileDownloadConfiguration.Builder;
import org.wlf.filedownloader.FileDownloader;

public class MyApplication extends Application {

    public User u;

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

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
        // 1、创建Builder
        Builder builder = new FileDownloadConfiguration.Builder(this);

        // 2.配置Builder
        // 配置下载文件保存的文件夹
        builder.configFileDownloadDir(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator + "FileDownloader");
        System.out.println(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator + "FileDownloader");
        // 配置同时下载任务数量，如果不配置默认为2
        builder.configDownloadTaskSize(3);
        // 配置失败时尝试重试的次数，如果不配置默认为0不尝试
        builder.configRetryDownloadTimes(5);
        // 开启调试模式，方便查看日志等调试相关，如果不配置默认不开启
        builder.configDebugMode(true);
        // 配置连接网络超时时间，如果不配置默认为15秒
        builder.configConnectTimeout(25000);// 25秒

        // 3、使用配置文件初始化FileDownloader
        FileDownloadConfiguration configuration = builder.build();
        FileDownloader.init(configuration);
        //实例化数据库

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
