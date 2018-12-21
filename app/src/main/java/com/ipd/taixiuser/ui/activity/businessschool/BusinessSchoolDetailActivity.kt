package com.ipd.taixiuser.ui.activity.businessschool

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import cn.jzvd.Jzvd
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BusinessDirectoryBean
import com.ipd.taixiuser.imageload.ImageLoader
import com.ipd.taixiuser.platform.http.HttpUrl
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.fragment.businessschool.BusinessSchoolDirectoryFragment
import com.ipd.taixiuser.ui.fragment.businessschool.BusinessSchoolNoteFragment
import com.ipd.taixiuser.ui.fragment.businessschool.BusinessSchoolTalkFragment
import kotlinx.android.synthetic.main.activity_business_school.*

class BusinessSchoolDetailActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, title: String, matterId: Int) {
            val intent = Intent(activity, BusinessSchoolDetailActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("businessSchoolId", matterId)
            activity.startActivity(intent)
        }
    }

    private val mBusinessSchoolId: Int by lazy { intent.getIntExtra("businessSchoolId", 0) }
    override fun getToolbarTitle(): String = intent.getStringExtra("title")

    override fun getContentLayout(): Int = R.layout.activity_business_school

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    private val tabs = arrayListOf("目录", "问答", "笔记")
    override fun loadData() {
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        BusinessSchoolDirectoryFragment.newInstance(mBusinessSchoolId)
                    }
                    1 -> {
                        BusinessSchoolTalkFragment.newInstance(mBusinessSchoolId)
                    }
                    else -> {
                        BusinessSchoolNoteFragment.newInstance(mBusinessSchoolId)
                    }
                }
            }

            override fun getCount(): Int = tabs.size

            override fun getPageTitle(position: Int) = tabs[position]
        }
        tab_layout.setupWithViewPager(view_pager)

    }

    override fun initListener() {
    }

    fun setHeaderInfo(info: BusinessDirectoryBean) {
        when (info.uploadtype) {
            0 -> {//图片
                iv_img.visibility = View.VISIBLE
                video_player.visibility = View.GONE
                ImageLoader.loadNoPlaceHolderImg(mActivity, info.img, iv_img)
            }
            1, 2 -> {//视频
                iv_img.visibility = View.GONE
                video_player.visibility = View.VISIBLE
                video_player.setUp(HttpUrl.VIDEO_URL + info.url, "", Jzvd.SCREEN_WINDOW_NORMAL)
                ImageLoader.loadNoPlaceHolderImg(mActivity, info.img, video_player.thumbImageView)
            }
        }
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