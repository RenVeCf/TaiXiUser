package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.OrderBean
import kotlinx.android.synthetic.main.item_order.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class OrderAdapter(val context: Context, private val list: List<OrderBean>?, private val itemClick: (info: OrderBean) -> Unit) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        holder.itemView.tv_order_no.text = "订单编号:${info.ordercode}"
        holder.itemView.tv_order_date.text = info.ctime
        holder.itemView.tv_order_price.text = "￥ ${info.expence}"


        holder.itemView.product_recycler_view.adapter = OrderProductAdapter(context, info.fox, listOf(info.goods)) {
            itemClick.invoke(info)
        }

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}