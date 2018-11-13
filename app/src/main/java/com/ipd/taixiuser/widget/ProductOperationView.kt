package com.ipd.taixiuser.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.ipd.taixiuser.R
import kotlinx.android.synthetic.main.layout_product_edit.view.*

class ProductOperationView : FrameLayout {
    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    var MIN_NUM = 0
    private var maxNum = 999
    val mContentView by lazy { LayoutInflater.from(context).inflate(R.layout.layout_product_edit, this, false) }

    private fun init() {
        addView(mContentView)

        mContentView.iv_sub.setOnClickListener {
            var num = getNum()
            if (num <= MIN_NUM) {
                return@setOnClickListener
            } else {
                num -= 1
            }
            mOnCartNumChangeListener?.onNumChange(mContentView.tv_num.text.toString().toInt(), num)
            mContentView.tv_num.text = num.toString()
            checkOperationStatus()
        }

        mContentView.iv_add.setOnClickListener {
            var num = getNum()
            num += 1
            mOnCartNumChangeListener?.onNumChange(mContentView.tv_num.text.toString().toInt(), num)
            mContentView.tv_num.text = num.toString()
            checkOperationStatus()
        }
        checkOperationStatus()
    }

    fun getNum(): Int {
        val numStr = mContentView.tv_num.text.toString().trim()
        return numStr.toInt()
    }

    fun setNum(num: Int) {
        mContentView.tv_num.text = num.toString()
    }

    fun setMinNum(minNum: Int) {
        MIN_NUM = minNum
        if (getNum() < minNum) {
            setNum(minNum)
        }
        checkOperationStatus()
    }


    fun setMaxNum(maxNum: Int) {
        this.maxNum = maxNum
        checkOperationStatus()
    }

    private fun checkOperationStatus() {
        mContentView.iv_sub.isEnabled = getNum() > MIN_NUM
        mContentView.iv_add.isEnabled = getNum() < maxNum
    }


    private var mOnCartNumChangeListener: OnCartNumChangeListener? = null
    fun setOnCartNumChangeListener(listener: OnCartNumChangeListener) {
        mOnCartNumChangeListener = listener
    }

    interface OnCartNumChangeListener {
        fun onNumChange(lastNum: Int, num: Int)
    }

}