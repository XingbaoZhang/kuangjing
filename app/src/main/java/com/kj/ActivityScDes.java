package com.kj;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kj.base.MyBaseActivity;
import com.kj.pojo.Xiazai;
import com.kj.util.MyToastUtil;

/**
 * Created by Administrator on 2017-12-24.
 */

public class ActivityScDes extends MyBaseActivity{

    TextView name, time, xzcs, yeshu;
    WebView webView;
    ImageView xiazai;
    Context con=ActivityScDes.this;
    LinearLayout back;
    @Override
    protected void initUI() {
        setContentView(R.layout.sc_des);
        back=(LinearLayout)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                if(getIntent().getStringExtra("isdown").equals("0")) {
                    Xiazai x = new Xiazai();
                    x.setType("手册");
                    x.setXid(getIntent().getStringExtra("id"));
                    x.setName(getIntent().getStringExtra("name"));
                    x.setMsg(getIntent().getStringExtra("msg"));
                    x.setTime(getIntent().getStringExtra("time"));
                    x.save();
                    MyToastUtil.ShowToast(con, "下载成功");
                }else{
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
