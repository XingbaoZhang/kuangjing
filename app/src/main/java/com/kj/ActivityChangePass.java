package com.kj;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.kj.base.MyBaseActivity;
import com.kj.pojo.RetMsg;
import com.kj.util.HttpUrl;
import com.kj.util.MyApplication;
import com.kj.util.MyToastUtil;
import com.kj.util.NetWorkUtils;
import com.kj.util.StringUtils;
import com.kj.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class ActivityChangePass extends MyBaseActivity {
    EditText ymm, mima, qrmm;
    Button bcmm;

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_changepass);
        ymm = (EditText) findViewById(R.id.ymm);
        mima = (EditText) findViewById(R.id.mima);
        qrmm = (EditText) findViewById(R.id.qrmm);
        bcmm = (Button) findViewById(R.id.bcmm);
        bcmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(ymm.getText().toString()) || StringUtils.isEmpty(mima.getText().toString()) || StringUtils.isEmpty(qrmm.getText().toString())) {
                    MyToastUtil.ShowToast(ActivityChangePass.this, "信息不能空");
                } else if (mima.getText().toString().equals(qrmm.getText().toString())) {

                    if (NetWorkUtils.isNetworkConnected(ActivityChangePass.this)) {
                        RequestParams ps = new RequestParams();
                        ps.add("oldpwd", ymm.getText().toString());
                        ps.add("newpwd", mima.getText().toString());
                        UserClient.get(HttpUrl.PassWordEdit + ";JSESSIONID=" + MyApplication.getApp().getU().getSessionid(), ps, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(String content) {
                                super.onSuccess(content);
                                RetMsg ret = JSON.parseObject(content, RetMsg.class);
                                if (ret.getCode().equals("0"))
                                    MyToastUtil.ShowToast(ActivityChangePass.this, "密码更新成功，下次登录生效");
                                finish();
                            }
                        });
                    }
                } else {
                    MyToastUtil.ShowToast(ActivityChangePass.this, "两次密码输入不一致");
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
