package com.ipd.taixiuser.ui.fragment.order

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.OrderAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.OrderBean
import com.ipd.taixiuser.bean.UpdateOrderEvent
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.order.OrderDetailActivity
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class OrderFragment : ListFragment<BaseResult<List<OrderBean>>, OrderBean>() {

    companion object {
        fun newInstance(actionType: Int): OrderFragment {
            val fragment = OrderFragment()
            val bundle = Bundle()
            bundle.putInt("actionType", actionType)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        setLoadMoreEnable(false)
    }

    override fun needLazyLoad(): Boolean = true

    private val mActionType: Int by lazy { arguments?.getInt("actionType", 0) ?: 0 }

    override fun loadListData(): Observable<BaseResult<List<OrderBean>>> {
        return ApiManager.getService().orderList(GlobalParam.getUserIdOrJump(), mActionType)
    }

    override fun isNoMoreData(result: BaseResult<List<OrderBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: OrderAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = OrderAdapter(mActivity, data) {
                //itemClick
                OrderDetailActivity.launch(mActivity, it.id)
            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<OrderBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }


    @Subscribe
    fun onMainEvent(event: UpdateOrderEvent) {
        if (isFirstLoad()) return
        if (event.refreshPos.contains(mActionType)) {
            onRefresh(true)
        }
    }


}