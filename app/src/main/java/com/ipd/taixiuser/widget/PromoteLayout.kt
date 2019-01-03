package com.ipd.taixiuser.widget

import android.content.Context
import android.graphics.*
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
        paint.color = resources.getColor(R.color.black)
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
        drawLine(canvas, getLinePoint(ll_retail, ll_gift_box), getCurStatus(ll_gift_box.id))
        drawText(canvas, getTextPoint(ll_retail), getCurStatus(ll_retail.id))
        drawLine(canvas, getLinePoint(ll_gift_box, ll_vip), getCurStatus(ll_vip.id))
        drawText(canvas, getTextPoint(ll_gift_box), getCurStatus(ll_gift_box.id))
        drawLine(canvas, getLinePoint(ll_vip, ll_proxy), getCurStatus(ll_proxy.id))
        drawText(canvas, getTextPoint(ll_vip), getCurStatus(ll_vip.id))
        drawLine(canvas, getLinePoint(ll_proxy, ll_leader_proxy), getCurStatus(ll_leader_proxy.id))
        drawText(canvas, getTextPoint(ll_proxy), getCurStatus(ll_proxy.id))
        drawLine(canvas, getLinePoint(ll_leader_proxy, ll_company), getCurStatus(ll_company.id))
        drawText(canvas, getTextPoint(ll_leader_proxy), getCurStatus(ll_leader_proxy.id))
        drawText(canvas, getTextPoint(ll_company), getCurStatus(ll_company.id))
        drawLine(canvas, getLinePoint(ll_company, ll_area_ceo), getCurStatus(ll_area_ceo.id))
        drawLine(canvas, getLinePoint(ll_company, ll_shareholder), getCurStatus(ll_shareholder.id))


        iv_area_ceo.setImageResource(PromoteInfo.getIconByResId(ll_area_ceo,getCurStatus(ll_area_ceo.id)))
        iv_shareholder.setImageResource(PromoteInfo.getIconByResId(ll_shareholder,getCurStatus(ll_shareholder.id)))
        for (index in 0 until childCount) {
            val childView = getChildAt(index)
            if (childView.id == R.id.tv_next_level_hint || childView.id == R.id.ll_area_ceo || childView.id == R.id.ll_shareholder) continue
            drawLevel(canvas, getIconPoint(childView), PromoteInfo.getIconByResId(childView,getCurStatus(childView.id)))
        }
    }

    private fun drawLevel(canvas: Canvas?, iconPoint: TextPoint, resId: Int) {
        canvas?.drawBitmap(BitmapFactory.decodeResource(resources, resId), iconPoint.startX, iconPoint.startY, mTextPoint)
    }


    private fun getCurStatus(resId: Int): Int {
        val resLevel = PromoteInfo.getLevelByResId(resId)
        return when {
            mCurLevel < resLevel -> 0
            mCurLevel == resLevel -> 1
            else -> 2
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

    private fun drawText(canvas: Canvas?, textPoint: TextPoint, status: Int) {
        when (status) {
            2 -> {
                mTextPoint.color = resources.getColor(R.color.black)
                canvas?.drawText("已完成", textPoint.startX, textPoint.startY, mTextPoint)
            }
            1 -> {
                mTextPoint.color = resources.getColor(R.color.colorPrimaryDark)
                canvas?.drawText("当前位置", textPoint.startX, textPoint.startY, mTextPoint)
            }
        }
    }

    private fun drawLine(canvas: Canvas?, linePoint: LinePoint, status: Int) {
        canvas?.drawLine(linePoint.startX, linePoint.startY, linePoint.endX, linePoint.endY,
                when (status) {
                    1, 2 -> mLightPathPaint
                    else -> mDarkPathPaint
                }
        )
    }

    private fun getLinePoint(startView: View, endView: View): LinePoint {
        return LinePoint(getCenterX(startView), startView.top.toFloat(), getCenterX(endView), endView.bottom.toFloat())
    }

    private fun getTextPoint(startView: View): TextPoint {
        return TextPoint(startView.right + 100f, startView.bottom.toFloat() - startView.measuredHeight / 2 + mTextPoint.textSize / 2)
    }

    private fun getIconPoint(startView: View): TextPoint {
        return TextPoint(startView.left - 120f, startView.bottom.toFloat() - startView.measuredHeight / 2 - 40f)
    }

    private fun getCenterX(view: View): Float {
        return view.x + view.measuredWidth / 2
    }


    data class LinePoint(val startX: Float, val startY: Float, val endX: Float, val endY: Float)
    data class TextPoint(val startX: Float, val startY: Float)


}