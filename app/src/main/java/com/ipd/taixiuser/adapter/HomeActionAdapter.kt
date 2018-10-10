package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.HomeActionBean
import kotlinx.android.synthetic.main.item_home_action.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class HomeActionAdapter(val context: Context, private val list: List<HomeActionBean>?, private val itemClick: (info: HomeActionBean) -> Unit) : RecyclerView.Adapter<HomeActionAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_action, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        holder.itemView.iv_icon.setImageResource(info.res)
        holder.itemView.tv_title.text = info.title
        holder.itemView.tv_msg.visibility = if (TextUtils.isEmpty(info.msg)) View.GONE else View.VISIBLE
        holder.itemView.tv_msg.text = info.msg
        holder.itemView.tv_unread.visibility = if (info.unread == 0) View.GONE else View.VISIBLE
        holder.itemView.tv_unread.text = info.unread.toString()

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}