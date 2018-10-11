package com.ipd.taixiuser.ui.fragment

import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.ui.BaseUIFragment
import com.ipd.taixiuser.ui.activity.manage.MineCustomerActivity
import kotlinx.android.synthetic.main.base_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_manage.view.*

class ManageFragment : BaseUIFragment() {

    override fun initTitle() {
        super.initTitle()
        mHeaderView.tv_title.text = "管理"
    }

    override fun getContentLayout(): Int = R.layout.fragment_manage

    override fun initView(bundle: Bundle?) {
    }

    override fun loadData() {
    }

    override fun initListener() {
        mContentView.tv_customer.setOnClickListener {
            //我的客户
            MineCustomerActivity.launch(mActivity)
        }
    }

}