package cn.tencent.DiscuzMob.ui.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.ui.Event.RefreshUserInfo;
import cn.tencent.DiscuzMob.utils.Callback;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.db.User;
import cn.tencent.DiscuzMob.model.UserInfoBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.VerifyCode;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.tencent.DiscuzMob.R;

/**
 * 第三方登陸後 綁定已有论坛账号
 */
public class LoginBindingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView login_back;
    private ImageView iv_showCode;
    private TextView tv_bind;
    private EditText et_username;
    private EditText et_password;
    private EditText et_verifycode;
    //产生的验证码
    private String realCode;
    private String inputcode;
    private String username;
    private String password;

    private String type;
    private String openId;

    private int task, task1 = 1 << 1, task2 = 2 << 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_binding);
        try {
            openId = getIntent().getStringExtra("openId");
            type = getIntent().getStringExtra("type");
            if (QQ.NAME.equals(type)) {
                type = "qq";
            } else if (Wechat.NAME.equals(type)) {
                type = "weixin";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        initView();
    }

    private void initView() {

        login_back = (ImageView) findViewById(R.id.login_back);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_verifycode = (EditText) findViewById(R.id.et_verifycode);//输入验证码
        iv_showCode = (ImageView) findViewById(R.id.iv_showCode);//验证码图片
        tv_bind = (TextView) findViewById(R.id.tv_bind);

        //将验证码用图片的形式显示出来
        iv_showCode.setImageBitmap(VerifyCode.getInstance().createBitmap());
        realCode = VerifyCode.getInstance().getCode();//产生的验证码

        login_back.setOnClickListener(this);
        iv_showCode.setOnClickListener(this);
        tv_bind.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_back:
                finish();
                break;
            case R.id.iv_showCode://点击验证码图片刷新
                iv_showCode.setImageBitmap(VerifyCode.getInstance().createBitmap());
                realCode = VerifyCode.getInstance().getCode();//产生的验证码
                break;
            case R.id.tv_bind://绑定
                getEditTextValue();

                if (username == null || username.equals("")) {
                    RednetUtils.toast(LoginBindingActivity.this, "请输入用户名/邮箱");
                    return;
                }
                if (password == null || password.equals("")) {
                    RednetUtils.toast(LoginBindingActivity.this, "请输入密码");
                    return;
                }

                if (inputcode != null && !inputcode.equals("")) {
                    if (realCode.equalsIgnoreCase(inputcode)) {
                        toLogin();
                    } else {
                        RednetUtils.toast(LoginBindingActivity.this, "验证码错误");
                    }
                } else {
                    RednetUtils.toast(LoginBindingActivity.this, "请输入验证码");
                }
                break;
        }
    }

    private String unionid = "";

    private void toLogin() {
        Log.e("TAG", "绑定的url =" + new StringBuilder(AppNetConfig.BASEURL)
                .append("?module=weiqqlogin")
                .append("&version=5&debug=1&android=1")
                .append("&openid=").append(openId)
                .append("&type=").append(type)
                .append("&username=").append(username)
                .append("&password=").append(password)
                .toString());
        if (type.equals("weixin")) {
            Platform weChat = ShareSDK.getPlatform(Wechat.NAME);
            unionid = weChat.getDb().get("unionid");
        } else {
            unionid = "";
        }
        Log.e("TAG", "unionid2=" + unionid);
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(new StringBuilder(AppNetConfig.BASEURL)
                        .append("?module=weiqqlogin")
                        .append("&version=5&debug=1&android=1")
                        .append("&openid=").append(openId)
                        .append("&type=").append(type)
                        .append("&username=").append(username)
                        .append("&password=").append(password)
                        .append("&unionid=").append(unionid)
                        .toString())
                .build())
                .enqueue(new com.squareup.okhttp.Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.e("TAG", "绑定请求失败");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginBindingActivity.this, "绑定请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (string != null) {
//                            BaseVariables variables = ((BaseModel<BaseVariables>) value).getVariales();
                            try {
                                JSONObject jsonObject = new JSONObject(string);
                                Log.e("TAG", "绑定请求成功 = " + jsonObject.toString());
                                JSONObject variables = jsonObject.getJSONObject("Variables");
                                JSONObject message = jsonObject.getJSONObject("Message");
                                final String messagestr = message.getString("messagestr");
                                if (variables != null) {
//                                    InfoMessage message = ((BaseModel<SearchVariables>) value).getMessage();
                                    UserInfoBean userInfoBean = new Gson().fromJson(jsonObject.toString(), UserInfoBean.class);
                                    ContentValues values = User.getContentValues(userInfoBean.getVariables());
                                    getContentResolver().insert(User.URI, values);//插入用户信息
                                    if (message != null) {
                                        if (message.getString("messagestatus").equals("1")) {//成功
                                            if (values != null) {
                                                getContentResolver().insert(User.URI, values);//插入用户信息
                                                MobclickAgent.onProfileSignIn("Official(binding)", values.getAsString("member_username"));
                                            }
                                            String platform = CacheUtils.getString(LoginBindingActivity.this, "platform");
                                            if (platform != null) {
                                                CacheUtils.putString(LoginBindingActivity.this, "platform", platform);
                                                Log.e("TAG", "type=" + type);
                                                if (type.equals("qq")) {
                                                    CacheUtils.putString(LoginBindingActivity.this, "member_loginstatus", "qq");
                                                } else {
                                                    CacheUtils.putString(LoginBindingActivity.this, "member_loginstatus", "weixin");
                                                }
                                            } else {
                                                RedNetApp.getInstance().getUserLogin(true).setMember_loginstatus("1");
                                            }
                                            CacheUtils.putBoolean(LoginBindingActivity.this, "login", true);
                                            String formhash1 = variables.getString("formhash");
                                            CacheUtils.putString(LoginBindingActivity.this, "formhash1", formhash1);
                                            String auth1 = URLEncoder.encode(variables.getString("auth"), "UTF-8");
                                            String saltkey1 = URLEncoder.encode(variables.getString("saltkey"), "UTF-8");
                                            String cookiepre = variables.getString("cookiepre");
                                            String cookiepre_auth = cookiepre + "auth=" + auth1;
                                            String cookiepre_saltkey = cookiepre + "saltkey=" + saltkey1;
                                            CacheUtils.putString(LoginBindingActivity.this, "cookiepre_auth", cookiepre_auth);
                                            CacheUtils.putString(LoginBindingActivity.this, "cookiepre_saltkey", cookiepre_saltkey);
                                            CacheUtils.putBoolean(LoginBindingActivity.this, "otherLogin", true);
                                            CacheUtils.putString(LoginBindingActivity.this, "username1", username);
                                            CacheUtils.putString(LoginBindingActivity.this, "username", username);
                                            CacheUtils.putString(LoginBindingActivity.this, "password1", password);
                                            prepareUserData();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    EventBus.getDefault().post(new RefreshUserInfo("0"));
//                                                    Toast.makeText(LoginBindingActivity.this, "绑定失败", Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(LoginBindingActivity.this, messagestr, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else {
//                                            RednetUtils.toast(LoginBindingActivity.this, "绑定失败");
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
//                                                    Toast.makeText(LoginBindingActivity.this, "绑定失败", Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(LoginBindingActivity.this, messagestr, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginBindingActivity.this, "绑定异常", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                e.printStackTrace();
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginBindingActivity.this, "绑定失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

    }

    private void getEditTextValue() {
        inputcode = et_verifycode.getText().toString();
        username = et_username.getText().toString();
        password = et_password.getText().toString();
    }

    void prepareUserData() {
        //task = 2 | 4  = 6
        task = task1 | task2;
        User.prepareForum(this, new Callback() {
            @Override
            public void onCallback(Object obj) {
                //异或(^)：逻辑规则是两个不同就为真。 a^=b等价于a = a^b
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
            Intent i = new Intent(LoginBindingActivity.this, RednetMainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("LoginBindingActivity(账号关联)");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageEnd("LoginBindingActivity(账号关联)");
    }

}
