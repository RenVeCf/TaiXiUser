package com.ipd.taixiuser.ui.fragment.mine

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.ConsumeAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.ConsumeBean
import com.ipd.taixiuser.bean.WalletBean
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import rx.Observable

class MoreConsumeFragment : ListFragment<BaseResult<WalletBean>, ConsumeBean>() {

    override fun loadListData(): Observable<BaseResult<WalletBean>> {
        return ApiManager.getService().myWallet(GlobalParam.getUserId(), page)
    }

    override fun isNoMoreData(result: BaseResult<WalletBean>): Int {
        if (page == INIT_PAGE && (result.data.consumption.data == null || result.data.consumption.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data.consumption.data == null || result.data.consumption.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: ConsumeAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = ConsumeAdapter(mActivity, data) {
                //itemClick
            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<WalletBean>) {
        data?.addAll(result?.data?.consumption?.data ?: arrayListOf())
    }

}