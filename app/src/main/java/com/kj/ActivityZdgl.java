package com.kj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.kj.base.MyBaseActivity;
import com.kj.pojo.RetMsg;
import com.kj.pojo.Xiazai;
import com.kj.pojo.Zd;
import com.kj.util.HttpUrl;
import com.kj.util.MyApplication;
import com.kj.util.MyToastUtil;
import com.kj.util.SharedPreferencesUtils;
import com.kj.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.litepal.crud.DataSupport;
import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.FileDownloader;
import org.wlf.filedownloader.listener.OnFileDownloadStatusListener;
import org.wlf.filedownloader.listener.simple.OnSimpleFileDownloadStatusListener;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class ActivityZdgl extends MyBaseActivity {

    private TabLayout tabLayout = null;

    private ViewPager viewPager;

    private Fragment[] mFragmentArrays;

    private String[] mTabTitles;
    LinearLayout cx;//查询
    List<Zd> list;
    Context con=ActivityZdgl.this;
    public static Xiazai x;


    @Override
    protected void initUI() {
        setContentView(R.layout.activity_zdgl);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.tab_viewpager);
        initView();
        FileDownloader
                .registerDownloadStatusListener(mOnFileDownloadStatusListener);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(Activity_Search.class);
            }
        });
    }

    private void initView() {
        cx = (LinearLayout) findViewById(R.id.cx);
        getbzfl();
    }

    final class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentArrays[position];
        }


        @Override
        public int getCount() {
            return mFragmentArrays.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileDownloader.unregisterDownloadStatusListener(mOnFileDownloadStatusListener);
    }
    public void getbzfl(){
        RequestParams ps=new RequestParams();
        UserClient.get(HttpUrl.GetZdFl+";JSESSIONID="+ MyApplication.getApp().getU().getSessionid(),ps,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                Log.i("制度：",content);
                RetMsg ret= JSON.parseObject(content,RetMsg.class);
                list=JSON.parseArray(ret.getData(),Zd.class);
                mTabTitles= new String[list.size()];
                for(int i=0;i<1;i++){
                    mTabTitles[i] = list.get(i).getClassName();
                }
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                //设置tablayout距离上下左右的距离
                //tab_title.setPadding(20,20,20,20);
                mFragmentArrays= new Fragment[1];
                    mFragmentArrays[0] = ZdFragment.newInstance(list.get(0).getId(),list.get(0).getIsdown());
                PagerAdapter pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
                viewPager.setAdapter(pagerAdapter);
                //将ViewPager和TabLayout绑定
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }
    private final OnFileDownloadStatusListener mOnFileDownloadStatusListener = new OnSimpleFileDownloadStatusListener() {
        @Override
        public void onFileDownloadStatusRetrying(
                DownloadFileInfo downloadFileInfo, int retryTimes) {
            // 正在重试下载（如果你配置了重试次数，当一旦下载失败时会尝试重试下载），retryTimes是当前第几次重试
            System.out.println("重试下载");
        }

        @Override
        public void onFileDownloadStatusWaiting(
                DownloadFileInfo downloadFileInfo) {
            // 等待下载（等待其它任务执行完成，或者FileDownloader在忙别的操作）
            System.out.println("等待下载");
        }

        @Override
        public void onFileDownloadStatusPreparing(
                DownloadFileInfo downloadFileInfo) {
            // 准备中（即，正在连接资源）
            System.out.println("准备中下载");
        }

        @Override
        public void onFileDownloadStatusPrepared(
                DownloadFileInfo downloadFileInfo) {
            // 已准备好（即，已经连接到了资源）
            System.out.println("准备下载");
        }

        @Override
        public void onFileDownloadStatusDownloading(
                DownloadFileInfo downloadFileInfo, float downloadSpeed,
                long remainingTime) {
            // 正在下载，downloadSpeed为当前下载速度，单位KB/s，remainingTime为预估的剩余时间，单位秒
        }

        @Override
        public void onFileDownloadStatusPaused(DownloadFileInfo downloadFileInfo) {
            // 下载已被暂停
            System.out.println("暂停下载");
        }

        @Override
        public void onFileDownloadStatusCompleted(
                final DownloadFileInfo downloadFileInfo) {
            // 下载完成（整个文件已经全部下载完成）
            // 需要判断文件是否存在
            String videopath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + File.separator
                    + "FileDownloader/"
                    + downloadFileInfo.getFileName();
            if (SharedPreferencesUtils.getParam(ActivityZdgl.this, "xztype", "").toString().equals("ckxq")) {
                Bundle b=new Bundle();
                x.setUrl(videopath);
                b.putSerializable("x",x);
                startActivity(new Intent(con, ActivityYulan.class).putExtra("url", videopath).putExtras(b));
            } else {
                x.setUrl(videopath);
                if(DataSupport.where("xid=?",x.getXid()).find(Xiazai.class).size()==0){
                    x.save();
                    MyToastUtil.ShowToast(ActivityZdgl.this, "下载成功,请在下载中心查看");
                }else{
                    MyToastUtil.ShowToast(ActivityZdgl.this, "已经下载了");
                }


            }
        }

        @Override
        public void onFileDownloadStatusFailed(String url,
                                               DownloadFileInfo downloadFileInfo,
                                               FileDownloadStatusFailReason failReason) {
            System.out.println("失败下载");
            // 下载失败了，详细查看失败原因failReason，有些失败原因你可能必须关心
            String failType = failReason.getType();
            String failUrl = failReason.getUrl();// 或：failUrl =
            // url，url和failReason.getUrl()会是一样的

            if (FileDownloadStatusFailReason.TYPE_URL_ILLEGAL.equals(failType)) {
                // 下载failUrl时出现url错误
            } else if (FileDownloadStatusFailReason.TYPE_STORAGE_SPACE_IS_FULL
                    .equals(failType)) {
                // 下载failUrl时出现本地存储空间不足
            } else if (FileDownloadStatusFailReason.TYPE_NETWORK_DENIED
                    .equals(failType)) {
                // 下载failUrl时出现无法访问网络
            } else if (FileDownloadStatusFailReason.TYPE_NETWORK_TIMEOUT
                    .equals(failType)) {
                // 下载failUrl时出现连接超时
            } else {
                // 更多错误....
            }

            // 查看详细异常信息
            Throwable failCause = failReason.getCause();// 或：failReason.getOriginalCause()

            // 查看异常描述信息
            String failMsg = failReason.getMessage();// 或：failReason.getOriginalCause().getMessage()
            System.out.println(failMsg);
        }
    };
}
