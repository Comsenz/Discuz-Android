package cn.tencent.DiscuzMob.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.model.Star;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/5/6.
 */
public class StarAdapter extends BaseAdapter {

    private List<Star> mStars;

    public StarAdapter() {
    }

    public void setData(List<Star> stars) {
        if (stars != null && !stars.isEmpty()) {
            this.mStars = stars;
            notifyDataSetChanged();
        }
    }

    public void append(List<Star> stars) {
        if (stars != null && !stars.isEmpty()) {
            if (mStars == null) {
                mStars = stars;
            } else {
                mStars.addAll(stars);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mStars == null ? 0 : mStars.size();
    }

    @Override
    public Object getItem(int position) {
        return mStars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_star, parent, false);
            holder = new ViewHolder();
            holder.header = (AsyncRoundedImageView) convertView.findViewById(R.id.header);
            holder.username = (TextView) convertView.findViewById(R.id.username);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Star star = mStars.get(position);
        holder.header.load(star.getAtavar(), R.drawable.ic_header_def);
        holder.username.setText(star.getUsername());
        return convertView;
    }

    static class ViewHolder {
        AsyncRoundedImageView header;
        TextView username;
    }

}
