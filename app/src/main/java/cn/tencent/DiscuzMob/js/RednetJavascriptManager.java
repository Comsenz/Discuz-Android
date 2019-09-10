package cn.tencent.DiscuzMob.js;

import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by AiWei on 2016/8/1.
 */
public final class RednetJavascriptManager {

    public static final String NAME = "Rednet";

    static volatile Handler HANDLER = new Handler();

    public static void settings(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
    }

    public static void addJavascriptInterface(WebView webView, IRednetJavascript callback) {
        webView.addJavascriptInterface(new DefaultRednetJavascriptImpl(HANDLER, callback), NAME);
    }

}
