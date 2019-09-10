package cn.tencent.DiscuzMob.base;


import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.utils.StatusBarUtil;

public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onStart() {
        super.onStart();
        StatusBarUtil.setStatusBarColor(this, R.color.background);
        StatusBarUtil.StatusBarLightMode(this);
        ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0).setFitsSystemWindows(true);
    }

}
