package com.ipd.taixiuser.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.layout_pay_type.view.*

class ChoosePayTypeLayout : LinearLayout {

    object PayType {
        const val ALIPAY = 0
        const val WECHAT = 1
        const val BALANCE = 2
    }

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private fun init() {
        switchPayType(PayType.BALANCE)
        ll_alipay.setOnClickListener {
            if (getPayType() == PayType.ALIPAY) return@setOnClickListener
            switchPayType(PayType.ALIPAY)
        }
        ll_wechat.setOnClickListener {
            if (getPayType() == PayType.WECHAT) return@setOnClickListener
            switchPayType(PayType.WECHAT)
        }
        ll_balance.setOnClickListener {
            if (getPayType() == PayType.BALANCE) return@setOnClickListener
            switchPayType(PayType.BALANCE)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        init()
    }

    fun switchPayType(payType: Int) {
        cb_alipay.isChecked = payType == PayType.ALIPAY
        cb_wechat.isChecked = payType == PayType.WECHAT
        cb_balance.isChecked = payType == PayType.BALANCE
    }

    fun getPayType(): Int {
        if (cb_alipay.isChecked) return PayType.ALIPAY
        if (cb_wechat.isChecked) return PayType.WECHAT
        return PayType.BALANCE
    }


}