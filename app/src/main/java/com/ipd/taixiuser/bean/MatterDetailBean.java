package com.ipd.taixiuser.bean;

import java.util.List;

public class MatterDetailBean {

    /**
     * id : 1
     * img : /pic/20180919/akjsgdahjdjhada.png
     * title : 大汉军深V噶
     * content : 哈斯VB就会发圣诞节哈北大街安静点吧的酒吧街是爱酱had三把手打死你打女的骄傲按计划大V啊打季后赛的爱打架哈女的叫爱神的箭哈女的啊
     * ctime : 2018-08-19
     * is_del : 0
     * banner : [{"id":1,"img":"/pic/20180919/akjsgdahjdjhada.png","material_id":1},{"id":2,"img":"/pic/20180919/akjsgdahjdjhada.png","material_id":1},{"id":3,"img":"/pic/20180919/akjsgdahjdjhada.png","material_id":1}]
     */

    public int id;
    public String img;
    public String title;
    public String content;
    public String brief;
    public String ctime;
    public int is_del;
    public List<BannerBean> banner;

}
