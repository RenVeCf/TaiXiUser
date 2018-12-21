package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BusinessTalkBean
import com.ipd.taixiuser.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_business_school_talk.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class BusinessSchoolTalkAdapter(val context: Context, private val list: List<BusinessTalkBean>?, private val itemClick: (type: Int, info: BusinessTalkBean) -> Unit) : RecyclerView.Adapter<BusinessSchoolTalkAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_business_school_talk, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        ImageLoader.loadAvatar(context, info.avatar, holder.itemView.civ_avatar)
        holder.itemView.tv_nickname.text = info.username
        holder.itemView.tv_date.text = info.ctime
        holder.itemView.tv_reply_content.text = info.content
        holder.itemView.tv_sub_comment_time.text = "来自${info.from}"

        if (info.subordinate == null || info.subordinate.isEmpty()) {
            holder.itemView.comments_view.visibility = View.GONE
        } else {
            holder.itemView.comments_view.visibility = View.VISIBLE
            holder.itemView.comments_view.adapter = BusinessSchoolTalkChildAdapter(context, info.subordinate) {

            }
        }

        holder.itemView.ll_sub_comment_zan.setOnClickListener {
            itemClick.invoke(1, info)
        }


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}