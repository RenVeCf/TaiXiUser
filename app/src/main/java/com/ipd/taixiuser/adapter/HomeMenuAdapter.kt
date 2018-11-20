package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.HomeBean
import com.ipd.taixiuser.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_home_menu.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class HomeMenuAdapter(val context: Context, private val list: List<HomeBean.IntroductionBean>?, private val itemClick: (info: HomeBean.IntroductionBean) -> Unit) : RecyclerView.Adapter<HomeMenuAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_menu, parent, false))

    }

    private val mRes = intArrayOf(R.mipmap.menu_business_school, R.mipmap.menu_travel, R.mipmap.menu_health_manage, R.mipmap.menu_e_commerce)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        holder.itemView.iv_image.setImageResource(mRes[position])
//        ImageLoader.loadNoPlaceHolderImg(context, info.img, holder.itemView.iv_image)
        holder.itemView.tv_title.text = info.title

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}