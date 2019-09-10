package cn.tencent.DiscuzMob.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.tencent.DiscuzMob.model.HotForumBean;
import cn.tencent.DiscuzMob.ui.activity.ForumListActivity;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/4/6.
 */

public class HotForumAdapter extends BaseAdapter {
    Context appContext;
    List<HotForumBean.VariablesBean.DataBean> data;

    public void cleanData() {
        if (null != data) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    public void addData(List<HotForumBean.VariablesBean.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getFid();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHoler viewHoler = null;
        if (convertView == null) {
            viewHoler = new ViewHoler();
//            convertView = View.inflate(parent.getContext(), R.layout.often_item, null);
            convertView = View.inflate(parent.getContext(), R.layout.grid_item, null);
            viewHoler.forumName = (TextView) convertView.findViewById(R.id.tv_forumName);
            viewHoler.tv_themecount = (TextView) convertView.findViewById(R.id.tv_themecount);
            viewHoler.tv_todaycount = (TextView) convertView.findViewById(R.id.tv_todaycount);
            viewHoler.rl_glide = (LinearLayout) convertView.findViewById(R.id.rl_glide);
            viewHoler.iv_picture = (ImageView) convertView.findViewById(R.id.iv_picture);
            convertView.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        viewHoler.forumName.setText(data.get(position).getName());
        String threads = data.get(position).getThreads();
        if (Integer.parseInt(threads) > 9999 && Integer.parseInt(threads) < 100000000) {
            int i1 = Integer.parseInt(threads) / 10000;
            viewHoler.tv_themecount.setText("主题:" + i1 + "万");
        } else if (Integer.parseInt(threads) > 99999999) {
            int i1 = Integer.parseInt(threads) / 100000000;
            viewHoler.tv_themecount.setText("主题:" + i1 + "亿");
        } else {
            viewHoler.tv_themecount.setText("主题:" + threads);
        }
        String icon = data.get(position).getIcon();
        final String todayposts = data.get(position).getTodayposts();
        int imageSetting = RedNetPreferences.getImageSetting();
        if (null != icon && !TextUtils.isEmpty(icon) && imageSetting != 1) {
            Picasso.with(parent.getContext())
                    .load(icon)
                    .into(viewHoler.iv_picture);
        } else {
            if (!todayposts.equals("0")) {
                viewHoler.iv_picture.setImageResource(R.drawable.newforum);
            } else {
                viewHoler.iv_picture.setImageResource(R.drawable.oldforum);
            }
        }
        if (Integer.parseInt(todayposts) > 0) {
            viewHoler.tv_todaycount.setVisibility(View.VISIBLE);
            if (Integer.parseInt(todayposts) > 9999 && Integer.parseInt(todayposts) < 100000000) {
                int i1 = Integer.parseInt(todayposts) / 10000;
                viewHoler.tv_todaycount.setText(i1 + "万");
            } else if (Integer.parseInt(todayposts) > 99999999) {
                int i1 = Integer.parseInt(todayposts) / 100000000;
                viewHoler.tv_todaycount.setText(i1 + "亿");
            } else {
                viewHoler.tv_todaycount.setText("(" + todayposts + ")");
            }
        } else {
            viewHoler.tv_todaycount.setVisibility(View.GONE);
        }
//        for(int i = 0; i < forumlist.size(); i++) {
//          if(data.get(position).getFid().equals(forumlist.get(i).getFid())) {
//              viewHoler.tv_forumcount.setText(forumlist.get(i).getPosts());
//              break;
//          }
//        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), ForumListActivity.class);
                intent.putExtra("threads", data.get(position).getThreads());
                intent.putExtra("todayposts", todayposts);
                intent.putExtra("fid", data.get(position).getFid());
                intent.putExtra("name", data.get(position).getName());
                parent.getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHoler {
        private TextView forumName, tv_themecount, tv_todaycount;
        private LinearLayout rl_glide;
        private ImageView iv_picture;
    }
}
