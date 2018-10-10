package com.ipd.taixiuser.platform.global;

import android.app.Activity;
import android.content.Context;

import com.ipd.taixiuser.ui.activity.account.LoginActivity;
import com.ipd.taixiuser.utils.MessageDialog;


/**
 * Created by jumpbox on 2017/8/16.
 */

public class AuthUtils {

    public static boolean isLoginAndShowDialog(final Context context) {
        if (!GlobalParam.isLogin()) {
            new MessageDialog.Builder(context)
                    .setTitle("提示")
                    .setMessage("您还没有登录，是否前往登录?")
                    .setCommit("去登录", new MessageDialog.OnClickListener() {
                        @Override
                        public void onClick(MessageDialog.Builder builder) {
                            builder.dismiss();
                            LoginActivity.Companion.launch((Activity) context);
                        }
                    }).setCancel("取消",
                    new MessageDialog.OnClickListener() {
                        @Override
                        public void onClick(MessageDialog.Builder builder) {
                            builder.dismiss();
                        }
                    })
                    .show();

            return false;
        }
        return true;
    }

}
