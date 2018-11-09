package com.ipd.taixiuser.utils;

import android.content.Context;

import com.ipd.taixiuser.bean.UploadResultBean;
import com.ipd.taixiuser.platform.http.ApiManager;
import com.ipd.taixiuser.platform.http.Response;
import com.ipd.taixiuser.platform.http.RxScheduler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by jumpbox on 2017/8/15.
 */

public class UploadUtils {

    public static Response<UploadResultBean> uploadPic(Context context, boolean needDialog, String filePath, final UploadCallback callback) {
        final Map<String, RequestBody> requestMap = new HashMap<>();
        requestMap.put("image\"; filename=\"" + new File(filePath).getName(),
                new ProgressRequestBody(RequestBody.create(MediaType.parse("image/png"), new File(filePath)), new ProgressRequestBody.UploadCallbacks() {
                    @Override
                    public void onProgressUpdate(int percentage) {
                        if (callback != null) {
                            callback.onProgress(percentage);
                        }
                    }
                }));

        Response<UploadResultBean> response;
        ApiManager.getService().uploadPicture(requestMap)
                .compose(RxScheduler.Companion.<UploadResultBean>applyScheduler())
                .subscribe(response = new Response<UploadResultBean>(context, needDialog) {
                    @Override
                    protected void _onNext(UploadResultBean result) {
                        if (result.code == 200) {
                            if (callback != null) callback.uploadSuccess(result);
                        } else {
                            if (callback != null) callback.uploadFail(result.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onCompleted();
                        if (callback != null) callback.uploadFail("上传失败,请检查网络");
                    }
                });
        return response;
    }


    public interface UploadCallback {
        void onProgress(int progress);

        void uploadSuccess(UploadResultBean resultBean);

        void uploadFail(String errMsg);

    }


}
