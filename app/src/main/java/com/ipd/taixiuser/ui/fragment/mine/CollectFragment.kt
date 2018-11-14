package com.ipd.taixiuser.ui.fragment.mine

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.CollectBusinessSchoolAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.CollectBusinessSchoolBean
import com.ipd.taixiuser.event.UpdateBusinessSchoolEvent
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.businessschool.BusinessSchoolDetailActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class CollectFragment : ListFragment<BaseResult<List<CollectBusinessSchoolBean>>, CollectBusinessSchoolBean>() {

    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
    }


    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        setLoadMoreEnable(false)
    }

    override fun loadListData(): Observable<BaseResult<List<CollectBusinessSchoolBean>>> {
        return ApiManager.getService().collectList(GlobalParam.getUserId())
    }

    override fun isNoMoreData(result: BaseResult<List<CollectBusinessSchoolBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: CollectBusinessSchoolAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = CollectBusinessSchoolAdapter(mActivity, data) {
                //itemClick
                BusinessSchoolDetailActivity.launch(mActivity, it.id)
            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<CollectBusinessSchoolBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

    @Subscribe
    fun onMainEvent(event: UpdateBusinessSchoolEvent) {
        onRefresh(true)
    }


}