package cn.tencent.DiscuzMob.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.manager.DataLoaderListener;
import cn.tencent.DiscuzMob.manager.TThreadManager;
import cn.tencent.DiscuzMob.model.PollPictureBean;
import cn.tencent.DiscuzMob.model.SecureVariables;
import cn.tencent.DiscuzMob.model.VoteItemBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.net.FileUploader;
import cn.tencent.DiscuzMob.ui.adapter.SwipeMenuListViewAdapter;
import cn.tencent.DiscuzMob.ui.dialog.CheckSecureDialogActivity;
import cn.tencent.DiscuzMob.ui.dialog.ImageChooserDialog;
import cn.tencent.DiscuzMob.ui.dialog.SelectActivitySexActivity;
import cn.tencent.DiscuzMob.utils.BitmapUtils;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.SwipeListLayout;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.R;


/**
 * Created by kurt on 15-7-17.
 * 发起投票帖子
 */
public class SendVoteThreadActivity extends BaseActivity implements View.OnClickListener {
    private static final int EDUCATION_REQUEST_CODE = 2;
    private ProgressBar mProgress;
    private EditText mTitleEdit, mVoteDaySize;
    private RadioGroup mRadio;
    private LinearLayout mVoteImgView;
    private CheckBox mResultShow, mOpenMember;
    private LayoutInflater inflater;
    private boolean isMaxChoice;
    private int maxchoices;
    private int isResultShow, isOpenMember;
    private String fid;
    private String mTid;
    private String uploadhash;
    private String seccodeverify;
    private String vcodeCookie;
    private String seccode;
    private String sechash;
    private String cookiepre;
    private int index;
    private static final int SECURE_REQUEST_CODE = 2;
    private ImageChooserDialog mImageChooserDialog;
    private ArrayList<VoteItemBean> voteOptionItemList = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mProgress != null) {
                switch (msg.what) {
                    case 1:
                        pb_progressbar.setVisibility(View.GONE);
                        sendEmptyMessageDelayed(4, 0);
                        RednetUtils.toast(SendVoteThreadActivity.this, "发帖成功");
                        break;
                    case 2:
                        pb_progressbar.setVisibility(View.GONE);
                        RednetUtils.toast(SendVoteThreadActivity.this, (String) msg.obj);
                        break;
                    case 3:
                        pb_progressbar.setVisibility(View.GONE);
                        RednetUtils.toast(SendVoteThreadActivity.this, "您没有发帖权限");
                        break;
                    case 4:
                        if (mTid != null) {
                            startActivity(new Intent(getApplicationContext(), VoteThreadDetailsActivity.class).putExtra("id", mTid));
                        }

                        finish();
                        break;
                    case 5:
                        RednetUtils.toast(SendVoteThreadActivity.this, "图片上传失败");
                        break;
                    default:
                        if (msg.what == -1 && msg.obj instanceof String) {
                            RednetUtils.toast(SendVoteThreadActivity.this, (String) msg.obj);
                        }
                        break;
                }
                mProgress.setVisibility(View.GONE);
            }
        }
    };
    String cookiepre_auth;
    String cookiepre_saltkey;
    String formhash;
    private EditText content;
    private ListView listView;
    private SwipeMenuListViewAdapter swipeMenuListViewAdapter;
    private Set<SwipeListLayout> sets = new HashSet();
    private AppAdapter appAdapter;
    int count = 2;
    private View choice;
    private FileUploader mFileUploader;
    private String mUploadHash;
    private List<View> items = new ArrayList<>();
    private List<Integer> aids = new ArrayList<>();
    private List<Integer> indexs = new ArrayList<>();
    private List<Integer> itemsCount;
    private List<VoteItemBean> voteItemBeans;
    private EditText vote_size;
    private TextView tv_types;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> listKeys = new ArrayList<>();
    private String key;
    private ProgressBar pb_progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organize_vote);
        pb_progressbar = (ProgressBar) findViewById(R.id.pb_progressbar);
        itemsCount = new ArrayList<>();
        voteItemBeans = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        View header = View.inflate(SendVoteThreadActivity.this, R.layout.header_view, null);
        tv_types = (TextView) header.findViewById(R.id.tv_types);
        View foot = View.inflate(SendVoteThreadActivity.this, R.layout.footview, null);
        foot.findViewById(R.id.add_img_btn).setOnClickListener(this);
        listView.addHeaderView(header);
        listView.addFooterView(foot);
        mUploadHash = CacheUtils.getString(SendVoteThreadActivity.this, "uploadhash");
        appAdapter = new AppAdapter();
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(SendVoteThreadActivity.this);
                openItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                openItem.setWidth(140);
                openItem.setTitle("删除");
                openItem.setTitleSize(16);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);
            }
        };
        String types = getIntent().getStringExtra("types");

        if (null != types) {
            Log.e("TAG", "types=" + types);
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
        tv_types.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent srxIntent = new Intent();
                srxIntent.putExtra("list", (Serializable) list);
                srxIntent.setClass(SendVoteThreadActivity.this, SelectActivitySexActivity.class);
                startActivityForResult(srxIntent, EDUCATION_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
            }
        });

        cookiepre_auth = CacheUtils.getString(this, "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(this, "cookiepre_saltkey");
        formhash = CacheUtils.getString(this, "formhash");
        fid = getIntent().getStringExtra("fid");
        inflater = LayoutInflater.from(this);
        mProgress = (ProgressBar) findViewById(R.id.progressbar);
        mTitleEdit = (EditText) header.findViewById(R.id.vote_title);
        content = (EditText) header.findViewById(R.id.content);
        mRadio = (RadioGroup) header.findViewById(R.id.vote_radio);
        mVoteImgView = (LinearLayout) header.findViewById(R.id.vote_img_view);
        mVoteDaySize = (EditText) foot.findViewById(R.id.vote_day_size);
        vote_size = (EditText) foot.findViewById(R.id.vote_size);
        mResultShow = (CheckBox) foot.findViewById(R.id.result_show);
        mOpenMember = (CheckBox) foot.findViewById(R.id.open_member);
        mResultShow.setChecked(true);
        mOpenMember.setChecked(false);
        findViewById(R.id.back).setOnClickListener(this);
        foot.findViewById(R.id.ok).setOnClickListener(this);
        foot.findViewById(R.id.cancel).setOnClickListener(this);
        foot.findViewById(R.id.submit).setOnClickListener(this);
        mResultShow.setOnClickListener(this);
        mOpenMember.setOnClickListener(this);
        mRadio.setOnCheckedChangeListener(onCheckedChangeListener);
        listView.setAdapter(appAdapter);
        mFileUploader = FileUploader.newInstance(this, new Object());
        mProgress.setVisibility(View.GONE);
        choice = inflater.inflate(R.layout.vote_item, mVoteImgView, false);
        initOptions();

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    //当listview开始滑动时，若有item的状态为Open，则Close，然后移除
                    case SCROLL_STATE_TOUCH_SCROLL:
                        if (sets.size() > 0) {
                            for (SwipeListLayout s : sets) {
                                s.setStatus(SwipeListLayout.Status.Close, true);
                                sets.remove(s);
                            }
                        }
                        break;

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void initOptions() {
        for (int i = 0; i < 2; i++) {
            VoteItemBean voteItemBean = new VoteItemBean();
            voteItemBean.setChoice("");
            voteItemBean.setUri(null);
            voteOptionItemList.add(voteItemBean);
        }
        appAdapter.notifyDataSetChanged();
    }


    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int radioButtonId = group.getCheckedRadioButtonId();
            RadioButton rb = (RadioButton) SendVoteThreadActivity.this.findViewById(radioButtonId);
            if (rb.getText().equals(getResources().getText(R.string.single_choice))) {
                isMaxChoice = false;
            } else if (rb.getText().equals(getResources().getText(R.string.multi_choice))) {
                isMaxChoice = true;
            }
        }
    };

    View.OnClickListener mOnChoiceItemDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mVoteImgView.removeView((View) v.getTag());
        }
    };

    void addChoice(boolean del) {
        View choice = inflater.inflate(R.layout.vote_item, mVoteImgView, false);
        if (del) {
            View delete = choice.findViewById(R.id.delete);
            delete.setTag(choice);
            delete.setOnClickListener(mOnChoiceItemDeleteListener);
            delete.setVisibility(View.VISIBLE);
        }
        mVoteImgView.addView(choice);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_img_btn:
//                addChoice(true);
//                count++;

                VoteItemBean voteItemBean = new VoteItemBean();
                voteItemBean.setChoice("");
                voteItemBean.setUri(null);
                voteOptionItemList.add(voteItemBean);
                LogUtils.d("新增后刷新前" + voteOptionItemList.toString());
                appAdapter.notifyDataSetChanged();

                break;
            case R.id.result_show:
                if (isResultShow == 1) {
                    mResultShow.setChecked(false);
                    isResultShow = 0;
                } else {
                    mResultShow.setChecked(true);
                    isResultShow = 1;
                }
                break;
            case R.id.open_member:
                if (isOpenMember == 1) {
                    mOpenMember.setChecked(false);
                    isOpenMember = 0;
                } else {
                    mOpenMember.setChecked(true);
                    isOpenMember = 1;
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                break;
            case R.id.ok:
                checkSecure();
                break;
            case R.id.cancel:
                finish();
                break;
            default:
                break;
        }
    }


    private void checkSecure() {
//        submitVoteThread();
        TThreadManager.checkSecure("post", new DataLoaderListener() {
            @Override
            public void onLoadCacheFinished(Object object) {
            }

            @Override
            public void onLoadDataFinished(Object object) {
                HashMap<String, Object> data = (HashMap<String, Object>) object;
                if (data != null) {
                    SecureVariables secure;
                    secure = (SecureVariables) data.get("secure");
                    cookiepre = (String) data.get("cookiepre");
                    seccode = secure.getSeccode();
                    sechash = secure.getSechash();
                    if (seccode != null && !seccode.equals("")) {// 显示验证码！进行验证
                        Intent intent = new Intent();
                        intent.setClass(SendVoteThreadActivity.this, CheckSecureDialogActivity.class);
                        intent.putExtra("seccode", seccode);
                        intent.putExtra("sechash", sechash);
                        intent.putExtra("cookiepre", cookiepre);
                        intent.putExtra("from", CheckSecureDialogActivity.FROM_SEND);
                        startActivityForResult(intent, SECURE_REQUEST_CODE);
                        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                    } else {
                        submitVoteThread();
                    }
                } else {
                    submitVoteThread();
                }
            }

            @Override
            public void onError(Object object) {
                mHandler.sendMessage(Message.obtain(mHandler, 2, "验证错误"));
            }
        });
    }

    String expiration = "";

    private void submitVoteThread() {
        if (isMaxChoice) {
            maxchoices = mVoteImgView.getChildCount();
        } else {
            maxchoices = 1;
        }
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        String trim = mVoteDaySize.getText().toString().trim();
        if (null != trim && !TextUtils.isEmpty(trim)) {
            calendar.add(calendar.DATE, Integer.parseInt(trim));//把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime();   //这个时间就是日期往后推一天的结果
            expiration = String.valueOf(date.getTime() / 1000);
        }


//        int size = mVoteImgView.getChildCount();
        ArrayList<String> polloptionArray = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            EditText text = (EditText) items.get(i).findViewById(R.id.vote_item);
            polloptionArray.add(text.getText().toString());
        }

        String[] polloptiondata = polloptionArray.toArray(new String[polloptionArray.size()]);
        String subject = mTitleEdit.getText().toString();
        String message = content.getText().toString();
        String visibilitypoll = String.valueOf(isResultShow);
        String overt = String.valueOf(isOpenMember);
        expiration = TextUtils.isEmpty(expiration) ? "" : expiration;
        subject = TextUtils.isEmpty(subject) ? "" : subject;
        sechash = TextUtils.isEmpty(sechash) ? "" : sechash;
//        seccodeverify = TextUtils.isEmpty(seccodeverify) ? "" : seccodeverify;
        pb_progressbar.setVisibility(View.VISIBLE);
        mHandler.sendMessage(Message.obtain(mHandler, -1, "发帖中..."));
        FormEncodingBuilder builder = new FormEncodingBuilder()
                .add("subject", subject)
                .add("message", message)
                .add("maxchoices", String.valueOf(vote_size.getText().toString().trim()))
                .add("visibilitypoll", visibilitypoll)
                .add("overt", overt)
                .add("expiration", expiration)
                .add("mobiletype", "2")
                .add("allownoticeauthor", "1")
                .add("special", "4")
                .add("mapifrom","android")
                .add("formhash", CacheUtils.getString(SendVoteThreadActivity.this, "formhash1"));
        /*添加选项*/
        for (int i = 0, length = polloptiondata != null ? polloptiondata.length : 0; i < length; i++) {
            builder.add("polloption[" + i + "]", URLEncoder.encode(polloptiondata[i]));
        }
        for (int i = 0; i < voteOptionItemList.size(); i++) {
            builder.add("polloption[" + i + "]", voteOptionItemList.get(i).getChoice());
        }
        /*添加选项对应的图片*/
        for (int i = 0; i < indexs.size(); i++) {
            builder.add("pollimage[" + indexs.get(i) + "]", aids.get(i) + "");
        }
        if (list.size() > 0 && key != null && !TextUtils.isEmpty(key)) {
            Log.e("TAG", "key=" + key);
            builder.add("typeid", key);
        }
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";")
                .url(new StringBuilder(AppNetConfig.VOTETHREAD + fid).append(TextUtils.isEmpty(sechash) ? "" : ("&sechash=" + sechash))
                        .append(TextUtils.isEmpty(seccodeverify) ? "" : ("&seccodeverify=" + seccodeverify))
                        .toString())
                .post(builder.build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        pb_progressbar.setVisibility(View.GONE);
                        mHandler.sendMessage(Message.obtain(mHandler, 2, "发送投票贴失败"));
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
//                        Log.e("TAG", "发送投票贴=" + response.body().string());
                        String string = response.body().string();
                        try {
                            JSONObject object = new JSONObject(string);
                            if (object != null) {
                                JSONObject variables = object.getJSONObject("Variables");
                                mTid = variables.getString("tid");
                                if (mTid != null && !TextUtils.isEmpty(mTid)) {
                                    mHandler.sendEmptyMessage(1);
                                } else {
                                    mHandler.sendMessage(Message.obtain(mHandler, 2, ""));
                                }
                            } else {
                                mHandler.sendMessage(Message.obtain(mHandler, 2, "发帖失败"));
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            mHandler.sendMessage(Message.obtain(mHandler, 2, "请求异常"));
                        }catch (JSONException e) {
                            mHandler.sendMessage(Message.obtain(mHandler, 2, "请求异常"));
                            e.printStackTrace();
                        }
                    }
                });
    }

    String path;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == ImageChooserDialog.REQUEST_CODE_PICK) {
                if (data != null && data.getData() != null) {
                    path = BitmapUtils.getRealFilePath(this, data.getData());
                    mFileUploader.submitPoll(path, mUploadHash, fid, new FileUploader.CallbackImpl<Object>(String.valueOf(path)) {
                        @Override
                        public void onCallback(Object obj) {
                            Log.e("TAG", "投票贴图片=" + obj.toString());
                            if (null != obj.toString()) {
                                if (obj.toString().contains("java.lang.Exception: java.lang.Exception")) {
                                    Toast.makeText(SendVoteThreadActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
                                } else {
                                    params(obj.toString());
                                }
                            } else {
                                Toast.makeText(SendVoteThreadActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
//                    Cursor cursor = this.getContentResolver()
//                            .query(data.getData(), new String[]{MediaStore.MediaColumns.DATA}, null, null, null);
//                    if (cursor != null) {
//                        try {
//                            cursor.moveToFirst();
//                            path = String.valueOf(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)));
//                            mFileUploader.submitPoll(path, mUploadHash, fid, new FileUploader.CallbackImpl<Object>(String.valueOf(path)) {
//                                @Override
//                                public void onCallback(Object obj) {
//                                    Log.e("TAG", "投票贴图片=" + obj.toString());
//                                    if (null != obj.toString()) {
//                                        if (obj.toString().contains("java.lang.Exception: java.lang.Exception")) {
//                                            Toast.makeText(SendVoteThreadActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            params(obj.toString());
//                                        }
//                                    } else {
//                                        Toast.makeText(SendVoteThreadActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
//                                    }
//
//
//                                }
//                            });
//                        } finally {
//                            cursor.close();
//                        }
//                    }
                }
            } else if (requestCode == ImageChooserDialog.REQUEST_CODE_CAMERA) {
                path = String.valueOf(mImageChooserDialog.getCurrentPhotoPath());
                Log.e("TAG", "path1=" + path);
                mFileUploader.submitPoll(path, mUploadHash, fid, new FileUploader.CallbackImpl<Object>(String.valueOf(path)) {
                    @Override
                    public void onCallback(Object obj) {
                        params(obj.toString());
                    }
                });
            } else if (requestCode == EDUCATION_REQUEST_CODE) {
                String sex = data.getStringExtra("sex");
                tv_types.setText(sex);
                int i = list.indexOf(sex);
                key = listKeys.get(i);
                Log.e("TAG", "key1=" + key);
            }
//            if (requestCode == SECURE_REQUEST_CODE) {
//                seccodeverify = data.getStringExtra("seccodeverify");
//                vcodeCookie = RedNetPreferences.getVcodeCookie();
//                mHandler.sendMessage(Message.obtain(mHandler, -1, "验证通过,发帖中"));
//                submitVoteThread();
//            }
        }
    }

    String bigimg;
    private Map<Integer, Integer> map = new HashMap<>();

    private void params(String s) {
        if (null != s && !TextUtils.isEmpty(s)) {
            PollPictureBean pollPictureBean = null;
            try {
                pollPictureBean = new Gson().fromJson(s, PollPictureBean.class);
                int aid = pollPictureBean.getAid();
                Log.e("TAG", "index1=" + index);
                map.put(index, aid);
                aids.add(aid);
                indexs.add(index);
                VoteItemBean voteItemBean = voteOptionItemList.get(index);
                voteItemBean.setUri(Uri.parse(path));
                bigimg = pollPictureBean.getBigimg();
//                appAdapter.setImg(path);
                appAdapter.notifyDataSetChanged();
            } catch (JsonSyntaxException e) {
                Toast.makeText(SendVoteThreadActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            mHandler.sendEmptyMessage(5);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("SendVoteThreadActivity(发表投票)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("SendVoteThreadActivity(发表投票)");
    }

    int p;

    private class AppAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return voteOptionItemList.size();
        }

        @Override
        public Object getItem(int position) {
            return voteOptionItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            convertView = View.inflate(parent.getContext(), R.layout.vote_item, null);
            ImageView iv_add = (ImageView) convertView.findViewById(R.id.iv_add);
            EditText vote_item = (EditText) convertView.findViewById(R.id.vote_item);
            final SwipeListLayout sll_main = (SwipeListLayout) convertView
                    .findViewById(R.id.sll_main);
            TextView tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
            sll_main.setOnSwipeStatusListener(new MyOnSlipStatusListener(
                    sll_main));
            iv_add.setScaleType(ImageView.ScaleType.FIT_XY);

            final VoteItemBean voteItemBean = voteOptionItemList.get(position);
            LogUtils.d("setText:" + voteItemBean.getChoice());
            vote_item.setText(voteItemBean.getChoice());
            iv_add.setImageURI(voteItemBean.getUri());

            TextWatcher tw = new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    voteItemBean.setChoice(s.toString());
                }
            };
            vote_item.addTextChangedListener(tw);
            iv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index = position;
                    mImageChooserDialog = new ImageChooserDialog(SendVoteThreadActivity.this);
                    mImageChooserDialog.show();
                }
            });
            tv_delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (voteOptionItemList.size() > 2) {
                        if (indexs.contains(position)) {
                            aids.remove(indexs.contains(position));
                        }
                        sll_main.setStatus(SwipeListLayout.Status.Close, true);
                        voteOptionItemList.remove(position);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(SendVoteThreadActivity.this, "最少有两个选项", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });
            return convertView;
        }

        class ViewHolder {
            private ImageView iv_add;
            private EditText vote_item;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            Log.d("0627", voteOptionItemList.toString());
        }
    }

    class MyOnSlipStatusListener implements SwipeListLayout.OnSwipeStatusListener {

        private SwipeListLayout slipListLayout;

        public MyOnSlipStatusListener(SwipeListLayout slipListLayout) {
            this.slipListLayout = slipListLayout;
        }

        @Override
        public void onStatusChanged(SwipeListLayout.Status status) {
            if (status == SwipeListLayout.Status.Open) {
                //若有其他的item的状态为Open，则Close，然后移除
                if (sets.size() > 0) {
                    for (SwipeListLayout s : sets) {
                        s.setStatus(SwipeListLayout.Status.Close, true);
                        sets.remove(s);
                    }
                }
                sets.add(slipListLayout);
            } else {
                if (sets.contains(slipListLayout))
                    sets.remove(slipListLayout);
            }
        }

        @Override
        public void onStartCloseAnimation() {

        }

        @Override
        public void onStartOpenAnimation() {

        }

    }
}
