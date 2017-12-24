package com.kj;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kj.adapter.CommonAdapter;
import com.kj.adapter.ViewHolder;
import com.kj.pojo.Bz;
import com.kj.pojo.RetMsg;
import com.kj.pojo.Zd;
import com.kj.util.HttpUrl;
import com.kj.util.MyApplication;
import com.kj.util.MyToastUtil;
import com.kj.util.UserClient;
import com.kj.view.XListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
    private int pageSize=10;
    public static Fragment newInstance(String classid) {
        ZdFragment fragment = new ZdFragment();
        Bundle args = new Bundle();
        args.putString("key", classid);
        fragment.setArguments(args);


        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.common_list, container, false);
        listView=(XListView) rootView.findViewById(R.id.listView);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        getlist();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void getlist(){
        RequestParams ps = new RequestParams();
        ps.add("PageNo", pageNum1 + "");
        ps.add("PageSize",pageSize+"");
        ps.add("classid",getArguments().getString("key"));
        UserClient.get(HttpUrl.GetZdList+";JSESSIONID="+ MyApplication.getApp().getU().getSessionid(), ps, new AsyncHttpResponseHandler() {
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
                    JSONObject j=JSON.parseObject(ret.getData());
                    List<Zd> ll = JSON.parseArray(
                            j.getString("list"), Zd.class);
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
                    listView.setAdapter(new CommonAdapter<Zd>(getActivity(), list, R.layout.zd_item) {
                        @Override
                        public void convert(ViewHolder helper, Zd item) {
                            helper.setText(R.id.zdbh,item.getId());
                            helper.setText(R.id.zdmc,item.getSysName());
                            helper.setText(R.id.fbsj,item.getPublicdate());
                            helper.setText(R.id.xzcs,item.getDowntime());
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
