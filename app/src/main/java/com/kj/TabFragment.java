package com.kj;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kj.adapter.CommonAdapter;
import com.kj.adapter.ViewHolder;
import com.kj.pojo.Bz;
import com.kj.pojo.RetMsg;
import com.kj.pojo.Xiazai;
import com.kj.util.HttpUrl;
import com.kj.util.MyApplication;
import com.kj.util.MyToastUtil;
import com.kj.util.TimeUtil;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zq on 2017/1/12.
 */
public class TabFragment extends Fragment {

    public static String cid;
    private XListView listView;
    List<Bz> list;
    private int pageNum1 = 1;// 无关键字页数
    private int pageSize = 10;
    private String type = "ckxq";
    Xiazai x;

    public static Fragment newInstance(String classid) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putString("key", classid);
        fragment.setArguments(args);


        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.common_list, container, false);
        FileDownloader
                .registerDownloadStatusListener(mOnFileDownloadStatusListener);
        listView = (XListView) rootView.findViewById(R.id.listView);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        getlist();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void getlist() {
        RequestParams ps = new RequestParams();
        ps.add("PageNo", pageNum1 + "");
        ps.add("PageSize", pageSize + "");
        ps.add("classid", getArguments().getString("key"));
        UserClient.get(HttpUrl.GetBzList + ";JSESSIONID=" + MyApplication.getApp().getU().getSessionid(), ps, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                super.onFailure(statusCode, error, content);
                MyToastUtil.ShowToast(getActivity(), "请求失败");
            }

            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                System.out.println(content);
                RetMsg ret = JSON.parseObject(content, RetMsg.class);
                if (ret.getCode().equals("0")) {
                    if (pageNum1 == 1)
                        list = new ArrayList<Bz>();
                    JSONObject j = JSON.parseObject(ret.getData());
                    List<Bz> ll = JSON.parseArray(
                            j.getString("list"), Bz.class);
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
                    listView.setAdapter(new CommonAdapter<Bz>(getActivity(), list, R.layout.bz_item) {
                        @Override
                        public void convert(ViewHolder helper, final Bz item) {
                            helper.setText(R.id.bzbh, item.getStandNum());
                            helper.setText(R.id.bzmc, item.getStandName());
                            helper.setText(R.id.fbsj, item.getPublicdate());
                            helper.setText(R.id.xzcs, item.getDowntime());
                            LinearLayout ckxq = (LinearLayout) helper.getView(R.id.ckxq);
                            LinearLayout xz = (LinearLayout) helper.getView(R.id.xz);
                            ckxq.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    type = "ckxq";
                                        FileDownloader.start("http://139.224.24.245:7878/AQ.pdf");
                                }
                            });
                            xz.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    type = "xz";
                                x=new Xiazai();
                                x.setName(item.getStandName());
                                x.setTime(TimeUtil.getTime());
                                x.setType("标准");
                                }
                            });

                        }
                    });

                    listView.setSelection(list.size() - ll.size());
                } else {
                    MyToastUtil.ShowToast(getActivity(), "获取错误");
                }
            }
        });
    }

    private OnFileDownloadStatusListener mOnFileDownloadStatusListener = new OnSimpleFileDownloadStatusListener() {
        @Override
        public void onFileDownloadStatusRetrying(
                DownloadFileInfo downloadFileInfo, int retryTimes) {
            // 正在重试下载（如果你配置了重试次数，当一旦下载失败时会尝试重试下载），retryTimes是当前第几次重试
        }

        @Override
        public void onFileDownloadStatusWaiting(
                DownloadFileInfo downloadFileInfo) {
            // 等待下载（等待其它任务执行完成，或者FileDownloader在忙别的操作）
        }

        @Override
        public void onFileDownloadStatusPreparing(
                DownloadFileInfo downloadFileInfo) {
            // 准备中（即，正在连接资源）
        }

        @Override
        public void onFileDownloadStatusPrepared(
                DownloadFileInfo downloadFileInfo) {
            // 已准备好（即，已经连接到了资源）
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
                if(type.equals("ckxq")){
                    startActivity(new Intent(getActivity(),ActivityYulan.class).putExtra("url",videopath));
                }else{
                    x.setUrl(videopath);
                    x.save();
                    MyToastUtil.ShowToast(getActivity(),"成功");
                }

        }

        @Override
        public void onFileDownloadStatusFailed(String url,
                                               DownloadFileInfo downloadFileInfo,
                                               FileDownloadStatusFailReason failReason) {
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
