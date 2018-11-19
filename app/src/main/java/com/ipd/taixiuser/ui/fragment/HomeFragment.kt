package com.ipd.taixiuser.ui.fragment

import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.HomeActionAdapter
import com.ipd.taixiuser.adapter.HomeMenuAdapter
import com.ipd.taixiuser.bean.BannerBean
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.HomeActionBean
import com.ipd.taixiuser.bean.HomeBean
import com.ipd.taixiuser.platform.global.AuthUtils
import com.ipd.taixiuser.platform.global.Constant
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.BaseUIFragment
import com.ipd.taixiuser.ui.activity.home.HomeActionActivity
import com.ipd.taixiuser.ui.activity.home.QuestionActivity
import com.ipd.taixiuser.ui.activity.home.SystemMessageActivity
import com.ipd.taixiuser.ui.activity.web.WebActivity
import com.ipd.taixiuser.utils.GlideImageLoader
import com.youth.banner.BannerConfig
import io.rong.imkit.RongIM
import kotlinx.android.synthetic.main.base_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : BaseUIFragment() {

    override fun initTitle() {
        super.initTitle()
        mHeaderView.tv_title.text = "泰溪科技"
    }

    override fun getContentLayout(): Int = R.layout.fragment_home

    override fun initView(bundle: Bundle?) {

    }

    override fun loadData() {
        showProgress()
        ApiManager.getService().home(GlobalParam.getUserId())
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<HomeBean>>() {
                    override fun _onNext(result: BaseResult<HomeBean>) {
                        if (result.code == 200) {
                            showContent()

                            val data = result.data
                            setBanner(result.data.banner)
                            if (data.headline?.size ?: 0 > 0) {
                                mContentView.tv_headline_text.text = data.headline[0].title
                                mContentView.cl_headline.setOnClickListener {
                                    //头条
                                    WebActivity.launch(mActivity, WebActivity.URL, data.headline[0].url, "泰溪头条")
                                }
                            }

                            mContentView.cl_info.setOnClickListener {
                                //泰溪介绍
                                WebActivity.launch(mActivity, WebActivity.HTML, data.taixi.content, "泰溪介绍")
                            }

                            mContentView.cl_project.setOnClickListener {
                                //科研项目
                                WebActivity.launch(mActivity, WebActivity.HTML, data.keyan.content, "科研项目")
                            }

                            mContentView.menu_recycler_view.adapter = HomeMenuAdapter(mActivity, data.introduction, {
                                WebActivity.launch(mActivity, WebActivity.HTML, it.content, it.title)
                            })


                            val list = arrayListOf(
                                    HomeActionBean(R.mipmap.icon_system_msg, data.lastsysnews.num, "系统消息", data.lastsysnews.content),
                                    HomeActionBean(R.mipmap.icon_sec, 0, "泰溪小秘书", "泰溪小秘书随时为您服务"),
                                    HomeActionBean(R.mipmap.icon_trade, data.transaction.getisreadnum, "交易动态", data.transaction.transaction),
                                    HomeActionBean(R.mipmap.icon_express, data.delivery.shipnum, "发货动态", data.delivery.ship),
                                    HomeActionBean(R.mipmap.icon_team, data.team.groupnum, "团队动态", data.team.group),
                                    HomeActionBean(R.mipmap.icon_customer, data.client.clientnum, "客户动态", data.client.client),
                                    HomeActionBean(R.mipmap.icon_question, 0, "常见问题", "")
                            )
                            mContentView.action_recycler_view.adapter = HomeActionAdapter(mActivity, list) {
                                if (!AuthUtils.isLoginAndShowDialog(mActivity)){
                                    return@HomeActionAdapter
                                }
                                when (it.title) {
                                    "系统消息" -> {
                                        SystemMessageActivity.launch(mActivity)
                                    }
                                    "泰溪小秘书" -> {
                                        RongIM.getInstance().startCustomerServiceChat(mActivity, Constant.KEFU_ID, "在线客服", null)
                                    }
                                    "交易动态" -> {
                                        HomeActionActivity.launch(mActivity, it.title, 0)
                                    }
                                    "发货动态" -> {
                                        HomeActionActivity.launch(mActivity, it.title, 1)
                                    }
                                    "团队动态" -> {
                                        HomeActionActivity.launch(mActivity, it.title, 2)
                                    }
                                    "客户动态" -> {
                                        HomeActionActivity.launch(mActivity, it.title, 3)
                                    }
                                    "常见问题" -> {
                                        QuestionActivity.launch(mActivity)
                                    }
                                }
                            }


                        } else {
                            showError(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        showError()
                    }
                })

    }

    private fun setBanner(banner: List<BannerBean>) {
        mContentView.banner.setIndicatorGravity(BannerConfig.RIGHT)
                .setImages(banner)
                .setImageLoader(GlideImageLoader())
                .setOnBannerListener {
                    WebActivity.launch(mActivity, WebActivity.URL, banner[it].url)
                }
                .start()
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