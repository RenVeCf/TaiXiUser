package com.ipd.taixiuser.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.imageload.ImageLoader
import com.ipd.taixiuser.ui.activity.PictureLookActivity
import kotlinx.android.synthetic.main.item_picture.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class ReturnPictureAdapter(val context: Context, private val list: List<String>?) : RecyclerView.Adapter<ReturnPictureAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_picture, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        ImageLoader.loadNoPlaceHolderImg(context, info, holder.itemView.iv_image)

        holder.itemView.setOnClickListener {
            PictureLookActivity.launch(context as Activity, ArrayList(list), position, PictureLookActivity.URL)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}