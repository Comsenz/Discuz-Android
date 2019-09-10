package cn.tencent.DiscuzMob.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by cg on 2017/5/16.
 */

public class Find_tab_Adapter extends FragmentPersistedPagerAdapter {
    private List<Fragment> listFragment;
    private List<String> list_title;
    private Context context;

    public Find_tab_Adapter(Context commonActivity, FragmentManager fragmentManager, List<Fragment> listFragment, List<String> list_title) {
        super(fragmentManager);
        this.context = commonActivity;
        this.listFragment = listFragment;
        this.list_title = list_title;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return list_title.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return list_title.get(position);
    }
}
