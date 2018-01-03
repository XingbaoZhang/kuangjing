package com.kj;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kj.adapter.CommonAdapter;
import com.kj.adapter.ViewHolder;
import com.kj.base.MyBaseActivity;
import com.kj.pojo.RetMsg;
import com.kj.pojo.sousuo;
import com.kj.util.HttpUrl;
import com.kj.util.MyApplication;
import com.kj.util.MyToastUtil;
import com.kj.util.NetWorkUtils;
import com.kj.util.Url;
import com.kj.util.UserClient;
import com.kj.view.XListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.FileDownloader;
import org.wlf.filedownloader.listener.OnFileDownloadStatusListener;
import org.wlf.filedownloader.listener.simple.OnSimpleFileDownloadStatusListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class Activity_Search extends MyBaseActivity {
    EditText key;
    private XListView listView;
    List<sousuo> list;
    private int pageNum1 = 1;// 无关键字页数
    private int pageSize = 10;
    Context con = Activity_Search.this;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileDownloader.unregisterDownloadStatusListener(mOnFileDownloadStatusListener);
    }

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_search);
        FileDownloader
                .registerDownloadStatusListener(mOnFileDownloadStatusListener);
        listView = (XListView) findViewById(R.id.listView);
        listView.setPullRefreshEnable(true);
        listView.setPullLoadEnable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position - 1).getWdtype().equals("1")) {
                    RequestParams ps = new RequestParams();
                    ps.add("id", list.get(position - 1).getId());
                    UserClient.get(HttpUrl.GetBzDes + ";JSESSIONID=" + MyApplication.getApp().getU().getSessionid(), ps, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(String content) {
                            super.onSuccess(content);
                            System.out.println(content);
                            RetMsg ret = JSON.parseObject(content, RetMsg.class);
                            if (ret.getCode().equals("0")) {
                                JSONObject j = JSON.parseArray(ret.getData()).getJSONObject(0);
                                FileDownloader.start(Url.urls() + j.getString("affixname"));
                            }
                        }
                    });
                }
                if (list.get(position - 1).getWdtype().equals("3")) {
                    RequestParams ps = new RequestParams();
                    ps.add("id", list.get(position - 1).getId());
                    UserClient.get(HttpUrl.GetZdDes + ";JSESSIONID=" + MyApplication.getApp().getU().getSessionid(), ps, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(String content) {
                            super.onSuccess(content);
                            System.out.println(content);
                            RetMsg ret = JSON.parseObject(content, RetMsg.class);
                            if (ret.getCode().equals("0")) {
                                JSONObject j = JSON.parseArray(ret.getData()).getJSONObject(0);
                                FileDownloader.start(Url.urls() + j.getString("affixaddress"));
                            }
                        }
                    });
                }
                if (list.get(position - 1).getWdtype().equals("2")) {
                    RequestParams ps = new RequestParams();
                    ps.add("id", list.get(position).getId());
                    UserClient.get(HttpUrl.GetScDes + ";JSESSIONID=" + MyApplication.getApp().getU().getSessionid(), ps, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(String content) {
                            super.onSuccess(content);
                            System.out.println(content);
                            RetMsg ret = JSON.parseObject(content, RetMsg.class);
                            if (ret.getCode().equals("0")) {
                                JSONObject j = JSON.parseArray(ret.getData()).getJSONObject(0);
                                startActivity(new Intent(Activity_Search.this, ActivityScDes.class).putExtra("name", j.getString("catalogname")).putExtra("time", j.getString("publicdate")).putExtra("id", j.getString("id")).putExtra("msg",j.getString("content")).putExtra("xzcs","").putExtra("yeshu","").putExtra("isdown",j.getString("isdown")));
                            }
                        }
                    });
                }
            }
        });
        key = (EditText) findViewById(R.id.key);
        key.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (NetWorkUtils.isNetworkConnected(con) && MyApplication.getApp().getU() != null)
                    getlist();
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
            System.out.println("完成下载");
            String videopath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + File.separator
                    + "FileDownloader/"
                    + downloadFileInfo.getFileName();
            startActivity(new Intent(con, ActivityYulan.class).putExtra("url", videopath));
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

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    public void getlist() {
        RequestParams ps = new RequestParams();
        ps.add("PageNo", pageNum1 + "");
        ps.add("PageSize", pageSize + "");
        ps.add("standName", key.getText().toString());
        UserClient.get(HttpUrl.Sousuo + ";JSESSIONID=" + MyApplication.getApp().getU().getSessionid(), ps, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                super.onFailure(statusCode, error, content);
                MyToastUtil.ShowToast(con, "请求失败");
            }

            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                System.out.println(content);
                RetMsg ret = JSON.parseObject(content, RetMsg.class);
                if (ret.getCode().equals("0")) {
                    if (pageNum1 == 1)
                        list = new ArrayList<sousuo>();
                    JSONObject j = JSON.parseObject(ret.getData());
                    List<sousuo> ll = JSON.parseArray(
                            j.getString("list"), sousuo.class);
                    for (int i = 0; i < ll.size(); i++) {
                        list.add(ll.get(i));
                    }

                    listView.stopRefresh();
                    listView.stopLoadMore();
                    listView.setRefreshTime("刚刚");
                    listView.setXListViewListener(new XListView.IXListViewListener() {
                        @Override
                        public void onRefresh() {
                            pageNum1 = 1;
                            getlist();
                        }

                        @Override
                        public void onLoadMore() {
                            pageNum1 = pageNum1 + 1;
                            getlist();
                        }
                    });
                    listView.setAdapter(new CommonAdapter<sousuo>(con, list, R.layout.sousuo_item) {
                        @Override
                        public void convert(ViewHolder helper, final sousuo item) {
                            helper.setText(R.id.name, item.getStandName());
                            helper.setText(R.id.time, item.getPublicdate());
                            ImageView icon = helper.getView(R.id.icon);
                            if (item.getWdtype().equals("1"))
                                icon.setImageResource(R.mipmap.bz);
                            if (item.getWdtype().equals("3"))
                                icon.setImageResource(R.mipmap.zd);
                            if (item.getWdtype().equals("2"))
                                icon.setImageResource(R.mipmap.sc);
                        }
                    });
                    listView.setSelection(list.size() - ll.size());
                } else {
                    MyToastUtil.ShowToast(con, "获取错误");
                }
            }
        });
    }
}
