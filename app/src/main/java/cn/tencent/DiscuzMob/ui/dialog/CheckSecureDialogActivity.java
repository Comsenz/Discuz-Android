package cn.tencent.DiscuzMob.ui.dialog;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.webkit.CookieManager;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.Config;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.net.Api;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;


/**
 * Created by kurt on 19/5/6.
 * 填写验证码
 */
public class CheckSecureDialogActivity extends FragmentActivity implements View.OnClickListener {

    private RelativeLayout mCheckSecureBtnView;
    private WebView mSecureImg;
    private EditText mSecureEdit;
    private String seccode;
    private String sechash;
    private String cookiepre;
    public static final int FROM_LOGIN = 0;
    public static final int FROM_SEND = 1;
    private int from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_secure_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        from = getIntent().getIntExtra("from", 0);
        seccode = getIntent().getStringExtra("seccode");
        sechash = getIntent().getStringExtra("sechash");
        cookiepre = getIntent().getStringExtra("cookiepre");
        mCheckSecureBtnView = (RelativeLayout) findViewById(R.id.check_secure_btn);
        mSecureImg = (WebView) findViewById(R.id.secure_img);
        mSecureEdit = (EditText) findViewById(R.id.secure_edit);
        mSecureImg.setClickable(false);
        mCheckSecureBtnView.setOnClickListener(this);
        findViewById(R.id.exit_select_layout).setOnClickListener(this);
        if (from == 1) {
            CookieSyncManager.createInstance(getApplicationContext());
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();//移除
            cookieManager.removeAllCookie();
            Map<String, List<String>> map = new HashMap<>();
            Map<String, List<String>> cookieData = null;
            try {
                cookieData = RedNet.mHttpClient.getCookieHandler().get(URI.create(Api.getInstance().URL), map);
            } catch (IOException e) {
                e.printStackTrace();
            }
            LogUtils.i("cookiedata"+cookieData.toString());
            List<String> cookieStrList = cookieData.get("Cookie");
            for (int i = 0; i < cookieStrList.size(); i++) {
                String[] cookies = cookieStrList.get(i).split(";");
                for (int j = 0; j < cookies.length; j++) {
                    cookieManager.setCookie(seccode, cookies[j]);
                    if (Build.VERSION.SDK_INT<21){
                        CookieSyncManager.getInstance().sync();
                    }else{
                        CookieManager.getInstance().flush();
                    }
                }
            }
        }
        mSecureImg.setWebViewClient(mWebViewClientBase);

        LogUtils.i(" seccode  :"+seccode);
        mSecureImg.loadUrl(seccode);
    }

    private WebViewClientBase mWebViewClientBase = new WebViewClientBase();

    private class WebViewClientBase extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            CookieSyncManager syncManager = CookieSyncManager.createInstance(CheckSecureDialogActivity.this);
            syncManager.sync();
            CookieManager cookieManager = CookieManager.getInstance();
            String cookie = cookieManager.getCookie(url);
            LogUtils.i(cookie);

            String[] cookies = cookie.split(";");
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].contains(cookiepre + "seccode")) {
                    CacheUtils.putString(CheckSecureDialogActivity.this,"VcodeCookie",cookies[i]);
                }
                if (cookies[i].contains(cookiepre + "saltkey")) {
                    LogUtils.i( "saltkey   "+cookies[i]);
                    CacheUtils.putString(CheckSecureDialogActivity.this,"cookiepre_saltkey",cookies[i]);
                }
            }
            super.onPageFinished(view, url);
        }

    }

    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_select_layout:
                finish();
                break;
            case R.id.check_secure_btn:
                String seccodeverifyText = mSecureEdit.getText().toString().trim();
                if (TextUtils.isEmpty(seccodeverifyText)) {
                    RednetUtils.toast(this, "请填写验证答案");
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("sechash",sechash);
                    intent.putExtra("seccodeverify", seccodeverifyText);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

}
