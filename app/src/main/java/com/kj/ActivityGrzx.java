package com.kj;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kj.base.MyBaseActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class ActivityGrzx extends MyBaseActivity {
    ImageView grxx;
    LinearLayout xgmm,xzzx;
    TextView tc,qkhc;
    Context con=ActivityGrzx.this;
    @Override
    protected void initUI() {
        setContentView(R.layout.activity_grzx);
        grxx = (ImageView) findViewById(R.id.grxx);
        xgmm=(LinearLayout)findViewById(R.id.xgmm);
        xzzx=(LinearLayout)findViewById(R.id.xzzx);
        tc=(TextView)findViewById(R.id.tc);
        qkhc=(TextView)findViewById(R.id.qkhc);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(con, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("确定要退出?")
                        .setConfirmText("退出")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                finishAll();
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        qkhc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(con, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("确定要清空缓存的文件?")
                        .setConfirmText("清空")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        grxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ActivityGrxx.class);
            }
        });
        xgmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ActivityChangePass.class);
            }
        });
        xzzx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ActivityXzzx.class);
            }
        });
    }
}
