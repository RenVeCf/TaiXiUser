package com.ipd.taixiuser.ui.activity.matter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cn.jzvd.Jzvd
import cn.sharesdk.framework.Platform
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.MatterDetailBean
import com.ipd.taixiuser.event.UpdateBusinessSchoolEvent
import com.ipd.taixiuser.event.UpdateCollectListEvent
import com.ipd.taixiuser.imageload.ImageLoader
import com.ipd.taixiuser.platform.global.AuthUtils
import com.ipd.taixiuser.platform.http.HttpUrl
import com.ipd.taixiuser.presenter.MatterDetailPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.utils.GlideImageLoader
import com.ipd.taixiuser.widget.ShareDialog
import com.ipd.taixiuser.widget.ShareDialogClick
import com.youth.banner.BannerConfig
import io.rong.eventbus.EventBus
import kotlinx.android.synthetic.main.activity_matter.*
import java.util.*

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
        ll_praise.setOnClickListener {
            if (!AuthUtils.isLoginAndShowDialog(mActivity)) {
                return@setOnClickListener
            }
            mPresenter?.praise(mMatterId)
        }
        ll_collect.setOnClickListener {
            if (!AuthUtils.isLoginAndShowDialog(mActivity)) {
                return@setOnClickListener
            }
            mPresenter?.collect(mMatterId)
        }
    }

    private var mInfo: MatterDetailBean? = null
    override fun loadMatterDetailSuccess(info: MatterDetailBean) {
        mInfo = info
        showContent()
        iv_praise.isSelected = info.is_praise == "1"
        iv_collect.isSelected = info.is_collect == "1"

        when (info.uploadtype) {
            0 -> {//图片
                banner.visibility = View.VISIBLE
                video_player.visibility = View.GONE
                banner.setIndicatorGravity(BannerConfig.RIGHT)
                        .setImages(info.banner)
                        .setImageLoader(GlideImageLoader())
                        .setOnBannerListener {
                            //                    WebActivity.launch(mActivity, WebActivity.URL, info.banner[it].url)
                        }
                        .start()

            }
//            1 -> {//音频
//
//            }
            1, 2 -> {//视频
                banner.visibility = View.GONE
                video_player.visibility = View.VISIBLE
                video_player.setUp(HttpUrl.VIDEO_URL + info.url, "", Jzvd.SCREEN_WINDOW_NORMAL)
                ImageLoader.loadNoPlaceHolderImg(mActivity, info.img, video_player.thumbImageView)
                video_player.startButton.performClick()
            }
        }
        tv_matter_title.text = info.title
        tv_time.text = info.ctime
        web_view.loadData(info.content, "text/html; charset=UTF-8", null)
    }

    override fun loadMatterDetailFail(errMsg: String) {
        showError(errMsg)
    }


    override fun praiseSuccess() {
        iv_praise.isSelected = !iv_praise.isSelected
    }

    override fun praiseFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun collectSuccess() {
        EventBus.getDefault().post(UpdateCollectListEvent())
        iv_collect.isSelected = !iv_collect.isSelected
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.share) {
            //分享
            if (mInfo == null) return false

            val dialog = ShareDialog(mActivity)
            dialog.setShareDialogOnClickListener(getShareDialogClick(mInfo!!))
            dialog.show()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getShareDialogClick(info: MatterDetailBean): ShareDialog.ShareDialogOnclickListener {
        var pic = HttpUrl.IMAGE_URL + info.img
        return ShareDialogClick()
                .setShareTitle(info.title)
                .setShareContent(info.brief)
                .setShareLogoUrl(pic)
                .setCallback(object : ShareDialogClick.MainPlatformActionListener {
                    override fun onComplete(platform: Platform?, i: Int, hashMap: HashMap<String, Any>?) {
                        toastShow(true, "分享成功")
                    }

                    override fun onError(platform: Platform?, i: Int, throwable: Throwable?) {
                        toastShow("分享失败")
                    }

                    override fun onCancel(platform: Platform?, i: Int) {
                        toastShow("取消分享")
                    }

                })
                .setShareUrl(HttpUrl.HTML_REG)
    }


    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }


}