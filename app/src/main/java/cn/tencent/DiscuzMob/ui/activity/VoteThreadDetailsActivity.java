package cn.tencent.DiscuzMob.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jingchen.pulltorefresh.PullToRefreshLayout;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.Config;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.db.UserThread;
import cn.tencent.DiscuzMob.js.WebViewWrapper;
import cn.tencent.DiscuzMob.js.ZhangShangJavaScriptInterface;
import cn.tencent.DiscuzMob.manager.DataLoaderCallback;
import cn.tencent.DiscuzMob.manager.DataLoaderListener;
import cn.tencent.DiscuzMob.manager.InterDataLoaderListener;
import cn.tencent.DiscuzMob.manager.TThreadManager;
import cn.tencent.DiscuzMob.model.CollecttheadBean;
import cn.tencent.DiscuzMob.model.DetailsBean;
import cn.tencent.DiscuzMob.model.LinkReplyVariables;
import cn.tencent.DiscuzMob.model.MyFavThreadBean;
import cn.tencent.DiscuzMob.model.PraiseBean;
import cn.tencent.DiscuzMob.model.QuoteBean;
import cn.tencent.DiscuzMob.model.SecureVariables;
import cn.tencent.DiscuzMob.model.Smiley;
import cn.tencent.DiscuzMob.net.Api;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.net.FileUploader;
import cn.tencent.DiscuzMob.ui.adapter.FaceAdapter;
import cn.tencent.DiscuzMob.ui.adapter.ViewPagerAdapter;
import cn.tencent.DiscuzMob.ui.dialog.CheckSecureDialogActivity;
import cn.tencent.DiscuzMob.ui.dialog.ImageChooserDialog;
import cn.tencent.DiscuzMob.ui.dialog.SelectReportActivity;
import cn.tencent.DiscuzMob.ui.dialog.ShareDialog;
import cn.tencent.DiscuzMob.utils.BitmapUtils;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;

/**
 * Created by kurt on 2015/7/30.
 * (投票)帖子详情
 */
public class VoteThreadDetailsActivity extends BaseActivity implements View.OnClickListener, ZhangShangJavaScriptInterface.OnActionListener, AdapterView.OnItemClickListener, PullToRefreshLayout.OnPullListener {

    private static final int REPORT_REQUEST_CODE = 0;
    private static final int SECURE_REQUEST_CODE = 1;
    private boolean isClick = true;
    private View mPostSubmit;
    private ImageView mAction, mSmileyBtn;
    private TextView mTitle, mWarning;
    private EditText mPostContent;
    private ProgressBar mProgressBar;
    private WebViewWrapper mWebWrapper;
    private WebView mWebView;
    private ShareDialog mShareDialog;
    private PopupWindow popupWindow;
    private TextView menuFav;
    private TextView menuPoster;

    private RelativeLayout mFaceViewLayout;
    private ViewPager vp_face;
    private LinearLayout layout_point;
    private ArrayList<ArrayList<Smiley>> emojis;
    private ArrayList<View> pageViews;
    private List<FaceAdapter> faceAdapters;
    private ArrayList<ImageView> pointViews;
    private int current = 0;
    private String inputType = "text";

    private String tid;
    private String fid;
    private int replies;
    private String rid;
    private int limit;
    private int page = 1;
    ArrayList<JSONObject> temp_data = new ArrayList<>();
    private String closed;
    private String allowvote;
    private String allowvotepolled;
    private String allowvotethread;
    private boolean isLoadImg;
    private String title, thumburl;
    private String authorId;
    private String mShareText;
    private String mReplyAnchorId;
    private LinkReplyVariables mLinkReply;//引用回复
    private boolean onlyHost;

    private String seccodeverify;
    private String vcodeCookie;
    private String seccode;
    private String sechash;
    private String cookiepre;
    private String from;
    String cookiepre_auth;
    String cookiepre_saltkey;
    String formhash;
    private QuoteBean.VariablesBean variables;
    private LinearLayout ll_reply, ll_other;
    private FileUploader mFileUploader;
    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            if (WelcomeActivity.FROM_WEB.equals(from)) {
                mWebView.loadUrl("javascript:isMobile()");
            }
        }
    };

    private UserThread mUserThread;
    long expirations;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                mProgressBar.setVisibility(View.GONE);
                if (mLoadType == LOAD_TYPE_REFRESH) {
                    mPull2RefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                } else {
                    mPull2RefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
                final String finalResult = (String) msg.obj;
                if (!TextUtils.isEmpty(finalResult)) {
                    mWebView.clearHistory();
                    if (page == 1) {
                        boolean resultPage = true;
                        long time = System.currentTimeMillis() / 1000;
                        if (expirations < time) {
                            resultPage = true;
                        } else {
                            resultPage = false;
                        }
                        if ("1".equalsIgnoreCase(closed)) {
                            mWarning.setText("该投票已经关闭或者过期，不能投票");
                            resultPage = true;
                        } else if (!"1".equalsIgnoreCase(allowvote)) {
//                            mWarning.setText("您已经投过票，谢谢您的参与");
                            resultPage = true;
                        } else {
                            resultPage = false;
                        }
                        if (resultPage) {
                            mWarning.setVisibility(View.VISIBLE);
                            mWebView.loadUrl("file:///android_asset/templates/thread_temp_poll_result.html");
                        } else {
                            mWarning.setVisibility(View.GONE);
                            mWebView.loadUrl("file:///android_asset/templates/thread_temp_poll.html");
                        }
                        mWebWrapper.setTaskOnLoadFinish(new Runnable() {
                            @Override
                            public void run() {
                                mWebView.loadUrl("javascript:setImgLoadable(" + (RedNetPreferences.getImageSetting() != 1) + ");");
                                if (TextUtils.isEmpty(mReplyAnchorId)) {
                                    mWebView.loadUrl("javascript:onDiscussSuccess(" + finalResult + "," + isLoadImg + "," + mReplyAnchorId + ");");
                                    mReplyAnchorId = null;
                                } else {
                                    mWebView.loadUrl("javascript:onRefresh(" + finalResult + "," + isLoadImg + ");");
                                }
                                if (replies == 0) {
                                    mWebView.loadUrl("javascript:enableAllComment();");
                                }
                            }
                        });
                    } else {
                        mWebView.loadUrl("javascript:onLoadReply(" + finalResult + "," + isLoadImg + ");");
                        if (temp_data.size() - 1 <= replies) {
                            mWebView.loadUrl("javascript:onLoadOver();");
                        }
                    }
                }
            } else if (msg.what == 2) {
                imgPaths.clear();
                aids.clear();
                mPull2RefreshLayout.autoRefresh();
            } else if (msg.what == 3) {
                discussUserComment((String) msg.obj);
            } else if (msg.what == 5) {
                RednetUtils.toast(VoteThreadDetailsActivity.this, (String) msg.obj);
            } else if (msg.what == 6) {
                RednetUtils.toast(VoteThreadDetailsActivity.this, (String) msg.obj);
                isZan = true;
                iv_fabulous.setImageResource(R.drawable.zans);
//                mWebView.loadUrl("javascript:onPraiseSuccess();");
                mHandler.sendEmptyMessageDelayed(2, 1500);
            } else if (msg.what == 9) {
                RedNetApp.getInstance().IMM.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                mPostContent.requestFocus();
            } else if (msg.what == 10) {
                RednetUtils.toast(VoteThreadDetailsActivity.this, "举报成功");
            } else if (msg.what == 13) {
                sendMsg();
            } else if (msg.what == 15) {//表情模式
                ll_photo.setVisibility(View.GONE);
                RednetUtils.hideSoftInput(mPostContent.getApplicationWindowToken());
                if (emojis == null || emojis.isEmpty()) {
                    emojis = RedNet.smileManager.initSmileData();
                    Init_viewPager();
                    Init_Point();
                    Init_Data();
                }
                mSmileyBtn.setImageResource(R.drawable.icon_key);
                mFaceViewLayout.setVisibility(View.VISIBLE);
            } else if (msg.what == 16) {
                ll_photo.setVisibility(View.GONE);
                RednetUtils.hideSoftInput(mPostContent.getApplicationWindowToken());
                mSmileyBtn.setImageResource(R.drawable.icon_face_smile);
                mFaceViewLayout.setVisibility(View.GONE);
                mPostContent.requestFocus();
            } else if (msg.what == 17) {
                RednetUtils.hideSoftInput(mPostContent.getApplicationWindowToken());
                mSmileyBtn.setImageResource(R.drawable.icon_face_smile);
                mFaceViewLayout.setVisibility(View.GONE);
                ll_photo.setVisibility(View.VISIBLE);

            } else if (msg.what == 18) {
                final String s = String.valueOf(msg.obj);
                Log.e("TAG", "uploadhash=" + uploadhash);
                mFileUploader.submit(String.valueOf(msg.obj), uploadhash, fid, new FileUploader.CallbackImpl<Object>(String.valueOf(msg.obj)) {
                    @Override
                    public void onCallback(Object obj) {
                        Log.e("TAG", "obj=" + obj);
                        mProgressBar.setVisibility(View.VISIBLE);
                        if (Integer.parseInt(obj.toString()) > 0) {
                            aids.add(obj.toString());
                            showPhoto(s);
                        } else {
                            isClick = true;
                            Toast.makeText(VoteThreadDetailsActivity.this, obj.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else {
                if (msg.what == -10001) {
                    if (msg.obj instanceof String) {
                        RednetUtils.toast(VoteThreadDetailsActivity.this, (String) msg.obj);
                    } else {
                        RednetUtils.toast(VoteThreadDetailsActivity.this, "请求失败");
                    }
                    if (TextUtils.isEmpty(mWebView.getUrl()))
                        mWebView.loadUrl("file:///android_asset/templates/thread_temp_poll.html");
                }
            }
        }
    };
    private List<ImageView> imageViews = new ArrayList<>();
    private boolean isZan = false;
    private PullToRefreshLayout mPull2RefreshLayout;
    private boolean mLoadAll;

    private void showPhoto(final String path) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
        final View inflate = View.inflate(VoteThreadDetailsActivity.this, R.layout.picture_item, null);
        ImageView iv_item = (ImageView) inflate.findViewById(R.id.iv_item);
        final ImageView iv_del = (ImageView) inflate.findViewById(R.id.iv_del);
        iv_item.setScaleType(ImageView.ScaleType.FIT_XY);
        iv_item.setPadding(0, 0, 10, 0);
        imageViews.add(iv_del);
        mProgressBar.setVisibility(View.GONE);
        if (imgPaths.size() == 10) {
            iv_addpicture.setVisibility(View.GONE);
        } else {
            iv_addpicture.setVisibility(View.VISIBLE);
        }
        iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i1 = imageViews.indexOf(iv_del);
                mFileUploader.cancel(imgPaths.get(i1), uploadhash);
                imageViews.remove(i1);
                imgPaths.remove(i1);
                aids.remove(i1);
                post_pic_container.removeView(inflate);
                iv_addpicture.setVisibility(View.VISIBLE);
            }
        });

        iv_item.setImageURI(Uri.parse(path));
        post_pic_container.addView(inflate, post_pic_container.getChildCount() - 1);
        isClick = true;
    }

    private ImageView iv_fabulous, iv_collect;
    private boolean isCollection;
    private String favid;
    private ImageView iv_share;
    private RelativeLayout ll_photo;
    private HorizontalScrollView post_scrollview;
    private LinearLayout post_pic_container;
    private ImageView iv_addpicture;
    private ImageChooserDialog mImageChooserDialog;
    private List<String> imgPaths = new ArrayList<>();
    private List<String> aids = new ArrayList<>();
    private final Object mObj = new Object();
    String uploadhash;
    private ImageView camera_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageChooserDialog = this != null ? new ImageChooserDialog(this) : null;
        mFileUploader = FileUploader.newInstance(this, mObj);
        setContentView(R.layout.activity_thread_details);
        ll_photo = (RelativeLayout) findViewById(R.id.ll_photo);
        camera_btn = (ImageView) findViewById(R.id.camera_btn);
        camera_btn.setOnClickListener(this);
        post_scrollview = (HorizontalScrollView) findViewById(R.id.post_scrollview);
        post_scrollview.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
        post_pic_container = (LinearLayout) findViewById(R.id.post_pic_container);
        iv_addpicture = (ImageView) findViewById(R.id.iv_addpicture);
        iv_addpicture.setOnClickListener(this);
        cookiepre_auth = CacheUtils.getString(this, "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(this, "cookiepre_saltkey");
        formhash = CacheUtils.getString(this, "formhash");
        String title = getIntent().getStringExtra("title");
        if (getIntent().getData() != null && "cn.rednet.bbs".equalsIgnoreCase(getIntent().getData().getScheme())) {
            String[] values = RednetUtils.findParams(getIntent().getData(), "tid");
            if (values != null && values.length > 0)
                tid = values[0];
        } else {
            tid = getIntent().getStringExtra("id");
            fid = getIntent().getStringExtra("fid");
            from = getIntent().getStringExtra("from");
        }
        isLoadImg = RedNetPreferences.getImageSetting() != 1;
        mPostSubmit = findViewById(R.id.submit);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        mPull2RefreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        mPull2RefreshLayout.setOnPullListener(this);
        mWebView = (WebView) findViewById(R.id.content_view);
        mWebView.setWebViewClient(mWebViewClient);
        mWebWrapper = new WebViewWrapper(mWebView, mProgressBar, this, this);

        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(title);
        mWarning = (TextView) findViewById(R.id.warning);
        mSmileyBtn = (ImageView) findViewById(R.id.smiley_btn);
        mAction = (ImageView) findViewById(R.id.action_btn);
        mFaceViewLayout = (RelativeLayout) findViewById(R.id.ll_facechoose);
        vp_face = (ViewPager) findViewById(R.id.vp_contains);
        layout_point = (LinearLayout) findViewById(R.id.iv_image);
        mPostContent = (EditText) findViewById(R.id.post_content);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.post).setOnClickListener(this);
        mPostContent.setOnClickListener(this);
        mPostContent.setOnEditorActionListener(mOnEditorActionListener);
        mPostSubmit.setOnClickListener(this);
        mSmileyBtn.setOnClickListener(this);
        mAction.setOnClickListener(this);
        ll_reply = (LinearLayout) findViewById(R.id.ll_reply);
        ll_other = (LinearLayout) findViewById(R.id.ll_other);
        iv_fabulous = (ImageView) findViewById(R.id.iv_fabulous);
        iv_collect = (ImageView) findViewById(R.id.iv_collect);
        iv_collect.setOnClickListener(this);
        iv_fabulous.setOnClickListener(this);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_share.setOnClickListener(this);
        if (!TextUtils.isEmpty(tid)) {
            mHandler.sendEmptyMessageDelayed(2, 500);
        }
        mPostContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {//获得焦点
                    ll_reply.setVisibility(View.VISIBLE);
                    ll_other.setVisibility(View.GONE);
                } else {//失去焦点
                    ll_reply.setVisibility(View.GONE);
                    ll_other.setVisibility(View.VISIBLE);
                    hideFaceView();
                    hidePotoView();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mPostContent.getWindowToken(), 0); //强制隐藏键盘
                }
            }
        });
        getDataFromNet();
    }

    private boolean hidePotoView() {//隐藏图片的框
        if (ll_photo.getVisibility() == View.VISIBLE) {
            ll_photo.setVisibility(View.GONE);
            inputType = "text";
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        XGPushClickedResult result = XGPushManager.onActivityStarted(this);
        if (result != null) {
            try {
                JSONObject params = new JSONObject(result.getCustomContent());
                boolean refresh = TextUtils.isEmpty(tid);
                tid = params.optString("id");
                if (refresh && !TextUtils.isEmpty(tid)) {
                    mHandler.sendEmptyMessageDelayed(2, 500);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("VoteThreadDetailsActivity(投票详情)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        XGPushManager.onActivityStoped(this);
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("VoteThreadDetailsActivity(投票详情)");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_collect:
//                popupWindow.dismiss();
                if (RednetUtils.hasLogin(this) && RednetUtils.networkIsOk(this)) {
//                    iv_collect.setEnabled(false);
                    mProgressBar.setVisibility(View.VISIBLE);
                    if (isCollection == true) {
                        RedNet.mHttpClient.newCall(new Request.Builder()
                                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey)
                                .url(AppNetConfig.UNCOLLECTTHEAD + tid + "&type=thread")
                                .post(new MultipartBuilder("----kDdwDwoddGegowwdSmoqdaAesgjk")
                                        .type(MultipartBuilder.FORM)
                                        .addFormDataPart("formhash", CacheUtils.getString(VoteThreadDetailsActivity.this, "formhash1"))
                                        .addFormDataPart("deletesubmit", "true")
                                        .build())
                                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                                .enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mProgressBar.setVisibility(View.GONE);
                                                RednetUtils.toast(VoteThreadDetailsActivity.this, "取消收藏请求失败");
                                            }
                                        });
                                    }

                                    @Override
                                    public void onResponse(final Response response) throws IOException {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mProgressBar.setVisibility(View.GONE);
                                            }
                                        });

                                        if (null != response) {
                                            CollecttheadBean collecttheadBean = new Gson().fromJson(response.body().string(), CollecttheadBean.class);
                                            if (collecttheadBean != null) {
                                                final CollecttheadBean.MessageBean message = collecttheadBean.getMessage();
                                                if (message != null) {
                                                    if ("do_success".equals(message.getMessageval())) {
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                iv_collect.setImageResource(R.drawable.collect);
                                                                isCollection = false;
//                                                        getDataFromNet();
                                                                RednetUtils.toast(VoteThreadDetailsActivity.this, message.getMessagestr());
                                                            }
                                                        });

                                                    } else {
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                RednetUtils.toast(VoteThreadDetailsActivity.this, message.getMessagestr());
                                                            }
                                                        });

                                                    }
                                                } else {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            RednetUtils.toast(VoteThreadDetailsActivity.this, "取消收藏失败");
                                                        }
                                                    });

                                                }
                                            } else {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        RednetUtils.toast(VoteThreadDetailsActivity.this, "取消收藏失败");
                                                    }
                                                });
                                            }
                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    RednetUtils.toast(VoteThreadDetailsActivity.this, "取消收藏失败");
                                                }
                                            });
                                        }

                                    }
                                });
                    } else {
                        RedNet.mHttpClient.newCall(new Request.Builder()
                                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey)
                                .url(AppNetConfig.COLLECTTHEAD + tid)
                                .post(new MultipartBuilder("----kDdwDwoddGegowwdSmoqdaAesgjk")
                                        .type(MultipartBuilder.FORM)
                                        .addFormDataPart("formhash", CacheUtils.getString(VoteThreadDetailsActivity.this, "formhash1"))
                                        .build())
                                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                                .enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mProgressBar.setVisibility(View.GONE);
                                                RednetUtils.toast(VoteThreadDetailsActivity.this, "收藏请求失败");
                                            }
                                        });
                                    }

                                    @Override
                                    public void onResponse(final Response response) throws IOException {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mProgressBar.setVisibility(View.GONE);

                                            }
                                        });

                                        if (response != null) {
                                            CollecttheadBean collecttheadBean = null;
                                            try {
                                                collecttheadBean = new Gson().fromJson(response.body().string(), CollecttheadBean.class);
                                                if (null != collecttheadBean) {
                                                    final CollecttheadBean.MessageBean message = collecttheadBean.getMessage();
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (message != null) {
                                                                if ("favorite_do_success".equals(message.getMessageval())) {
                                                                    iv_collect.setImageResource(R.drawable.collection_selete);
                                                                    isCollection = true;
                                                                    RednetUtils.toast(VoteThreadDetailsActivity.this, message.getMessagestr());
                                                                } else {
                                                                    RednetUtils.toast(VoteThreadDetailsActivity.this, message.getMessagestr());
                                                                }
                                                            } else {
                                                                RednetUtils.toast(VoteThreadDetailsActivity.this, "收藏失败");
                                                            }
                                                        }
                                                    });

                                                } else {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            RednetUtils.toast(VoteThreadDetailsActivity.this, "收藏失败");
                                                        }
                                                    });

                                                }
                                            } catch (IOException e) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(VoteThreadDetailsActivity.this, "请求异常", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                                e.printStackTrace();
                                            }
                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    RednetUtils.toast(VoteThreadDetailsActivity.this, "收藏失败");
                                                }
                                            });
                                        }

                                    }
                                });
                    }
                }
                break;
            case R.id.post:
                showShare();
                break;
            case R.id.back:
                if (from != null && (from.equals("tuisong") || from.equals(WelcomeActivity.FROM_WEB))) {
//                android.os.Process.killProcess(android.os.Process.myPid());
                    startActivity(new Intent(VoteThreadDetailsActivity.this, RednetMainActivity.class));
                    finish();
                } else {
                    finish();
                }
                break;
            case R.id.smiley_btn:
                if (inputType.equals("smiley")) {
                    inputType = "text";
                    mHandler.sendEmptyMessage(16);
                } else if (inputType.equals("text")) {
                    inputType = "smiley";
                    mHandler.sendEmptyMessage(15);
                } else if (inputType.equals("photo")) {
                    inputType = "text";
                    mHandler.sendEmptyMessage(16);
                }
                break;
            case R.id.action_btn:
                getPopupWindowInstance();
                popupWindow.showAsDropDown(v, Config.DeviceInfo.screenWidth, 40);
                break;
            case R.id.popupwindow_item02:
                mProgressBar.setVisibility(View.VISIBLE);
                popupWindow.dismiss();
                menuPoster.setSelected(onlyHost = !onlyHost);
                menuPoster.setText(menuPoster.isSelected() ? "查看全部" : "只看楼主");
                temp_data.clear();
                getThreadData(tid, page = 1, authorId, onlyHost);
                break;
            case R.id.submit:
                if (RednetUtils.hasLogin(VoteThreadDetailsActivity.this)) {
                    String message = mPostContent.getText().toString().trim();
                    if (TextUtils.isEmpty(tid)) {
                        RednetUtils.toast(VoteThreadDetailsActivity.this, "无效的帖子id,请刷新重试");
                    } else if (TextUtils.isEmpty(message)) {
                        RednetUtils.toast(VoteThreadDetailsActivity.this, "内容不能为空");
                    } else {
                        checkSecure();
                    }
                }
                break;
            case R.id.post_content:
                ll_reply.setVisibility(View.VISIBLE);
                ll_other.setVisibility(View.GONE);
                hideFaceView();
                hidePotoView();
                break;
            case R.id.iv_fabulous:
                if (RednetUtils.hasLogin(this)) {
                    if (isZan == false) {
                        praiseThread();
                    } else {
                        Toast.makeText(VoteThreadDetailsActivity.this, "您已经点过赞了", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            case R.id.iv_share:
                showShare();
                break;
            case R.id.iv_addpicture:
                if (isClick == true) {
                    if (RednetUtils.hasLogin(this)) {
                        if (mImageChooserDialog != null) mImageChooserDialog.show();
                    }
                } else {
                    Toast.makeText(VoteThreadDetailsActivity.this, "图片正在上传，请稍等", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.camera_btn:
                if (inputType.equals("photo")) {
                    inputType = "text";
                    mHandler.sendEmptyMessage(16);
                } else if (inputType.equals("text")) {
                    inputType = "photo";
                    mHandler.sendEmptyMessage(17);
                } else if (inputType.equals("smiley")) {
                    inputType = "text";
                    mHandler.sendEmptyMessage(17);
                }
                break;
        }

    }

    private void getDataFromNet() {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey)
                .url(AppNetConfig.MYFAVTHREAD)
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv_collect.setImageResource(R.drawable.collect);
                                isCollection = false;
                            }
                        });
                    }

                    @Override
                    public void onResponse(final Response response) throws IOException {
//                      Log.e("TAG", "我收藏的帖子 = " + response);
                        MyFavThreadBean myFavThreadBean = null;
                        try {
                            myFavThreadBean = new Gson().fromJson(response.body().string(), MyFavThreadBean.class);
                            if (myFavThreadBean != null) {

                                final List<MyFavThreadBean.VariablesBean.ListBean> list = myFavThreadBean.getVariables().getList();
//                                formhash = myFavThreadBean.getVariables().getFormhash();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (list != null && list.size() > 0) {
                                            for (int i = 0; i < list.size(); i++) {
                                                if (tid.equals(list.get(i).getId())) {
                                                    iv_collect.setImageResource(R.drawable.collection_selete);
                                                    isCollection = true;
                                                    favid = list.get(i).getFavid();
                                                    break;
                                                } else {
                                                    iv_collect.setImageResource(R.drawable.collect);
                                                    isCollection = false;
                                                }
                                            }
                                        } else {
                                            iv_collect.setImageResource(R.drawable.collect);
                                            isCollection = false;
                                        }
                                    }
                                });

                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        iv_collect.setImageResource(R.drawable.collect);
                                        isCollection = false;
                                    }
                                });

                            }
                        } catch (IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    iv_collect.setImageResource(R.drawable.collect);
                                    isCollection = false;
                                }
                            });

                            e.printStackTrace();
                        }


                    }
                });
    }

    private void sendMsg() {
        hideFaceView();
        hidePotoView();
        RednetUtils.hideSoftInput(mPostContent.getApplicationWindowToken());
        String message = mPostContent.getText().toString().trim();
        mPostContent.setText("");
        mProgressBar.setVisibility(View.VISIBLE);
        if (variables != null) {
            sechash = TextUtils.isEmpty(sechash) ? "" : sechash;
            seccodeverify = TextUtils.isEmpty(seccodeverify) ? "" : seccodeverify;
            FormEncodingBuilder builder = new FormEncodingBuilder()
                    .add("formhash", formhash)
                    .add("message", message)
                    .add("mobiletype", "2")
                    .add("allownoticeauthor", "1")
                    .add("reppid", variables.getPid())
                    .add("noticetrimstr", variables.getNoticetrimstr());
            for (int i = 0; i < aids.size(); i++) {
                builder.add("attachnew[" + aids.get(i) + "][description]", aids.get(i));

            }
            RedNet.mHttpClient.newCall(new Request.Builder()
                    .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";")
                    .url(AppNetConfig.COMMENT + "&tid=" + tid+"&sechash=" + sechash+"&seccodeverify=" + seccodeverify)
                    .post(builder.build()).build())
                    .enqueue(new com.squareup.okhttp.Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    RednetUtils.toast(VoteThreadDetailsActivity.this, "跟帖请求失败");
                                }
                            });

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            try {
                                if (response != null) {
                                    String s = response.request().body().toString();
                                    Log.e("TAG", "s=" + s);
                                    DetailsBean detailsBean = new Gson().fromJson(response.body().string(), DetailsBean.class);
                                    final DetailsBean.VariablesBean variables = detailsBean.getVariables();
                                    DetailsBean.MessageBean message1 = detailsBean.getMessage();
                                    String messageval = message1.getMessageval();
                                    final String messagestr = message1.getMessagestr();
                                    if ("post_reply_succeed".equals(messageval)) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mLinkReply = null;
                                                mReplyAnchorId = variables.getPid();
                                                mPostContent.setText("");
                                                for (int i = 0; i < imageViews.size(); i++) {
                                                    post_pic_container.removeViewAt(i);
                                                }
                                                RednetUtils.toast(VoteThreadDetailsActivity.this, "跟帖成功");
                                                mHandler.sendEmptyMessageDelayed(2, 0);

                                            }
                                        });

                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                RednetUtils.toast(VoteThreadDetailsActivity.this, messagestr);
                                                mPostContent.setText("");
                                            }
                                        });

                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            RednetUtils.toast(VoteThreadDetailsActivity.this, "跟帖请求失败");
                                        }
                                    });

                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mProgressBar != null) {
                                            mProgressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });

                            } catch (JsonSyntaxException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        RednetUtils.toast(VoteThreadDetailsActivity.this, "请求异常");
                                    }
                                });

                                e.printStackTrace();
                            }
                        }
                    });
//
        } else {
            sechash = TextUtils.isEmpty(sechash) ? "" : sechash;
            seccodeverify = TextUtils.isEmpty(seccodeverify) ? "" : seccodeverify;
            FormEncodingBuilder builder = new FormEncodingBuilder()
                    .add("formhash", formhash)
                    .add("message", message)
                    .add("mobiletype", "2")
                    .add("allownoticeauthor", "1");
            for (int i = 0; i < aids.size(); i++) {
                builder.add("attachnew[" + aids.get(i) + "][description]", aids.get(i));

            }
            RedNet.mHttpClient.newCall(new Request.Builder()
                    .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";")
                    .url(AppNetConfig.COMMENT + "&tid=" + tid+"&sechash=" + sechash+"&seccodeverify=" + seccodeverify)
                    .post(builder.build()).build())
                    .enqueue(new com.squareup.okhttp.Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    RednetUtils.toast(VoteThreadDetailsActivity.this, "跟帖请求失败");
                                }
                            });

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            try {
                                if (response != null) {
                                    String s = response.request().body().toString();
                                    DetailsBean detailsBean = new Gson().fromJson(response.body().string(), DetailsBean.class);
                                    final DetailsBean.VariablesBean variables = detailsBean.getVariables();
                                    DetailsBean.MessageBean message1 = detailsBean.getMessage();
                                    String messageval = message1.getMessageval();
                                    final String messagestr = message1.getMessagestr();
                                    if ("post_reply_succeed".equals(messageval)) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mLinkReply = null;
                                                mReplyAnchorId = variables.getPid();
                                                mPostContent.setText("");
                                                for (int i = 0; i < imageViews.size(); i++) {
                                                    post_pic_container.removeViewAt(i);
                                                }
                                                RednetUtils.toast(VoteThreadDetailsActivity.this, "跟帖成功");
                                                mHandler.sendEmptyMessageDelayed(2, 0);

                                            }
                                        });

                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                RednetUtils.toast(VoteThreadDetailsActivity.this, messagestr);
                                            }
                                        });

                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            RednetUtils.toast(VoteThreadDetailsActivity.this, "跟帖请求失败");
                                        }
                                    });

                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mProgressBar != null) {
                                            mProgressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });

                            } catch (JsonSyntaxException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        RednetUtils.toast(VoteThreadDetailsActivity.this, "请求异常");
                                    }
                                });

                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    private void checkSecure() {
//        mHandler.sendEmptyMessage(13);
        TThreadManager.checkSecure("post", new DataLoaderListener() {
            @Override
            public void onLoadCacheFinished(Object object) {
            }

            @Override
            public void onLoadDataFinished(Object object) {
                HashMap<String, Object> data = (HashMap<String, Object>) object;
                if (data != null) {
                    SecureVariables secure = (SecureVariables) data.get("secure");
                    cookiepre = (String) data.get("cookiepre");
                    seccode = secure.getSeccode();
                    sechash = secure.getSechash();
                    if (!TextUtils.isEmpty(seccode)) {   // 显示验证码！进行验证
                        startActivityForResult(new Intent(VoteThreadDetailsActivity.this, CheckSecureDialogActivity.class)
                                .putExtra("seccode", seccode)
                                .putExtra("sechash", sechash)
                                .putExtra("cookiepre", cookiepre)
                                .putExtra("from", CheckSecureDialogActivity.FROM_SEND), SECURE_REQUEST_CODE);
                    } else {
                        mHandler.sendEmptyMessage(13);
                    }
                } else {
                    mHandler.sendEmptyMessage(13);
                }
            }

            @Override
            public void onError(Object object) {
                mHandler.sendMessage(Message.obtain(mHandler, -10001, "请求失败"));
            }
        });
    }



    @Override
    public void onAction(final Object object, final int type) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onJSCallback(object, type);
            }
        });
    }

    void onJSCallback(final Object object, final int type) {
        if (type == Config.ACTIONTYPE_SHARE) {
            showShare();
        } else if (type == Config.ACTIONTYPE_PRAISE) {
            if (RednetUtils.hasLogin(this)) {
//                praiseThread();
            }
        } else if (type == Config.ACTIONTYPE_DISCUSSUSER) {
            if (RednetUtils.hasLogin(this)) {
                if (object instanceof String) {//回复评论用户
                    mHandler.sendMessage(Message.obtain(mHandler, 3, object));
                } else {//回复帖子
                    mLinkReply = null;
                    mReplyAnchorId = null;
                    mHandler.sendEmptyMessage(9);
                }
            }
        } else if (type == Config.ACTIONTYPE_SENDPOLL) {
            if (RednetUtils.hasLogin(this)) {
                sendPoll((String[]) object);
            }
        } else if (type == Config.ACTIONTYPE_CHECKVOTER) {
            startActivity(new Intent(this, VoterPollpotionActivity.class).putExtra("tid", tid));
        } else if (type == Config.ACTIONTYPE_LOADMORE) {
            getThreadData(tid, ++page, authorId, onlyHost);
        } else if (type == Config.ACTIONTYPE_USERINFO) {
            String userId = (String) object;
            if (!TextUtils.isEmpty(userId) && !userId.equals(RedNetApp.getInstance().getUid())) {
                startActivity(new Intent(this, UserDetailActivity.class).putExtra("userId", userId));
            }
        } else if (type == Config.ACTIONTYOE_REPORT) {
            if (RednetUtils.hasLogin(this)) {
                startActivityForResult(new Intent(this, SelectReportActivity.class).putExtra("rid", rid), REPORT_REQUEST_CODE);
            }
        } else if (type == Config.ACTIONTYOE_REPORT_COMMENT) {
            if (RednetUtils.hasLogin(this)) {
                startActivityForResult(new Intent(this, SelectReportActivity.class).putExtra("rid", (String) object), REPORT_REQUEST_CODE);
            }
        } else if (type == Config.ACTIONTYOE_THUMBCLICK) {
            startActivity(new Intent(this, ShowImageActivity.class).putExtra("url", (String) object));
        }
    }

    private void sendPoll(String[] data) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        StringBuilder sb = new StringBuilder();
        String[] items = new String[data.length];
        for (int i = 0; i < data.length; i++) {
//            sb.append(data[i]).append("|");
            builder.add("pollanswers[]", data[i]);
//            Log.e("TAG", "data[i]" + );
        }

//        String result = sb.toString().substring(0, sb.toString().length() - 1);

//        builder.add("type", "poll");
        builder.add("formhash", formhash);
        RequestBody formBody = builder.build();
        Request request = new Request.Builder()
                .addHeader("Cookie", CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_auth") + ";" + CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_saltkey") + ";")
                .url(AppNetConfig.BASEURL + "?module=pollvote&pollsubmit=yes&fid=" + fid + "&tid=" + tid + "&version=1&mobiletype=Android").post(formBody).build();
//                                           ?module=pollvote&version=1&fid=%@&tid=%@&pollsubmit=yes&mobiletype=IOS
        RedNet.mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
//                Log.e("TAG", "投票请求失败");
                mHandler.sendMessage(Message.obtain(mHandler, -10001, "投票请求失败"));
            }

            @Override
            public void onResponse(Response response) throws IOException {
//                Log.e("TAG", "投票请求成功=" + response.body().string());
                String result = (String) response.body().string();
                try {
                    JSONObject json = new JSONObject(result);
                    Log.e("TAG", "json=" + json.toString());
                    final JSONObject variablesJson = json.optJSONObject("Variables");
                    if (variablesJson != null) {
                        RedNetApp.setFormHash(variablesJson.optString("formhash"));
                    }
                    String tid = variablesJson.optString("tid");
                    String uid = variablesJson.optString("uid");
                    if (tid != null && uid != null) {  // 投票成功需要刷新页面转到结果页面
                        mHandler.sendEmptyMessage(2);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    RednetUtils.toast(VoteThreadDetailsActivity.this, variablesJson.getJSONObject("Message").getString("messagestr"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showShare() {
        if (mShareDialog == null) {
            mShareDialog = new ShareDialog(this) {
                @Override
                public ShareDialog.Builder onShare() {
                    return ShareDialog.Builder.create(VoteThreadDetailsActivity.this)
                            .setText(title, mShareText)
                            .setImageUrl(!TextUtils.isEmpty(thumburl) ? new StringBuilder(Config.HOST).append("/").append(thumburl).toString() : null)
                            .setUrl(Config.SHARE_URL + tid);
                }
            };
        }
        mShareDialog.show();
    }

    private void praiseThread() {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";")
                .url(AppNetConfig.PRAISETHREAD + "&tid=" + tid + "&hash=" + CacheUtils.getString(this, "formhash1"))
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new com.squareup.okhttp.Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        mHandler.sendMessage(Message.obtain(mHandler, -10001, "请求失败"));
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
//                        Log.e("TAG", "点赞=" + response.body().string());
                        if (null != response) {
                            try {
                                PraiseBean praiseBean = new Gson().fromJson(response.body().string(), PraiseBean.class);
                                PraiseBean.MessageBean message = praiseBean.getMessage();
                                if (null != message)
                                    mHandler.sendMessage(Message.obtain(mHandler, message.getMessageval().equals("recommend_succeed") ? 6 : 5, message.getMessagestr()));
                            } catch (JsonSyntaxException e) {
                                mHandler.sendMessage(Message.obtain(mHandler, -10001, "请求异常"));
                                e.printStackTrace();
                            }
                        } else {
                            mHandler.sendMessage(Message.obtain(mHandler, -10001, "点赞失败"));
                        }
                    }
                });
    }

    private void discussUserComment(final String pid) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey)
                .url(AppNetConfig.QUOTE)
                .post(new MultipartBuilder("----kDdwDwoddGegowwdSmoqdaAesgjk")
                        .type(MultipartBuilder.FORM)
                        .addFormDataPart("tid", tid)
                        .addFormDataPart("repquote", pid)
                        .build())
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mHandler.sendMessage(Message.obtain(mHandler, -10001, "请求失败"));
                            }
                        });
                    }

                    @Override
                    public void onResponse(final Response response) throws IOException {
                        try {
                            QuoteBean quoteBean = new Gson().fromJson(response.body().string(), QuoteBean.class);
                            if (quoteBean != null) {
                                variables = quoteBean.getVariables();
                                if (variables != null) {
                                    RedNetApp.setFormHash(variables.getFormhash());
                                }
                                mHandler.sendEmptyMessage(9);
                            } else {
                                mHandler.sendMessage(Message.obtain(mHandler, -10001, "回复失败"));
                            }
                        } catch (JsonSyntaxException e) {
                            mHandler.sendMessage(Message.obtain(mHandler, -10001, "请求异常"));
                            e.printStackTrace();
                        }


                    }
                });
    }

    private void getThreadData(final String tid, final int pageSize, String authorid, boolean onlyHost) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";")
                .url(AppNetConfig.BASEURL + "?module=viewthread&tid=" + tid + "&submodule=checkpost&version=5&page=" + pageSize + "&width=360&height=480&checkavatar=1&submodule=checkpost")
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        mHandler.sendEmptyMessage(-10001);
                        page = Math.max(1, --page);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        Log.e("TAG", "投票贴url=" + AppNetConfig.BASEURL + "?module=viewthread&tid=" + tid + "&submodule=checkpost&version=5&page=" + pageSize + "&width=360&height=480&checkavatar=1&submodule=checkpost");
                        try {
                            JSONObject json = new JSONObject(response.body().string());
                            final JSONObject resultMessage = json.optJSONObject("Message");
                            if (resultMessage != null) {
                                mHandler.sendMessage(Message.obtain(mHandler, 5, resultMessage.optString("messagestr")));
                                if ("thread_nonexistence".equalsIgnoreCase(resultMessage.optString("messageval"))) {
                                    finish();
                                    return;
                                }
                                page = Math.max(1, --page);
                            } else {
                                JSONObject vJson = json.optJSONObject("Variables");
                                if (vJson != null) {
                                    formhash = vJson.optString("formhash");
                                    RedNetApp.setFormHash(vJson.optString("formhash"));
                                    uploadhash = vJson.getJSONObject("allowperm").getString("uploadhash");
                                    JSONArray postList = vJson.optJSONArray("postlist");
                                    if (page == 1) {
                                        final JSONObject threadJson = vJson.optJSONObject("thread");
                                        JSONObject specialJson = (JSONObject) vJson.opt("special_poll");
                                        expirations = specialJson.getLong("expirations");
                                        closed = threadJson.optString("closed");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                String s = threadJson.optString("forumnames");
                                                mTitle.setText(s);
                                            }
                                        });
                                        allowvote = specialJson.optString("allowvote");
                                        allowvotepolled = specialJson.optString("allowvotepolled");
                                        allowvotethread = specialJson.optString("allowvotethread");
                                        if (postList != null && postList.length() > 0) {
                                            for (int i = 0; i < postList.length(); i++) {
                                                temp_data.add(postList.optJSONObject(i));
                                            }
                                            handleThreadContent(postList, pageSize);
                                            RednetUtils.fixPostList(postList);
                                        }
                                        replies = Integer.valueOf(threadJson.optString("replies"));
                                        limit = Integer.valueOf(vJson.optString("ppp"));
                                        title = threadJson.optString("subject");
                                        authorId = threadJson.optString("authorid");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                String recommend = threadJson.optString("recommend");
                                                if (recommend.equals("1")) {
                                                    iv_fabulous.setImageResource(R.drawable.zans);
                                                    isZan = true;
                                                } else {
                                                    isZan = false;
                                                    iv_fabulous.setImageResource(R.drawable.fabulous);
                                                }
                                                mShareText = threadJson.optString("author") + "发表于" + threadJson.optString("dateline");
                                            }
                                        });
                                    }

                                    if (postList != null && postList.length() > 0) {
                                        mLoadAll = postList.length() != 10;
                                        mPull2RefreshLayout.setPullUpEnable(!mLoadAll);
                                    }
                                }
                                mHandler.sendMessage(Message.obtain(mHandler, 1, json.toString()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            mHandler.sendEmptyMessage(-10001);
                            page = Math.max(1, --page);
                        }
                    }
                });
    }

    private void handleThreadContent(JSONArray postList, int pageSize) {
        JSONArray attachList = postList.optJSONObject(0).optJSONArray("attchlist");
        if (attachList != null && attachList.length() > 0) {
            thumburl = attachList.optJSONObject(0).optString("thumburl");
        }
        rid = pageSize == 1 ? postList.optJSONObject(0).optString("pid") : rid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RednetUtils.REQUEST_CODE_LOGIN) {
                mHandler.sendEmptyMessage(2);
                if (!RednetUtils.hasLogin(this)) {
                    return;
                }
                HashMap<String, Object> result = new HashMap<>();
                result.put("report_select", data.getStringExtra("report_select"));
                result.put("referer", Config.REPORT_URL + tid);
                result.put("rid", rid);
                result.put("tid", tid);
                result.put("fid", tid);
                result.put("rtype", "post");
                result.put("inajax", "1");
                result.put("formhash",CacheUtils.getString(VoteThreadDetailsActivity.this,"formhash1"));
                mProgressBar.setVisibility(View.VISIBLE);

                Api.getInstance().requestReport(result, new DataLoaderCallback(new InterDataLoaderListener() {
                    @Override
                    public void onLoadFinished(Object object) {
                        String result = (String) object;
                        if (mProgressBar != null) {
                            mProgressBar.setVisibility(View.GONE);
                        }
                        try {
                            JSONObject json = new JSONObject(result);
                            String messagestr = json.optString("messagestr");
                            if (messagestr.equals("success")) {
                                mHandler.sendMessage(mHandler.obtainMessage(5, "举报成功"));
                            } else {
                                mHandler.sendMessage(mHandler.obtainMessage(5, messagestr));
                            }
                        } catch (JSONException e) {
                            mHandler.sendMessage(mHandler.obtainMessage(5, "数据解析错误"));
                        }
                    }

                    @Override
                    public void onError(Object object) {
                        mHandler.sendMessage(mHandler.obtainMessage(5, "请求错误"));
                    }
                }));

            } else if (requestCode == SECURE_REQUEST_CODE) {

                seccodeverify = data.getStringExtra("seccodeverify");
                vcodeCookie = RedNetPreferences.getVcodeCookie();
                mHandler.sendEmptyMessage(13);
            } else if (requestCode == ImageChooserDialog.REQUEST_CODE_PICK) {
                Log.e("TAG", "data.getData()=" + data.getData());
                if (data != null && data.getData() != null) {
                    String realFilePath = BitmapUtils.getRealFilePath(this, data.getData());
                    addPath(realFilePath);

//                    Cursor cursor = this.getContentResolver()
//                            .query(data.getData(), new String[]{MediaStore.MediaColumns.DATA}, null, null, null);
//                    if (cursor != null) {
//                        try {
//                            cursor.moveToFirst();
//                            String path = String.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)));
//                            Log.e("TAG", "相册path=" + path);
//                            addPath(path);
////                            showPhoto(path);
//
////                            if (chain.doChain(path)) {
////                                appendPhoto(activity, path);
////                            }
//                        } finally {
//                            cursor.close();
//                        }
//                    }
                }
            } else if (requestCode == ImageChooserDialog.REQUEST_CODE_CAMERA) {
                String path = String.valueOf(mImageChooserDialog.getCurrentPhotoPath());
                Log.e("TAG", "照片path=" + path);
                addPath(path);
//                showPhoto(path);

//                ImageLoader.getInstance().displayImage(files.get(i).getThumbnailUri(), new ImageViewAware(imageView), options,
//                        null, null, files.get(i).getOrientation());
//                if (chain.doChain(path)) {
//                    appendPhoto(activity, path);
//                }
            }
            dissmissChooser();

        }
    }

    private void dissmissChooser() {
        if (mImageChooserDialog != null) mImageChooserDialog.dismiss();
    }

    private void addPath(String path) {
        if (null != path && !TextUtils.isEmpty(path)) {
            if(!imgPaths.contains(path)) {
                mProgressBar.setVisibility(View.VISIBLE);
                imgPaths.add(path);
                mHandler.sendMessage(mHandler.obtainMessage(18, path));
                isClick = false;
            }else {
                Toast.makeText(this, "请勿重复添加图片", Toast.LENGTH_SHORT).show();
                isClick = true;
            }

        } else {
            Toast.makeText(this, "图片无法使用", Toast.LENGTH_SHORT).show();
            isClick = true;
        }
    }

    private void getPopupWindowInstance() {
        if (popupWindow != null) {
            if (menuFav != null) {
                menuFav.setText(menuFav.isSelected() ? "取消" : "收藏");
                menuFav.setEnabled(true);
            }
        } else {
            initPopupWindow();
        }
    }

    private void initPopupWindow() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.popup_window_action, null);
        View item1 = view.findViewById(R.id.popupwindow_item01);
        View item2 = view.findViewById(R.id.popupwindow_item02);
        menuFav = (TextView) item1.findViewById(R.id.fav);
        menuPoster = (TextView) item2.findViewById(R.id.poster);
        item1.setOnClickListener(this);
        item2.setOnClickListener(this);
        popupWindow = new PopupWindow(view, RednetUtils.dipToPx(96), RednetUtils.dipToPx(72));
        if (RedNetApp.getInstance().isLogin()) {
            mUserThread = UserThread.has(this, RedNetApp.getInstance().getUid(), tid);
            menuFav.setSelected(mUserThread != null);
        } else {
            menuFav.setSelected(false);
        }
        menuFav.setText(menuFav.isSelected() ? "取消" : "收藏");
        menuPoster.setSelected(onlyHost);
        menuPoster.setText(menuPoster.isSelected() ? "查看全部" : "只看楼主");
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());  // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        if (Build.VERSION.SDK_INT >= 21) {
            popupWindow.setElevation(RednetUtils.dp(this, 8));
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

    public boolean hideFaceView() {  // 隐藏表情选择框
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
            mPostContent.append(emoji.getCode());
        }
    }

    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                RednetUtils.hideSoftInput(v.getApplicationWindowToken());
                mPostSubmit.performClick();
                return true;
            }
            return false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (from != null && (from.equals("tuisong") || from.equals(WelcomeActivity.FROM_WEB))) {
//                android.os.Process.killProcess(android.os.Process.myPid());
                startActivity(new Intent(VoteThreadDetailsActivity.this, RednetMainActivity.class));
                finish();
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public static final int LOAD_TYPE_REFRESH = 0;
    public static final int LOAD_TYPE_MORE = 1;
    private int mLoadType = LOAD_TYPE_REFRESH;

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        if (!TextUtils.isEmpty(tid) && RednetUtils.networkIsOk(this)) {
            mLoadType = LOAD_TYPE_REFRESH;
            getThreadData(tid, page = 1, authorId, onlyHost);
        } else {
            pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
        }
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        if (!TextUtils.isEmpty(tid) && RednetUtils.networkIsOk(this)) {
            mLoadType = LOAD_TYPE_MORE;
            getThreadData(tid, ++page, authorId, onlyHost);
        } else {
            pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
            if (TextUtils.isEmpty(mWebView.getUrl()))
                mWebView.loadUrl("file:///android_asset/templates/thread_temp_poll.html");
        }
    }

}
