package com.ipd.taixiuser.ui.activity.matter

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

class MatterDetailActivity : BaseUIActivity(), MatterDetailPresenter.MatterDetailView {

    companion object {
        fun launch(activity: Activity, matterId: Int) {
            val intent = Intent(activity, MatterDetailActivity::class.java)
            intent.putExtra("matterId", matterId)
            activity.startActivity(intent)
        }
    }

    private val mMatterId: Int by lazy { intent.getIntExtra("matterId", 0) }
    override fun getToolbarTitle(): String = "素材详情"

    override fun getContentLayout(): Int = R.layout.activity_matter

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
        showProgress()
        mPresenter?.loadMatterDetail(mMatterId)

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


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.share) {
            //分享


            return true
        }

        return super.onOptionsItemSelected(item)
    }

}