package com.jingchen.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

public class PullableWebView extends WebView implements Pullable {

    private final String TAG = getClass().getSimpleName();

    public PullableWebView(Context context) {
        super(context);
    }

    public PullableWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        return getScrollY() == 0;
    }

    @Override
    public boolean canPullUp() {
        float contentHeight = getContentHeight() * getScale();
        Log.i(TAG, "contentHeight-getHeight()=" + (contentHeight - getHeight()) + " getScrollY=" + getScrollY());
        return getScrollY() + 4 >= (contentHeight - getHeight());
    }
}
