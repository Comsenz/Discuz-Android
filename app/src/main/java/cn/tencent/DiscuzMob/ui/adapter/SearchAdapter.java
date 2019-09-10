package cn.tencent.DiscuzMob.ui.adapter;

import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.model.SearchBean;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/5/5.
 */
public class SearchAdapter extends BaseAdapter {

    private String mKey;
    private List<SearchBean.VariablesBean.ThreadlistBean> mData;

    public SearchAdapter(String keyword) {
        this.mKey = keyword;
    }

    public void setData(List<SearchBean.VariablesBean.ThreadlistBean > searches) {
        if (searches != null && !searches.isEmpty()) {
            mData = searches;
            notifyDataSetChanged();
        }
    }

    public void append(List<SearchBean.VariablesBean.ThreadlistBean > searches) {
        if (searches != null && !searches.isEmpty()) {
            if (mData == null) {
                mData = searches;
            } else {
                mData.addAll(searches);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_search, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.comments = (TextView) convertView.findViewById(R.id.comment);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SearchBean.VariablesBean.ThreadlistBean  data = mData.get(position);
        String title = data.getSubject();
        if (!TextUtils.isEmpty(title)) {
            title = title.replaceAll(mKey, new StringBuilder("<font color=\"#3c96d6\">").append(mKey).append("</font>").toString());
        } else {
            title = new StringBuilder("<font color=\"#3c96d6\">").append(mKey).append("</font>").toString();
        }
        holder.title.setText(Html.fromHtml(title));
        holder.date.setText(Html.fromHtml(data.getDateline()));
        holder.comments.setText(data.getReplies() + "个评论");
        return convertView;
    }

    static class ViewHolder {
        TextView title, date, comments;
    }

}
