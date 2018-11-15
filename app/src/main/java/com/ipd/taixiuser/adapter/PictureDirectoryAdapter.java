package com.ipd.taixiuser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipd.taixiuser.R;
import com.ipd.taixiuser.bean.LocalDirectoryBean;
import com.ipd.taixiuser.imageload.ImageLoader;
import com.ipd.taixiuser.utils.BaseAdapter;

import java.util.List;


/**
 * Created by jumpbox on 2017/7/23.
 */

public class PictureDirectoryAdapter extends BaseAdapter<PictureDirectoryAdapter.ViewHolder> {

    private Context mContext;
    private List<LocalDirectoryBean> list;

    public PictureDirectoryAdapter(Context context, List<LocalDirectoryBean> list) {
        mContext = context;
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_local_directory, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_directory_name.setText(list.get(position).showName);
        ImageLoader.loadImgFromLocal(mContext, list.get(position).firstPicturePath, holder.iv_local_picture);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_directory_name;
        ImageView iv_local_picture;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_directory_name = itemView.findViewById(R.id.tv_directory_name);
            iv_local_picture = itemView.findViewById(R.id.iv_local_picture);
        }
    }
}
