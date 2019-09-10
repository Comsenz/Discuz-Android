package cn.tencent.DiscuzMob.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.widget.NestedSwipeRefreshLayout;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.ConversationBean;
import cn.tencent.DiscuzMob.model.Smiley;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.adapter.FaceAdapter;
import cn.tencent.DiscuzMob.ui.adapter.PMItemAdapter;
import cn.tencent.DiscuzMob.ui.adapter.ViewPagerAdapter;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.R;


/**
 * Created by kurt on 15/8/25.
 */
public class MyPmActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private NestedSwipeRefreshLayout mRefreshView;
    private ListView mList;
    private PMItemAdapter mAdapter;
    private ImageView mBackBtn, mSmileyBtn;
    private TextView mTitle, mSubmit;
    private EditText mContent;
    private ProgressBar mProgress;
    private RelativeLayout mFaceViewLayout;
    private ViewPager vp_face;
    private LinearLayout layout_point;

    private ArrayList<ArrayList<Smiley>> emojis;
    private ArrayList<View> pageViews;
    private List<FaceAdapter> faceAdapters;
    private ArrayList<ImageView> pointViews;

    private String touid;
    private String tousername;
    private String inputType = "text";
    private int current = 0;
    private int mPage = 0;
    private int mRealPage;
    String cookiepre_auth;
    String cookiepre_saltkey;
    String formhash;
    private List<ConversationBean.VariablesBean.ListBean> messages = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 5) {
                RednetUtils.hideSoftInput(mContent.getApplicationWindowToken());
                mSmileyBtn.setImageResource(R.drawable.icon_face_smile);
                mFaceViewLayout.setVisibility(View.GONE);
                mContent.requestFocus();
            } else if (msg.what == 6) {
                RednetUtils.hideSoftInput(mContent.getApplicationWindowToken());
                if (emojis == null || emojis.isEmpty()) {
                    emojis = RedNet.smileManager.initSmileData();
                    Init_viewPager();
                    Init_Point();
                    Init_Data();
                }
                mSmileyBtn.setImageResource(R.drawable.icon_key);
                mFaceViewLayout.setVisibility(View.VISIBLE);
            } else if (msg.what == 7) {
                RednetUtils.toast(RedNetApp.getInstance(), "请求异常");
            } else if (msg.what == 8) {
                RednetUtils.toast(RedNetApp.getInstance(), "请求失败");
            }
            if (mProgress != null) mProgress.setVisibility(View.GONE);
        }
    };

    public static void startActivityToMyPm(Context context, String touid, String tousername, String formhash) {
        context.startActivity(new Intent(context, MyPmActivity.class).putExtra("touid", touid).putExtra("tousername", tousername).putExtra("formhash", formhash));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_details);
        cookiepre_auth = CacheUtils.getString(this, "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(this, "cookiepre_saltkey");
//        formhash = CacheUtils.getString(this, "formhash");
        touid = getIntent().getStringExtra("touid");
        Log.e("TAG", "touid=" + touid);
        tousername = getIntent().getStringExtra("tousername");
        formhash = getIntent().getStringExtra("formhash");

        mRefreshView = (NestedSwipeRefreshLayout) findViewById(R.id.refresh);
        mList = (ListView) findViewById(R.id.list);
        mSmileyBtn = (ImageView) findViewById(R.id.smiley_btn);
        mBackBtn = (ImageView) findViewById(R.id.back_btn);
        mTitle = (TextView) findViewById(R.id.action_bar_title);
        mSubmit = (TextView) findViewById(R.id.submit);
        mContent = (EditText) findViewById(R.id.pm_content);
        mContent.setImeOptions(EditorInfo.IME_ACTION_SEND);
        mContent.setImeActionLabel("发送", KeyEvent.KEYCODE_ENTER);
        mProgress = (ProgressBar) findViewById(R.id.progressbar);
        mFaceViewLayout = (RelativeLayout) findViewById(R.id.ll_facechoose);
        vp_face = (ViewPager) findViewById(R.id.vp_contains);
        layout_point = (LinearLayout) findViewById(R.id.iv_image);
        mTitle.setText(tousername);
        mList.setAdapter(mAdapter = new PMItemAdapter(this));
        findViewById(R.id.back_btn).setOnClickListener(this);
        mRefreshView.setOnRefreshListener(this);
        mSmileyBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mContent.setOnClickListener(this);
        mContent.setOnKeyListener(mOnKeyListener);

        mRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshView.setRefreshing(true);
                load(false);
            }
        }, 500);
    }


    private View.OnKeyListener mOnKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                mSubmit.performClick();
            }
            return false;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.smiley_btn:
                if (inputType.equals("smiley")) {
                    inputType = "text";
                    mHandler.sendEmptyMessage(5);
                } else if (inputType.equals("text")) {
                    inputType = "smiley";
                    mHandler.sendEmptyMessage(6);
                }
                break;
            case R.id.pm_content:
                hideFaceView();
                break;
            case R.id.back_btn:
                finish();
                break;
            case R.id.submit:
                sendMessage();
                break;
            default:
                return;
        }
    }

    void sendMessage() {
        String message = mContent.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            RednetUtils.toast(MyPmActivity.this, "消息内容不能为空");
        } else {
            RednetUtils.hideSoftInput(mContent.getApplicationWindowToken());
            mProgress.setVisibility(View.VISIBLE);
            RedNet.mHttpClient.newCall(new Request.Builder()
                    .addHeader("Cookie", RedNetApp.getInstance().getCookie())
//                    .url(AppNetConfig.BASEURL + "?module=sendpm&pmsubmit=yes&pmid=0&touid=" + touid + "&checkavatar=1&Android=1&smiley=no&convimg=1")
                    .url(AppNetConfig.BASEURL + "?module=sendpm&pmsubmit=yes&pmid=0&touid=" + touid + "&mobiletype=android&ios=1&version=5")
                    .post(new FormEncodingBuilder().add("formhash", formhash)
                            .addEncoded("username", tousername)
                            .addEncoded("message", message).build())
                    .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mProgress.setVisibility(View.GONE);
                                    Toast.makeText(MyPmActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
//                            Log.e("TAG", "jsonObject000000000000000000000=" + response.body().string());
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());

                                if (null != jsonObject) {
                                    JSONObject message1 = jsonObject.getJSONObject("Message");
                                    String messageval = message1.getString("messageval");
                                    final String messagestr = message1.getString("messagestr");
                                    if (messageval != null && messageval.contains("do_success")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mRefreshView.setRefreshing(true);
                                                mPage = 0;
                                                mRefreshView.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        mRefreshView.setRefreshing(true);
                                                        load(false);
                                                    }
                                                }, 500);
                                                mContent.setText("");
                                                if (inputType.equals("smiley")) {
                                                    inputType = "text";
                                                    mHandler.sendEmptyMessage(5);
                                                }
                                                Toast.makeText(MyPmActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(MyPmActivity.this, messagestr, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MyPmActivity.this, "请求异常", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MyPmActivity.this, "请求异常", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.showSoftInput(mContent, InputMethodManager.SHOW_FORCED);

                                    imm.hideSoftInputFromWindow(mContent.getWindowToken(), 0); //强制隐藏键盘
                                    if (mProgress != null) {
                                        mProgress.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                    });
        }
    }

    private void Init_viewPager() {
        pageViews = new ArrayList<>();
        View emptyView = new View(getApplicationContext());
        emptyView.setBackgroundColor(Color.TRANSPARENT);
        pageViews.add(emptyView);
        faceAdapters = new ArrayList<>();
        for (int i = 0; i < emojis.size(); i++) {
            GridView view = new GridView(getApplicationContext());
            FaceAdapter adapter = new FaceAdapter(getApplicationContext(), emojis.get(i));
            view.setAdapter(adapter);
            faceAdapters.add(adapter);
            view.setOnItemClickListener(this);
            view.setNumColumns(7);
            view.setBackgroundColor(Color.TRANSPARENT);
            view.setHorizontalSpacing(1);
            view.setVerticalSpacing((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
            view.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            view.setCacheColorHint(0);
            view.setPadding(10, 10, 10, 10);
            view.setSelector(new ColorDrawable(Color.TRANSPARENT));
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            view.setGravity(Gravity.CENTER);
            pageViews.add(view);
        }
        emptyView = new View(getApplicationContext());
        emptyView.setBackgroundColor(Color.TRANSPARENT);
        pageViews.add(emptyView);
    }

    private void Init_Point() {
        pointViews = new ArrayList<>();
        ImageView imageView;
        for (int i = 0; i < pageViews.size(); i++) {
            imageView = new ImageView(getApplicationContext());
            imageView.setBackgroundResource(R.drawable.d1);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            layoutParams.width = 15;
            layoutParams.height = 15;
            layout_point.addView(imageView, layoutParams);
            if (i == 0 || i == pageViews.size() - 1) {
                imageView.setVisibility(View.GONE);
            }
            if (i == 1) {
                imageView.setBackgroundResource(R.drawable.d2);
            }
            pointViews.add(imageView);
        }
    }

    private void Init_Data() {
        vp_face.setAdapter(new ViewPagerAdapter(pageViews));
        vp_face.setCurrentItem(1);
        current = 0;
        vp_face.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                current = arg0 - 1;
                draw_Point(arg0);
                // 如果是第一屏或者是最后一屏禁止滑动，其实这里实现的是如果滑动的是第一屏则跳转至第二屏，如果是最后一屏则跳转到倒数第二屏.
                if (arg0 == pointViews.size() - 1 || arg0 == 0) {
                    if (arg0 == 0) {
                        vp_face.setCurrentItem(arg0 + 1);// 第二屏 会再次实现该回调方法实现跳转.
                        pointViews.get(1).setBackgroundResource(R.drawable.d2);
                    } else {
                        vp_face.setCurrentItem(arg0 - 1);// 倒数第二屏
                        pointViews.get(arg0 - 1).setBackgroundResource(
                                R.drawable.d2);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }

    public void draw_Point(int index) {
        for (int i = 1; i < pointViews.size(); i++) {
            if (index == i) {
                pointViews.get(i).setBackgroundResource(R.drawable.d2);
            } else {
                pointViews.get(i).setBackgroundResource(R.drawable.d1);
            }
        }
    }

    public boolean hideFaceView() {        // 隐藏表情选择框
        if (mFaceViewLayout.getVisibility() == View.VISIBLE) {
            mFaceViewLayout.setVisibility(View.GONE);
            mSmileyBtn.setImageResource(R.drawable.icon_face_smile);
            inputType = "text";
            return true;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Smiley emoji = (Smiley) faceAdapters.get(current).getItem(position);
        if (!TextUtils.isEmpty(emoji.getCode())) {
            mContent.append(emoji.getCode());
        }
    }

    String count;

    void load(final boolean append) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(AppNetConfig.CONVERSATION + "&touid=" + touid + "&page=" + String.valueOf(mPage))
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MyPmActivity.this, "会话内容请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.body().string());
                        Log.e("TAG", "jsonObject=" + jsonObject);
                        ConversationBean conversationBean = null;
                        try {
                            conversationBean = new Gson().fromJson(jsonObject.toString(), ConversationBean.class);
                            if (mRefreshView != null) {
                                final ConversationBean finalConversationBean = conversationBean;
                                final String formhash = conversationBean.getVariables().getFormhash();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (finalConversationBean != null) {
                                            ConversationBean.VariablesBean variables = finalConversationBean.getVariables();
                                            mPage = Integer.parseInt(variables.getPage());
                                            count = variables.getCount();
                                            List<ConversationBean.VariablesBean.ListBean> list = variables.getList();
                                            for (int i = 0; i < list.size(); i++) {

                                            }
                                            if (append) {
                                                messages.addAll(0, list);
                                                mAdapter.append(list);
                                            } else {
                                                Log.e("TAG", "list=" + list.toString());
                                                messages.clear();
                                                messages.addAll(list);
                                                mAdapter.cleanDate();
                                                mAdapter.set(list, formhash);
                                            }
                                        }
                                        mRefreshView.setRefreshing(false);
                                    }
                                });

                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @Override
    public void onRefresh() {
        if (RednetUtils.networkIsOk(this) && messages.size() < Integer.parseInt(count)) {
            --mPage;
            load(true);
        } else {
            Toast.makeText(MyPmActivity.this, "没有更多回复", Toast.LENGTH_SHORT).show();
            mRefreshView.setRefreshing(false);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("MyPmActivity(聊天页)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("MyPmActivity(聊天页)");
    }

    public static Html.ImageGetter imgGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(final String source) {

            URL url;
            Drawable drawable = null;
            try {
                url = new URL(source);
                drawable = Drawable.createFromStream(
                        url.openStream(), null);
                drawable.setBounds(0, 0,
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return drawable;


//            int resId = android.R.drawable.screen_background_light_transparent;
//            if (!TextUtils.isEmpty(source)) {
//                for (int i = 0; i < SmileManager.CODE_DECODE.length; i++) {
//                    if (source.contains(SmileManager.CODE_DECODE[i])) {
//                        resId = SmileManager.EMOJ[i];
//                        break;
//                    }
//                }
//            }
//            Drawable drawable = null;
//            if (resId != 0) {
//                Bitmap bit = BitmapFactory.decodeResource(context.getResources(), resId);
//                drawable = resizeImage(bit, 140, 140);
//                if (drawable != null) {
//                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
//                            .getIntrinsicHeight());
//                }
//            }
//            return drawable;
        }
    };
}
