package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.ActionMessageBean
import kotlinx.android.synthetic.main.item_system_message.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class ActionMessageAdapter(val context: Context, private val list: List<ActionMessageBean>?, private val itemClick: (info: ActionMessageBean) -> Unit) : RecyclerView.Adapter<ActionMessageAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_system_message, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        holder.itemView.tv_msg.text = info.content

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}