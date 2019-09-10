package cn.tencent.DiscuzMob.ui.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.CollecttheadBean;
import cn.tencent.DiscuzMob.model.MyFavThreadBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.Event.RefreshInteractionPicturePath;
import cn.tencent.DiscuzMob.ui.Event.RefreshPicturePath;
import cn.tencent.DiscuzMob.ui.adapter.Find_tab_Adapter;
import cn.tencent.DiscuzMob.ui.dialog.ImageChooserDialog;
import cn.tencent.DiscuzMob.ui.fragment.live.InteractionViewPager;
import cn.tencent.DiscuzMob.ui.fragment.live.LivePager;
import cn.tencent.DiscuzMob.utils.BitmapUtils;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;

import static android.R.attr.path;

/*
* 直播详情帖
* */
public class LiveDetialActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rl_collection;//收藏
    private TabLayout tab_FindFragment_title;
    private ViewPager viewpager;
    private List<String> list_title;//tab名称列表
    private LivePager livePager;
    private InteractionViewPager interactionViewPager;
    private List<Fragment> listFragment; //fragment的集合
    private Find_tab_Adapter fAdapter;

    private TextView tv_subject;
    private AsyncRoundedImageView avatar_author;
    private TextView tv_authorName;
    private String fid;
    private String live = "[live]";
    String authorid;
    private ImageView iv_collection;
    private TextView tv_collect;
    private boolean isCollection;
    private String favid;
    private String cookiepre_auth;
    private String cookiepre_saltkey;
    private String tid;
    private TextView tv_leve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_detial);
        cookiepre_auth = CacheUtils.getString(this, "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(this, "cookiepre_saltkey");
        findViewById(R.id.back).setOnClickListener(this);
        rl_collection = (RelativeLayout) findViewById(R.id.rl_collection);
        iv_collection = (ImageView) findViewById(R.id.iv_collection);
        tv_collect = (TextView) findViewById(R.id.tv_collect);
        rl_collection.setOnClickListener(this);
        tab_FindFragment_title = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tv_subject = (TextView) findViewById(R.id.tv_subject);
        tv_authorName = (TextView) findViewById(R.id.tv_authorName);
        avatar_author = (AsyncRoundedImageView) findViewById(R.id.avatar_author);
        tv_leve = (TextView) findViewById(R.id.tv_leve);
        Intent intent = getIntent();
        String subject = intent.getStringExtra("subject");
        fid = intent.getStringExtra("fid");
        authorid = intent.getStringExtra("authorid");
        String author = intent.getStringExtra("author");
        String avatar = intent.getStringExtra("avatar");
        String level = intent.getStringExtra("level");
        tv_leve.setText(RednetUtils.delHTMLTag(level));
        tid = intent.getStringExtra("tid");
        formhash = intent.getStringExtra("formhash");
        avatar_author.load(avatar, R.drawable.ic_header_def);
        tv_authorName.setText(author);
        final SpannableStringBuilder builder = new SpannableStringBuilder(subject);
        builder.append(live);
        String dc = builder.toString();
        Drawable drawable = this.getResources().getDrawable(R.drawable.live);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight() + 5);
        ImageSpan ds = new ImageSpan(drawable);
        builder.setSpan(ds, dc.indexOf(live), dc.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_subject.setText(builder);
        list_title = new ArrayList<>();
        list_title.add("直  播");
        list_title.add("互  动");
        livePager = new LivePager(fid, authorid, tid);
        interactionViewPager = new InteractionViewPager(fid, authorid, tid);
        listFragment = new ArrayList<>();
        listFragment.add(livePager);
        listFragment.add(interactionViewPager);
        tab_FindFragment_title.setTabMode(TabLayout.LAYOUT_MODE_CLIP_BOUNDS | TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(0)));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(1)));
        //viewpager加载adapter
        fAdapter = new Find_tab_Adapter(LiveDetialActivity.this, getSupportFragmentManager(), listFragment, list_title);
        //viewpager加载adapter
        viewpager.setAdapter(fAdapter);
        //TabLayout加载viewpager
        tab_FindFragment_title.setupWithViewPager(viewpager);
        //设置默认的界面
        viewpager.setCurrentItem(0);
        //        title的点击事件
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());

        if (CacheUtils.getBoolean(this, "login") == true) {
            getCollectionData();
        } else {
            iv_collection.setImageResource(R.drawable.collection);
            tv_collect.setText("收藏");
            isCollection = false;
        }

    }

    String formhash;

    private void getCollectionData() {
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
                                iv_collection.setImageResource(R.drawable.collection);
                                tv_collect.setText("收藏");
                                isCollection = false;
                                tv_collect.setVisibility(View.VISIBLE);
                                iv_collection.setVisibility(View.VISIBLE);
                                tv_collect.setTextColor(getResources().getColor(R.color.blue));
                                rl_collection.setBackgroundResource(R.drawable.bg_collections);
                            }
                        });
                    }

                    @Override
                    public void onResponse(final Response response) throws IOException {
//                      Log.e("TAG", "我收藏的帖子 = " + response);
                        MyFavThreadBean myFavThreadBean = new Gson().fromJson(response.body().string(), MyFavThreadBean.class);
                        if (myFavThreadBean != null) {
                            formhash = myFavThreadBean.getVariables().getFormhash();
                            final List<MyFavThreadBean.VariablesBean.ListBean> list = myFavThreadBean.getVariables().getList();
                            if (list != null && list.size() > 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    if (tid.equals(list.get(i).getId())) {
                                        final int finalI = i;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
//                                                iv_collection.setImageResource(R.drawable.collection_selete);
                                                iv_collection.setVisibility(View.GONE);
                                                tv_collect.setText("已收藏");
                                                isCollection = true;
                                                favid = list.get(finalI).getFavid();
                                                tv_collect.setTextColor(Color.GRAY);
                                                rl_collection.setBackgroundResource(R.drawable.bg_collection);
                                            }
                                        });

                                        break;

                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                iv_collection.setImageResource(R.drawable.collection);
                                                tv_collect.setText("收藏");
                                                isCollection = false;
                                                tv_collect.setVisibility(View.VISIBLE);
                                                iv_collection.setVisibility(View.VISIBLE);
                                                tv_collect.setTextColor(getResources().getColor(R.color.blue));
                                                rl_collection.setBackgroundResource(R.drawable.bg_collections);
                                            }
                                        });

                                    }
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        iv_collection.setImageResource(R.drawable.collection);
                                        tv_collect.setText("收藏");
                                        isCollection = false;
                                        tv_collect.setVisibility(View.VISIBLE);
                                        iv_collection.setVisibility(View.VISIBLE);
                                        tv_collect.setTextColor(getResources().getColor(R.color.blue));
                                        rl_collection.setBackgroundResource(R.drawable.bg_collections);
                                    }
                                });

                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    iv_collection.setImageResource(R.drawable.collection);
                                    tv_collect.setText("收藏");
                                    isCollection = false;
                                    tv_collect.setVisibility(View.VISIBLE);
                                    iv_collection.setVisibility(View.VISIBLE);
                                    tv_collect.setTextColor(getResources().getColor(R.color.blue));
                                    rl_collection.setBackgroundResource(R.drawable.bg_collections);
                                }
                            });

                        }


                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_collection:
                if (RednetUtils.hasLogin(this)) {
                    if (isCollection == false) {
                        CollectForum();
                    } else {
                        CancelCollection();
                    }
                }
                break;
        }
    }

    private void CancelCollection() {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey)
                .url(AppNetConfig.UNCOLLECTTHEAD + tid + "&type=thread")
                .post(new MultipartBuilder("----kDdwDwoddGegowwdSmoqdaAesgjk")
                        .type(MultipartBuilder.FORM)
                        .addFormDataPart("formhash", CacheUtils.getString(LiveDetialActivity.this, "formhash1"))
                        .addFormDataPart("deletesubmit", "true")
                        .build())
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RednetUtils.toast(LiveDetialActivity.this, "取消收藏请求失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(final Response response) throws IOException {

                        if (response != null) {
                            JSONObject jsonObject = JSON.parseObject(response.body().string());
                            if (jsonObject != null) {
                                final JSONObject message = jsonObject.getJSONObject("Message");
                                if (message != null) {
                                    if (message.getString("messageval").contains("success")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                iv_collection.setVisibility(View.VISIBLE);
                                                iv_collection.setImageResource(R.drawable.collection);
                                                tv_collect.setText("收藏");
                                                isCollection = false;
                                                tv_collect.setVisibility(View.VISIBLE);
                                                tv_collect.setTextColor(getResources().getColor(R.color.blue));
                                                rl_collection.setBackgroundResource(R.drawable.bg_collections);
                                                RednetUtils.toast(LiveDetialActivity.this, message.getString("messagestr"));
                                            }
                                        });

                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                RednetUtils.toast(LiveDetialActivity.this, message.getString("messagestr"));
                                            }
                                        });

                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            RednetUtils.toast(LiveDetialActivity.this, "取消收藏失败");
                                        }
                                    });

                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        RednetUtils.toast(LiveDetialActivity.this, "取消收藏失败");
                                    }
                                });

                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    RednetUtils.toast(LiveDetialActivity.this, "取消收藏失败");
                                }
                            });

                        }

                    }
                });
    }

    private void CollectForum() {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey)
                .url(AppNetConfig.COLLECTTHEAD + tid)
                .post(new MultipartBuilder("----kDdwDwoddGegowwdSmoqdaAesgjk")
                        .type(MultipartBuilder.FORM)
                        .addFormDataPart("formhash", formhash)
                        .build())
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RednetUtils.toast(LiveDetialActivity.this, "收藏请求失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(final Response response) throws IOException {
//                        Log.e("TAG", "直播收藏=" + response.body().string());
                        if (response != null) {
                            CollecttheadBean collecttheadBean = null;
                            try {
                                collecttheadBean = new Gson().fromJson(response.body().string(), CollecttheadBean.class);
                                if (collecttheadBean != null) {
                                    final CollecttheadBean.MessageBean message = collecttheadBean.getMessage();
                                    if (message != null) {
                                        if (message.getMessageval().contains("success")) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
//                                                    iv_collection.setImageResource(R.drawable.collection_selete);
                                                    iv_collection.setVisibility(View.GONE);
                                                    tv_collect.setText("已收藏");
                                                    isCollection = true;
                                                    tv_collect.setTextColor(Color.GRAY);
                                                    rl_collection.setBackgroundResource(R.drawable.bg_collection);
                                                    RednetUtils.toast(LiveDetialActivity.this, message.getMessagestr());
                                                }
                                            });

                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    RednetUtils.toast(LiveDetialActivity.this, message.getMessagestr());
                                                }
                                            });

                                        }
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                RednetUtils.toast(LiveDetialActivity.this, "收藏失败");
                                            }
                                        });

                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            RednetUtils.toast(LiveDetialActivity.this, "收藏失败");
                                        }
                                    });

                                }
                            } catch (JsonSyntaxException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        RednetUtils.toast(LiveDetialActivity.this, "请求异常");
                                    }
                                });
                                e.printStackTrace();
                            } catch (IOException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        RednetUtils.toast(LiveDetialActivity.this, "请求异常");
                                    }
                                });
                                e.printStackTrace();
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    RednetUtils.toast(LiveDetialActivity.this, "收藏失败");
                                }
                            });

                        }
                    }
                });
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int currentItems = viewpager.getCurrentItem();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (currentItems) {
                case 0:
                    if (livePager != null) {
                        ft.hide(livePager);
                    }
                    break;
                case 1:
                    if (interactionViewPager != null) {
                        ft.hide(interactionViewPager);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "requestCode=" + requestCode + ";resultCode=" + resultCode);
        String from = CacheUtils.getString(RedNetApp.getInstance(), "from");
        Log.e("TAG", "from=" + from);
        if (resultCode == RESULT_OK) {
            if (requestCode == ImageChooserDialog.REQUEST_CODE_PICK) {
                if (data != null && data.getData() != null) {
                    String realFilePath = BitmapUtils.getRealFilePath(this, data.getData());
                    if (from.equals("live")) {
                        EventBus.getDefault().post(new RefreshPicturePath(realFilePath));
                    } else {
                        EventBus.getDefault().post(new RefreshInteractionPicturePath(realFilePath));
                    }
//                    Cursor cursor = this.getContentResolver()
//                            .query(data.getData(), new String[]{MediaStore.MediaColumns.DATA}, null, null, null);
//                    if (cursor != null) {
//                        try {
//                            cursor.moveToFirst();
//                            String path = String.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)));
//                            Log.e("TAG", "相册path=" + path);
//                            if (from.equals("live")) {
//                                EventBus.getDefault().post(new RefreshPicturePath(path));
//                            } else {
//                                EventBus.getDefault().post(new RefreshInteractionPicturePath(path));
//                            }
//
////                            addPath(path);
//                        } finally {
//                            cursor.close();
//                        }
//                    }
                }
            } else if (requestCode == ImageChooserDialog.REQUEST_CODE_CAMERA) {

                if (from.equals("live")) {
                    String path = String.valueOf(livePager.mImageChooserDialog.getCurrentPhotoPath());

                    EventBus.getDefault().post(new RefreshPicturePath(path));
                } else {
                    String path = String.valueOf(interactionViewPager.mImageChooserDialog.getCurrentPhotoPath());

                    EventBus.getDefault().post(new RefreshInteractionPicturePath(path));
                }

                Log.e("TAG", "照片path=" + path);
//                addPath(path);
            }
//            dissmissChooser();
        }


    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        tab_FindFragment_title.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tab_FindFragment_title, 60, 60);
            }
        });
    }
}
