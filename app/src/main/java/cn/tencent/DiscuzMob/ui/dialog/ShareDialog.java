package cn.tencent.DiscuzMob.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.social.UMPlatformData;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.utils.DateUtils;


public abstract class ShareDialog extends Dialog implements View.OnClickListener {

    private View mSina, mQQ, mWx, mPyq;

    public ShareDialog(Context context) {
        this(context, R.style.Theme_Rednet_Dialog);
    }

    public ShareDialog(Context context, int theme) {
        super(context, theme);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(context.getResources().getColor(android.R.color.transparent));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share);
//        (mSina = findViewById(R.id.weibo)).setTag(SinaWeibo.NAME);
        (mSina = findViewById(R.id.weibo)).setTag(QZone.NAME);
        (mQQ = findViewById(R.id.qq)).setTag(QQ.NAME);
        (mWx = findViewById(R.id.wx)).setTag(Wechat.NAME);
        (mPyq = findViewById(R.id.pyq)).setTag(WechatMoments.NAME);
        mWx.setOnClickListener(this);
        mPyq.setOnClickListener(this);
        mSina.setOnClickListener(this);
        mQQ.setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            dismiss();
            return true;
        } else
            return super.onTouchEvent(event);
    }

    public static final class Builder {

        Context context;
        OnekeyShare oks;
        String platform;
        boolean captureView;

        private String title, text;
        private String imageUrl;
        private String url;

        public Builder(Context context, OnekeyShare oks) {
            this.context = context;
            this.oks = oks;
            ShareSDK.initSDK(context);
        }

        Builder setPlatform(String platform, boolean captureView) {
            this.platform = platform;
            this.captureView = captureView;
            return this;
        }

        public Builder setText(String title, String text) {
            this.title = title;
            this.text = text;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public void sharePage() {
            oks.setPlatform(platform);
            oks.setSilent(true);
            Log.e("TAG", "text=" + text);
            int i = text.indexOf("于");
            String substring1 = text.substring(0, i + 1);
            String substring = text.substring(i + 1, text.length());
            Log.e("TAG", "substring1=" + substring1 + ";substring=" + substring);
            String timedate = DateUtils.timedate(substring);
            String s = substring1 + timedate;
            if (SinaWeibo.NAME.equals(platform)) {
                oks.setText(new StringBuilder("【").append(title).append("】").append(s).append(url).toString());
                MobclickAgent.onSocialEvent(context, new UMPlatformData(UMPlatformData.UMedia.SINA_WEIBO, ShareSDK.getPlatform(SinaWeibo.NAME).getDb().getUserId()));
            } else if (Wechat.NAME.equals(platform) || WechatMoments.NAME.equals(platform)) {
                oks.setTitle(title);
                oks.setText(s);
                oks.setUrl(url);
                oks.setTitleUrl(url);
                Log.e("TAG", "url=" + url);
                oks.setImageUrl(AppNetConfig.LOGOURL);
                MobclickAgent.onSocialEvent(context, new UMPlatformData(Wechat.NAME.equals(platform) ? UMPlatformData.UMedia.WEIXIN_FRIENDS : UMPlatformData.UMedia.WEIXIN_CIRCLE
                        , ShareSDK.getPlatform(Wechat.NAME.equals(platform) ? Wechat.NAME : WechatMoments.NAME).getDb().getUserId()));

            } else if (QQ.NAME.equals(platform)) {
                oks.setTitle(title);
                oks.setTitleUrl(url);
                oks.setText(!TextUtils.isEmpty(s) && text.length() > 40 ? s.substring(0, 40) : s);
                oks.setImageUrl(AppNetConfig.LOGOURL);
                MobclickAgent.onSocialEvent(context, new UMPlatformData(UMPlatformData.UMedia.TENCENT_QQ, ShareSDK.getPlatform(QQ.NAME).getDb().getUserId()));
            } else if (QZone.NAME.equals(platform)) {
                oks.setTitle(title);
                oks.setTitleUrl(url);
                oks.setText(s);
                oks.setSite("掌上论坛");
                oks.setSiteUrl(AppNetConfig.BASEURL);
                oks.setImageUrl(AppNetConfig.LOGOURL);
                MobclickAgent.onSocialEvent(context, new UMPlatformData(UMPlatformData.UMedia.TENCENT_QZONE, ShareSDK.getPlatform(QZone.NAME).getDb().getUserId()));
            }


//            oks.setImageUrl("http://oma9bz6fv.bkt.clouddn.com/ic_logo_share.png");
//            oks.setCallback(new PlatformActionListener() {
//                @Override
//                public void onComplete(final Platform platform, int i, HashMap<String, Object> hashMap) {
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, "onComplete", Toast.LENGTH_SHORT).show();
//                            if (platform.isClientValid()) {
//                            }
//
//                        }
//                    });
//                }
//
//                @Override
//                public void onError(Platform platform, int i, final Throwable throwable) {
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.e("TAG", "throwable.getMessage()=" + throwable.getMessage());
//                            Toast.makeText(context, "onError : = " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//
//                @Override
//                public void onCancel(Platform platform, int i) {
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, "onCancel", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            });
            oks.show(context);
        }


        public static Builder create(Context context) {
            return new Builder(context, new OnekeyShare());
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getTag() != null) {
            Builder sb = onShare();
            if (sb != null) {
                sb.setPlatform((String) v.getTag(), false).sharePage();
            }
        } else {
            dismiss();
        }
    }

    public abstract Builder onShare();

}
