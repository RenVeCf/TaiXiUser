package com.ipd.taixiuser.ui.fragment.mine

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.BusinessSchoolAdapter
import com.ipd.taixiuser.bean.BusinessSchoolBean
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.businessschool.BusinessSchoolDetailActivity
import rx.Observable
import java.util.concurrent.TimeUnit

class CollectFragment : ListFragment<List<BusinessSchoolBean>, BusinessSchoolBean>() {
    override fun loadListData(): Observable<List<BusinessSchoolBean>> {
        return Observable.timer(2000L, TimeUnit.MILLISECONDS)
                .map {
                    var list: ArrayList<BusinessSchoolBean> = ArrayList()
                    for (index in 0 until 10) {
                        list.add(BusinessSchoolBean())
                    }
                    list
                }
    }

    override fun isNoMoreData(result: List<BusinessSchoolBean>): Int {
        if (page == INIT_PAGE && (result == null || result.isEmpty())) {
            return EMPTY_DATA
        } else if (result == null || result.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: BusinessSchoolAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = BusinessSchoolAdapter(mActivity, data, {
                //itemClick
                BusinessSchoolDetailActivity.launch(mActivity, -1)
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: List<BusinessSchoolBean>) {
        data?.addAll(result)
    }


}