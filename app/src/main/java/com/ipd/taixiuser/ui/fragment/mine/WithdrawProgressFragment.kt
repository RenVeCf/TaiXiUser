package com.ipd.taixiuser.ui.fragment.mine

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.WithdrawProgressAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.WithdrawProgressBean
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.mine.WithdrawProgressDetailActivity
import rx.Observable

class WithdrawProgressFragment : ListFragment<BaseResult<List<WithdrawProgressBean>>, WithdrawProgressBean>() {

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        setLoadMoreEnable(false)
    }

    override fun loadListData(): Observable<BaseResult<List<WithdrawProgressBean>>> {
        return ApiManager.getService().withdrawProgress(GlobalParam.getUserIdOrJump())
    }

    override fun isNoMoreData(result: BaseResult<List<WithdrawProgressBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: WithdrawProgressAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = WithdrawProgressAdapter(mActivity, data) {
                //itemClick
                WithdrawProgressDetailActivity.launch(mActivity,it.id)
            }
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