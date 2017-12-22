package com.kj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.kj.pojo.RetMsg;
import com.kj.pojo.danwei;
import com.kj.tree.DanweiAdapter;
import com.kj.tree.Dept;
import com.kj.tree.Node;
import com.kj.tree.NodeHelper;
import com.kj.tree.NodeTreeAdapter;
import com.kj.util.HttpUrl;
import com.kj.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class ActivityChooseDw extends Activity {
    ListView id_tre;
    List<danwei> list;
    List<Node> data;
    private DanweiAdapter mAdapter;
    Context con = ActivityChooseDw.this;
    private LinkedList<Node> mLinkedList = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_activity);
        id_tre = (ListView) findViewById(R.id.id_tree);
        RequestParams ps = new RequestParams();
        UserClient.post(HttpUrl.GetDanWei, ps, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                System.out.println(content);
                RetMsg ret = JSON.parseObject(content, RetMsg.class);
                if (ret.getCode().equals("0")) {
                    list = JSON.parseArray(ret.getData(), danwei.class);
                    mAdapter = new DanweiAdapter(con, id_tre, mLinkedList);
                    id_tre.setAdapter(mAdapter);
                   data = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        data.add(new Dept(list.get(i).getId(), list.get(i).getpId(), list.get(i).getName()));
                    }
//                addOne(data);
                    mLinkedList.addAll(NodeHelper.sortNodes(data));
                    mAdapter.notifyDataSetChanged();

                }
            }
        });
    }
}
