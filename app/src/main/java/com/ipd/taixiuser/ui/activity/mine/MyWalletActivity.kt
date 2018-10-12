package com.ipd.taixiuser.ui.activity.mine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.ConsumeAdapter
import com.ipd.taixiuser.bean.ConsumeBean
import com.ipd.taixiuser.presenter.RetailPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_my_wallet.*


class MyWalletActivity : BaseUIActivity(), RetailPresenter.IRetailView {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, MyWalletActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "我的钱包"

    override fun getContentLayout(): Int = R.layout.activity_my_wallet


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
        val list = listOf(ConsumeBean(), ConsumeBean(), ConsumeBean(), ConsumeBean(), ConsumeBean(), ConsumeBean(), ConsumeBean())
        consume_recycler_view.adapter = ConsumeAdapter(mActivity, list, {

        })
    }

    override fun initListener() {
        tv_withdraw.setOnClickListener {
            //申请提现
            WithDrawActivity.launch(mActivity)
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