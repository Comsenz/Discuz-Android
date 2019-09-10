package cn.tencent.DiscuzMob.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.ui.activity.JoinManagerActivity;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/7/28.
 */

public class JoinManagerDialog extends Dialog implements View.OnClickListener {
    private JoinManagerActivity mContext;
    private String content;
    private String positiveName;
    private String negativeName;
    private String title;
    private ImageView iv_close;
    private Button btn_agree, btn_refuse;
    private TextView tv_apply_name;//申请人
    private TextView tv_leave_message_context;//留言
    private TextView tv_really_name_context;//
    private TextView tv_sex_context;
    private TextView tv_birthday_context;
    private TextView tv_spending_context;
    private TextView tv_apply_time_context;
    private TextView tv_status_context;
    private EditText et_postscript_context;
    private String formhash;
    com.alibaba.fastjson.JSONObject applylistItem;
    private LinearLayout ll_detial;
    private TextView tv_postscript;
    private LinearLayout ll_close;

    public JoinManagerDialog(@NonNull Context context, com.alibaba.fastjson.JSONObject applylistItem, String formhash) {
        super(context);
        this.mContext = (JoinManagerActivity) context;
        this.formhash = formhash;
        this.applylistItem = applylistItem;
    }

    public JoinManagerDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = (JoinManagerActivity) context;
    }

    protected JoinManagerDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = (JoinManagerActivity) context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popuo_dialog);
        setCanceledOnTouchOutside(false);
        initView();
        setData();
    }

    private List<String> strings;
    private List<com.alibaba.fastjson.JSONObject> list;

    private void setData() {
        Log.e("TAG", "applylistItem=" + applylistItem.toString());
        tv_apply_name.setText(applylistItem.getString("username"));
        tv_leave_message_context.setText(applylistItem.getString("message"));
        tv_apply_time_context.setText(Html.fromHtml(applylistItem.getString("dateline")));
        String verified = applylistItem.getString("verified");
        String uid = applylistItem.getString("uid");
        if (uid.equals(RedNetApp.getInstance().getUid())) {
            et_postscript_context.setVisibility(View.GONE);
            tv_postscript.setVisibility(View.GONE);
            btn_agree.setVisibility(View.GONE);
            btn_refuse.setVisibility(View.GONE);
        } else {
            et_postscript_context.setVisibility(View.VISIBLE);
            tv_postscript.setVisibility(View.VISIBLE);
            btn_agree.setVisibility(View.VISIBLE);
            btn_refuse.setVisibility(View.VISIBLE);
        }
        if (verified.equals("0")) {
            tv_status_context.setText("尚未审核");
        } else if (verified.equals("1")) {
            tv_status_context.setText("允许参加");
        } else {
            tv_status_context.setText("等待完善");
        }
        String payment = applylistItem.getString("payment");
        if (!payment.equals("-1")) {
            tv_spending_context.setText(applylistItem.getString("payment"));
        } else {
            tv_spending_context.setText("自费");
        }

        strings = new ArrayList<>();
        list = new ArrayList<>();
        Log.e("TAG", "applylistItem =" + applylistItem);
        com.alibaba.fastjson.JSONObject dbufielddata = applylistItem.getJSONObject("dbufielddata");
        if (null != dbufielddata) {
            for (Map.Entry<String, Object> entry : dbufielddata.entrySet()) {
                com.alibaba.fastjson.JSONObject value = (com.alibaba.fastjson.JSONObject) entry.getValue();
                String title = value.getString("title");
                String value1 = value.getString("value");
                View inflate = View.inflate(mContext, R.layout.joinmanagerdialog_item, null);
                TextView tv_item = (TextView) inflate.findViewById(R.id.tv_item);
                TextView tv_item_content = (TextView) inflate.findViewById(R.id.tv_item_content);
                tv_item.setText(title);
                tv_item_content.setText(value1);
                ll_detial.addView(inflate);
            }
        }

    }

    private void initView() {
        et_postscript_context = (EditText) findViewById(R.id.et_postscript_context);
        tv_status_context = (TextView) findViewById(R.id.tv_status_context);
        tv_apply_time_context = (TextView) findViewById(R.id.tv_apply_time_context);
        tv_spending_context = (TextView) findViewById(R.id.tv_spending_context);
        tv_birthday_context = (TextView) findViewById(R.id.tv_birthday_context);
        tv_sex_context = (TextView) findViewById(R.id.tv_sex_context);
        tv_really_name_context = (TextView) findViewById(R.id.tv_really_name_context);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        btn_agree = (Button) findViewById(R.id.btn_agree);
        btn_refuse = (Button) findViewById(R.id.btn_refuse);
        ll_close = (LinearLayout) findViewById(R.id.ll_close);
        ll_close.setOnClickListener(this);
        btn_agree.setOnClickListener(this);
        btn_refuse.setOnClickListener(this);
        tv_apply_name = (TextView) findViewById(R.id.tv_apply_name);
        tv_leave_message_context = (TextView) findViewById(R.id.tv_leave_message_context);
        ll_detial = (LinearLayout) findViewById(R.id.ll_detial);
        tv_postscript = (TextView) findViewById(R.id.tv_postscript);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_close:
                this.dismiss();
                break;
            case R.id.btn_agree:
                RedNet.mHttpClient.newCall(new Request.Builder()
                        .addHeader("Cookie", CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_auth") + ";" + CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_saltkey"))
                        .url(AppNetConfig.activity_management + applylistItem.getString("tid"))
                        .post(new MultipartBuilder("----kDdwDwoddGegowwdSmoqdaAesgjk")
                                .type(MultipartBuilder.FORM)
                                .addFormDataPart("formhash", formhash)
                                .addFormDataPart("handlekey", "activity")
                                .addFormDataPart("applyidarray[]", applylistItem.getString("applyid"))
                                .addFormDataPart("reason", et_postscript_context.getText().toString())
                                .addFormDataPart("operation", "")
                                .build())
                        .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                mContext.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, "网络请求失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    JSONObject message = jsonObject.getJSONObject("Message");
                                    if (null != message) {
                                        final String messageval = message.getString("messageval");
                                        final String messagestr = message.getString("messagestr");
                                        if (null != messageval && messageval.contains("activity_auditing_completion")) {
                                            mContext.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(mContext, messagestr, Toast.LENGTH_SHORT).show();
                                                    mContext.adapter.cleanData();
                                                    mContext.load(false);
                                                    dismiss();
                                                }
                                            });
                                        } else {
                                            mContext.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    JoinManagerActivity joinManagerActivity = new JoinManagerActivity();
                                                    mContext.adapter.cleanData();
                                                    mContext.load(false);
                                                    dismiss();
                                                }
                                            });
                                        }
                                    } else {
                                        mContext.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(mContext, "网络请求失败", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } catch (JSONException e) {
                                    mContext.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(mContext, "网络请求异常", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    e.printStackTrace();
                                }
                            }

                        });
                break;
            case R.id.btn_refuse:
                RedNet.mHttpClient.newCall(new Request.Builder()
                        .addHeader("Cookie", CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_auth") + ";" + CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_saltkey"))
                        .url(AppNetConfig.activity_management + applylistItem.getString("tid"))
                        .post(new MultipartBuilder("----kDdwDwoddGegowwdSmoqdaAesgjk")
                                .type(MultipartBuilder.FORM)
                                .addFormDataPart("formhash", formhash)
                                .addFormDataPart("handlekey", "activity")
                                .addFormDataPart("applyidarray[]", applylistItem.getString("applyid"))
                                .addFormDataPart("reason", et_postscript_context.getText().toString())
                                .addFormDataPart("operation", "delete")
                                .build())
                        .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                mContext.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, "网络请求失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    JSONObject message = jsonObject.getJSONObject("Message");
                                    if (null != message) {
                                        final String messageval = message.getString("messageval");
                                        final String messagestr = message.getString("messagestr");
                                        if (null != messageval && messageval.contains("activity_auditing_completion")) {
                                            mContext.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(mContext, messagestr, Toast.LENGTH_SHORT).show();
                                                    dismiss();
                                                    mContext.adapter.cleanData();
                                                    mContext.load(false);
                                                }
                                            });
                                        } else {
                                            mContext.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(mContext, messagestr, Toast.LENGTH_SHORT).show();
                                                    mContext.adapter.cleanData();
                                                    mContext.load(false);
                                                    dismiss();
                                                }
                                            });
                                        }
                                    } else {
                                        mContext.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(mContext, "网络请求失败", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } catch (JSONException e) {
                                    mContext.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(mContext, "网络请求异常", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    e.printStackTrace();
                                }
                            }

                        });
                break;
        }
    }


}
