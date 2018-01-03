package com.kj;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kj.adapter.CommonAdapter;
import com.kj.adapter.ViewHolder;
import com.kj.base.MyBaseActivity;
import com.kj.pojo.Xiazai;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2017-12-15.
 */

public class ActivityXzzx extends MyBaseActivity {
    ListView listView;
    List<Xiazai> list;
    Context con = ActivityXzzx.this;

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_xzzx);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position).getType().equals("手册")) {
                    startActivity(new Intent(con, ActivityScDes.class)
                            .putExtra("name", list.get(position).getName())
                            .putExtra("time", list.get(position).getTime())
                            .putExtra("id",list.get(position).getXid())
                            .putExtra("msg", list.get(position).getMsg())
                            .putExtra("xzcs", "").putExtra("yeshu", "")
                            .putExtra("isdown", "0"));
                } else
                    startActivity(new Intent(con, ActivityYulan.class).putExtra("url", list.get(position).getUrl()));
            }
        });

    }

    @Override
    protected void initData() {

        getdata();
    }

    @Override
    protected void initListener() {

    }


    public void getdata() {
        if (getIntent().getStringExtra("type") == null)
            list = DataSupport.findAll(Xiazai.class);
        else
            list = DataSupport.where("type=?", getIntent().getStringExtra("type")).find(Xiazai.class);
        listView.setAdapter(new CommonAdapter<Xiazai>(con, list, R.layout.xzxz_item) {
            @Override
            public void convert(ViewHolder helper, final Xiazai item) {
                helper.setText(R.id.name, item.getName());
                helper.setText(R.id.time, item.getTime());
                ImageView icon = helper.getView(R.id.icon);
                if (item.getType().equals("标准"))
                    icon.setImageResource(R.mipmap.bz);
                if (item.getType().equals("制度"))
                    icon.setImageResource(R.mipmap.zd);
                if (item.getType().equals("手册"))
                    icon.setImageResource(R.mipmap.sc);

                TextView shanchu = helper.getView(R.id.shanchu);
                shanchu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.delete();
                        getdata();
                    }
                });

            }
        });
    }
}
