package cn.tencent.DiscuzMob.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.tencent.DiscuzMob.model.HotTThread;
import cn.tencent.DiscuzMob.widget.AsyncImageView;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2015/5/11.
 * 板块列表
 */
public class ActivityForumDetailsAdapter extends BaseAdapter {

    private ArrayList<HotTThread> mThreads;

    public void setThreads(ArrayList<HotTThread> mThreads) {
        if (mThreads != null ) {
            this.mThreads = mThreads;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mThreads == null ? 0 : mThreads.size();
    }

    @Override
    public Object getItem(int position) {
        return mThreads.get(position);
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_thread_item, parent, false);
            holder.threadTitle = (TextView)convertView.findViewById(R.id.activity_title);
            holder.timeText = (TextView) convertView.findViewById(R.id.time_text);
            holder.addressText = (TextView) convertView.findViewById(R.id.address_text );
            holder.memeberSize = (TextView) convertView.findViewById(R.id.member_size );
            holder.mCover = (AsyncImageView) convertView.findViewById(R.id.activity_cover);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        HotTThread thread = mThreads.get(position);
        holder.threadTitle.setText(thread.getSubject());

        return convertView;
    }
    static class ViewHolder {
        TextView threadTitle;
        AsyncImageView mCover;
        TextView timeText;
        TextView addressText;
        TextView memeberSize;
    }
}
