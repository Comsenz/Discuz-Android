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
public class SelectQuestionActivity extends FragmentActivity implements View.OnClickListener{
    private RelativeLayout mSelectZeroView, mSelectOneView,mSelectTwoView,mSelectThreeView,mSelectFourView,mSelectFiveView,mSelectSixeView,mSelectSevenView, mCancelView;

    public static final String BUNDLE_KEY = "bundle";

    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;

    private static final String IMAGE_FILE_NAME = "header.jpg";
    private HashMap<Integer,String> data = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_question);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
            setTranslucentStatus(true);
        }
        initData();
        initView();
        initListener();
    }

    private void initData() {
        data.clear();
        data.put(0, "安全提问（未设置请忽略)");
        data.put(1,"母亲的名字");
        data.put(2,"爷爷的名字");
        data.put(3,"父亲出生的城市");
        data.put(4,"你其中一位老师的名字");
        data.put(5,"你个人计算机的型号");
        data.put(6,"你最喜欢的餐馆的名称");
        data.put(7,"驾驶执照最后四位的数字");
    }

    private void initView() {
        mSelectZeroView = (RelativeLayout) findViewById(R.id.select_0_view);
        mCancelView = (RelativeLayout) findViewById(R.id.cancel_view);
        mSelectOneView = (RelativeLayout) findViewById(R.id.select_1_view);
        mSelectTwoView = (RelativeLayout) findViewById(R.id.select_2_view);
        mSelectThreeView = (RelativeLayout) findViewById(R.id.select_3_view);
        mSelectFourView = (RelativeLayout) findViewById(R.id.select_4_view);
        mSelectFiveView = (RelativeLayout) findViewById(R.id.select_5_view);
        mSelectSixeView = (RelativeLayout) findViewById(R.id.select_6_view);
        mSelectSevenView = (RelativeLayout) findViewById(R.id.select_7_view);
    }

    private void initListener() {
        mCancelView.setOnClickListener(this);
        mSelectZeroView.setOnClickListener(this);
        mSelectOneView.setOnClickListener(this);
        mSelectTwoView.setOnClickListener(this);
        mSelectThreeView.setOnClickListener(this);
        mSelectFourView.setOnClickListener(this);
        mSelectFiveView.setOnClickListener(this);
        mSelectSixeView.setOnClickListener(this);
        mSelectSevenView.setOnClickListener(this);
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
                intent0.putExtra("question",data.get(0));
                intent0.putExtra("id", 0);
                setResult(RESULT_OK, intent0);
                finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            case R.id.select_1_view:
                Intent intent1= new Intent();
                intent1.putExtra("question",data.get(1));
                intent1.putExtra("id", 1);
                setResult(RESULT_OK, intent1);
                finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            case R.id.select_2_view:
                Intent intent2= new Intent();
                intent2.putExtra("question",data.get(2));
                intent2.putExtra("id",2);
                setResult(RESULT_OK, intent2);
                finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            case R.id.select_3_view:
                Intent intent3= new Intent();
                intent3.putExtra("question",data.get(3));
                intent3.putExtra("id",3);
                setResult(RESULT_OK, intent3);
                finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            case R.id.select_4_view:
                Intent intent4= new Intent();
                intent4.putExtra("question",data.get(4));
                intent4.putExtra("id",4);
                setResult(RESULT_OK, intent4);
                finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            case R.id.select_5_view:
                Intent intent5= new Intent();
                intent5.putExtra("question",data.get(5));
                intent5.putExtra("id",5);
                setResult(RESULT_OK, intent5);
                finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            case R.id.select_6_view:
                Intent intent6= new Intent();
                intent6.putExtra("question",data.get(6));
                intent6.putExtra("id",6);
                setResult(RESULT_OK, intent6);
                finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            case R.id.select_7_view:
                Intent intent7= new Intent();
                intent7.putExtra("question",data.get(7));
                intent7.putExtra("id",7);
                setResult(RESULT_OK, intent7);
                finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            case R.id.cancel_view:
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
