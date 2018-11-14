package com.ipd.taixiuser.bean;

import com.google.gson.annotations.SerializedName;

public class OrderBean {

    /**
     * id : 3
     * user_id : 3
     * receiver_id : 15
     * goods_id : 1
     * logistics_id : 4
     * trackingnumber : 58758324242342
     * fox : 1
     * case : 0
     * ctime : 2018-10-24
     * freight : 200
     * ordercode : 3201809280552116611
     * expence : 200
     * statue : 0
     * type : 0
     * style : 0
     * is_del : 0
     * goods : {"id":1,"name":"青汁","img":"/pic/20181106/e36c6112741a1a0ad1b174e4b17714d5.png","fox":260,"price":"17680","is_del":0,"content":"萨科技风噶几很舒服v","statue":1,"unit_id":2,"ctime":1541480967,"update_time":1541482749}
     */

    public int id;
    public int user_id;
    public int receiver_id;
    public int goods_id;
    public int logistics_id;
    public String trackingnumber;
    public int fox;
    @SerializedName("case")
    public int caseX;
    public String ctime;
    public String freight;
    public String ordercode;
    public String expence;
    public int statue;
    public int type;
    public int style;
    public int is_del;
    public ProductBean goods;

}
