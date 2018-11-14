package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.ProxyReplenishProductAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.ProductDetailBean
import com.ipd.taixiuser.bean.ReplenishBean
import com.ipd.taixiuser.imageload.ImageLoader
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_normal_replenish.*
import kotlinx.android.synthetic.main.item_replenish_product.view.*

class ProxyReplenishActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, ProxyReplenishActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "补货"

    override fun getContentLayout(): Int = R.layout.activity_normal_replenish

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        showProgress()
        ApiManager.getService().replenishProductList(GlobalParam.getUserIdOrJump())
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<ReplenishBean>>() {
                    override fun _onNext(result: BaseResult<ReplenishBean>) {
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

    private fun setContent(data: ReplenishBean) {
        ImageLoader.loadAvatar(mActivity, data.userinfo.avatar, customer_avatar)
        tv_customer_name.text = data.userinfo.username
        tv_customer_level.text = "${data.userinfo.proxyname}(${data.userinfo.nickname})"
        tv_customer_phone.text = data.userinfo.phone

        product_recycler_view.adapter = ProxyReplenishProductAdapter(mActivity, data.purchasegoods) {

        }

        tv_confirm.setOnClickListener {
            if (data.purchasegoods == null || data.purchasegoods.isEmpty()) {
                toastShow("暂无可补货的商品")
                return@setOnClickListener
            }
            val num = product_recycler_view.layoutManager.findViewByPosition(0).fox_operation_view.getNum()
            if (num == 0) {
                toastShow("补货数量不能为0")
                return@setOnClickListener
            }

            ApiManager.getService().replenish(GlobalParam.getUserIdOrJump(), data.purchasegoods[0].id, num)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<ProductDetailBean>>(mActivity, true) {
                        override fun _onNext(result: BaseResult<ProductDetailBean>) {
                            if (result.code == 200) {
                                toastShow(true, result.msg)
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