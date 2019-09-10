package cn.tencent.DiscuzMob.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import cn.tencent.DiscuzMob.ui.dialog.SelectActivitySexActivity;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/8/9.
 */

public class ActivityJoinSeleterItemView extends LinearLayout implements View.OnClickListener {
    private final TextView tv_title;
    private final TextView tv_content;
    private final TextView tv_seleter;
    private Activity activity;
    private int EDUCATION_REQUEST_CODE = 10;

    public ActivityJoinSeleterItemView(Context context) {
        super(context);
        activity = (Activity) context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.birthday_seleter, this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_seleter = (TextView) findViewById(R.id.tv_seleter);
        tv_seleter.setOnClickListener(this);
    }

    private ArrayList<String> strings;

    public void setData(JSONObject json, String choices) {
        tv_title.setText(json.optString("title"));
        tv_content.setTag(json.optString("fieldid"));
        String[] ss = choices.split("\n");
        strings = new ArrayList<>();
        for (int i1 = 0; i1 < ss.length; i1++) {
            String s = ss[i1];
            strings.add(s);
        }
    }

    public HashMap<String, String> getValue() {
        HashMap<String, String> result = new HashMap<>();
        result.put("fieldid", tv_title.getTag().toString());
        result.put("value", tv_seleter.getText().toString().trim());
        return result;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_seleter) {
            Intent srxIntent = new Intent();
            srxIntent.putExtra("list", (Serializable) strings);
            srxIntent.setClass(activity, SelectActivitySexActivity.class);
            activity.startActivityForResult(srxIntent, EDUCATION_REQUEST_CODE);
            activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
        }
    }
}
