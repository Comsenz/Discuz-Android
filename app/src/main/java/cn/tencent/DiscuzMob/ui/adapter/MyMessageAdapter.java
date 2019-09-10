package cn.tencent.DiscuzMob.ui.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.model.MyMessage;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

public class MyMessageAdapter extends BaseAdapter {

    private List<?> mMessageList;

    public MyMessageAdapter() {
    }

    public void setData(List list) {
        if (list != null && !list.isEmpty()) {
            this.mMessageList = list;
            notifyDataSetChanged();
        }
    }

    public void appendData(List list) {
        if (list != null && !list.isEmpty()) {
            if (mMessageList == null) {
                mMessageList = list;
            } else {
                mMessageList.addAll(list);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mMessageList == null ? 2 : mMessageList.size() + 2;
    }

    @Override
    public Object getItem(int position) {
        return position > 1 ? mMessageList.get(position - 2) : null;
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_message_item, parent, false);
            holder.item1 = convertView.findViewById(R.id.item1);
            holder.item2 = convertView.findViewById(R.id.item2);
            holder.header = (AsyncRoundedImageView) convertView.findViewById(R.id.header);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.dateline = (TextView) convertView.findViewById(R.id.dateline);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position < 2) {
            holder.header.load(null, R.drawable.ic_message_sys);
            ((TextView) holder.item1).setText(position == 0 ? "系统消息" : "我的通知");
            holder.item1.setVisibility(View.VISIBLE);
            holder.item2.setVisibility(View.GONE);
        } else {
            Object object = mMessageList.get(position - 2);
            if (object instanceof MyMessage) {
                MyMessage message = (MyMessage) object;
                holder.header.load(message.getToatavar(), R.drawable.ic_header_def);
                holder.title.setText(message.getTousername());
                holder.text.setText(Html.fromHtml(message.getSubject()));
                holder.dateline.setText(RednetUtils.DateFormat.MILLI2.dateFormat
                        .format(RednetUtils.covertMillSeconds(String.valueOf(message.getDateline()))));
            }
            holder.item1.setVisibility(View.GONE);
            holder.item2.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    static class ViewHolder {
        View item1, item2;
        AsyncRoundedImageView header;
        TextView title;
        TextView text;
        TextView dateline;
    }

}
