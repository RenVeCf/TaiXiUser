package com.ipd.taixiuser.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.MatterAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.ListResult
import com.ipd.taixiuser.bean.MatterBean
import com.ipd.taixiuser.platform.global.Constant
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.matter.MatterDetailActivity
import kotlinx.android.synthetic.main.base_toolbar.view.*
import rx.Observable

class MatterFragment : ListFragment<BaseResult<ListResult<MatterBean>>, MatterBean>() {

    override fun getTitleLayout(): Int {
        return R.layout.base_toolbar
    }

    override fun getContentLayout(): Int = R.layout.fragment_matter_list

    override fun initTitle() {
        super.initTitle()
        mHeaderView.tv_title.text = "素材"
    }

    override fun loadListData(): Observable<BaseResult<ListResult<MatterBean>>> {
        return ApiManager.getService().matterList(page, Constant.PAGE_SIZE)
    }

    override fun isNoMoreData(result: BaseResult<ListResult<MatterBean>>): Int {
        if (page == INIT_PAGE && (result == null || result.data.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result == null || result.data.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: MatterAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = MatterAdapter(mActivity, data, {
                //itemClick
                MatterDetailActivity.launch(mActivity,it.id)
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<ListResult<MatterBean>>) {
        data?.addAll(result?.data?.data ?: arrayListOf())
    }


}