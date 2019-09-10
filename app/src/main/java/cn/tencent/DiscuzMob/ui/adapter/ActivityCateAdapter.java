package cn.tencent.DiscuzMob.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.tencent.DiscuzMob.R;


/**
 * Created by kurt on 2015/6/18.
 * 分类列表
 */
public class ActivityCateAdapter extends BaseAdapter {

    private ArrayList<String> mData;

    public ActivityCateAdapter() {
    }

    public void setData(ArrayList<String> data) {
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_item, parent, false);
            tv = (TextView) convertView.findViewById(R.id.type_text);
            convertView.setTag(tv);
        } else {
            tv = (TextView) convertView.getTag();
        }
        String cate = mData.get(position);
        tv.setText(cate);
        return convertView;
    }

}
