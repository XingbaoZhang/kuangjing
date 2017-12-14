package com.kj;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kj.base.MyBaseActivity;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class ActivityBzgl extends MyBaseActivity {

    private TabLayout tabLayout = null;

    private ViewPager viewPager;

    private Fragment[] mFragmentArrays = new Fragment[3];

    private String[] mTabTitles = new String[3];
    LinearLayout cx;//查询

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_bzgl);
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
        mTabTitles[0] = "国家标准";
        mTabTitles[1] = "行业标准";
        mTabTitles[2] = "中国石化企业标准";

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //设置tablayout距离上下左右的距离
        //tab_title.setPadding(20,20,20,20);
        mFragmentArrays[0] = TabFragment.newInstance();
        mFragmentArrays[1] = TabFragment.newInstance();
        mFragmentArrays[2] = TabFragment.newInstance();

        PagerAdapter pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        //将ViewPager和TabLayout绑定
        tabLayout.setupWithViewPager(viewPager);
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
}
