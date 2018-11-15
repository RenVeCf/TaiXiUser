package com.ipd.taixiuser.bean;

public class WithdrawDetailBean {

    /**
     * id : 1
     * user_id : 3
     * bank_id : 1
     * money : 1000.00
     * chargefree : 10.00
     * ctime : 1540445144
     * findtime : 0
     * statue : 0
     * bank : {"id":1,"user_id":3,"name":"刷卡见不到","code":"1234567890112","newbank_id":1,"tailnumber":"0112"}
     * state : 处理中
     * showtime : 2018-10-25
     */

    public int id;
    public int user_id;
    public int bank_id;
    public String money;
    public String chargefree;
    public int ctime;
    public int findtime;
    public int statue;
    public BankCardBean bank;
    public String state;
    public String showtime;

}
