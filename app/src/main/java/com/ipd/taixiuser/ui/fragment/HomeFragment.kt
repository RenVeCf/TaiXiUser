package com.ipd.taixiuser.ui.fragment

import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.HomeActionAdapter
import com.ipd.taixiuser.bean.HomeActionBean
import com.ipd.taixiuser.ui.BaseUIFragment
import com.ipd.taixiuser.utils.GlideImageLoader
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.base_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : BaseUIFragment() {

    override fun initTitle() {
        super.initTitle()
        mHeaderView.tv_title.text = "泰溪科技"
    }

    override fun getContentLayout(): Int = R.layout.fragment_home

    private val images = arrayListOf(R.mipmap.banner, R.mipmap.banner, R.mipmap.banner)
    override fun initView(bundle: Bundle?) {
        mContentView.banner.setIndicatorGravity(BannerConfig.RIGHT)
                .setImages(images)
                .setImageLoader(GlideImageLoader())
                .start()
    }

    override fun loadData() {
        val list = arrayListOf(
                HomeActionBean(R.mipmap.icon_system_msg, 33, "系统消息", "恭喜您注册成为泰溪科技app的用户"),
                HomeActionBean(R.mipmap.icon_sec, 0, "泰溪小秘书", "泰溪小秘书随时为您服务"),
                HomeActionBean(R.mipmap.icon_trade, 0, "交易动态", "您已完成15笔交易"),
                HomeActionBean(R.mipmap.icon_express, 0, "发货动态", "您的货品已发出"),
                HomeActionBean(R.mipmap.icon_team, 3, "团队动态", "欢迎用户xxx加入您的团队"),
                HomeActionBean(R.mipmap.icon_customer, 0, "客户动态", "您的客户xxx下单啦"),
                HomeActionBean(R.mipmap.icon_question, 0, "常见问题", "")
        )
        mContentView.action_recycler_view.adapter = HomeActionAdapter(mActivity, list, {

        })
    }

    override fun initListener() {
    }

    override fun onStart() {
        super.onStart()
        mContentView.banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        mContentView.banner.stopAutoPlay()
    }

}