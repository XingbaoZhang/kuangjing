package com.kj;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.kj.base.MyBaseActivity;
import com.kj.pojo.RetMsg;
import com.kj.pojo.User;
import com.kj.util.HttpUrl;
import com.kj.util.MyApplication;
import com.kj.util.MyToastUtil;
import com.kj.util.NetWorkUtils;
import com.kj.util.PermissionUtils;
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

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO:
//                    Toast.makeText(con, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_GET_ACCOUNTS:
//                    Toast.makeText(con, "Result Permission Grant CODE_GET_ACCOUNTS", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_PHONE_STATE:
//                    Toast.makeText(con, "Result Permission Grant CODE_READ_PHONE_STATE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CALL_PHONE:
//                    Toast.makeText(con, "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CAMERA:
//                    Toast.makeText(con, "Result Permission Grant CODE_CAMERA", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
//                    Toast.makeText(con, "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
//                    Toast.makeText(con, "Result Permission Grant CODE_ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
//                    Toast.makeText(con, "Result Permission Grant CODE_READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
//                    Toast.makeText(con, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_user_login);
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_CAMERA, mPermissionGrant);
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_READ_EXTERNAL_STORAGE, mPermissionGrant);
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, mPermissionGrant);
        regist=(ImageView)findViewById(R.id.regist);
        login=(ImageView)findViewById(R.id.login);
        username=(EditText)findViewById(R.id.username);
        pass=(EditText)findViewById(R.id.pass);
        jzmm=(CheckBox)findViewById(R.id.jzmm);
        username.setText(SharedPreferencesUtils.getParam(con,"username","").toString());
        pass.setText(SharedPreferencesUtils.getParam(con,"pass","").toString());
        if(username.getText().toString().length()>0) {
            jzmm.setChecked(true);
            if(!NetWorkUtils.isNetworkConnected(con)){
                if(SharedPreferencesUtils.getParam(con,"username","").toString().equals(username.getText().toString())&&SharedPreferencesUtils.getParam(con,"pass","").toString().equals(pass.getText().toString())){
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
                            }else{
                                SharedPreferencesUtils.setParam(con, "username", "");
                                SharedPreferencesUtils.setParam(con, "pass","");
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
                    if(SharedPreferencesUtils.getParam(con,"username","").toString().equals(username.getText().toString())&&SharedPreferencesUtils.getParam(con,"pass","").toString().equals(pass.getText().toString())){
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
                            }else{
                                SharedPreferencesUtils.setParam(con, "username", "");
                                SharedPreferencesUtils.setParam(con, "pass","");
                            }
                            openActivity(MainActivity.class);
                            finish();
                        }else{
                            MyToastUtil.ShowToast(con, ret.getMessage());
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
