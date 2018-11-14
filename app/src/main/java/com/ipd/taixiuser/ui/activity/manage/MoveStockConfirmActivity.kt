package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.ProductAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.CustomerBean
import com.ipd.taixiuser.bean.MoveStockInfoBean
import com.ipd.taixiuser.bean.ProductBean
import com.ipd.taixiuser.imageload.ImageLoader
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_move_stock_confirm.*

class MoveStockConfirmActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, list: ArrayList<ProductBean>, customerId: Int) {
            val intent = Intent(activity, MoveStockConfirmActivity::class.java)
            intent.putParcelableArrayListExtra("product", list)
            intent.putExtra("customerId", customerId)
            activity.startActivity(intent)
        }
    }


    override fun getToolbarTitle(): String = "移仓确认"

    override fun getContentLayout(): Int = R.layout.activity_move_stock_confirm


    private val mCustomerId: Int by lazy { intent.getIntExtra("customerId", -1) }
    private val mProductList: ArrayList<ProductBean> by lazy { intent.getParcelableArrayListExtra<ProductBean>("product") }
    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        showProgress()
        ApiManager.getService().moveStockCustomerInfo(mCustomerId)
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<MoveStockInfoBean>>() {
                    override fun _onNext(result: BaseResult<MoveStockInfoBean>) {
                        if (result.code == 200) {
                            setContent(result.data.user)
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

    private fun setContent(data: CustomerBean) {
        ImageLoader.loadAvatar(mActivity, data.avatar, customer_avatar)
        tv_customer_name.text = data.username
        tv_customer_level.text = "${data.proxyname}(${data.nickname})"
        tv_customer_phone.text = data.phone

        product_recycler_view.adapter = ProductAdapter(mActivity, mProductList) {

        }
        tv_commit.setOnClickListener {
            ApiManager.getService().confirmMoveStock(GlobalParam.getUserIdOrJump(), mProductList[0].id, mCustomerId, mProductList[0].chooseNum)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<CustomerBean>>(mActivity, true) {
                        override fun _onNext(result: BaseResult<CustomerBean>) {
                            if (result.code == 200) {
                                toastShow(true, "移仓成功")
                                finish()
                            } else {
                                toastShow(result.msg)
                            }
                        }
                    })
        }
    }

    override fun initListener() {

    }


}