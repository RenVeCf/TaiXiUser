package com.ipd.taixiuser.ui.fragment.manage

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.StoreProductAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.ProductBean
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.manage.StorePayActivity
import rx.Observable
import java.util.*
import java.util.concurrent.TimeUnit

class StoreFragment : ListFragment<BaseResult<List<ProductBean>>, ProductBean>() {

    override fun loadListData(): Observable<BaseResult<List<ProductBean>>> {
        return ApiManager.getService().storeList(GlobalParam.getUserIdOrJump())
    }

    override fun isNoMoreData(result: BaseResult<List<ProductBean>>): Int {
        if (page == INIT_PAGE && (result == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: StoreProductAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = StoreProductAdapter(mActivity, data) {
                //itemClick
                StorePayActivity.launch(mActivity)
            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<ProductBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

}