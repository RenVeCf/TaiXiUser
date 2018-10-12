package com.ipd.taixiuser

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.ipd.taixiuser.platform.global.AuthUtils
import com.ipd.taixiuser.ui.BaseActivity
import com.ipd.taixiuser.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private val fragmentManager: FragmentManager by lazy { supportFragmentManager }

    override fun getBaseLayout(): Int = R.layout.activity_main

    override fun initView(bundle: Bundle?) {
        changePage(0)
    }

    override fun loadData() {
    }

    override fun initListener() {
        tabs.forEachIndexed { index, layout ->
            layout.setOnClickListener {
                if (it == ll_manage || it == ll_mine) {
                    //登录
                    if (!AuthUtils.isLoginAndShowDialog(mActivity)) {
                        return@setOnClickListener
                    }
                }
                changePage(index)
            }
        }
    }


    private fun changePage(pos: Int) {
        setTabChecked(pos)
        setTabSelection(pos)
    }


    private val tabs: Array<LinearLayout> by lazy { arrayOf(ll_home, ll_matter, ll_manage, ll_business_school, ll_mine) }
    private fun setTabChecked(pos: Int) {
        tabs.forEachIndexed { index, layout ->
            layout.getChildAt(0).isSelected = pos == index
            layout.getChildAt(1).isSelected = pos == index
            if (pos == index) {
                var animView: View = layout.getChildAt(0)
                val mainTabAnim = AnimationUtils.loadAnimation(mActivity, R.anim.main_tab_anim)
                animView.startAnimation(mainTabAnim)
            }
        }
    }


    private var homeFragment: HomeFragment? = null
    private var matterFragment: MatterFragment? = null
    private var manageFragment: ManageFragment? = null
    private var businessSchoolFragment: BusinessSchoolFragment? = null
    private var mineFragment: MineFragment? = null

    /**
     * 选中的页面
     *
     * @param position
     */
    private fun setTabSelection(position: Int) {
        val transaction = fragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0 -> if (homeFragment == null) {
                homeFragment = HomeFragment()
                transaction.add(R.id.fl_container, homeFragment)
            } else {
                transaction.show(homeFragment)
            }
            1 -> if (matterFragment == null) {
                matterFragment = MatterFragment()
                transaction.add(R.id.fl_container, matterFragment)
            } else {
                transaction.show(matterFragment)
            }
            2 -> if (manageFragment == null) {
                manageFragment = ManageFragment()
                transaction.add(R.id.fl_container, manageFragment)
            } else {
                transaction.show(manageFragment)
            }
            3 -> if (businessSchoolFragment == null) {
                businessSchoolFragment = BusinessSchoolFragment()
                transaction.add(R.id.fl_container, businessSchoolFragment)
            } else {
                transaction.show(businessSchoolFragment)
            }
            4 -> if (mineFragment == null) {
                mineFragment = MineFragment()
                transaction.add(R.id.fl_container, mineFragment)
            } else {
                transaction.show(mineFragment)

            }
        }
        // 提交
        transaction.commit()
    }

    /**
     * 隐藏所有的页面
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment)
        }
        if (matterFragment != null) {
            transaction.hide(matterFragment)
        }
        if (manageFragment != null) {
            transaction.hide(manageFragment)
        }
        if (businessSchoolFragment != null) {
            transaction.hide(businessSchoolFragment)
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment)
        }
    }


    override fun onAttachFragment(fragment: Fragment?) {
        if (fragment == null) return
        when (fragment) {
            is HomeFragment -> homeFragment = fragment
            is MatterFragment -> matterFragment = fragment
            is ManageFragment -> manageFragment = fragment
            is BusinessSchoolFragment -> businessSchoolFragment = fragment
            is MineFragment -> mineFragment = fragment
        }
    }
}
