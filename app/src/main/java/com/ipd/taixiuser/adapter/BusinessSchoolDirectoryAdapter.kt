package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BusinessDirectoryBean
import kotlinx.android.synthetic.main.item_business_school_directory.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class BusinessSchoolDirectoryAdapter(val context: Context, private val list: List<BusinessDirectoryBean>?, private val itemClick: (type: Int, info: BusinessDirectoryBean) -> Unit) : RecyclerView.Adapter<BusinessSchoolDirectoryAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_business_school_directory, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        holder.itemView.tv_directory_name.text = info.title


        holder.itemView.setOnClickListener {
            itemClick.invoke(0, info)
        }
        holder.itemView.iv_arrow.setOnClickListener {
            itemClick.invoke(1, info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}