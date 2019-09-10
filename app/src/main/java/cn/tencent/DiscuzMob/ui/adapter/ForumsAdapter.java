package cn.tencent.DiscuzMob.ui.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.widget.NoScrollGridView;
import cn.tencent.DiscuzMob.model.AllForumBean;
import cn.tencent.DiscuzMob.model.CatlistBean;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/5/9.
 */

public class ForumsAdapter extends BaseAdapter {
    private List<CatlistBean> listGroup;
    private List<List<String>> listChild;
    private List<AllForumBean.VariablesBean.ForumlistBean> forumlist;
    private Activity activity;
    private boolean isShow = true;

    public ForumsAdapter(FragmentActivity activity, List<CatlistBean> listGroup, List<List<String>> listChild, List<AllForumBean.VariablesBean.ForumlistBean> forumlist) {
        this.listGroup = listGroup;
        this.listChild = listChild;
        this.forumlist = forumlist;
        this.activity = activity;
    }

    public void cleanData() {
        if (listGroup != null) {
            listGroup.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return listGroup == null ? 0 : listGroup.size();
    }

    @Override
    public Object getItem(int position) {
        return listGroup == null ? null : listGroup.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listGroup == null ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.group_item, null);
            viewHolder.tv_group = (TextView) convertView.findViewById(R.id.tv_group);
            viewHolder.listview_item_gridview = (NoScrollGridView) convertView.findViewById(R.id.listview_item_gridview);
            viewHolder.iv_show = (ImageView) convertView.findViewById(R.id.iv_show);
            viewHolder.ll_group = (LinearLayout) convertView.findViewById(R.id.ll_group);
            viewHolder.rl_gridview = (RelativeLayout) convertView.findViewById(R.id.rl_gridview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_group.setText(listGroup.get(position).getName());
        final List<String> forums = listGroup.get(position).getForums();
        ForumsGrideAdapter forumsGrideAdapter = new ForumsGrideAdapter(activity, forums, forumlist);
        viewHolder.listview_item_gridview.setAdapter(forumsGrideAdapter);
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.ll_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = finalViewHolder.listview_item_gridview.getVisibility();
                if (isShow == true) {
                    isShow = false;
                    finalViewHolder.rl_gridview.setVisibility(View.GONE);
                    finalViewHolder.iv_show.setImageResource(R.drawable.right);
                } else {
                    isShow = true;
                    finalViewHolder.rl_gridview.setVisibility(View.VISIBLE);
                    finalViewHolder.iv_show.setImageResource(R.drawable.down);
                }
            }
        });
//        viewHolder.listview_item_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(parent.getContext(), ForumListActiviy.class);
//                intent.putExtra("threads", forumlist.get(position).getPosts());
//                intent.putExtra("todayposts", forumlist.get(position).getTodayposts());
//                intent.putExtra("fid", forums.get(position));
//                intent.putExtra("name", forumlist.get(position).getName());
//                mActivity.startActivity(intent);
//            }
//        });
        return convertView;
    }

    class ViewHolder {
        private TextView tv_group;
        private NoScrollGridView listview_item_gridview;
        private ImageView iv_show;
        private LinearLayout ll_group;
        private RelativeLayout rl_gridview;
    }
}
