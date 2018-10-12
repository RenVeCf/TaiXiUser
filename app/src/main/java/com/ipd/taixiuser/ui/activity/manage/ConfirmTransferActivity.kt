package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.utils.MessageDialog
import kotlinx.android.synthetic.main.activity_confirm_transfer.*


class ConfirmTransferActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, ConfirmTransferActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "确认转移"

    override fun getContentLayout(): Int = R.layout.activity_confirm_transfer


    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
    }

    override fun initListener() {
        tv_confirm.setOnClickListener {
            val builder = MessageDialog.Builder(mActivity)
            builder.setTitle("提醒")
                    .setMessage("确认转移客户到18321836626吗?")
                    .setCommit("确认", {
                        it.dismiss()
                        toastShow(true, "转移成功")
                        finish()
                    })
                    .setCancel("再想想", {
                        it.dismiss()
                    }).show()
        }

    }


}