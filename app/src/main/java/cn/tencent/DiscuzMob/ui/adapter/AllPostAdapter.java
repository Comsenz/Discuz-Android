package cn.tencent.DiscuzMob.ui.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.model.ForumThreadlistBean;
import cn.tencent.DiscuzMob.ui.activity.ActivityThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.ThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.VoteThreadDetailsActivity;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/4/11.
 */

public class AllPostAdapter extends BaseAdapter {
    List<ForumThreadlistBean> forum_threadlist = new ArrayList<>();
    String mName;
    String fid;


    public AllPostAdapter(List<ForumThreadlistBean> forum_threadlist, String name, String fid) {
        this.forum_threadlist.clear();
        this.forum_threadlist.addAll(forum_threadlist);
//        this.forum_threadlist = forum_threadlist;
        this.mName = name;
        this.fid = fid;
    }

    public void insertData(List<ForumThreadlistBean> listPart) {
        this.forum_threadlist.clear();
        this.forum_threadlist.addAll(listPart);
//        this.forum_threadlist = listPart;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return forum_threadlist == null ? 0 : forum_threadlist.size();
    }

    @Override
    public Object getItem(int position) {
        return forum_threadlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        AllPostViewHolder allPostViewHolder = null;
        if (convertView == null) {
            allPostViewHolder = new AllPostViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.forum_item, null);
            allPostViewHolder.tv_subject = (TextView) convertView.findViewById(R.id.tv_subject);
            allPostViewHolder.tv_authorName = (TextView) convertView.findViewById(R.id.tv_authorName);
            allPostViewHolder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            allPostViewHolder.tv_look = (TextView) convertView.findViewById(R.id.tv_look);
            allPostViewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            allPostViewHolder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
            convertView.setTag(allPostViewHolder);
        } else {
            allPostViewHolder = (AllPostViewHolder) convertView.getTag();
        }
        allPostViewHolder.tv_subject.setText(forum_threadlist.get(position).getSubject());
        allPostViewHolder.tv_authorName.setText(forum_threadlist.get(position).getAuthor());
        allPostViewHolder.tv_time.setText(forum_threadlist.get(position).getDateline());
        allPostViewHolder.tv_comment.setText(forum_threadlist.get(position).getReplies());
        allPostViewHolder.tv_look.setText(forum_threadlist.get(position).getViews());

        allPostViewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String special = forum_threadlist.get(position).getSpecial();
                Class claz;
                special = TextUtils.isEmpty(special) ? "0" : special;
                if ("1".equals(special)) {
                    claz = VoteThreadDetailsActivity.class;
                } else if ("4".equals(special)) {
                    claz = ActivityThreadDetailsActivity.class;
                } else {
                    claz = ThreadDetailsActivity.class;
                }
                Intent intent = new Intent(parent.getContext(), claz);
                intent.putExtra("id", forum_threadlist.get(position).getTid());
                intent.putExtra("title", mName);
                intent.putExtra("fid", fid);
//                parent.getContext().startActivity(new Intent(parent.getContext(), ThreadDetailsActivity.class).putExtra("id", forum_threadlist.get(position).getTid()));
                parent.getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    class AllPostViewHolder {
        private TextView tv_subject, tv_authorName, tv_time, tv_comment, tv_look;
        private LinearLayout ll_item;
    }
}
