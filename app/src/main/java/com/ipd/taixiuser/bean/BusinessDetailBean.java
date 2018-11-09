package com.ipd.taixiuser.bean;

import java.util.List;

public class BusinessDetailBean {

    /**
     * id : 1
     * title : 青汁怎么查真伪
     * img : /pic/20180928/c61b2d0a99147472d0ed5ebf9a4e9284.png
     * content : 氨甲苯酸的骄傲不是大部分几哈奥术爆发很骄傲不到法律
     * ctime : 2018-10-15
     * read : 0
     * collect : 1
     * praise : 2
     * is_collect : 1
     * is_praise : 1
     * banner : [{"id":1,"img":"/pic/20180928/c61b2d0a99147472d0ed5ebf9a4e9284.png","business_id":1},{"id":2,"img":"/pic/20180928/c61b2d0a99147472d0ed5ebf9a4e9284.png","business_id":1},{"id":3,"img":"/pic/20180928/c61b2d0a99147472d0ed5ebf9a4e9284.png","business_id":1}]
     */

    public int id;
    public String title;
    public String img;
    public String content;
    public String ctime;
    public int read;
    public int collect;
    public int praise;
    public String is_collect;
    public String is_praise;
    public List<BannerBean> banner;

}
