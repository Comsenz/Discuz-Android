package cn.tencent.DiscuzMob.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.utils.Callback;
import cn.tencent.DiscuzMob.model.SecureVariables;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/7/11.
 */
public class SecureDialog extends Dialog implements View.OnClickListener {

    private WebViewClient mWebViewClient;
    private View mLabel, mSubmit;
    private WebView mWebView;
    private EditText mCode;

    private SecureVariables mVariables;
    private String mCodeCookie;
    private Callback<String[]> mResult;

    public SecureDialog(Context context) {
        this(context, R.style.Theme_Rednet_Dialog);
    }

    public SecureDialog(Context context, int theme) {
        super(context, theme);
        setCanceledOnTouchOutside(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_secure);
        mLabel = findViewById(R.id.label);
        mSubmit = findViewById(R.id.submit);
        mWebView = (WebView) findViewById(R.id.webview);
        mCode = (EditText) findViewById(R.id.code);
        mLabel.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
    }

    public void show(final SecureVariables variables, final Callback<String[]> result) {
        super.show();
        this.mVariables = variables;
        this.mResult = result;
        if (variables != null) {
            if (mWebViewClient == null) {
                mWebViewClient = new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        String codeCookie = CookieManager.getInstance().getCookie(url);
                        String[] codeCookies = TextUtils.isEmpty(codeCookie) ? null : codeCookie.split(";");
                        if (codeCookies != null && codeCookies.length > 0) {
                            String key = mVariables.getCookiepre() + "seccode=";
                            for (String cookie : codeCookies) {
                                if (cookie.contains(key)) {
                                    mCodeCookie = cookie.substring(cookie.indexOf(key) + 1, cookie.length());
                                }
                            }
                        }
                    }
                };
                mWebView.setWebViewClient(mWebViewClient);
                mLabel.performClick();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.label) {
            removeAllCookies();
            String url = mVariables.getSeccode();
            String cookie = new StringBuilder(RedNetApp.getInstance().getCookie())
                    .append(mVariables.getCookiepre()).append("saltkey=").append(mVariables.getSaltkey()).toString();
            CookieManager.getInstance().setCookie(url, cookie);
            mWebView.loadUrl(url);
        } else {
            if (TextUtils.isEmpty(mCode.getText().toString())) {
                RednetUtils.toast(getContext(), "请输入验证码");
            } else {
                if (mResult != null) {
                    mResult.onCallback(new String[]{mCode.getText().toString(), mCodeCookie});
                }
            }
        }
    }

    void removeAllCookies() {
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean value) {
                }
            });
        } else {
            CookieManager.getInstance().removeAllCookie();
        }
    }

}
