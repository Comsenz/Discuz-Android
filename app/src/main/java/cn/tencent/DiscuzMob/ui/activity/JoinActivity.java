package cn.tencent.DiscuzMob.ui.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.ui.dialog.ListSeleterDialog;
import cn.tencent.DiscuzMob.widget.ActivityJoinInfoItemView;
import cn.tencent.DiscuzMob.widget.CustomListView;
import cn.tencent.DiscuzMob.widget.wheelview.DashedLineView;
import cn.tencent.DiscuzMob.widget.wheelview.WheelMain;
import cn.tencent.DiscuzMob.widget.wheelview.WheelView;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.dialog.SelectActivitySexActivity;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.ActivityJoinTextareaItemView;
import cn.tencent.DiscuzMob.widget.wheelview.DateUtils;
import cn.tencent.DiscuzMob.widget.wheelview.JudgeDate;
import cn.tencent.DiscuzMob.widget.wheelview.ScreenInfo;
import cn.tencent.DiscuzMob.R;

import static cn.tencent.DiscuzMob.R.id.tv_content;


/**
 * Created by kurt on 15-7-30.
 */
public class JoinActivity extends BaseActivity implements View.OnClickListener {

    private static final int SEX_REQUEST_CODE = 3;
    private static final int BLOODTYPE_REQUEST_CODE = 1;
    private static final int IDCARDTYPE_REQUEST_CODE = 4;
    private static final int EDUCATION_REQUEST_CODE = 2;
    private static final int SELECT_REQUEST_CODE = 5;
    private static final int RADIO_REQUEST_CODE = 6;
    private static final int FIELD_REQUEST_CODE = 7;
    private LinearLayout infoItemView;
    private EditText mContent;
    private TextView mTitle, mOk, mCancel;
    private TextView tv_credit;
    private ImageView iv_chick;
    private ImageView iv_unchick;
    private ProgressBar mProgress;
    private HashMap<String, String> data;
    private String ufieldStr;
    private String joinfieldStr;
    String settings;
    private String credit;
    private String creditcost;
    private String cost;
    private String fid;
    private String tid;
    private String pid;
    private String message;
    private boolean isJoin;
    private List<String> keys = new ArrayList<>();
    private List<String> values = new ArrayList<>();
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mProgress.setVisibility(View.GONE);
            if (msg.what == 1) {
                boolean isJoin = (boolean) msg.obj;
                Intent intent = new Intent();
                intent.putExtra("result", 1);
                intent.putExtra("isJoin", isJoin);
                setResult(RESULT_OK, intent);
                finish();
            } else if (msg.what == 2) {
                RednetUtils.toast(JoinActivity.this, "请填写完整的信息");
            } else {
                RednetUtils.toast(JoinActivity.this, "请求失败");
            }
        }
    };
    String cookiepre_auth;
    String cookiepre_saltkey;
    String formhash;
    private LinearLayout ll_chick, ll_unchick;
    TextView info_value_gender;
    private WheelMain wheelMainDate;
    TextView tv_bloodtype;
    TextView tv_field;
    TextView tv_radio;
    TextView tv_select;
    TextView tv_education;
    private TextView tv_idcardtype;
    TextView tv_gender;
    ArrayList<Integer> integers;
    ArrayList<Integer> textareaIntegers;
    private ArrayList<Integer> listData = new ArrayList<>();
    private RelativeLayout rl_cost;
    private TextView tv_line;
    private EditText et_pay;
    TextView tv_birthcityt;
    TextView tv_residecity;
    TextView tv_birthdist;
    private String specialJson;
    com.alibaba.fastjson.JSONObject joinfield;
    String formtype = null;
    String choices = null;
    com.alibaba.fastjson.JSONObject json = null;
    private List<String> listType = new ArrayList<>();
    ArrayList<String> arrayList;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        setContentView(R.layout.activity_join_activity);
        et_pay = (EditText) findViewById(R.id.et_pay);
        rl_cost = (RelativeLayout) findViewById(R.id.rl_cost);
        tv_line = (TextView) findViewById(R.id.tv_line);
        cookiepre_auth = CacheUtils.getString(this, "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(this, "cookiepre_saltkey");
        formhash = CacheUtils.getString(this, "formhash");
        ufieldStr = getIntent().getStringExtra("ufield");
        credit = getIntent().getStringExtra("credit");
        creditcost = getIntent().getStringExtra("creditcost");
        cost = getIntent().getStringExtra("cost");
        joinfieldStr = getIntent().getStringExtra("joinfield");
        settings = getIntent().getStringExtra("settings");
        fid = getIntent().getStringExtra("fid");
        tid = getIntent().getStringExtra("tid");
        pid = getIntent().getStringExtra("pid");
        specialJson = getIntent().getStringExtra("specialJson");

        isJoin = getIntent().getBooleanExtra("isJoin", false);
        infoItemView = (LinearLayout) findViewById(R.id.info_item_view);
        iv_chick = (ImageView) findViewById(R.id.iv_chick);
        iv_unchick = (ImageView) findViewById(R.id.iv_unchick);
        iv_chick.setSelected(true);
        iv_unchick.setSelected(false);
        mTitle = (TextView) findViewById(R.id.title);
        mOk = (TextView) findViewById(R.id.ok);
        mCancel = (TextView) findViewById(R.id.cancel);
        mContent = (EditText) findViewById(R.id.msg_content_edit);
        mProgress = (ProgressBar) findViewById(R.id.progressbar);
        findViewById(R.id.back).setOnClickListener(this);
        mOk.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        ll_chick = (LinearLayout) findViewById(R.id.ll_chick);
        ll_unchick = (LinearLayout) findViewById(R.id.ll_unchick);
        ll_chick.setOnClickListener(this);
        ll_unchick.setOnClickListener(this);
        tv_credit = (TextView) findViewById(R.id.tv_credit);
        mTitle.setText(!isJoin ? "我要参加" : "取消报名");
        if (isJoin == false && !TextUtils.isEmpty(specialJson)) {
            Log.e("TAG", "object=" + specialJson);
            com.alibaba.fastjson.JSONObject object = JSON.parseObject(specialJson);
            cost = object.getString("cost");//花费
            if (Integer.parseInt(credit) > 0) {
                creditcost = object.getString("creditcost");
                Log.e("TAG", "creditcost=" + creditcost);
                if (creditcost != null) {
                    tv_credit.setVisibility(View.VISIBLE);
                    tv_credit.setText("注意:参加此活动将扣除您" + creditcost);
                } else {
                    tv_credit.setVisibility(View.GONE);
                }

            } else {
                tv_credit.setVisibility(View.GONE);
            }
            if (Integer.parseInt(cost) > 0) {
                tv_line.setVisibility(View.VISIBLE);
                rl_cost.setVisibility(View.VISIBLE);
            } else {
                tv_line.setVisibility(View.GONE);
                rl_cost.setVisibility(View.GONE);
            }
            com.alibaba.fastjson.JSONObject settings = object.getJSONObject("settings");
            if (!object.get("joinfield").toString().equals("[]")) {

                joinfield = object.getJSONObject("joinfield");
                Log.e("TAG", "joinfield=" + joinfield.size());
            }
            com.alibaba.fastjson.JSONObject ufield = object.getJSONObject("ufield");
            JSONArray userfield = ufield.getJSONArray("userfield");
            integers = new ArrayList<>();
            textareaIntegers = new ArrayList<>();
            final List<String> strings = new ArrayList<>();
            List<String> userfiels = new ArrayList<>();
            if (null != settings) {
                for (Map.Entry<String, Object> entry : settings.entrySet()) {
                    strings.add(entry.getKey());
                }
            }

            Collections.sort(strings, Collator.getInstance(java.util.Locale.CHINA));
            if (null != userfield && userfield.size() > 0) {
                for (int i = 0; i < userfield.size(); i++) {
                    String s = (String) userfield.get(i);
                    userfiels.add(s);
                }
            }
            ArrayList<String> list = new ArrayList<>();
            list.clear();
            for (int i = 0; i < userfiels.size(); i++) {
                String s = userfiels.get(i);
                boolean contains = strings.contains(s);
                if (contains == false) {
                    list.add(s);
                }
            }
            for (int i = 0; i < list.size(); i++) {
                userfiels.remove(list.get(i));
            }
            arrayList = new ArrayList<>();
            for (int i = 0; i < userfiels.size(); i++) {
                arrayList.add(userfiels.get(i));
            }
            for (int i = 0; i < arrayList.size(); i++) {

//                    String key = (String) userfieldArray.get(i);
                final String key = arrayList.get(i);
                Log.e("TAG", "key=" + key + ";" + i);
//                    final JSONObject json = joinfield.getJSONObject(key);
                if (joinfield != null) {
                    json = joinfield.getJSONObject(key);
                    formtype = json.getString("formtype");
                    choices = json.getString("choices");
                }

                if (i == 24 || i == 25 || i == 26) {

                }
                if (formtype.equals("text")) {
                    ActivityJoinInfoItemView item = new ActivityJoinInfoItemView(getApplicationContext());
                    EditText info_value_edit = (EditText) item.findViewById(R.id.info_value_edit);
                    info_value_edit.setHint("请输入" + json.getString("title"));
                    info_value_edit.setHintTextColor(Color.parseColor("#bbbbbb"));
                    item.setData(json);
                    Log.e("TAG", "i=" + i + ";infoItemView.getChildCount()" + infoItemView.getChildCount());
                    integers.add(i);
                    infoItemView.addView(item, i);
                } else if (formtype.equals("select")) {
                    if (key.equals("birthday")) {
                        View inflate = View.inflate(JoinActivity.this, R.layout.birthday_seleter, null);
                        inflate.setBackgroundColor(Color.parseColor("#ffffff"));
                        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
                        TextView tv_seleter = (TextView) inflate.findViewById(R.id.tv_seleter);
                        final TextView tv_content = (TextView) inflate.findViewById(R.id.tv_content);
                        tv_title.setText(json.getString("title"));
                        tv_seleter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showtimePopupWindow(tv_content);
                            }
                        });
                        Log.e("TAG", "i=" + i + ";infoItemView.getChildCount()" + infoItemView.getChildCount());
                        infoItemView.addView(inflate, i);
                    } else if (key.equals("residecity")) {//居住地
                        View inflate = View.inflate(JoinActivity.this, R.layout.birthday_seleter, null);
                        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
                        TextView tv_seleter = (TextView) inflate.findViewById(R.id.tv_seleter);
                        tv_title.setText(json.getString("title"));
                        tv_residecity = (TextView) inflate.findViewById(tv_content);
                        tv_seleter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                imm.hideSoftInputFromWindow(infoItemView.getWindowToken(), 0); //强制隐藏键盘
                                CityPicker cityPicker = new CityPicker.Builder(JoinActivity.this).textSize(20)
                                        .titleTextColor("#000000")
                                        .backgroundPop(0xa0000000)
                                        .province("北京市")
                                        .city("北京市")
                                        .district("海淀区")
                                        .textColor(Color.parseColor("#000000"))
                                        .provinceCyclic(true)
                                        .cityCyclic(false)
                                        .districtCyclic(false)
                                        .visibleItemsCount(7)
                                        .itemPadding(10)
                                        .build();

                                cityPicker.show();
                                cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
                                    @Override
                                    public void onSelected(String... citySelected) {
                                        tv_residecity.setVisibility(View.VISIBLE);
                                        tv_residecity.setText(citySelected[0] + citySelected[1] + citySelected[2]);
                                        if (keys.contains("residecity")) {
                                            int i1 = keys.indexOf("residecity");
                                            values.remove(i1);
                                            values.add(i1, citySelected[0] + citySelected[1] + citySelected[2]);
                                        } else {
                                            keys.add("residecity");
                                            values.add(citySelected[0] + citySelected[1] + citySelected[2]);
                                        }
//                                        keys.add("residecity");
//                                        values.add(citySelected[0] + citySelected[1] + citySelected[2]);
                                    }

                                    @Override
                                    public void onCancel() {
                                        Toast.makeText(JoinActivity.this, "已取消", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                        Log.e("TAG", "i=" + i + ";infoItemView.getChildCount()" + infoItemView.getChildCount());
                        infoItemView.addView(inflate, i);
                    } else if (key.equals("education")) {
                        final ArrayList<String> education = new ArrayList<>();
                        String[] ss = choices.split("\n");
                        for (int i1 = 0; i1 < ss.length; i1++) {
                            String s = ss[i1];
                            education.add(s);
                        }
                        View inflate = View.inflate(JoinActivity.this, R.layout.birthday_seleter, null);
                        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
                        TextView tv_seleter = (TextView) inflate.findViewById(R.id.tv_seleter);
                        tv_education = (TextView) inflate.findViewById(tv_content);

                        tv_title.setText(json.getString("title"));
                        tv_seleter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent srxIntent = new Intent();
                                srxIntent.putExtra("list", (Serializable) education);
                                srxIntent.setClass(JoinActivity.this, SelectActivitySexActivity.class);
                                startActivityForResult(srxIntent, EDUCATION_REQUEST_CODE);
                                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            }
                        });
                        Log.e("TAG", "i=" + i + ";infoItemView.getChildCount()" + infoItemView.getChildCount());
                        infoItemView.addView(inflate, i);
                    } else if (key.equals("gender")) {
                        final ArrayList<String> genders = new ArrayList<>();
                        genders.add("保密");
                        genders.add("男");
                        genders.add("女");
                        View inflate = View.inflate(JoinActivity.this, R.layout.birthday_seleter, null);
                        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
                        TextView tv_seleter = (TextView) inflate.findViewById(R.id.tv_seleter);
                        tv_title.setText(json.getString("title"));
                        tv_gender = (TextView) inflate.findViewById(R.id.tv_content);
                        tv_seleter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent srxIntent = new Intent();
                                srxIntent.putExtra("list", (Serializable) genders);
                                srxIntent.setClass(JoinActivity.this, SelectActivitySexActivity.class);
                                startActivityForResult(srxIntent, SEX_REQUEST_CODE);
                                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            }
                        });
                        Log.e("TAG", "i=" + i + ";infoItemView.getChildCount()" + infoItemView.getChildCount());
                        infoItemView.addView(inflate, i);
                    } else if (key.equals("birthcity")) {//出生地
                        View inflate = View.inflate(JoinActivity.this, R.layout.birthday_seleter, null);
                        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
                        TextView tv_seleter = (TextView) inflate.findViewById(R.id.tv_seleter);
                        tv_title.setText(json.getString("title"));
                        tv_birthcityt = (TextView) inflate.findViewById(tv_content);
                        tv_seleter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                imm.hideSoftInputFromWindow(infoItemView.getWindowToken(), 0); //强制隐藏键盘
                                CityPicker cityPicker = new CityPicker.Builder(JoinActivity.this).textSize(20)
                                        .titleTextColor("#000000")
                                        .backgroundPop(0xa0000000)
                                        .province("北京市")
                                        .city("北京市")
                                        .district("海淀区")
                                        .textColor(Color.parseColor("#000000"))
                                        .provinceCyclic(true)
                                        .cityCyclic(false)
                                        .districtCyclic(false)
                                        .visibleItemsCount(7)
                                        .itemPadding(10)
                                        .build();

                                cityPicker.show();
                                cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
                                    @Override
                                    public void onSelected(String... citySelected) {
                                        tv_birthcityt.setVisibility(View.VISIBLE);
                                        tv_birthcityt.setText(citySelected[0] + citySelected[1] + citySelected[2]);
                                        if (keys.contains("birthcity")) {
                                            int i1 = keys.indexOf("birthcity");
                                            values.remove(i1);
                                            values.add(i1, citySelected[0] + citySelected[1] + citySelected[2]);
                                        } else {
                                            keys.add("birthcity");
                                            values.add(citySelected[0] + citySelected[1] + citySelected[2]);
                                        }
//                                        keys.add("birthcity");
//                                        values.add(citySelected[0] + citySelected[1] + citySelected[2]);
                                    }

                                    @Override
                                    public void onCancel() {
                                        Toast.makeText(JoinActivity.this, "已取消", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                        Log.e("TAG", "i=" + i + ";infoItemView.getChildCount()" + infoItemView.getChildCount());
                        infoItemView.addView(inflate, i);
                    } else if (key.equals("birthdist")) {//出生县
                        View inflate = View.inflate(JoinActivity.this, R.layout.birthday_seleter, null);
                        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
                        TextView tv_seleter = (TextView) inflate.findViewById(R.id.tv_seleter);
                        tv_title.setText(json.getString("title"));
                        tv_birthdist = (TextView) inflate.findViewById(tv_content);

                        tv_seleter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                imm.hideSoftInputFromWindow(infoItemView.getWindowToken(), 0); //强制隐藏键盘
                                CityPicker cityPicker = new CityPicker.Builder(JoinActivity.this).textSize(20)
                                        .titleTextColor("#000000")
                                        .backgroundPop(0xa0000000)
                                        .province("北京市")
                                        .city("北京市")
                                        .district("海淀区")
                                        .textColor(Color.parseColor("#000000"))
                                        .provinceCyclic(true)
                                        .cityCyclic(false)
                                        .districtCyclic(false)
                                        .visibleItemsCount(7)
                                        .itemPadding(10)
                                        .build();

                                cityPicker.show();
                                cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
                                    @Override
                                    public void onSelected(String... citySelected) {
                                        tv_birthdist.setVisibility(View.VISIBLE);
                                        tv_birthdist.setText(citySelected[0] + citySelected[1] + citySelected[2]);
                                        if (keys.contains("birthdist")) {
                                            int i1 = keys.indexOf("birthdist");
                                            values.remove(i1);
                                            values.add(i1, citySelected[0] + citySelected[1] + citySelected[2]);
                                        } else {
                                            keys.add("birthdist");
                                            values.add(citySelected[0] + citySelected[1] + citySelected[2]);
                                        }
//                                        keys.add("birthdist");
//                                        values.add("citySelected[0] + citySelected[1] + citySelected[2]");
                                    }

                                    @Override
                                    public void onCancel() {
                                        Toast.makeText(JoinActivity.this, "已取消", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                        Log.e("TAG", "i=" + i + ";infoItemView.getChildCount()" + infoItemView.getChildCount());
                        infoItemView.addView(inflate, i);
                    } else if (key.equals("idcardtype")) {//证件类型
                        final ArrayList<String> idcardtype = new ArrayList<>();
                        String[] ss = choices.split("\n");
                        for (int i1 = 0; i1 < ss.length; i1++) {
                            String s = ss[i1];
                            idcardtype.add(s);
                        }
                        View inflate = View.inflate(JoinActivity.this, R.layout.birthday_seleter, null);
                        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
                        TextView tv_seleter = (TextView) inflate.findViewById(R.id.tv_seleter);
                        tv_idcardtype = (TextView) inflate.findViewById(tv_content);

                        tv_title.setText(json.getString("title"));
                        tv_seleter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent srxIntent = new Intent();
//                                    srxIntent.putExtra("list", "gender");
                                srxIntent.putExtra("list", (Serializable) idcardtype);
//                                    srxIntent.putStringArrayListExtra("list", bloodtype);
                                srxIntent.setClass(JoinActivity.this, SelectActivitySexActivity.class);
                                startActivityForResult(srxIntent, IDCARDTYPE_REQUEST_CODE);
                                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            }
                        });
                        Log.e("TAG", "i=" + i + ";infoItemView.getChildCount()" + infoItemView.getChildCount());
                        infoItemView.addView(inflate, i);
                    } else if (key.equals("bloodtype")) {//血型
                        final ArrayList<String> bloodtype = new ArrayList<>();
                        String[] ss = choices.split("\n");
                        for (int i1 = 0; i1 < ss.length; i1++) {
                            String s = ss[i1];
                            bloodtype.add(s);
                        }
                        View inflate = View.inflate(JoinActivity.this, R.layout.birthday_seleter, null);
                        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
                        tv_bloodtype = (TextView) inflate.findViewById(tv_content);
                        TextView tv_seleter = (TextView) inflate.findViewById(R.id.tv_seleter);
                        tv_title.setText(json.getString("title"));
                        tv_seleter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent srxIntent = new Intent();
//                                    srxIntent.putExtra("list", "gender");
                                srxIntent.putExtra("list", (Serializable) bloodtype);
//                                    srxIntent.putStringArrayListExtra("list", bloodtype);
                                srxIntent.setClass(JoinActivity.this, SelectActivitySexActivity.class);
                                startActivityForResult(srxIntent, BLOODTYPE_REQUEST_CODE);
                                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            }
                        });
                        Log.e("TAG", "i=" + i + ";infoItemView.getChildCount()" + infoItemView.getChildCount());
                        infoItemView.addView(inflate, i);
                    } else if (key.contains("field")) {
                        final ArrayList<String> bloodtype = new ArrayList<>();
                        String[] ss = choices.split("\n");
                        for (int i1 = 0; i1 < ss.length; i1++) {
                            String s = ss[i1];
                            bloodtype.add(s);
                        }
                        View inflate = View.inflate(JoinActivity.this, R.layout.birthday_seleter, null);
                        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
                        tv_field = (TextView) inflate.findViewById(tv_content);
                        TextView tv_seleter = (TextView) inflate.findViewById(R.id.tv_seleter);
                        tv_title.setText(json.getString("title"));
                        tv_seleter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent srxIntent = new Intent();
//                                    srxIntent.putExtra("list", "gender");
                                srxIntent.putExtra("list", (Serializable) bloodtype);
//                                    srxIntent.putStringArrayListExtra("list", bloodtype);
                                srxIntent.setClass(JoinActivity.this, SelectActivitySexActivity.class);
                                startActivityForResult(srxIntent, FIELD_REQUEST_CODE);
                                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            }
                        });
                        Log.e("TAG", "i=" + i + ";infoItemView.getChildCount()" + infoItemView.getChildCount());
                        infoItemView.addView(inflate, i);
                    } else {
                        ActivityJoinInfoItemView item = new ActivityJoinInfoItemView(getApplicationContext());
                        EditText info_value_edit = (EditText) item.findViewById(R.id.info_value_edit);
                        info_value_edit.setHint("请输入" + json.getString("title"));
                        info_value_edit.setHintTextColor(Color.parseColor("#bbbbbb"));
                        item.setData(json);
                        Log.e("TAG", "i=" + i + ";infoItemView.getChildCount()" + infoItemView.getChildCount());
                        infoItemView.addView(item, i);
                        integers.add(i);

                    }
                } else if (formtype.equals("radio")) {
                    final ArrayList<String> bloodtype = new ArrayList<>();
                    String[] ss = choices.split("\n");
                    for (int i1 = 0; i1 < ss.length; i1++) {
                        String s = ss[i1];
                        bloodtype.add(s);
                    }
                    View inflate = View.inflate(JoinActivity.this, R.layout.birthday_seleter, null);
                    TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
                    tv_radio = (TextView) inflate.findViewById(tv_content);
                    TextView tv_seleter = (TextView) inflate.findViewById(R.id.tv_seleter);
                    tv_title.setText(json.getString("title"));
                    tv_seleter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent srxIntent = new Intent();
                            srxIntent.putExtra("list", (Serializable) bloodtype);
                            srxIntent.setClass(JoinActivity.this, SelectActivitySexActivity.class);
                            startActivityForResult(srxIntent, RADIO_REQUEST_CODE);
                            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                        }
                    });
                    Log.e("TAG", "i=" + i + ";infoItemView.getChildCount()" + infoItemView.getChildCount());
                    infoItemView.addView(inflate, i);
                } else if (formtype.equals("list") || formtype.equals("checkbox")) {
                    final ArrayList<String> bloodtype = new ArrayList<>();
                    String[] ss = choices.split("\n");
                    for (int i1 = 0; i1 < ss.length; i1++) {
                        String s = ss[i1];
                        bloodtype.add(s);
                    }
                    CustomListView customListView = new CustomListView(JoinActivity.this);
                    customListView.setData(bloodtype, json.getString("title"), key);
                    listData.add(i);
                    Log.e("TAG", "i=" + i + ";infoItemView.getChildCount()" + infoItemView.getChildCount());
                    infoItemView.addView(customListView, i);

                } else if (formtype.equals("checkbox")) {
                    final ArrayList<String> bloodtype = new ArrayList<>();
                    String[] ss = choices.split("\n");
                    for (int i1 = 0; i1 < ss.length; i1++) {
                        String s = ss[i1];
                        bloodtype.add(s);
                    }
                    View inflate = View.inflate(JoinActivity.this, R.layout.birthday_seleter, null);
                    TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
//                    tv_radio = (TextView) inflate.findViewById(R.id.tv_content);
                    TextView tv_seleter = (TextView) inflate.findViewById(R.id.tv_seleter);
                    tv_title.setText(json.getString("title"));
                    tv_seleter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent srxIntent = new Intent();
                            srxIntent.putExtra("list", (Serializable) bloodtype);
                            srxIntent.setClass(JoinActivity.this, ListSeleterDialog.class);
                            startActivityForResult(srxIntent, RADIO_REQUEST_CODE);
                            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                        }
                    });
                    Log.e("TAG", "i=" + i + ";infoItemView.getChildCount()" + infoItemView.getChildCount());
                    infoItemView.addView(inflate, i);
                } else if (formtype.equals("textarea")) {
                    ActivityJoinTextareaItemView activityJoinTextareaItemView = new ActivityJoinTextareaItemView(getApplicationContext());
                    TextView tv_title = (TextView) activityJoinTextareaItemView.findViewById(R.id.info_key_text);
                    EditText info_value_edit = (EditText) activityJoinTextareaItemView.findViewById(R.id.info_value_edit);
//                    tv_title.setText(json.getString("title"));
                    Log.e("TAG", "i=" + i + ";infoItemView.getChildCount()" + infoItemView.getChildCount());
                    activityJoinTextareaItemView.setData(json);
                    textareaIntegers.add(i);
                    infoItemView.addView(activityJoinTextareaItemView, i);
                } else {
                    ActivityJoinInfoItemView item = new ActivityJoinInfoItemView(getApplicationContext());
                    EditText info_value_edit = (EditText) item.findViewById(R.id.info_value_edit);
                    info_value_edit.setHint("请输入" + json.getString("title"));
                    info_value_edit.setHintTextColor(Color.parseColor("#bbbbbb"));
                    item.setData(json);
                    infoItemView.addView(item, i);
                    integers.add(i);
                    Log.e("TAG", "i=" + i + ";infoItemView.getChildCount()" + infoItemView.getChildCount());
                }


            }
        }
    }

    private void showtimePopupWindow(final TextView tv_content) {
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = manager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        View menuView = LayoutInflater.from(this).inflate(R.layout.show_popup_window, null);
        final PopupWindow mPopupWindow = new PopupWindow(menuView, (int) (width * 0.9),
                ActionBar.LayoutParams.WRAP_CONTENT);
        WheelView mins = (WheelView) menuView.findViewById(R.id.mins);
        WheelView hour = (WheelView) menuView.findViewById(R.id.hour);
        DashedLineView line_hour = (DashedLineView) menuView.findViewById(R.id.line_hour);
        DashedLineView line_mins = (DashedLineView) menuView.findViewById(R.id.line_mins);
        mins.setVisibility(View.GONE);
        hour.setVisibility(View.GONE);
        line_hour.setVisibility(View.GONE);
        line_mins.setVisibility(View.GONE);
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
                String beginTime = wheelMainDate.getTime().toString();
                tv_content.setVisibility(View.VISIBLE);
                String s = DateUtils.formateStringH(beginTime, DateUtils.yyyyMMddHHmm);
                String substring = s.substring(0, s.length() - 5);
                tv_content.setText(substring);
                mPopupWindow.dismiss();
                backgroundAlpha(1f);
                if (keys.contains("birthday")) {
                    int i1 = keys.indexOf("birthday");
                    values.remove(i1);
                    values.add(i1, substring);
                } else {
                    keys.add("birthday");
                    values.add(substring);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String sex = data.getStringExtra("sex");
            if (requestCode == SEX_REQUEST_CODE) {
                tv_gender.setVisibility(View.VISIBLE);
                tv_gender.setText(sex);
                int i = 0;
                if (sex.equals("男")) {
                    i = 1;
                } else if (sex.equals("不限")) {
                    i = 2;
                }
                if (keys.contains("gender")) {
                    int i1 = keys.indexOf("gender");
                    values.remove(i1);
                    values.add(i1, sex);
                } else {
                    keys.add("gender");
                    values.add(i + "");
                }

            } else if (requestCode == BLOODTYPE_REQUEST_CODE) {
                tv_bloodtype.setVisibility(View.VISIBLE);
                tv_bloodtype.setText(sex);
                if (keys.contains("bloodtype")) {
                    int i1 = keys.indexOf("bloodtype");
                    values.remove(i1);
                    values.add(i1, sex);
                } else {
                    keys.add("bloodtype");
                    values.add(sex);
                }

            } else if (requestCode == EDUCATION_REQUEST_CODE) {
                tv_education.setVisibility(View.VISIBLE);
                tv_education.setText(sex);
                if (keys.contains("education")) {
                    int i1 = keys.indexOf("education");
                    values.remove(i1);
                    values.add(i1, sex);
                } else {
                    keys.add("education");
                    values.add(sex);
                }

            } else if (requestCode == IDCARDTYPE_REQUEST_CODE) {
                tv_idcardtype.setVisibility(View.VISIBLE);
                tv_idcardtype.setText(sex);
                if (keys.contains("idcardtype")) {
                    int i1 = keys.indexOf("idcardtype");
                    values.remove(i1);
                    values.add(i1, sex);
                } else {
                    keys.add("idcardtype");
                    values.add(sex);
                }
            } else if (requestCode == SELECT_REQUEST_CODE) {
                tv_select.setVisibility(View.VISIBLE);
                tv_select.setText(sex);
            } else if (requestCode == RADIO_REQUEST_CODE) {
                tv_radio.setVisibility(View.VISIBLE);
                tv_radio.setText(sex);
                if (keys.contains("field4")) {
                    int i1 = keys.indexOf("field4");
                    values.remove(i1);
                    values.add(i1, sex);
                } else {
                    keys.add("field4");
                    values.add(sex);
                }
            } else if (requestCode == FIELD_REQUEST_CODE) {
                tv_field.setVisibility(View.VISIBLE);
                tv_field.setText(sex);
                if (keys.contains("field5")) {
                    int i1 = keys.indexOf("field5");
                    values.remove(i1);
                    values.add(i1, sex);
                } else {
                    keys.add("field5");
                    values.add(sex);
                }
            }
        }
    }

    private boolean chick = true;
    private boolean unchick = false;

    @Override
    public void onClick(View v) {
        imm.hideSoftInputFromWindow(infoItemView.getWindowToken(), 0); //强制隐藏键盘
        switch (v.getId()) {
            case R.id.ll_chick:
                chick = true;
                unchick = false;
                iv_chick.setImageResource(R.drawable.checked);
                iv_unchick.setImageResource(R.drawable.unchecked);
                break;
            case R.id.ll_unchick:
                chick = false;
                unchick = true;
                iv_unchick.setImageResource(R.drawable.checked);
                iv_chick.setImageResource(R.drawable.unchecked);

                break;
            case R.id.back:
                imm.hideSoftInputFromWindow(infoItemView.getWindowToken(), 0); //强制隐藏键盘
                finish();
                break;
            case R.id.ok:
                imm.hideSoftInputFromWindow(infoItemView.getWindowToken(), 0); //强制隐藏键盘
                message = mContent.getText().toString().trim();
                if (isJoin) {
                    mProgress.setVisibility(View.VISIBLE);
                    if (!message.equals("")) {
                        FormEncodingBuilder builder = new FormEncodingBuilder();
                        builder.add("formhash", CacheUtils.getString(this, "formhash"));
                        builder.add("fid", fid);
                        builder.add("tid", tid);
                        builder.add("pid", pid);
                        RequestBody formBody = builder.build();
                        Request request = new Request.Builder()
                                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";")
                                .url((AppNetConfig.BASEURL + "?module=activityapplies&activitycancel=yes&tid=" + tid + "&version=5" + "&fid=" + fid + "&pid=" + pid))
                                .post(formBody).build();
                        RedNet.mHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                mHandler.sendEmptyMessage(0);
                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
                                String result = response.body().string();
                                Log.e("TAG", "result =" + result);
                                JSONObject json = null;
                                try {
                                    json = new JSONObject(result);
                                    JSONObject variablesJson = json.optJSONObject("Variables");
                                    if (variablesJson != null) {
                                        RedNetApp.setFormHash(variablesJson.optString("formhash"));
                                    }
//                                    String message = variablesJson.optString("message");
                                    JSONObject message = json.getJSONObject("Message");
                                    if (null != message) {
                                        final String messagestr = message.optString("messagestr");
                                        String messageval = message.getString("messageval");
                                        if (messageval.contains("activity_cancel_success")) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mProgress.setVisibility(View.GONE);
                                                    Toast.makeText(JoinActivity.this, messagestr, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            Message msg = mHandler.obtainMessage();
                                            msg.what = 1;
                                            msg.obj = false;
                                            mHandler.sendMessage(msg);
                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mProgress.setVisibility(View.GONE);
                                                    Toast.makeText(JoinActivity.this, messagestr, Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                            });
                                        }
                                    } else {
                                        mHandler.sendEmptyMessage(0);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    mHandler.sendEmptyMessage(0);
                                }
                            }

                        });
                    } else {
                        mHandler.sendEmptyMessage(2);
                    }
                } else {
                    data = getItemData();
                    gettextareaIntegersData();
                    getListData();
                    if (arrayList != null && arrayList.size() != keys.size()) {
                        Log.e("TAG", "arrayList.size()=" + arrayList.size());
                        Log.e("TAG", "keys.size()=" + keys.size());
                        if (values.size() > 0) {
                            for (int i = 0; i < values.size(); i++) {
                                Log.e("TAG", "keys.get(i)=" + keys.get(i) + ";values.get(i)=" + values.get(i));
                            }
                        }
                        mHandler.sendEmptyMessage(2);
                        return;
                    }
                    if (checkData(data) && !message.equals("")) {
                        mProgress.setVisibility(View.VISIBLE);
                        FormEncodingBuilder builder = new FormEncodingBuilder();
                        builder.add("formhash", CacheUtils.getString(this, "formhash"));
                        builder.add("apply[message]", message);
                        builder.add("tid", tid);
                        Log.e("TAG", "values.size()=" + values.size());
                        if (values.size() > 0) {
                            for (int i = 0; i < values.size(); i++) {
                                builder.add("" + keys.get(i), values.get(i));
                                Log.e("TAG", "keys.get(i)=" + i + keys.get(i) + ";values.get(i)=" + values.get(i));
                            }
                        }
                        if (Integer.parseInt(cost) > 0) {
                            if (chick == true) {
                                builder.add("payment", "0");
                            } else {
                                builder.add("payment", "1");
                                String s = et_pay.getText().toString();
                                if (null == s && TextUtils.isEmpty(s)) {
                                    Toast.makeText(JoinActivity.this, "请填支付的费用", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    builder.add("payvalue", s);
                                }

                            }
                        }
                        if (Integer.parseInt(credit) > 0) {
                            if (creditcost == null) {
                                creditcost = "";
                            }
                            builder.add("creditcost", creditcost);
                        }
                        RequestBody formBody = builder.build();
                        Log.e("TAG", "参加活动的url=" + AppNetConfig.JOINACTIVITY + "&fid=" + fid + "&tid=" + tid + "&pid=" + pid);
                        Request request = new Request.Builder()
                                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";")
                                .url(AppNetConfig.JOINACTIVITY + "&fid=" + fid + "&tid=" + tid + "&pid=" + pid)
                                .post(formBody).build();
                        RedNet.mHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                mHandler.sendEmptyMessage(0);
                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
//                                Log.e("TAG", "参加请求成功=" + response.body().string());
                                Log.e("TAG", "response.request().body().toString()=" + response.request().body().toString());
                                String result = response.body().string();
                                JSONObject json = null;
                                try {
                                    json = new JSONObject(result);
                                    Log.e("TAG", "参加请求成功=" + json.toString());
                                    JSONObject variablesJson = json.optJSONObject("Variables");
                                    if (variablesJson != null) {
                                        RedNetApp.setFormHash(variablesJson.optString("formhash"));
                                    }
                                    JSONObject msgJson = json.optJSONObject("Message");
                                    if (msgJson != null) {
                                        final String messagestr = msgJson.optString("messagestr");
                                        String messageval = msgJson.getString("messageval");
                                        if (messageval.contains("activity_completion")) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mProgress.setVisibility(View.GONE);
                                                    Toast.makeText(JoinActivity.this, messagestr, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            Message msg = mHandler.obtainMessage();
                                            msg.what = 1;
                                            msg.obj = true;
                                            mHandler.sendMessage(msg);
                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mProgress.setVisibility(View.GONE);
                                                    Toast.makeText(JoinActivity.this, messagestr, Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                            });
                                        }

                                    } else {
                                        mHandler.sendEmptyMessage(0);
                                    }
                                } catch (JSONException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(JoinActivity.this, "请求异常", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    e.printStackTrace();
                                }
                            }

                        });
                    } else {
                        mHandler.sendEmptyMessage(2);
                    }
                }
                break;
            case R.id.cancel:
                imm.hideSoftInputFromWindow(infoItemView.getWindowToken(), 0); //强制隐藏键盘
                finish();
                break;
            default:
                break;
        }

    }

    private void gettextareaIntegersData() {
        for (int i = 0; i < textareaIntegers.size(); i++) {
            Log.e("TAG", "integers.size()=" + integers.size());
            ActivityJoinTextareaItemView item = (ActivityJoinTextareaItemView) infoItemView.getChildAt(textareaIntegers.get(i));
            HashMap<String, String> itemData = item.getValue();
            if (keys.contains(itemData.get("fieldid"))) {
                int i1 = keys.indexOf(itemData.get("fieldid"));
                values.remove(i1);
                values.add(i1, itemData.get("value"));
            } else {
                keys.add(itemData.get("fieldid"));
                values.add(itemData.get("value"));
            }
//            keys.add(itemData.get("fieldid"));
//            values.add(itemData.get("value"));
        }
    }

    private void getListData() {
        for (int i = 0; i < listData.size(); i++) {
            CustomListView castomListView = (CustomListView) infoItemView.getChildAt(listData.get(i));
            HashMap<String, String> listValue = castomListView.getListValue();
            if (keys.contains(listValue.get("fieldid"))) {
                int i1 = keys.indexOf(listValue.get("fieldid"));
                values.remove(i1);
                values.add(i1, listValue.get("value"));
            } else {
                keys.add(listValue.get("fieldid"));
                values.add(listValue.get("value"));
            }
//            keys.add(listValue.get("fieldid"));
//            values.add(listValue.get("value"));
        }
    }

    private HashMap<String, String> getItemData() {
        HashMap<String, String> data = new HashMap<>();
        for (int i = 0; i < integers.size(); i++) {
            ActivityJoinInfoItemView item = (ActivityJoinInfoItemView) infoItemView.getChildAt(integers.get(i));
            HashMap<String, String> itemData = item.getValue();
            if (keys.contains(itemData.get("fieldid"))) {
                int i1 = keys.indexOf(itemData.get("fieldid"));
                values.remove(i1);
                values.add(i1, itemData.get("value"));
            } else {
                keys.add(itemData.get("fieldid"));
                values.add(itemData.get("value"));
            }
//            keys.add(itemData.get("fieldid"));
//            values.add(itemData.get("value"));
            data.putAll(itemData);
        }
        return data;
    }

    private boolean checkData(HashMap<String, String> itemData) {
        Iterator iter = itemData.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            if (val == null || val.equals("")) {
                return false;
            }
        }
        return true;

    }

    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
}
