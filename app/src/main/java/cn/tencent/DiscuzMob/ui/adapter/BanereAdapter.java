package cn.tencent.DiscuzMob.ui.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.model.Banere;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/4/29.
 */
public class BanereAdapter extends BaseAdapter {

    public List<Banere> mBaneres;

    public BanereAdapter() {
    }

    public void setData(List<Banere> list) {
        if (list != null && !list.isEmpty()) {
            this.mBaneres = list;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mBaneres != null ? mBaneres.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mBaneres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_banere, parent, false);
            holder = new Holder();
            holder.cover = (AsyncRoundedImageView) convertView.findViewById(R.id.cover);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Banere banere = mBaneres.get(position);
        holder.title.setText(banere.getName());
        String cover = banere.getImage();
        holder.cover.load(!TextUtils.isEmpty(cover) ? cover : null);
        return convertView;
    }

    static class Holder {
        AsyncRoundedImageView cover;
        TextView title;
    }

}
