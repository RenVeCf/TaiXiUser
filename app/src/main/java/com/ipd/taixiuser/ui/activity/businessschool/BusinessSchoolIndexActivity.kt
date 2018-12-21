package com.ipd.taixiuser.ui.activity.businessschool

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.BusinessSchoolTabBean
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.BaseUIActivity
import com.ipd.taixiuser.ui.fragment.businessschool.BusinessSchoolListFragment
import kotlinx.android.synthetic.main.fragment_matter_index.*

class BusinessSchoolIndexActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity, title: String, parentId: Int) {
            val intent = Intent(activity, BusinessSchoolIndexActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("parentId", parentId)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = intent.getStringExtra("title")

    override fun getContentLayout(): Int = R.layout.fragment_matter_index

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    private val parentId: Int by lazy { intent.getIntExtra("parentId", -1) }
    override fun loadData() {
        showProgress()
        ApiManager.getService().businessSchoolTabs(parentId)
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<List<BusinessSchoolTabBean>>>() {
                    override fun _onNext(result: BaseResult<List<BusinessSchoolTabBean>>) {
                        if (result.code == 200) {
                            view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
                                override fun getItem(position: Int): Fragment {
                                    return BusinessSchoolListFragment.newInstance(result.data[position].id)
                                }

                                override fun getCount(): Int = result.data.size

                                override fun getPageTitle(position: Int) = result.data[position].name
                            }
                            tab_layout.setupWithViewPager(view_pager)

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