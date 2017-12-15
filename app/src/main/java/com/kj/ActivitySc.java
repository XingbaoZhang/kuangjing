package com.kj;

import android.widget.ListView;

import com.kj.base.MyBaseActivity;
import com.kj.tree.Dept;
import com.kj.tree.Node;
import com.kj.tree.NodeHelper;
import com.kj.tree.NodeTreeAdapter;
import com.kj.view.SlidingMenu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017-12-15.
 */

public class ActivitySc extends MyBaseActivity{
    private SlidingMenu mSm;
    private ListView mListView;
    private NodeTreeAdapter mAdapter;
    private LinkedList<Node> mLinkedList = new LinkedList<>();
    @Override
    protected void initUI() {
        setContentView(R.layout.activity_sc);
        mListView = (ListView)findViewById(R.id.id_tree);
        mAdapter = new NodeTreeAdapter(this,mListView,mLinkedList);
        mListView.setAdapter(mAdapter);
        List<Node> data = new ArrayList<>();
        addOne(data);
        mLinkedList.addAll(NodeHelper.sortNodes(data));
        mAdapter.notifyDataSetChanged();
        mSm = (SlidingMenu) findViewById(R.id.sliding_menu);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
    private void addOne(List<Node> data){
        /*data.add(new Dept(1, 0, "总公司"));//可以直接注释掉此项，即可构造一个森林*/
        data.add(new Dept(2, 1, "目录"));
        data.add(new Dept(3, 1, "简介"));
        data.add(new Dept(4, 1, "第一部分"));
        data.add(new Dept(5, 1, "第二部分"));
        data.add(new Dept(6, 1, "第三部分"));
        data.add(new Dept(7, 1, "第四部分"));
        data.add(new Dept(21, 4, "类别1"));
               data.add(new Dept(211, 21, "类别11"));
        data.add(new Dept(2111, 211, "类别111"));
               data.add(new Dept(212, 21, "类别12"));
               data.add(new Dept(213, 21, "类别13"));
        data.add(new Dept(22, 4, "类别2"));
        data.add(new Dept(23, 4, "类别2"));
        data.add(new Dept(24, 4, "类别2"));
        data.add(new Dept(25, 4, "类别2"));
        data.add(new Dept(51, 5, "类别1"));
        data.add(new Dept(52, 5, "类别2"));
        data.add(new Dept(53, 5, "类别2"));
        data.add(new Dept(54, 5, "类别2"));
        data.add(new Dept(55, 5, "类别2"));
    }
}
