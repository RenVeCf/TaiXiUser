package com.ipd.taixiuser.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamStructBean extends CustomerBean {
    @SerializedName("customer")
    private List<TeamStructBean> children;
    private int num;
    private boolean isSelect = false;//是否选中

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public List<TeamStructBean> getChildren() {
        return children;
    }

    public void setChildren(List<TeamStructBean> children) {
        this.children = children;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
