package com.ipd.taixiuser.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.MatterAdapter
import com.ipd.taixiuser.bean.MatterBean
import com.ipd.taixiuser.ui.ListFragment
import kotlinx.android.synthetic.main.base_toolbar.view.*
import rx.Observable
import java.util.concurrent.TimeUnit

class MatterFragment : ListFragment<List<MatterBean>, MatterBean>() {

    override fun getTitleLayout(): Int {
        return R.layout.base_toolbar
    }

    override fun getContentLayout(): Int = R.layout.fragment_matter_list

    override fun initTitle() {
        super.initTitle()
        mHeaderView.tv_title.text = "素材"
    }

    override fun loadListData(): Observable<List<MatterBean>> {
        return Observable.timer(2000L, TimeUnit.MILLISECONDS)
                .map {
                    var list: ArrayList<MatterBean> = ArrayList()
                    for (index in 0 until 10) {
                        list.add(MatterBean())
                    }
                    list
                }
    }

    override fun isNoMoreData(result: List<MatterBean>): Int {
        if (page == INIT_PAGE && (result == null || result.isEmpty())) {
            return EMPTY_DATA
        } else if (result == null || result.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: MatterAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = MatterAdapter(mActivity, data, {
                //itemClick

            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: List<MatterBean>) {
        data?.addAll(result)
    }


}