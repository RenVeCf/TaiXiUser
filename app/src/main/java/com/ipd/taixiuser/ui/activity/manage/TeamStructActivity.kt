package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.TeamStructBean
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_team_struct.*

class TeamStructActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity, totalNum: Int, newNum: Int) {
            val intent = Intent(activity, TeamStructActivity::class.java)
            intent.putExtra("totalNum", totalNum)
            intent.putExtra("newNum", newNum)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "团队结构图"
    override fun getContentLayout(): Int = R.layout.activity_team_struct

    private val mTotalNum by lazy { intent.getIntExtra("totalNum", 0) }
    private val mNewNum by lazy { intent.getIntExtra("newNum", 0) }
    override fun initView(bundle: Bundle?) {
        initToolbar()
        tv_team_total.text = mTotalNum.toString()
        tv_month_team.text = mNewNum.toString()
    }

    override fun loadData() {
        showProgress()
        ApiManager.getService().teamStruct(GlobalParam.getUserIdOrJump())
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<TeamStructBean>>() {
                    override fun _onNext(result: BaseResult<TeamStructBean>) {
                        if (result.code == 200) {
                            setContent(result.data)
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

    private fun setContent(data: TeamStructBean) {
        family_tree_view.setTeamStructBean(data)
    }

    override fun initListener() {
    }

}