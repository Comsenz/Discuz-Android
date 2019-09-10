package cn.tencent.DiscuzMob.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.CheckPostModelVariables;
import cn.tencent.DiscuzMob.model.FileBean;
import cn.tencent.DiscuzMob.model.SendThreadBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.net.FileUploader;
import cn.tencent.DiscuzMob.ui.Event.SendAudio;
import cn.tencent.DiscuzMob.ui.adapter.AudioListAdapter;
import cn.tencent.DiscuzMob.ui.dialog.ProgressDialog;
import cn.tencent.DiscuzMob.ui.dialog.SecureDialog;
import cn.tencent.DiscuzMob.ui.dialog.SelectActivitySexActivity;
import cn.tencent.DiscuzMob.ui.fragment.BaseFragment;
import cn.tencent.DiscuzMob.utils.Callback;
import cn.tencent.DiscuzMob.utils.Constant;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.RednetInput;

/**
 * Created by tiantian on 2016/7/10.
 */
public class SendThreadActivity extends BaseActivity implements View.OnClickListener, Callback<String> {
    private static final int EDUCATION_REQUEST_CODE = 2;
    private View mSubmit;
    private ProgressDialog mProgressDialog;
    private EditText mTitle, mContent;
    private RednetInput mRednetInput;
    private FileUploader mFileUploader;
    private SecureDialog mSecureDialog;
    private CheckPostModelVariables mCheckVariables;
    private String mUploadHash, mFid;
    private String audioid = "";
    private final Object mObj = new Object();
    //语音文件列表数据
    private List<FileBean> dataList;
    String key;
    //语音的aid
    private List<String> audioAids;
    private RednetInput.IChain mSimpleChainImpl = new RednetInput.IChain() {
        @Override
        public boolean doChain(String path) {
            return !TextUtils.isEmpty(path) && mFileUploader.canPut(path, mUploadHash, true);
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {

            if (msg.arg1 == 1) {
                mFileUploader.cancel((String) msg.obj, mUploadHash);
            } else if (msg.what == Constant.PLAY_COMPLETION) {
                showToastMsg(String.valueOf(R.string.play_over));
            } else if (msg.what == Constant.PLAY_ERROR) {
                showToastMsg(String.valueOf(R.string.play_error));
            } else {
                mFileUploader.submit(String.valueOf(msg.obj), mUploadHash, mFid, new FileUploader.CallbackImpl<Object>(String.valueOf(msg.obj)) {
                    @Override
                    public void onCallback(Object obj) {
                        if (obj instanceof String) {
                            mRednetInput.notifyUploadState(key, 2);
                        } else {
                            mRednetInput.notifyUploadState(key, 3);
                        }
                    }
                });
            }
        }
    };
    private Map strings;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> listKeys = new ArrayList<>();
    private String keyType;


    protected void showToastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private Thread mWaiting;
    String cookiepre_auth;
    String cookiepre_saltkey;
    String formhash;
    private SwipeMenuListView listView;
    AudioListAdapter audioListAdapter;
    private boolean isPlaying = false;
    private MediaPlayer mediaPlayer;
    private long index = -1;
    private TextView tv_types;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_thread);
        EventBus.getDefault().register(this);
        cookiepre_auth = CacheUtils.getString(this, "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(this, "cookiepre_saltkey");
        formhash = CacheUtils.getString(this, "formhash");
        mFid = getIntent().getStringExtra("fid");
        CacheUtils.putString(this, "mFid", mFid);
        mSubmit = findViewById(R.id.submit);
//        View headview = View.inflate(this, R.layout.sendthreadheadview, null);
        tv_types = (TextView) findViewById(R.id.tv_types);
        mUploadHash = CacheUtils.getString(SendThreadActivity.this, "uploadhash");
        dataList = new ArrayList<>();
        audioAids = new ArrayList<>();
        mTitle = (EditText) findViewById(R.id.title);
        mContent = (EditText) findViewById(R.id.content);
//        mRednetInput.setFlags(this, "发布", this, RednetInput.FLAG_EMOJ);
        mFileUploader = FileUploader.newInstance(this, mObj);
        mProgressDialog = new ProgressDialog(this, R.style.Theme_Rednet_Dialog_UnDim);
        mSubmit.setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
//        mProgressDialog.show();
        View inflate = View.inflate(this, R.layout.sendthreadfootview, null);
        mRednetInput = (RednetInput) inflate.findViewById(R.id.input);
        mRednetInput.setFileUploadHandler(mHandler, this);
        mRednetInput.setFlags(this, "发布", this, RednetInput.FLAG_EMOJ | RednetInput.FLAG_PHOTO | RednetInput.FLAG_RADIO);
        mRednetInput.setEmojListener(this);
        listView = (SwipeMenuListView) findViewById(R.id.listView);
        audioListAdapter = new AudioListAdapter();
        listView.setAdapter(audioListAdapter);
        listView.addFooterView(inflate);
        String types = getIntent().getStringExtra("types");
        Log.e("TAG", "types=" + types);
        if (null != types) {
            tv_types.setVisibility(View.VISIBLE);
            JSONObject object = JSON.parseObject(types);
            strings = new HashMap();
            for (Map.Entry<String, Object> entry : object.entrySet()) {
                strings.put(entry.getKey(), entry.getValue().toString());
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
                srxIntent.setClass(SendThreadActivity.this, SelectActivitySexActivity.class);
                startActivityForResult(srxIntent, EDUCATION_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
            }
        });
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(SendThreadActivity.this);
                openItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                openItem.setWidth(140);
                openItem.setTitle("删除");
                openItem.setTitleSize(16);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);
            }
        };

        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                dataList.remove(position);
                audioAids.remove(index);
                audioListAdapter.notifyDataSetChanged();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dataList.size() == 1) {
                    if (isPlaying) {
                        stopPlay();
                    } else {
                        startPlay(dataList.get((int) id).getFile());
                    }
                } else {
                    if (index == -1) {
                        index = id;
                        startPlay(dataList.get((int) index).getFile());
                    } else if (index == id) {
                        if (isPlaying) {
                            stopPlay();
                        } else {
                            startPlay(dataList.get((int) index).getFile());
                        }

                    } else {
                        index = id;
                        if (isPlaying) {
                            stopPlay();
                            startPlay(dataList.get((int) index).getFile());
                        } else {
                            startPlay(dataList.get((int) index).getFile());
                        }
                    }
                }
            }
        });
    }

    private void stopPlay() {
        isPlaying = false;
        //停止播放
        mediaPlayer.stop();
        mediaPlayer = null;
    }

    private void startPlay(File file) {
        try {
            isPlaying = true;
            //初始化播放器
            mediaPlayer = new MediaPlayer();
            //设置播放音频数据文件
            mediaPlayer.setDataSource(file.getAbsolutePath());
            //设置播放监听事件
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    //播放完成
                    playEndOrFail(true);
                }
            });
            //播放发生错误监听事件
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    playEndOrFail(false);
                    return true;
                }
            });
            //播放器音量配置
            mediaPlayer.setVolume(1, 1);
            //是否循环播放
            mediaPlayer.setLooping(false);
            //准备及播放
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
            //播放失败正理
            playEndOrFail(false);
        }
    }

    private void playEndOrFail(boolean isEnd) {
        isPlaying = false;
        if (isEnd) {
            mHandler.sendEmptyMessage(Constant.PLAY_COMPLETION);
        } else {
            mHandler.sendEmptyMessage(Constant.PLAY_ERROR);
        }
        if (null != mediaPlayer) {
            mediaPlayer.setOnCompletionListener(null);
            mediaPlayer.setOnErrorListener(null);
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    void submit(final String sechash, final String seccodeverify) {
        mSubmit.setEnabled(false);
        if (!mFileUploader.isAllCompleted()) {
            (mWaiting = new Thread() {
                @Override
                public void run() {
                    try {
                        synchronized (mObj) {
                            mObj.wait();
                        }
                    } catch (InterruptedException e) {
                        return;
                    }
                    postSubmit(sechash, seccodeverify);
                }
            }).start();
        } else
            postSubmit(sechash, seccodeverify);
    }

    void postSubmit(final String sechash, final String seccodeverify) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!RednetUtils.networkIsOk(SendThreadActivity.this)) {
                    mSubmit.setEnabled(true);
                    return;
                } else {
                    if (!mFileUploader.isAllCompletedSuccessful()) {
                        return;
                    }
                }
                String subject = mTitle.getText().toString().trim();
                String message = mContent.getText().toString().trim();
                FormEncodingBuilder builder = new FormEncodingBuilder()
                        .add("subject", subject)
                        .add("message", message)
                        .add("mobiletype", "2")
                        .add("allownoticeauthor", "1")
                        .add("typeid", "0")
                        .add("formhash", CacheUtils.getString(SendThreadActivity.this, "formhash1"));
                for (int i = 0, size = mFileUploader.aidList(mUploadHash, mRednetInput.photos()) != null ? mFileUploader.aidList(mUploadHash, mRednetInput.photos()).size() : 0; i < size; i++) {
                    builder.add(mFileUploader.aidList(mUploadHash, mRednetInput.photos()).get(i), "");
                }
                for (int i = 0; i < audioAids.size(); i++) {
                    builder.add("attachnew[" + audioAids.get(i) + "][description]",  dataList.get(i).getFileLength()+"");
                }
                if (strings != null && keyType != null && !TextUtils.isEmpty(keyType)) {
                    Log.e("TAG", "key="+key);
                    builder.add("typeid", keyType);
                }

//                if (!TextUtils.isEmpty(audioid)) {
//                    builder.add("attachnew[" + audioid + "][description]", "");
//                }
                RedNet.mHttpClient.newCall(new Request.Builder()
                        .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";")
                        .url(new StringBuilder(AppNetConfig.THREAD + mFid)
//                                .append(TextUtils.isEmpty(sechash) ? "" : ("&sechash=" + sechash))
//                                .append(TextUtils.isEmpty(seccodeverify) ? "" : ("&seccodeverify=" + seccodeverify))
                                .toString())
                        .post(builder.build()).build())
                        .enqueue(new com.squareup.okhttp.Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        RednetUtils.toast(SendThreadActivity.this, "发布普通帖请求失败");
                                    }
                                });

                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
                                String string = response.body().string();
                                Log.i("TAG", "发布普通帖成功=" + string);
                                SendThreadBean sendThreadBean = null;
                                try {
                                    sendThreadBean = new Gson().fromJson(string, SendThreadBean.class);
                                    LogUtils.i("sendThreadBean!=null "+(sendThreadBean != null));
                                    if (sendThreadBean != null) {
                                        final SendThreadBean.VariablesBean variables = sendThreadBean.getVariables();
                                        final String messagestr = sendThreadBean.getMessage().getMessagestr();
                                        LogUtils.i("messagestr="+messagestr);
                                        LogUtils.i("variables.getTid()="+variables.getTid());
                                        if (!TextUtils.isEmpty(variables.getTid())) {
                                            mHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    finish();
                                                    BaseFragment.toThreadDetails(SendThreadActivity.this, mFid, variables.getTid(), "0");
                                                }
                                            }, 1000);
                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    RednetUtils.toast(SendThreadActivity.this, messagestr);
                                                }
                                            });

                                        }
                                    }
                                    if (mProgressDialog != null) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mProgressDialog.dismiss();
                                                mSubmit.setEnabled(true);
                                            }
                                        });
                                    }
                                } catch (JsonSyntaxException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgressDialog.dismiss();
                                            mSubmit.setEnabled(true);
                                            RednetUtils.toast(SendThreadActivity.this, "请求异常");
                                        }
                                    });
                                    e.printStackTrace();
                                }
                            }
                        });
            }

//                ApiVersion5.INSTANCE.requestSendPost(mFid,subject,message,"0",sechash,seccodeverify,mFileUploader.aidList(mUploadHash,mRednetInput.photos())
//                    ,new ApiVersion5.Result<Object>(SendThreadActivity.this,SendThreadVariables.class,false,null,true)
//
//            {
//                @Override
//                public void onResult ( int code, Object value){
//                if (value instanceof BaseModel) {
//                    final SendThreadVariables variables = ((BaseModel<SendThreadVariables>) value).getVariables();
//                    if (!TextUtils.isEmpty(variables.getTid())) {
//                        mHandler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                finish();
//                                BaseFragment.toThreadDetails(SendThreadActivity.this, mFid, variables.getTid(), "0");
//                            }
//                        }, 1000);
//                    }
//                }
//                if (mProgressDialog != null) {
//                    mProgressDialog.dismiss();
//                    mSubmit.setEnabled(true);
//                }
//            }
//            });
//        }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        } else {
            String title = mTitle.getText().toString();
            String content = mContent.getText().toString();
            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
                RednetUtils.toast(this, "帖子标题内容不能为空");
            } else if (!RednetUtils.networkIsOk(SendThreadActivity.this)) {
            } else {
                mProgressDialog.show();
                submit(null, null);
//                ApiVersion5.INSTANCE.requestSecure("post", new ApiVersion5.Result<Object>(this, SecureVariables.class, false) {
//                    @Override
//                    public void onResult(int code, Object value) {
//                        if (value instanceof BaseModel) {
//                            final SecureVariables variables = ((BaseModel<SecureVariables>) value).getVariables();
//                            if (!TextUtils.isEmpty(variables.getSeccode())) {
//                                if (mSecureDialog == null) {
//                                    mSecureDialog = new SecureDialog(SendThreadActivity.this);
//                                }
//                                mProgressDialog.dismiss();
//                                mSecureDialog.show(variables, new Callback<String[]>() {
//                                    @Override
//                                    public void onCallback(final String[] obj) {
//                                        mHandler.post(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                submit(variables.getSechash(), obj[0]);
//                                            }
//                                        });
//                                    }
//                                });
//                                return;
//                            }
//                        }
//                        submit(null, null);
//                    }
//                });
            }
        }
    }

    @Override
    public void onCallback(String obj) {
        mContent.append(TextUtils.isEmpty(obj) ? "" : obj);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mRednetInput.handleOnActivityResult(this, mSimpleChainImpl, requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String sex = data.getStringExtra("sex");
            if (requestCode == EDUCATION_REQUEST_CODE) {
                tv_types.setText(sex);
                int i = list.indexOf(sex);
                keyType = listKeys.get(i);
                Log.e("TAG", "key1="+key);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        } else {
            if (mRednetInput.canBack()) super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        mFileUploader.clearAll();
        if (mWaiting != null && mWaiting.isAlive())
            mWaiting.interrupt();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("SendThreadActivity(发表新帖)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("SendThreadActivity(发表新帖)");
    }

    private List<String> audioPaths = new ArrayList<>();

    @Subscribe
    public void onEventMainThread(SendAudio finish) {
//        int id = finish.getId();
        final FileBean fileBean = finish.getFileBean();
        mFileUploader.submitAudio(SendThreadActivity.this, fileBean.getFile().getPath(), mUploadHash, mFid, new FileUploader.CallbackImpl<Object>(fileBean.getFile().getPath()) {
            @Override
            public void onCallback(Object obj) {
                audioid = obj.toString();
//                audioPaths.add(audioPath);
                dataList.add(fileBean);
                audioAids.add(audioid);
                audioListAdapter.setData(dataList);
                audioListAdapter.notifyDataSetChanged();
//                if (obj instanceof String) {
//                    mRednetInput.notifyUploadState(key, 2);
//                } else {
//                    mRednetInput.notifyUploadState(key, 3);
//                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            //就像onActivityResult一样这个地方就是判断你是从哪来的。
            case 222:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
//                    openCamra();
                    Log.e("TAG", "000000000000000000000000000000000000000000000");
                } else {
                    // Permission Denied
                    Toast.makeText(RedNetApp.getInstance(), "很遗憾你把相机权限禁用了。请务必开启相机权限享受我们提供的服务吧。", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
