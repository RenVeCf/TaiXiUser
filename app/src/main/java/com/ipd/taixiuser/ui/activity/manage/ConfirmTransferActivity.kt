package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.CustomerBean
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.utils.MessageDialog
import kotlinx.android.synthetic.main.activity_confirm_transfer.*


class ConfirmTransferActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, acceptInfo: CustomerBean, customerId: Int) {
            val intent = Intent(activity, ConfirmTransferActivity::class.java)
            intent.putExtra("customerId", customerId)
            val bundle = Bundle()
            bundle.putSerializable("acceptInfo", acceptInfo)
            intent.putExtras(bundle)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "确认转移"

    override fun getContentLayout(): Int = R.layout.activity_confirm_transfer


    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    private val mCustomerId: Int  by lazy { intent.getIntExtra("customerId", -1) }
    private val mAcceptInfo: CustomerBean  by lazy { intent.extras.getSerializable("acceptInfo") as CustomerBean }
    override fun loadData() {
        tv_accept_account.text = mAcceptInfo.phone
        tv_accept_name.text = mAcceptInfo.username
    }

    override fun initListener() {
        tv_confirm.setOnClickListener {
            val builder = MessageDialog.Builder(mActivity)
            builder.setTitle("提醒")
                    .setMessage("确认转移客户到${mAcceptInfo.phone}吗?")
                    .setCommit("确认") {
                        it.dismiss()
                        confirmTransfer(mAcceptInfo.id, mCustomerId)
                    }
                    .setCancel("再想想") {
                        it.dismiss()
                    }.show()
        }

    }

    private fun confirmTransfer(acceptId: Int, customerId: Int) {
        ApiManager.getService().confirmTransfer(acceptId, GlobalParam.getUserId(), customerId)
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<CustomerBean>>(mActivity, true) {
                    override fun _onNext(result: BaseResult<CustomerBean>) {
                        if (result.code == 200) {
                            toastShow(true,"转移成功")
                            finish()
                        } else {
                            toastShow(result.msg)
                        }
                    }
                })
    }


}