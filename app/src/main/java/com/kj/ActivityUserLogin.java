package com.kj;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.kj.base.MyBaseActivity;
import com.kj.pojo.RetMsg;
import com.kj.pojo.User;
import com.kj.util.HttpUrl;
import com.kj.util.MyApplication;
import com.kj.util.MyToastUtil;
import com.kj.util.NetWorkUtils;
import com.kj.util.SharedPreferencesUtils;
import com.kj.util.StringUtils;
import com.kj.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Administrator on 2017-12-14.
 */

public class ActivityUserLogin extends MyBaseActivity{
    private ImageView regist,login;//登录注册按钮
    private EditText username,pass;
    private CheckBox jzmm;
    Context con=ActivityUserLogin.this;
    @Override
    protected void initUI() {
        setContentView(R.layout.activity_user_login);
        regist=(ImageView)findViewById(R.id.regist);
        login=(ImageView)findViewById(R.id.login);
        username=(EditText)findViewById(R.id.username);
        pass=(EditText)findViewById(R.id.pass);
        jzmm=(CheckBox)findViewById(R.id.jzmm);
        username.setText(SharedPreferencesUtils.getParam(con,"username","").toString());
        pass.setText(SharedPreferencesUtils.getParam(con,"pass","").toString());


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(StringUtils.isEmpty(username.getText().toString()))
                MyToastUtil.ShowToast(con,"帐号不能为空");
            else if(StringUtils.isEmpty(pass.getText().toString()))
                MyToastUtil.ShowToast(con,"密码不能为空");
            else{
                if(!NetWorkUtils.isNetworkConnected(con)){
                    if(SharedPreferencesUtils.getParam(con,"username","").toString().equals(username.getText().toString())&&SharedPreferencesUtils.getParam(con,"password","").toString().equals(pass.getText().toString())){
                        openActivity(MainActivity.class);
                        finish();
                    }else{
                        MyToastUtil.ShowToast(con,"登录失败");
                    }
                }else{

                RequestParams ps=new RequestParams();
                ps.add("username",username.getText().toString());
                ps.add("password",pass.getText().toString());
                ps.add("mobileLogin","true");
                ps.add("__ajax","true");
                UserClient.post(HttpUrl.UserLogin,ps,new AsyncHttpResponseHandler(){
                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);
                        System.out.println(content);
                        RetMsg ret= JSON.parseObject(content,RetMsg.class);
                        if(ret.getCode().equals("0")) {
                            User u = JSON.parseObject(ret.getData(), User.class);
                            MyApplication.getApp().setU(u);
                            MyToastUtil.ShowToast(con, "登录成功");
                            if (jzmm.isChecked()) {
                                SharedPreferencesUtils.setParam(con, "username", username.getText().toString());
                                SharedPreferencesUtils.setParam(con, "pass", pass.getText().toString());
                            }
                            openActivity(MainActivity.class);
                            finish();
                        }else{
                            MyToastUtil.ShowToast(con, "登录失败");
                        }
                    }
                });
            }

            }
            }
        });

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ActivityUserRegist.class);
            }
        });

    }
}
