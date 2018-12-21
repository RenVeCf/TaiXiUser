package com.ipd.taixiuser.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.BusinessSchoolCategoryAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.BusinessSchoolCategoryBean
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.businessschool.BusinessSchoolIndexActivity
import kotlinx.android.synthetic.main.base_toolbar.view.*
import rx.Observable

class BusinessSchoolFragment : ListFragment<BaseResult<List<BusinessSchoolCategoryBean>>, BusinessSchoolCategoryBean>() {

    override fun getTitleLayout(): Int {
        return R.layout.base_toolbar
    }

    override fun initTitle() {
        super.initTitle()
        mHeaderView.tv_title.text = "商学院"
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        setLoadMoreEnable(false)
    }

    override fun loadListData(): Observable<BaseResult<List<BusinessSchoolCategoryBean>>> {
        return ApiManager.getService().businessSchoolCategory()
    }

    override fun isNoMoreData(result: BaseResult<List<BusinessSchoolCategoryBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: BusinessSchoolCategoryAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = BusinessSchoolCategoryAdapter(mActivity, data) {
                //itemClick
                BusinessSchoolIndexActivity.launch(mActivity, it.name, it.id)
            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<BusinessSchoolCategoryBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }


}