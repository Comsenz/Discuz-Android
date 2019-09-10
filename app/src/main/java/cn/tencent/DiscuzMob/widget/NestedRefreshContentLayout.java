package cn.tencent.DiscuzMob.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;

/**
 * Created by AiWei on 2016/5/18.
 */
public class NestedRefreshContentLayout extends FrameLayout {

    private View mContent;

    public NestedRefreshContentLayout(Context context) {
        super(context);
    }

    public NestedRefreshContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedRefreshContentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public NestedRefreshContentLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    void validate() {
        if (mContent == null) {
            View child = getChildAt(0);
            if (child instanceof AbsListView || child instanceof RecyclerView) {
                mContent = child;
            } else if (child instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) child;
                for (int i = 0, count = group.getChildCount(); i < count; i++) {
                    View c = group.getChildAt(i);
                    if (c instanceof AbsListView || c instanceof RecyclerView) {
                        mContent = c;
                        break;
                    }
                }
            } else {
                mContent = null;
            }
        }
    }

    public boolean canChildScroll() {
        validate();
        if (mContent != null) {
            return canChildScrollUp(mContent);
        }
        return true;
    }

    public static boolean canChildScrollUp(View target) {
        if (target instanceof AbsListView) {
            AbsListView absListView = (AbsListView) target;
            return absListView.getChildCount() > 0
                    && (absListView.getFirstVisiblePosition() > 0 || (absListView.getChildCount() > 0 && absListView.getChildAt(0)
                    .getTop() < absListView.getPaddingTop()));
        } else if (target instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) target;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            boolean firstVisiblePosition = false;
            if (layoutManager instanceof LinearLayoutManager) {
                firstVisiblePosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition() > 0;
            } else if (layoutManager instanceof GridLayoutManager) {
                firstVisiblePosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition() > 0;
            }
            return recyclerView.getChildCount() > 0
                    && firstVisiblePosition || (recyclerView.getChildCount() > 0 && recyclerView.getChildAt(0)
                    .getTop() < recyclerView.getPaddingTop());
        } else if (target instanceof ViewGroup) {
            ViewGroup targetV = (ViewGroup) target;
            for (int i = 0, count = targetV.getChildCount(); i < count; i++) {
                View child = targetV.getChildAt(i);
                if (child instanceof AbsListView || child instanceof RecyclerView) {
                    return canChildScrollUp(child);
                }
            }
            return target.getScrollY() > 0;
        } else {
            return false;
        }
    }

}
