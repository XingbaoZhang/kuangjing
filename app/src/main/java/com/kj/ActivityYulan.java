package com.kj;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

import java.io.File;

/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class ActivityYulan extends Activity implements OnPageChangeListener {
    PDFView pdfView;
    TextView text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.yulan);
        text=(TextView)findViewById(R.id.text);
        pdfView=(PDFView)findViewById(R.id.pdfView);
        pdfView.fromFile(new File(getIntent().getStringExtra("url")))
                //                .pages(0, 0, 0, 0, 0, 0) // 默认全部显示，pages属性可以过滤性显示
                .defaultPage(1)//默认展示第一页
                .onPageChange(ActivityYulan.this)//监听页面切换
                .load();

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        text.setText(page+"/"+pageCount);
    }
}
