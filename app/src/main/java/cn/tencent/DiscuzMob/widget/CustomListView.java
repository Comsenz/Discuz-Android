package cn.tencent.DiscuzMob.widget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.tencent.DiscuzMob.utils.DensityUtil;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/8/15.
 */

public class CustomListView extends LinearLayout {
    private TextView tv_title;
    private IntricateLayout intricatelayout;
    private Activity activity;
    private List<String> strings = new ArrayList<>();

    public CustomListView(Context context) {
        super(context);
        this.activity = (Activity) context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.customlis_seleter, this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        intricatelayout = (IntricateLayout) findViewById(R.id.intricatelayout);
    }

    public void setData(final ArrayList<String> bloodtype, String title, String key) {
        tv_title.setText(title);
        intricatelayout.setTag(key);
//        mKeyView.setText(json.getString("title"));
//        mValueView.setTag(json.getString("fieldid"));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.rightMargin = DensityUtil.dip2px(activity, 5);
        params.bottomMargin = DensityUtil.dip2px(activity, 12);
        params.leftMargin = DensityUtil.dip2px(activity, 5);
        for (int i = 0; i < bloodtype.size(); i++) {
            View inflate = View.inflate(activity, R.layout.activityfieldvalues_item, null);
            CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.name_check);
            checkBox.setLayoutParams(params);
            String name = bloodtype.get(i);
            checkBox.setText(name);
            intricatelayout.addView(inflate);

        }
        intricatelayout.setOnIntricateClickListener(new IntricateLayout.OnIntricateClickListener() {
            @Override
            public void onIntricateClick(int position) {
                for (int i = 0, count = intricatelayout.getChildCount(); i < count; i++) {
                    intricatelayout.getChildAt(i).setSelected(i == position);

                }
                CheckBox childAt = (CheckBox) intricatelayout.getChildAt(position);
                boolean checked = childAt.isChecked();
                if (checked == true) {
                    strings.add(bloodtype.get(position));
//                    Log.e("TAG", "activityfieldKeys.get(position)=" + activityfieldKeys.get(position));
                } else {
                    if (strings.contains(bloodtype.get(position))) {
                        strings.remove(bloodtype.get(position));
                    }
                }
//                boolean selected = childAt.isSelected();
//                Log.e("TAG", "selected=" + checked + position);
//                id = threadtypes.get(position).getId();
            }
        });
    }

    public HashMap<String, String> getListValue() {
        HashMap<String, String> result = new HashMap<>();
        result.put("fieldid", intricatelayout.getTag().toString());
        result.put("value", strings.toString());
        return result;
    }
}
