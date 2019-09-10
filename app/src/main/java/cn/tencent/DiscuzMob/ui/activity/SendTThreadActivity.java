package cn.tencent.DiscuzMob.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.manager.DataLoaderListener;
import cn.tencent.DiscuzMob.manager.TThreadManager;
import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.model.CheckPost;
import cn.tencent.DiscuzMob.model.SecureVariables;
import cn.tencent.DiscuzMob.model.SendThreadVariables;
import cn.tencent.DiscuzMob.net.ApiVersion5;
import cn.tencent.DiscuzMob.net.UploadFile;
import cn.tencent.DiscuzMob.ui.adapter.FaceAdapter;
import cn.tencent.DiscuzMob.ui.adapter.PhotoGalleryAdapter;
import cn.tencent.DiscuzMob.ui.adapter.ViewPagerAdapter;
import cn.tencent.DiscuzMob.ui.dialog.CheckSecureDialogActivity;
import cn.tencent.DiscuzMob.ui.dialog.SelectPhotoDialogActivity;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.widget.HorizontalListView;
import cn.tencent.DiscuzMob.model.Smiley;
import cn.tencent.DiscuzMob.R;

/**
 * Created by kurt on 15-6-8.
 */
@Deprecated
public class SendTThreadActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final int PHOTO_REQUEST_CODE = 1;
    private static final int SECURE_REQUEST_CODE = 2;

    private EditText mTitleEdit, mContentEdit;
    private ImageView mFaceView, mPhotoView, mBackBtn;
    private RelativeLayout mFaceViewLayout;
    private Button mSendBtn;
    private ProgressBar mProgress;
    private String fid;
    private String typeId = "0";
    private String mTid;
    private boolean isLoaded = false;

    private ViewPager vp_face;//显示表情页的viewpager
    private LinearLayout layout_point;//游标显示布局
    private ArrayList<ArrayList<Smiley>> emojis;
    private ArrayList<View> pageViews;// 表情页界面集合
    private List<FaceAdapter> faceAdapters;//表情数据填充器
    private ArrayList<ImageView> pointViews;
    private int current = 0;//当前表情页
    private int picindex;
    private HorizontalListView mGallery;
    private PhotoGalleryAdapter mGalleryAdapter;

    private String uploadhash;
    private ArrayList<String> picPathList = new ArrayList<>();
    private ArrayList<String> attachIdList = new ArrayList<>();

    private String seccodeverify;
    private String vcodeCookie;
    private String seccode;
    private String sechash;
    private String cookiepre;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 3) {
                RednetUtils.toast(SendTThreadActivity.this, "发帖成功");
                mProgress.setVisibility(View.GONE);
                mHandler.sendEmptyMessageDelayed(9, 1000);
            } else if (msg.what == 4) {
                RednetUtils.toast(SendTThreadActivity.this, (String) msg.obj);
                mProgress.setVisibility(View.GONE);
            } else if (msg.what == 5) {
                RednetUtils.toast(SendTThreadActivity.this, "您没有发帖权限");
                mProgress.setVisibility(View.GONE);
            } else if (msg.what == 6) {
                mProgress.setVisibility(View.GONE);
                Init_viewPager();
                Init_Point();
                Init_Data();
                mFaceViewLayout.setVisibility(View.VISIBLE);
                isLoaded = true;
            } else if (msg.what == 7) {
                mFaceViewLayout.setVisibility(View.VISIBLE);
            } else if (msg.what == 9) {
                mProgress.setVisibility(View.GONE);
                RednetUtils.toast(SendTThreadActivity.this, "发帖成功");
                startActivity(new Intent(SendTThreadActivity.this, ThreadDetailsActivity.class).putExtra("id", mTid));
                finish();
            } else {
                if (msg.what == -1 && msg.obj instanceof String) {
                    RednetUtils.toast(SendTThreadActivity.this, (String) msg.obj);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tthread);
        fid = getIntent().getStringExtra("fid");
        mBackBtn = (ImageView) findViewById(R.id.back_btn);
        mTitleEdit = (EditText) findViewById(R.id.tthread_title_edit);
        mContentEdit = (EditText) findViewById(R.id.tthead_content_edit);
        mFaceView = (ImageView) findViewById(R.id.face_view);
        mPhotoView = (ImageView) findViewById(R.id.photo_view);
        mSendBtn = (Button) findViewById(R.id.thread_send_btn);
        mProgress = (ProgressBar) findViewById(R.id.progressbar);
        mGallery = (HorizontalListView) findViewById(R.id.img_gallery);
        mGalleryAdapter = new PhotoGalleryAdapter(this);
        mGallery.setAdapter(mGalleryAdapter);
        mGallery.setOnItemClickListener(new MyGalleryOnItemClickListener());
        mFaceViewLayout = (RelativeLayout) findViewById(R.id.ll_facechoose);
        vp_face = (ViewPager) findViewById(R.id.vp_contains);
        layout_point = (LinearLayout) findViewById(R.id.iv_image);

        mBackBtn.setOnClickListener(this);
        mFaceView.setOnClickListener(this);
        mPhotoView.setOnClickListener(this);
        mSendBtn.setOnClickListener(this);
    }

    /**
     * 初始化显示表情的viewpager
     */
    private void Init_viewPager() {
        pageViews = new ArrayList<>();
        View nullView1 = new View(getApplicationContext());
        nullView1.setBackgroundColor(Color.TRANSPARENT);
        pageViews.add(nullView1);
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
        View nullView2 = new View(getApplicationContext());
        nullView2.setBackgroundColor(Color.TRANSPARENT);
        pageViews.add(nullView1);
    }

    /**
     * 初始化游标
     */
    private void Init_Point() {
        pointViews = new ArrayList<>();
        ImageView imageView;
        Log.d("kurt", "pageViews.size() = " + pageViews.size());
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

    /**
     * 填充数据
     */
    private void Init_Data() {
        vp_face.setAdapter(new ViewPagerAdapter(pageViews));
        vp_face.setCurrentItem(1);
        current = 0;
        vp_face.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                current = arg0 - 1;
                // 描绘分页点
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

    /**
     * 绘制游标背景
     */
    public void draw_Point(int index) {
        for (int i = 1; i < pointViews.size(); i++) {
            if (index == i) {
                pointViews.get(i).setBackgroundResource(R.drawable.d2);
            } else {
                pointViews.get(i).setBackgroundResource(R.drawable.d1);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.face_view:
                if (mFaceViewLayout.getVisibility() == View.VISIBLE) {
                    mFaceViewLayout.setVisibility(View.GONE);
                } else {
                    if (!isLoaded) {
                        mProgress.setVisibility(View.VISIBLE);
                        emojis = RedNet.smileManager.initSmileData();
                        mHandler.sendEmptyMessage(6);
                    } else {
                        mHandler.sendEmptyMessage(7);
                    }
                }
                break;
            case R.id.photo_view:
                startActivityForResult(new Intent(this, SelectPhotoDialogActivity.class), PHOTO_REQUEST_CODE);
                break;
            case R.id.thread_send_btn:
                if (TextUtils.isEmpty(mTitleEdit.getText().toString().trim())) {
                    mHandler.sendMessage(Message.obtain(mHandler, -1, "请填写标题"));
                } else if (TextUtils.isEmpty(mContentEdit.getText().toString().trim())) {
                    mHandler.sendMessage(Message.obtain(mHandler, -1, "请填写帖子内容"));
                } else {
                    mProgress.setVisibility(View.VISIBLE);
                    mHandler.sendMessage(Message.obtain(mHandler, -1, "正在检查您的发帖权限"));
                    TThreadManager.checkPost(fid, new DataLoaderListener() {
                        @Override
                        public void onLoadCacheFinished(Object object) {
                        }

                        @Override
                        public void onLoadDataFinished(Object object) {
                            HashMap<String, Object> data = (HashMap<String, Object>) object;
                            CheckPost check = (CheckPost) data.get("checkpost");
                            cookiepre = (String) data.get("cookiepre");
                            uploadhash = check.getUploadhash();
                            if (check.getAllowpost().equals("1")) {
                                checkSecure();
                            } else {
                                mHandler.sendEmptyMessage(5);
                            }
                        }

                        @Override
                        public void onError(Object object) {
                            mHandler.sendMessage(Message.obtain(mHandler, 4, "请求失败"));
                        }
                    });
                }

                break;
        }
    }

    private void checkSecure() {
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
                    if (seccode != null && !seccode.equals("")) {   // 显示验证码！进行验证
                        Intent intent = new Intent();
                        intent.setClass(SendTThreadActivity.this, CheckSecureDialogActivity.class);
                        intent.putExtra("seccode", seccode);
                        intent.putExtra("sechash", sechash);
                        intent.putExtra("cookiepre", cookiepre);
                        intent.putExtra("from", CheckSecureDialogActivity.FROM_SEND);
                        startActivityForResult(intent, SECURE_REQUEST_CODE);
                        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                    } else {
                        if (mGalleryAdapter.getSelectedPhotos().size() > 0) {
                            picPathList = mGalleryAdapter.getSelectedPhotos();
                            uploadFile(picPathList);
                        } else {
                            submitThread();
                        }
                    }
                } else {
                    if (mGalleryAdapter.getSelectedPhotos().size() > 0) {
                        picPathList = mGalleryAdapter.getSelectedPhotos();
                        uploadFile(picPathList);
                    } else {
                        submitThread();
                    }
                }
            }

            @Override
            public void onError(Object object) {
                mHandler.sendMessage(Message.obtain(mHandler, 4, "验证失败"));
            }
        });
    }

    private void uploadFile(ArrayList<String> paths) {
        String picPath = paths.get(picindex);
        new Thread(new UploadFile(picPath, uploadhash, fid, uploadResult)).start();
    }

    private void submitThread() {
        mHandler.sendMessage(Message.obtain(mHandler, -1, "验证通过,发帖中..."));
        ArrayList<String> attachParams = new ArrayList<>();
        if (attachIdList.size() > 0) {
            for (int i = 0; i < attachIdList.size(); i++) {
                attachParams.add("attachnew[" + attachIdList.get(i) + "][description]");
            }
        }
        sechash = TextUtils.isEmpty(sechash) ? "" : sechash;
        seccodeverify = TextUtils.isEmpty(sechash) ? "" : sechash;
        String subject = mTitleEdit.getText().toString().trim();
        String message = mContentEdit.getText().toString().trim();
        ApiVersion5.INSTANCE.requestSendPost(fid, subject, message, typeId, sechash, seccodeverify, attachParams
                , new ApiVersion5.Result<Object>(SendTThreadActivity.this, SendThreadVariables.class, false, null, true) {
                    @Override
                    public void onResult(int code, Object value) {
                        if (value instanceof BaseModel) {
                            SendThreadVariables variables = ((BaseModel<SendThreadVariables>) value).getVariables();
                            mTid = variables.getTid();
                            if (!TextUtils.isEmpty(mTid)) {
                                mHandler.sendEmptyMessage(3);
                            } else {
                                mHandler.sendMessage(Message.obtain(mHandler, 4, ""));
                            }
                        } else {
                            mHandler.sendMessage(Message.obtain(mHandler, 4, "发帖失败"));
                        }
                    }
                });
    }

    private UploadFile.FileResult uploadResult = new UploadFile.FileResult() {
        @Override
        public void onResult(Object fileResult) {
            String result = (String) fileResult;
            String[] resultStr = result.split("\\|");//            DISCUZUPLOAD|0|65|-1|0
            String resultValue = resultStr[1];
            if (resultValue.endsWith("0")) {
                String aid = resultStr[2];
                attachIdList.add(aid);
                picindex++;
                if (picindex < picPathList.size()) {
                    uploadFile(picPathList);
                } else {
                    submitThread();
                }
            }
        }

        @Override
        public void onError(Exception ex, String path) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null)
            if (requestCode == PHOTO_REQUEST_CODE) {
                if (data.getData() != null) {
                    Cursor cursor = getContentResolver()
                            .query(data.getData(), new String[]{MediaStore.MediaColumns.DATA}, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        String path = String.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)));
                        if (TextUtils.isEmpty(RednetUtils.getMimeTypeOfImageFile(path))) {
                            RednetUtils.toast(this, "错误的图片类型");
                        } else {
                            mGalleryAdapter.selected(path);
                            mGallery.setVisibility(View.VISIBLE);
                        }
                        cursor.close();
                    }
                } else if (data.hasExtra("path")) {
                    mGalleryAdapter.selected(data.getStringExtra("path"));
                    mGallery.setVisibility(View.VISIBLE);
                }
            } else if (requestCode == SECURE_REQUEST_CODE) {
                seccodeverify = data.getStringExtra("seccodeverify");
                vcodeCookie = RedNetPreferences.getVcodeCookie();
                if (mGalleryAdapter.getSelectedPhotos().size() > 0) {
                    picPathList = mGalleryAdapter.getSelectedPhotos();
                    uploadFile(picPathList);
                } else {
                    submitThread();
                }
            }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Smiley emoji = (Smiley) faceAdapters.get(current).getItem(position);
        if (!TextUtils.isEmpty(emoji.getCode())) {
            mContentEdit.append(emoji.getCode());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && hideFaceView()) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 隐藏表情选择框
    public boolean hideFaceView() {
        if (mFaceViewLayout.getVisibility() == View.VISIBLE) {
            mFaceViewLayout.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    class MyGalleryOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
        }
    }

}
