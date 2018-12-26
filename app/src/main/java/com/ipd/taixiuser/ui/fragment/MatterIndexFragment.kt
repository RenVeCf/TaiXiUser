package com.ipd.taixiuser.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.MatterResultBean
import com.ipd.taixiuser.platform.global.Constant
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.BaseUIFragment
import kotlinx.android.synthetic.main.base_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_matter_index.view.*

class MatterIndexFragment : BaseUIFragment() {

    override fun getTitleLayout(): Int {
        return R.layout.base_toolbar
    }

    override fun initTitle() {
        super.initTitle()
        mHeaderView.tv_title.text = "素材"
    }

    override fun getContentLayout(): Int = R.layout.fragment_matter_index

    override fun initView(bundle: Bundle?) {

    }

    override fun loadData() {
        showProgress()
        ApiManager.getService().matterList(1, Constant.PAGE_SIZE)
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<MatterResultBean>>() {
                    override fun _onNext(result: BaseResult<MatterResultBean>) {
                        if (result.code == 200) {
                            mContentView.view_pager.adapter = object : FragmentPagerAdapter(childFragmentManager) {
                                override fun getItem(position: Int): Fragment {
                                    val info = result.data.type[position]
                                    return if (info.is_h5 == 0) {
                                        MatterFragment.newInstance(result.data.type[position].id)
                                    } else {
                                        MatterForwardFragment.newInstance(result.data.type[position].id)
                                    }
                                }

                                override fun getCount(): Int = result.data.type.size

                                override fun getPageTitle(position: Int) = result.data.type[position].name
                            }
                            mContentView.tab_layout.setupWithViewPager(mContentView.view_pager)


                            showContent()
                        } else {
                            showError(result.msg)
                        }

                    }

                    override fun onError(e: Throwable?) {
                        showError()
                    }

                })


    }


    override fun initListener() {
    }

}