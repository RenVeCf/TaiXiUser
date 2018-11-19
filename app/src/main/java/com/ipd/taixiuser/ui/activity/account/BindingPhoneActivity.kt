package com.ipd.taixiuser.ui.activity.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.taixiuser.MainActivity
import com.ipd.taixiuser.R
import com.ipd.taixiuser.presenter.AccountPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_bind_phone.*

class BindingPhoneActivity : BaseUIActivity(), AccountPresenter.IBindingPhoneView, TextWatcher {

    companion object {
        fun launch(activity: Activity, type: Int, openId: String) {
            val intent = Intent(activity, BindingPhoneActivity::class.java)
            intent.putExtra("type", type)
            intent.putExtra("openId", openId)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "绑定手机号"

    override fun getContentLayout(): Int = R.layout.activity_bind_phone


    private val mType by lazy { intent.getIntExtra("type", 0) }
    private val mOpenId by lazy { intent.getStringExtra("openId") }
    private var mPresenter: AccountPresenter<AccountPresenter.IBindingPhoneView>? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = AccountPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null
    }

    override fun initView(bundle: Bundle?) {
        initToolbar()
        btn_login.text = "绑定"
    }

    override fun loadData() {
    }

    override fun initListener() {
        et_phone.addTextChangedListener(this)
        btn_login.setOnClickListener {
            val phone = et_phone.text.toString().trim()
            mPresenter?.bindingPhone(mType, phone, mOpenId)
        }

    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val phone = et_phone.text.toString().trim()
        btn_login.isEnabled = CommonUtils.isMobileNO(phone)
    }

    override fun getSmsCodeSuccess() {

    }

    override fun getSmsCodeFail(errMsg: String) {
    }

    override fun bindingSuccess() {
        toastShow(true, "登录成功")
        val intent = Intent(mActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun bindingFail(errMsg: String) {
        toastShow(errMsg)
    }


}