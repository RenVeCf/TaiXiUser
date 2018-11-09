package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.EarningParentBean
import com.ipd.taixiuser.bean.ExplainHtmlBean
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.activity.web.WebActivity
import com.ipd.taixiuser.ui.fragment.manage.EarningsFragment
import kotlinx.android.synthetic.main.activity_earnings.*

class EarningsActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, EarningsActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "收益"

    override fun getContentLayout(): Int = R.layout.activity_earnings

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, EarningsFragment()).commit()
    }

    override fun initListener() {
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_earnings_explain, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.earnings_explain) {
            //收益说明
            ApiManager.getService().explainHtml("3")
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<ExplainHtmlBean>>(mActivity, true) {
                        override fun _onNext(result: BaseResult<ExplainHtmlBean>) {
                            if (result.code == 200) {
                                WebActivity.launch(mActivity, WebActivity.HTML, result.data.content, "收益说明")
                            } else {
                                toastShow(result.msg)
                            }
                        }
                    })

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun setPriceInfo(result: BaseResult<EarningParentBean>) {
        tv_month_earnings.text = "${result.data.monthprice}元"
        tv_total_earnings.text = "${result.data.price}元"

    }

}