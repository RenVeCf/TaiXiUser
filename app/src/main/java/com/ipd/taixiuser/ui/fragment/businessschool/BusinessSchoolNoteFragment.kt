package com.ipd.taixiuser.ui.fragment.businessschool

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.BusinessSchoolNoteAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.BusinessNoteBean
import com.ipd.taixiuser.bean.BusinessTalkBean
import com.ipd.taixiuser.event.UpdateBusinessNoteEvent
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.businessschool.PublishBusinessNoteActivity
import kotlinx.android.synthetic.main.fragment_business_note.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class BusinessSchoolNoteFragment : ListFragment<BaseResult<List<BusinessNoteBean>>, BusinessNoteBean>() {

    companion object {
        fun newInstance(businessSchoolId: Int): BusinessSchoolNoteFragment {
            val fragment = BusinessSchoolNoteFragment()
            val bundle = Bundle()
            bundle.putInt("businessSchoolId", businessSchoolId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getTitleLayout() = -1
    override fun getContentLayout(): Int = R.layout.fragment_business_note
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
        mContentView.tv_note.setOnClickListener {
            //提问
            PublishBusinessNoteActivity.launch(mActivity, businessSchoolId)
        }
    }

    private val businessSchoolId by lazy { arguments.getInt("businessSchoolId") }
    override fun loadListData(): Observable<BaseResult<List<BusinessNoteBean>>> {
        return ApiManager.getService().businessNote(GlobalParam.getUserIdOrJump(), businessSchoolId)
    }

    override fun isNoMoreData(result: BaseResult<List<BusinessNoteBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return NORMAL
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: BusinessSchoolNoteAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = BusinessSchoolNoteAdapter(mActivity, data) { type, info ->
                //itemClick
                val builder = AlertDialog.Builder(mActivity)
                builder.setTitle("提示")
                        .setMessage("确定要删除该笔记吗?")
                        .setPositiveButton("确定") { dialog, which ->
                            dialog.dismiss()
                            ApiManager.getService().businessNoteDelete(info.id)
                                    .compose(RxScheduler.applyScheduler())
                                    .subscribe(object : Response<BaseResult<BusinessTalkBean>>(mActivity, true) {
                                        override fun _onNext(result: BaseResult<BusinessTalkBean>) {
                                            if (result.code == 200) {
                                                toastShow(true,"删除成功")
                                                onRefresh(true)
                                            } else {
                                                toastShow(result.msg)
                                            }
                                        }
                                    })
                        }
                        .setNegativeButton("取消") { dialog, which ->
                            dialog.dismiss()

                        }.show()


            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<BusinessNoteBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

    @Subscribe
    fun onMainEvent(event: UpdateBusinessNoteEvent) {
        onRefresh(true)
    }


}