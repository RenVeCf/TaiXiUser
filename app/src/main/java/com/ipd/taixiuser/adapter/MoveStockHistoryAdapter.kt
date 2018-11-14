package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.MoveStockHistoryBean
import com.ipd.taixiuser.bean.ProductBean
import kotlinx.android.synthetic.main.item_move_stock_history.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class MoveStockHistoryAdapter(val context: Context, private val list: List<MoveStockHistoryBean>?, private val itemClick: (info: MoveStockHistoryBean) -> Unit) : RecyclerView.Adapter<MoveStockHistoryAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_move_stock_history, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        if (info.statue == 0) {
            holder.itemView.tv_operator.text = "接收:${info.receivername}"
            holder.itemView.tv_operation_level.text = "(${info.receivernickname})"
        } else {
            holder.itemView.tv_operator.text = "发出:${info.username}"
            holder.itemView.tv_operation_level.text = "(${info.usernickname})"
        }
        holder.itemView.tv_operation_date.text = info.ctime
        holder.itemView.tv_order_hint.text = info.ctime



        holder.itemView.product_recycler_view.adapter = OrderProductAdapter(context, info.fox, listOf(info.goods)) {
            itemClick.invoke(info)
        }

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}