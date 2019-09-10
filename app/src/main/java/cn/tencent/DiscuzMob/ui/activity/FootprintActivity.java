package cn.tencent.DiscuzMob.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.ui.fragment.me.FootprintFragment;
import cn.tencent.DiscuzMob.ui.fragment.me.ThreadFragment;
import cn.tencent.DiscuzMob.R;


public class FootprintActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_forums, tv_thread;
    private View view_forums, view_hread;
    private FragmentManager fragmentManager;
    private FootprintFragment footprintFragment;
    private ThreadFragment threadFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fragmentManager = getSupportFragmentManager();
        tv_forums = (TextView) findViewById(R.id.tv_forums);
        tv_thread = (TextView) findViewById(R.id.tv_thread);
        view_forums = findViewById(R.id.view_forums);
        view_hread = findViewById(R.id.view_hread);
        tv_forums.setOnClickListener(this);
        tv_thread.setOnClickListener(this);
        setTabSelection(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forums:
                setTabSelection(0);
                break;
            case R.id.tv_thread:
                setTabSelection(1);
                break;
        }

    }

    private void setTabSelection(int i) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (i) {
            case 0:
                if (footprintFragment == null) {
                    footprintFragment = new FootprintFragment();
                    transaction.add(R.id.fl_container, footprintFragment);
                } else {
                    transaction.show(footprintFragment);
                }
                tv_forums.setSelected(true);
                view_forums.setVisibility(View.VISIBLE);
                break;

            case 1:
                if (threadFragment == null) {
                    threadFragment = new ThreadFragment();
                    transaction.add(R.id.fl_container, threadFragment);
                } else {
                    transaction.show(threadFragment);
                }
                tv_thread.setSelected(true);
                view_hread.setVisibility(View.VISIBLE);
                break;

        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (footprintFragment != null) {
            transaction.hide(footprintFragment);
            tv_forums.setSelected(false);
            view_forums.setVisibility(View.INVISIBLE);
        }
        if (threadFragment != null) {
            transaction.hide(threadFragment);
            tv_thread.setSelected(false);
            view_hread.setVisibility(View.INVISIBLE);
        }
    }

}
