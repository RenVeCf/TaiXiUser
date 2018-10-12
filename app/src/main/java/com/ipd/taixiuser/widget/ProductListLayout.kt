package com.ipd.taixiuser.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.ipd.taixiuser.R
import kotlinx.android.synthetic.main.item_retail_add_product.view.*

class ProductListLayout : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()
        addProductView()
    }

    private val mProductList = arrayListOf("酵素青汁", "青汁饼干", "苹果汁", "葡萄干")

    private val mInflater by lazy { LayoutInflater.from(context) }
    fun addProductView() {
        val contentView = mInflater.inflate(R.layout.item_retail_add_product, this, false)
        contentView.spinner.setItems(mProductList)

        contentView.iv_remove_product.setOnClickListener {
            removeView(contentView)
        }
        addView(contentView)
    }

}