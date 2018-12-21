package com.ipd.taixiuser.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ipd.taixiuser.R;


public class ReplyDialog extends DialogFragment {
    //点击发表，内容不为空时的回调
    public SendBackListener sendBackListener;

    public interface SendBackListener {
        void sendBack(String inputText);
    }

    private String texthint;

    private Dialog dialog;
    private EditText replyView;
    private int numconut = 300;
    private String tag = null;

    public ReplyDialog() {
    }


    public ReplyDialog(String texthint, SendBackListener sendBackListener) {//提示文字
        this.texthint = texthint;
        this.sendBackListener = sendBackListener;

    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        dialog = new Dialog(getActivity(), R.style.ReplyDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View contentview = View.inflate(getActivity(), R.layout.layout_reply_comment, null);
        dialog.setContentView(contentview);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.alpha = 1;
        lp.dimAmount = 0.5f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        replyView = (EditText) contentview.findViewById(R.id.et_reply);
        replyView.setHint(texthint);
        final TextView tv_send = (TextView) contentview.findViewById(R.id.tv_reply);
        replyView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_send.setEnabled(!TextUtils.isEmpty(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                sendBackListener.sendBack(replyView.getText().toString().trim());
            }
        });
        replyView.setFocusable(true);
        replyView.setFocusableInTouchMode(true);
        replyView.requestFocus();
        final Handler hanler = new Handler();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                hanler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideSoftkeyboard();
                    }
                }, 200);

            }
        });
        return dialog;
    }


    public void hideSoftkeyboard() {
        try {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException e) {

        }
    }
}
