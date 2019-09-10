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
import cn.tencent.DiscuzMob.ui.fragment.myinteraction.LeavingMessageFragment;
import cn.tencent.DiscuzMob.ui.fragment.myinteraction.MyCallFragment;
import cn.tencent.DiscuzMob.ui.fragment.myinteraction.MyCommentFragment;
import cn.tencent.DiscuzMob.ui.fragment.myinteraction.MyFriendFragment;
import cn.tencent.DiscuzMob.ui.fragment.myinteraction.QuiteFragment;
import cn.tencent.DiscuzMob.ui.fragment.myinteraction.ShareFragment;

public class InteractionActivity extends BaseActivity {
    private ImageView iv_back;//返回键
    private TabLayout tab_thread;
    private ViewPager vp_thread;
    private List<String> list_title;//tab名称列表
    private List<Fragment> listFragment; //fragment的集合
    private MyCallFragment myCallFragment;//打招呼
    private MyFriendFragment friendFragment;//好友
    private LeavingMessageFragment leavingMessageFragment;//留言
    private MyCommentFragment myCommentFragment;//评论
    private Find_tab_Adapter fAdapter;//定义adapter
    private QuiteFragment quiteFragment;//商品
    private ShareFragment shareFragment;//提到我的


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interaction);
        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tab_thread = (TabLayout) findViewById(R.id.tab_thread);
        vp_thread = (ViewPager) findViewById(R.id.vp_thread);
        list_title = new ArrayList<>();
        listFragment = new ArrayList<>();
        list_title.add("打招呼");
        list_title.add("好友");
        list_title.add("留言");
        list_title.add("评论");
        list_title.add("挺你");
        list_title.add("分享");
        myCallFragment = new MyCallFragment(InteractionActivity.this);
        friendFragment = new MyFriendFragment(InteractionActivity.this);
        leavingMessageFragment = new LeavingMessageFragment(InteractionActivity.this);
        myCommentFragment = new MyCommentFragment(InteractionActivity.this);
        quiteFragment = new QuiteFragment(InteractionActivity.this);
        shareFragment = new ShareFragment(InteractionActivity.this);
        listFragment.add(myCallFragment);
        listFragment.add(friendFragment);
        listFragment.add(leavingMessageFragment);
        listFragment.add(myCommentFragment);
        listFragment.add(quiteFragment);
        listFragment.add(shareFragment);

        tab_thread.setTabMode(TabLayout.MODE_SCROLLABLE);
        //为TabLayout添加tab名称
        tab_thread.addTab(tab_thread.newTab().setText(list_title.get(0)));
        tab_thread.addTab(tab_thread.newTab().setText(list_title.get(1)));
        tab_thread.addTab(tab_thread.newTab().setText(list_title.get(2)));
        tab_thread.addTab(tab_thread.newTab().setText(list_title.get(3)));
        tab_thread.addTab(tab_thread.newTab().setText(list_title.get(4)));
        tab_thread.addTab(tab_thread.newTab().setText(list_title.get(5)));
        //viewpager加载adapter
        fAdapter = new Find_tab_Adapter(InteractionActivity.this, getSupportFragmentManager(), listFragment, list_title);
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
                    if (myCallFragment != null) {
                        ft.hide(myCallFragment);
                    }
                    break;
                case 1:
                    if (friendFragment != null) {
                        ft.hide(friendFragment);
                    }
                    break;
                case 3:
                    if (leavingMessageFragment != null) {
                        ft.hide(leavingMessageFragment);
                    }
                    break;
                case 4:
                    if (myCommentFragment != null) {
                        ft.hide(myCommentFragment);
                    }
                    break;
                case 5:
                    if (quiteFragment != null) {
                        ft.hide(quiteFragment);
                    }
                    break;
                case 6:
                    if (shareFragment != null) {
                        ft.hide(shareFragment);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
