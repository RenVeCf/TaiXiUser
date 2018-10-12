package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.TeamGroupBean
import kotlinx.android.synthetic.main.item_mine_team.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class TeamGroupAdapter(val context: Context, private val list: List<TeamGroupBean>?, private val itemClick: (info: TeamGroupBean) -> Unit) : RecyclerView.Adapter<TeamGroupAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mine_team, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        holder.itemView.iv_group.setImageResource(info.res)
        holder.itemView.tv_group_name.text = info.teamName
        holder.itemView.tv_group_num.text = "(${info.num})"


        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}