package com.kj;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.kj.base.MyBaseActivity;
import com.kj.pojo.Bz;
import com.kj.pojo.RetMsg;
import com.kj.pojo.Zd;
import com.kj.util.HttpUrl;
import com.kj.util.MyApplication;
import com.kj.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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


    @Override
    protected void initUI() {
        setContentView(R.layout.activity_zdgl);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.tab_viewpager);
        initView();

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

    public void getbzfl(){
        RequestParams ps=new RequestParams();
        UserClient.get(HttpUrl.GetZdFl+";JSESSIONID="+ MyApplication.getApp().getU().getSessionid(),ps,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                System.out.println(content);
                RetMsg ret= JSON.parseObject(content,RetMsg.class);
                list=JSON.parseArray(ret.getData(),Zd.class);
                mTabTitles= new String[list.size()];
                for(int i=0;i<list.size();i++){
                    mTabTitles[i] = list.get(i).getClassName();
                }
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                //设置tablayout距离上下左右的距离
                //tab_title.setPadding(20,20,20,20);
                mFragmentArrays= new Fragment[list.size()];
                for(int i=0;i<list.size();i++){
                    mFragmentArrays[i] = ZdFragment.newInstance(list.get(i).getId());
                }
                PagerAdapter pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
                viewPager.setAdapter(pagerAdapter);
                //将ViewPager和TabLayout绑定
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }
}
