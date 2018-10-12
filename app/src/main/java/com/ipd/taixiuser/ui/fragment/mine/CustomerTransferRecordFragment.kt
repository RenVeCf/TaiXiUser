package com.ipd.taixiuser.ui.fragment.mine

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.CustomerTransferRecordAdapter
import com.ipd.taixiuser.bean.CustomerTransferRecordBean
import com.ipd.taixiuser.ui.ListFragment
import rx.Observable
import java.util.concurrent.TimeUnit

class CustomerTransferRecordFragment : ListFragment<List<CustomerTransferRecordBean>, CustomerTransferRecordBean>() {
    override fun loadListData(): Observable<List<CustomerTransferRecordBean>> {
        return Observable.timer(2000L, TimeUnit.MILLISECONDS)
                .map {
                    var list: ArrayList<CustomerTransferRecordBean> = ArrayList()
                    for (index in 0 until 10) {
                        list.add(CustomerTransferRecordBean())
                    }
                    list
                }
    }

    override fun isNoMoreData(result: List<CustomerTransferRecordBean>): Int {
        if (page == INIT_PAGE && (result == null || result.isEmpty())) {
            return EMPTY_DATA
        } else if (result == null || result.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: CustomerTransferRecordAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = CustomerTransferRecordAdapter(mActivity, data, {
                //itemClick

            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: List<CustomerTransferRecordBean>) {
        data?.addAll(result)
    }


}