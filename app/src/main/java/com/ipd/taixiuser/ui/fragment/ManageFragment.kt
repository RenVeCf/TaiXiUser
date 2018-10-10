package com.ipd.taixiuser.ui.fragment

import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.ui.BaseUIFragment
import kotlinx.android.synthetic.main.base_toolbar.view.*

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
    }

}