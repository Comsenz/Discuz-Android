package cn.tencent.DiscuzMob.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.ui.Event.ReFreshImg;
import cn.tencent.DiscuzMob.utils.FileUtils;
import cn.tencent.DiscuzMob.utils.GetFileSize;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;

import static android.os.AsyncTask.execute;

/**
 * Created by kurt on 15-5-25.
 */
public class GeneralSettingsActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout mImageBroweSettingView, mNotificationSettingView, mClearCacheView, mAboutUsView;
    private ImageView mNotificationSwitch, mImageSwitch, mSettingBack;
    private boolean isLoad=false;
    private boolean isGet;
    private TextView cacheView;

    private long allFilesSize = 0;
    private int files;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    cacheView.setText(msg.obj.toString());
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initListener();
        calculateCache();
    }

    private void initView() {
        mImageBroweSettingView = (RelativeLayout) findViewById(R.id.image_browse_setting);
        mNotificationSettingView = (RelativeLayout) findViewById(R.id.notification_setting);
        mClearCacheView = (RelativeLayout) findViewById(R.id.clear_cache_setting);
        mAboutUsView = (RelativeLayout) findViewById(R.id.setting_about);
        mImageSwitch = (ImageView) findViewById(R.id.image_select_switch);
        mNotificationSwitch = (ImageView) findViewById(R.id.notification_select_switch);
        mSettingBack = (ImageView) findViewById(R.id.setting_back);
        cacheView = (TextView) findViewById(R.id.setting_cache);
        String imageval =CacheUtils.getImgString(this,"imageSetting");;
        LogUtils.i(imageval+"");
        if (imageval.equals("0")) {
            mImageSwitch.setImageLevel(0);
        } else {
            mImageSwitch.setImageLevel(1);
        }
        int val = RedNetPreferences.getNotificationSetting();
        if (val == 0) {
            mNotificationSwitch.setImageLevel(0);
            isGet = true;
        } else {
            mNotificationSwitch.setImageLevel(1);
            isGet = false;
        }
    }

    private void initListener() {
        mNotificationSettingView.setOnClickListener(this);
        mImageSwitch.setOnClickListener(this);
        mClearCacheView.setOnClickListener(this);
        mAboutUsView.setOnClickListener(this);
        mSettingBack.setOnClickListener(this);
        mNotificationSwitch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_back:
                finish();
                break;
            case R.id.image_select_switch:
                if (CacheUtils.getImgString(this,"imageSetting").equals("0")) {
                    mImageSwitch.setImageLevel(1);
                    CacheUtils.putString(this,"imageSetting","1");
//                    RedNetPreferences.setImageSetting(0);
                    EventBus.getDefault().post(new ReFreshImg(1));
                } else {
                    mImageSwitch.setImageLevel(0);
//                    RedNetPreferences.setImageSetting(1);
                    CacheUtils.putString(this,"imageSetting","0");
                    EventBus.getDefault().post(new ReFreshImg(0));
                }
                break;
            case R.id.notification_select_switch:
                if (!isGet) {
                    isGet = true;
                    mNotificationSwitch.setImageLevel(0);
                    RedNetPreferences.setNotificationSetting(0);
                } else {
                    mNotificationSwitch.setImageLevel(1);
                    isGet = false;
                    RedNetPreferences.setNotificationSetting(1);
                }
                RedNetApp.getInstance().registPusher();
                break;
            case R.id.clear_cache_setting:
                if ("0.00B".equals(cacheView.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "暂无缓存", Toast.LENGTH_SHORT).show();
                } else {
//                    FileUtils.clearDirectory(new File(Config.SDCARD_PATH));
                    FileUtils.clearDirectory(getCacheDir());
                    cacheView.setText("0.00B");
                    Toast.makeText(getApplicationContext(), "清理缓存成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.setting_about:
                Intent intent = new Intent(GeneralSettingsActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void calculateCache() {
        execute(new Runnable() {
            @Override
            public void run() {
                allFilesSize = 0;
                try {
//                GetFileSize g = new GetFileSize();
//                    long l = 0;
//                    String filesize = null;
//                    File ff = new File(Config.SDCARD_PATH);
//                    if (ff.isDirectory()) {
//                        l = g.getFileSize(ff);
//                        allFilesSize += l;
//                        filesize = g.FormetFileSize(l);
//                        files += g.getlist(ff);
//                        if (files > 0 || allFilesSize > 0) {
//                            Message msg = new Message();
//                            msg.what = 1;
//                            msg.obj = g.FormetFileSize(allFilesSize);
//                            Log.d("kurt", "-----------cache  size  = " + msg.obj);
//                            handler.sendMessage(msg);
//                        }
//                    }


                    String size = new GetFileSize().getFormatedFileSize(getCacheDir());
                    if (size.startsWith(".")){ //有可能是 .00
                        size = "0" + size;
                    }
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    message.obj = size;
                    handler.sendMessage(message);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("GeneralSettingsActivity(设置)");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageEnd("GeneralSettingsActivity(设置)");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
