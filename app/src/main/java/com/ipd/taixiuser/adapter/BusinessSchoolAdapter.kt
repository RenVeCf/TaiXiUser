package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BusinessSchoolBean
import com.ipd.taixiuser.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_business_school.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class BusinessSchoolAdapter(val context: Context, private val list: List<BusinessSchoolBean>?, private val itemClick: (info: BusinessSchoolBean) -> Unit) : RecyclerView.Adapter<BusinessSchoolAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_business_school, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        ImageLoader.loadNoPlaceHolderImg(context, info.img, holder.itemView.iv_product)
        holder.itemView.tv_title.text = info.title
        holder.itemView.tv_desc.text = info.brief
        holder.itemView.tv_study_num.text = "${info.studynum}人学习"


        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}