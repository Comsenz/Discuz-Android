package cn.tencent.DiscuzMob.ui.dialog;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.utils.DensityUtil;
import cn.tencent.DiscuzMob.widget.IntricateLayout;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/8/15.
 */

public class ListSeleterDialog extends FragmentActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private IntricateLayout intricateLayout;
    private List<String> strings = new ArrayList<>();
    private ImageView iv_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_seleter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        initView();
        initListener();
    }

    private void initView() {
//        mList = (ListView) findViewById(R.id.type_list);
//        mAdapter = new ActivityCateAdapter();
//        mAdapter.setData(getData());
//        mList.setAdapter(mAdapter);
//        mList.setOnItemClickListener(this);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        iv_close.setOnClickListener(this);
        final List<String> bloodtype = (List<String>) getIntent().getSerializableExtra("list");

        intricateLayout = (IntricateLayout) findViewById(R.id.intricatelayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.rightMargin = DensityUtil.dip2px(this, 5);
        params.bottomMargin = DensityUtil.dip2px(this, 12);
        params.leftMargin = DensityUtil.dip2px(this, 5);
        for (int i = 0; i < bloodtype.size(); i++) {
            View inflate = View.inflate(this, R.layout.activityfieldvalues_item, null);
            CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.name_check);
            checkBox.setLayoutParams(params);
            String name = bloodtype.get(i);
            checkBox.setText(name);
            intricateLayout.addView(inflate);

        }
        intricateLayout.setOnIntricateClickListener(new IntricateLayout.OnIntricateClickListener() {
            @Override
            public void onIntricateClick(int position) {
                for (int i = 0, count = intricateLayout.getChildCount(); i < count; i++) {
                    intricateLayout.getChildAt(i).setSelected(i == position);

                }
                CheckBox childAt = (CheckBox) intricateLayout.getChildAt(position);
                boolean checked = childAt.isChecked();
                if (checked == true) {
                    strings.add(bloodtype.get(position));
//                    Log.e("TAG", "activityfieldKeys.get(position)=" + activityfieldKeys.get(position));
                } else {
                    if (strings.contains(bloodtype.get(position))) {
                        strings.remove(bloodtype.get(position));
                    }
                }
                boolean selected = childAt.isSelected();
                Log.e("TAG", "selected=" + checked + position);
//                id = threadtypes.get(position).getId();
            }
        });
    }


    private void initListener() {
        //除可见界面外点击退出
//        findViewById(R.id.exit_select_layout).setOnClickListener(this);
    }


    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent0 = new Intent();
//        intent0.putExtra("sex", data.get(position));
//        setResult(RESULT_OK, intent0);
//        finish();
//        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
    }
}
