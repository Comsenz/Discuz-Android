package cn.tencent.DiscuzMob.ui.activity;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.R;

/**
 * Created by kurt on 15-6-18.
 */
public class MyFavActivity extends BaseActivity implements View.OnClickListener{


    List<View> listViews;

    Context context = null;

    LocalActivityManager manager = null;

    TabHost tabHost = null;

    private ViewPager pager = null;

    private ImageView mBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fav);
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);

        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();
        tabHost.setup(manager);
        context = MyFavActivity.this;
        pager  = (ViewPager) findViewById(R.id.viewpager);
        listViews = new ArrayList<>();
        mBackBtn = (ImageView) findViewById(R.id.back_btn);
        mBackBtn.setOnClickListener(this);
        Intent i1 = new Intent(context, MyFavForumActivity.class);
        listViews.add(getView("MyFavForumActivity", i1));
        Intent i2 = new Intent(context, MyFavThreadActivity.class);
        listViews.add(getView("MyFavThreadActivity", i2));

        RelativeLayout tabIndicator1 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tabwidget, null);
        TextView tvTab1 = (TextView)tabIndicator1.findViewById(R.id.tv_title);
        tvTab1.setText("版块");

        RelativeLayout tabIndicator2 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tabwidget,null);
        TextView tvTab2 = (TextView)tabIndicator2.findViewById(R.id.tv_title);
        tvTab2.setText("帖子");

        tabHost.addTab(tabHost.newTabSpec("A").setIndicator(tabIndicator1).setContent(i1));
        tabHost.addTab(tabHost.newTabSpec("B").setIndicator(tabIndicator2).setContent(i2));

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override            public void onTabChanged(String tabId) {
                tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                    @Override
                    public void onTabChanged(String tabId) {
                        if ("A".equals(tabId)) {
                            pager.setCurrentItem(0);
                        } else if ("B".equals(tabId)) {
                            pager.setCurrentItem(1);
                        }
                    }

                });
            }
        });

        pager.setAdapter(new MyPageAdapter(listViews));
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabHost.setCurrentTab(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        tabHost.setCurrentTab(1);
        tabHost.setCurrentTab(0);
    }

    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.back_btn:
            finish();
            break;
        }
    }

    private class MyPageAdapter extends PagerAdapter {

        private List<View> list;

        private MyPageAdapter(List<View> list) {
            this.list = list;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object arg2) {
            ViewPager pViewPager = ((ViewPager) view);
            pViewPager.removeView(list.get(position));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            ViewPager pViewPager = ((ViewPager) view);
            pViewPager.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

    }

}
