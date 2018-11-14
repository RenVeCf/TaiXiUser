package com.ipd.taixiuser.ui.activity.mine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import com.ipd.jumpbox.jumpboxlibrary.utils.BitmapUtils
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.UploadResultBean
import com.ipd.taixiuser.bean.UserInfoBean
import com.ipd.taixiuser.event.UpdateUserInfoEvent
import com.ipd.taixiuser.imageload.ImageLoader
import com.ipd.taixiuser.presenter.UserPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.activity.CropActivity
import com.ipd.taixiuser.utils.CityUtils
import com.ipd.taixiuser.utils.PictureChooseUtils
import com.ipd.taixiuser.utils.StringUtils
import com.ipd.taixiuser.utils.UploadUtils
import kotlinx.android.synthetic.main.activity_user_info.*
import org.greenrobot.eventbus.EventBus
import java.io.File


class UserInfoActivity : BaseUIActivity(), UserPresenter.IUserView {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, UserInfoActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "个人资料"

    override fun getContentLayout(): Int = R.layout.activity_user_info


    private var mPresenter: UserPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = UserPresenter()
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
        mPresenter?.loadUserInfo()
    }

    override fun initListener() {
        ll_avatar.setOnClickListener {
            PictureChooseUtils.showDialog(mActivity)
        }

        ll_city.setOnClickListener {
            //城市
            CityUtils.getInstance().showSelectDialog(mActivity) { province, city, area ->
                tv_city.text = "${province.title}/${city.title}/${area.title}"
            }
        }

    }

    override fun loadUserInfoSuccess(info: UserInfoBean) {
        ImageLoader.loadAvatar(mActivity, info.avatar, civ_avatar)
        et_customer_name.setText(info.username)
        et_customer_weixin.setText(info.weixin)
        et_customer_phone.text = info.phone
        tv_level.text = info.proxyname
        tv_leader.text = info.posname
        tv_city.text = info.area
        tv_address.setText(info.address)

        showContent()
    }

    override fun loadUserInfoFail(errMsg: String) {
        showError(errMsg)
    }

    override fun updateUserInfoSuccess() {
        EventBus.getDefault().post(UpdateUserInfoEvent())
        toastShow(true, "修改成功")
        finish()
    }

    override fun updateUserInfoFail(errMsg: String) {
        toastShow(errMsg)
    }


    private var mNewAvatarUrl = ""
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var path: String
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureChooseUtils.PHOTOTAKE) {
                path = CommonUtils.getPhotoSavePath(this, Environment.DIRECTORY_PICTURES) + "/" + PictureChooseUtils.getPhotoSaveName()
                CropActivity.launch(this, path)
            }

            if (requestCode == PictureChooseUtils.PHOTOZOOM) {
                if (data == null)
                    return
                path = BitmapUtils.getInstance().getPath(this, data.data)
                CropActivity.launch(this, path)

            }

            if (requestCode == PictureChooseUtils.IMAGE_COMPLETE) {//截图返回
                if (data == null) return
                path = data.getStringExtra("path")

                val file = File(path)
                if (file.exists()) {
                    UploadUtils.uploadPic(mActivity, true, path, object : UploadUtils.UploadCallback {
                        override fun onProgress(progress: Int) {

                        }

                        override fun uploadSuccess(resultBean: UploadResultBean) {
                            mNewAvatarUrl = resultBean.data[0]
                            ImageLoader.loadAvatar(mActivity, resultBean.data[0], civ_avatar)
                        }

                        override fun uploadFail(errMsg: String) {
                            toastShow(errMsg)
                        }

                    })
                }
            }


        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.save) {
            //保存
            val username = et_customer_name.text.toString().trim()
            val wechat = et_customer_weixin.text.toString().trim()
            val city = tv_city.text.toString().trim()
            val address = tv_address.text.toString().trim()
            mPresenter?.updateUserInfo(avatar = mNewAvatarUrl, username = username, weixin = wechat, area = city, address = address)
            return true
        }

        return super.onOptionsItemSelected(item)
    }


}