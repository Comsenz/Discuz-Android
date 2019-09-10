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
import cn.tencent.DiscuzMob.model.HisThemeBean;
import cn.tencent.DiscuzMob.ui.activity.ActivityThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.ThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.VoteThreadDetailsActivity;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/4/20.
 */

public class HisThemeAdapter extends BaseAdapter {
    private List<HisThemeBean.VariablesBean.ThreadlistBean> threadlist;


    public void setThreads(List<HisThemeBean.VariablesBean.ThreadlistBean> threadlist) {
        this.threadlist = threadlist;
        notifyDataSetChanged();
    }

    public void setAppendData(List<HisThemeBean.VariablesBean.ThreadlistBean> threadlist) {
        this.threadlist.addAll(threadlist);
        notifyDataSetChanged();
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

    private boolean isAdd = false;
    private List<String> tids;

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Viewholder allPostViewHolder = null;
        if (convertView == null) {
            allPostViewHolder = new Viewholder();
            convertView = View.inflate(parent.getContext(), R.layout.forum_item, null);
            allPostViewHolder.tv_subject = (TextView) convertView.findViewById(R.id.tv_subject);
            allPostViewHolder.tv_authorName = (TextView) convertView.findViewById(R.id.tv_authorName);
            allPostViewHolder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            allPostViewHolder.tv_look = (TextView) convertView.findViewById(R.id.tv_look);
            allPostViewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(allPostViewHolder);
        } else {
            allPostViewHolder = (Viewholder) convertView.getTag();
        }
        allPostViewHolder.tv_subject.setText(threadlist.get(position).getSubject());
        allPostViewHolder.tv_authorName.setText(threadlist.get(position).getAuthor());
        allPostViewHolder.tv_time.setText(threadlist.get(position).getDateline());
        allPostViewHolder.tv_comment.setText(threadlist.get(position).getReplies());
        allPostViewHolder.tv_look.setText(threadlist.get(position).getViews());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tid1 = threadlist.get(position).getTid();
                long count = Modal.getInstance().getUserAccountDao().getCount();
                List<ForumThreadlistBean> data = Modal.getInstance().getUserAccountDao().getScrollData(0, count);

                if (null != data) {
                    if (data.size() > 0) {
                        tids = new ArrayList<String>();
                        for (int i = 0; i < data.size(); i++) {
                            String tid = data.get(i).getTid();
                            tids.add(tid);
                        }
                        if (!tids.contains(tid1)) {
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
                    forumThreadlistBean.setAuthor(threadlist.get(position).getAuthor());
                    forumThreadlistBean.setAvatar("");
                    forumThreadlistBean.setTid(tid1);
                    forumThreadlistBean.setSpecial(threadlist.get(position).getSpecial());
                    forumThreadlistBean.setSubject(threadlist.get(position).getSubject());
                    forumThreadlistBean.setViews(threadlist.get(position).getViews());
                    forumThreadlistBean.setReplies(threadlist.get(position).getReplies());
                    forumThreadlistBean.setDateline(threadlist.get(position).getDateline());
                    forumThreadlistBean.setRecommend_add("0");
                    forumThreadlistBean.setDigest("0");
                    forumThreadlistBean.setDisplayorder("0");
                    forumThreadlistBean.setImglist(null);
                    forumThreadlistBean.setLevel("");
                    Modal.getInstance().getUserAccountDao().addAccount(forumThreadlistBean);
                }
                String special = threadlist.get(position).getSpecial();
                Class claz;
                special = TextUtils.isEmpty(special) ? "0" : special;
                if ("1".equals(special)) {
                    claz = VoteThreadDetailsActivity.class;
                } else if ("4".equals(special)) {
                    claz = ActivityThreadDetailsActivity.class;
                } else {
                    claz = ThreadDetailsActivity.class;
                }
                parent.getContext().startActivity(new Intent(parent.getContext(), claz).putExtra("fid", threadlist.get(position).getFid()).putExtra("id", threadlist.get(position).getTid()));
            }
        });
        return convertView;
    }

    class Viewholder {
        private TextView tv_subject, tv_authorName, tv_time, tv_comment, tv_look;
    }
}
