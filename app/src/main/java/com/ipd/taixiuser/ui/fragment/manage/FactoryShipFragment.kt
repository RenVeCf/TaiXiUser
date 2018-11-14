package com.ipd.taixiuser.ui.fragment.manage

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.FactoryShipAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.FactoryShipBean
import com.ipd.taixiuser.bean.ProductBean
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.manage.FactoryShipPayActivity
import kotlinx.android.synthetic.main.fragment_factory_ship_list.view.*
import rx.Observable

class FactoryShipFragment : ListFragment<BaseResult<FactoryShipBean>, ProductBean>() {

    override fun getContentLayout(): Int = R.layout.fragment_factory_ship_list

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        setLoadMoreEnable(false)
    }

    override fun initListener() {
        super.initListener()
        mContentView.tv_next.setOnClickListener {
            //下一步
            val list = ArrayList<ProductBean>()
            data?.forEach {
                if (it.chooseNum > 0) list.add(it)
            }
            if (list.isEmpty()){
                toastShow("请选择商品数量")
                return@setOnClickListener
            }
            FactoryShipPayActivity.launch(mActivity,list)
        }
    }

    override fun loadListData(): Observable<BaseResult<FactoryShipBean>> {
        return ApiManager.getService().factoryShip(GlobalParam.getUserIdOrJump())
    }

    override fun isNoMoreData(result: BaseResult<FactoryShipBean>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.goods == null)) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.goods == null) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: FactoryShipAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = FactoryShipAdapter(mActivity, data) {
                //itemClick


            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<FactoryShipBean>) {
        data?.add(result.data.goods)
    }

}