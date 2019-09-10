package cn.tencent.DiscuzMob.ui.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.ui.activity.ActivityThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.ThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.VoteThreadDetailsActivity;
import cn.tencent.DiscuzMob.utils.DateUtils;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.model.MessageRepliesBean;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/4/19.
 */

public class MessageRepliesAdapter extends BaseAdapter {
    private List<MessageRepliesBean.VariablesBean.ListBean> list;

    public MessageRepliesAdapter(List<MessageRepliesBean.VariablesBean.ListBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.sample_message_item, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.dateline = (TextView) convertView.findViewById(R.id.dateline);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            viewHolder.header = (AsyncRoundedImageView) convertView.findViewById(R.id.header);
            viewHolder.item0 = (LinearLayout) convertView.findViewById(R.id.item0);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String author = list.get(position).getAuthor();
        viewHolder.title.setText(author);
        final String dateline = list.get(position).getDateline();
        String timedate = DateUtils.timedate(dateline);
        viewHolder.dateline.setText(timedate);
        viewHolder.header.setImageResource(R.drawable.ic_message_sys);
//        viewHolder.text.setText("回复了您的帖子  " + list.get(position).getNotevar().getSubject());
        viewHolder.text.setText(list.get(position).getNote());
        viewHolder.item0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String special = list.get(position).getSpecial();
                Class claz;
                special = TextUtils.isEmpty(special) ? "0" : special;
                if ("1".equals(special)) {
                    claz = VoteThreadDetailsActivity.class;
                } else if ("4".equals(special)) {
                    claz = ActivityThreadDetailsActivity.class;
                } else {
                    claz = ThreadDetailsActivity.class;
                }
                parent.getContext().startActivity(new Intent(parent.getContext(), claz).putExtra("id", list.get(position).getFrom_id()));
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView title, dateline, text;
        AsyncRoundedImageView header;
        private LinearLayout item0;
    }
}
