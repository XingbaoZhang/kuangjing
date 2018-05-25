package com.kj;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kj.base.MyBaseActivity;
import com.kj.pojo.RetMsg;
import com.kj.pojo.Xiazai;
import com.kj.util.HttpUrl;
import com.kj.util.MyApplication;
import com.kj.util.MyToastUtil;
import com.kj.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017-12-24.
 */

public class ActivityScDes extends MyBaseActivity {

    TextView name, time, xzcs, yeshu;
    WebView webView;
    ImageView xiazai;
    Button xz;
    Context con = ActivityScDes.this;
    LinearLayout back;

    @Override
    protected void initUI() {
        setContentView(R.layout.sc_des);
        back = (LinearLayout) findViewById(R.id.ddd);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        xz = (Button) findViewById(R.id.xz);
        if (getIntent().getStringExtra("xz") != null && getIntent().getStringExtra("xz").equals("1")) {
            xz.setText("发送到邮箱");
            xz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestParams ps = new RequestParams();
                    ps.add("type","book");
                    ps.add("id",getIntent().getStringExtra("id"));
                    ps.add("emailaddress", MyApplication.getApp().getU().getPloginname());
                    UserClient.get(HttpUrl.SendEmail + ";JSESSIONID=" + MyApplication.getApp().getU().getSessionid(), ps, new AsyncHttpResponseHandler(){
                        @Override
                        public void onSuccess(String content) {
                            super.onSuccess(content);
                            Log.i("发送邮件：",content);
                            RetMsg ret = JSON.parseObject(content, RetMsg.class);
                            if (ret.getCode().equals("0")) {
                                MyToastUtil.ShowToast(ActivityScDes.this,"发送成功");
                            }else{
                                MyToastUtil.ShowToast(ActivityScDes.this,"发送失败");
                            }
                        }
                    });
                }
            });
        } else {
            xz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getIntent().getStringExtra("isdown").equals("0")) {
                        Xiazai x = new Xiazai();
                        x.setType("手册");
                        x.setXid(getIntent().getStringExtra("id"));
                        x.setName(getIntent().getStringExtra("name"));
                        x.setMsg(getIntent().getStringExtra("msg"));
                        x.setTime(getIntent().getStringExtra("time"));
                        if (DataSupport.where("xid=?", x.getXid()).find(Xiazai.class).size() == 0) {
                            x.save();
                            MyToastUtil.ShowToast(ActivityScDes.this, "下载成功,请在下载中心查看");
                        } else {
                            MyToastUtil.ShowToast(ActivityScDes.this, "已经下载了");
                        }
                    } else {
                        MyToastUtil.ShowToast(con, "没有权限");
                    }
                }
            });
        }
        webView = (WebView) findViewById(R.id.webview);
        name = (TextView) findViewById(R.id.name);
        time = (TextView) findViewById(R.id.time);
        xzcs = (TextView) findViewById(R.id.xzcs);
        yeshu = (TextView) findViewById(R.id.yeshu);
        xiazai = (ImageView) findViewById(R.id.xiazai);
        name.setText(getIntent().getStringExtra("name"));
        time.setText(getIntent().getStringExtra("time"));
        xzcs.setText(getIntent().getStringExtra("xzcs"));
        yeshu.setText(getIntent().getStringExtra("yeshu"));
        webView.loadDataWithBaseURL(null, getIntent().getStringExtra("msg"), "text/html", "utf-8", null);
        xiazai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getStringExtra("isdown").equals("0")) {
                    Xiazai x = new Xiazai();
                    x.setType("手册");
                    x.setXid(getIntent().getStringExtra("id"));
                    x.setName(getIntent().getStringExtra("name"));
                    x.setMsg(getIntent().getStringExtra("msg"));
                    x.setTime(getIntent().getStringExtra("time"));
                    if (DataSupport.where("xid=?", x.getXid()).find(Xiazai.class).size() == 0) {
                        x.save();
                        MyToastUtil.ShowToast(ActivityScDes.this, "下载成功,请在下载中心查看");
                    } else {
                        MyToastUtil.ShowToast(ActivityScDes.this, "已经下载了");
                    }
                } else {
                    MyToastUtil.ShowToast(con, "没有权限");
                }
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
