package com.ipd.taixiuser.ui.fragment.manage

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.ProxyAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.CustomerBean
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.manage.ProxyInfoActivity
import rx.Observable
import java.util.*
import java.util.concurrent.TimeUnit

class ProxyListFragment : ListFragment<BaseResult<List<CustomerBean>>, CustomerBean>() {

    override fun loadListData(): Observable<BaseResult<List<CustomerBean>>> {
        return Observable.timer(2000L, TimeUnit.MILLISECONDS)
                .map {
                    val list = ArrayList<CustomerBean>()
                    for (index in 0 until 10) {
                        list.add(CustomerBean())
                    }
                    BaseResult(200, list.toList())
                }
    }

    override fun isNoMoreData(result: BaseResult<List<CustomerBean>>): Int {
        if (page == INIT_PAGE && (result == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: ProxyAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = ProxyAdapter(mActivity, data, {
                //itemClick
                ProxyInfoActivity.launch(mActivity, 1)
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<CustomerBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

}