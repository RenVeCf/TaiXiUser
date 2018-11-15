package com.ipd.taixiuser.ui.activity.mine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.MoveStockHistoryBean
import com.ipd.taixiuser.event.UpdateBankEvent
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_new_bank_card.*
import org.greenrobot.eventbus.EventBus


class NewBankCardActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, NewBankCardActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "添加银行卡"

    override fun getContentLayout(): Int = R.layout.activity_new_bank_card

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
    }

    override fun initListener() {
        tv_commit.setOnClickListener {
            val customerName = et_customer_name.text.toString().trim()
            val customerNo = et_customer_no.text.toString().trim()
            val bankName = et_bank_name.text.toString().trim()
            if (TextUtils.isEmpty(customerName)) {
                toastShow("请输入持卡人姓名")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(customerNo)) {
                toastShow("请输入银行卡号")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(bankName)) {
                toastShow("请输入所属银行")
                return@setOnClickListener
            }
            ApiManager.getService().addBank(GlobalParam.getUserIdOrJump(), customerName, customerNo, bankName)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<MoveStockHistoryBean>>(mActivity, true) {
                        override fun _onNext(result: BaseResult<MoveStockHistoryBean>) {
                            if (result.code == 200) {
                                EventBus.getDefault().post(UpdateBankEvent())
                                toastShow(true, "添加成功")
                                finish()
                            } else {
                                toastShow(result.msg)
                            }
                        }
                    })


        }


    }


}