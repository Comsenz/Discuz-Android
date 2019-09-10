package cn.tencent.DiscuzMob.ui.adapter;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.db.Modal;
import cn.tencent.DiscuzMob.model.ForumThreadlistBean;
import cn.tencent.DiscuzMob.model.UserThreadsBean;
import cn.tencent.DiscuzMob.ui.activity.ActivityThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.ThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.VoteThreadDetailsActivity;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/4/19.
 */

public class UserThreadsAdapter extends BaseAdapter {
    private List<UserThreadsBean.VariablesBean.DataBean> data;

    public UserThreadsAdapter(List<UserThreadsBean.VariablesBean.DataBean> data) {
        this.data = data;
    }

    public void setAppendData(List<UserThreadsBean.VariablesBean.DataBean> threads) {
        if (threads != null && data != null) {
            if (data == null) {
                data = threads;
            } else {
                data.addAll(threads);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
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
            allPostViewHolder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
            convertView.setTag(allPostViewHolder);
        } else {
            allPostViewHolder = (Viewholder) convertView.getTag();
        }
        if (data.get(position).getDisplayorder().equals("-2")){
            allPostViewHolder.tv_subject.setText(data.get(position).getSubject()+"(审核中)");
        }else if(data.get(position).getDisplayorder().equals("-1")){
            allPostViewHolder.tv_subject.setText(data.get(position).getSubject()+"(回收站)");
        }else {
            allPostViewHolder.tv_subject.setText(data.get(position).getSubject());
        }

        allPostViewHolder.tv_authorName.setText(data.get(position).getAuthor());
        allPostViewHolder.tv_time.setText(Html.fromHtml(data.get(position).getDateline()));
        allPostViewHolder.tv_comment.setText(data.get(position).getReplies());
        allPostViewHolder.tv_look.setText(data.get(position).getViews());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tid1 = data.get(position).getTid();
                long count = Modal.getInstance().getUserAccountDao().getCount();
                List<ForumThreadlistBean> datas = Modal.getInstance().getUserAccountDao().getScrollData(0, count);

                if (null != datas) {
                    if (datas.size() > 0) {
                        tids = new ArrayList<String>();
                        for (int i = 0; i < datas.size(); i++) {
                            String tid = datas.get(i).getTid();
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
                    forumThreadlistBean.setAuthor(data.get(position).getAuthor());
                    forumThreadlistBean.setAvatar("");
                    forumThreadlistBean.setTid(tid1);
                    forumThreadlistBean.setSpecial(data.get(position).getSpecial());
                    forumThreadlistBean.setSubject(data.get(position).getSubject());
                    forumThreadlistBean.setViews(data.get(position).getViews());
                    forumThreadlistBean.setReplies(data.get(position).getReplies());
                    forumThreadlistBean.setDateline(data.get(position).getDateline());
                    forumThreadlistBean.setRecommend_add(data.get(position).getRecommend_add());
                    forumThreadlistBean.setDigest(data.get(position).getDigest());
                    forumThreadlistBean.setDisplayorder(data.get(position).getDisplayorder());
                    forumThreadlistBean.setImglist(null);
                    forumThreadlistBean.setLevel("");
                    Modal.getInstance().getUserAccountDao().addAccount(forumThreadlistBean);
                }
                String special = data.get(position).getSpecial();
                Class claz;
                special = TextUtils.isEmpty(special) ? "0" : special;
                if ("1".equals(special)) {
                    claz = VoteThreadDetailsActivity.class;
                } else if ("4".equals(special)) {
                    claz = ActivityThreadDetailsActivity.class;
                } else {
                    claz = ThreadDetailsActivity.class;
                }
                parent.getContext().startActivity(new Intent(parent.getContext(), claz).putExtra("fid", data.get(position).getFid()).putExtra("id", data.get(position).getTid()));
            }
        });
        return convertView;
    }

    class Viewholder {
        private TextView tv_subject, tv_authorName, tv_time, tv_comment, tv_look;
        private LinearLayout ll_item;
    }
}
