package cn.tencent.DiscuzMob.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.ui.fragment.recommend.EssenceFragment;
import cn.tencent.DiscuzMob.ui.fragment.recommend.NewFragment;
import cn.tencent.DiscuzMob.ui.menu.OnMenuItemClickListener;
import cn.tencent.DiscuzMob.ui.activity.ToolbarActivity;
import cn.tencent.DiscuzMob.ui.fragment.recommend.HomePageFragment;
import cn.tencent.DiscuzMob.ui.fragment.recommend.LiveFragment;
import cn.tencent.DiscuzMob.ui.menu.ForumPopupWindow;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/5/10.
 */

public class FristFragment extends BaseFragment implements View.OnClickListener, OnMenuItemClickListener {
    private TextView mTitle;
    private ImageView mTypeTag;
    private CharSequence mTitleStr;
    private ForumPopupWindow mPopupmenu;
    TextView tv_recommend,  tv_footprint;
    View view_recommend, view_footprint;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private int mCurrentId;
    private SparseArray<Fragment> mStack = new SparseArray<>(3);
    private SparseArray<String> mKeys = new SparseArray<>(3);

    {
//        mKeys.put(R.id.popupwindow_item01_name, NewFragment.class.getName());
        mKeys.put(R.id.popupwindow_item01_name, NewFragment.class.getName());
        mKeys.put(R.id.popupwindow_item03_name, EssenceFragment.class.getName());//收藏移动到全部版面中
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolbar();
    }

    void initToolbar() {
        if (getSupportToolbar() != null) {
            getSupportToolbar().show(ToolbarActivity.Toolbar.DEFAULT);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initToolbar();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frist, container, false);
        tv_recommend = (TextView) view.findViewById(R.id.tv_recommend);
//        tv_live = (TextView) view.findViewById(R.id.tv_live);
        tv_footprint = (TextView) view.findViewById(R.id.tv_footprint);
        view_recommend = view.findViewById(R.id.view_recommend);
//        view_live = view.findViewById(R.id.view_live);
        view_footprint = view.findViewById(R.id.view_footprint);
        tv_recommend.setOnClickListener(this);
//        tv_live.setOnClickListener(this);
        tv_footprint.setOnClickListener(this);
        tv_recommend.setClickable(true);
        tv_recommend.setSelected(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPopupmenu = new ForumPopupWindow(RedNetApp.getInstance(), this);
        mFragmentManager = getChildFragmentManager();
        show(R.id.popupwindow_item01_name);
        mPopupmenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mTypeTag.setImageResource(R.drawable.arrow_t);
            }
        });
    }

    void show(int id) {
        if (id > 0 && mCurrentId != id) {
            Fragment fragment = mStack.get(id);
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            if (fragment == null) {
                fragment = instantiate(getActivity(), mKeys.get(id));
                if (fragment != null) {
                    mStack.put(id, fragment);
                    transaction.add(R.id.fl_recommend, fragment).hide(fragment);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_recommend:
                tv_recommend.setSelected(true);
                view_recommend.setVisibility(View.VISIBLE);
                tv_footprint.setSelected(false);
                view_footprint.setVisibility(View.INVISIBLE);
//                tv_live.setSelected(false);
//                view_live.setVisibility(View.INVISIBLE);
                show(R.id.popupwindow_item01_name);
                break;
//            case R.id.tv_live:
//                tv_live.setSelected(true);
//                view_live.setVisibility(View.VISIBLE);
//                tv_recommend.setSelected(false);
//                view_recommend.setVisibility(View.INVISIBLE);
//                tv_footprint.setSelected(false);
//                view_footprint.setVisibility(View.INVISIBLE);
//                show(R.id.popupwindow_item02_name);
//                break;
            case R.id.tv_footprint:
                tv_footprint.setSelected(true);
                view_footprint.setVisibility(View.VISIBLE);
//                tv_live.setSelected(false);
//                view_live.setVisibility(View.INVISIBLE);
                tv_recommend.setSelected(false);
                view_recommend.setVisibility(View.INVISIBLE);
                show(R.id.popupwindow_item03_name);
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
}
