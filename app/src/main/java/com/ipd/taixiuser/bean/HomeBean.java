package com.ipd.taixiuser.bean;

import java.util.List;

public class HomeBean {

    /**
     * banner : [{"id":1,"img":"/pic/20180919/aksdjhadgjad.png","url":"www.baidu.com","introduction_id":0},{"id":2,"img":"/pic/20180919/aksdjhadgjad.png","url":"www.baidu.com","introduction_id":0},{"id":3,"img":"/pic/20180919/aksdjhadgjad.png","url":"www.baidu.com","introduction_id":0},{"id":4,"img":"/pic/20180919/aksdjhadgjad.png","url":"www.baidu.com","introduction_id":0},{"id":5,"img":"/pic/20180919/aksdjhadgjad.png","url":"www.baidu.com","introduction_id":0}]
     * headline : [{"id":1,"title":"几十分卡飓风","url":"www.baidu.com"}]
     * taixi : {"id":1,"img":"/pic/20180919/aksdjhadgjad.png","title":"泰溪介绍","brief_introduction":"附件八点","content":"爱看不烦解散吧空间大把健康大框架房"}
     * introduction : [{"id":2,"img":"/pic/20180919/aksdjhadgjad.png","title":"商学院","brief_introduction":"","content":"按实际咖啡吧华盛顿巴科技部发送卡寄到哪框架的"},{"id":3,"img":"/pic/20180919/aksdjhadgjad.png","title":"悦八方旅游","brief_introduction":"","content":"安抚va涉及到把科技大部分框架阿爸放假好"},{"id":4,"img":"/pic/20180919/aksdjhadgjad.png","title":"乐多健康管理","brief_introduction":"","content":"阿克金森广发聚划算不大姐夫八十多把科技发布开发阿基本法就开始巴克斯九点半那空间的把控"},{"id":5,"img":"/pic/20180919/aksdjhadgjad.png","title":"众悦电子商务","brief_introduction":"","content":"爱空间是否哈进口到哈市将开放噶几是的哈卡时间段哈会计师发噶框架很大氨基酸部分空间啊是的框架爱蝴蝶结熬枯受淡"}]
     * keyan : {"id":6,"img":"/pic/20180919/aksdjhadgjad.png","title":"科研项目","brief_introduction":"撒娇和v发","content":"啊数据库va话费卡第八届十八届豪放不羁哈市第八届十点半 "}
     * lastsysnews : {"content":"爱说梦话v发哈吉斯大V安徽JSAV大家哈师大  计划va师傅好 ","num":0}
     * transaction : {"transaction":"张韩购买了进货产品青汁","getisreadnum":4}
     * delivery : {"ship":"啊数据库备份过华斯股份","shipnum":2}
     * team : {"group":"案例是借款方哈吉斯返回数据啊","groupnum":2}
     * client : {"client":"阿斯利康干哈苏大哥hi阿U盾还","clientnum":2}
     */

    public TaixiBean taixi;
    public KeyanBean keyan;
    public LastsysnewsBean lastsysnews;
    public TransactionBean transaction;
    public DeliveryBean delivery;
    public TeamBean team;
    public ClientBean client;
    public List<BannerBean> banner;
    public List<HeadlineBean> headline;
    public List<IntroductionBean> introduction;


    public static class TaixiBean {
        /**
         * id : 1
         * img : /pic/20180919/aksdjhadgjad.png
         * title : 泰溪介绍
         * brief_introduction : 附件八点
         * content : 爱看不烦解散吧空间大把健康大框架房
         */

        public int id;
        public String img;
        public String title;
        public String brief_introduction;
        public String content;

    }

    public static class KeyanBean {
        /**
         * id : 6
         * img : /pic/20180919/aksdjhadgjad.png
         * title : 科研项目
         * brief_introduction : 撒娇和v发
         * content : 啊数据库va话费卡第八届十八届豪放不羁哈市第八届十点半 
         */

        public int id;
        public String img;
        public String title;
        public String brief_introduction;
        public String content;

    }

    public static class LastsysnewsBean {
        /**
         * content : 爱说梦话v发哈吉斯大V安徽JSAV大家哈师大  计划va师傅好 
         * num : 0
         */

        public String content;
        public int num;

    }

    public static class TransactionBean {
        /**
         * transaction : 张韩购买了进货产品青汁
         * getisreadnum : 4
         */

        public String transaction;
        public int getisreadnum;

    }

    public static class DeliveryBean {
        /**
         * ship : 啊数据库备份过华斯股份
         * shipnum : 2
         */

        public String ship;
        public int shipnum;

    }

    public static class TeamBean {
        /**
         * group : 案例是借款方哈吉斯返回数据啊
         * groupnum : 2
         */

        public String group;
        public int groupnum;

    }

    public static class ClientBean {
        /**
         * client : 阿斯利康干哈苏大哥hi阿U盾还
         * clientnum : 2
         */

        public String client;
        public int clientnum;

    }


    public static class HeadlineBean {
        /**
         * id : 1
         * title : 几十分卡飓风
         * url : www.baidu.com
         */

        public int id;
        public String title;
        public String url;

    }

    public static class IntroductionBean {
        /**
         * id : 2
         * img : /pic/20180919/aksdjhadgjad.png
         * title : 商学院
         * brief_introduction : 
         * content : 按实际咖啡吧华盛顿巴科技部发送卡寄到哪框架的
         */

        public int id;
        public String img;
        public String title;
        public String brief_introduction;
        public String content;

    }
}
