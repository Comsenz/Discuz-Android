package cn.tencent.DiscuzMob.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.umeng.analytics.MobclickAgent;

import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.model.InfoMessage;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.model.BaseMessageVariables;
import cn.tencent.DiscuzMob.model.MyMessageInfo;
import cn.tencent.DiscuzMob.net.ApiVersion5;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by kurt on 15-6-8.
 */
public class SendMessageActivity extends BaseActivity implements View.OnClickListener {

    private EditText mTitleEdit, mContentEdit;
    private ProgressBar mProgress;
    private String userName;
    private String msgContent;
    private int formType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        formType = getIntent().getIntExtra("fromType", 0);
        mTitleEdit = (EditText) findViewById(R.id.recipient_name_edit);
        mContentEdit = (EditText) findViewById(R.id.msg_content_edit);
        mProgress = (ProgressBar) findViewById(R.id.progressbar);
        findViewById(R.id.setting_back).setOnClickListener(this);
        findViewById(R.id.send_msg_view).setOnClickListener(this);
        if (formType == 2) {
            mTitleEdit.setText(getIntent().getStringExtra("name"));
            mTitleEdit.setClickable(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_msg_view:
                userName = mTitleEdit.getText().toString().trim();
                msgContent = mContentEdit.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    RednetUtils.toast(SendMessageActivity.this, "请填写收件人");
                } else if (TextUtils.isEmpty(msgContent)) {
                    RednetUtils.toast(SendMessageActivity.this, "请填消息内容");
                } else {
                    mProgress.setVisibility(View.VISIBLE);

                    ApiVersion5.INSTANCE.requestSendMessage("0", userName, msgContent
                            , new ApiVersion5.Result<Object>(SendMessageActivity.this, MyMessageInfo.class, false, null, true) {
                                @Override
                                public void onResult(int code, Object value) {
                                    if (mProgress != null) {
                                        mProgress.setVisibility(View.GONE);
                                        if (value instanceof BaseModel) {
                                            BaseMessageVariables<MyMessageInfo> variables =
                                                    ((BaseModel<BaseMessageVariables<MyMessageInfo>>) value).getVariables();
                                            variables.getPmid();
                                            InfoMessage result = ((BaseModel) value).getMessage();
                                            if (result != null && "do_success".equals(result.getMessageval())) {
                                                finish();
                                            }
                                        }
                                    }
                                }
                            });
                }
                break;
            default:
                finish();
                return;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("SendMessageActivity(发送消息)");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageEnd("SendMessageActivity(发送消息)");
    }

}
