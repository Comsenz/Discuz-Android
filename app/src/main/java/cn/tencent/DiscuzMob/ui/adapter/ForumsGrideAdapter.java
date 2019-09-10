package cn.tencent.DiscuzMob.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.tencent.DiscuzMob.ui.activity.ForumListActivity;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.model.AllForumBean;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/5/9.
 */

public class ForumsGrideAdapter extends BaseAdapter {
    List<String> listChild;
    //    List<List<String>> listChild;
    List<AllForumBean.VariablesBean.ForumlistBean> forumlist;
    private Activity activity;

    public ForumsGrideAdapter(Activity activity, List<String> listChild, List<AllForumBean.VariablesBean.ForumlistBean> forumlist) {
        this.listChild = listChild;
        this.forumlist = forumlist;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listChild == null ? 0 : listChild.size();
    }

    @Override
    public Object getItem(int position) {
        return listChild == null ? null : listChild.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listChild == null ? 0 : position;
    }

    String posts;
    String name;
    String todayposts;
    String threads;

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.grid_item, null);
            viewHolder.forumName = (TextView) convertView.findViewById(R.id.tv_forumName);
            viewHolder.tv_themecount = (TextView) convertView.findViewById(R.id.tv_themecount);
//            viewHolder.tv_forumcount = (TextView) convertView.findViewById(R.id.tv_forumcount);
            viewHolder.tv_todaycount = (TextView) convertView.findViewById(R.id.tv_todaycount);
//            viewHoler.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.rl_glide = (LinearLayout) convertView.findViewById(R.id.rl_glide);
            viewHolder.iv_picture = (ImageView) convertView.findViewById(R.id.iv_picture);
            viewHolder.iv_picture.setScaleType(ImageView.ScaleType.FIT_CENTER);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final String s = listChild.get(position);
        for (int i = 0; i < forumlist.size(); i++) {
            AllForumBean.VariablesBean.ForumlistBean forumlistBean = forumlist.get(i);
            String fid = forumlistBean.getFid();
            if (null != s && !TextUtils.isEmpty(s) & null != fid && !TextUtils.isEmpty(fid) && s.equals(fid)) {
                name = forumlistBean.getName();
                threads = forumlistBean.getThreads();
                if (Integer.parseInt(threads) > 9999 && Integer.parseInt(threads) < 100000000) {
                    int i1 = Integer.parseInt(threads) / 10000;
                    viewHolder.tv_themecount.setText("主题:" + i1 + "万");
                } else if (Integer.parseInt(threads) > 99999999) {
                    int i1 = Integer.parseInt(threads) / 100000000;
                    viewHolder.tv_themecount.setText("主题:" + i1 + "亿");
                } else {
                    viewHolder.tv_themecount.setText("主题:" + threads);
                }

                posts = forumlistBean.getPosts();
//                viewHolder.tv_forumcount.setText(posts);
                todayposts = forumlistBean.getTodayposts();
                if (Integer.parseInt(todayposts) > 0) {
                    viewHolder.tv_todaycount.setVisibility(View.VISIBLE);
                    if (Integer.parseInt(todayposts) > 9999 && Integer.parseInt(todayposts) < 100000000) {
                        int i1 = Integer.parseInt(todayposts) / 10000;
                        viewHolder.tv_todaycount.setText(i1 + "万");
                    } else if (Integer.parseInt(todayposts) > 99999999) {
                        int i1 = Integer.parseInt(todayposts) / 100000000;
                        viewHolder.tv_todaycount.setText(i1 + "亿");
                    } else {
                        viewHolder.tv_todaycount.setText("(" + todayposts + ")");
                    }


                } else {
                    viewHolder.tv_todaycount.setVisibility(View.GONE);
                }
                viewHolder.forumName.setText(name);
                String icon = forumlistBean.getIcon();
                int imageSetting = RedNetPreferences.getImageSetting();
                if (null != icon && !TextUtils.isEmpty(icon) && imageSetting != 1) {
                    Picasso.with(parent.getContext())
                            .load(icon)
                            .into(viewHolder.iv_picture);
                } else {
                    String todayposts = forumlistBean.getTodayposts();
                    if (!todayposts.equals("0")) {
                        viewHolder.iv_picture.setImageResource(R.drawable.newforum);
                    } else if (todayposts.equals("0")) {
                        viewHolder.iv_picture.setImageResource(R.drawable.oldforum);
                    }
                }
            }
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.rl_glide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), ForumListActivity.class);
                String s1 = finalViewHolder.tv_themecount.getText().toString();
                int visibility = finalViewHolder.tv_todaycount.getVisibility();
                String todayposts;
                if (visibility == View.VISIBLE) {
                    String today = finalViewHolder.tv_todaycount.getText().toString();
                    Log.e("TAG", "today=" + today);
                    todayposts = today.substring(1, today.length() - 1);
                } else {
                    todayposts = "0";
                }

                String substring = s1.substring(3, s1.length());
                intent.putExtra("threads", substring);
                intent.putExtra("todayposts", todayposts);
                intent.putExtra("fid", s);
                intent.putExtra("name", finalViewHolder.forumName.getText());
                activity.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView forumName, tv_themecount, tv_todaycount;
        private LinearLayout rl_glide;
        private ImageView iv_picture;
    }
}
