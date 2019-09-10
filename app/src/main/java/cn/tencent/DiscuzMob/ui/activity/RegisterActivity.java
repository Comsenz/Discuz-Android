package cn.tencent.DiscuzMob.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tencent.DiscuzMob.base.AppendCookieManager;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.net.Api;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.model.BaseVariables;
import cn.tencent.DiscuzMob.ui.dialog.ProgressDialog;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by kurt on 15-5-25.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private ProgressDialog mProgressDialog;
    private WebView mWeb;
    private String registerUrl;
    private ImageView mBack;
    private Button mToLogin;

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            dissmissDialog("");
            if (msg.what == 1) {
                if (msg.obj instanceof String) {
                    BaseModel<BaseVariables> data = new Gson().fromJson((String) msg.obj, new TypeToken<BaseModel<BaseVariables>>() {
                    }.getType());
                    BaseVariables variables = data.getVariables();
//                    if (variables != null) {
//                        if (!TextUtils.isEmpty(variables.getAuth())) {
//                            ContentValues values = User.getContentValues(variables);
//                            if (values != null) {
//                                getContentResolver().insert(User.URI, values);
//                                setResult(RESULT_OK);
//                                finish();
//                            }
//                        }
//                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mWeb = (WebView) findViewById(R.id.regsiter_web);
        mBack = (ImageView) findViewById(R.id.register_back);
        mToLogin = (Button) findViewById(R.id.to_login);
        mProgressDialog = new ProgressDialog(this);
        mBack.setOnClickListener(this);
        mToLogin.setOnClickListener(this);
        mWeb.setHorizontalScrollbarOverlay(false);
        WebSettings settings = mWeb.getSettings();
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(false);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLightTouchEnabled(true);
        mWeb.setWebViewClient(mWebViewClient);
        mWeb.clearCache(true);
        mWeb.clearFormData();
        mWeb.clearHistory();
        mWeb.clearMatches();
        mWeb.clearSslPreferences();
        registerUrl = Api.getInstance().URL + "index.php?module=register&mod=registert&mobilemessage=yes&android=1";
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().removeSessionCookies(null);
        } else {
            CookieManager.getInstance().removeAllCookie();
            CookieManager.getInstance().removeSessionCookie();
            CookieManager.getInstance().removeExpiredCookie();
        }
        mWeb.loadUrl(registerUrl);
    }

    private final WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                mProgressDialog.show();
                String result[] = java.net.URLDecoder.decode(url, "utf-8").split("//");
                if (result[1].equals("register_succeed") || result[1].equals("register_email_verify")) {
                    RedNet.mHttpClient.setCookieHandler(new AppendCookieManager(RedNetApp.getInstance()));

                    String cookieString = CookieManager.getInstance().getCookie(registerUrl);
                    String[] cookieStrArr = cookieString.split(";");
                    List<String> cookie = new ArrayList<>();
                    for (int i = 0; i < cookieStrArr.length; i++) {
                        cookie.add(cookieStrArr[i]);
                    }

                    Map<String, List<String>> headerFields = new HashMap<>();
                    headerFields.put("Set-Cookie", cookie);
                    RedNet.mHttpClient.getCookieHandler().put(URI.create(registerUrl), headerFields);

                    mHanlder.sendMessage(Message.obtain(mHanlder, 1, result[3]));
                } else {
                    mHanlder.sendMessage(Message.obtain(mHanlder, 0, result[3]));
                }
                RednetUtils.toast(RegisterActivity.this, result[2]);
            } catch (Exception e) {
                RednetUtils.toast(RegisterActivity.this, "注册失败请重试");
                mProgressDialog.dismiss();
            }
            return true;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_back:
                finish();
                break;
            case R.id.to_login:
                finish();
                break;
        }
    }

    void dissmissDialog(String toast) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        if (!TextUtils.isEmpty(toast)) {
            RednetUtils.toast(this, toast);
        }
    }

}
