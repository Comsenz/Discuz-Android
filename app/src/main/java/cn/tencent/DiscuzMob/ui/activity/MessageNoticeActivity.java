package cn.tencent.DiscuzMob.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.umeng.analytics.MobclickAgent;

import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.ui.adapter.FragmentPersistedPagerAdapter;
import cn.tencent.DiscuzMob.ui.fragment.notice.FriendNoticeFragment;
import cn.tencent.DiscuzMob.ui.fragment.notice.ThreadNoticeFragment;
import cn.tencent.DiscuzMob.widget.pagerindicator.PagerSlidingTabStrip;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/6/16.
 */
public class MessageNoticeActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager mPager;
    private PagerSlidingTabStrip mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notice);
        mPager = (ViewPager) findViewById(R.id.pager);
        mIndicator = (PagerSlidingTabStrip) findViewById(R.id.indicator);
        mPager.setAdapter(new InternalPagerAdapter(this, getSupportFragmentManager()));
        mIndicator.setViewPager(mPager);
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    static final class InternalPagerAdapter extends FragmentPersistedPagerAdapter {

        Fragment[] fragments = new Fragment[]{new ThreadNoticeFragment(), new FriendNoticeFragment()};
        String[] titles;

        InternalPagerAdapter(MessageNoticeActivity activity, FragmentManager fragmentManager) {
            super(fragmentManager);
            titles = activity.getResources().getStringArray(R.array.menu_notice);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onResume(this);
    }

}
