package com.ipd.taixiuser.ui.activity.businessschool

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.BusinessTalkBean
import com.ipd.taixiuser.event.UpdateBusinessTalkEvent
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_publish_business_talk.*
import org.greenrobot.eventbus.EventBus

class PublishBusinessTalkActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity, businessId: Int) {
            val intent = Intent(activity, PublishBusinessTalkActivity::class.java)
            intent.putExtra("businessId", businessId)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "提问"

    override fun getContentLayout(): Int = R.layout.activity_publish_business_talk

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    private val businessId: Int by lazy { intent.getIntExtra("businessId", -1) }
    override fun loadData() {

    }

    override fun initListener() {
        tv_commit.setOnClickListener {
            val content = et_question_content.text.toString().trim()
            if (TextUtils.isEmpty(content)) {
                toastShow("请输入您的问题")
                return@setOnClickListener
            }
            ApiManager.getService().businessTalkReply(GlobalParam.getUserIdOrJump(), businessId, content)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<BusinessTalkBean>>(mActivity, true) {
                        override fun _onNext(result: BaseResult<BusinessTalkBean>) {
                            if (result.code == 200) {
                                EventBus.getDefault().post(UpdateBusinessTalkEvent())
                                toastShow("提交成功")
                                finish()
                            } else {
                                toastShow(result.msg)
                            }
                        }
                    })

        }
    }

}