package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BankCardBean
import com.ipd.taixiuser.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_bank_card.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class BankCardListAdapter(val context: Context, private val list: List<BankCardBean>?, private val itemClick: (info: BankCardBean) -> Unit) : RecyclerView.Adapter<BankCardListAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bank_card, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        ImageLoader.loadNoPlaceHolderImg(context, info.newbank.img, holder.itemView.iv_bank_icon)
        holder.itemView.tv_bank_name.text = info.newbank.bankname
        holder.itemView.tv_bank_no.text = "尾号 ${info.tailnumber}"

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}