package cn.tencent.DiscuzMob.ui.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.db.Modal;
import cn.tencent.DiscuzMob.model.ForumThreadlistBean;
import cn.tencent.DiscuzMob.model.ReplyBean;
import cn.tencent.DiscuzMob.model.ThreadlistBean;
import cn.tencent.DiscuzMob.model.UserRepliesBean;
import cn.tencent.DiscuzMob.ui.activity.ActivityThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.ThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.VoteThreadDetailsActivity;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/7/11.
 */

public class ReplayAdapter extends BaseAdapter {
    private List<UserRepliesBean.VariablesBean.DataBean> reply;
    private ArrayList<String> tids;
    private boolean isAdd = false;

//    public ReplayAdapter(List<UserRepliesBean.VariablesBean.DataBean> reply) {
//        this.reply = reply;
//    }

    public void appendData(List<UserRepliesBean.VariablesBean.DataBean> reply) {
        this.reply = reply;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return reply == null ? 0 : reply.size();
    }

    @Override
    public Object getItem(int position) {
        return reply == null ? null : reply.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.reply_item, null);
            viewHolder.post_content = (TextView) convertView.findViewById(R.id.content);
            viewHolder.post_time = (TextView) convertView.findViewById(R.id.post_time);
            viewHolder.thread_name = (TextView) convertView.findViewById(R.id.thread_name);
            viewHolder.tv_position = (TextView) convertView.findViewById(R.id.tv_position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.post_content.setText(reply.get(position).getComments());
        viewHolder.post_time.setText(reply.get(position).getDateline());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long count = Modal.getInstance().getUserAccountDao().getCount();
                List<ForumThreadlistBean> data = Modal.getInstance().getUserAccountDao().getScrollData(0, count);

                if (null != data) {
                    if (data.size() > 0) {
                        tids = new ArrayList<String>();
                        for (int i = 0; i < data.size(); i++) {
                            String tid = data.get(i).getTid();
                            tids.add(tid);
                        }
                        if (!tids.contains(reply.get(position).getTid())) {
                            isAdd = true;
                        } else {
                            isAdd = false;
                        }
                    } else {
                        isAdd = true;
                    }

                } else {
                    isAdd = false;
                }
                if (isAdd == true) {
                    ForumThreadlistBean forumThreadlistBean = new ForumThreadlistBean();
                    forumThreadlistBean.setAvatar("");
                    forumThreadlistBean.setTid(reply.get(position).getTid());
                    forumThreadlistBean.setRecommend_add("");
                    forumThreadlistBean.setImglist(null);
                    forumThreadlistBean.setLevel("");
                    Modal.getInstance().getUserAccountDao().addAccount(forumThreadlistBean);
                }
//            Intent intent = new Intent(parent.getContext(), claz);
//                intent.putExtra("id", reply.get(position).getTid());
//                intent.putExtra("title",subjects.get(position).getForumname());
//                intent.putExtra("fid", reply.get(position).getFid());
//                parent.getContext().startActivity(intent);
            }

        });
        return convertView;
    }

    class ViewHolder {
        TextView post_time, post_content, thread_name, tv_position;
    }
}
