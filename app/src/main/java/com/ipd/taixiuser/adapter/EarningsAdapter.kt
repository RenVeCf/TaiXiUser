package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.EarningsBean
import kotlinx.android.synthetic.main.item_earnings.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class EarningsAdapter(val context: Context, private val list: List<EarningsBean>?, private val itemClick: (info: EarningsBean) -> Unit) : RecyclerView.Adapter<EarningsAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_earnings, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        holder.itemView.tv_consume_name.text = info.content
        holder.itemView.tv_record_desc.text = info.ctime
        holder.itemView.tv_record_num.text = info.price

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}