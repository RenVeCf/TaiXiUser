package com.ipd.taixiuser.ui.fragment.mine

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.BankCardListAdapter
import com.ipd.taixiuser.bean.BankCardBean
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.event.ChooseBankCardEvent
import com.ipd.taixiuser.event.UpdateBankEvent
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class BankCardListFragment : ListFragment<BaseResult<List<BankCardBean>>, BankCardBean>() {


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

    override fun loadListData(): Observable<BaseResult<List<BankCardBean>>> {
        return ApiManager.getService().bankList(GlobalParam.getUserId())
    }

    override fun isNoMoreData(result: BaseResult<List<BankCardBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: BankCardListAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = BankCardListAdapter(mActivity, data) {
                //itemClick
                EventBus.getDefault().post(ChooseBankCardEvent(it))
                mActivity.finish()
            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<BankCardBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

    @Subscribe
    fun onMainEvent(event: UpdateBankEvent) {
        onRefresh(true)
    }

}