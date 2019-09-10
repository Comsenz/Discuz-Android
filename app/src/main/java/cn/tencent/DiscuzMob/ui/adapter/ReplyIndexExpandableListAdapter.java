package cn.tencent.DiscuzMob.ui.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.diegocarloslima.fgelv.lib.FloatingGroupExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

import cn.tencent.DiscuzMob.model.HotTThread;
import cn.tencent.DiscuzMob.model.Reply;
import cn.tencent.DiscuzMob.R;

/**
 * Created by kurt on 2015/6/2.
 */
public class ReplyIndexExpandableListAdapter extends BaseExpandableListAdapter {

    private ArrayList<HotTThread> groupList;
    private ArrayList<ArrayList<Reply>> childList;
    private FloatingGroupExpandableListView mListView;

    public ReplyIndexExpandableListAdapter() {
    }

    public void setData(HashMap<String, Object> data) {
        if (data != null) {
            this.groupList = (ArrayList<HotTThread>) data.get("group");
            this.childList = (ArrayList<ArrayList<Reply>>) data.get("child");
            notifyDataSetChanged();
        }
    }

    public void setAppendData(HashMap<String, Object> data) {
        ArrayList<HotTThread> appendGroupList = (ArrayList<HotTThread>) data.get("group");
        ArrayList<ArrayList<Reply>> appendChildList = (ArrayList<ArrayList<Reply>>) data.get("child");
        groupList.addAll(appendGroupList);
        childList.addAll(appendChildList);
        notifyDataSetChanged();
    }

    public void setList(FloatingGroupExpandableListView listView) {
        mListView = listView;
    }

    @Override
    public int getGroupCount() {
        return groupList != null ? groupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList != null && groupPosition < getGroupCount() ? childList.get(groupPosition).size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_thread_item, parent, false);
            holder = new GroupHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.title.setText(groupList.get(groupPosition).getSubject());
        holder.forumName.setText(groupList.get(groupPosition).getForumName());
        holder.postTime.setText(Html.fromHtml(groupList.get(groupPosition).getDateline()));

        return convertView;
    }

    class GroupHolder {
        TextView title;
        TextView forumName, postTime;

        GroupHolder(View convertView) {
            title = (TextView) convertView.findViewById(R.id.fav_thread_title);
            forumName = (TextView) convertView.findViewById(R.id.forum_name);
            postTime = (TextView) convertView.findViewById(R.id.post_time);
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_reply_item, parent, false);
            holder = new ChildHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        holder.message.setText(childList.get(groupPosition).get(childPosition).getMessage());
        return convertView;
    }

    class ChildHolder {
        TextView message;

        ChildHolder(View convertView) {
            message = (TextView) convertView.findViewById(R.id.reply_message);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        int groupCount = mListView.getCount();
        super.notifyDataSetChanged();
        for (int i = 0; i < groupCount; i++) {
            mListView.expandGroup(i);
        }
    }
}
