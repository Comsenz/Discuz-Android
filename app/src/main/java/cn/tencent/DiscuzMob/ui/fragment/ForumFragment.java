package cn.tencent.DiscuzMob.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import cn.tencent.DiscuzMob.ui.fragment.forum.HotForumFragment;
import cn.tencent.DiscuzMob.ui.menu.OnMenuItemClickListener;
import cn.tencent.DiscuzMob.ui.activity.ToolbarActivity;
import cn.tencent.DiscuzMob.ui.fragment.forum.ForumsFragment;
import cn.tencent.DiscuzMob.ui.menu.ForumPopupWindow;
import cn.tencent.DiscuzMob.R;


/**
 * Created by AiWei on 2016/5/25.
 * 板块
 */
public class ForumFragment extends BaseFragment implements View.OnClickListener, OnMenuItemClickListener {

    private TextView mTitle;
    private ImageView mTypeTag;
    private CharSequence mTitleStr;
    private ForumPopupWindow mPopupmenu;

    private SparseArray<Fragment> mStack = new SparseArray<>(3);
    private SparseArray<String> mKeys = new SparseArray<>(3);

    {
//        mKeys.put(R.id.popupwindow_item01_name, AllForumFragment.class.getName());
        mKeys.put(R.id.popupwindow_item01_name, ForumsFragment.class.getName());
//        mKeys.put(R.id.popupwindow_item02_name, HotForumFragment.class.getName());
//        mKeys.put(R.id.popupwindow_item03_name, MyFavForumFragment.class.getName());//收藏移动到全部版面中
    }

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private int mCurrentId;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolbar();
    }


    private TextView tv_all;//全部版块
    private TextView tv_hot;//热门版块
    private View view_hot, view_all;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.simple_container, container, false);
        tv_all = (TextView) inflate.findViewById(R.id.tv_all);
        tv_hot = (TextView) inflate.findViewById(R.id.tv_hot);
        view_hot = inflate.findViewById(R.id.view_hot);
        view_all = inflate.findViewById(R.id.view_all);
        tv_hot.setClickable(true);
        tv_hot.setSelected(true);
//        tv_all.setBackgroundColor(Color.BLUE);
//        tv_all.setTextColor(Color.WHITE);
        tv_all.setOnClickListener(this);
        tv_hot.setOnClickListener(this);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPopupmenu = new ForumPopupWindow(getActivity(), this);
        mFragmentManager = getChildFragmentManager();
        show(R.id.popupwindow_item01_name);
        mPopupmenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mTypeTag.setImageResource(R.drawable.arrow_t);
            }
        });
    }

    void initToolbar() {
        if (getSupportToolbar() != null) {
            getSupportToolbar().show(ToolbarActivity.Toolbar.DEFAULT | ToolbarActivity.Toolbar.SHOW_SPINNER);
            mTitle = getSupportToolbar().title;
            mTitle.setOnClickListener(this);
            mTypeTag = getSupportToolbar().arrow;
            if (TextUtils.isEmpty(mTitleStr)) {
//                mTitleStr = "全部版面";
                mTitleStr = "版块";
            }
            mTitle.setText(mTitleStr);
        }
    }

    void show(int id) {
        if (id > 0 && mCurrentId != id) {
            Fragment fragment = mStack.get(id);
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            if (fragment == null) {
                fragment = instantiate(getActivity(), mKeys.get(id));
                if (fragment != null) {
                    mStack.put(id, fragment);
                    transaction.add(R.id.container, fragment).hide(fragment);
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initToolbar();
        }
        for (int i = 0, size = mStack.size(); i < size; i++) {
            Fragment f = mStack.get(mStack.keyAt(i));
            if (f != null) {
                f.onHiddenChanged(hidden);
            }
        }
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.title) {
//            mPopupmenu.showAsDropDown(v, 0, 0);
//            mTypeTag.setImageResource(R.drawable.arrow_b);
//        }
        switch (v.getId()) {
            case R.id.tv_all:
                tv_all.setSelected(true);
//                tv_hot.setSelected(false);
                view_all.setVisibility(View.VISIBLE);
                view_hot.setVisibility(View.INVISIBLE);
                show(R.id.popupwindow_item01_name);
                break;
            case R.id.tv_hot:
//                tv_hot.setSelected(true);
//                tv_all.setSelected(false);
//                view_all.setVisibility(View.INVISIBLE);
//                view_hot.setVisibility(View.VISIBLE);
//                show(R.id.popupwindow_item02_name);
                break;
        }
    }

    @Override
    public void onItemClick(Object obj, View v, int position, CharSequence title) {
        mTitle.setText(mTitleStr = title);
        mTypeTag.setImageResource(R.drawable.arrow_t);
        show(v.getId());
        mPopupmenu.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ForumFragment(版块)");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ForumFragment(版块)");
    }

}
