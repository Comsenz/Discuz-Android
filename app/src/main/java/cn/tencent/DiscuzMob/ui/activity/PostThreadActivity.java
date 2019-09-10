package cn.tencent.DiscuzMob.ui.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.ui.adapter.Find_tab_Adapter;
import cn.tencent.DiscuzMob.ui.fragment.me.PostFragment;
import cn.tencent.DiscuzMob.ui.fragment.me.ReplayFragment;
import cn.tencent.DiscuzMob.ui.fragment.me.RepliesFragment;

/**
 * 帖子fragment
 */
public class PostThreadActivity extends BaseActivity {
    private TabLayout tab_FindFragment_title;
    private ViewPager vp_FindFragment_pager;
    private List<String> list_title;//tab名称列表
    private List<Fragment> listFragment; //fragment的集合
    private Find_tab_Adapter fAdapter;//定义adapter
    PostFragment postFragment;//主题
    ReplayFragment repliesFragment; //回复

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_thread);
        tab_FindFragment_title = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        vp_FindFragment_pager = (ViewPager) findViewById(R.id.vp_FindFragment_pager);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list_title = new ArrayList<>();
        list_title.add("主题");
        list_title.add("回复");
        listFragment = new ArrayList<>();
        postFragment = new PostFragment();
        repliesFragment = new ReplayFragment();
        listFragment.add(postFragment);
        listFragment.add(repliesFragment);
        //设置TabLayout的模式
        tab_FindFragment_title.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(0)));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(1)));
        //viewpager加载adapter
        fAdapter = new Find_tab_Adapter(PostThreadActivity.this, getSupportFragmentManager(), listFragment, list_title);
        //viewpager加载adapter
        vp_FindFragment_pager.setAdapter(fAdapter);
        //TabLayout加载viewpager
        tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager);
        //设置默认的界面
        vp_FindFragment_pager.setCurrentItem(0);
        //        title的点击事件
        vp_FindFragment_pager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int currentItems = vp_FindFragment_pager.getCurrentItem();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (currentItems) {
                case 0:
                    if (postFragment != null) {
                        ft.hide(postFragment);
                    }
                    break;
                case 1:
                    if (repliesFragment != null) {
                        ft.hide(repliesFragment);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        tab_FindFragment_title.post(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "宽度" + tab_FindFragment_title.getWidth());
                setIndicator(tab_FindFragment_title, 60, 60);
            }
        });
    }
}
