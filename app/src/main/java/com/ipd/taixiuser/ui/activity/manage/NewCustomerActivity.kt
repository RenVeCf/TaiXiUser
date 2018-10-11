package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.ipd.taixiuser.R
import com.ipd.taixiuser.event.UpdateCustomerEvent
import com.ipd.taixiuser.presenter.CustomerPresenter
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.utils.CityUtils
import com.ipd.taixiuser.utils.StringUtils
import kotlinx.android.synthetic.main.activity_new_customer.*
import org.greenrobot.eventbus.EventBus


class NewCustomerActivity : BaseUIActivity(), CustomerPresenter.ICustomerOperationView {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, NewCustomerActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "添加客户"

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
    }

    override fun initListener() {
        ll_city.setOnClickListener {
            CityUtils.getInstance().showSelectDialog(mActivity, { province, city, area ->
                tv_city.text = "${province.title}/${city.title}/${area.title}"
            })
        }

        ll_level.setOnClickListener {
            val pvOptions = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, option2, options3, v ->
                tv_level.text = StringUtils.levels[options1]
            }).setCancelColor(resources.getColor(R.color.black))
                    .setSubmitColor(resources.getColor(R.color.colorPrimaryDark))
                    .build<String>()
            pvOptions.setPicker(StringUtils.levels, null, null)
            pvOptions.show()
        }

        tv_commit.setOnClickListener {
            val customerName = et_customer_name.text.toString().trim()
            val customerPhone = et_customer_phone.text.toString().trim()
            val customerCity = tv_city.text.toString().trim()
            val customerWeixin = tv_customer_weixin.text.toString().trim()
            val customerLevel = tv_level.text.toString().trim()
            val customerRemark = tv_customer_remark.text.toString().trim()
            mPresenter?.addCustomer(customerPhone, customerName, customerWeixin, customerLevel, customerRemark, customerCity)
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