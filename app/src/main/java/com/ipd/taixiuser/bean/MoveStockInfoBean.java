package com.ipd.taixiuser.bean;

import java.util.List;

public class MoveStockInfoBean {

    /**
     * user : {"id":38,"username":"刘德华","nickname":"","qrcodes":"/qrcodes/TXkj000038.png","Invitationcode":"TXkj000038","phone":"17601232333","password":"","avatar":"/avatar/avatar.png","weixin":"wexin5","area":"上海/上海/黄浦区","address":"进去咯么默默","proxy":0,"remark":"涂涂抹抹","balance":"0","user_token":"","last_login_time":0,"rongcloud_token":"","update_time":0,"ctime":1542163374,"transfer_id":0,"pos_id":23,"wxopenid":"","qqopenid":"","statue":1,"is_del":0,"proxyname":"零售"}
     * goods : [{"id":1,"name":"青汁","img":"/pic/20181112/3b05ed958ce9538f7cbc018dfeee41e9.png","fox":260,"price":"1730","is_del":0,"content":"萨科技风噶几很舒服v","statue":1,"unit_id":2,"ctime":1541480967,"update_time":1542102916}]
     */

    public CustomerBean user;
    public List<ProductBean> goods;

}
