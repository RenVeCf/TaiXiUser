package com.ipd.taixiuser.ui.activity.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.OrderProductAdapter
import com.ipd.taixiuser.bean.SaleAfterBean
import com.ipd.taixiuser.bean.UpdateOrderEvent
import com.ipd.taixiuser.event.UpdateOrderDetailEvent
import com.ipd.taixiuser.presenter.RequestSaleAfterPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.utils.OrderUtils
import com.ipd.taixiuser.utils.StringUtils
import kotlinx.android.synthetic.main.activity_request_sale_after.*
import org.greenrobot.eventbus.EventBus

class RequestSaleAfterActivity : BaseUIActivity(), RequestSaleAfterPresenter.IRequestSaleAfterView {

    companion object {
        fun launch(activity: Activity, orderCode: String) {
            val intent = Intent(activity, RequestSaleAfterActivity::class.java)
            intent.putExtra("orderCode", orderCode)
            activity.startActivity(intent)
        }
    }

    private val mOrderCode: String by lazy { intent.getStringExtra("orderCode") }
    override fun getToolbarTitle(): String = "申请售后"

    override fun getContentLayout(): Int = R.layout.activity_request_sale_after


    private var mPresenter: RequestSaleAfterPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = RequestSaleAfterPresenter()
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
        mPresenter?.loadInfo(mOrderCode)
    }

    override fun loadInfoSuccess(info: SaleAfterBean) {
        tv_order_code.text = "订单编号:${info.ordercode}"
        product_recycler_view.adapter = OrderProductAdapter(mActivity, info.fox, info.goods) {

        }

        val numList = ArrayList<String>()
        for (index in 1..info.fox) {
            numList.add(index.toString())
        }


        ll_return_num.setOnClickListener {
            val pvOptions = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, option2, options3, v ->
                tv_return_num.text = numList[options1]
            }).setCancelColor(resources.getColor(R.color.black))
                    .setSubmitColor(resources.getColor(R.color.colorPrimaryDark))
                    .build<String>()
            pvOptions.setPicker(numList, null, null)
            pvOptions.show()
        }

        tv_confirm.setOnClickListener {
            //退货方式
            var returnType = 0
            val checkedRadioButtonId = return_type.checkedRadioButtonId
            if (checkedRadioButtonId == R.id.rb_change) returnType = 1
            //数量
            val returnNum = tv_return_num.text.toString().trim()
            if (TextUtils.isEmpty(returnNum)) {
                toastShow("请选择退货数量")
                return@setOnClickListener
            }
            //退款原因
            val returnReason = et_return_reason.text.toString().trim()
            if (TextUtils.isEmpty(returnReason)) {
                toastShow("请输入退款原因")
                return@setOnClickListener
            }
            //图片
            val pictureList = picture_recycler_view.getPictureList()
            var picStr = ""
            var uploadStatus = true
            pictureList.forEach {
                if (TextUtils.isEmpty(it.url)) {
                    uploadStatus = false
                    return@forEach
                }
                picStr += "${it.url}，"
            }
            picStr = StringUtils.fixedPicStr(picStr)

            if (!uploadStatus) {
                toastShow("图片未上传成功，请稍候")
                return@setOnClickListener
            }


            mPresenter?.requestSaleAfter(mOrderCode, returnType, returnNum, returnReason, picStr)
        }

        picture_recycler_view.init()

        showContent()
    }

    override fun loadInfoFail(errMsg: String) {
        showError(errMsg)
    }


    override fun initListener() {
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        picture_recycler_view.onActivityResult(requestCode, resultCode, data)
    }

    override fun requestSuccess() {
        EventBus.getDefault().post(UpdateOrderEvent(intArrayOf(OrderUtils.FINISH, OrderUtils.AFTER_SALE)))
        EventBus.getDefault().post(UpdateOrderDetailEvent())
        toastShow(true, "申请成功")
        finish()
    }

    override fun requestFail(errMsg: String) {
        toastShow(errMsg)
    }


}