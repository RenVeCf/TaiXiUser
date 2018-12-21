package com.ipd.taixiuser.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.MatterAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.ListResult
import com.ipd.taixiuser.bean.MatterBean
import com.ipd.taixiuser.bean.MatterResultBean
import com.ipd.taixiuser.platform.global.Constant
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.SearchActivity
import com.ipd.taixiuser.ui.activity.matter.MatterDetailActivity
import kotlinx.android.synthetic.main.fragment_matter_list.view.*
import rx.Observable

class MatterFragment : ListFragment<BaseResult<MatterResultBean>, MatterBean>() {

    companion object {
        fun newInstance(typeId: Int): MatterFragment {
            val fragment = MatterFragment()
            val bundle = Bundle()
            bundle.putInt("typeId", typeId)
            fragment.arguments = bundle
            return fragment
        }
    }


    private val mTypeId: Int by lazy { arguments.getInt("typeId", 0) }
    override fun getTitleLayout(): Int = -1

    override fun getContentLayout(): Int = R.layout.fragment_matter_list

    override fun needLazyLoad(): Boolean = true

    override fun initListener() {
        super.initListener()
        mContentView.tv_search.setOnClickListener {
            SearchActivity.launch(mActivity)
        }
    }

    override fun loadListData(): Observable<BaseResult<MatterResultBean>> {
        return ApiManager.getService().matterList(page, Constant.PAGE_SIZE, mTypeId)
    }

    override fun isNoMoreData(result: BaseResult<MatterResultBean>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.material.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.material.data.isEmpty()) {
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

    override fun addData(isRefresh: Boolean, result: BaseResult<MatterResultBean>) {
        data?.addAll(result?.data?.material?.data ?: arrayListOf())
    }


}