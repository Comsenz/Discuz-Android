package cn.tencent.DiscuzMob.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import cn.tencent.DiscuzMob.R;


/**
 * Created by cg on 2017/5/3.
 */

public class SwipeMenuListViewAdapter extends BaseAdapter {

    int count;
    public void setCount(int count) {
        this.count =count;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return count;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if(convertView == null) {
        convertView = View.inflate(parent.getContext(), R.layout.vote_item, null);
//        }
        return convertView;
    }
}
