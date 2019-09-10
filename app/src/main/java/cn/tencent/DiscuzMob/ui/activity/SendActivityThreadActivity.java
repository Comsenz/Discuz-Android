package cn.tencent.DiscuzMob.ui.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.tencent.DiscuzMob.ui.dialog.ImageChooserDialog;
import cn.tencent.DiscuzMob.ui.dialog.SelectActivityCateActivity;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.widget.wheelview.WheelMain;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.model.SendActivityBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.net.UploadFile;
import cn.tencent.DiscuzMob.ui.dialog.SelectActivitySexActivity;
import cn.tencent.DiscuzMob.utils.BitmapUtil;
import cn.tencent.DiscuzMob.utils.BitmapUtils;
import cn.tencent.DiscuzMob.utils.DensityUtil;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.IntricateLayout;
import cn.tencent.DiscuzMob.widget.wheelview.DateUtils;
import cn.tencent.DiscuzMob.widget.wheelview.JudgeDate;
import cn.tencent.DiscuzMob.widget.wheelview.ScreenInfo;
import cn.tencent.DiscuzMob.R;

/**
 * Created by kurt on 15-7-17.
 */
public class SendActivityThreadActivity extends BaseActivity implements View.OnClickListener {
    private static final int EDUCATION_REQUEST_CODE = 4;
    private ImageView mBack, mCover;
    private EditText mTitle, mActivityAddress, mActivityMemberSize, mActivityDetail, mCateEdit;
    private CheckBox mNameCheck, mPhoneCheck, qq_check;
    private TextView mSubmit, mStartTimeText, mEndTimeText, mActivityCate;
    private ProgressBar mProgress;
    private static final int CATE_REQUEST_CODE = 0;
    private static final int PHOTO_REQUEST_CODE = 1;
    private static final int SECURE_REQUEST_CODE = 2;
    private static final int SEX_REQUEST_CODE = 3;
    private String mCoverPath = "";
    private String initDateTime;
    private int isHasName, isHasPhone, isHasQQ;
    private String fid;
    private String mTid;
    private String attachId;
    private String activityClass = "";
    private String uploadhash;
    private String seccodeverify;
    private String vcodeCookie;
    private String seccode;
    private String sechash;
    private String cookiepre;
    private List<String> strings = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                RednetUtils.toast(SendActivityThreadActivity.this, "发帖成功");
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendEmptyMessage(4);
                    }
                }, 0);
            } else if (msg.what == 2) {
                mProgress.setVisibility(View.GONE);
                RednetUtils.toast(SendActivityThreadActivity.this, (String) msg.obj);
            } else if (msg.what == 3) {
                mProgress.setVisibility(View.GONE);
                RednetUtils.toast(SendActivityThreadActivity.this, "您没有发帖权限");
            } else if (msg.what == 4) {
                mProgress.setVisibility(View.GONE);
                startActivity(new Intent(SendActivityThreadActivity.this, ActivityThreadDetailsActivity.class).putExtra("id", mTid).putExtra("fid", fid));
                finish();
            } else {
                if (msg.what == -1 && msg.obj instanceof String) {
                    RednetUtils.toast(SendActivityThreadActivity.this, (String) msg.obj);
                }
            }
        }
    };
    String cookiepre_auth;
    String cookiepre_saltkey;
    String formhash;
    List<String> activityType;
    List<String> activityfieldKeys;
    List<String> activityfieldValues;
    private IntricateLayout label;
    private EditText activity_integral;//积分
    private EditText activity_spendingl;//花销
    private TextView activity_end;//截止报名时间
    private TextView activity_sex;//性别
    private WheelMain wheelMainDate;
    private String beginTime;
    private TextView tv_types;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> listKeys = new ArrayList<>();
    private String key;
    public ImageChooserDialog mImageChooserDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organize_activities);
        mImageChooserDialog = this != null ? new ImageChooserDialog(this) : null;
        tv_types = (TextView) findViewById(R.id.tv_types);
        uploadhash = CacheUtils.getString(SendActivityThreadActivity.this, "uploadhash");
        cookiepre_auth = CacheUtils.getString(this, "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(this, "cookiepre_saltkey");
        formhash = CacheUtils.getString(this, "formhash");
        fid = getIntent().getStringExtra("fid");
        activityType = (List<String>) getIntent().getSerializableExtra("activityType");
        activityfieldKeys = (List<String>) getIntent().getSerializableExtra("activityfieldKeys");
        activityfieldValues = (List<String>) getIntent().getSerializableExtra("activityfieldValues");
        mBack = (ImageView) findViewById(R.id.back);
        mTitle = (EditText) findViewById(R.id.activity_title);
        mStartTimeText = (TextView) findViewById(R.id.start_time_edit);
        mEndTimeText = (TextView) findViewById(R.id.end_time_edit);
        mCover = (ImageView) findViewById(R.id.activity_cover);
        mCover.setScaleType(ImageView.ScaleType.FIT_XY);
        mActivityAddress = (EditText) findViewById(R.id.activity_address);
        mActivityMemberSize = (EditText) findViewById(R.id.activity_member_size);
        mActivityDetail = (EditText) findViewById(R.id.activity_detail);
        mNameCheck = (CheckBox) findViewById(R.id.name_check);
        mPhoneCheck = (CheckBox) findViewById(R.id.phone_check);
        qq_check = (CheckBox) findViewById(R.id.qq_check);
        mSubmit = (TextView) findViewById(R.id.publish);
        mProgress = (ProgressBar) findViewById(R.id.progressbar);
        mActivityCate = (TextView) findViewById(R.id.activity_cate);
        mCateEdit = (EditText) findViewById(R.id.activity_edit_cate);
        activity_sex = (TextView) findViewById(R.id.activity_sex);
        label = (IntricateLayout) findViewById(R.id.label);
        activity_integral = (EditText) findViewById(R.id.activity_integral);
        activity_spendingl = (EditText) findViewById(R.id.activity_spendingl);
        activity_end = (TextView) findViewById(R.id.activity_end);
        mBack.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mCover.setOnClickListener(this);
        mStartTimeText.setOnClickListener(this);
        mEndTimeText.setOnClickListener(this);
        mNameCheck.setOnClickListener(this);
        mPhoneCheck.setOnClickListener(this);
        qq_check.setOnClickListener(this);
        mActivityCate.setOnClickListener(this);
        activity_sex.setOnClickListener(this);
        activity_end.setOnClickListener(this);
        tv_types.setOnClickListener(this);
        String types = getIntent().getStringExtra("types");

        if (null != types) {
            tv_types.setVisibility(View.VISIBLE);
            list.clear();
            listKeys.clear();
            com.alibaba.fastjson.JSONObject object = JSON.parseObject(types);
            for (Map.Entry<String, Object> entry : object.entrySet()) {
                list.add(entry.getValue().toString());
                listKeys.add(entry.getKey().toString());
            }
        } else {
            tv_types.setVisibility(View.GONE);
        }
        initData();
    }

    private void initData() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.rightMargin = DensityUtil.dip2px(this, 12);
        params.bottomMargin = DensityUtil.dip2px(this, 12);
        for (int i = 0; i < activityfieldValues.size(); i++) {
            View inflate = View.inflate(this, R.layout.activityfieldvalues_item, null);
            CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.name_check);
            checkBox.setLayoutParams(params);
            String name = activityfieldValues.get(i);
            checkBox.setText(name);
            label.addView(inflate);

        }
        label.setOnIntricateClickListener(new IntricateLayout.OnIntricateClickListener() {
            @Override
            public void onIntricateClick(int position) {
                for (int i = 0, count = label.getChildCount(); i < count; i++) {
                    label.getChildAt(i).setSelected(i == position);

                }
                CheckBox childAt = (CheckBox) label.getChildAt(position);
                boolean checked = childAt.isChecked();
                if (checked == true) {
                    strings.add(activityfieldKeys.get(position));
                } else {
                    if (strings.contains(activityfieldKeys.get(position))) {
                        strings.remove(activityfieldKeys.get(position));
                    }
                }
                boolean selected = childAt.isSelected();
//                id = threadtypes.get(position).getId();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.publish:
                if (RednetUtils.hasLogin(this)) {
                    if (activityClass.equals("")) {
                        activityClass = mCateEdit.getText().toString().trim();
                    }
                    if (TextUtils.isEmpty(mTitle.getText().toString().trim())) {
                        mHandler.sendMessage(Message.obtain(mHandler, -1, "请填写标题"));
                        return;
                    } else if (TextUtils.isEmpty(mActivityDetail.getText().toString().trim())) {
                        mHandler.sendMessage(Message.obtain(mHandler, -1, "请填写帖子内容"));
                        return;
                    } else if (TextUtils.isEmpty(mCateEdit.getText().toString().trim())) {
                        mHandler.sendMessage(Message.obtain(mHandler, -1, "请输入活动的类型"));
                        return;
                    } else if (TextUtils.isEmpty(mActivityAddress.getText().toString().trim())) {
                        mHandler.sendMessage(Message.obtain(mHandler, -1, "请输入活动地点"));
                        return;
                    } else if (TextUtils.isEmpty(mStartTimeText.getText().toString().trim())) {
                        mHandler.sendMessage(Message.obtain(mHandler, -1, "请选择开始的时间"));
                        return;
                    } else if (TextUtils.isEmpty(mEndTimeText.getText().toString().trim())) {
                        mHandler.sendMessage(Message.obtain(mHandler, -1, "请选择结束的时间"));
                        return;
                    } else {

                        checkSecure();
//                        mProgress.setVisibility(View.VISIBLE);
//                        mHandler.sendMessage(Message.obtain(mHandler, -1, "正在检查您的发帖权限"));
//                        RedNet.mHttpClient.newCall(new Request.Builder()
//                                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";" + formhash + ";")
//                                .url(AppNetConfig.JURISDICTION + "&fid=" + fid)
//                                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
//                                .enqueue(new com.squareup.okhttp.Callback() {
//                                    @Override
//                                    public void onFailure(Request request, IOException e) {
//                                        mHandler.sendMessage(Message.obtain(mHandler, 2, "检查发帖权限失败"));
//                                    }
//
//                                    @Override
//                                    public void onResponse(Response response) throws IOException {
//                                        try {
//                                            JurisdictionBean jurisdictionBean = new Gson().fromJson(response.body().string(), JurisdictionBean.class);
//                                            JurisdictionBean.VariablesBean variables = jurisdictionBean.getVariables();
//                                            if (variables != null) {
//                                                JurisdictionBean.VariablesBean.AllowpermBean allowperm = variables.getAllowperm();
//                                                String allowpost = allowperm.getAllowpost();
//                                                if (allowpost.equals("1")) {
//                                                    runOnUiThread(new Runnable() {
//                                                        @Override
//                                                        public void run() {
//                                                            checkSecure();
//                                                        }
//                                                    });
//
//                                                } else {
//
//                                                    mHandler.sendEmptyMessage(3);
//                                                }
//                                            } else {
//                                                mHandler.sendEmptyMessage(3);
//                                            }
//
//                                        } catch (JsonSyntaxException e) {
//                                            mHandler.sendEmptyMessage(3);
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                });
//
                    }
                }

                break;
            case R.id.activity_cover:
                if (mImageChooserDialog != null) mImageChooserDialog.show();
//                Intent selectPhotoIntent = new Intent();
//                selectPhotoIntent.setClass(this, SelectPhotoDialogActivity.class);
//                startActivityForResult(selectPhotoIntent, PHOTO_REQUEST_CODE);
//                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            case R.id.start_time_edit:
                showtimePopupWindow(mStartTimeText);
                break;
            case R.id.end_time_edit:
                showtimePopupWindow(mEndTimeText);
                break;
            case R.id.name_check:
                if (isHasName == 1) {
                    mNameCheck.setChecked(false);
                    isHasName = 0;
                } else {
                    mNameCheck.setChecked(true);
                    isHasName = 1;
                }
                break;
            case R.id.phone_check:
                if (isHasPhone == 1) {
                    mPhoneCheck.setChecked(false);
                    isHasPhone = 0;
                } else {
                    mPhoneCheck.setChecked(true);
                    isHasPhone = 1;
                }
                break;
            case R.id.qq_check:
                if (isHasQQ == 1) {
                    qq_check.setChecked(false);
                    isHasQQ = 0;
                } else {
                    qq_check.setChecked(true);
                    isHasQQ = 1;
                }
                break;
            case R.id.activity_cate:
                Intent selectTypeIntent = new Intent();
                selectTypeIntent.putExtra("activityType", (Serializable) activityType);
                selectTypeIntent.setClass(this, SelectActivityCateActivity.class);
                startActivityForResult(selectTypeIntent, CATE_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            case R.id.activity_sex:
                Intent srxIntent = new Intent();
                srxIntent.setClass(this, SelectActivitySexActivity.class);
                startActivityForResult(srxIntent, SEX_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            case R.id.activity_end:
                showtimePopupWindow(activity_end);
                break;
            case R.id.tv_types:
                Intent typeIntent = new Intent();
                typeIntent.putExtra("list", (Serializable) list);
                typeIntent.setClass(SendActivityThreadActivity.this, SelectActivitySexActivity.class);
                startActivityForResult(typeIntent, EDUCATION_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            default:
                break;
        }
    }

    private void showtimePopupWindow(final TextView TimeText) {
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = manager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        View menuView = LayoutInflater.from(this).inflate(R.layout.show_popup_window, null);
        final PopupWindow mPopupWindow = new PopupWindow(menuView, (int) (width * 0.9),
                ActionBar.LayoutParams.WRAP_CONTENT);
        ScreenInfo screenInfoDate = new ScreenInfo(this);
        wheelMainDate = new WheelMain(menuView, true);
        wheelMainDate.screenheight = screenInfoDate.getHeight();
        String time = DateUtils.currentMonth().toString();
        Calendar calendar = Calendar.getInstance();
        if (JudgeDate.isDate(time, "yyyy-MM-DD")) {
            try {
                calendar.setTime(new Date(time));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelMainDate.initDateTimePicker(year, month, day, hours, minute);
        mPopupWindow.setAnimationStyle(R.style.AnimationPreview);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.showAtLocation(mProgress, Gravity.CENTER, 0, 0);
        mPopupWindow.setOnDismissListener(new poponDismissListener());
        backgroundAlpha(0.6f);
        TextView tv_cancle = (TextView) menuView.findViewById(R.id.tv_cancle);
        TextView tv_ensure = (TextView) menuView.findViewById(R.id.tv_ensure);
        TextView tv_pop_title = (TextView) menuView.findViewById(R.id.tv_pop_title);
        tv_pop_title.setText("选择时间");
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mPopupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });
        tv_ensure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                beginTime = wheelMainDate.getTime().toString();
                TimeText.setText(DateUtils.formateStringH(beginTime, DateUtils.yyyyMMddHHmm));
                mPopupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    private void submit() {
        mHandler.sendMessage(Message.obtain(mHandler, -1, "发帖中..."));

        String starttimefrom = RednetUtils.conertTimeToTimeStamp2(mStartTimeText.getText().toString().trim());
        String starttimeto = RednetUtils.conertTimeToTimeStamp2(mEndTimeText.getText().toString().trim());
        String subject = mTitle.getText().toString().trim();
        String message = mActivityDetail.getText().toString().trim();
        String activityclass = TextUtils.isEmpty(activityClass) ? "" : activityClass;
        String activityplace = mActivityAddress.getText().toString().trim();
        String activitynumber = mActivityMemberSize.getText().toString().trim();
        activityClass = mCateEdit.getText().toString().trim();
        attachId = TextUtils.isEmpty(attachId) ? "" : "attachnew[" + attachId + "][description]";
        sechash = TextUtils.isEmpty(sechash) ? "" : sechash;
        seccodeverify = TextUtils.isEmpty(seccodeverify) ? "" : seccodeverify;
//        if (isHasName == 1) {
//            strings.add("realname");
//        }
//        if (isHasPhone == 1) {
//            strings.add("mobile");
//        }
//        if (isHasQQ == 1) {
//            strings.add("qq");
//        }
        String sex = activity_sex.getText().toString();
        if (sex.equals("不限")) {
            sex = "0";
        } else if (sex.equals("男")) {
            sex = "1";
        } else {
            sex = "2";
        }
        FormEncodingBuilder builder = new FormEncodingBuilder()
                .add("subject", subject)
                .add("message", message)
                .add("special", "4")
                .add("activityclass", activityclass)
                .add("activitytime", "1")
                .add("starttimefrom[1]", mStartTimeText.getText().toString().trim())
                .add("starttimeto", mEndTimeText.getText().toString().trim())
                .add("activityplace", activityplace)
                .add("activitynumber", activitynumber)
                .add("mobiletype", "2")
                .add("allownoticeauthor", "1")
                .add("userfield", "")
                .add("activitycredit", activity_integral.getText().toString())
                .add("cost", activity_spendingl.getText().toString())
                .add("gender", sex)
                .add("activityexpiration", activity_end.getText().toString())
                .add("formhash", CacheUtils.getString(SendActivityThreadActivity.this, "formhash1"));
        for (int i = 0; i < strings.size(); i++) {
            builder.add("userfield[" + i + "]", URLEncoder.encode(strings.get(i)));
        }
        if (list.size() > 0 && key != null && !TextUtils.isEmpty(key)) {
            builder.add("typeid", key);
        }
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";")
                .url(AppNetConfig.ACTIVITYTHRADF + fid)
                .post(builder.build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        mHandler.sendMessage(Message.obtain(mHandler, 2, "请求发帖失败"));
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        String strring  = response.body().string();
                        try {
                            SendActivityBean sendActivityBean = new Gson().fromJson(strring, SendActivityBean.class);
                            mTid = sendActivityBean.getVariables().getTid();
                            if (!TextUtils.isEmpty(mTid)) {
                                mHandler.sendEmptyMessage(1);
                            } else {
                                String messagestr = sendActivityBean.getMessage().getMessagestr();
                                mHandler.sendMessage(Message.obtain(mHandler, 2, messagestr));
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            mHandler.sendMessage(Message.obtain(mHandler, 2, "发帖失败"));
                        }
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
//            if (requestCode == PHOTO_REQUEST_CODE) {
//                if (data.getData() != null) {
//                    Uri uri = data.getData();
//                    String[] pojo = {MediaStore.Images.Media.DATA};
//                    Cursor cursor = managedQuery(uri, pojo, null, null, null);
//                    if (cursor != null) {
//                        int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
//                        cursor.moveToFirst();
//                        mCoverPath = cursor.getString(columnIndex);
//                        Log.e("TAG", "mCoverPath2=" + mCoverPath);
//                        mCover.setImageBitmap(null);
//                        Bitmap bit = BitmapUtil.decodeSampledBitmapFromResource(mCoverPath, 100, 100);
//
////                        BitmapFactory.Options options = new BitmapFactory.Options();
////                        Bitmap bitmap = BitmapFactory.decodeFile(mCoverPath, options);
//                        Log.e("TAG", "bit=" + bit);
//                        mCover.setImageBitmap(bit);
////
////                        mCover.setImageURI(Uri.parse(mCoverPath));
//                        Drawable drawable = mCover.getDrawable();
//                        Log.e("TAG", "drawable=" + drawable);
//                        cursor.close();
//                    }
//                } else if (data.getExtras() != null) {
//                    mCoverPath = data.getStringExtra("path");
//                    Log.e("TAG", "mCoverPath1=" + mCoverPath);
////                    mCover.setImageBitmap(null);
//                    Bitmap bit = BitmapUtil.decodeSampledBitmapFromResource(mCoverPath, 100, 100);
//                    Log.e("TAG", "bit=" + bit);
//                    mCover.setImageBitmap(bit);
////                    mCover.setImageURI(Uri.parse(mCoverPath));
//                }
//            }
            if (requestCode == ImageChooserDialog.REQUEST_CODE_PICK) {
                if (data != null && data.getData() != null) {
                    mCoverPath = BitmapUtils.getRealFilePath(SendActivityThreadActivity.this, data.getData());
                    Bitmap bit = BitmapUtils.decodeBitmapFromFile(mCoverPath, 100, 100);
                    if (bit != null) {
                        mCover.setImageBitmap(bit);
                    } else {
                        mCover.setImageURI(Uri.parse(mCoverPath));
                    }
//                    Cursor cursor = this.getContentResolver()
//                            .query(data.getData(), new String[]{MediaStore.MediaColumns.DATA}, null, null, null);
//                    if (cursor != null) {
//                        try {
//                            cursor.moveToFirst();
//                            mCoverPath = String.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)));
//                            Log.e("TAG", "mCoverPath=" + mCoverPath);
//                            Bitmap bit = BitmapUtils.decodeBitmapFromFile(mCoverPath, 100, 100);
//                            Log.e("TAG", "bit=" + bit);
//
//                            if (bit != null) {
//                                mCover.setImageBitmap(bit);
//                            } else {
//                                Log.e("TAG", "0000000000000000000000");
//                                mCover.setImageURI(Uri.parse(mCoverPath));
//                            }
////                            mCover.setImageBitmap(bit);
//                        } finally {
//                            cursor.close();
//                        }
//                    }
                }
            } else if (requestCode == ImageChooserDialog.REQUEST_CODE_CAMERA) {
                mCoverPath = mImageChooserDialog.getCurrentPhotoPath();
                Bitmap bit = BitmapUtil.decodeSampledBitmapFromResource(mCoverPath, 100, 100);
                mCover.setImageBitmap(null);
//                mCover.setImageURI(Uri.parse(mCoverPath));
                mCover.setImageBitmap(bit);
            } else if (requestCode == CATE_REQUEST_CODE) {
                activityClass = data.getStringExtra("cate");
                if (activityClass.equals("自定义")) {
                    activityClass = "";
//                    mActivityCate.setVisibility(View.GONE);
//                    mCateEdit.setVisibility(View.VISIBLE);
                    mCateEdit.setFocusableInTouchMode(true);
                    mCateEdit.requestFocus();
                    InputMethodManager inputManager = (InputMethodManager) mCateEdit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    mActivityCate.setText(activityClass);
                    mCateEdit.setText(activityClass);
                }
            } else if (requestCode == SECURE_REQUEST_CODE) {
                seccodeverify = data.getStringExtra("seccodeverify");
                vcodeCookie = RedNetPreferences.getVcodeCookie();
                if (mCoverPath != null & !mCoverPath.equals("")) {
                    uploadFile(mCoverPath);
                } else {
                    submit();
                }
            } else if (requestCode == SEX_REQUEST_CODE) {
                activity_sex.setText(data.getStringExtra("sex"));
            } else if (requestCode == EDUCATION_REQUEST_CODE) {
                String sex = data.getStringExtra("sex");
                tv_types.setTextColor(Color.BLACK);
                tv_types.setText(sex);
                int i = list.indexOf(sex);
                key = listKeys.get(i);
            }
        }
    }

    private void checkSecure() {
        submit();
//        TThreadManager.checkSecure("post", new DataLoaderListener() {
//            @Override
//            public void onLoadCacheFinished(Object object) {
//            }
//
//            @Override
//            public void onLoadDataFinished(Object object) {
//                HashMap<String, Object> data = (HashMap<String, Object>) object;
//                if (data != null) {
//                    SecureVariables secure = (SecureVariables) data.get("secure");
//                    cookiepre = (String) data.get("cookiepre");
//                    seccode = secure.getSeccode();
//                    sechash = secure.getSechash();
//                    if (seccode != null && !seccode.equals("")) {
//                        // 显示验证码！进行验证
//                        Intent intent = new Intent();
//                        intent.setClass(SendActivityThreadActivity.this, CheckSecureDialogActivity.class);
//                        intent.putExtra("seccode", seccode);
//                        intent.putExtra("sechash", sechash);
//                        intent.putExtra("cookiepre", cookiepre);
//                        intent.putExtra("from", CheckSecureDialogActivity.FROM_SEND);
//                        startActivityForResult(intent, SECURE_REQUEST_CODE);
//                        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
//                    } else {
//                        if (mCoverPath != null & !mCoverPath.equals("")) {
//                            uploadFile(mCoverPath);
//                        } else {
//                            submit();
//                        }
//                    }
//                } else {
//                    if (mCoverPath != null & !mCoverPath.equals("")) {
//                        uploadFile(mCoverPath);
//                    } else {
//                        submit();
//                    }
//                }
//            }
//
//
//            @Override
//            public void onError(Object object) {
//                mHandler.sendMessage(Message.obtain(mHandler, 2, "验证失败"));
//            }
//        });
    }

    private void uploadFile(String mCoverPath) {
        new Thread(new UploadFile(mCoverPath, uploadhash, fid, uploadResult)).start();
    }

    private UploadFile.FileResult uploadResult = new UploadFile.FileResult() {
        @Override
        public void onResult(Object fileResult) {
            String result = (String) fileResult;
//            DISCUZUPLOAD|0|65|-1|0
            String[] resultStr = result.split("\\|");
            String resultValue = resultStr[1];
            if (resultValue.endsWith("0")) {
                attachId = resultStr[2];
                submit();
            }
        }

        @Override
        public void onError(Exception ex, String path) {

        }
    };

    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

    }
}
