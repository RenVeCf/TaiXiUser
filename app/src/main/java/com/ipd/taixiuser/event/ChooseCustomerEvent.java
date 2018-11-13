package com.ipd.taixiuser.event;

import com.ipd.taixiuser.bean.CustomerBean;

public class ChooseCustomerEvent {
    public CustomerBean customerInfo;

    public ChooseCustomerEvent(CustomerBean customerInfo) {
        this.customerInfo = customerInfo;
    }
}
