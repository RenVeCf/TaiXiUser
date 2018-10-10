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
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseUIActivity(), AccountPresenter.ILoginView, TextWatcher {


    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "登录"

    override fun getContentLayout(): Int = R.layout.activity_login


    private var mPresenter: AccountPresenter<AccountPresenter.ILoginView>? = null
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
    }

    override fun loadData() {
    }

    override fun initListener() {
        et_phone.addTextChangedListener(this)
        et_password.addTextChangedListener(this)

        tv_register.setOnClickListener { RegisterActivity.launch(mActivity) }//注册
        tv_forget.setOnClickListener { LostPwdActivity.launch(mActivity) }//忘记密码
        btn_login.setOnClickListener {
            val phone = et_phone.text.toString().trim()
            val password = et_password.text.toString().trim()
            mPresenter?.login(phone, password)
        }
    }

    override fun loginSuccess() {
        toastShow("登录成功")
        MainActivity.launch(mActivity)
        finish()
    }

    override fun loginFail(errMsg: String) {
        toastShow(errMsg)
    }


    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val phone = et_phone.text.toString().trim()
        val password = et_password.text.toString().trim()
        btn_login.isEnabled = CommonUtils.isMobileNO(phone) &&
                CommonUtils.passwordIsLegal(password)
    }

}