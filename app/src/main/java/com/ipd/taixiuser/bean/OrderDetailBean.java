package com.ipd.taixiuser.bean;

import com.google.gson.annotations.SerializedName;

public class OrderDetailBean {

    /**
     * id : 5
     * user_id : 3
     * receiver_id : 3
     * goods_id : 1
     * logistics_id : 0
     * trackingnumber :
     * fox : 0
     * case : 260
     * ctime : 1540957374
     * freight : 0
     * ordercode : 3201810311142547492
     * expence : 17680
     * statue : 1
     * type : 0
     * style : 0
     * is_del : 0
     * applysale : {"id":1,"user_id":3,"ordercode":"3201810311142547492","returntype":0,"fox":0,"reason":"啊实打实大","img":"\u2018\u2019","resultsreason":"","statue":0,"ctime":0}
     * address : {"id":4,"username":"张","phone":"18356635295","area":"","address":"","out_trade_no":"3201810311142547492"}
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
    public ApplysaleBean applysale;
    public AddressBean address;
    public ProductBean goods;

    public static class ApplysaleBean {
        /**
         * id : 1
         * user_id : 3
         * ordercode : 3201810311142547492
         * returntype : 0
         * fox : 0
         * reason : 啊实打实大
         * img : ‘’
         * resultsreason :
         * statue : 0
         * ctime : 0
         */

        public int id;
        public int user_id;
        public String ordercode;
        public int returntype;
        public int fox;
        public String reason;
        public String img;
        public String resultsreason;
        public int statue;
        public int ctime;

    }

    public static class AddressBean {
        /**
         * id : 4
         * username : 张
         * phone : 18356635295
         * area :
         * address :
         * out_trade_no : 3201810311142547492
         */

        public int id;
        public String username;
        public String phone;
        public String area;
        public String address;
        public String out_trade_no;

    }

}
