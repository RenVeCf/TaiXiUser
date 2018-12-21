package com.ipd.taixiuser.ui.activity.businessschool

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.BusinessTalkBean
import com.ipd.taixiuser.event.UpdateBusinessNoteEvent
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_publish_business_note.*
import org.greenrobot.eventbus.EventBus

class PublishBusinessNoteActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity, businessId: Int) {
            val intent = Intent(activity, PublishBusinessNoteActivity::class.java)
            intent.putExtra("businessId", businessId)
            activity.startActivity(intent)
        }

        fun launch(activity: Activity, notesId: Int, content: String) {
            val intent = Intent(activity, PublishBusinessNoteActivity::class.java)
            intent.putExtra("notesId", notesId)
            intent.putExtra("content", content)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "写笔记"

    override fun getContentLayout(): Int = R.layout.activity_publish_business_note

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    private val businessId: Int by lazy { intent.getIntExtra("businessId", -1) }
    private val notesId: Int by lazy { intent.getIntExtra("notesId", -1) }
    private val content by lazy { intent.getStringExtra("content") }
    override fun loadData() {
        if (!TextUtils.isEmpty(content)) et_note_content.setText(content)
    }

    override fun initListener() {
        tv_commit.setOnClickListener {
            val content = et_note_content.text.toString().trim()
            if (TextUtils.isEmpty(content)) {
                toastShow("请输入内容")
                return@setOnClickListener
            }
            if (notesId == -1) {
                ApiManager.getService().businessNoteAdd(GlobalParam.getUserIdOrJump(), businessId, content)
                        .compose(RxScheduler.applyScheduler())
                        .subscribe(object : Response<BaseResult<BusinessTalkBean>>(mActivity, true) {
                            override fun _onNext(result: BaseResult<BusinessTalkBean>) {
                                if (result.code == 200) {
                                    EventBus.getDefault().post(UpdateBusinessNoteEvent())
                                    toastShow(true, "提交成功")
                                    finish()
                                } else {
                                    toastShow(result.msg)
                                }
                            }
                        })
            } else {
                ApiManager.getService().businessNoteEdit(notesId, content)
                        .compose(RxScheduler.applyScheduler())
                        .subscribe(object : Response<BaseResult<BusinessTalkBean>>(mActivity, true) {
                            override fun _onNext(result: BaseResult<BusinessTalkBean>) {
                                if (result.code == 200) {
                                    EventBus.getDefault().post(UpdateBusinessNoteEvent())
                                    toastShow(true, "提交成功")
                                    finish()
                                } else {
                                    toastShow(result.msg)
                                }
                            }
                        })
            }

        }
    }

}