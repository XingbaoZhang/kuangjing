package com.kj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kj.adapter.CommonAdapter;
import com.kj.adapter.ViewHolder;
import com.kj.pojo.Bz;
import com.kj.pojo.RetMsg;
import com.kj.pojo.Xiazai;
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
public class TabFragment extends Fragment {

    public static String cid;
    private XListView listView;
    List<Bz> list;
    private int pageNum1 = 1;// 无关键字页数
    private int pageSize = 10;
    private String type = "ckxq";
    int cont = 0;
    Xiazai x;
    String is = "";

    public static Fragment newInstance(String classid, String isdown) {
        TabFragment fragment = new TabFragment();
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
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        pageNum1=1;
        getlist();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
                    final JSONObject j = JSON.parseObject(ret.getData());
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
                            if (Integer.parseInt(j.getString("count")) > pageNum1 * 10)
                                getlist();
                            else
                                listView.stopLoadMore();
                        }
                    });
                    listView.setAdapter(new CommonAdapter<Bz>(getActivity(), list, R.layout.new_bz_item) {
                        @Override
                        public void convert(ViewHolder helper, final Bz item) {
                            helper.setText(R.id.name, item.getStandNum() + "  " + item.getStandName());
//                            helper.setText(R.id.bzmc, item.getStandName());
//                            helper.setText(R.id.fbsj, item.getPublicdate());
//                            helper.setText(R.id.xzcs, item.getDowntime());
//                            LinearLayout ckxq = (LinearLayout) helper.getView(R.id.ckxq);
//                            LinearLayout xz = (LinearLayout) helper.getView(R.id.xz);
//                            ckxq.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    type = "ckxq";
//                                    ActivityBzgl.x = new Xiazai();
//                                    ActivityBzgl.x.setName(item.getStandName());
//                                    ActivityBzgl.x.setTime(TimeUtil.getTime());
//                                    ActivityBzgl.x.setType("标准");
//                                    ActivityBzgl.x.setXid(item.getId());
//                                    ActivityBzgl.x.setXzzt(getArguments().getString("key"));
//                                    SharedPreferencesUtils.setParam(getActivity(), "xztype", "ckxq");
//                                    FileDownloader.start(Url.urls() + item.getAffixname());
//
//                                }
//                            });
//                            xz.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    type = "xz";
//                                    ActivityBzgl.x = new Xiazai();
//                                    ActivityBzgl.x.setName(item.getStandName());
//                                    ActivityBzgl.x.setTime(TimeUtil.getTime());
//                                    ActivityBzgl.x.setType("标准");
//                                    ActivityBzgl.x.setXid(item.getId());
//                                    ActivityBzgl.x.setXzzt(getArguments().getString("key"));
//                                    SharedPreferencesUtils.setParam(getActivity(), "xztype", "zx");
//
//                                    cont = 0;
//                                    if (getArguments().getString("key").equals("0")) {
//                                        FileDownloader.start(Url.urls() + item.getAffixname());
//                                    }
//                                    else
//                                        MyToastUtil.ShowToast(getActivity(), "没有下载权限");
//
//                                }
//                            });

                        }
                    });
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            type = "ckxq";
                            ActivityBzgl.x = new Xiazai();
                            ActivityBzgl.x.setName(list.get(position - 1).getStandName());
                            ActivityBzgl.x.setTime(TimeUtil.getTime());
                            ActivityBzgl.x.setType("标准");
                            ActivityBzgl.x.setXid(list.get(position - 1).getId());
                            ActivityBzgl.x.setXzzt(getArguments().getString("key"));
                            SharedPreferencesUtils.setParam(getActivity(), "xztype", "ckxq");
                            FileDownloader.start(Url.urls() + list.get(position - 1).getAffixname());
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
