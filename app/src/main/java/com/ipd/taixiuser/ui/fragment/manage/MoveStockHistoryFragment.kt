package com.ipd.taixiuser.ui.fragment.manage

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.MoveStockHistoryAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.OrderBean
import com.ipd.taixiuser.ui.ListFragment
import rx.Observable
import java.util.*
import java.util.concurrent.TimeUnit

class MoveStockHistoryFragment : ListFragment<BaseResult<List<OrderBean>>, OrderBean>() {

    companion object {
        fun newInstance(actionType: Int): MoveStockHistoryFragment {
            val fragment = MoveStockHistoryFragment()
            val bundle = Bundle()
            bundle.putInt("actionType", actionType)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun needLazyLoad(): Boolean = true

    private val mActionType: Int by lazy { arguments?.getInt("actionType", 0) ?: 0 }

    override fun loadListData(): Observable<BaseResult<List<OrderBean>>> {
        return Observable.timer(2000L, TimeUnit.MILLISECONDS)
                .map {
                    val list = ArrayList<OrderBean>()
                    for (index in 0 until 10) {
                        list.add(OrderBean())
                    }
                    BaseResult(200, list.toList())
                }
    }

    override fun isNoMoreData(result: BaseResult<List<OrderBean>>): Int {
        if (page == INIT_PAGE && (result == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: MoveStockHistoryAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = MoveStockHistoryAdapter(mActivity, data, {
                //itemClick

            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<OrderBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }


}