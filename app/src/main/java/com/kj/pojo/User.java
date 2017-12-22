package com.kj.pojo;

/**用户信息
 *
 * Created by Administrator on 2017/12/21 0021.
 */

public class User {
    private String loginName;
    private String id;
    private String name;
    private String mobileLogin;
    private String sessionid;

    public User() {
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileLogin() {
        return mobileLogin;
    }

    public void setMobileLogin(String mobileLogin) {
        this.mobileLogin = mobileLogin;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
}
