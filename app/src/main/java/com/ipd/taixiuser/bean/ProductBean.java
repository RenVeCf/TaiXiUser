package com.ipd.taixiuser.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductBean implements Parcelable {

    /**
     * id : 1
     * name : 青汁
     * img : /pic/20181106/e36c6112741a1a0ad1b174e4b17714d5.png
     * fox : 260
     * price : 17680
     * is_del : 0
     * content : 萨科技风噶几很舒服v
     * statue : 1
     * unit_id : 2
     * ctime : 1541480967
     * update_time : 1541482749
     * unit : 盒
     */

    public int id;
    public String name;
    public String img;
    public int fox;
    public String price;
    public int is_del;
    public String content;
    public int statue;
    public int unit_id;
    public int ctime;
    public int update_time;
    public String unit;
    public int chooseNum = 0;

    @Override
    public String toString() {
        return name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.img);
        dest.writeInt(this.fox);
        dest.writeString(this.price);
        dest.writeInt(this.is_del);
        dest.writeString(this.content);
        dest.writeInt(this.statue);
        dest.writeInt(this.unit_id);
        dest.writeInt(this.ctime);
        dest.writeInt(this.update_time);
        dest.writeString(this.unit);
        dest.writeInt(this.chooseNum);
    }

    public ProductBean() {
    }

    protected ProductBean(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.img = in.readString();
        this.fox = in.readInt();
        this.price = in.readString();
        this.is_del = in.readInt();
        this.content = in.readString();
        this.statue = in.readInt();
        this.unit_id = in.readInt();
        this.ctime = in.readInt();
        this.update_time = in.readInt();
        this.unit = in.readString();
        this.chooseNum = in.readInt();
    }

    public static final Creator<ProductBean> CREATOR = new Creator<ProductBean>() {
        @Override
        public ProductBean createFromParcel(Parcel source) {
            return new ProductBean(source);
        }

        @Override
        public ProductBean[] newArray(int size) {
            return new ProductBean[size];
        }
    };
}
