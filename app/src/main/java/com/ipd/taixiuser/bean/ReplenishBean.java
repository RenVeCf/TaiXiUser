package com.ipd.taixiuser.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReplenishBean {


    public UserInfoBean userinfo;
    public List<PurchasegoodsBean> purchasegoods;


    public static class PurchasegoodsBean {


        public int id;
        public int goods_id;
        public int fox;
        @SerializedName("case")
        public int caseX;
        public String total;
        public int statue;
        public int update_time;
        public PosgoodsBean posgoods;
        public String goodsname;
        public String name;
        public String img;
        public String price;
        public int is_del;
        public String content;
        public int unit_id;
        public int ctime;
        public int chooseNum = 0;


        public static class PosgoodsBean {
            /**
             * id : 16
             * user_id : 1
             * fox : 8
             * goods_id : 1
             * update_time : 0
             */

            public int id;
            public int user_id;
            public int fox;
            public int goods_id;
            public int update_time;

        }
    }
}
