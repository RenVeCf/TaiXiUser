package com.ipd.taixiuser.ui.activity.mine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.WithdrawDetailBean
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_withdraw_progress_detail.*

class WithdrawProgressDetailActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity, withdrawId: Int) {
            val intent = Intent(activity, WithdrawProgressDetailActivity::class.java)
            intent.putExtra("withdrawId", withdrawId)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "进度详情"

    override fun getContentLayout(): Int = R.layout.activity_withdraw_progress_detail


    private val mWithdrawId by lazy { intent.getIntExtra("withdrawId", -1) }
    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        showProgress()
        ApiManager.getService().withdrawDetail(mWithdrawId)
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<WithdrawDetailBean>>() {
                    override fun _onNext(result: BaseResult<WithdrawDetailBean>) {
                        if (result.code == 200) {
                            setContent(result.data)

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

    private fun setContent(data: WithdrawDetailBean) {
        tv_bank_name.text = data.bank.newbank
        tv_money.text = data.money
        tv_charge.text = data.chargefree
        tv_withdraw_info.text = "${data.bank.newbank}（${data.bank.tailnumber}）${data.bank.name}"
        tv_time.text = data.showtime
        tv_status.text = data.state
    }

    override fun initListener() {
    }


}