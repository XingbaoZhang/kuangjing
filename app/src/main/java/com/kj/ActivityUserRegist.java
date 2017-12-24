package com.kj;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kj.base.MyBaseActivity;
import com.kj.pojo.RetMsg;
import com.kj.util.HttpUrl;
import com.kj.util.MyToastUtil;
import com.kj.util.StringUtils;
import com.kj.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


/**
 * Created by Administrator on 2017-12-14.
 */

public class ActivityUserRegist extends MyBaseActivity {

    EditText yhm, zsxm, sjh, mima, qrmm;
    TextView ssdw;
    Button zc;
    Context con = ActivityUserRegist.this;
    String depid = "";

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_user_regist);
        yhm = (EditText) findViewById(R.id.yhm);
        zsxm = (EditText) findViewById(R.id.zsxm);
        sjh = (EditText) findViewById(R.id.sjh);
        mima = (EditText) findViewById(R.id.mima);
        qrmm = (EditText) findViewById(R.id.qrmm);
        ssdw = (TextView) findViewById(R.id.ssdw);
        ssdw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(con, ActivityChooseDw.class), 1);
            }
        });
        zc = (Button) findViewById(R.id.zc);
        zc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(yhm.getText().toString()))
                    MyToastUtil.ShowToast(con, "用户名不能为空");
                else if (StringUtils.isEmpty(zsxm.getText().toString()))
                    MyToastUtil.ShowToast(con, "真实姓名不能为空");
                else if (StringUtils.isEmpty(mima.getText().toString()))
                    MyToastUtil.ShowToast(con, "密码不能为空");
                else if (StringUtils.isEmpty(qrmm.getText().toString()))
                    MyToastUtil.ShowToast(con, "确认密码不能为空");
                else if (StringUtils.isEmpty(depid))
                    MyToastUtil.ShowToast(con, "请选择所属单位");
                else if (mima.getText().toString().equals(qrmm.getText().toString())) {

                    RequestParams ps = new RequestParams();
                    ps.add("ploginname", yhm.getText().toString());
                    ps.add("pusername", zsxm.getText().toString());
                    ps.add("pPwd", mima.getText().toString());
                    ps.add("telphone", sjh.getText().toString());
                    ps.add("depid", depid);
                    UserClient.post(HttpUrl.UserRegist, ps, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(String content) {
                            super.onSuccess(content);
                            RetMsg ret = JSON.parseObject(content, RetMsg.class);
                            if (ret.getCode().equals("200")) {
                                MyToastUtil.ShowToast(con, "注册成功");
                                finish();
                            } else {
                                MyToastUtil.ShowToast(con, "注册失败");
                            }
                        }
                    });

                } else {
                    MyToastUtil.ShowToast(con, "");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            ssdw.setText(data.getStringExtra("name"));
            depid=data.getStringExtra("id");
        }
    }
}
