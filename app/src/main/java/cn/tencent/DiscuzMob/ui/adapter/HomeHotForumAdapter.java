package cn.tencent.DiscuzMob.ui.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.ui.activity.VoteThreadDetailsActivity;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.model.Hot;
import cn.tencent.DiscuzMob.ui.activity.ActivityThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.ThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.UserDetailActivity;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/4/7.
 */

public class HomeHotForumAdapter extends BaseAdapter {
    List<Hot.VariablesBean.DataBean> data;
    ArrayList<Integer> drawables = new ArrayList<>();

    public HomeHotForumAdapter(List<Hot.VariablesBean.DataBean> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
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
            convertView = View.inflate(parent.getContext(), R.layout.hot_item, null);
            viewHoler.forumName = (TextView) convertView.findViewById(R.id.tv_forumName);
            viewHoler.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHoler.tv_authorName = (TextView) convertView.findViewById(R.id.tv_authorName);
            viewHoler.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            viewHoler.tv_look = (TextView) convertView.findViewById(R.id.tv_look);
            viewHoler.tv_thumbs = (TextView) convertView.findViewById(R.id.tv_thumbs);
            viewHoler.gridView = (GridView) convertView.findViewById(R.id.glide);
            viewHoler.iv_picture = (AsyncRoundedImageView) convertView.findViewById(R.id.iv_picture);
            viewHoler.ll_hot = (LinearLayout) convertView.findViewById(R.id.ll_hot);
            convertView.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        viewHoler.forumName.setText(data.get(position).getSubject());
        viewHoler.tv_authorName.setText(data.get(position).getAuthor());
        viewHoler.tv_time.setText(data.get(position).getDateline());
        viewHoler.tv_look.setText(data.get(position).getViews());
        viewHoler.tv_thumbs.setText(data.get(position).getRecommends());
        viewHoler.tv_comment.setText(data.get(position).getReplies());
        viewHoler.iv_picture.load(data.get(position).getAvatar(), R.drawable.ic_header_def);
        GridAdapter gridAdapter = null;
        if (data.get(position).getImglist().size() < 4) {
            gridAdapter = new GridAdapter(data.get(position).getImglist());
        } else {
            List<String> imgList = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                String s = data.get(position).getImglist().get(i);
                imgList.add(s);
            }
            gridAdapter = new GridAdapter(imgList);
        }

        viewHoler.gridView.setAdapter(gridAdapter);
        viewHoler.iv_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.getContext().startActivity(new Intent(parent.getContext(), UserDetailActivity.class).putExtra("userId", data.get(position).getAuthorid()));
            }
        });
        viewHoler.ll_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String special = data.get(position).getSpecial();
                Class claz;
                special = TextUtils.isEmpty(special) ? "0" : special;
                if ("1".equals(special)) {
                    claz = VoteThreadDetailsActivity.class;
                } else if ("4".equals(special)) {
                    claz = ActivityThreadDetailsActivity.class;
                } else {
                    claz = ThreadDetailsActivity.class;
                }
                Intent intent = new Intent(parent.getContext(), claz);
                intent.putExtra("id", data.get(position).getTid());
                intent.putExtra("title", "精彩热帖");
                parent.getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHoler {
        private TextView forumName, tv_time, tv_authorName, tv_comment, tv_look, tv_thumbs;
        private GridView gridView;
        private AsyncRoundedImageView iv_picture;
        private LinearLayout ll_hot;
    }
}
