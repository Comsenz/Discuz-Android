package cn.tencent.DiscuzMob.ui.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.model.ArticleSnap;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.widget.AsyncImageView;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/4/29.
 */
public class ArticleSnapAdapter extends BaseAdapter {

    private List<ArticleSnap> mList;

    public ArticleSnapAdapter() {
    }

    public void setData(List<ArticleSnap> list) {
        if (list != null && !list.isEmpty()) {
            this.mList = list;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_article_snap, parent, false);
            holder = new ViewHolder();
            holder.multi = ((ViewGroup) convertView).getChildAt(0);
            holder.simple = ((ViewGroup) convertView).getChildAt(1);
            holder.title = (TextView) holder.multi.findViewById(R.id.title);
            holder.title2 = (TextView) holder.simple.findViewById(R.id.title);
            holder.cover = (AsyncImageView) convertView.findViewById(R.id.cover);
            holder.desc = (TextView) convertView.findViewById(R.id.desc);
            holder.dateline = (TextView) convertView.findViewById(R.id.dateline);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ArticleSnap articleSnap = mList.get(position);
        if (position == 0) {
            boolean loadable = RedNetPreferences.getImageSetting() != 1;
            holder.cover.load(loadable ? articleSnap.getImgurl() : null);
            holder.title.setText(articleSnap.getTitle());
            holder.desc.setText(articleSnap.getSummary());
            holder.dateline.setText(Html.fromHtml(articleSnap.getDateline()));
        } else {
            holder.title2.setText(articleSnap.getTitle());
        }
        holder.multi.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        holder.simple.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
        return convertView;
    }

    static class ViewHolder {
        View multi, simple;
        AsyncImageView cover;
        TextView title, title2;
        TextView desc, dateline;
    }

}
