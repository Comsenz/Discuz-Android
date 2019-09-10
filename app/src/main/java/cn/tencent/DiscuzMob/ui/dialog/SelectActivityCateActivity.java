package cn.tencent.DiscuzMob.ui.dialog;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.ui.adapter.ActivityCateAdapter;
import cn.tencent.DiscuzMob.R;

/**
 * Created by kurt on 15-7-21.
 */
public class SelectActivityCateActivity extends FragmentActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView mList;
    private ActivityCateAdapter mAdapter;
    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_threadtype);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        initView();
        initListener();
    }

    private void initView() {
        mList = (ListView) findViewById(R.id.type_list);
        mAdapter = new ActivityCateAdapter();
        mAdapter.setData(getData());
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(this);
    }

    private ArrayList<String> getData() {
        List<String> activityType = (List<String>) getIntent().getSerializableExtra("activityType");
        data = new ArrayList<>();
        data.add("自定义");
        for (int i = 0; i < activityType.size(); i++) {
            data.add(activityType.get(i));
        }
        return data;
    }

    private void initListener() {
        //除可见界面外点击退出
        findViewById(R.id.exit_select_layout).setOnClickListener(this);
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
            case R.id.exit_select_layout:
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
        Intent intent0 = new Intent();
        intent0.putExtra("cate", data.get(position));
        setResult(RESULT_OK, intent0);
        finish();
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
    }
}
