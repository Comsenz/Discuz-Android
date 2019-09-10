package cn.tencent.DiscuzMob.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.tencent.DiscuzMob.model.ForumThreadType;
import cn.tencent.DiscuzMob.R;

/**
 * Created by kurt on 2015/6/18.
 * 分类列表
 */
public class ForumThreadTypeAdapter extends BaseAdapter {

    private ArrayList<ForumThreadType> mData;

    public ForumThreadTypeAdapter() {
    }

    public void setData(ArrayList<ForumThreadType> data) {
        this.mData = data;
        if (mData != null) {
            ForumThreadType type = new ForumThreadType();
            type.setTypeName("不选择分类");
            type.setTypeId("0");
            mData.add(0,type);
            notifyDataSetChanged();
        }
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
        ForumThreadType type = mData.get(position);
        tv.setText(type.getTypeName());
        Log.d("kurt",type.getTypeName());
        return convertView;
    }

}
