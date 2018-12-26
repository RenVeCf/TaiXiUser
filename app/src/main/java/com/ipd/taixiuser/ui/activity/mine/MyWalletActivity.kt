package com.ipd.taixiuser.ui.activity.mine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.ConsumeAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.WalletBean
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_my_wallet.*


class MyWalletActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, MyWalletActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "我的钱包"

    override fun getContentLayout(): Int = R.layout.activity_my_wallet


    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        showProgress()
        ApiManager.getService().myWallet(GlobalParam.getUserIdOrJump(), 1)
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<WalletBean>>() {
                    override fun _onNext(result: BaseResult<WalletBean>) {
                        if (result.code == 200) {
                            tv_balance.text = result.data.balance
                            consume_recycler_view.adapter = ConsumeAdapter(mActivity, result.data.consumption.data) {

                            }

                            rl_more_consume.setOnClickListener {
                                //更多记录
                                MoreConsumeActivity.launch(mActivity)
                            }

                            showContent()
                        } else {
                            showError(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        showError()
                    }
                })

    }

    override fun initListener() {
        tv_withdraw.setOnClickListener {
            //申请提现
            WithDrawActivity.launch(mActivity)
        }

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