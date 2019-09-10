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
import java.util.List;

import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.model.Cat;
import cn.tencent.DiscuzMob.model.Forum;
import cn.tencent.DiscuzMob.R;

/**
 * Created by kurt on 2015/6/2.
 */
public class ForumIndexExpandableListAdapter extends BaseExpandableListAdapter {


    private ArrayList<Cat> groupList = new ArrayList<>();
    private ArrayList<ArrayList<Forum>> childList = new ArrayList<>();
    private FloatingGroupExpandableListView mListView;

    public ForumIndexExpandableListAdapter() {
    }

    public ForumIndexExpandableListAdapter(FloatingGroupExpandableListView listView) {
        this.mListView = listView;
    }

    public void setData(HashMap<String, Object> data) {
        if (data != null) {
            ArrayList<Cat> catList = (ArrayList<Cat>) data.get("group");
            if (catList != null && !catList.isEmpty()) {
                for (int i = 0, size = catList.size(); i < size; i++) {
                    Cat cat = catList.get(i);
                    if (groupList.contains(cat)) {
                        int index = groupList.indexOf(cat);
                        if (index != -1) {
                            childList.remove(index);
                        }
                        groupList.remove(cat);
                    }
                }
                groupList.addAll(catList);
                ArrayList<ArrayList<Forum>> cl = (ArrayList<ArrayList<Forum>>) data.get("child");
                if (cl != null && !cl.isEmpty())
                    childList.addAll(cl);
            }
            notifyDataSetChanged();
        }
    }

    public void removeCat(Cat cat) {
        if (groupList.contains(cat)) {
            int i = groupList.indexOf(cat);
            if (i != -1)
                childList.remove(i);
            groupList.remove(cat);
            notifyDataSetChanged();
        }
    }

    public void addCat(Cat cat, ArrayList<Forum> list, boolean header) {
        if (cat != null && !groupList.contains(cat)) {
            if (header)
                groupList.add(0, cat);
            else
                groupList.add(cat);
            int i = groupList.indexOf(cat);
            childList.add(i, list);
            notifyDataSetChanged();
        }
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

        if (groupPosition < childList.size()) {
            List<Forum> child = childList.get(groupPosition);
            return child != null ? child.size() : 0;
        } else {
            return 0;
        }
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
        Forum forum = childList.get(groupPosition).get(childPosition);
        final String html = forum.getName() + "<font color='#3c96d6'>(" + forum.getTodayposts() + ")</font>";
        holder.item1.setText(Html.fromHtml(html));
        return convertView;
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

    @Override
    public void notifyDataSetChanged() {
        int groupCount = mListView.getCount();
        super.notifyDataSetChanged();
        for (int i = 0; i < groupCount; i++) {
            mListView.expandGroup(i);
        }
    }

}
