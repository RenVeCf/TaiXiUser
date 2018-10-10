package com.ipd.taixiuser.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ipd.taixiuser.bean.BannerBean;
import com.ipd.taixiuser.platform.http.HttpUrl;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if (path instanceof BannerBean) {
            BannerBean banner = (BannerBean) path;
            Glide.with(context).load(HttpUrl.IMAGE_URL + banner.img).into(imageView);
        }
    }


}