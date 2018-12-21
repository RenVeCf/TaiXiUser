package com.ipd.taixiuser.ui.fragment.businessschool

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.BusinessSchoolTalkAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.BusinessTalkBean
import com.ipd.taixiuser.event.UpdateBusinessTalkEvent
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.businessschool.PublishBusinessTalkActivity
import com.ipd.taixiuser.ui.fragment.BusinessSchoolFragment
import com.ipd.taixiuser.widget.ReplyDialog
import kotlinx.android.synthetic.main.fragment_business_talk.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class BusinessSchoolTalkFragment : ListFragment<BaseResult<List<BusinessTalkBean>>, BusinessTalkBean>() {

    companion object {
        fun newInstance(businessSchoolId: Int): BusinessSchoolTalkFragment {
            val fragment = BusinessSchoolTalkFragment()
            val bundle = Bundle()
            bundle.putInt("businessSchoolId", businessSchoolId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getTitleLayout() = -1
    override fun getContentLayout(): Int = R.layout.fragment_business_talk
    override fun needLazyLoad(): Boolean = true

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

    override fun initListener() {
        super.initListener()
        mContentView.tv_talk.setOnClickListener {
            //提问
            PublishBusinessTalkActivity.launch(mActivity, businessSchoolId)
        }
    }

    private val businessSchoolId by lazy { arguments.getInt("businessSchoolId") }
    override fun loadListData(): Observable<BaseResult<List<BusinessTalkBean>>> {
        return ApiManager.getService().businessTalk(businessSchoolId)
    }

    override fun isNoMoreData(result: BaseResult<List<BusinessTalkBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return NORMAL
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: BusinessSchoolTalkAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = BusinessSchoolTalkAdapter(mActivity, data) { type, info ->
                //itemClick
                ReplyDialog("回复${info.username}") {
                    if (TextUtils.isEmpty(it)) {
                        toastShow("请输入回复内容")
                        return@ReplyDialog
                    }
                    ApiManager.getService().businessTalkReplySecond(GlobalParam.getUserIdOrJump(), info.id, it)
                            .compose(RxScheduler.applyScheduler())
                            .subscribe(object : Response<BaseResult<BusinessTalkBean>>(context, true) {
                                override fun _onNext(result: BaseResult<BusinessTalkBean>) {
                                    if (result.code == 200) {
                                        toastShow(true,"回复成功")
                                        onRefresh(true)
                                    } else {
                                        toastShow(result.msg)
                                    }
                                }
                            })

                }.show(childFragmentManager, BusinessSchoolFragment::class.java.name)
            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<BusinessTalkBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

    @Subscribe
    fun onMainEvent(event: UpdateBusinessTalkEvent) {
        onRefresh(true)
    }


}