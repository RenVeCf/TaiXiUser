package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.TeamGroupAdapter
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.TeamGroupBean
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler
import com.ipd.taixiuser.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_mine_team.*

class MineTeamActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, MineTeamActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "我的团队"

    override fun getContentLayout(): Int = R.layout.activity_mine_team

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        showProgress()
        ApiManager.getService().mineTeam(GlobalParam.getUserIdOrJump())
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<List<TeamGroupBean>>>() {
                    override fun _onNext(result: BaseResult<List<TeamGroupBean>>) {
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

    private fun setContent(data: List<TeamGroupBean>) {
        val list = listOf(
                TeamGroupBean(R.mipmap.icon_retail, "零售", data[0].num, data[0].proxy),
                TeamGroupBean(R.mipmap.icon_gift_box, "礼盒装", data[1].num, data[1].proxy),
                TeamGroupBean(R.mipmap.icon_vip, "VIP", data[2].num, data[2].proxy),
                TeamGroupBean(R.mipmap.icon_proxy, "代理", data[3].num, data[3].proxy),
                TeamGroupBean(R.mipmap.icon_max_proxy, "总代理", data[4].num, data[4].proxy),
                TeamGroupBean(R.mipmap.icon_company, "分公司", data[5].num, data[5].proxy),
                TeamGroupBean(R.mipmap.icon_area_ceo, "大区总裁", data[6].num, data[6].proxy),
                TeamGroupBean(R.mipmap.icon_shareholder, "分红股东", data[7].num, data[7].proxy)
        )
        group_recycler_view.adapter = TeamGroupAdapter(mActivity, list) {
            ProxyListActivity.launch(mActivity, it.proxy,it.teamName)
        }

        tv_month_team.text = data[8].monthnum.toString()
        tv_team_total.text = data[9].num.toString()

    }

    override fun initListener() {
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_team_struct, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.team_struct) {
            //团队结构图
            return true
        }

        return super.onOptionsItemSelected(item)
    }


}