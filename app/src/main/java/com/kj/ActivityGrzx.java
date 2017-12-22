package com.kj;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kj.base.MyBaseActivity;
import com.kj.util.HttpUrl;
import com.kj.util.MyApplication;
import com.kj.util.UserClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class ActivityGrzx extends MyBaseActivity {
    ImageView grxx;
    LinearLayout xgmm,xzzx;
    TextView tc,qkhc;
    Context con=ActivityGrzx.this;
    String names="";
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
                ShowPickDialog();
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
    /**

     */
    private void ShowPickDialog() {

        new AlertDialog.Builder(this)
                .setTitle("选择图片")
                .setNegativeButton("相册", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                        startActivityForResult(intent, 1);

                    }
                })
                .setPositiveButton("拍照", new DialogInterface.OnClickListener() {
                    @SuppressLint("SimpleDateFormat")
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        SimpleDateFormat df = new SimpleDateFormat(
                                "MMddHHmmssSSSS");
                        names = df.format(new Date());
                        Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                                .fromFile(new File(Environment
                                        .getExternalStorageDirectory(), names
                                        + ".jpg")));
                        startActivityForResult(intent, 2);
                    }
                }).show();
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (data != null) {
                    Uri uri = data.getData();
                    cropImage(uri, 500, 500, 4);
                }
                break;
            case 2:
                try {
                    if (resultCode != 0) {
                        Uri uris = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), names + ".jpg"));
                        cropImage(uris, 500, 500, 4);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

                break;
            case 4:
                if (data != null) {
                    Bitmap photo = null;
                    Uri photoUri = data.getData();
                    if (photoUri != null) {
                        photo = BitmapFactory.decodeFile(photoUri.getPath());
                    }
                    if (photo == null) {
                        Bundle extra = data.getExtras();
                        if (extra != null) {
                            photo = (Bitmap) extra.get("data");
                        }
                    }
                    File f = new File(Environment.getExternalStorageDirectory(),
                            "pic.jpg");
                    if (f.exists()) {
                        f.delete();
                    }
                    try {
                        FileOutputStream out = new FileOutputStream(f);
                        photo.compress(Bitmap.CompressFormat.PNG, 90, out);
                        out.flush();
                        out.close();
                        Log.i("image", "已经保存");
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    FileInputStream fis;
                    try {
                        fis = new FileInputStream(
                                Environment.getExternalStorageDirectory()
                                        + "/pic.jpg");

                        grxx.setImageBitmap(BitmapFactory.decodeStream(fis));
                        String picPath = Environment.getExternalStorageDirectory()
                                + "/pic.jpg";
                        RequestParams ps=new RequestParams();
                        ps.put("file",new File(picPath));
                        UserClient.post(HttpUrl.UploadHead+";JSESSIONID="+ MyApplication.getApp().getU().getSessionid(),ps,new AsyncHttpResponseHandler(){
                            @Override
                            public void onSuccess(String content) {
                                super.onSuccess(content);
                                System.out.println(content);

                            }

                            @Override
                            public void onFailure(Throwable error, String content) {
                                super.onFailure(error, content);
                                System.out.println(content);
                            }
                        });

                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    // 截取图片
    public void cropImage(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 180);
        intent.putExtra("outputY", 180);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, requestCode);
    }
}
