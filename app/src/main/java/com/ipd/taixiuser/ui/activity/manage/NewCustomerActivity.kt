package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.CustomerBean
import com.ipd.taixiuser.event.UpdateCustomerEvent
import com.ipd.taixiuser.presenter.CustomerPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.utils.CityUtils
import com.ipd.taixiuser.utils.StringUtils
import kotlinx.android.synthetic.main.activity_new_customer.*
import org.greenrobot.eventbus.EventBus


class NewCustomerActivity : BaseUIActivity(), CustomerPresenter.ICustomerOperationView {

    companion object {
        fun launch(activity: Activity, customerInfo: CustomerBean? = null) {
            val intent = Intent(activity, NewCustomerActivity::class.java)
            if (customerInfo != null) {
                val bundle = Bundle()
                bundle.putSerializable("info", customerInfo)
                intent.putExtras(bundle)
            }
            activity.startActivity(intent)
        }
    }

    private val mCustomerInfo: CustomerBean? by lazy {
        val serializable = intent.extras?.getSerializable("info")
        if (serializable != null) {
            serializable as CustomerBean
        } else {
            null
        }
    }

    override fun getToolbarTitle(): String = if (mCustomerInfo == null) "添加客户" else "修改客户"

    override fun getContentLayout(): Int = R.layout.activity_new_customer


    private var mPresenter: CustomerPresenter<CustomerPresenter.ICustomerOperationView>? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = CustomerPresenter()
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
        if (mCustomerInfo != null) {
            et_customer_name.setText(mCustomerInfo!!.username)
            et_customer_phone.setText(mCustomerInfo!!.phone)
            tv_city.text = mCustomerInfo!!.area
            tv_customer_weixin.setText(mCustomerInfo!!.weixin)
            tv_level.text = StringUtils.getLevelById(mCustomerInfo!!.proxy.toString())
            et_detail_address.setText(mCustomerInfo!!.address)
            tv_customer_remark.setText(mCustomerInfo!!.remark)

            et_customer_name.isEnabled = false
            et_customer_phone.isEnabled = false
            tv_customer_weixin.isEnabled = false

            if (mCustomerInfo!!.proxy > 3){
                ll_level.isEnabled = false
            }

        }
    }

    override fun initListener() {
        ll_city.setOnClickListener {
            CityUtils.getInstance().showSelectDialog(mActivity) { province, city, area ->
                tv_city.text = "${province.title}/${city.title}/${area.title}"
            }
        }


        val inputMethodManager = (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
        ll_level.setOnClickListener {
            inputMethodManager.hideSoftInputFromWindow(tv_customer_weixin.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            val pvOptions = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, option2, options3, v ->
                tv_level.text = StringUtils.addCustomerLevels[options1]
            }).setCancelColor(resources.getColor(R.color.black))
                    .setSubmitColor(resources.getColor(R.color.colorPrimaryDark))
                    .build<String>()
            pvOptions.setPicker(StringUtils.addCustomerLevels, null, null)
            pvOptions.show()
        }

        tv_commit.setOnClickListener {
            val customerName = et_customer_name.text.toString().trim()
            val customerPhone = et_customer_phone.text.toString().trim()
            val customerCity = tv_city.text.toString().trim()
            val customerWeixin = tv_customer_weixin.text.toString().trim()
            val customerLevel = tv_level.text.toString().trim()
            val customerAddress = et_detail_address.text.toString().trim()
            val customerRemark = tv_customer_remark.text.toString().trim()
            mPresenter?.addCustomer(mCustomerInfo?.id
                    ?: -1, customerPhone, customerName, customerWeixin, customerLevel, customerRemark, customerCity, customerAddress)
        }

    }

    override fun addCustomerSuccess() {
        toastShow(true, "添加成功")
        EventBus.getDefault().post(UpdateCustomerEvent())
        finish()
    }

    override fun addCustomerFail(errMsg: String) {
        toastShow(errMsg)
    }


}