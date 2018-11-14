package com.ipd.taixiuser.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.BusinessSchoolAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.BusinessSchoolBean
import com.ipd.taixiuser.platform.global.Constant
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.businessschool.BusinessSchoolDetailActivity
import kotlinx.android.synthetic.main.base_toolbar.view.*
import rx.Observable

class BusinessSchoolFragment : ListFragment<BaseResult<List<BusinessSchoolBean>>, BusinessSchoolBean>() {

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

    override fun loadListData(): Observable<BaseResult<List<BusinessSchoolBean>>> {
        return ApiManager.getService().businessSchoolList(page, Constant.PAGE_SIZE)
    }

    override fun isNoMoreData(result: BaseResult<List<BusinessSchoolBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: BusinessSchoolAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = BusinessSchoolAdapter(mActivity, data, {
                //itemClick
                BusinessSchoolDetailActivity.launch(mActivity, it.id)
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<BusinessSchoolBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }


}