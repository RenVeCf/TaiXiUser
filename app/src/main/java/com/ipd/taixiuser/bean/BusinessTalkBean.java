package com.ipd.taixiuser.bean;

import java.util.List;

public class BusinessTalkBean {

    /**
     * id : 1
     * user_id : 180
     * content : 家啥办法将哈啥办法将
     * primary_directory_id : 1
     * pid : 0
     * ctime : 2018-12-14
     * username : TX _ 000030
     * avatar : /avatar/avatar.png
     * from : 新手说明手册
     * subordinate : [{"id":2,"user_id":180,"content":"家啥办法将哈啥办法将","primary_directory_id":1,"pid":1,"ctime":"2018-12-14","username":"TX _ 000030","avatar":"/avatar/avatar.png"},{"id":3,"user_id":180,"content":"家啥办法将哈啥办法将","primary_directory_id":1,"pid":1,"ctime":"2018-12-14","username":"TX _ 000030","avatar":"/avatar/avatar.png"}]
     */

    public int id;
    public int user_id;
    public String content;
    public int primary_directory_id;
    public int pid;
    public String ctime;
    public String username;
    public String avatar;
    public String from;
    public List<SubordinateBean> subordinate;


    public static class SubordinateBean {
        /**
         * id : 2
         * user_id : 180
         * content : 家啥办法将哈啥办法将
         * primary_directory_id : 1
         * pid : 1
         * ctime : 2018-12-14
         * username : TX _ 000030
         * avatar : /avatar/avatar.png
         */

        public int id;
        public int user_id;
        public String content;
        public int primary_directory_id;
        public int pid;
        public String ctime;
        public String username;
        public String avatar;

    }
}
