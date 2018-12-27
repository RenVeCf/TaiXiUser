package com.ipd.taixiuser.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil
import com.ipd.taixiuser.R
import com.ipd.taixiuser.utils.PromoteInfo
import kotlinx.android.synthetic.main.activity_promote.view.*


class PromoteLayout : ConstraintLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    val mLightPathPaint: Paint by lazy {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = resources.getColor(R.color.colorPrimaryDark)
        paint.strokeWidth = DensityUtil.dip2px(context, 2f).toFloat()
        paint
    }
    val mDarkPathPaint: Paint by lazy {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.parseColor("#ECECEC")
        paint.strokeWidth = DensityUtil.dip2px(context, 2f).toFloat()
        paint.pathEffect = DashPathEffect(floatArrayOf(8f, 6f), 0f)
        paint
    }

    private var mCurLevel = PromoteInfo.COMPANY
    fun setCurLevel(level: Int) {
        mCurLevel = level
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //连线
        drawLine(canvas, getLinePoint(ll_retail, ll_gift_box), mCurLevel >= PromoteInfo.getLevelByResId(ll_gift_box.id))
        drawText(canvas, getTextPoint(ll_retail), mCurLevel >= PromoteInfo.getLevelByResId(ll_retail.id))
        drawLine(canvas, getLinePoint(ll_gift_box, ll_vip), mCurLevel >= PromoteInfo.getLevelByResId(ll_vip.id))
        drawText(canvas, getTextPoint(ll_gift_box), mCurLevel >= PromoteInfo.getLevelByResId(ll_gift_box.id))
        drawLine(canvas, getLinePoint(ll_vip, ll_proxy), mCurLevel >= PromoteInfo.getLevelByResId(ll_proxy.id))
        drawText(canvas, getTextPoint(ll_vip), mCurLevel >= PromoteInfo.getLevelByResId(ll_vip.id))
        drawLine(canvas, getLinePoint(ll_proxy, ll_leader_proxy), mCurLevel >= PromoteInfo.getLevelByResId(ll_leader_proxy.id))
        drawText(canvas, getTextPoint(ll_proxy), mCurLevel >= PromoteInfo.getLevelByResId(ll_proxy.id))
        drawLine(canvas, getLinePoint(ll_leader_proxy, ll_company), mCurLevel >= PromoteInfo.getLevelByResId(ll_company.id))
        drawText(canvas, getTextPoint(ll_leader_proxy), mCurLevel >= PromoteInfo.getLevelByResId(ll_leader_proxy.id))
        drawText(canvas, getTextPoint(ll_company), mCurLevel >= PromoteInfo.getLevelByResId(ll_company.id))
        drawLine(canvas, getLinePoint(ll_company, ll_area_ceo), mCurLevel == PromoteInfo.getLevelByResId(ll_area_ceo.id))
        drawLine(canvas, getLinePoint(ll_company, ll_shareholder), mCurLevel == PromoteInfo.getLevelByResId(ll_shareholder.id))

        for (index in 0 until childCount) {
            if (getChildAt(index).id == R.id.tv_next_level_hint) continue
            PromoteInfo.setStyleByLevel(context, getChildAt(index) as ViewGroup, mCurLevel)
        }
    }

    private val mTextPoint by lazy {
        val paint = Paint()
        paint.color = resources.getColor(R.color.colorPrimaryDark)
        paint.isAntiAlias = true
        paint.textSize = resources.getDimension(R.dimen.small_text_size)
        paint.strokeWidth = 2f
        paint
    }

    private fun drawText(canvas: Canvas?, textPoint: TextPoint, isLight: Boolean) {
        if (isLight) {
            canvas?.drawText("已完成", textPoint.startX, textPoint.startY, mTextPoint)
        }
    }

    private fun drawLine(canvas: Canvas?, linePoint: LinePoint, isLight: Boolean) {
        canvas?.drawLine(linePoint.startX, linePoint.startY, linePoint.endX, linePoint.endY, if (isLight) mLightPathPaint else mDarkPathPaint)
    }

    private fun getLinePoint(startView: View, endView: View): LinePoint {
        return LinePoint(getCenterX(startView), startView.top.toFloat(), getCenterX(endView), endView.bottom.toFloat())
    }

    private fun getTextPoint(startView: View): TextPoint {
        return TextPoint(startView.right + 100f, startView.bottom.toFloat() - startView.measuredHeight / 2 + mTextPoint.textSize / 2)
    }

    private fun getCenterX(view: View): Float {
        return view.x + view.measuredWidth / 2
    }


    data class LinePoint(val startX: Float, val startY: Float, val endX: Float, val endY: Float)
    data class TextPoint(val startX: Float, val startY: Float)


}