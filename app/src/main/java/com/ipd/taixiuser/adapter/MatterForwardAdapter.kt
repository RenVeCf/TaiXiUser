package com.ipd.taixiuser.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.jumpbox.jumpboxlibrary.utils.LoadingUtils
import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.MatterBean
import com.ipd.taixiuser.platform.global.GlobalApplication
import com.ipd.taixiuser.utils.PictureUtils
import com.ipd.taixiuser.utils.ShareUtil
import com.ipd.taixiuser.utils.StringUtils
import kotlinx.android.synthetic.main.item_matter_forward.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class MatterForwardAdapter(val context: Context, private val list: List<MatterBean>?, private val itemClick: (info: MatterBean) -> Unit) : RecyclerView.Adapter<MatterForwardAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_matter_forward, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        holder.itemView.tv_title.text = info.title
        holder.itemView.tv_desc.text = info.brief
        holder.itemView.tv_date.text = info.ctime
        holder.itemView.tv_forward_num.text = info.forwardnum.toString()

        val splitImagesV2 = StringUtils.splitImagesV2(info.images)
        holder.itemView.img_recycler_view.adapter = MediaPictureAdapter(context, splitImagesV2) { list, pos ->

        }


        holder.itemView.setOnClickListener {
            LoadingUtils.show(context)
            PictureUtils.savePhotos(context, splitImagesV2) { code, files ->
                LoadingUtils.dismiss()
                if (code == 0) {
                    ShareUtil.sendMoreImage(context, ArrayList(files), "分享")
                } else {
                    ToastCommom.getInstance().show(GlobalApplication.mContext, "操作失败")
                }
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}