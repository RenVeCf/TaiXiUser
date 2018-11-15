package com.ipd.taixiuser.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils;
import com.ipd.taixiuser.R;
import com.ipd.taixiuser.bean.PictureBean;
import com.ipd.taixiuser.bean.UploadResultBean;
import com.ipd.taixiuser.imageload.ImageLoader;
import com.ipd.taixiuser.platform.http.HttpUrl;
import com.ipd.taixiuser.ui.activity.PhotoSelectActivity;
import com.ipd.taixiuser.ui.activity.PictureLookActivity;
import com.ipd.taixiuser.utils.BaseAdapter;
import com.ipd.taixiuser.utils.UploadUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jumpbox on 2017/7/23.
 */

public class PictureAdapter extends BaseAdapter<PictureAdapter.ViewHolder> {

    private Context mContext;
    private List<PictureBean> list;
    private int maxImageCount = 0;
    private OnItemClickListener mOnItemClickListener;

    public PictureAdapter(Context context, List<PictureBean> list, int maxImageCount) {
        mContext = context;
        this.list = list;
        this.maxImageCount = maxImageCount;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_picture, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.e("tag", "onBindViewHolder:" + position);

        holder.tv_error.setVisibility(View.GONE);
        holder.iv_delete.setVisibility(View.GONE);

        if (list.size() < maxImageCount && position == list.size()) {
            holder.iv_image.setImageResource(R.mipmap.add_picture);
        } else {
            holder.iv_delete.setVisibility(View.VISIBLE);
            ImageLoader.loadImgFromLocal(mContext, list.get(position).path, holder.iv_image);

            final PictureBean info = list.get(position);
            if (TextUtils.isEmpty(info.url)) {
                if (info.response == null) {
                    //图片还没有上传
                    LogUtils.e("tag", "图片还没有上传...");
                    holder.progress_bar.setProgress(0);
                    holder.progress_bar.setVisibility(View.VISIBLE);
                    //上传图片
                    info.response = UploadUtils.uploadPic(mContext, false, info.path, new UploadUtils.UploadCallback() {
                        @Override
                        public void onProgress(int progress) {
                            holder.progress_bar.setProgress(progress);
                        }

                        @Override
                        public void uploadSuccess(UploadResultBean resultBean) {
                            holder.progress_bar.setVisibility(View.GONE);
                            info.url = resultBean.data[0];
                            info.response = null;
                        }

                        @Override
                        public void uploadFail(String errMsg) {
                            info.response = null;
                            holder.progress_bar.setVisibility(View.GONE);
                            holder.tv_error.setVisibility(View.VISIBLE);
                            holder.tv_error.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    notifyItemChanged(position);
                                }
                            });
                        }
                    });
                }
            }
        }

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除
                if (mOnItemClickListener != null) mOnItemClickListener.showDeleteDialog(position);
                else showDeleteDialog(position);
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tag", "onClick:" + position);
                if (position == list.size()) {
                    //添加图片
                    if (mOnItemClickListener != null) mOnItemClickListener.choosePicture(position);
                    else choosePicture();
                } else {
                    PictureBean info = list.get(position);
                    ArrayList<String> picList = new ArrayList<>();
                    if (!TextUtils.isEmpty(info.url)) {
                        picList.add(HttpUrl.IMAGE_URL + info.url);
                    } else {
                        picList.add(info.path);
                    }
                    PictureLookActivity.launch((Activity) mContext, picList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list.size() == maxImageCount) {
            return list.size();
        }

        return list.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        ProgressBar progress_bar;
        TextView tv_error;
        ImageView iv_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_image);
            progress_bar = itemView.findViewById(R.id.progress_bar);
            tv_error = itemView.findViewById(R.id.tv_error);
            iv_delete = itemView.findViewById(R.id.iv_delete);
        }
    }

    // 头像
    public void choosePicture() {
        PhotoSelectActivity.launch((Activity) mContext, maxImageCount - list.size());
    }

    public void showDeleteDialog(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("确定要移除该图？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (list.get(pos).response != null) {
                    list.get(pos).response.unsubscribe();//取消订阅
                    list.get(pos).response = null;
                }
                list.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, getItemCount());
            }
        });
        builder.show();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public interface OnItemClickListener {
        void choosePicture(int position);

        void showDeleteDialog(int position);
    }
}
