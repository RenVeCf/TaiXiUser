package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.ShipOrderBean
import kotlinx.android.synthetic.main.item_ship_order.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class ShipOrderAdapter(val context: Context, private val list: List<ShipOrderBean>?, private val itemClick: (info: ShipOrderBean) -> Unit) : RecyclerView.Adapter<ShipOrderAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ship_order, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        holder.itemView.product_recycler_view.adapter = ShipProductAdapter(context, arrayListOf(ShipOrderBean(), ShipOrderBean(), ShipOrderBean()), {

        })


        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}