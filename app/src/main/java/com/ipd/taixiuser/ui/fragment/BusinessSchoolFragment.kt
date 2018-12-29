package com.ipd.taixiuser.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.BusinessSchoolCategoryAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.BusinessSchoolCategoryBean
import com.ipd.taixiuser.bean.CertBean
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.ListFragment
import com.ipd.taixiuser.ui.activity.PictureLookActivity
import com.ipd.taixiuser.ui.activity.businessschool.BusinessSchoolIndexActivity
import kotlinx.android.synthetic.main.base_toolbar.view.*
import rx.Observable
import java.util.ArrayList

class BusinessSchoolFragment : ListFragment<BaseResult<List<BusinessSchoolCategoryBean>>, BusinessSchoolCategoryBean>() {

    override fun getTitleLayout(): Int {
        return R.layout.base_toolbar
    }

    override fun initTitle() {
        super.initTitle()
        mHeaderView.tv_title.text = "商学院"
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        setLoadMoreEnable(false)
    }

    override fun loadListData(): Observable<BaseResult<List<BusinessSchoolCategoryBean>>> {
        return ApiManager.getService().businessSchoolCategory(GlobalParam.getUserIdOrJump())
    }

    override fun isNoMoreData(result: BaseResult<List<BusinessSchoolCategoryBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: BusinessSchoolCategoryAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = BusinessSchoolCategoryAdapter(mActivity, data) {
                //itemClick
                if (it.id == 9){
                    //我的毕业证书
                    ApiManager.getService().businessMyCert(GlobalParam.getUserIdOrJump(),it.id)
                            .compose(RxScheduler.applyScheduler())
                            .subscribe(object :Response<BaseResult<CertBean>>(mActivity,true){
                                override fun _onNext(result: BaseResult<CertBean>) {
                                    if (result.code == 200){
                                        PictureLookActivity.launch(mActivity, ArrayList(arrayListOf(result.data.certificate)),0,PictureLookActivity.URL)

                                    }else{
                                        toastShow(result.msg)
                                    }
                                }
                            })

                }else{
                    BusinessSchoolIndexActivity.launch(mActivity, it.name, it.id)
                }
            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<BusinessSchoolCategoryBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }


}