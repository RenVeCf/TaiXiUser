package com.ipd.taixiuser.ui.fragment.manage

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.MoveStockAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.MoveStockBean
import com.ipd.taixiuser.bean.ProductBean
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.manage.MoveStockConfirmActivity
import kotlinx.android.synthetic.main.fragment_factory_ship_list.view.*
import rx.Observable

class MoveStockProductFragment : ListFragment<BaseResult<List<MoveStockBean>>, MoveStockBean>() {
    companion object {
        fun newInstance(customerId: Int): MoveStockProductFragment {
            val fragment = MoveStockProductFragment()
            val bundle = Bundle()
            bundle.putInt("customerId", customerId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val mCustomerId: Int by lazy { arguments?.getInt("customerId", 0) ?: 0 }

    override fun getContentLayout(): Int = R.layout.fragment_move_stock_product_list

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        setLoadMoreEnable(false)
    }

    override fun initListener() {
        super.initListener()
        mContentView.tv_next.setOnClickListener {
            //下一步
            val list = ArrayList<ProductBean>()
            data?.forEach {
                if (it.goods.chooseNum > 0) list.add(it.goods)
            }
            if (list.isEmpty()) {
                toastShow("请选择商品数量")
                return@setOnClickListener
            }
            MoveStockConfirmActivity.launch(mActivity, list, mCustomerId)
            mActivity.finish()
        }
    }

    override fun loadListData(): Observable<BaseResult<List<MoveStockBean>>> {
        return ApiManager.getService().moveStockProduct(GlobalParam.getUserIdOrJump())
    }

    override fun isNoMoreData(result: BaseResult<List<MoveStockBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: MoveStockAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = MoveStockAdapter(mActivity, data) {
                //itemClick

            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<MoveStockBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

}