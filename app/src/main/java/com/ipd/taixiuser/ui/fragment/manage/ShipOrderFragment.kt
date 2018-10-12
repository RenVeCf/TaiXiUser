package com.ipd.taixiuser.ui.fragment.manage

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.ShipOrderAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.ShipOrderBean
import com.ipd.taixiuser.ui.ListFragment
import rx.Observable
import java.util.*
import java.util.concurrent.TimeUnit

class ShipOrderFragment : ListFragment<BaseResult<List<ShipOrderBean>>, ShipOrderBean>() {

    override fun loadListData(): Observable<BaseResult<List<ShipOrderBean>>> {
        return Observable.timer(2000L, TimeUnit.MILLISECONDS)
                .map {
                    val list = ArrayList<ShipOrderBean>()
                    for (index in 0 until 10) {
                        list.add(ShipOrderBean())
                    }
                    BaseResult(200, list.toList())
                }
    }

    override fun isNoMoreData(result: BaseResult<List<ShipOrderBean>>): Int {
        if (page == INIT_PAGE && (result == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: ShipOrderAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = ShipOrderAdapter(mActivity, data, {
                //itemClick

            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<ShipOrderBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

}