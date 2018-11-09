package com.ipd.taixiuser.ui.fragment.mine

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.CustomerTransferRecordAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.CustomerTransferRecordBean
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import rx.Observable

class CustomerTransferRecordFragment : ListFragment<BaseResult<List<CustomerTransferRecordBean>>, CustomerTransferRecordBean>() {
    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        setLoadMoreEnable(false)
    }

    override fun loadListData(): Observable<BaseResult<List<CustomerTransferRecordBean>>> {
        return ApiManager.getService().customerTransferRecord(GlobalParam.getUserIdOrJump())
    }

    override fun isNoMoreData(result: BaseResult<List<CustomerTransferRecordBean>>): Int {
        if (page == INIT_PAGE && (result == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: CustomerTransferRecordAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = CustomerTransferRecordAdapter(mActivity, data) {
                //itemClick

            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<CustomerTransferRecordBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }


}