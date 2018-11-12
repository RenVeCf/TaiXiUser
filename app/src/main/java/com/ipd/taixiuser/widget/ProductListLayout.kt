package com.ipd.taixiuser.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.ipd.taixiuser.R
import com.ipd.taixiuser.bean.ProductBean
import com.ipd.taixiuser.bean.StartShipProductBean
import kotlinx.android.synthetic.main.item_retail_add_product.view.*

class ProductListLayout : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private var mProductList: List<ProductBean>? = null
    fun init(productList: List<ProductBean>) {
        removeAllViews()
        mProductList = productList
        addProductView()
    }


    private val mInflater by lazy { LayoutInflater.from(context) }
    fun addProductView() {
        if (mProductList == null) return
        val contentView = mInflater.inflate(R.layout.item_retail_add_product, this, false)
        contentView.spinner.setItems(mProductList!!)

        contentView.iv_remove_product.setOnClickListener {
            removeView(contentView)
        }
        addView(contentView)
    }


    fun getShipProductList(callback: (msg: String, list: List<StartShipProductBean>?) -> Unit) {
        val list = ArrayList<StartShipProductBean>()
        for (index in 0 until childCount) {
            val contentView = getChildAt(index)
            val product = mProductList?.get(contentView.spinner.selectedIndex)
            val fox = contentView.et_fox.text.toString().trim()
            val box = contentView.et_box.text.toString().trim()
            if (product == null) {
                callback.invoke("商品信息不能为空", null)
                return
            }
            if (TextUtils.isEmpty(fox) && TextUtils.isEmpty(box)) {
                callback.invoke("请输入商品数量", null)
                return
            }
            list.add(StartShipProductBean(product.id.toString(), fox, box))
        }
        callback.invoke("", list)
    }

}