package com.kj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kj.adapter.CommonAdapter;
import com.kj.adapter.ViewHolder;
import com.kj.pojo.RetMsg;
import com.kj.pojo.Xiazai;
import com.kj.pojo.Zd;
import com.kj.util.HttpUrl;
import com.kj.util.MyApplication;
import com.kj.util.MyToastUtil;
import com.kj.util.SharedPreferencesUtils;
import com.kj.util.TimeUtil;
import com.kj.util.Url;
import com.kj.util.UserClient;
import com.kj.view.XListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.wlf.filedownloader.FileDownloader;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zq on 2017/1/12.
 */
public class ZdFragment extends Fragment {

    public static String cid;
    private XListView listView;
    List<Zd> list;
    private int pageNum1 = 1;// 无关键字页数
    private int pageSize = 10;

    public static Fragment newInstance(String classid,String isdown) {
        ZdFragment fragment = new ZdFragment();
        Bundle args = new Bundle();
        args.putString("key", classid);
        args.putString("isdown", isdown);
        fragment.setArguments(args);


        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.common_list, container, false);
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
        UserClient.get(HttpUrl.GetZdList + ";JSESSIONID=" + MyApplication.getApp().getU().getSessionid(), ps, new AsyncHttpResponseHandler() {
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
                        list = new ArrayList<Zd>();
                    final JSONObject j = JSON.parseObject(ret.getData());
                    List<Zd> ll = JSON.parseArray(
                            j.getString("list"), Zd.class);
                    for (int i = 0; i < ll.size(); i++) {
                        list.add(ll.get(i));
                    }

                    for(int i=0;i<list.size();i++){
                        list.get(i).setPos(i+1+"");
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
                            if (Integer.parseInt(j.getString("count")) > (pageNum1-1) * 10)
                                getlist();
                            else
                                listView.stopLoadMore();
                        }
                    });
                    listView.setAdapter(new CommonAdapter<Zd>(getActivity(), list, R.layout.new_bz_item) {
                        @Override
                        public void convert(ViewHolder helper, final Zd item) {
                            helper.setText(R.id.name, item.getPos() + "  " + item.getSysName());
//                            helper.setText(R.id.zdmc, item.getSysName());
//                            helper.setText(R.id.fbsj, item.getPublicdate());
//                            helper.setText(R.id.xzcs, item.getDowntime());
//                            LinearLayout ckxq = (LinearLayout) helper.getView(R.id.ckxq);
//                            LinearLayout xz = (LinearLayout) helper.getView(R.id.xz);
//                            ckxq.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    SharedPreferencesUtils.setParam(getActivity(), "xztype", "ckxq");
//                                    ActivityZdgl.x = new Xiazai();
//                                    ActivityZdgl.x.setName(item.getSysName());
//                                    ActivityZdgl.x.setTime(TimeUtil.getTime());
//                                    ActivityZdgl.x.setType("制度");
//                                    ActivityZdgl.x.setXid(item.getId());
//                                    ActivityBzgl.x.setXzzt(getArguments().getString("key"));
//                                    FileDownloader.start(Url.urls() + item.getAffixaddress());
//                                    MyToastUtil.ShowToast(getActivity(),"开始加载，请等待...");
//                                }
//                            });
//                            xz.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    ActivityZdgl.x = new Xiazai();
//                                    ActivityZdgl.x.setName(item.getSysName());
//                                    ActivityZdgl.x.setTime(TimeUtil.getTime());
//                                    ActivityZdgl.x.setType("制度");
//                                    ActivityZdgl.x.setXid(item.getId());
//                                    ActivityBzgl.x.setXzzt(getArguments().getString("key"));
//                                    SharedPreferencesUtils.setParam(getActivity(), "xztype", "zx");
//                                    FileDownloader.start(Url.urls() + item.getAffixaddress());
//                                    MyToastUtil.ShowToast(getActivity(),"开始下载，请等待...");
//
//                                }
//                            });
                        }
                    });
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            SharedPreferencesUtils.setParam(getActivity(), "xztype", "ckxq");
                            Log.i("iddddddd:", getArguments().getString("key"));
                            ActivityZdgl.x = new Xiazai();
                            ActivityZdgl.x.setName(list.get(position - 1).getSysName());
                            ActivityZdgl.x.setTime(TimeUtil.getTime());
                            ActivityZdgl.x.setType("制度");
                            ActivityZdgl.x.setXid(list.get(position - 1).getId());
                            ActivityZdgl.x.setXzzt(getArguments().getString("isdown"));
                            FileDownloader.start(Url.urls() + list.get(position - 1).getAffixaddress());
                            MyToastUtil.ShowToast(getActivity(), "开始加载，请等待...");
                        }
                    });

                    listView.setSelection(list.size() - ll.size());
                } else {
                    MyToastUtil.ShowToast(getActivity(), "获取错误");
                }

            }
        });
    }
}
