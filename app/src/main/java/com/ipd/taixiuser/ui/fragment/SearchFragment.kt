package com.ipd.taixiuser.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.MatterAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.ListResult
import com.ipd.taixiuser.bean.MatterBean
import com.ipd.taixiuser.platform.global.Constant
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.matter.MatterDetailActivity
import rx.Observable

class SearchFragment : ListFragment<BaseResult<ListResult<MatterBean>>, MatterBean>() {

    override fun loadListData(): Observable<BaseResult<ListResult<MatterBean>>> {
        return ApiManager.getService().matterSearch(searchStr, page, Constant.PAGE_SIZE)
    }

    override fun isNoMoreData(result: BaseResult<ListResult<MatterBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: MatterAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = MatterAdapter(mActivity, data) {
                //itemClick
                MatterDetailActivity.launch(mActivity, it.id)
            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<ListResult<MatterBean>>) {
        data?.addAll(result?.data?.data ?: arrayListOf())
    }


    private var searchStr: String = ""
    fun search(searchStr: String) {
        this.searchStr = searchStr
        isCreate = true
        onRefresh()
    }

}