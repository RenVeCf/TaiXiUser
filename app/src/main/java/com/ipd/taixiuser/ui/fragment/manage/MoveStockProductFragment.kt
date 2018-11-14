package com.ipd.taixiuser.ui.fragment.manage

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.FactoryShipAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.ProductBean
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.manage.FactoryShipPayActivity
import com.ipd.taixiuser.ui.activity.manage.MoveStockConfirmActivity
import kotlinx.android.synthetic.main.fragment_factory_ship_list.view.*
import rx.Observable
import java.util.*
import java.util.concurrent.TimeUnit

class MoveStockProductFragment : ListFragment<BaseResult<List<ProductBean>>, ProductBean>() {

    override fun getContentLayout(): Int = R.layout.fragment_move_stock_product_list

    override fun initListener() {
        super.initListener()
        mContentView.tv_next.setOnClickListener {
            //下一步
            MoveStockConfirmActivity.launch(mActivity)
            mActivity.finish()
        }
    }

    override fun loadListData(): Observable<BaseResult<List<ProductBean>>> {
        return Observable.timer(2000L, TimeUnit.MILLISECONDS)
                .map {
                    val list = ArrayList<ProductBean>()
                    for (index in 0 until 10) {
                        list.add(ProductBean())
                    }
                    BaseResult(200, list.toList())
                }
    }

    override fun isNoMoreData(result: BaseResult<List<ProductBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: FactoryShipAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = FactoryShipAdapter(mActivity, data, {
                //itemClick

            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<ProductBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

}