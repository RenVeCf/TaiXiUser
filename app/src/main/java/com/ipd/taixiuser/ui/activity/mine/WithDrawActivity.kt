package com.ipd.taixiuser.ui.activity.mine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taixiuser.R
import com.ipd.taixiuser.presenter.RetailPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_apply_withdraw.*


class WithDrawActivity : BaseUIActivity(), RetailPresenter.IRetailView {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, WithDrawActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "申请提现"

    override fun getContentLayout(): Int = R.layout.activity_apply_withdraw


    private var mPresenter: RetailPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = RetailPresenter()
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
        cl_bank.setOnClickListener {
            ChooseBankCardActivity.launch(mActivity)
        }

        tv_withdraw.setOnClickListener {
            //确认提现
            finish()
        }

    }


    override fun shipSuccess() {

    }

    override fun shipFail(errMsg: String) {
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