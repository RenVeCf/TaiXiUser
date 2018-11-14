package com.ipd.taixiuser.ui.fragment.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.adapter.QuestionAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.QuestionBean
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.web.WebActivity
import rx.Observable

class QuestionFragment : ListFragment<BaseResult<List<QuestionBean>>, QuestionBean>() {

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        setLoadMoreEnable(false)
    }

    override fun loadListData(): Observable<BaseResult<List<QuestionBean>>> {
        return ApiManager.getService().question()
    }

    override fun isNoMoreData(result: BaseResult<List<QuestionBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: QuestionAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = QuestionAdapter(mActivity, data, {
                //itemClick
                ApiManager.getService().questionDetail(it.id)
                        .compose(RxScheduler.applyScheduler())
                        .subscribe(object : Response<BaseResult<QuestionBean>>(mActivity, true) {
                            override fun _onNext(result: BaseResult<QuestionBean>) {
                                if (result.code == 200) {
                                    WebActivity.launch(mActivity, WebActivity.HTML, result.data.content, it.title)
                                } else {
                                    toastShow(result.msg)
                                }
                            }
                        })


            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<QuestionBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }


}