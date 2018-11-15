package com.ipd.taixiuser.ui.activity.mine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.ApplyWithdrawBean
import com.ipd.taixiuser.bean.BankCardBean
import com.ipd.taixiuser.event.ChooseBankCardEvent
import com.ipd.taixiuser.presenter.WithdrawPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_apply_withdraw.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


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
        EventBus.getDefault().register(this)
        mPresenter = WithdrawPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
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
            if (mBankInfo == null) {
                toastShow("请选择提现的银行")
                return@setOnClickListener
            }
            val money = et_withdraw_money.text.toString().trim()
            mPresenter?.confirmWithdraw(mBankInfo!!.id, money)
        }

    }

    private var mBankInfo: BankCardBean? = null
    override fun loadWithdrawInfoSuccess(info: ApplyWithdrawBean) {
        if (info.bankcard == null || info.bankcard.id == 0) {
            cl_bank.visibility = View.GONE
            cl_empty_bank.visibility = View.VISIBLE

        } else {
            mBankInfo = info.bankcard
            setBankInfo(info.bankcard)
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

    override fun withdrawSuccess() {
        toastShow(true, "申请已提交成功")
        finish()

    }

    override fun withdrawFail(errMsg: String) {
        toastShow(errMsg)
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

    @Subscribe
    fun onMainEvent(event: ChooseBankCardEvent) {
        val bankCardBean = event.bankCardBean
        mBankInfo = bankCardBean

        setBankInfo(bankCardBean)
    }

    private fun setBankInfo(bankInfo: BankCardBean) {
        cl_bank.visibility = View.VISIBLE
        cl_empty_bank.visibility = View.GONE

        tv_bank_name.text = bankInfo.newbank
        tv_bank_no.text = "尾号 ${bankInfo.tailnumber}"
        tv_withdraw_info.text = "提现金额（收取${bankInfo.charge}%手续费）"
    }


}