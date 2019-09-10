package cn.tencent.DiscuzMob.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.tencent.DiscuzMob.model.SublistBean;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/5/24.
 */

public class ListDataAdapter extends BaseAdapter {
    private List<SublistBean> sublist;

    public void addData(List<SublistBean> sublist) {
        this.sublist = sublist;
    }

    @Override
    public int getCount() {
        return sublist == null ? 0 : sublist.size();
    }

    @Override
    public Object getItem(int position) {
        return sublist == null ? null : sublist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return sublist == null ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.often_item, null);
            viewHolder.iv_picture = (ImageView) convertView.findViewById(R.id.iv_picture);
            viewHolder.tv_themecount = (TextView) convertView.findViewById(R.id.tv_themecount);
            viewHolder.tv_forumcount = (TextView) convertView.findViewById(R.id.tv_forumcount);
            viewHolder.tv_forumName = (TextView) convertView.findViewById(R.id.tv_forumName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String icon = sublist.get(position).getIcon();
        int imageSetting = RedNetPreferences.getImageSetting();
        if (null != icon && !TextUtils.isEmpty(icon) && imageSetting == 0) {
            Picasso.with(parent.getContext())
                    .load(icon)
                    .into(viewHolder.iv_picture);
        } else {
            String todayposts = sublist.get(position).getTodayposts();
            if (todayposts.equals("0")) {
                viewHolder.iv_picture.setImageResource(R.drawable.old);
            } else {
                viewHolder.iv_picture.setImageResource(R.drawable.newp);
            }
        }
        viewHolder.tv_forumName.setText(sublist.get(position).getName());
        viewHolder.tv_forumcount.setText(sublist.get(position).getPosts());
        viewHolder.tv_themecount.setText(sublist.get(position).getThreads());
        return convertView;
    }

    class ViewHolder {
        private ImageView iv_picture;
        private TextView tv_themecount, tv_forumcount, tv_forumName;
    }

}
