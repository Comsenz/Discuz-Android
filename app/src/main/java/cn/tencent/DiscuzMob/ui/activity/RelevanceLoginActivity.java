package cn.tencent.DiscuzMob.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.R;

public class RelevanceLoginActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mLoginBack;
    private AsyncRoundedImageView iv_three_icon;
    private TextView tv_three_name;
    private String nickname;
    private String icon;
    private String type;
    private String openId,unionid;
    private static final int THREE_LOGIN_TO_REGISTER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relevance_login);
        try {
            nickname = getIntent().getStringExtra("nickname");
            icon = getIntent().getStringExtra("icon");
            openId = getIntent().getStringExtra("openId");
            type = getIntent().getStringExtra("type");
            unionid = getIntent().getStringExtra("unionid");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
    }
    public static void ComeIn(Activity activity,String openId,String nickname,String unionid,String type) {
        Intent intent = new Intent(activity, RelevanceLoginActivity.class);
        intent.putExtra("openId", openId);
        intent.putExtra("nickname", nickname);
        intent.putExtra("unionid", unionid);
        intent.putExtra("type", type);
        activity.startActivityForResult(intent,10080);
    }
    private void initView() {
        mLoginBack = (ImageView) findViewById(R.id.login_back);
        iv_three_icon = (AsyncRoundedImageView) findViewById(R.id.iv_three_icon);//第三方头像
        tv_three_name = (TextView) findViewById(R.id.tv_three_name);//第三方昵称
        mLoginBack.setOnClickListener(this);
        findViewById(R.id.tv_three_register).setOnClickListener(this);
        findViewById(R.id.tv_three_relevance).setOnClickListener(this);
        if (icon != null && !icon.equals("")) {
            iv_three_icon.load(icon);
        } else {
            iv_three_icon.setBackgroundResource(R.drawable.ic_header_def);
        }

        if (nickname != null && !nickname.equals("")) {
            tv_three_name.setText(nickname);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_back:
                finish();
                break;
            case R.id.tv_three_register://第三方登陆 注册论坛账户
                startActivity(new Intent(RelevanceLoginActivity.this, NewRegisterActivity.class)
                    .putExtra("type", type)
                    .putExtra("openId", openId));
                break;
            case R.id.tv_three_relevance://第三方登陆 关联已有论坛账户
//                startActivity(new Intent(RelevanceLoginActivity.this, LoginBindingActivity.class)
//                    .putExtra("type", type)
//                    .putExtra("openId", openId));
                Intent intent = new Intent();
                intent.putExtra("type",type);
                intent.putExtra("openId",openId);
                if (type.equals("weixin")){
                    intent.putExtra("unionid",unionid);
                }
                intent.putExtra("logintype","1");
                setResult(10086,intent);
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("RelevanceLoginActivity(关联登录)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("RelevanceLoginActivity(关联登录)");
    }

}
