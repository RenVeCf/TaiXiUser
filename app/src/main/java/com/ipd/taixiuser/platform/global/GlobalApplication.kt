package com.ipd.taixiuser.platform.global

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDexApplication
import cn.jpush.android.api.JPushInterface
import com.mob.MobSDK
import io.rong.imkit.RongIM
import kotlin.properties.Delegates
import com.alipay.android.phone.mrpc.core.HttpException.NETWORK_UNAVAILABLE
import android.net.wifi.p2p.WifiP2pDevice.CONNECTED
import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom
import io.rong.imlib.RongIMClient


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

        RongIM.setConnectionStatusListener {
            when (it) {
                RongIMClient.ConnectionStatusListener.ConnectionStatus.KICKED_OFFLINE_BY_OTHER_CLIENT//用户账户在其他设备登录，本机会被踢掉线
                -> {
                    ToastCommom.getInstance().show(mContext,"用户在其他设备登录")
                    GlobalParam.onExitUser()
                }
            }
        }


    }


}