package com.ipd.taixiuser.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by jumpbox on 2017/8/4.
 */

public class MessageDialog {


    public static class Builder {
        private AlertDialog mDialog;
        private Context mContext;
        private final AlertDialog.Builder builder;

        public Builder(Context context) {
            mContext = context;
            builder = new AlertDialog.Builder(mContext);
        }

        public Builder setTitle(String title) {
            builder.setTitle(title);
            return this;
        }

        public Builder setMessage(String message) {
            builder.setMessage(message);
            return this;
        }

        public Builder setCommit(String commitStr, final OnClickListener onClickListener) {
            builder.setPositiveButton(commitStr, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (onClickListener != null) {
                        onClickListener.onClick(Builder.this);
                    }
                }
            });
            return this;
        }

        public Builder setCancel(String cancelStr, final OnClickListener onClickListener) {
            builder.setNegativeButton(cancelStr, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (onClickListener != null) {
                        onClickListener.onClick(Builder.this);
                    }
                }
            });
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            builder.setCancelable(cancelable);
            return this;
        }

        public void show() {
            mDialog = builder.show();
        }

        public void dismiss() {
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }

        }

    }

    public interface OnClickListener {
        void onClick(Builder builder);

    }
}
