package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.ProductBean

/**
 * Created by jumpbox on 2017/8/31.
 */
class ProxyReplenishProductAdapter(val context: Context, private val list: List<ProductBean>?, private val itemClick: (info: ProductBean) -> Unit) : RecyclerView.Adapter<ProxyReplenishProductAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_replenish_product, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]


        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}