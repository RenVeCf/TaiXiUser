package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.CollectMatterBean
import com.ipd.taixiuser.bean.MatterBean
import com.ipd.taixiuser.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_matter.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class CollectMatterAdapter(val context: Context, private val list: List<CollectMatterBean>?, private val itemClick: (info: CollectMatterBean) -> Unit) : RecyclerView.Adapter<CollectMatterAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_matter, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        ImageLoader.loadImgWithPlaceHolder(context, info.material.img, R.mipmap.matter_img, holder.itemView.iv_product)
        holder.itemView.tv_title.text = info.material.title
        holder.itemView.tv_desc.text = info.material.brief
        holder.itemView.tv_time.text = info.material.ctime

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}