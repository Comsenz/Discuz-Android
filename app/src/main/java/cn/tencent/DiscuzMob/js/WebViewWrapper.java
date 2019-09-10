package cn.tencent.DiscuzMob.js;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by AiWei on 2015/5/22.
 */
@Deprecated
public class WebViewWrapper {

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private Runnable mCurrentTaskOnLoadFinish = null;

    private ZhangShangJavaScriptInterface.OnActionListener onActionListener;

    private WebViewClient mClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (mProgressBar != null)
                mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (url.indexOf("file:///android_asset/") != -1) {
                if (mCurrentTaskOnLoadFinish != null) {
                    mCurrentTaskOnLoadFinish.run();
                    mCurrentTaskOnLoadFinish = null;
                }
            }
            if (mProgressBar != null)
                mProgressBar.setVisibility(View.GONE);
        }
    };

    private WebChromeClient mChromeClient = new WebChromeClient() {
    };

    public WebViewWrapper(WebView webView, ProgressBar progressBar, Context context, ZhangShangJavaScriptInterface.OnActionListener onActionListener) {
        this.mWebView = webView;
        this.mProgressBar = progressBar;
        this.onActionListener = onActionListener;
        mWebView.setWebViewClient(mClient);
        mWebView.setWebChromeClient(mChromeClient);
        mWebView.setHorizontalScrollbarOverlay(false);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        mWebView.addJavascriptInterface(new ZhangShangJavaScriptInterface(context, onActionListener), "Rednet");
    }

    public void setTaskOnLoadFinish(Runnable runnable) {
        if (mWebView != null) {
            this.mCurrentTaskOnLoadFinish = runnable;
        }
    }

}
