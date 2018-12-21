package com.ipd.taixiuser.ui.activity.businessschool

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cn.jzvd.Jzvd
import cn.sharesdk.framework.Platform
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BusinessDirectoryBean
import com.ipd.taixiuser.imageload.ImageLoader
import com.ipd.taixiuser.platform.http.HttpUrl
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.widget.ShareDialog
import com.ipd.taixiuser.widget.ShareDialogClick
import kotlinx.android.synthetic.main.activity_business_directory_detail.*
import java.util.*

class BusinessDirectoryDetailActivity : BaseUIActivity() {


    companion object {
        fun launch(activity: Activity, info: BusinessDirectoryBean) {
            val intent = Intent(activity, BusinessDirectoryDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("info", info)
            intent.putExtras(bundle)
            activity.startActivity(intent)
        }
    }

    private val mInfo: BusinessDirectoryBean by lazy { intent.extras.getSerializable("info") as BusinessDirectoryBean }
    override fun getToolbarTitle(): String = mInfo.title

    override fun getContentLayout(): Int = R.layout.activity_business_directory_detail

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        setContent(mInfo)
    }


    override fun initListener() {


    }

    private fun setContent(info: BusinessDirectoryBean) {
        when (info.uploadtype) {
            0 -> {//图片
                banner.visibility = View.VISIBLE
                video_player.visibility = View.GONE
                ImageLoader.loadNoPlaceHolderImg(mActivity, info.img, banner)

            }
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

    private fun getShareDialogClick(info: BusinessDirectoryBean): ShareDialog.ShareDialogOnclickListener {
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