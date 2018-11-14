package com.ipd.taixiuser.ui.fragment.manage

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.ProxyAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.CustomerBean
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.manage.ProxyInfoActivity
import rx.Observable

class ProxyListFragment : ListFragment<BaseResult<List<CustomerBean>>, CustomerBean>() {
    companion object {
        fun newInstance(proxy: Int): ProxyListFragment {
            val fragment = ProxyListFragment()
            val bundle = Bundle()
            bundle.putInt("proxy", proxy)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        setLoadMoreEnable(false)
    }

    private val mProxy: Int by lazy { arguments.getInt("proxy", -1) }
    override fun loadListData(): Observable<BaseResult<List<CustomerBean>>> {
        return ApiManager.getService().mineTeamList(GlobalParam.getUserIdOrJump(), mProxy)
    }

    override fun isNoMoreData(result: BaseResult<List<CustomerBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: ProxyAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = ProxyAdapter(mActivity, data) {
                //itemClick
                ProxyInfoActivity.launch(mActivity, it.id)
            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<CustomerBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

}