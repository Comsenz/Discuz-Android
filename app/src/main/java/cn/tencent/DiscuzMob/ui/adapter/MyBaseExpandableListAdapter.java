package cn.tencent.DiscuzMob.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.model.AllForumBean;
import cn.tencent.DiscuzMob.model.CatlistBean;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2016/coolmonkey011/5.
 */
public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<CatlistBean> listGroup;
    private List<List<String>> listChild;
    private List<AllForumBean.VariablesBean.ForumlistBean> forumlist;

    public MyBaseExpandableListAdapter(Context mContext, List<CatlistBean> listGroup, List<List<String>> listChild, List<AllForumBean.VariablesBean.ForumlistBean> forumlist) {
        this.mContext = mContext;
        this.listGroup = listGroup;
        this.listChild = listChild;
        this.forumlist = forumlist;
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //我们这里返回一下每个item的名称，以便单击item时显示
        return listChild.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /*
     * 取得指定分组的子元素数
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return listChild.get(groupPosition).size();
    }

    /**
     * 取得与给定分组关联的数据
     */
    @Override
    public Object getGroup(int groupPosition) {
        return listGroup.get(groupPosition);
    }

    /**
     * 取得分组数
     */
    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    /**
     * 取得指定分组的ID.该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.groupitem, null);
            groupHolder = new GroupHolder();
            groupHolder.groupImg = (ImageView) convertView.findViewById(R.id.img_indicator);
            groupHolder.groupText = (TextView) convertView.findViewById(R.id.tv_tv_group_text);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        if (isExpanded) {
            groupHolder.groupImg.setBackgroundResource(R.drawable.right);
        } else {
            groupHolder.groupImg.setBackgroundResource(R.drawable.down);
        }
        groupHolder.groupText.setText(listGroup.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        ChildHolder childHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.often_item, null);
            childHolder = new ChildHolder();
            childHolder.tv_forumName = (TextView) convertView.findViewById(R.id.tv_forumName);
            childHolder.tv_themecount = (TextView) convertView.findViewById(R.id.tv_themecount);
            childHolder.tv_forumcount = (TextView) convertView.findViewById(R.id.tv_forumcount);
            childHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        for (int i = 0; i < forumlist.size(); i++) {
            if (forumlist.get(i).getFid().equals(listChild.get(groupPosition).get(childPosition).toString())) {
                childHolder.tv_forumName.setText(forumlist.get(i).getName());
                childHolder.tv_forumcount.setText(forumlist.get(i).getPosts());
                childHolder.tv_themecount.setText(forumlist.get(i).getThreads());
//                childHolder.tv_time.setText("");
            }
        }

        return convertView;
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class GroupHolder {
        ImageView groupImg;
        TextView groupText;
    }

    private class ChildHolder {
        private TextView tv_forumName, tv_themecount, tv_forumcount, tv_time;
    }

}