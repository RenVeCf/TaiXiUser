package com.ipd.taixiuser.utils;

import com.ipd.taixiuser.bean.OrderCategoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jumpbox on 2017/12/11.
 */

public class OrderUtils {
    public static final int WAIT_SEND = 0, WAIT_RECEIVE = 1, CANCELED = 2, AFTER_SALE = 3;

    public static List<OrderCategoryBean> buildOrderCategory() {
        List<OrderCategoryBean> list = new ArrayList<>();
        list.add(new OrderCategoryBean("待发货", WAIT_SEND));
        list.add(new OrderCategoryBean("已发货", WAIT_RECEIVE));
        list.add(new OrderCategoryBean("已取消", CANCELED));
        list.add(new OrderCategoryBean("申请售后", AFTER_SALE));
        return list;
    }


    public interface OrderDetailBtnClickListener {

    }
}
