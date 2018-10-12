package com.ipd.taixiuser.ui.fragment.mine

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.BankCardListAdapter
import com.ipd.taixiuser.bean.BankCardBean
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.ui.ListFragment
import rx.Observable
import java.util.concurrent.TimeUnit

class BankCardListFragment : ListFragment<BaseResult<List<BankCardBean>>, BankCardBean>() {

    override fun loadListData(): Observable<BaseResult<List<BankCardBean>>> {
        return Observable.timer(2000L, TimeUnit.MILLISECONDS)
                .map {
                    val list = ArrayList<BankCardBean>()
                    for (index in 0 until 10) {
                        list.add(BankCardBean())
                    }
                    BaseResult(200, list.toList())
                }
    }

    override fun isNoMoreData(result: BaseResult<List<BankCardBean>>): Int {
        if (page == INIT_PAGE && (result == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: BankCardListAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = BankCardListAdapter(mActivity, data, {
                //itemClick
                mActivity.finish()
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<BankCardBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

}