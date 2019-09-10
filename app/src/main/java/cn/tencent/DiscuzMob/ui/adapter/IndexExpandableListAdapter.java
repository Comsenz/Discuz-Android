package cn.tencent.DiscuzMob.ui.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.model.Forum;
import cn.tencent.DiscuzMob.model.HotTThread;
import cn.tencent.DiscuzMob.widget.AsyncImageView;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2015/5/6.
 */
public class IndexExpandableListAdapter extends BaseExpandableListAdapter {

    private String[] mGroups = new String[]{
            "常去的版块", "热帖"
    };

//    private BaseModel<HotVariables<Forum>> mHotForum;
//    private BaseModel<HotVariables<HotTThread>> mHotThread;

    private ArrayList<Forum> mHotForum;
    private ArrayList<HotTThread> mHotThread;

    public IndexExpandableListAdapter() {
    }

    public void setData(ArrayList<Forum> hotForum, ArrayList<HotTThread> hotThread) {
        this.mHotForum = hotForum;
        this.mHotThread = hotThread;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mGroups.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition == 0 && mHotForum != null) {
            List<?> data = mHotForum;
            return data == null ? 0 : data.size();
        }
        if (groupPosition == 1 && mHotThread != null) {
            List<?> data = mHotThread;
            return data == null ? 0 : data.size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (groupPosition == 0 && mHotForum != null) {
            List<?> data = mHotForum;
            return data == null ? null : data.get(childPosition);
        } else if (groupPosition == 1 && mHotThread != null) {
            List<?> data = mHotThread;
            return data == null ? null : data.get(childPosition);
        } else return null;
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
        tv.setText(mGroups[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_item1, parent, false);
            holder = new ChildHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }

        holder.item1.setVisibility(groupPosition == 0 ? View.VISIBLE : View.GONE);
        holder.item2.setVisibility(groupPosition == 1 ? View.VISIBLE : View.GONE);
        if (groupPosition == 0) {
            Forum forum = mHotForum.get(childPosition);
            final String html = forum.getName() + "<font color='#3c96d6'>(" + forum.getTodayposts() + ")</font>";
            holder.item1.setText(Html.fromHtml(html));
        } else if (groupPosition == 1) {
            HotTThread tThread = mHotThread.get(childPosition);
            holder.comment.setText(String.valueOf(tThread.getReplies()));
            holder.title.setText(tThread.getSubject().trim());
            holder.username.setText(tThread.getAuthor());
            holder.header.load(tThread.getAvatar(),R.drawable.ic_header_def);
            holder.date.setText(tThread.getDateline());
        } else ;
        return convertView;
    }

    class ChildHolder {
        TextView item1;
        View item2;
        TextView comment, title, username, date;
        AsyncImageView preview, header;

        ChildHolder(View convertView) {
            item1 = (TextView) convertView.findViewById(R.id.item1);
            item2 = convertView.findViewById(R.id.item2);
            comment = (TextView) convertView.findViewById(R.id.comment);
            title = (TextView) convertView.findViewById(R.id.title);
            username = (TextView) convertView.findViewById(R.id.username);
            date = (TextView) convertView.findViewById(R.id.date);
            preview = (AsyncImageView) convertView.findViewById(R.id.preview);
            header = (AsyncImageView) convertView.findViewById(R.id.header);
        }
    }


}
