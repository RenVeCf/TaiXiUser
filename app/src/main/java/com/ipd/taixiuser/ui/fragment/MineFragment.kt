package com.ipd.taixiuser.ui.fragment

import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.UserInfoBean
import com.ipd.taixiuser.imageload.ImageLoader
import com.ipd.taixiuser.presenter.UserInfoPresenter
import com.ipd.taixiuser.ui.BaseUIFragment
import com.ipd.taixiuser.utils.StringUtils
import kotlinx.android.synthetic.main.fragment_mine.view.*

class MineFragment : BaseUIFragment(), UserInfoPresenter.IUserInfoView {
    override fun getTitleLayout(): Int = -1
    override fun getContentLayout(): Int = R.layout.fragment_mine


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
    }

    override fun loadData() {
        showProgress()
        mPresenter?.loadUserInfo()

    }

    override fun initListener() {
    }

    override fun getUserInfoSuccess(userInfo: UserInfoBean) {
        showContent()
        ImageLoader.loadAvatar(mActivity, userInfo.avatar, mContentView.civ_avatar)
        mContentView.tv_username.text = userInfo.username
        mContentView.tv_proxy.text = "${StringUtils.getLevelById(userInfo.proxy.toString())}(${userInfo.nickname})"

    }

    override fun getUserInfoFail(errMsg: String) {
        showError(errMsg)
    }


}