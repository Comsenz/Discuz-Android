package cn.tencent.DiscuzMob.ui.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.model.Forum;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2015/5/11.
 * 板块列表
 */
public class ForumAdapter extends BaseAdapter {

    private List<Forum> mForumList;

    public void setForums(List<Forum> mForumList) {
        if (mForumList != null ) {
            this.mForumList = mForumList;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mForumList == null ? 0 : mForumList.size();
    }

    @Override
    public Object getItem(int position) {
        return mForumList.get(position).getFid();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_item1, parent, false);
            tv = (TextView) convertView.findViewById(R.id.item1);
            convertView.setTag(tv);
        } else {
            tv = (TextView) convertView.getTag();
        }
        Forum forum = mForumList.get(position);
        final String html = forum.getName() + "<font color='#3c96d6'>(" + forum.getTodayposts() + ")</font>";
        tv.setText(Html.fromHtml(html));
        LinearLayout item2 = (LinearLayout) convertView.findViewById(R.id.item2);
        item2.setVisibility(View.GONE);
        return convertView;
    }

}
