package com.ipd.taixiuser.bean;


import com.ipd.taixiuser.platform.http.Response;

import java.io.Serializable;

/**
 * Created by jumpbox on 2017/7/26.
 */

public class PictureBean implements Serializable {
    public String path;
    public String url;
    public Response<UploadResultBean> response;

    public PictureBean(String path) {
        this.path = path;
    }
}
