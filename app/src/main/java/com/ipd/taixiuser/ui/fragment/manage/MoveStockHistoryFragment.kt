package com.ipd.taixiuser.ui.fragment.manage

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.MoveStockHistoryAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.MoveStockHistoryBean
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import rx.Observable
import java.util.*
import java.util.concurrent.TimeUnit

class MoveStockHistoryFragment : ListFragment<BaseResult<List<MoveStockHistoryBean>>, MoveStockHistoryBean>() {

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

    override fun loadListData(): Observable<BaseResult<List<MoveStockHistoryBean>>> {
        return ApiManager.getService().moveStockHistory(GlobalParam.getUserIdOrJump(),mActionType)
    }

    override fun isNoMoreData(result: BaseResult<List<MoveStockHistoryBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: MoveStockHistoryAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = MoveStockHistoryAdapter(mActivity, data) {
                //itemClick

            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<MoveStockHistoryBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }


}