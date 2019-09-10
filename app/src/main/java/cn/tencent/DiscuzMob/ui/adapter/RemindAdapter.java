package cn.tencent.DiscuzMob.ui.adapter;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.model.RemindBean;
import cn.tencent.DiscuzMob.utils.DateUtils;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/4/19.
 */

public class RemindAdapter extends BaseAdapter {
    List<RemindBean.VariablesBean.ListBean> list;

    public void setThreads(List<RemindBean.VariablesBean.ListBean> list) {
        if (list != null && list != null) {
            this.list = list;
            notifyDataSetChanged();
        }
    }

    public void setAppendData(List<RemindBean.VariablesBean.ListBean> threads) {
        if (threads != null && list != null) {
            if (list == null) {
                list = threads;
            } else {
                list.addAll(threads);
            }
            notifyDataSetChanged();
        }
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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String message = list.get(position).getMessage();

        viewHolder.text.setText(Html.fromHtml(message));
        viewHolder.title.setText(list.get(position).getAuthor());
        String timedate = DateUtils.timedate(list.get(position).getDateline());
        viewHolder.header.setImageResource(R.drawable.ic_header_def);
        viewHolder.dateline.setText(timedate);
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String special = list.get(position).getSpecial();
//                Class claz;
//                special = TextUtils.isEmpty(special) ? "0" : special;
//                if ("1".equals(special)) {
//                    claz = VoteThreadDetailsActivity.class;
//                } else if ("0".equals(special)) {
//                    claz = ThreadDetailsActivity.class;
//                } else {
//                    claz = ActivityThreadDetailsActivity.class;
//                }
//                Intent intent = new Intent(parent.getContext(), claz);
//                intent.putExtra("id", list.get(position).getFrom_id());
//                intent.putExtra("title", "");
//                intent.putExtra("fid", "");
//                parent.getContext().startActivity(intent);
//            }
//        });
        return convertView;
    }

    class ViewHolder {
        TextView title, dateline, text;
        AsyncRoundedImageView header;
    }
}
