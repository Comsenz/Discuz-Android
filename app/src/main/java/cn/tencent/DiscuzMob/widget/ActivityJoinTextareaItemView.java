package cn.tencent.DiscuzMob.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import cn.tencent.DiscuzMob.R;


/**
 * Created by cg on 2017/8/24.
 */

public class ActivityJoinTextareaItemView extends LinearLayout {
    private TextView mKeyView;
    private EditText mValueView;

    public ActivityJoinTextareaItemView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.textarea_value_edit, this);
        mKeyView = (TextView) findViewById(R.id.info_key_text);
        mValueView = (EditText) findViewById(R.id.info_value_edit);
    }

    public void setData(com.alibaba.fastjson.JSONObject json) {
        mKeyView.setText(json.getString("title"));
        mValueView.setTag(json.getString("fieldid"));
    }

    public HashMap<String, String> getValue() {
        HashMap<String, String> result = new HashMap<>();
        result.put("fieldid", mValueView.getTag().toString());
        result.put("value", mValueView.getText().toString().trim());
        return result;
    }

}
