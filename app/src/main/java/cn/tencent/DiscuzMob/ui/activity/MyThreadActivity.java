package cn.tencent.DiscuzMob.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.ui.adapter.Find_tab_Adapter;
import cn.tencent.DiscuzMob.ui.fragment.mynews.ActivityFragment;
import cn.tencent.DiscuzMob.ui.fragment.mynews.CommentFragment;
import cn.tencent.DiscuzMob.ui.fragment.mynews.GoodsFragment;
import cn.tencent.DiscuzMob.ui.fragment.mynews.MentionMeFragment;
import cn.tencent.DiscuzMob.ui.fragment.mynews.MyThreadFragment;
import cn.tencent.DiscuzMob.ui.fragment.mynews.RewardFragment;


public class MyThreadActivity extends BaseActivity {
    private ImageView iv_back;//返回键
    private TabLayout tab_thread;
    private ViewPager vp_thread;
    private List<String> list_title;//tab名称列表
    private List<Fragment> listFragment; //fragment的集合
    private MyThreadFragment myThreadFragment;//帖子
    private CommentFragment commentFragment;//点评
    private ActivityFragment activityFragment;//活动
    private RewardFragment rewardFragment;//悬赏
    private Find_tab_Adapter fAdapter;//定义adapter
    private GoodsFragment goodsFragment;//商品
    private MentionMeFragment mentionMeFragment;//提到我的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_thread);
        initView();
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {
        tab_thread = (TabLayout) findViewById(R.id.tab_thread);
        vp_thread = (ViewPager) findViewById(R.id.vp_thread);
        list_title = new ArrayList<>();
        listFragment = new ArrayList<>();
        list_title.add("帖子");
        list_title.add("点评");
        list_title.add("活动");
        list_title.add("悬赏");
        list_title.add("商品");
        list_title.add("提到我的");
        myThreadFragment = new MyThreadFragment(MyThreadActivity.this);
        commentFragment = new CommentFragment(MyThreadActivity.this);
        activityFragment = new ActivityFragment(MyThreadActivity.this);
        rewardFragment = new RewardFragment(MyThreadActivity.this);
        goodsFragment = new GoodsFragment(MyThreadActivity.this);
        mentionMeFragment = new MentionMeFragment(MyThreadActivity.this);
        listFragment.add(myThreadFragment);
        listFragment.add(commentFragment);
        listFragment.add(activityFragment);
        listFragment.add(rewardFragment);
        listFragment.add(goodsFragment);
        listFragment.add(mentionMeFragment);

        tab_thread.setTabMode(TabLayout.MODE_SCROLLABLE);
        //为TabLayout添加tab名称
        tab_thread.addTab(tab_thread.newTab().setText(list_title.get(0)));
        tab_thread.addTab(tab_thread.newTab().setText(list_title.get(1)));
        tab_thread.addTab(tab_thread.newTab().setText(list_title.get(2)));
        tab_thread.addTab(tab_thread.newTab().setText(list_title.get(3)));
        tab_thread.addTab(tab_thread.newTab().setText(list_title.get(4)));
        tab_thread.addTab(tab_thread.newTab().setText(list_title.get(5)));
        //viewpager加载adapter
        fAdapter = new Find_tab_Adapter(MyThreadActivity.this, getSupportFragmentManager(), listFragment, list_title);
        //viewpager加载adapter
        vp_thread.setAdapter(fAdapter);
        //TabLayout加载viewpager
        tab_thread.setupWithViewPager(vp_thread);
        //设置默认的界面
        vp_thread.setCurrentItem(0);
        //        title的点击事件
        vp_thread.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int currentItems = vp_thread.getCurrentItem();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (currentItems) {
                case 0:
                    if (myThreadFragment != null) {
                        ft.hide(myThreadFragment);
                    }
                    break;
                case 1:
                    if (commentFragment != null) {
                        ft.hide(commentFragment);
                    }
                    break;
                case 3:
                    if (activityFragment != null) {
                        ft.hide(activityFragment);
                    }
                    break;
                case 4:
                    if (rewardFragment != null) {
                        ft.hide(rewardFragment);
                    }
                    break;
                case 5:
                    if (goodsFragment != null) {
                        ft.hide(goodsFragment);
                    }
                    break;
                case 6:
                    if (mentionMeFragment != null) {
                        ft.hide(mentionMeFragment);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
