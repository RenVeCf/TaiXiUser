package com.ipd.taixiuser.event;

import com.ipd.taixiuser.bean.BankCardBean;

public class ChooseBankCardEvent {
    public BankCardBean bankCardBean;

    public ChooseBankCardEvent(BankCardBean bankCardBean) {
        this.bankCardBean = bankCardBean;
    }
}
