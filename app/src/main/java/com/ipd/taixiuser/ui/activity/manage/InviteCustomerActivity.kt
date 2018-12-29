package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import cn.sharesdk.framework.Platform
import com.ipd.taixiuser.R
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.HttpUrl
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.widget.ShareDialog
import com.ipd.taixiuser.widget.ShareDialogClick
import kotlinx.android.synthetic.main.activity_invite_customer.*
import java.util.HashMap


class InviteCustomerActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, customerId: Int) {
            val intent = Intent(activity, InviteCustomerActivity::class.java)
            intent.putExtra("customerId", customerId)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "客户资料"

    override fun getContentLayout(): Int = R.layout.activity_invite_customer

    private val mCustomerId by lazy { intent.getIntExtra("customerId", -1) }


    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
    }

    override fun initListener() {
        tv_share.setOnClickListener {
            val dialogClick = ShareDialogClick()
                    .setShareTitle("泰溪生物")
                    .setShareContent("")
                    .setCallback(object : ShareDialogClick.MainPlatformActionListener {
                        override fun onComplete(platform: Platform?, i: Int, hashMap: HashMap<String, Any>?) {
                            toastShow(true, "分享成功")
                        }

                        override fun onError(platform: Platform?, i: Int, throwable: Throwable?) {
                            toastShow("分享失败")
                        }

                        override fun onCancel(platform: Platform?, i: Int) {
                            toastShow("取消分享")
                        }

                    })
                    .setShareUrl(HttpUrl.HTML_REG + GlobalParam.getInvitationCode())


            val dialog = ShareDialog(mActivity)
            dialog.setShareDialogOnClickListener(dialogClick)
            dialog.show()
        }


    }


}