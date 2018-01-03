package com.kj;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kj.base.MyBaseActivity;
import com.kj.pojo.RetMsg;
import com.kj.pojo.User;
import com.kj.util.HttpUrl;
import com.kj.util.MyApplication;
import com.kj.util.NetWorkUtils;
import com.kj.util.Url;
import com.kj.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class ActivityGrxx extends MyBaseActivity {
    LinearLayout back;
    TextView pusername, yhm, zsxm, sjh, ssdw;
    Context con = ActivityGrxx.this;
    ImageView head;

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_grxx);
        head = (ImageView) findViewById(R.id.head);
        back = (LinearLayout) findViewById(R.id.back);
        pusername = (TextView) findViewById(R.id.pusername);
        yhm = (TextView) findViewById(R.id.yhm);
        zsxm = (TextView) findViewById(R.id.zsxm);
        sjh = (TextView) findViewById(R.id.sjh);
        ssdw = (TextView) findViewById(R.id.ssdw);
        if (NetWorkUtils.isNetworkConnected(con) && MyApplication.getApp().getU() != null) {
            getuser();
        }

    }


    public void getuser() {
        RequestParams ps = new RequestParams();
        ps.add("id", MyApplication.getApp().getU().getId());
        UserClient.get(HttpUrl.GetUserInfo + ";JSESSIONID=" + MyApplication.getApp().getU().getSessionid(), ps, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                RetMsg ret = JSON.parseObject(content, RetMsg.class);
                User u = JSON.parseObject(ret.getData(), User.class);
                pusername.setText(u.getPusername());
                yhm.setText(u.getPloginname());
                zsxm.setText(u.getPusername());
                sjh.setText(u.getTelphone());
                ssdw.setText(u.getDepname());
                ImageLoader.getInstance().displayImage(Url.urlss() + u.getHeadphoto(), head);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
