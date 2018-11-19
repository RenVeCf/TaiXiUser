package com.ipd.taixiuser.platform.global

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDexApplication
import cn.jpush.android.api.JPushInterface
import com.mob.MobSDK
import io.rong.imkit.RongIM
import kotlin.properties.Delegates


/**
 * Created by jumpbox on 2018/6/6.
 */
class GlobalApplication : MultiDexApplication() {
    companion object {
        var mContext: Application by Delegates.notNull()
        fun getCurProcessName(ctx: Context, pid: Int): String? {
            val activityManager = ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val runningAppProcesses = activityManager.runningAppProcesses
            runningAppProcesses.forEach {
                if (it.pid == pid) {
                    return it.processName
                }
            }
            return null
        }


    }


    override fun onCreate() {
        super.onCreate()
        mContext = this@GlobalApplication

        RongIM.init(this)

        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)

        MobSDK.init(this)
    }


}