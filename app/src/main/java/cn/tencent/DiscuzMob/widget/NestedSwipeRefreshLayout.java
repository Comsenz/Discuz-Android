package cn.tencent.DiscuzMob.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;

import cn.tencent.DiscuzMob.R;


/**
 * Created by AiWei on 2016/1/20.
 * sample implement
 */
public class NestedSwipeRefreshLayout extends SwipeRefreshLayout {

    private View mTarget;
    private int mOriginalOffsetTop;
    private Field mCurrentTargetOffsetTopField, mOriginalOffsetTopField;

    private int mTouchSlop;
    private float mInitialDownX;
    private int mActivePointerId;
    private boolean mIsBeingDragged;

    public NestedSwipeRefreshLayout(Context context) {
        super(context);
    }

    public NestedSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeResources(R.color.blue);
        /*setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light
                , android.R.color.holo_orange_light, android.R.color.holo_red_light);*/
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        try {
            mCurrentTargetOffsetTopField = SwipeRefreshLayout.class.getDeclaredField("mCurrentTargetOffsetTop");
            mCurrentTargetOffsetTopField.setAccessible(true);
            mOriginalOffsetTopField = SwipeRefreshLayout.class.getDeclaredField("mOriginalOffsetTop");
            mOriginalOffsetTopField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0, count = getChildCount(); i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof NestedRefreshContentLayout) {
                mTarget = child;
                break;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        try {
            mOriginalOffsetTop = mOriginalOffsetTopField.getInt(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);

        if (!isEnabled() || isRefreshing()) {
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mIsBeingDragged = false;
                final float initialDownX = getMotionEventX(ev, mActivePointerId);
                if (initialDownX == -1) {
                    return false;
                }
                mInitialDownX = initialDownX;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == -1) {
                    mIsBeingDragged = false;
                    break;
                }

                final float x = getMotionEventX(ev, mActivePointerId);
                if (x == -1) {
                    mIsBeingDragged = false;
                    break;
                }
                final float xDiff = x - mInitialDownX;
                if (Math.abs(xDiff) > mTouchSlop && !mIsBeingDragged) {
                    mIsBeingDragged = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                mActivePointerId = -1;
                break;
        }

        if (mIsBeingDragged) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    private float getMotionEventX(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getX(ev, index);
    }

    @Override
    public boolean canChildScrollUp() {
        if (mTarget instanceof NestedRefreshContentLayout && invokeCurrentTargetOffsetTop() == mOriginalOffsetTop)
            return ((NestedRefreshContentLayout) mTarget).canChildScroll();
        else
            return super.canChildScrollUp();//active pointer sometime lose
    }

    private int invokeCurrentTargetOffsetTop() {
        if (mCurrentTargetOffsetTopField != null) {
            try {
                return mCurrentTargetOffsetTopField.getInt(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

}
