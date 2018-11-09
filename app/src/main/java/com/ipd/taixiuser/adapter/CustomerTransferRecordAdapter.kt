package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.CustomerTransferRecordBean
import kotlinx.android.synthetic.main.item_customer_transfer_record.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class CustomerTransferRecordAdapter(val context: Context, private val list: List<CustomerTransferRecordBean>?, private val itemClick: (info: CustomerTransferRecordBean) -> Unit) : RecyclerView.Adapter<CustomerTransferRecordAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_customer_transfer_record, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        holder.itemView.tv_transfer_name.text = "客户姓名：${info.customername}"
        holder.itemView.tv_transfer_time.text = info.ctime
        holder.itemView.tv_transfer_account.text = "客户账号：${info.phone}"
        holder.itemView.tv_operator.text = "转移人：${info.transfername}"
        holder.itemView.tv_receive_name.text = "接收人：${info.acceptname}"


        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}