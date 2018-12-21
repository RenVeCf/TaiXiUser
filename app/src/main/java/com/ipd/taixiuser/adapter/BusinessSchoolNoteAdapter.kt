package com.ipd.taixiuser.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.BusinessNoteBean
import com.ipd.taixiuser.ui.activity.businessschool.PublishBusinessNoteActivity
import kotlinx.android.synthetic.main.item_business_school_note.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class BusinessSchoolNoteAdapter(val context: Context, private val list: List<BusinessNoteBean>?, private val itemClick: (type: Int, info: BusinessNoteBean) -> Unit) : RecyclerView.Adapter<BusinessSchoolNoteAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_business_school_note, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        holder.itemView.tv_note_content.text = info.content
        holder.itemView.tv_sub_comment_time.text = "来自${info.from}"
        holder.itemView.tv_sub_comment_zan_num.text = info.ctime
        holder.itemView.tv_note_edit.setOnClickListener {
            PublishBusinessNoteActivity.launch(context as Activity, info.id, info.content)
        }
        holder.itemView.tv_note_delete.setOnClickListener {
            itemClick.invoke(1, info)
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}