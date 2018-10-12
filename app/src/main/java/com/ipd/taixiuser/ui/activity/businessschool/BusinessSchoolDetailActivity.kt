package com.ipd.taixiuser.ui.activity.businessschool

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.MatterDetailBean
import com.ipd.taixiuser.presenter.MatterDetailPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.activity.web.WebActivity
import com.ipd.taixiuser.utils.GlideImageLoader
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.activity_matter.*

class BusinessSchoolDetailActivity : BaseUIActivity(), MatterDetailPresenter.MatterDetailView {

    companion object {
        fun launch(activity: Activity, matterId: Int) {
            val intent = Intent(activity, BusinessSchoolDetailActivity::class.java)
            intent.putExtra("businessSchoolId", matterId)
            activity.startActivity(intent)
        }
    }

    private val mBusinessSchoolId: Int by lazy { intent.getIntExtra("businessSchoolId", 0) }
    override fun getToolbarTitle(): String = "商学院详情"

    override fun getContentLayout(): Int = R.layout.activity_business_school

    private var mPresenter: MatterDetailPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = MatterDetailPresenter()
        mPresenter?.attachView(this, this)
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
//        showProgress()
//        mPresenter?.loadMatterDetail(mBusinessSchoolId)

    }

    override fun initListener() {
    }

    override fun loadMatterDetailSuccess(info: MatterDetailBean) {
        showContent()
        banner.setIndicatorGravity(BannerConfig.RIGHT)
                .setImages(info.banner)
                .setImageLoader(GlideImageLoader())
                .setOnBannerListener {
                    WebActivity.launch(mActivity, WebActivity.URL, info.banner[it].url)
                }
                .start()


        tv_matter_title.text = info.title
        tv_time.text = info.ctime
        tv_content.text = info.content

    }

    override fun loadMatterDetailFail(errMsg: String) {
        showError(errMsg)
    }


}