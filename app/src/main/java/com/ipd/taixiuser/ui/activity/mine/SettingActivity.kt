package com.ipd.taixiuser.ui.activity.mine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.ipd.jumpbox.jumpboxlibrary.utils.ThreadUtils
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.WebBean
import com.ipd.taixiuser.platform.global.GlobalApplication
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.activity.account.LostPwdActivity
import com.ipd.taixiuser.ui.activity.web.WebActivity
import com.ipd.taixiuser.utils.CacheUtils
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, SettingActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "设置"

    override fun getContentLayout(): Int = R.layout.activity_setting

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        getCacheSize()
    }

    override fun initListener() {
        rl_change_pwd.setOnClickListener {
            //修改密码
            LostPwdActivity.launch(mActivity, LostPwdActivity.CHANGE_PWD)
        }
        ll_clear_cache.setOnClickListener {
            //清除缓存
            clearCacheSize()
        }
        rl_use_rule.setOnClickListener {
            //使用条款
            ApiManager.getService().mineHtml("1")
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<WebBean>>(mActivity, true) {
                        override fun _onNext(result: BaseResult<WebBean>) {
                            if (result.code == 200) {
                                WebActivity.launch(mActivity, WebActivity.HTML, result.data.content, "使用条款")
                            } else {
                                toastShow(result.msg)
                            }
                        }
                    })
        }
        rl_privacy.setOnClickListener {
            //隐私政策
            ApiManager.getService().mineHtml("2")
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<WebBean>>(mActivity, true) {
                        override fun _onNext(result: BaseResult<WebBean>) {
                            if (result.code == 200) {
                                WebActivity.launch(mActivity, WebActivity.HTML, result.data.content, "隐私政策")
                            } else {
                                toastShow(result.msg)
                            }
                        }
                    })
        }
        tv_exit.setOnClickListener {
            //退出
            GlobalParam.onExitUser()
        }

    }


    private fun getCacheSize() {
        try {
            val size = CacheUtils.getFolderSize(Glide.getPhotoCacheDir(GlobalApplication.mContext))
            val sizeStr = CacheUtils.getFormatSize(size.toDouble())
            tv_cache.text = sizeStr
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun clearCacheSize() {
        try {
            CacheUtils.deleteFolderFile(Glide.getPhotoCacheDir(GlobalApplication.mContext)?.absolutePath, false)
            val size = CacheUtils.getFolderSize(Glide.getPhotoCacheDir(GlobalApplication.mContext))
            val sizeStr = CacheUtils.getFormatSize(size.toDouble())
            ThreadUtils.runOnThread { Glide.get(mActivity).clearDiskCache() }
            tv_cache.text = sizeStr
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}