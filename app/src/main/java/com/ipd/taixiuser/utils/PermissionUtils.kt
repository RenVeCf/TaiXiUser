package com.ipd.taixiuser.utils

import android.content.Context
import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom
import com.ipd.taixiuser.bean.BaseResult
import com.ipd.taixiuser.bean.UserInfoBean
import com.ipd.taixiuser.platform.global.GlobalApplication
import com.ipd.taixiuser.platform.global.GlobalParam
import com.ipd.taixiuser.platform.http.ApiManager
import com.ipd.taixiuser.platform.http.Response
import com.ipd.taixiuser.platform.http.RxScheduler

object PermissionUtils {

    fun hasPermission(context: Context, callback: (hasPermission: Boolean, errMsg: String) -> Unit) {
        ApiManager.getService().userInfo(GlobalParam.getUserId())
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<UserInfoBean>>(context, true) {
                    override fun _onNext(result: BaseResult<UserInfoBean>) {
                        if (result.code == 200) {
                            if (result.data.proxy.toInt() >= PromoteInfo.LEADER_PROXY) {
                                callback.invoke(true, "")
                            } else {
                                callback.invoke(false, "等级不足，请先成为总代理")
                            }

                        } else {
                            ToastCommom.getInstance().show(GlobalApplication.mContext, result.msg)
                        }
                    }
                })
    }
}