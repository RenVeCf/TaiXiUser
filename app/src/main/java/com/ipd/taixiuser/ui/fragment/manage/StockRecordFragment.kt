package com.ipd.taixiuser.ui.fragment.manage

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.StockRecordAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.StockRecordBean
import com.ipd.taixiuser.bean.StockRecordParentBean
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.manage.StockRecordActivity
import rx.Observable

class StockRecordFragment : ListFragment<BaseResult<StockRecordParentBean>, StockRecordBean>() {

    override fun getContentLayout(): Int = R.layout.fragment_stock_record_list

    override fun loadListData(): Observable<BaseResult<StockRecordParentBean>> {
        return ApiManager.getService().stockRecordList(GlobalParam.getUserIdOrJump())
    }

    override fun loadListDataSuccess(isRefresh: Boolean, result: BaseResult<StockRecordParentBean>) {
        super.loadListDataSuccess(isRefresh, result)
        if (mActivity is StockRecordActivity){
            (mActivity as StockRecordActivity).setStockInfo(result)
        }
    }

    override fun isNoMoreData(result: BaseResult<StockRecordParentBean>): Int {
        if (page == INIT_PAGE && (result == null || result.data.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result == null || result.data.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: StockRecordAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = StockRecordAdapter(mActivity, data) {
                //itemClick

            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<StockRecordParentBean>) {
        data?.addAll(result?.data.data ?: arrayListOf())
    }

}