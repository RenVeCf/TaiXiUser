package com.ipd.taixiuser.ui.activity.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taixiuser.R
import com.ipd.taixiuser.adapter.TeamGroupAdapter
import com.ipd.taixiuser.bean.TeamGroupBean
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
        val list = listOf(
                TeamGroupBean(R.mipmap.icon_retail, "零售", "10"),
                TeamGroupBean(R.mipmap.icon_vip, "VIP", "11"),
                TeamGroupBean(R.mipmap.icon_proxy, "代理", "12"),
                TeamGroupBean(R.mipmap.icon_max_proxy, "总代理", "13"),
                TeamGroupBean(R.mipmap.icon_company, "分公司", "14"),
                TeamGroupBean(R.mipmap.icon_area_ceo, "大区总裁", "10"),
                TeamGroupBean(R.mipmap.icon_shareholder, "分红股东", "10")

        )
        group_recycler_view.adapter = TeamGroupAdapter(mActivity, list, {
            ProxyListActivity.launch(mActivity)
        })
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