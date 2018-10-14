package com.ipd.taixiuser.ui.fragment

import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.ui.BaseUIFragment
import com.ipd.taixiuser.ui.activity.manage.*
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
        mContentView.tv_retail.setOnClickListener {
            //零售发货
            RetailActivity.launch(mActivity)
        }
        mContentView.tv_team.setOnClickListener {
            //我的团队
            MineTeamActivity.launch(mActivity)
        }
        mContentView.tv_store.setOnClickListener {
            //商城
            StoreActivity.launch(mActivity)
        }
        mContentView.tv_replenish.setOnClickListener {
            //补货
//            LeaderReplenishActivity.launch(mActivity)
            ProxyReplenishActivity.launch(mActivity)
        }
        mContentView.tv_earnings.setOnClickListener {
            //收益
            EarningsActivity.launch(mActivity)
        }
        mContentView.tv_factory_ship.setOnClickListener {
            //工厂代发
            FactoryShipActivity.launch(mActivity)
        }
        mContentView.tv_move_stock.setOnClickListener {
            //配置移仓
            MoveStockActivity.launch(mActivity)
        }
        mContentView.tv_stock_record.setOnClickListener {
            //库存记录
            StockRecordActivity.launch(mActivity)
        }
    }

}