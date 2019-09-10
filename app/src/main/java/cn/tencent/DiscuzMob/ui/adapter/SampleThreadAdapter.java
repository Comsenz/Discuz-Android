package cn.tencent.DiscuzMob.ui.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.model.HotTThread;
import cn.tencent.DiscuzMob.R;

/**
 * Created by kurt on 2015/6/19.
 * 收藏帖子列表
 */
public class SampleThreadAdapter extends BaseAdapter {

    private List<HotTThread> mThreadList;

    public void setThreads(List<HotTThread> mThreadList) {
        if (mThreadList != null && mThreadList != null) {
            this.mThreadList = mThreadList;
            notifyDataSetChanged();
        }
    }

    public void setAppendData(List<HotTThread> threads) {
        if (threads != null && mThreadList != null) {
            if (mThreadList == null) {
                mThreadList = threads;
            } else {
                mThreadList.addAll(threads);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mThreadList == null ? 0 : mThreadList.size();
    }

    @Override
    public Object getItem(int position) {
        return mThreadList.get(position);
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_thread_item, parent, false);
            holder.threadTitle = (TextView) convertView.findViewById(R.id.fav_thread_title);
            holder.forumName = (TextView) convertView.findViewById(R.id.forum_name);
            holder.postTime = (TextView) convertView.findViewById(R.id.post_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HotTThread thread = mThreadList.get(position);
        holder.threadTitle.setText(thread.getSubject());
        holder.forumName.setText(thread.getAuthor());
        holder.postTime.setText(Html.fromHtml(thread.getDateline()));
        return convertView;
    }

    static class ViewHolder {
        TextView threadTitle;
        TextView forumName;
        TextView postTime;
    }

}
