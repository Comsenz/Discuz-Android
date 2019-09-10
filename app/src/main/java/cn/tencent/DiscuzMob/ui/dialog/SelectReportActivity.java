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
import android.widget.RelativeLayout;

import java.util.HashMap;

import cn.tencent.DiscuzMob.R;


/**
 * Created by kurt on 15-5-27.
 */
public class SelectReportActivity extends FragmentActivity implements View.OnClickListener{
    private RelativeLayout mSelectZeroView, mSelectOneView,mSelectTwoView,mSelectThreeView,mSelectFourView;

    private HashMap<Integer,String> data = new HashMap<>();
    private String rid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_report);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
            setTranslucentStatus(true);
        }
        rid = getIntent().getStringExtra("rid");
        initData();
        initView();
        initListener();
    }

    private void initData() {
        data.clear();
        data.put(0, "广告垃圾");
        data.put(1,"违规内容");
        data.put(2,"恶意灌水");
        data.put(3,"重复发帖");
    }

    private void initView() {
        mSelectZeroView = (RelativeLayout) findViewById(R.id.select_0_view);
        mSelectFourView = (RelativeLayout) findViewById(R.id.select_4_view);
        mSelectOneView = (RelativeLayout) findViewById(R.id.select_1_view);
        mSelectTwoView = (RelativeLayout) findViewById(R.id.select_2_view);
        mSelectThreeView = (RelativeLayout) findViewById(R.id.select_3_view);
    }

    private void initListener() {
        mSelectFourView.setOnClickListener(this);
        mSelectZeroView.setOnClickListener(this);
        mSelectOneView.setOnClickListener(this);
        mSelectTwoView.setOnClickListener(this);
        mSelectThreeView.setOnClickListener(this);
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
            case R.id.select_0_view:
                Intent intent0= new Intent();
                intent0.putExtra("report_select",data.get(0));
                intent0.putExtra("rid",rid);
                setResult(RESULT_OK, intent0);
                finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            case R.id.select_1_view:
                Intent intent1= new Intent();
                intent1.putExtra("report_select",data.get(1));
                intent1.putExtra("rid", rid);
                setResult(RESULT_OK, intent1);
                finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            case R.id.select_2_view:
                Intent intent2= new Intent();
                intent2.putExtra("report_select",data.get(2));
                intent2.putExtra("rid",rid);
                setResult(RESULT_OK, intent2);
                finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            case R.id.select_3_view:
                Intent intent3= new Intent();
                intent3.putExtra("report_select",data.get(3));
                intent3.putExtra("rid",rid);
                setResult(RESULT_OK, intent3);
                finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            case R.id.select_4_view:
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
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
