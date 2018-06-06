package com.kj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    final EditText e=new EditText(ActivityYulan.this);
                    e.setBackgroundResource(R.drawable.edit_bg);
                    new AlertDialog.Builder(ActivityYulan.this).setTitle("输入邮箱").setView(e).setPositiveButton("发送", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if(isEmail(e.getText().toString())) {
                                RequestParams ps = new RequestParams();
                                if (getIntent().getStringExtra("types").equals("标准"))
                                    ps.add("type", "stand");
                                else
                                    ps.add("type", "system");
                                ps.add("id", getIntent().getStringExtra("id"));
                                ps.add("emailaddress",e.getText().toString());
                                UserClient.get(HttpUrl.SendEmail + ";JSESSIONID=" + MyApplication.getApp().getU().getSessionid(), ps, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(String content) {
                                        super.onSuccess(content);
                                        Log.i("发送邮件：", content);
                                        RetMsg ret = JSON.parseObject(content, RetMsg.class);
                                        if (ret.getCode().equals("0")) {
                                            MyToastUtil.ShowToast(ActivityYulan.this, "发送成功");
                                        } else {
                                            MyToastUtil.ShowToast(ActivityYulan.this, "发送失败");
                                        }
                                    }
                                });
                            }else{
                                MyToastUtil.ShowToast(ActivityYulan.this, "邮箱不正确");
                            }
                        }
                    }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();

                }
            });
        }else {

            if(getIntent().getStringExtra("dd")!=null)
                xz.setVisibility(View.GONE);
            xz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Xiazai x = (Xiazai) getIntent().getExtras().getSerializable("x");
                    Log.i("下载状态：",x.getXzzt());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public static boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }
}
