package com.ipd.taixiuser.widget.familytree;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipd.taixiuser.R;
import com.ipd.taixiuser.bean.TeamStructBean;
import com.ipd.taixiuser.imageload.ImageLoader;
import com.ipd.taixiuser.widget.familytree.interfaces.OnFamilySelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 家谱树自定义ViewGroup
 */

public class FamilyTreeView2 extends ViewGroup {

    private static final int MAX_HEIGHT_DP = 590;//最大高度为590dp
    private static final int SPACE_WIDTH_DP = 20;//间距为20dp
    private static final int ITEM_WIDTH_DP = 80;//家庭成员View宽度50dp
    private static final int ITEM_HEIGHT_DP = 110;//家庭成员View高度80dp
    private static final float CALL_TEXT_SIZE_SP = 9f;//称呼文字大小9sp
    private static final float NAME_TEXT_SIZE_SP = 11f;//名称文字大小11sp
    private static final int LINE_WIDTH_DP = 2;//连线宽度2dp
    private static final int SCROLL_WIDTH = 2;//移动超过2dp，响应滑动，否则属于点击

    private OnFamilySelectListener mOnFamilySelectListener;

    private int mScreenWidth;//屏幕宽度PX
    private int mScreenHeight;//屏幕高度PX

    private int mItemWidthPX;//家庭成员View宽度PX
    private int mItemHeightPX;//家庭成员View高度PX
    private int mMaxWidthPX;//最大宽度PX
    private int mMaxHeightPX;//最大高度PX
    private int mSpacePX;//元素间距PX
    private int mLineWidthPX;//连线宽度PX

    private int mWidthMeasureSpec;
    private int mHeightMeasureSpec;

    private int mShowWidthPX;//在屏幕所占的宽度
    private int mShowHeightPX;//在屏幕所占的高度

    private TeamStructBean mTeamStructBean;//我的
    private List<TeamStructBean> mMyChildren;//子女

    private View mMineView;//我的View
    private List<View> mChildrenView;//子女View
    private List<View> mGrandChildrenView;//孙子女View

    private int mGrandChildrenMaxWidth;//孙子女所占总长度

    private Paint mPaint;//连线样式
    private Path mPath;//路径

    private int mScrollWidth;//移动范围
    private int mCurrentX;//当前X轴偏移量
    private int mCurrentY;//当前Y轴偏移量
    private int mLastTouchX;//最后一次触摸的X坐标
    private int mLastTouchY;//最后一次触摸的Y坐标
    private int mLastInterceptX;
    private int mLastInterceptY;

    private int mCurrentLeft = 0;//当前选中View的Left距离
    private int mCurrentTop = 0;//当前选中View的Top距离
    private int mCurrentScrollX = 0;//当前滚动位置
    private int mCurrentScrollY = 0;//当前滚动位置

    public FamilyTreeView2(Context context) {
        this(context, null, 0);
    }

    public FamilyTreeView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FamilyTreeView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScreenWidth = DisplayUtil.getScreenWidth();
        mScreenHeight = DisplayUtil.getScreenHeight();
        mScrollWidth = DisplayUtil.dip2px(SCROLL_WIDTH);
        mSpacePX = DisplayUtil.dip2px(SPACE_WIDTH_DP);
        mLineWidthPX = DisplayUtil.dip2px(LINE_WIDTH_DP);
        mItemWidthPX = DisplayUtil.dip2px(ITEM_WIDTH_DP);
        mItemHeightPX = DisplayUtil.dip2px(ITEM_HEIGHT_DP);
        mWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mItemWidthPX, MeasureSpec.EXACTLY);
        mHeightMeasureSpec = MeasureSpec.makeMeasureSpec(mItemHeightPX, MeasureSpec.EXACTLY);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.reset();
        mPaint.setColor(0xFF888888);
        mPaint.setStrokeWidth(mLineWidthPX);
        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setPathEffect(new DashPathEffect(new float[]{mLineWidthPX, mLineWidthPX * 4}, 0));

        mPath = new Path();
        mPath.reset();
    }

    private void recycleAllView() {
        removeAllViews();
        mMineView = null;

        if (mChildrenView != null) {
            mChildrenView.clear();
        } else {
            mChildrenView = new ArrayList<>();
        }
        if (mGrandChildrenView != null) {
            mGrandChildrenView.clear();
        } else {
            mGrandChildrenView = new ArrayList<>();
        }

        if (mMyChildren != null) {
            mMyChildren.clear();
            mMyChildren = null;
        }
    }

    private void initData(TeamStructBean familyMember) {
        this.mTeamStructBean = familyMember;
        mTeamStructBean.setSelect(true);
        mMyChildren = mTeamStructBean.getChildren();
    }

    private void initWidthAndHeight() {
        final int[] widthDP = {
                830,//第一代最大宽度
                720,//第二代最大宽度
                ITEM_WIDTH_DP,//第三代最大宽度
                ITEM_WIDTH_DP,//第四代最大宽度
                ITEM_WIDTH_DP//第五代最大宽度
        };


        if (mMyChildren != null) {
            widthDP[3] += (SPACE_WIDTH_DP + ITEM_WIDTH_DP) * mMyChildren.size();
            widthDP[4] = 0;
            for (int i = 0; i < mMyChildren.size(); i++) {
                final TeamStructBean child = mMyChildren.get(i);
                final List<TeamStructBean> grandChildrenList = child.getChildren();

                final int grandchildMaxWidthDP;
                if (grandChildrenList != null && grandChildrenList.size() > 0) {
                    final int grandchildCount = grandChildrenList.size();
                    if (grandchildCount == 1 && mMyChildren.size() == 1) {
                        grandchildMaxWidthDP = ITEM_WIDTH_DP + SPACE_WIDTH_DP;
                    } else {
                        grandchildMaxWidthDP = (ITEM_WIDTH_DP + SPACE_WIDTH_DP) * grandchildCount;
                    }
                } else {
                    if (mMyChildren.size() > 1) {
                        grandchildMaxWidthDP = ITEM_WIDTH_DP + SPACE_WIDTH_DP;
                    } else {
                        grandchildMaxWidthDP = ITEM_WIDTH_DP + SPACE_WIDTH_DP;
                    }
                }
                widthDP[4] += grandchildMaxWidthDP;
            }
            widthDP[4] -= SPACE_WIDTH_DP;
            mGrandChildrenMaxWidth = DisplayUtil.dip2px(widthDP[4]);
        }

        mMaxWidthPX = mScreenWidth;
        for (int width : widthDP) {
            final int widthPX = DisplayUtil.dip2px(width);
            if (widthPX > mMaxWidthPX) {
                mMaxWidthPX = widthPX;
            }
        }

        mMaxHeightPX = Math.max(DisplayUtil.dip2px(MAX_HEIGHT_DP), mScreenHeight);
    }

    private void initView() {
        mMineView = createFamilyView(mTeamStructBean);

        mChildrenView.clear();
        if (mMyChildren != null) {
            for (TeamStructBean family : mMyChildren) {
                mChildrenView.add(createFamilyView(family));

                final List<TeamStructBean> grandChildrens = family.getChildren();

                if (grandChildrens != null && grandChildrens.size() > 0) {
                    for (TeamStructBean childFamily : grandChildrens) {
                        mGrandChildrenView.add(createFamilyView(childFamily));
                    }
                }
            }
        }
    }

    private View createFamilyView(TeamStructBean family) {
        final View familyView = LayoutInflater.from(getContext()).inflate(R.layout.item_family, this, false);
        familyView.getLayoutParams().width = mItemWidthPX;
        familyView.getLayoutParams().height = mItemHeightPX;
        familyView.setTag(family);

        final ImageView ivAvatar = familyView.findViewById(R.id.iv_avatar);
//        ivAvatar.getLayoutParams().height = mItemWidthPX;

        final TextView tvCall = familyView.findViewById(R.id.tv_call);
//        tvCall.getLayoutParams().height = (mItemHeightPX - mItemWidthPX) / 2;
        tvCall.setTextSize(CALL_TEXT_SIZE_SP);
        tvCall.setText("(" + family.nickname + ")");

        final TextView tvName = familyView.findViewById(R.id.tv_name);
//        tvName.getLayoutParams().height = (mItemHeightPX - mItemWidthPX) / 2;
        tvName.setTextSize(NAME_TEXT_SIZE_SP);
        tvName.setText(family.username + "(" + family.proxyname + ")");

        final TextView tvNum = familyView.findViewById(R.id.tv_num);
        tvNum.setText(family.getNum() + "人");

        final String url = family.avatar;
        if (!TextUtils.isEmpty(url)) {
            ImageLoader.loadAvatar(getContext(), url, ivAvatar);
        }

        familyView.setOnClickListener(click);

        this.addView(familyView);
        return familyView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mShowWidthPX = MeasureSpec.getSize(widthMeasureSpec);
        mShowHeightPX = MeasureSpec.getSize(heightMeasureSpec);

        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            childView.measure(mWidthMeasureSpec, mHeightMeasureSpec);
        }

        setMeasuredDimension(mMaxWidthPX, mMaxHeightPX);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (mCurrentScrollX == 0 && mCurrentScrollY == 0) {
            scrollTo((left + right - mShowWidthPX) / 2, (top + bottom - mShowHeightPX) / 2);
        } else {
            scrollTo(mCurrentScrollX, mCurrentScrollY);
        }
        if (mMineView != null) {
            final int mineLeft;
            final int mineTop;
            if (mCurrentLeft == 0 && mCurrentTop == 0) {
                mineLeft = (left + right - mItemWidthPX) / 2;
                mineTop = (top + bottom - mItemHeightPX) / 2;
            } else {
                mineLeft = mCurrentLeft;
                mineTop = mCurrentTop;
            }

            setChildViewFrame(mMineView, mineLeft, mineTop, mItemWidthPX, mItemHeightPX);

            final int parentTop = mineTop - mSpacePX * 2 - mItemHeightPX;
            final int grandParentTop = parentTop - mSpacePX * 2 - mItemHeightPX;


            if (mChildrenView != null && mChildrenView.size() > 0) {
                final int childTop = mineTop + mItemHeightPX + mSpacePX * 2;
                int childLeft = mineLeft + mItemWidthPX / 2 - mGrandChildrenMaxWidth / 2;

                final int grandChildrenTop = childTop + mItemHeightPX + mSpacePX * 2;
                int grandChildrenLeft = childLeft;

                int grandchildIndex = 0;
                int childSpouseIndex = 0;
                final int childCount = mChildrenView.size();
                for (int i = 0; i < childCount; i++) {
                    final View myChildView = mChildrenView.get(i);
                    final TeamStructBean myChild = mMyChildren.get(i);
                    final List<TeamStructBean> myGrandChildren = myChild.getChildren();

                    if (myGrandChildren != null && myGrandChildren.size() > 0) {
                        final int startGrandChildLeft = grandChildrenLeft;
                        int endGrandChildLeft = grandChildrenLeft;

                        final int myGrandChildrenCount = myGrandChildren.size();
                        for (int j = 0; j < myGrandChildrenCount; j++) {
                            final View grandChildView = mGrandChildrenView.get(grandchildIndex);
                            setChildViewFrame(grandChildView, grandChildrenLeft, grandChildrenTop, mItemWidthPX, mItemHeightPX);
                            endGrandChildLeft = grandChildrenLeft;
                            grandChildrenLeft += mItemWidthPX + mSpacePX;
                            grandchildIndex++;
                        }

                        childLeft = (endGrandChildLeft - startGrandChildLeft) / 2 + startGrandChildLeft;
                    } else {
                        childLeft = grandChildrenLeft;
                        grandChildrenLeft += mSpacePX + mItemWidthPX;
                    }

                    setChildViewFrame(myChildView, childLeft, childTop, mItemWidthPX, mItemHeightPX);

                }
            }
        }
    }

    private void setChildViewFrame(View childView, int left, int top, int width, int height) {
        childView.layout(left, top, left + width, top + height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        drawChildrenLine(canvas);
    }


    private void drawChildrenLine(Canvas canvas) {
        if (mMyChildren != null && mMyChildren.size() > 0) {
            final int myVerticalLineX = (int) mMineView.getX() + mItemWidthPX / 2;
            final int myVerticalLineStartY = (int) mMineView.getY() + mItemHeightPX;
            final int myVerticalLinesStopY = myVerticalLineStartY + mSpacePX;
            mPath.reset();
            mPath.moveTo(myVerticalLineX, myVerticalLineStartY);
            mPath.lineTo(myVerticalLineX, myVerticalLinesStopY);
            canvas.drawPath(mPath, mPaint);

            int index = 0;
            int childSpouseIndex = 0;
            final int childrenViewCount = mChildrenView.size();
            for (int i = 0; i < childrenViewCount; i++) {
                final View startChildView = mChildrenView.get(i);
                final int childLineY = (int) startChildView.getY() - mSpacePX;
                final int childVerticalLineEndY = (int) startChildView.getY() + mItemWidthPX / 2;
                final int childLineStartX = (int) startChildView.getX() + mItemWidthPX / 2;
                mPath.reset();
                mPath.moveTo(childLineStartX, childLineY);
                mPath.lineTo(childLineStartX, childVerticalLineEndY);
                canvas.drawPath(mPath, mPaint);

                if (i < childrenViewCount - 1) {
                    final View endChildView = mChildrenView.get(i + 1);
                    final int horizontalLineStopX = (int) endChildView.getX() + mItemWidthPX / 2;
                    mPath.reset();
                    mPath.moveTo(childLineStartX, childLineY);
                    mPath.lineTo(horizontalLineStopX, childLineY);
                    canvas.drawPath(mPath, mPaint);
                }

                final List<TeamStructBean> grandChildren = mMyChildren.get(i).getChildren();
                if (grandChildren != null) {
                    final int grandChildrenCount = grandChildren.size();
                    for (int j = 0; j < grandChildrenCount; j++) {
                        final View startView = mGrandChildrenView.get(j + index);
                        final int grandchildLineX = (int) startView.getX() + mItemWidthPX / 2;
                        final int grandchildLineStartY = (int) startView.getY() - mSpacePX;
                        final int garndchildLineEndY = (int) startView.getY();
                        mPath.reset();
                        mPath.moveTo(grandchildLineX, grandchildLineStartY);
                        mPath.lineTo(grandchildLineX, garndchildLineEndY);
                        canvas.drawPath(mPath, mPaint);

                        if (j < grandChildrenCount - 1) {
                            final View endView = mGrandChildrenView.get(j + 1 + index);
                            final int hLineStopX = (int) endView.getX() + mItemWidthPX / 2;
                            mPath.reset();
                            mPath.moveTo(grandchildLineX, grandchildLineStartY);
                            mPath.lineTo(hLineStopX, grandchildLineStartY);
                            canvas.drawPath(mPath, mPaint);
                        }
                    }

                    if (grandChildrenCount > 0) {
                        final View grandChildView = mGrandChildrenView.get(index);
                        final int vLineX = (int) startChildView.getX() + mItemWidthPX / 2;
                        final int vLineStopY = (int) startChildView.getY() + mItemHeightPX;
                        final int hLineY = (int) grandChildView.getY() - mSpacePX;
                        mPath.reset();
                        mPath.moveTo(vLineX, hLineY);
                        mPath.lineTo(vLineX, vLineStopY);
                        canvas.drawPath(mPath, mPaint);

                        index += grandChildrenCount;
                    }
                }
            }
        }
    }

    public void setTeamStructBean(TeamStructBean familyMember) {
        recycleAllView();
        initData(familyMember);
        initWidthAndHeight();
        initView();
        invalidate();
    }

    public void setOnFamilySelectListener(OnFamilySelectListener onFamilySelectListener) {
        this.mOnFamilySelectListener = onFamilySelectListener;
    }


    private OnClickListener click = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnFamilySelectListener != null) {
                mCurrentLeft = v.getLeft();
                mCurrentTop = v.getTop();
                mCurrentScrollX = getScrollX();
                mCurrentScrollY = getScrollY();
                mOnFamilySelectListener.onFamilySelect((TeamStructBean) v.getTag());
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                mCurrentX = getScrollX();
//                mCurrentY = getScrollY();
//                mLastTouchX = (int) event.getX();
//                mLastTouchY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final int currentTouchX = (int) event.getX();
                final int currentTouchY = (int) event.getY();

                final int distanceX = currentTouchX - mLastTouchX;
                final int distanceY = currentTouchY - mLastTouchY;

                mCurrentX -= distanceX;
                mCurrentY -= distanceY;

//                if (mCurrentX < getLeft()) {
//                    mCurrentX = getLeft();
//                } else if (mCurrentX > getRight() - mShowWidthPX) {
//                    mCurrentX = getRight() - mShowWidthPX;
//                }
//
//                if (mCurrentY < getTop()) {
//                    mCurrentY = getTop();
//                } else if (mCurrentY > getBottom() - mShowHeightPX) {
//                    mCurrentY = getBottom() - mShowHeightPX;
//                }

                this.scrollTo(mCurrentX, mCurrentY);
                mLastTouchX = currentTouchX;
                mLastTouchY = currentTouchY;
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercerpt = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastInterceptX = (int) event.getX();
                mLastInterceptY = (int) event.getY();
                mCurrentX = getScrollX();
                mCurrentY = getScrollY();
                mLastTouchX = (int) event.getX();
                mLastTouchY = (int) event.getY();
                intercerpt = false;
                break;
            case MotionEvent.ACTION_MOVE:
                final int distanceX = Math.abs((int) event.getX() - mLastInterceptX);
                final int distanceY = Math.abs((int) event.getY() - mLastInterceptY);
                if (distanceX < mScrollWidth && distanceY < mScrollWidth) {
                    intercerpt = false;
                } else {
                    intercerpt = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercerpt = false;
                break;
        }
        return intercerpt;
    }
}
