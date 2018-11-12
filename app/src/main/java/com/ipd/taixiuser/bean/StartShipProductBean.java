package com.ipd.taixiuser.bean;

import com.google.gson.annotations.SerializedName;

public class StartShipProductBean {
    public String goods_id;
    public String fox;
    @SerializedName("case")
    public String box;

    public StartShipProductBean(String goods_id, String fox, String box) {
        this.goods_id = goods_id;
        this.fox = fox;
        this.box = box;
    }
}
