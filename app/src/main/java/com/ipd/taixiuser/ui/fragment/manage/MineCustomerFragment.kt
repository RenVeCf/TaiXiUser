package com.ipd.taixiuser.ui.fragment.manage

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.CustomerAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.CustomerBean
import com.ipd.taixiuser.event.ChooseCustomerEvent
import com.ipd.taixiuser.event.UpdateCustomerEvent
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.manage.CustomerInfoActivity
import com.ipd.taixiuser.ui.activity.manage.MineCustomerActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class MineCustomerFragment : ListFragment<BaseResult<List<CustomerBean>>, CustomerBean>() {

    companion object {
        fun newInstance(type: Int): MineCustomerFragment {
            val fragment = MineCustomerFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
    }


    private val mType by lazy { arguments.getInt("type", MineCustomerActivity.NORMAL) }
    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        setLoadMoreEnable(false)
    }

    override fun loadListData(): Observable<BaseResult<List<CustomerBean>>> {
        return ApiManager.getService().mineCustomer(GlobalParam.getUserIdOrJump())
    }

    override fun isNoMoreData(result: BaseResult<List<CustomerBean>>): Int {
        if (page == INIT_PAGE && (result == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: CustomerAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = CustomerAdapter(mActivity, data) {
                //itemClick
                when (mType) {
                    MineCustomerActivity.NORMAL -> {
                        CustomerInfoActivity.launch(mActivity, it.id)
                    }
                    MineCustomerActivity.CHOOSE -> {
                        EventBus.getDefault().post(ChooseCustomerEvent(it))
                        mActivity.finish()
                    }
                }
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

    @Subscribe
    fun onMainEvent(event: UpdateCustomerEvent) {
        isCreate = true
        onRefresh()
    }


}