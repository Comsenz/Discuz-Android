package cn.tencent.DiscuzMob.ui.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.model.FavModel;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/5/16.
 */
public class ForumFavAdapter extends BaseAdapter {

    private List<FavModel> mList;

    public ForumFavAdapter() {
    }

    public void set(List<FavModel> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void append(List<FavModel> list) {
        if (list != null && !list.isEmpty()) {
            if (mList == null) {
                mList = list;
            } else {
                mList.addAll(list);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position).getId();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_forum, parent, false);
            tv = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(tv);
        } else {
            tv = (TextView) convertView.getTag();
        }
        FavModel forum = mList.get(position);
        tv.setText(Html.fromHtml(new StringBuilder(forum.getTitle())
                .append("<font color='#3c96d6'>(").append(forum.getTodayposts()).append(")</font>")
                .toString()));
        return convertView;
    }

}
