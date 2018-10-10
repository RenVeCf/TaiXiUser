package com.ipd.taixiuser.bean;

public class HomeActionBean {
    public int res;
    public int unread;
    public String title;
    public String msg;

    public HomeActionBean(int res, int unread, String title, String msg) {
        this.res = res;
        this.unread = unread;
        this.title = title;
        this.msg = msg;
    }
}
