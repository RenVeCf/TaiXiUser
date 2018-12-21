package com.ipd.taixiuser.ui.fragment.businessschool

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.BusinessSchoolDirectoryAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.BusinessDirectoryBean
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.businessschool.BusinessDirectoryDetailActivity
import com.ipd.taixiuser.ui.activity.businessschool.BusinessSchoolDetailActivity
import rx.Observable

class BusinessSchoolDirectoryFragment : ListFragment<BaseResult<List<BusinessDirectoryBean>>, BusinessDirectoryBean>() {

    companion object {
        fun newInstance(businessSchoolId: Int): BusinessSchoolDirectoryFragment {
            val fragment = BusinessSchoolDirectoryFragment()
            val bundle = Bundle()
            bundle.putInt("businessSchoolId", businessSchoolId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getTitleLayout() = -1
    override fun needLazyLoad(): Boolean = true

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        setLoadMoreEnable(false)
    }

    private val businessSchoolId by lazy { arguments.getInt("businessSchoolId") }
    override fun loadListData(): Observable<BaseResult<List<BusinessDirectoryBean>>> {
        return ApiManager.getService().businessDirectory(businessSchoolId)
    }

    override fun isNoMoreData(result: BaseResult<List<BusinessDirectoryBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: BusinessSchoolDirectoryAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = BusinessSchoolDirectoryAdapter(mActivity, data) { type, info ->
                //itemClick
                when (type) {
                    0 -> {
                        (mActivity as BusinessSchoolDetailActivity).setHeaderInfo(info)
                    }
                    1 -> {
                        BusinessDirectoryDetailActivity.launch(mActivity,info)
                    }
                }

            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<BusinessDirectoryBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
        if (isRefresh) {
            if (mActivity is BusinessSchoolDetailActivity) {
                if (data != null && data!!.isNotEmpty()) {
                    (mActivity as BusinessSchoolDetailActivity).setHeaderInfo(data!![0])
                }
            }
        }
    }


}