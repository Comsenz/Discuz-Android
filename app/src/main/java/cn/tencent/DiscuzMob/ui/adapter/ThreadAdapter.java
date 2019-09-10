package cn.tencent.DiscuzMob.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.model.EssenceBean;
import cn.tencent.DiscuzMob.model.ForumThreadlistBean;
import cn.tencent.DiscuzMob.model.ImglistBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.activity.ActivityThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.ThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.VoteThreadDetailsActivity;
import cn.tencent.DiscuzMob.utils.DateUtils;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;

/**
 * Created by cg on 2017/6/16.
 */

public class ThreadAdapter extends BaseAdapter {
    private List<ForumThreadlistBean> list = new ArrayList<>();
    private List<ImglistBean> strings = new ArrayList<>();
    private Context mContext;
    private Activity activity;

    public ThreadAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    public void setData(List<ForumThreadlistBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void cleanData() {
        if (list != null) {
            list.clear();
            notifyDataSetChanged();
        }

    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list == null ? 0 : position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.recommen_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int imageSetting = RedNetPreferences.getImageSetting();
        final ForumThreadlistBean forumThreadlistBean = list.get(position);
        LogUtils.d("ForumThreadlistBean = " + forumThreadlistBean.toString());

        if (imageSetting == 0) {
            viewHolder.avatar_author.load(forumThreadlistBean.getAvatar(), R.drawable.ic_header_def);
        } else {
            viewHolder.avatar_author.setImageResource(R.drawable.ic_header_def);
        }

        viewHolder.tv_authorName.setText(forumThreadlistBean.getAuthor());
        // viewHolder.tv_authorName.setText("姓名特别长");
        String displayorder = forumThreadlistBean.getDisplayorder();
        String digest = forumThreadlistBean.getDigest();
        if (null != displayorder && !displayorder.equals("0")) {
            viewHolder.tv_gradle.setText("置顶");
        } else if (null != digest && !digest.equals("0")) {
            viewHolder.tv_gradle.setText("精华");
        } else {
            viewHolder.tv_gradle.setVisibility(View.GONE);
        }
        final String subject = forumThreadlistBean.getSubject();
        LogUtils.d("subject = " + subject);
        viewHolder.tv_subject.setText(subject);
        String dateline = forumThreadlistBean.getDateline();
        boolean numeric = RednetUtils.isNumeric(dateline);
        if (numeric) {
            String timedate = DateUtils.timedate(dateline);
            viewHolder.tv_time.setText(timedate);
        } else {
            viewHolder.tv_time.setText(dateline);
        }

        viewHolder.tv_views.setText(forumThreadlistBean.getViews());
        viewHolder.tv_comment.setText(forumThreadlistBean.getReplies());
        viewHolder.tv_zan.setText(forumThreadlistBean.getRecommend_add());
        String level = forumThreadlistBean.getLevel();
        if (null != level && !TextUtils.isEmpty(level)) {
            viewHolder.tv_leve.setVisibility(View.VISIBLE);
            viewHolder.tv_leve.setText(RednetUtils.delHTMLTag(level));
        } else {
            viewHolder.tv_leve.setVisibility(View.GONE);
        }

        List<EssenceBean.VariablesBean.DataBean.AttachlistBean> imglist = forumThreadlistBean.getImglist();
        viewHolder.tv_hot.setVisibility(View.GONE);
        if (null != imglist && imglist.size() > 0) {
            viewHolder.ll_img.setVisibility(View.VISIBLE);
            if (imglist.size() == 1) {
                showImage(parent,imglist.get(0).getAttachment() , viewHolder.iv_img1,imglist,0);
                viewHolder.iv_img1.setVisibility(View.VISIBLE);
                viewHolder.iv_img2.setVisibility(View.GONE);
                viewHolder.iv_img3.setVisibility(View.GONE);
            } else if (imglist.size() == 2) {
                showImage(parent,imglist.get(0).getAttachment() , viewHolder.iv_img1,imglist,0);
                showImage(parent,imglist.get(1).getAttachment() , viewHolder.iv_img2,imglist,1);
                viewHolder.iv_img1.setVisibility(View.VISIBLE);
                viewHolder.iv_img2.setVisibility(View.VISIBLE);
                viewHolder.iv_img3.setVisibility(View.GONE);
            } else {
                showImage(parent,imglist.get(0).getAttachment() , viewHolder.iv_img1,imglist,0);
                showImage(parent,imglist.get(1).getAttachment() , viewHolder.iv_img2,imglist,1);
                showImage(parent,imglist.get(2).getAttachment() , viewHolder.iv_img3,imglist,2);
                viewHolder.iv_img1.setVisibility(View.VISIBLE);
                viewHolder.iv_img2.setVisibility(View.VISIBLE);
                viewHolder.iv_img3.setVisibility(View.VISIBLE);
            }
        } else {
            viewHolder.ll_img.setVisibility(View.GONE);

        }
//        viewHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                String special = list.get(position).getSpecial();
//                Class claz;
//                special = TextUtils.isEmpty(special) ? "0" : special;
//                if ("1".equals(special)) {
//                    claz = VoteThreadDetailsActivity.class;
//                } else if ("4".equals(special)) {
//                    claz = ActivityThreadDetailsActivity.class;
//                } else {
//                    claz = ThreadDetailsActivity.class;
//                }
//                Intent intent = new Intent(parent.getContext(), claz);
//                intent.putExtra("id", list.get(position).getTid());
//                intent.putExtra("title", list.get(position).getSubject());
//                intent.putExtra("fid", "");
//                parent.getContext().startActivity(intent);
//            }
//        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String special = forumThreadlistBean.getSpecial();
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
                intent.putExtra("id", forumThreadlistBean.getTid());
                intent.putExtra("title", subject);
                intent.putExtra("fid", "");
                activity.startActivity(intent);
            }
        });
        return convertView;
    }
    private void showImage(ViewGroup parent, String url,ImageView imageView, List<EssenceBean.VariablesBean.DataBean.AttachlistBean> imglist,int position) {
        if (imglist.get(position).getType().equals("image")){
            Picasso.with(parent.getContext())
                    .load(url)
                    .error(R.drawable.picture)
                    .into(imageView);
        }
    }

    class ViewHolder {
        private AsyncRoundedImageView avatar_author;
        private TextView tv_authorName, tv_leve, tv_gradle, tv_subject, tv_time, tv_hot, tv_views, tv_comment, tv_zan;
        //        private NoScrollGridView gridView;
        private ImageView iv_img1, iv_img2, iv_img3;
        private LinearLayout ll_recommen;
        private LinearLayout ll_img;

        public ViewHolder(View rootView) {
            avatar_author = (AsyncRoundedImageView) rootView.findViewById(R.id.avatar_author);
            tv_authorName = (TextView) rootView.findViewById(R.id.tv_authorName);
            tv_leve = (TextView) rootView.findViewById(R.id.tv_leve);
            tv_gradle = (TextView) rootView.findViewById(R.id.tv_gradle);
            tv_subject = (TextView) rootView.findViewById(R.id.tv_subject);
            tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            tv_hot = (TextView) rootView.findViewById(R.id.tv_hot);
            tv_views = (TextView) rootView.findViewById(R.id.tv_views);
            tv_comment = (TextView) rootView.findViewById(R.id.tv_comment);
            tv_zan = (TextView) rootView.findViewById(R.id.tv_zan);
            ll_recommen = (LinearLayout) rootView.findViewById(R.id.ll_recommen);
            iv_img1 = (ImageView) rootView.findViewById(R.id.iv_img1);
            iv_img2 = (ImageView) rootView.findViewById(R.id.iv_img2);
            iv_img3 = (ImageView) rootView.findViewById(R.id.iv_img3);
            ll_img = (LinearLayout) rootView.findViewById(R.id.ll_img);

        }
    }
}
