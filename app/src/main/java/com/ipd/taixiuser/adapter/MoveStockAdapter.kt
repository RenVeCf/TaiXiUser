package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.MoveStockBean
import com.ipd.taixiuser.imageload.ImageLoader
import com.ipd.taixiuser.widget.ProductOperationView
import kotlinx.android.synthetic.main.item_factory_ship.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class MoveStockAdapter(val context: Context, private val list: List<MoveStockBean>?, private val itemClick: (info: MoveStockBean) -> Unit) : RecyclerView.Adapter<MoveStockAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_factory_ship, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        ImageLoader.loadNoPlaceHolderImg(context, info.goods.img, holder.itemView.iv_product)
        holder.itemView.tv_title.text = info.goods.name
        holder.itemView.tv_product_desc.text = "库存：${info.fox}箱"
        holder.itemView.fox_operation_view.setMaxNum(info.fox)
        holder.itemView.fox_operation_view.setNum(info.goods.chooseNum)
        holder.itemView.fox_operation_view.setOnCartNumChangeListener(object : ProductOperationView.OnCartNumChangeListener {
            override fun onNumChange(lastNum: Int, num: Int) {
                info.goods.chooseNum = num
            }
        })

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}