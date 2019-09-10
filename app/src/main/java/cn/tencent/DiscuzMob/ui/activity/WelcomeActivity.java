package cn.tencent.DiscuzMob.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.squareup.okhttp.Request;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.AsyncImageView;
import cn.tencent.DiscuzMob.widget.pagerindicator.CirclePageIndicator;

/**
 * Created by AiWei on 2016/4/19.
 */
public class WelcomeActivity extends BaseActivity implements View.OnClickListener {

    public static final String TYPE_NORMAL = "0";
    public static final String TYPE_VOTE = "1";
    public static final String TYPE_ACTIVITY = "4";
    public static final String FROM_WEB = "web";
    private ViewAnimator mViewAnimator;
    private ImageView mSplashImage;
    private ViewPager mReadmePager;
    private CirclePageIndicator mIndicator;
    private TextView mLabel;
    private View mTimer;
    private int mTime = 5;
    private boolean isSplashLoadingSuccess;
    private boolean isSpalshLoadingTimeout;
    private Runnable mDefaultRunnable, mSplashRunnable, mTimerRunnable;
    private String XGtid;
    private String XGtype;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mViewAnimator = (ViewAnimator) findViewById(R.id.va);
        mSplashImage = (ImageView) findViewById(R.id.splash);
        mLabel = (TextView) findViewById(R.id.label);
        mTimer = findViewById(R.id.timer);
        mTimer.setOnClickListener(this);
        SharedPreferences sp = getSharedPreferences("SP_Rednet", Context.MODE_PRIVATE);
//        if (CacheUtils.getBoolean(this, "login") == true) {
//            login();
//        }
//        if (CacheUtils.getBoolean(this, "otherLogin") == true) {
//            otherLogin();
//        }
//        if (sp.getBoolean("appIsFirstLaunch", true)) {
//            mReadmePager = (ViewPager) findViewById(R.id.pager);
//            mReadmePager.setAdapter(new GuidingPagerAdapter());
//            mReadmePager.setOffscreenPageLimit(6);
//            mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
//            mIndicator.setViewPager(mReadmePager);
//            sp.edit().putBoolean("appIsFirstLaunch", false).commit();
//        }

        openThreadFromWeb();

        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
        if (click != null) {
            try {
                JSONObject params = new JSONObject(click.getCustomContent());
                XGtid = params.optString("tid");
                XGtype = params.optString("type");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (XGtid != null && !XGtid.equals("")) {
            if (XGtype != null && !XGtype.equals("")) {
                if (XGtype.equals("1")) {//普通帖
                    startActivity(new Intent(WelcomeActivity.this, ThreadDetailsActivity.class).putExtra("id", XGtid).putExtra("from", "tuisong").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else if (XGtype.equals("2")) {//投票贴
                    startActivity(new Intent(WelcomeActivity.this, VoteThreadDetailsActivity.class).putExtra("id", XGtid).putExtra("from", "tuisong").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else if (XGtype.equals("3")) {//活动贴
                    startActivity(new Intent(WelcomeActivity.this, ActivityThreadDetailsActivity.class).putExtra("id", XGtid).putExtra("from", "tuisong").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            } else {
                //type为空 跳至普通帖
                startActivity(new Intent(WelcomeActivity.this, ThreadDetailsActivity.class).putExtra("id", XGtid).putExtra("from", "tuisong").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        } else {
            startSplash();
        }
    }

    private void otherLogin() {
        final String username = CacheUtils.getString(WelcomeActivity.this, "username1");
        final String password = CacheUtils.getString(WelcomeActivity.this, "password1");
        final String questionId = CacheUtils.getString(WelcomeActivity.this, "questionId");
        final String answer = CacheUtils.getString(WelcomeActivity.this, "answer");
        OkHttpUtils.post()
                .url(AppNetConfig.LOGIN)
                .addParams("username", username)
                .addParams("password", password)
                .addParams("questionid", questionId)
                .addParams("answer", answer)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        login();
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject message = jsonObject.getJSONObject("Message");
                            String messagestr = message.getString("messagestr");
                            JSONObject variables = jsonObject.getJSONObject("Variables");
                            String messageval = message.getString("messageval");
                            String member_uid = variables.getString("member_uid");
                            String formhash1 = variables.getString("formhash");
                            String cookiepre = variables.getString("cookiepre");
                            CacheUtils.putString(RedNetApp.getInstance(), "cookiepre", cookiepre);
                            if (messageval != null && messageval.equals("login_succeed")) {
//                                ContentValues values = User.getContentValues(variables);
//                                getContentResolver().insert(User.URI, values);//插入用户信息
                                RednetUtils.login(WelcomeActivity.this);
                                CacheUtils.putBoolean(WelcomeActivity.this, "login", true);
                                CacheUtils.putString(WelcomeActivity.this, "formhash1", formhash1);
                                CacheUtils.putString(WelcomeActivity.this, "username", username);
                                CacheUtils.putString(WelcomeActivity.this, "password", password);
                                CacheUtils.putString(WelcomeActivity.this, "questionId", questionId);
                                CacheUtils.putString(WelcomeActivity.this, "answer", answer);

                                CacheUtils.putString(WelcomeActivity.this, "Cookie", variables.getString("cookiepre"));
                                CacheUtils.putString(WelcomeActivity.this, "saltkey", variables.getString("saltkey"));
                                CacheUtils.putString(WelcomeActivity.this, "userId", member_uid);
                                MobclickAgent.onProfileSignIn("Official", username);
                                String platform = CacheUtils.getString(WelcomeActivity.this, "platform");
                                if (platform != null) {
                                    CacheUtils.putString(WelcomeActivity.this, "platform", platform);
                                    if (platform.equals("QQ")) {
                                        CacheUtils.putString(WelcomeActivity.this, "member_loginstatus", "qq");
                                    } else {
                                        CacheUtils.putString(WelcomeActivity.this, "member_loginstatus", "weixin");
                                    }
                                } else {
                                    RedNetApp.getInstance().getUserLogin(true).setMember_loginstatus("1");
                                }
                                String auth1 = URLEncoder.encode(variables.getString("auth"), "UTF-8");
                                String saltkey1 = URLEncoder.encode(variables.getString("saltkey"), "UTF-8");
                                String cookiepre_auth = cookiepre + "auth=" + auth1;
                                String cookiepre_saltkey = cookiepre + "saltkey=" + saltkey1;
                                CacheUtils.putString(WelcomeActivity.this, "cookiepre_auth", cookiepre_auth);
                                CacheUtils.putString(WelcomeActivity.this, "cookiepre_saltkey", cookiepre_saltkey);
                                setResult(RESULT_OK);
                                Intent intent = new Intent(WelcomeActivity.this, RednetMainActivity.class);
                                intent.putExtra("userId", member_uid);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(WelcomeActivity.this, messagestr, Toast.LENGTH_SHORT).show();
//                                login();
                            }
                        } catch (JSONException e) {
//                            login();
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
//                            login();
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void login() {
        final String username = CacheUtils.getString(WelcomeActivity.this, "username");
        final String password = CacheUtils.getString(WelcomeActivity.this, "password");
        final String questionId = CacheUtils.getString(WelcomeActivity.this, "questionId");
        final String answer = CacheUtils.getString(WelcomeActivity.this, "answer");
        OkHttpUtils.post()
                .url(AppNetConfig.LOGIN)
                .addParams("username", username)
                .addParams("password", password)
                .addParams("questionid", questionId)
                .addParams("answer", answer)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        login();
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject message = jsonObject.getJSONObject("Message");
                            String messagestr = message.getString("messagestr");
                            JSONObject variables = jsonObject.getJSONObject("Variables");
                            String messageval = message.getString("messageval");
                            String member_uid = variables.getString("member_uid");
                            String formhash1 = variables.getString("formhash");
                            String cookiepre = variables.getString("cookiepre");
                            CacheUtils.putString(RedNetApp.getInstance(), "cookiepre", cookiepre);
                            if (messageval != null && messageval.equals("login_succeed")) {
//                                ContentValues values = User.getContentValues(variables);
//                                getContentResolver().insert(User.URI, values);//插入用户信息
                                RednetUtils.login(WelcomeActivity.this);
                                CacheUtils.putBoolean(WelcomeActivity.this, "login", true);
                                CacheUtils.putString(WelcomeActivity.this, "formhash1", formhash1);
                                CacheUtils.putString(WelcomeActivity.this, "username", username);
                                CacheUtils.putString(WelcomeActivity.this, "password", password);
                                CacheUtils.putString(WelcomeActivity.this, "questionId", questionId);
                                CacheUtils.putString(WelcomeActivity.this, "answer", answer);

                                CacheUtils.putString(WelcomeActivity.this, "Cookie", variables.getString("cookiepre"));
                                CacheUtils.putString(WelcomeActivity.this, "saltkey", variables.getString("saltkey"));
                                CacheUtils.putString(WelcomeActivity.this, "userId", member_uid);
                                MobclickAgent.onProfileSignIn("Official", username);
                                RedNetApp.getInstance().getUserLogin(true).setMember_loginstatus("1");
                                String auth1 = URLEncoder.encode(variables.getString("auth"), "UTF-8");
                                String saltkey1 = URLEncoder.encode(variables.getString("saltkey"), "UTF-8");
                                String cookiepre_auth = cookiepre + "auth=" + auth1;
                                String cookiepre_saltkey = cookiepre + "saltkey=" + saltkey1;
                                CacheUtils.putString(WelcomeActivity.this, "cookiepre_auth", cookiepre_auth);
                                CacheUtils.putString(WelcomeActivity.this, "cookiepre_saltkey", cookiepre_saltkey);
                                setResult(RESULT_OK);
                                Intent intent = new Intent(WelcomeActivity.this, RednetMainActivity.class);
                                intent.putExtra("userId", member_uid);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(WelcomeActivity.this, messagestr, Toast.LENGTH_SHORT).show();
//                                login();
                            }
                        } catch (JSONException e) {
//                            login();
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
//                            login();
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void openThreadFromWeb() {
        Uri uri = getIntent().getData();
        if (uri == null) return;
        String type = uri.getQueryParameter("type");
        String tid = uri.getQueryParameter("tid");
        LogUtils.i("type=" + type + " tid=" + tid);
        if (TextUtils.isEmpty(type) || TextUtils.isEmpty(tid)) return;
        switch (type) {
            case TYPE_NORMAL:
                openThreadByWeb(tid, ThreadDetailsActivity.class);
                break;
            case TYPE_VOTE:
                openThreadByWeb(tid, VoteThreadDetailsActivity.class);
                break;
            case TYPE_ACTIVITY:
                openThreadByWeb(tid, ActivityThreadDetailsActivity.class);
                break;
        }
    }

    private void openThreadByWeb(String tid, Class<?> clazz) {
        startActivity(new Intent(WelcomeActivity.this, clazz).putExtra("id", tid).putExtra("from", FROM_WEB).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public void onBackPressed() {
    }

    void tomain() {
        if (getWindow() != null && getWindow().getDecorView() != null) {
            startActivity(new Intent(WelcomeActivity.this, RednetMainActivity.class));
            finish();
        }
    }

    void toAdDetails(String url) {
        startActivity(RednetUtils.newWebIntent(this, url).putExtra("fromIndex", true));
    }

    void startSplash() {
        isSpalshLoadingTimeout = false;
        isSplashLoadingSuccess = false;
        mSplashImage.postDelayed(mDefaultRunnable = new Runnable() {
            @Override
            public void run() {
                isSpalshLoadingTimeout = true;
                if (!isSplashLoadingSuccess) {
                    if (mReadmePager != null) {
                        clearTask();
                        mViewAnimator.setDisplayedChild(1);
                    } else {
                        mTimer.performClick();
                    }
                }
            }
        }, 2000);
//        ApiVersion5.INSTANCE.requestSplash(!RedNetApp.getInstance().networkIsOk()
//                , new ApiVersion5.Result<Object>(this, SplashVariables.class, !RedNetApp.getInstance().networkIsOk(), null, false) {
//                    @Override
//                    public void onResult(int code, Object value) {
//                        if (code != 0 && code != 504 && value instanceof BaseModel) {
//                            SplashVariables variables = ((BaseModel<SplashVariables>) value).getVariables();
//                            if (variables != null && variables.getOpenimage() != null) {
//                                final String url = variables.getOpenimage().getImgsrc();
//                                final String adUrl = variables.getOpenimage().getImgurl();
//                                if (TextUtils.isEmpty(url)) {
//                                    return;
//                                }
//                                Picasso.with(WelcomeActivity.this).load(variables.getOpenimage().getImgsrc()).into(mSplashImage, new Callback() {
//                                    @Override
//                                    public void onSuccess() {
//                                        isSplashLoadingSuccess = true;
//                                        if (!isSpalshLoadingTimeout) {
//                                            mSplashImage.postDelayed(mSplashRunnable = new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    if (mReadmePager != null) {
//                                                        clearTask();
//                                                        mViewAnimator.setDisplayedChild(1);
//                                                    } else {
//                                                        mTimer.performClick();
//                                                    }
//                                                }
//                                            }, 5000);
//                                            mTimer.setVisibility(View.VISIBLE);
//                                            mTimer.postDelayed(mTimerRunnable = new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    if (mTime >= 0) {
//                                                        mTime--;
//                                                        mLabel.setText(String.valueOf(mTime));
//                                                        mTimer.postDelayed(mTimerRunnable, 1000);
//                                                    } else {
//                                                        mTimer.performClick();
//                                                    }
//                                                }
//                                            }, 1000);
//                                            mSplashImage.setOnClickListener(new View.OnClickListener() {
//                                                @Override
//                                                public void onClick(View v) {
//                                                    clearTask();
//                                                    finish();
//                                                    toAdDetails(adUrl);
//                                                }
//                                            });
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onError() {
//                                        isSplashLoadingSuccess = false;
//                                    }
//                                });
//                            }
//                        }
//                    }
//                });
    }

    @Override
    public void onClick(View v) {
        clearTask();
        tomain();
    }

    void clearTask() {
        mSplashImage.removeCallbacks(mDefaultRunnable);
        mSplashImage.removeCallbacks(mSplashRunnable);
        mTimer.removeCallbacks(mTimerRunnable);
    }

    class GuidingPagerAdapter extends PagerAdapter {

        final int[] guide = new int[]{R.drawable.ic_guide_a, R.drawable.ic_guide_b, R.drawable.ic_guide_c,
                R.drawable.ic_guide_d, R.drawable.ic_guide_e, R.drawable.ic_guide_f};

        @Override
        public int getCount() {
            return guide.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View page = LayoutInflater.from(WelcomeActivity.this).inflate(R.layout.adapter_guide, container, false);
            AsyncImageView cover = (AsyncImageView) page.findViewById(R.id.cover);
            cover.setOnTouchListener(position == getCount() - 1 ? new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_CANCEL:
                        case MotionEvent.ACTION_UP:
                            Rect gvr = new Rect();
                            v.getGlobalVisibleRect(gvr);
                            int width = gvr.right - gvr.left;
                            int height = gvr.bottom - gvr.top;
                            Rect rect = new Rect(width / 3, height * 22 / 25, width * 2 / 3, height * 23 / 25);
                            int rx = (int) event.getRawX();
                            int ry = (int) event.getRawY();
                            if (rect.contains(rx, ry)) {
                                tomain();
                            }
                            break;
                    }
                    return false;
                }
            } : null);
            cover.setImageResource(guide[position]);
            container.addView(page, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return page;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("WelcomeActivity(启动页)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("WelcomeActivity(启动页)");
    }


}
