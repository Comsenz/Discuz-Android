package cn.tencent.DiscuzMob.ui.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.model.ThreadlistBean;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/4/20.
 */
public class HisRepliesAdapter extends BaseAdapter {
    List<ThreadlistBean> threadlist;

    public HisRepliesAdapter(List<ThreadlistBean> threadlist) {
        this.threadlist = threadlist;
    }

    @Override
    public int getCount() {
        return threadlist == null ? 0 : threadlist.size();
    }

    @Override
    public Object getItem(int position) {
        return threadlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_thread_item, parent, false);
            holder.threadTitle = (TextView) convertView.findViewById(R.id.fav_thread_title);
            holder.forumName = (TextView) convertView.findViewById(R.id.forum_name);
            holder.postTime = (TextView) convertView.findViewById(R.id.post_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ThreadlistBean thread = threadlist.get(position);
        holder.threadTitle.setText(thread.getSubject());
        holder.forumName.setText(thread.getAuthor());
        holder.postTime.setText(Html.fromHtml(thread.getDateline()));
        return convertView;
    }

    static class ViewHolder {
        TextView threadTitle;
        TextView forumName;
        TextView postTime;
    }
}
//public class HisRepliesAdapter extends BaseExpandableListAdapter {
//    private Context mContext;
//    List<ThreadlistBean> listGroup;
//    List<List<ReplyBean>> listChild;
//    public HisRepliesAdapter(Context appContext, List<ThreadlistBean> listGroup, List<List<ReplyBean>> listChild) {
//        mContext = appContext;
//        this.listGroup = listGroup;
//        this.listChild = listChild;
//    }
//
//    @Override
//    public int getGroupCount() {
//        return listGroup == null ?0:listGroup.size();
//    }
//
//    @Override
//    public int getChildrenCount(int groupPosition) {
//        return listChild == null ?0:listChild.size();
//    }
//
//    @Override
//    public Object getGroup(int groupPosition) {
//        return listGroup.get(groupPosition);
//    }
//
//    @Override
//    public Object getChild(int groupPosition, int childPosition) {
//        return listChild.get(groupPosition).get(childPosition);
//    }
//
//    @Override
//    public long getGroupId(int groupPosition) {
//        return groupPosition;
//    }
//
//    @Override
//    public long getChildId(int groupPosition, int childPosition) {
//        return childPosition;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return false;
//    }
//
//    @Override
//    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//        GroupHolder groupHolder = null;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.groupitem, null);
//            groupHolder = new GroupHolder();
//            groupHolder.groupImg = (ImageView) convertView.findViewById(R.id.img_indicator);
//            groupHolder.groupText = (TextView) convertView.findViewById(R.id.tv_tv_group_text);
//            convertView.setTag(groupHolder);
//        } else {
//            groupHolder = (GroupHolder) convertView.getTag();
//        }
//        if (isExpanded) {
//            groupHolder.groupImg.setBackgroundResource(R.drawable.right);
//        } else {
//            groupHolder.groupImg.setBackgroundResource(R.drawable.down);
//        }
//        groupHolder.groupText.setText(listGroup.get(groupPosition).getForumname());
//        return convertView;
//    }
//
//    @Override
//    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        ChildHolder childHolder = null;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.sample_message_item, null);
//            childHolder = new ChildHolder();
//            childHolder.title = (TextView) convertView.findViewById(R.id.title);
//            childHolder.dateline = (TextView) convertView.findViewById(R.id.dateline);
//            childHolder.text = (TextView) convertView.findViewById(R.id.text);
//            childHolder.header = (AsyncRoundedImageView) convertView.findViewById(R.id.header);
//            convertView.setTag(childHolder);
//        } else {
//            childHolder = (ChildHolder) convertView.getTag();
//        }
//        childHolder.title.setText(listGroup.get(groupPosition).getAuthor());
//
//        return convertView;
//    }
//
//    @Override
//    public boolean isChildSelectable(int groupPosition, int childPosition) {
//        return false;
//    }
//    private class GroupHolder {
//        ImageView groupImg;
//        TextView groupText;
//    }
//    private class ChildHolder {
//        TextView title, dateline, text;
//        AsyncRoundedImageView header;
//    }
//}
