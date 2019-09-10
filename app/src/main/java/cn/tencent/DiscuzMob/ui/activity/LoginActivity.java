package cn.tencent.DiscuzMob.ui.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.login.LoginApi;
import cn.sharesdk.login.OnLoginListener;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.db.User;
import cn.tencent.DiscuzMob.model.UserInfoBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.Event.RefreshUserInfo;
import cn.tencent.DiscuzMob.ui.dialog.ProgressDialog;
import cn.tencent.DiscuzMob.ui.dialog.SelectQuestionActivity;
import cn.tencent.DiscuzMob.utils.Callback;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;


/**
 * Created by kurt on 15-5-25.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private EditText mAccountEdit, mPwdEdit, mAnswerEdit;
    private TextView mQuestionText;
    private View mLoginView, mSelectQuestionView, mAnswerView;
    private ImageView mLoginBack;
    private Button mToRegisterBtn;
    private ProgressDialog mProgressDialog;
    private static final int QUESTION_REQUEST_CODE = 0;
    private static final int LOGIN_SECURE_REQUEST_CODE = 1;
    private static final int REGISTER_REQUEST_CODE = 2;
    private static final int THREEE_LOGIN_RELEVANCE = 3;//第三方登陆后 关联已有论坛或者新注册论坛
    private int questionid;

    private String seccodeverify;
    private String vcodeCookie;
    private String sechash;
    private String loginSaltkey;

    private static volatile boolean isWecharCallTimeout;
    private int task, task1 = 1 << 1, task2 = 2 << 1;  //task1 = 2  task2 = 4

    private String icon;
    private String nickname;
    private String openId;
    private String platformName;
    private int messagestatus;

    private ImageView name_icon;
    private ImageView pwd_icon;
    private ImageView question_icon;
    private String TYPE = "login";
    private EditText et_code;
    //    private WebView iv_code;
    private WebView iv_code;
    private RelativeLayout ll_code;
    private String formhash;
    String seccode;
    String cookiepre;
    boolean otherlogin = false;
    private String platform;
    private String saltkey;
    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (getWindow() != null) {
                if (msg.what == 1) {
                    dissmissDialog(msg.obj instanceof String ? (String) msg.obj : "");
                } else if (msg.what == 2) {
                    setResult(RESULT_OK);
                    finish();
                } else if (msg.what == 3) {
                    login();
                } else {
                    dissmissDialog("登录失败");
                }
            }
        }
    };


    private final String TAG = getClass().getSimpleName();
    private OnLoginListener mOnLoginListner = new OnLoginListener() {
        @Override
        public void onResult(boolean success, final String platform, final Bundle res) {
            Log.e("TAG", "onResult: success=" + success + " platform=" + platform);
            if (Wechat.NAME.equalsIgnoreCase(platform)) {
                if (isWecharCallTimeout)
                    return;
                else
                    isWecharCallTimeout = true;
            }
            if (success && res != null && res.size() > 0) {
                if (mProgressDialog != null) {
                    if (!mProgressDialog.isShowing()) {
                        mProgressDialog.show();
                    } else {
                        mProgressDialog.dismiss();
                    }
                    openId = res.getString("openId");
                    platformName = res.getString("platform");
                    nickname = res.getString("nickname");
                    icon = res.getString("icon");
                    mProgressDialog.setLabel("授权成功,正在登录");
                    if (QQ.NAME.equals(platformName)) {
                        login(true,openId,"","qq",nickname,icon);
                    } else if (Wechat.NAME.equals(platformName)) {
//                        Log.e("TAG", "微信");
                        Platform weChat = ShareSDK.getPlatform(Wechat.NAME);
                        final String unionid = weChat.getDb().get("unionid");
                        login(true,openId,unionid,"weixin",nickname,icon);
                    } else {
                        dissmissDialog("不支持的平台");
                    }
                }
            } else {
                dissmissDialog(null);
            }
        }
    };
    TextView login_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        getQuestion();
//        sp = getSharedPreferences("LoginCookie", Context.MODE_PRIVATE);
        name_icon = (ImageView) findViewById(R.id.name_icon);
        mAccountEdit = (EditText) findViewById(R.id.account_edit);
        mAccountEdit.setOnFocusChangeListener(this);
        pwd_icon = (ImageView) findViewById(R.id.pwd_icon);
        mPwdEdit = (EditText) findViewById(R.id.pwd_edit);
        mPwdEdit.setOnFocusChangeListener(this);
        mLoginView = findViewById(R.id.login_btn);
        mLoginBack = (ImageView) findViewById(R.id.login_back);
        mToRegisterBtn = (Button) findViewById(R.id.to_register);
        mSelectQuestionView = findViewById(R.id.question_text_view);
        question_icon = (ImageView) findViewById(R.id.question_icon);
        mQuestionText = (TextView) findViewById(R.id.question_text);
        login_text = (TextView) findViewById(R.id.login_text);
        mAnswerEdit = (EditText) findViewById(R.id.answer_edit);
        et_code = (EditText) findViewById(R.id.et_code);
        iv_code = (WebView) findViewById(R.id.iv_code);
//        iv_code = (AsyncRoundedImageView) findViewById(R.id.iv_code);
        ll_code = (RelativeLayout) findViewById(R.id.ll_code);
        mAnswerEdit.setOnFocusChangeListener(this);
        mAnswerView = findViewById(R.id.answer_edit_view);
        findViewById(R.id.qq).setOnClickListener(this);
        findViewById(R.id.wechat).setOnClickListener(this);
        mProgressDialog = new ProgressDialog(this);
        login_text.setText("登录");
        mLoginView.setOnClickListener(this);
        mLoginBack.setOnClickListener(this);
        mToRegisterBtn.setOnClickListener(this);
        mSelectQuestionView.setOnClickListener(this);
        iv_code.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    getQuestion();
                }
                return true;
            }
        });
    }

    String cookie2;

    private void getQuestion() {
        cookiepre = CacheUtils.getString(this, "cookiepre");
        Request request = new Request.Builder()
                .url(AppNetConfig.QUESTION + TYPE)
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build();
        RedNet.mHttpClient.newCall(request).enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RednetUtils.toast(LoginActivity.this, "验证码请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONObject variables = object.getJSONObject("Variables");
                    if (null != variables && !TextUtils.isEmpty(variables.toString())) {
                        seccode = variables.getString("seccode");
                        sechash = variables.getString("sechash");
                        CacheUtils.putString(LoginActivity.this, "sechash", sechash);
                        LoginActivity.this.formhash = variables.getString("formhash");
                        cookiepre = variables.getString("cookiepre");
                        CacheUtils.putString(LoginActivity.this, "cookiepre", cookiepre);
                        saltkey = variables.getString("saltkey");
                        syncCookie();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ll_code.setVisibility(View.VISIBLE);
                                iv_code.setWebViewClient(mWebViewClientBase);
                                iv_code.loadUrl(seccode);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ll_code.setVisibility(View.GONE);
                            }
                        });
                    }

                } catch (JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ll_code.setVisibility(View.GONE);
                        }
                    });
                    e.printStackTrace();
                }
            }
        });
//        }
    }

    public void login(String platform) {
        LoginApi api = new LoginApi();
        this.platform = platform;
        api.setPlatform(platform);//设置登陆的平台后执行登陆的方法
        api.setOnLoginListener(mOnLoginListner);
        api.login(this);
        if (Wechat.NAME.equals(platform)) {
            isWecharCallTimeout = false;
            mHanlder.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!isWecharCallTimeout) {
                        isWecharCallTimeout = true;
                        dissmissDialog("请求超时,请重试");
                    }
                }
            }, 24000);
        }
    }

    String username;
    String password;
    public void login(){
        login(false,"","","","","");
    }
    public void login(boolean isthird, final String openId, final String unionid, final String type, final String nickname, final String icon) {
        RequestBody body = null;
         String questionId ="";
         String answer = "";
         String formhash ="";
         String seccodeCookie = "";
         String url ="";
        seccodeverify = seccodeverify == null ? "" : seccodeverify;
        sechash = sechash == null ? "" : sechash;
        if (!isthird){
            LogUtils.i("alan1995  1111111111111");
            ShareSDK.initSDK(getApplicationContext());
            username = mAccountEdit.getText().toString().trim();
            password = mPwdEdit.getText().toString().trim();
            CacheUtils.putString(LoginActivity.this,"username",username.toString());
            CacheUtils.putString(LoginActivity.this,"password",password.toString());
            questionId = questionid != 0 ? String.valueOf(questionid) : "";
            answer = mAnswerEdit.getText().toString().trim();
            seccodeverify = et_code.getText().toString().trim();
            CacheUtils.putString(LoginActivity.this,"seccodeverify",seccodeverify);
            formhash = CacheUtils.getString(LoginActivity.this, "formhash");
             seccodeCookie = RedNetPreferences.getSeccodeCookie();
            CookieSyncManager syncManager = CookieSyncManager.createInstance(LoginActivity.this);
            syncManager.sync();
            if (!mProgressDialog.isShowing()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.show();
                    }
                });
            }
            mProgressDialog.setLabel("正在登录");
            LogUtils.d("登录参数：username=" + username + " password=" + password + " questionId=" + questionId + " answer=" + answer + " sechash=" + sechash + " seccodeverify=" + seccodeverify);
            FormEncodingBuilder builder = new FormEncodingBuilder();
            if (!TextUtils.isEmpty(questionId)&&!TextUtils.isEmpty(answer)){
                builder.add("questionid", questionId)
                        .add("answer", answer);
            }
            if (!TextUtils.isEmpty(openId)){
                builder.add("openid", openId);
            }
            if (!TextUtils.isEmpty(unionid)){
                builder.add("openid", openId);
                builder.add("unionid", unionid);
            }
            body = builder.add("username", username)
                    .add("password", password)
                    .add("sechash", sechash)
                    .add("seccodeverify", seccodeverify)
                    .add("loginsubmit","yes")
                    .build();
            url =  AppNetConfig.LOGIN+"&type="+type;
        }else {
            LogUtils.i("alan1995  2222222222222222222");
            if (!mProgressDialog.isShowing()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.show();
                    }
                });
            }
            mProgressDialog.setLabel("正在登录");
            LogUtils.i(type);
            FormEncodingBuilder builder = new FormEncodingBuilder();
            if (type.equals("weixin")){
                builder.add("openid", openId)
                        .add("unionid", unionid);
            }
            body = builder.add("openid", openId)
//                    .add("sechash", sechash)
//                    .add("seccodeverify", seccodeverify)
//                    .add("loginsubmit","yes")
                    .build();
            url =  AppNetConfig.LOGIN+"&type="+type;
        }
        LogUtils.i(url  +"  body: " +body.toString());
        final String finalQuestionId = questionId;
        final String finalAnswer = answer;
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetPreferences.getVcodeCookie() + ";" + RedNetPreferences.getSaltkey() + ";" + seccodeCookie + ";")
                .url(url)
                .post(body)
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new com.squareup.okhttp.Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        mHanlder.sendMessage(Message.obtain(mHanlder, 1, "登录失败"));

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        String string = response.body().string();
                        LogUtils.i(string);
                        if (null != string) {
                            try {
                                Headers headers = response.request().headers();
                                String cookie1 = headers.get("Cookie");
                                StringBuffer stringBuffer = new StringBuffer();
                                stringBuffer.append(cookie1);
                                JSONObject jsonObject = new JSONObject(string);
                                Log.e("TAG", "登陆成功jsonObject =" + jsonObject.toString());
                                if (null != jsonObject) {
                                    JSONObject variables = jsonObject.getJSONObject("Variables");
                                    JSONObject message = jsonObject.getJSONObject("Message");
                                    if (null != message) {
                                        String messagestr = message.getString("messagestr");
                                        String messageval = message.getString("messageval");
                                        if (messageval != null && (messageval.contains("login_succeed")|| messageval.contains("succeed"))) {
                                            UserInfoBean userInfoBean = new Gson().fromJson(jsonObject.toString(), UserInfoBean.class);
                                            ContentValues values = User.getContentValues(userInfoBean.getVariables());
                                            getContentResolver().insert(User.URI, values);//插入用户信息
                                            prepareUserData();
//                                            mWebViewClientBase.shouldOverrideUrlLoading(iv_code, AppNetConfig.LOGIN);
                                            CacheUtils.putString(LoginActivity.this, "username", username);
                                            CacheUtils.putString(LoginActivity.this, "password", password);
                                            CacheUtils.putString(LoginActivity.this, "questionId", finalQuestionId);
                                            CacheUtils.putString(LoginActivity.this, "answer", finalAnswer);
                                            CacheUtils.putBoolean(LoginActivity.this, "login", true);
                                            String auth = URLEncoder.encode(variables.getString("auth"), "UTF-8");
                                            String saltkey = URLEncoder.encode(variables.getString("saltkey"), "UTF-8");
                                            String cookiepre = userInfoBean.getVariables().getCookiepre();
                                            String cookiepre_auth = cookie1 + "auth=" + auth;
                                            String cookiepre_saltkey = cookie1 + "saltkey=" + saltkey;
                                            stringBuffer.append(cookiepre_auth).append(cookiepre_saltkey);
                                            CacheUtils.putString(LoginActivity.this, "cookiepre_auth", cookiepre_auth);
                                            CacheUtils.putString(LoginActivity.this, "cookiepre_saltkey", cookiepre_saltkey);
                                            CacheUtils.putString(LoginActivity.this, "userId", variables.getString("member_uid"));
                                            CacheUtils.putBoolean(LoginActivity.this, "login", true);
                                            CacheUtils.putString(LoginActivity.this, "formhash1", variables.getString("formhash"));
                                            CacheUtils.putString(LoginActivity.this, "formhash0", variables.getString("formhash"));
                                            CacheUtils.putString(LoginActivity.this, "Cookie0", LoginActivity.this.cookiepre);
                                            CacheUtils.putString(LoginActivity.this, "saltkey", userInfoBean.getVariables().getSaltkey());
//                                            RednetUtils.login(LoginActivity.this);
//                                            getUserInfo();
                                            MobclickAgent.onProfileSignIn("Official", username);
                                            CacheUtils.putString(LoginActivity.this, "cookie1", stringBuffer.toString());
                                            LogUtils.i("cookie---------------"+stringBuffer.toString());
                                            setResult(RESULT_OK);
//                                            Intent intent = new Intent(LoginActivity.this, RednetMainActivity.class);
//                                            startActivity(intent);
//                                            finish();
                                            return;
                                        } else if (messageval != null && messageval.contains("no_bind")){
                                            RelevanceLoginActivity.ComeIn(LoginActivity.this,openId,nickname,unionid,type);
                                        }else {
                                            mHanlder.sendMessage(Message.obtain(mHanlder, 1, messagestr));
                                        }
                                    } else {
                                        mHanlder.sendMessage(Message.obtain(mHanlder, 1, "登录失败"));
                                    }
                                } else {
                                    mHanlder.sendMessage(Message.obtain(mHanlder, 1, "登录失败"));
                                }
                            } catch (JSONException e) {
                                mHanlder.sendMessage(Message.obtain(mHanlder, 1, "请求异常"));
                                e.printStackTrace();
                            }
                        } else {
                            mHanlder.sendMessage(Message.obtain(mHanlder, 1, "登录失败"));
                        }
                    }
                });
    }

    void dissmissDialog(String toast) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        if (!TextUtils.isEmpty(toast)) {
            RednetUtils.toast(LoginActivity.this, toast);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_back:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.login_btn:
                if (mAccountEdit.getText().toString().trim().equals("") || mPwdEdit.getText().toString().trim()
                        .equals("")) {
                    RednetUtils.toast(this, "请输入用户名或密码");
                }
//                else if (ll_code.getVisibility() == View.VISIBLE && et_code.getText().toString().equals("")) {
//                    RednetUtils.toast(this, "请输入验证码");
//                }
                else if (questionid != 0 && mAnswerEdit.getText().toString().trim().equals("")) {
                    RednetUtils.toast(this, "请输入验证问题答案");
                } else if (ll_code.getVisibility() == View.VISIBLE && et_code.getText().toString().trim().equals("")) {
                    RednetUtils.toast(this, "请输入验证码");
                } else {
                    login();
//                    mProgressDialog.show("");
//                    Api.getInstance().requestSecure("login", new DataLoaderCallback(new InterDataLoaderListener() {
//                        @Override
//                        public void onLoadFinished(Object object) {
//                            String result = (String) object;
//                            if (result != null) {
//                                BaseModel<SecureVariables> vriablesBaseModel = new Gson().fromJson(result, new
//                                        TypeToken<BaseModel<SecureVariables>>() {
//                                        }.getType());
//                                if (vriablesBaseModel.getVariables() != null) {
//                                    RedNetApp.setFormHash(vriablesBaseModel.getVariables().getFormhash());
//                                    SecureVariables secure = vriablesBaseModel.getVariables();
//                                    seccode = secure.getSeccode();
//                                    sechash = secure.getSechash();
//                                    String cookiepre = vriablesBaseModel.getVariables().getCookiepre();
//                                    if (seccode != null && !seccode.equals("")) {  // 显示验证码！进行验证
//                                        Intent intent = new Intent();
//                                        intent.setClass(LoginActivity.this, CheckSecureDialogActivity.class);
//                                        intent.putExtra("seccode", seccode);
//                                        intent.putExtra("sechash", sechash);
//                                        intent.putExtra("cookiepre", cookiepre);
//                                        intent.putExtra("from", CheckSecureDialogActivity.FROM_LOGIN);
//                                        startActivityForResult(intent, LOGIN_SECURE_REQUEST_CODE);
//                                    } else {
//                                        mHanlder.sendEmptyMessage(3);
//                                    }
//                                } else {
//                                    mHanlder.sendEmptyMessage(3);
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onError(Object object) {
//                            mHanlder.sendEmptyMessage(3);
//                        }
//                    }));
                }
                break;
            case R.id.to_register: //跳转到注册页面
                startActivityForResult(new Intent(this, NewRegisterActivity.class), REGISTER_REQUEST_CODE);
                break;
            case R.id.question_text_view:
//                if (mAnswerView.getVisibility() != View.VISIBLE) {
//                    mAnswerView.setVisibility(View.VISIBLE);
//                }
                startActivityForResult(new Intent(this, SelectQuestionActivity.class), QUESTION_REQUEST_CODE);
                break;
            case R.id.qq:
            case R.id.wechat:
//                if (mProgressDialog != null) {
//                    if (!mProgressDialog.isShowing()) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mProgressDialog.show();
//                            }
//                        });
//
//                    }
//                    mProgressDialog.setLabel("正在登录");
                login(v.getId() == R.id.qq ? QQ.NAME : Wechat.NAME);//isQQ ? "get_simple_userinfo" : "snsapi_userinfo"
//                } else {
//                    dissmissDialog(null);
//                }
//                mProgressDialog.show();
//                mProgressDialog.setLabel("正在登陆");

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i(requestCode+"");
        if (resultCode == RESULT_OK) {
            if (requestCode == QUESTION_REQUEST_CODE) {
                String question = data.getStringExtra("question");
                questionid = data.getIntExtra("id", 0);
                mQuestionText.setText(question);
                if (questionid != 0) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(mAnswerEdit, InputMethodManager.SHOW_FORCED);
                    mAnswerEdit.requestFocus();
                    mAnswerView.setVisibility(View.VISIBLE);
                } else {
                    mAnswerView.setVisibility(View.GONE);
                }
            }
            if (requestCode == LOGIN_SECURE_REQUEST_CODE) {
                seccodeverify = data.getStringExtra("seccodeverify");
                vcodeCookie = RedNetPreferences.getVcodeCookie();
                loginSaltkey = RedNetPreferences.getSaltkey();
                login();
            }
            if (requestCode == REGISTER_REQUEST_CODE) {
                finish();
            }
        }else if (requestCode==10080){
            type = data.getStringExtra("type");
            openid = data.getStringExtra("openid");
            unionid = data.getStringExtra("unionid");
            logintype = data.getStringExtra("logintype");
            login_text.setText("关联");
            mProgressDialog.dismiss();
            mLoginView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    login(false,openId,unionid,type,"","");
                }
            });
        }
    }

    String type,openid,unionid,logintype;
    void prepareUserData() {
        //task = 2 | 4  = 6
        task = task1 | task2;
        User.prepareForum(this, new Callback() {
            @Override
            public void onCallback(Object obj) {
                //异或(^)：逻辑规则是两个不同就为真。 a^=b等价于a&=~b;
                task ^= task1;
                onTaskFinish();
            }
        });
        User.prepareThread(this, new Callback() {
            @Override
            public void onCallback(Object obj) {
                task ^= task2;
                onTaskFinish();
            }
        });
    }

    void onTaskFinish() {
        if ((task & task1) == 0 && (task & task2) == 0) {
//            dissmissDialog(null);
            finish();
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.account_edit:
//                if (hasFocus) {
//                    name_icon.setBackgroundResource(R.drawable.new_head_light);
//                    pwd_icon.setBackgroundResource(R.drawable.lock_dark);
//                    question_icon.setBackgroundResource(R.drawable.lock_dark);
//                }
                break;
            case R.id.pwd_edit:
//                if (hasFocus) {
//                    name_icon.setBackgroundResource(R.drawable.new_head_dark);
//                    pwd_icon.setBackgroundResource(R.drawable.lock_light);
//                    question_icon.setBackgroundResource(R.drawable.lock_dark);
//                }
                break;
            case R.id.answer_edit:
//                if (hasFocus) {
//                    name_icon.setBackgroundResource(R.drawable.new_head_dark);
//                    pwd_icon.setBackgroundResource(R.drawable.lock_dark);
//                    question_icon.setBackgroundResource(R.drawable.lock_light);
//                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("LoginActivity(登录)");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageEnd("LoginActivity(登录)");
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

    private void syncCookie() {
        try {
            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();// 移除
            cookieManager.removeAllCookie();//清除所有cookie
            Map<String, List<String>> map = new HashMap<>();
            Map<String, List<String>> cookieData = null;
            try {
                cookieData = RedNet.mHttpClient.getCookieHandler().get(URI.create(AppNetConfig.BASEURL), map);
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<String> cookieStrList = cookieData.get("Cookie");

            for (int i = 0; i < cookieStrList.size(); i++) {
                String[] cookies = cookieStrList.get(i).split(";");
                for (int j = 0; j < cookies.length; j++) {

                    cookieManager.setCookie(AppNetConfig.BASEURL, cookies[j]);
                    CookieSyncManager.getInstance().sync(); //cookies是在HttpClient中获得的cookie
                }
            }
            //参数 - 单独形式
//            cookieManager.setCookie(seccode, cookiepre + "seccode;");
//            cookieManager.setCookie(seccode, saltkey+ ";");
            //最后一定要调用
            CookieSyncManager.getInstance().sync();

        } catch (Exception e) {
//            Log.e("Nat: webView.syncCookie failed", e.toString());
        }
    }

    private WebViewClientBase mWebViewClientBase = new WebViewClientBase();

    private class WebViewClientBase extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            CookieSyncManager syncManager = CookieSyncManager.createInstance(LoginActivity.this);
            syncManager.sync();
            CookieManager cookieManager = CookieManager.getInstance();
            String cookie = cookieManager.getCookie(url);
            if (cookie == null) {
                return;
            }
            String[] cookies = cookie.split(";");
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].contains(cookiepre + "seccode")) {
                    RedNetPreferences.setVcodeCookie(cookies[i]);
                }
                if (cookies[i].contains(cookiepre + "saltkey")) {
                    RedNetPreferences.setSaltkey(cookies[i]);
                }
                if (cookies[i].contains(cookiepre + "seccode")) {
                    RedNetPreferences.setSeccode(cookies[i]);
                }
            }
            super.onPageFinished(view, url);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
