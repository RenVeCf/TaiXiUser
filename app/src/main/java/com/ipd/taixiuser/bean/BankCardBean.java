package com.ipd.taixiuser.bean;

public class BankCardBean {

    /**
     * id : 1
     * user_id : 3
     * name : 刷卡见不到
     * code : 1234567890112
     * newbank_id : 1
     * tailnumber : 0112
     * newbank : {"id":1,"bankname":"中国建设银行","img":"/pic/20181025/acb3e4b2fa44372e7844cf084e466288.png","charge":0.01}
     */

    public int id;
    public int user_id;
    public String name;
    public String code;
    public int newbank_id;
    public String tailnumber;
    public NewbankBean newbank;

    public static class NewbankBean {
        /**
         * id : 1
         * bankname : 中国建设银行
         * img : /pic/20181025/acb3e4b2fa44372e7844cf084e466288.png
         * charge : 0.01
         */

        public int id;
        public String bankname;
        public String img;
        public double charge;

    }
}
