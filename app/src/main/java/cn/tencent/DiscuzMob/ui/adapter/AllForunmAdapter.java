package cn.tencent.DiscuzMob.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.diegocarloslima.fgelv.lib.FloatingGroupExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.model.AllForumBean;
import cn.tencent.DiscuzMob.model.CatlistBean;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/4/6.
 */

public class AllForunmAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<CatlistBean> groupList;
    private ArrayList<ArrayList<AllForumBean.VariablesBean.ForumlistBean>> childList;

    public AllForunmAdapter(Context activity, List<CatlistBean> groupList, ArrayList<ArrayList<AllForumBean.VariablesBean.ForumlistBean>> childList) {
        this.context = activity;
        this.groupList =groupList;
        this.childList = childList;
    }

    public AllForunmAdapter(FloatingGroupExpandableListView mAllForumListView) {

    }
    public void setData(HashMap<String, Object> data) {
        if (data != null) {
            ArrayList<CatlistBean> catList = (ArrayList<CatlistBean>) data.get("name");
            if (catList != null && !catList.isEmpty()) {
                for (int i = 0, size = catList.size(); i < size; i++) {
                    CatlistBean cat = catList.get(i);
                    if (groupList.contains(cat)) {
                        int index = groupList.indexOf(cat);
                        if (index != -1) {
                            childList.remove(index);
                        }
                        groupList.remove(cat);
                    }
                }
                groupList.addAll(catList);
                ArrayList<ArrayList<AllForumBean.VariablesBean.ForumlistBean>> cl = (ArrayList<ArrayList<AllForumBean.VariablesBean.ForumlistBean>>) data.get("name");
                if (cl != null && !cl.isEmpty())
                    childList.addAll(cl);
            }
            notifyDataSetChanged();
        }
    }
    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.size();
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_item0, parent, false);
        }
        TextView tv = (TextView) convertView;
        tv.setText(groupList.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_item1, parent, false);
            holder = new ChildHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }

        holder.item1.setVisibility(View.VISIBLE);
        holder.item2.setVisibility(View.GONE);
//        AllForumBean.VariablesBean.ForumlistBean forum = childList.get(groupPosition).get(childPosition);
        holder.title.setText(childList.get(groupPosition).get(childPosition).getName());
//        final String html = forum.getName() + "<font color='#3c96d6'>(" + forum.getTodayposts() + ")</font>";
//        holder.item1.setText(Html.fromHtml(html));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    class ChildHolder {
        TextView item1;
        View item2;
        TextView comment, title, username, date;
        AsyncRoundedImageView preview, header;

        ChildHolder(View convertView) {
            item1 = (TextView) convertView.findViewById(R.id.item1);
            item2 = convertView.findViewById(R.id.item2);
            comment = (TextView) convertView.findViewById(R.id.comment);
            title = (TextView) convertView.findViewById(R.id.title);
            username = (TextView) convertView.findViewById(R.id.username);
            date = (TextView) convertView.findViewById(R.id.date);
            preview = (AsyncRoundedImageView) convertView.findViewById(R.id.preview);
            header = (AsyncRoundedImageView) convertView.findViewById(R.id.header);
        }
    }
}
