package com.ipd.taixiuser.ui.activity.businessschool

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import cn.sharesdk.framework.Platform
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BusinessDetailBean
import com.ipd.taixiuser.event.UpdateBusinessSchoolEvent
import com.ipd.taixiuser.platform.global.AuthUtils
import com.ipd.taixiuser.platform.global.Constant
import com.ipd.taixiuser.platform.http.HttpUrl
import com.ipd.taixiuser.presenter.BusinessSchoolDetailPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.activity.web.WebActivity
import com.ipd.taixiuser.utils.GlideImageLoader
import com.ipd.taixiuser.widget.ShareDialog
import com.ipd.taixiuser.widget.ShareDialogClick
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.activity_business_school.*
import org.greenrobot.eventbus.EventBus
import java.util.HashMap

class BusinessSchoolDetailActivity : BaseUIActivity(), BusinessSchoolDetailPresenter.IBusinessSchoolDetailView {

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

    private var mPresenter: BusinessSchoolDetailPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = BusinessSchoolDetailPresenter()
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
        mPresenter?.loadBusinessSchoolDetail(mBusinessSchoolId)

    }

    override fun initListener() {
    }

    override fun loadBusinessDetailSuccess(info: BusinessDetailBean) {
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
        iv_praise.isSelected = info.is_praise == "1"
        iv_collect.isSelected = info.is_collect == "1"


        ll_praise.setOnClickListener {
            //点赞
            if (!AuthUtils.isLoginAndShowDialog(mActivity)) {
                return@setOnClickListener
            }
            mPresenter?.praise(mBusinessSchoolId)
        }

        ll_collect.setOnClickListener {
            //收藏
            if (!AuthUtils.isLoginAndShowDialog(mActivity)) {
                return@setOnClickListener
            }
            mPresenter?.collect(mBusinessSchoolId)
        }
        ll_share.setOnClickListener {
            //分享
            val dialog = ShareDialog(mActivity)
            dialog.setShareDialogOnClickListener(getShareDialogClick(info))
            dialog.show()
        }

        showContent()
    }

    private fun getShareDialogClick(info: BusinessDetailBean): ShareDialog.ShareDialogOnclickListener {
        var pic = HttpUrl.IMAGE_URL + info.img
        return ShareDialogClick()
                .setShareTitle(info.title)
                .setShareContent(info.content)
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

    override fun loadBusinessDetailFail(errMsg: String) {
        showError(errMsg)
    }

    override fun praiseSuccess() {
        iv_praise.isSelected = !iv_praise.isSelected
    }

    override fun praiseFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun collectSuccess() {
        EventBus.getDefault().post(UpdateBusinessSchoolEvent())
        iv_collect.isSelected = !iv_collect.isSelected
    }


}