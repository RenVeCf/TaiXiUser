package com.ipd.taixiuser.ui.fragment

import android.os.Build
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.UserInfoBean
import com.ipd.taixiuser.event.UpdateUserInfoEvent
import com.ipd.taixiuser.imageload.ImageLoader
import com.ipd.taixiuser.presenter.UserInfoPresenter
import com.ipd.taixiuser.ui.BaseUIFragment
import com.ipd.taixiuser.ui.activity.mine.*
import com.ipd.taixiuser.utils.StringUtils
import kotlinx.android.synthetic.main.fragment_mine.view.*
import kotlinx.android.synthetic.main.layout_mine_menu.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MineFragment : BaseUIFragment(), UserInfoPresenter.IUserInfoView {
    override fun getTitleLayout(): Int = -1
    override fun getContentLayout(): Int = R.layout.fragment_mine


    private var mPresenter: UserInfoPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
        mPresenter = UserInfoPresenter()
        mPresenter?.attachView(mActivity, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
        mPresenter?.detachView()
        mPresenter = null
    }


    override fun initView(bundle: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mContentView.card_user.clipToOutline = false
        }
    }

    override fun loadData() {
        showProgress()
        mPresenter?.loadUserInfo()

    }

    override fun initListener() {
        mContentView.civ_avatar.setOnClickListener {
            //用户资料
            UserInfoActivity.launch(mActivity)
        }
        mContentView.ll_wallet.setOnClickListener {
            //我的钱包
            MyWalletActivity.launch(mActivity)
        }
        mContentView.ll_collect.setOnClickListener {
            //我的收藏
            MyCollectActivity.launch(mActivity)
        }
        mContentView.ll_customer_transfer_record.setOnClickListener {
            //客户转移记录
            CustomerTransferRecordActivity.launch(mActivity)
        }
        mContentView.ll_kefu.setOnClickListener {
            //客服
        }
        mContentView.ll_about.setOnClickListener {
            //关于我们
        }
        mContentView.ll_setting.setOnClickListener {
            //设置
            SettingActivity.launch(mActivity)
        }
    }

    override fun getUserInfoSuccess(userInfo: UserInfoBean) {
        showContent()
        ImageLoader.loadAvatar(mActivity, userInfo.avatar, mContentView.civ_avatar)
        mContentView.tv_username.text = userInfo.username
        mContentView.tv_proxy.text = "${userInfo.proxyname}(${userInfo.nickname})"

    }

    override fun getUserInfoFail(errMsg: String) {
        showError(errMsg)
    }


    @Subscribe
    fun onMainEvent(event: UpdateUserInfoEvent) {
        loadData()
    }

}