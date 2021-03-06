package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.SystemMessageBean
import com.ipd.taixiuser.utils.StringUtils
import kotlinx.android.synthetic.main.item_system_message.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class SystemMessageAdapter(val context: Context, private val list: List<SystemMessageBean>?, private val itemClick: (info: SystemMessageBean) -> Unit) : RecyclerView.Adapter<SystemMessageAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_system_message, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        holder.itemView.tv_time.text = StringUtils.getDateBySecond(info.pushtime)
        holder.itemView.tv_msg.text = info.content

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}