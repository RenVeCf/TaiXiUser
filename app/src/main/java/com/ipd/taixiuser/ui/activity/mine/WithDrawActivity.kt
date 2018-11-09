package com.ipd.taixiuser.ui.activity.mine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.ApplyWithdrawBean
import com.ipd.taixiuser.imageload.ImageLoader
import com.ipd.taixiuser.presenter.WithdrawPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_apply_withdraw.*


class WithDrawActivity : BaseUIActivity(), WithdrawPresenter.IWithdrawView {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, WithDrawActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "申请提现"

    override fun getContentLayout(): Int = R.layout.activity_apply_withdraw


    private var mPresenter: WithdrawPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = WithdrawPresenter()
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
        mPresenter?.loadWithdrawInfo()
    }

    override fun initListener() {
        cl_bank.setOnClickListener {
            ChooseBankCardActivity.launch(mActivity)
        }

        cl_empty_bank.setOnClickListener {
            ChooseBankCardActivity.launch(mActivity)
        }

        tv_withdraw.setOnClickListener {
            //确认提现
            finish()
        }

    }

    override fun loadWithdrawInfoSuccess(info: ApplyWithdrawBean) {
        if (info.bankcard == null || info.bankcard.id == 0) {
            cl_bank.visibility = View.GONE
            cl_empty_bank.visibility = View.VISIBLE

        } else {
            cl_bank.visibility = View.VISIBLE
            cl_empty_bank.visibility = View.GONE
            ImageLoader.loadNoPlaceHolderImg(mActivity, info.bankcard.newbank.img, iv_bank_icon)
            tv_bank_name.text = info.bankcard.newbank.bankname
            tv_bank_no.text = "尾号 ${info.bankcard.tailnumber}"
            tv_withdraw_info.text = "提现金额（收取${info.bankcard.newbank.charge}%手续费）"
        }

        tv_balance.text = "可用余额：${info.balance}元"

        tv_all_balance.setOnClickListener {
            et_withdraw_money.setText(info.balance.toString())
        }

        showContent()
    }

    override fun loadWithdrawInfoFail(errMsg: String) {
        showError(errMsg)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_withdraw_progress, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.withdraw_progress) {
            //提现进度
            WithdrawProgressActivity.launch(mActivity)
            return true
        }

        return super.onOptionsItemSelected(item)
    }


}