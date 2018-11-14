package com.ipd.taixiuser.bean;

public class TeamGroupBean {
    public int res;
    public String teamName;
    public String num;
    public String monthnum;
    public int proxy;

    public TeamGroupBean(int res, String teamName, String num,int proxy) {
        this.res = res;
        this.teamName = teamName;
        this.num = num;
        this.proxy = proxy;
    }

    public TeamGroupBean() {
    }
}
