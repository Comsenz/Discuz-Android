package cn.tencent.DiscuzMob.utils;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by ${liguifeng} on 2017/4/6.
 */

public class SwipeListLayout extends FrameLayout {
    private View hiddenView;
    private View itemView;
    private int hiddenViewWidth;
    private ViewDragHelper mDragHelper;
    private int hiddenViewHeight;
    private int itemWidth;
    private int itemHeight;
    private OnSwipeStatusListener listener;
    private Status status = Status.Close;
    private boolean smooth = true;

    public static final String TAG = "SlipListLayout";

    // 状态
    public enum Status {
        Open, Close
    }

    /**
     * 设置侧滑状态
     *
     * @param status
     *            状态 Open or Close
     * @param smooth
     *            若为true则有过渡动画，否则没有
     */
    public void setStatus(Status status, boolean smooth) {
        this.status = status;
        if (status == Status.Open) {
            open(smooth);
        } else {
            close(smooth);
        }
    }

    public void setOnSwipeStatusListener(OnSwipeStatusListener listener) {
        this.listener = listener;
    }

    /**
     * 是否设置过渡动画
     *
     * @param smooth
     */
    public void setSmooth(boolean smooth) {
        this.smooth = smooth;
    }

    public interface OnSwipeStatusListener {

        /**
         * 当状态改变时回调
         *
         * @param status
         */
        void onStatusChanged(Status status);

        /**
         * 开始执行Open动画
         */
        void onStartCloseAnimation();

        /**
         * 开始执行Close动画
         */
        void onStartOpenAnimation();

    }

    public SwipeListLayout(Context context) {
        this(context, null);
    }

    public SwipeListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDragHelper = ViewDragHelper.create(this, callback);
    }

    // ViewDragHelper的回调
    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View view, int arg1) {
            return view == itemView;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == itemView) {
                if (left > 0) {
                    return 0;
                } else {
                    left = Math.max(left, -hiddenViewWidth);
                    return left;
                }
            }
            return 0;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return hiddenViewWidth;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top,
                                          int dx, int dy) {
            if (itemView == changedView) {
                hiddenView.offsetLeftAndRight(dx);
            }
            // 有时候滑动很快的话 会出现隐藏按钮的linearlayout没有绘制的问题
            // 为了确保绘制成功 调用 invalidate
            invalidate();
        }

        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            // 向右滑xvel为正 向左滑xvel为负
            if (releasedChild == itemView) {
                if (xvel == 0
                        && Math.abs(itemView.getLeft()) > hiddenViewWidth / 2.0f) {
                    open(smooth);
                } else if (xvel < 0) {
                    open(smooth);
                } else {
                    close(smooth);
                }
            }
        }

    };
    private Status preStatus = Status.Close;

    /**
     * 侧滑关闭
     *
     * @param smooth
     *            为true则有平滑的过渡动画
     */
    private void close(boolean smooth) {
        preStatus = status;
        status = Status.Close;
        if (smooth) {
            if (mDragHelper.smoothSlideViewTo(itemView, 0, 0)) {
                if (listener != null) {
                   // Log.i(TAG, "start close animation");
                    listener.onStartCloseAnimation();
                }
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layout(status);
        }
        if (listener != null && preStatus == Status.Open) {
           // Log.i(TAG, "close");
            listener.onStatusChanged(status);
        }
    }

    /**
     *
     * @param status
     */
    private void layout(Status status) {
        if (status == Status.Close) {
            hiddenView.layout(itemWidth, 0, itemWidth + hiddenViewWidth,
                    itemHeight);
            itemView.layout(0, 0, itemWidth, itemHeight);
        } else {
            hiddenView.layout(itemWidth - hiddenViewWidth, 0, itemWidth,
                    itemHeight);
            itemView.layout(-hiddenViewWidth, 0, itemWidth - hiddenViewWidth,
                    itemHeight);
        }
    }

    /**
     * 侧滑打开
     *
     * @param smooth
     *            为true则有平滑的过渡动画
     */
    private void open(boolean smooth) {
        preStatus = status;
        status = Status.Open;
        if (smooth) {
            if (mDragHelper.smoothSlideViewTo(itemView, -hiddenViewWidth, 0)) {
                if (listener != null) {
                   // Log.i(TAG, "start open animation");
                    listener.onStartOpenAnimation();
                }
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layout(status);
        }
        if (listener != null && preStatus == Status.Close) {
           // Log.i(TAG, "open");
            listener.onStatusChanged(status);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        // 开始执行动画
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    // 让ViewDragHelper来处理触摸事件
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        if (action == MotionEvent.ACTION_CANCEL) {
            mDragHelper.cancel();
            return false;
        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    // 让ViewDragHelper来处理触摸事件
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        hiddenView = getChildAt(0); // 得到隐藏按钮的linearlayout
        itemView = getChildAt(1); // 得到最上层的linearlayout
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 测量子View的长和宽
        itemWidth = itemView.getMeasuredWidth();
        itemHeight = itemView.getMeasuredHeight();
        hiddenViewWidth = hiddenView.getMeasuredWidth();
        hiddenViewHeight = hiddenView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        layout(Status.Close);
    }


}
