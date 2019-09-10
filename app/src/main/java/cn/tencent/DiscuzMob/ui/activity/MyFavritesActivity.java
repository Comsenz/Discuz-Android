package cn.tencent.DiscuzMob.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.ui.fragment.BaseFragment;
import cn.tencent.DiscuzMob.ui.fragment.forum.MyFavForumFragment;
import cn.tencent.DiscuzMob.ui.fragment.forum.MyFavThreadFragment;
import cn.tencent.DiscuzMob.widget.pagerindicator.PagerSlidingTabStrip;

/**
 * Created by AiWei on 2016/5/30.
 */
public class MyFavritesActivity extends BaseActivity implements View.OnClickListener {

    private PagerSlidingTabStrip mIndicator;
    private ViewPager mPager;
    private TextView tv_forum, tv_thread;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private int mCurrentId;
    private SparseArray<Fragment> mStack = new SparseArray<>(2);
    private SparseArray<String> mKeys = new SparseArray<>(2);
    private View view_forum, view_thread;
    private int mMainColor;

    {
        mKeys.put(R.id.tv_forum, MyFavForumFragment.class.getName());
        mKeys.put(R.id.tv_thread, MyFavThreadFragment.class.getName());
//        mKeys.put(R.id.tv_thread, MyFavPostFragment.class.getName());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
//        mIndicator = (PagerSlidingTabStrip) findViewById(R.id.indicator);
//        mPager = (ViewPager) findViewById(R.id.pager);
        mFragmentManager = getSupportFragmentManager();

//        mPager.setAdapter(new SimpleFragmentPagerAdapter(this, getSupportFragmentManager()));
//        mIndicator.setViewPager(mPager);
        mMainColor = getResources().getColor(R.color.blue);
        findViewById(R.id.back).setOnClickListener(this);
        tv_forum = (TextView) findViewById(R.id.tv_forum);
        tv_thread = (TextView) findViewById(R.id.tv_thread);
        view_forum = findViewById(R.id.view_forum);
        view_thread = findViewById(R.id.view_thread);
        tv_forum.setOnClickListener(this);
        tv_thread.setOnClickListener(this);
        tv_forum.setSelected(true);
        view_forum.setBackgroundColor(mMainColor);
        show(R.id.tv_forum);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forum:
                tv_forum.setSelected(true);
                tv_thread.setSelected(false);
                view_forum.setBackgroundColor(mMainColor);
                view_thread.setBackgroundColor(Color.WHITE);
                show(R.id.tv_forum);
                break;
            case R.id.tv_thread:
                view_thread.setBackgroundColor(mMainColor);
                view_forum.setBackgroundColor(Color.WHITE);
                tv_forum.setSelected(false);
                tv_thread.setSelected(true);
                show(R.id.tv_thread);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    void show(int id) {
        if (id > 0 && mCurrentId != id) {
            Fragment fragment = mStack.get(id);
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            if (fragment == null) {
                fragment = BaseFragment.instantiate(this, mKeys.get(id));
                if (fragment != null) {
                    mStack.put(id, fragment);
                    transaction.add(R.id.pager, fragment).hide(fragment);
                } else {
                    return;
                }
            }
            if (mCurrentFragment != null) {
                transaction.hide(mCurrentFragment);
            }
            transaction.show(fragment).commit();
            mCurrentFragment = fragment;
            mCurrentId = id;
        }
    }

}
