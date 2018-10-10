package com.ipd.taixiuser.ui.fragment.home

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.SystemMessageAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.ListResult
import com.ipd.taixiuser.bean.SystemMessageBean
import com.ipd.taixiuser.platform.global.Constant
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import rx.Observable

class SystemMessageFragment : ListFragment<BaseResult<ListResult<SystemMessageBean>>, SystemMessageBean>() {

    override fun loadListData(): Observable<BaseResult<ListResult<SystemMessageBean>>> {
        return ApiManager.getService().systemMessage(GlobalParam.getUserIdOrJump(), page, Constant.PAGE_SIZE)
    }

    override fun isNoMoreData(result: BaseResult<ListResult<SystemMessageBean>>): Int {
        if (page == INIT_PAGE && (result == null || result.data.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result == null || result.data.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: SystemMessageAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = SystemMessageAdapter(mActivity, data, {
                //itemClick

            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<ListResult<SystemMessageBean>>) {
        data?.addAll(result?.data?.data ?: arrayListOf())
    }


}