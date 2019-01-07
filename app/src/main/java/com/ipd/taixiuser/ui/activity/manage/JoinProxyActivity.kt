package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.UserInfoBean
import com.ipd.taixiuser.imageload.ImageLoader
import com.ipd.taixiuser.presenter.UserInfoPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.utils.PictureUtils
import kotlinx.android.synthetic.main.activity_join_proxy.*


class JoinProxyActivity : BaseUIActivity(), UserInfoPresenter.IUserInfoView {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, JoinProxyActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "加盟代理"

    override fun getContentLayout(): Int = R.layout.activity_join_proxy


    private var mPresenter: UserInfoPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = UserInfoPresenter()
        mPresenter?.attachView(mActivity, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null
    }


    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        showProgress()
        mPresenter?.loadUserInfo()

    }

    override fun initListener() {

    }

    override fun getUserInfoSuccess(userInfo: UserInfoBean) {
        ImageLoader.loadAvatar(mActivity, userInfo.avatar, civ_avatar)
        tv_proxy_name.text = userInfo.username
        tv_proxy_no.text = "(${userInfo.proxyname}${userInfo.nickname})"
        ImageLoader.loadNoPlaceHolderImg(mActivity, userInfo.qrcodes, iv_code)
        tv_invite_code.text ="邀请码:${userInfo.Invitationcode}"

        tv_save.setOnClickListener {
            tv_save.isEnabled = false
            PictureUtils.savePhotoAndRefreshGallery(mActivity, userInfo.qrcodes) { integer ->
                tv_save.isEnabled = true
                toastShow(integer == 0, if (integer == 0) "保存成功" else "保存失败")
                null
            }
        }

        showContent()
    }

    override fun getUserInfoFail(errMsg: String) {
        showError(errMsg)
    }


}