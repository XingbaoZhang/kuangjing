package com.kj.pojo;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class Xiazai extends DataSupport {
    private String type;
    private String name;
    private String time;
    private String url;
    private int id;

    public Xiazai() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
