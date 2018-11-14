package com.ipd.taixiuser.bean;

import com.google.gson.annotations.SerializedName;

public class MoveStockHistoryBean {

    /**
     * id : 16
     * user_id : 3
     * receiver_id : 16
     * goods_id : 1
     * fox : 1
     * case : 0
     * ctime : 2018-10-24
     * statue : 1
     * goods : {"id":1,"name":"青汁","img":"/pic/20180928/9053f9b737bed1161028cb3dcbaeed9e.png","fox":100,"case":100,"price":"800","is_del":0,"content":"奥斯卡基本发静安寺的八九点就加不上飞机","update_time":0}
     * username : 张韩
     * usernickname : TX18090000003
     * receivername : null
     * receivernickname : TX18090000016
     */

    public int id;
    public int user_id;
    public int receiver_id;
    public int goods_id;
    public int fox;
    @SerializedName("case")
    public int caseX;
    public String ctime;
    public int statue;
    public ProductBean goods;
    public String username;
    public String usernickname;
    public String receivername;
    public String receivernickname;

}
