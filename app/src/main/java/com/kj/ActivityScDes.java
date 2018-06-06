package com.kj;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
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
import com.kj.util.Url;
import com.kj.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.litepal.crud.DataSupport;
import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.FileDownloader;
import org.wlf.filedownloader.listener.OnFileDownloadStatusListener;
import org.wlf.filedownloader.listener.simple.OnSimpleFileDownloadStatusListener;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017-12-24.
 */

public class ActivityScDes extends MyBaseActivity {

    TextView name, time, xzcs, yeshu;
    TextView webView;
    ImageView xiazai;
    Button xz;
    Context con = ActivityScDes.this;
    LinearLayout back;

    @Override
    protected void initUI() {
        setContentView(R.layout.sc_des2);
        FileDownloader
                .registerDownloadStatusListener(mOnFileDownloadStatusListener);
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
                    final EditText e=new EditText(ActivityScDes.this);
                    e.setBackgroundResource(R.drawable.edit_bg);
                    new AlertDialog.Builder(ActivityScDes.this).setTitle("输入邮箱").setView(e).setPositiveButton("发送", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if(isEmail(e.getText().toString())) {
                                RequestParams ps = new RequestParams();
                                ps.add("type","book");
                                ps.add("id", getIntent().getStringExtra("id"));
                                ps.add("emailaddress",e.getText().toString());
                                UserClient.get(HttpUrl.SendEmail + ";JSESSIONID=" + MyApplication.getApp().getU().getSessionid(), ps, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(String content) {
                                        super.onSuccess(content);
                                        Log.i("发送邮件：", content);
                                        RetMsg ret = JSON.parseObject(content, RetMsg.class);
                                        if (ret.getCode().equals("0")) {
                                            MyToastUtil.ShowToast(ActivityScDes.this, "发送成功");
                                        } else {
                                            MyToastUtil.ShowToast(ActivityScDes.this, "发送失败");
                                        }
                                    }
                                });
                            }else{
                                MyToastUtil.ShowToast(ActivityScDes.this, "邮箱不正确");
                            }
                        }
                    }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();

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
        webView = (TextView) findViewById(R.id.webview);
        name = (TextView) findViewById(R.id.name);
        time = (TextView) findViewById(R.id.time);
        xzcs = (TextView) findViewById(R.id.xzcs);
        yeshu = (TextView) findViewById(R.id.yeshu);
        xiazai = (ImageView) findViewById(R.id.xiazai);
        name.setText(getIntent().getStringExtra("name"));
        time.setText(getIntent().getStringExtra("time"));
        xzcs.setText(getIntent().getStringExtra("xzcs"));
        yeshu.setText(getIntent().getStringExtra("yeshu"));
        webView.setText(Html.fromHtml(getIntent().getStringExtra("msg")));
        textHtmlClick(ActivityScDes.this,webView);
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
    protected void onDestroy() {
        super.onDestroy();
        FileDownloader.unregisterDownloadStatusListener(mOnFileDownloadStatusListener);
    }

    /**
     * 处理html文本超链接点击事件
     * @param context
     * @param tv
     */
    public static void textHtmlClick(Context context, TextView tv) {
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = tv.getText();
        if (text instanceof Spannable) {
            int end = text.length();
            Spannable sp = (Spannable) text;
            URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.clearSpans();// should clear old spans
            for (URLSpan url : urls) {
                MyURLSpan myURLSpan = new MyURLSpan(url.getURL(), context);
                style.setSpan(myURLSpan, sp.getSpanStart(url),
                        sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            tv.setText(style);
        }
    }
    private static class MyURLSpan extends ClickableSpan {
        private String mUrl;
        private Context mContext;

        MyURLSpan(String url, Context context) {
            mContext = context;
            mUrl = url;
        }

        @Override
        public void onClick(View widget) {
            FileDownloader.start(Url.urls() + mUrl);
        }
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
    private final OnFileDownloadStatusListener mOnFileDownloadStatusListener = new OnSimpleFileDownloadStatusListener() {
        @Override
        public void onFileDownloadStatusRetrying(
                DownloadFileInfo downloadFileInfo, int retryTimes) {
            // 正在重试下载（如果你配置了重试次数，当一旦下载失败时会尝试重试下载），retryTimes是当前第几次重试
            System.out.println("重试下载");
        }

        @Override
        public void onFileDownloadStatusWaiting(
                DownloadFileInfo downloadFileInfo) {
            // 等待下载（等待其它任务执行完成，或者FileDownloader在忙别的操作）
            System.out.println("等待下载");
        }

        @Override
        public void onFileDownloadStatusPreparing(
                DownloadFileInfo downloadFileInfo) {
            // 准备中（即，正在连接资源）
            System.out.println("准备中下载");
        }

        @Override
        public void onFileDownloadStatusPrepared(
                DownloadFileInfo downloadFileInfo) {
            // 已准备好（即，已经连接到了资源）
            System.out.println("准备下载");
        }

        @Override
        public void onFileDownloadStatusDownloading(
                DownloadFileInfo downloadFileInfo, float downloadSpeed,
                long remainingTime) {
            // 正在下载，downloadSpeed为当前下载速度，单位KB/s，remainingTime为预估的剩余时间，单位秒
        }

        @Override
        public void onFileDownloadStatusPaused(DownloadFileInfo downloadFileInfo) {
            // 下载已被暂停
            System.out.println("暂停下载");

        }

        @Override
        public void onFileDownloadStatusCompleted(
                final DownloadFileInfo downloadFileInfo) {
            // 下载完成（整个文件已经全部下载完成）
            // 需要判断文件是否存在
            System.out.println("完成下载");
            String videopath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + File.separator
                    + "FileDownloader/"
                    + downloadFileInfo.getFileName();
            startActivity(new Intent(con, ActivityYulan.class).putExtra("url", videopath).putExtra("dd","dd"));
        }

        @Override
        public void onFileDownloadStatusFailed(String url,
                                               DownloadFileInfo downloadFileInfo,
                                               FileDownloadStatusFailReason failReason) {
            System.out.println("失败下载");
            // 下载失败了，详细查看失败原因failReason，有些失败原因你可能必须关心
            String failType = failReason.getType();
            String failUrl = failReason.getUrl();// 或：failUrl =
            // url，url和failReason.getUrl()会是一样的

            if (FileDownloadStatusFailReason.TYPE_URL_ILLEGAL.equals(failType)) {
                // 下载failUrl时出现url错误
            } else if (FileDownloadStatusFailReason.TYPE_STORAGE_SPACE_IS_FULL
                    .equals(failType)) {
                // 下载failUrl时出现本地存储空间不足
            } else if (FileDownloadStatusFailReason.TYPE_NETWORK_DENIED
                    .equals(failType)) {
                // 下载failUrl时出现无法访问网络
            } else if (FileDownloadStatusFailReason.TYPE_NETWORK_TIMEOUT
                    .equals(failType)) {
                // 下载failUrl时出现连接超时
            } else {
                // 更多错误....
            }

            // 查看详细异常信息
            Throwable failCause = failReason.getCause();// 或：failReason.getOriginalCause()

            // 查看异常描述信息
            String failMsg = failReason.getMessage();// 或：failReason.getOriginalCause().getMessage()
            System.out.println(failMsg);
        }
    };
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
