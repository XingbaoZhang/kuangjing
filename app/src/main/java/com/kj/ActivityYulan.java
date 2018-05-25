package com.kj;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.kj.pojo.RetMsg;
import com.kj.pojo.Xiazai;
import com.kj.util.HttpUrl;
import com.kj.util.MyApplication;
import com.kj.util.MyToastUtil;
import com.kj.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.litepal.crud.DataSupport;

import java.io.File;

/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class ActivityYulan extends Activity implements OnPageChangeListener {
    PDFView pdfView;
    TextView text;
    Button xz;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.yulan);
        text = (TextView) findViewById(R.id.text);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.fromFile(new File(getIntent().getStringExtra("url")))
                //                .pages(0, 0, 0, 0, 0, 0) // 默认全部显示，pages属性可以过滤性显示
                .defaultPage(0)//默认展示第一页
                .onPageChange(ActivityYulan.this)//监听页面切换
                .load();
        xz = (Button) findViewById(R.id.xz);
        if(getIntent().getStringExtra("lx")!=null) {
            xz.setText("发送到邮箱");
            xz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestParams ps = new RequestParams();
                    if(getIntent().getStringExtra("types").equals("标准"))
                    ps.add("type","stand");
                    else
                        ps.add("type","system");
                    ps.add("id",getIntent().getStringExtra("id"));
                    ps.add("emailaddress", MyApplication.getApp().getU().getPloginname());
                    UserClient.get(HttpUrl.SendEmail + ";JSESSIONID=" + MyApplication.getApp().getU().getSessionid(), ps, new AsyncHttpResponseHandler(){
                        @Override
                        public void onSuccess(String content) {
                            super.onSuccess(content);
                            Log.i("发送邮件：",content);
                            RetMsg ret = JSON.parseObject(content, RetMsg.class);
                            if (ret.getCode().equals("0")) {
                                MyToastUtil.ShowToast(ActivityYulan.this,"发送成功");
                            }else{
                                MyToastUtil.ShowToast(ActivityYulan.this,"发送失败");
                            }
                        }
                    });
                }
            });
        }else {
            xz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Xiazai x = (Xiazai) getIntent().getExtras().getSerializable("x");
                    if (x.getXzzt().equals("0")) {
                        if (DataSupport.where("xid=?", x.getXid()).find(Xiazai.class).size() == 0) {
                            x.save();
                            MyToastUtil.ShowToast(ActivityYulan.this, "下载成功,请在下载中心查看");
                        } else {
                            MyToastUtil.ShowToast(ActivityYulan.this, "已经下载了");
                        }
                    } else {
                        MyToastUtil.ShowToast(ActivityYulan.this, "没有下载权限");
                    }
                }
            });
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        text.setText(page + 1 + "/" + pageCount);
    }
}
