package com.ipd.taixiuser.ui.fragment.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.ActionMessageAdapter
import com.ipd.taixiuser.bean.ActionMessageBean
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.ListResult
import com.ipd.taixiuser.platform.global.Constant
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import rx.Observable

class HomeActionFragment : ListFragment<BaseResult<ListResult<ActionMessageBean>>, ActionMessageBean>() {

    companion object {
        fun newInstance(actionType: Int): HomeActionFragment {
            val fragment = HomeActionFragment()
            val bundle = Bundle()
            bundle.putInt("actionType", actionType)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val mActionType: Int by lazy { arguments.getInt("actionType", 0) }

    override fun loadListData(): Observable<BaseResult<ListResult<ActionMessageBean>>> {
        return ApiManager.getService().actionMessage(mActionType, GlobalParam.getUserIdOrJump(), page, Constant.PAGE_SIZE)
    }

    override fun isNoMoreData(result: BaseResult<ListResult<ActionMessageBean>>): Int {
        if (page == INIT_PAGE && (result == null || result.data.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result == null || result.data.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: ActionMessageAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = ActionMessageAdapter(mActivity, data, {
                //itemClick

            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<ListResult<ActionMessageBean>>) {
        data?.addAll(result?.data?.data ?: arrayListOf())
    }


}