package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.WithdrawProgressBean
import kotlinx.android.synthetic.main.item_withdraw_progress.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class WithdrawProgressAdapter(val context: Context, private val list: List<WithdrawProgressBean>?, private val itemClick: (info: WithdrawProgressBean) -> Unit) : RecyclerView.Adapter<WithdrawProgressAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_withdraw_progress, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        holder.itemView.tv_bank_no.text = info.showtime
        holder.itemView.tv_status.text = info.state


        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}