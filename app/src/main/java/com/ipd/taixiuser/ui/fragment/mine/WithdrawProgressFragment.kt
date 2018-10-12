package com.ipd.taixiuser.ui.fragment.mine

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.WithdrawProgressAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.WithdrawProgressBean
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.mine.WithdrawProgressDetailActivity
import rx.Observable
import java.util.concurrent.TimeUnit

class WithdrawProgressFragment : ListFragment<BaseResult<List<WithdrawProgressBean>>, WithdrawProgressBean>() {

    override fun loadListData(): Observable<BaseResult<List<WithdrawProgressBean>>> {
        return Observable.timer(2000L, TimeUnit.MILLISECONDS)
                .map {
                    val list = ArrayList<WithdrawProgressBean>()
                    for (index in 0 until 10) {
                        list.add(WithdrawProgressBean())
                    }
                    BaseResult(200, list.toList())
                }
    }

    override fun isNoMoreData(result: BaseResult<List<WithdrawProgressBean>>): Int {
        if (page == INIT_PAGE && (result == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: WithdrawProgressAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = WithdrawProgressAdapter(mActivity, data, {
                //itemClick
                WithdrawProgressDetailActivity.launch(mActivity)
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<WithdrawProgressBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

}