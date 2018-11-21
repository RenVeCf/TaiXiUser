package com.ipd.taixiuser.ui.fragment.manage

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.EarningsAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.EarningParentBean
import com.ipd.taixiuser.bean.EarningsBean
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.manage.EarningsActivity
import rx.Observable

class EarningsFragment : ListFragment<BaseResult<EarningParentBean>, EarningsBean>() {

    override fun getContentLayout(): Int = R.layout.fragment_stock_record_list

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        setLoadMoreEnable(false)
    }

    override fun loadListData(): Observable<BaseResult<EarningParentBean>> {
        return ApiManager.getService().earningsList(GlobalParam.getUserIdOrJump())
    }

    override fun isNoMoreData(result: BaseResult<EarningParentBean>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.list.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.list.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    override fun loadListDataSuccess(isRefresh: Boolean, result: BaseResult<EarningParentBean>) {
        super.loadListDataSuccess(isRefresh, result)
        if (mActivity is EarningsActivity) {
            (mActivity as EarningsActivity).setPriceInfo(result)
        }
    }

    private var mAdapter: EarningsAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = EarningsAdapter(mActivity, data) {
                //itemClick

            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<EarningParentBean>) {
        data?.addAll(result?.data?.list ?: arrayListOf())
    }

}