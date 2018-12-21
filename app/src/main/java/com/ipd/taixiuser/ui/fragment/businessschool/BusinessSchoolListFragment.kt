package com.ipd.taixiuser.ui.fragment.businessschool

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.BusinessSchoolAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.BusinessSchoolBean
import com.ipd.taixiuser.bean.ListResult
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.businessschool.BusinessSchoolDetailActivity
import rx.Observable

class BusinessSchoolListFragment : ListFragment<BaseResult<ListResult<BusinessSchoolBean>>, BusinessSchoolBean>() {

    companion object {
        fun newInstance(typeId: Int): BusinessSchoolListFragment {
            val fragment = BusinessSchoolListFragment()
            val bundle = Bundle()
            bundle.putInt("typeId", typeId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getTitleLayout() = -1
    override fun needLazyLoad(): Boolean = true


    private val typeId by lazy { arguments.getInt("typeId") }
    override fun loadListData(): Observable<BaseResult<ListResult<BusinessSchoolBean>>> {
        return ApiManager.getService().businessSchoolList(page, typeId)
    }

    override fun isNoMoreData(result: BaseResult<ListResult<BusinessSchoolBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: BusinessSchoolAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = BusinessSchoolAdapter(mActivity, data) {
                //itemClick
                BusinessSchoolDetailActivity.launch(mActivity, it.title, it.id)
            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<ListResult<BusinessSchoolBean>>) {
        data?.addAll(result?.data?.data ?: arrayListOf())
    }


}