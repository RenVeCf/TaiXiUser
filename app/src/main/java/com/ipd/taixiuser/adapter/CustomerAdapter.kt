package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.CustomerBean
import com.ipd.taixiuser.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_customer.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class CustomerAdapter(val context: Context, private val list: List<CustomerBean>?, private val itemClick: (info: CustomerBean) -> Unit) : RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_customer, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]


        ImageLoader.loadAvatar(context, info.avatar, holder.itemView.iv_product)
        holder.itemView.tv_customer_name.text = info.username
        holder.itemView.tv_level.text = "(${info.proxy})"
        holder.itemView.tv_customer_remark.text = "备注:${info.remark}"
        holder.itemView.tv_customer_phone.text = info.phone






        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}