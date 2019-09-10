package cn.tencent.DiscuzMob.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.R;

/**
 * Created by .Wei on 15/10/10.
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
