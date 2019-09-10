package cn.tencent.DiscuzMob.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.db.Modal;
import cn.tencent.DiscuzMob.model.EssenceBean;
import cn.tencent.DiscuzMob.model.ForumThreadlistBean;
import cn.tencent.DiscuzMob.model.ImglistBean;
import cn.tencent.DiscuzMob.model.PraiseBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.activity.ActivityThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.ThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.UserDetailActivity;
import cn.tencent.DiscuzMob.ui.activity.VoteThreadDetailsActivity;
import cn.tencent.DiscuzMob.utils.ImageDisplay;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.widget.NoScrollGridView;

/**
 * Created by cg on 2017/6/13.
 */

public class EssenceAdapter extends BaseAdapter {
    private List<EssenceBean.VariablesBean.DataBean> list;
    private List<ImglistBean> strings = new ArrayList<>();
    private String Tag = "";
    private ArrayList<String> tids;
    private boolean isAdd = false;
    private Map<String, Object> lvs;
    private Activity activity;
    private String vote = "[vote]";
    private String ac = "[ac]";

    public EssenceAdapter(Activity activity) {
        this.activity = activity;
    }

    public void addData(List<EssenceBean.VariablesBean.DataBean> thread_list) {
        if (this.list != null) {
            this.list.addAll(thread_list);
        } else {
            this.list = thread_list;
        }
        notifyDataSetChanged();
    }

    public void setData(List<EssenceBean.VariablesBean.DataBean> thread_list, Map<String, Object> strings) {
        this.list = thread_list;
        this.lvs = strings;
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
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    RecommendGlideAdapter gridAdapter;

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.recommen_item
                    , null);
            viewHolder.avatar_author = (ImageView) convertView.findViewById(R.id.avatar_author);
            viewHolder.tv_authorName = (TextView) convertView.findViewById(R.id.tv_authorName);
            viewHolder.tv_leve = (TextView) convertView.findViewById(R.id.tv_leve);
            viewHolder.tv_gradle = (TextView) convertView.findViewById(R.id.tv_gradle);
            viewHolder.tv_subject = (TextView) convertView.findViewById(R.id.tv_subject);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_hot = (TextView) convertView.findViewById(R.id.tv_hot);
            viewHolder.tv_views = (TextView) convertView.findViewById(R.id.tv_views);
            viewHolder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            viewHolder.tv_zan = (TextView) convertView.findViewById(R.id.tv_zan);
            viewHolder.gridView = (NoScrollGridView) convertView.findViewById(R.id.gridView);
            viewHolder.ll_recommen = (LinearLayout) convertView.findViewById(R.id.ll_recommen);
            viewHolder.ll_img = (LinearLayout) convertView.findViewById(R.id.ll_img);
            viewHolder.iv_img1 = (ImageView) convertView.findViewById(R.id.iv_img1);
            viewHolder.iv_img2 = (ImageView) convertView.findViewById(R.id.iv_img2);
            viewHolder.iv_img3 = (ImageView) convertView.findViewById(R.id.iv_img3);
//            gridAdapter = new RecommendGlideAdapter();
//            viewHolder.gridView.setAdapter(gridAdapter);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_authorName.setText(list.get(position).getAuthor());
        String displayorder = list.get(position).getDisplayorder();
        String digest = list.get(position).getDigest();
        if (list.get(position).getDigest().equals("1")||list.get(position).equals("2")||list.get(position).equals("3")){
            viewHolder.tv_gradle.setText("精华");
        }else {
            viewHolder.tv_gradle.setText("");
            viewHolder.tv_gradle.setBackgroundColor(activity.getResources().getColor(R.color.transparent));
        }

        String recommend = list.get(position).getRecommends();
        if (null != recommend && recommend.equals("1")) {
            Drawable drawable = parent.getContext().getResources().getDrawable(R.drawable.icon_zans);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            viewHolder.tv_zan.setCompoundDrawables(drawable, null, null, null);
        } else {
            Drawable drawable = parent.getContext().getResources().getDrawable(R.drawable.icon_zan);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            viewHolder.tv_zan.setCompoundDrawables(drawable, null, null, null);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.tv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RednetUtils.hasLogin(activity)) {
                    praiseThread(finalViewHolder.tv_zan, list.get(position).getTid());
                }
            }
        });
        final String subject = list.get(position).getSubject();
        String special = list.get(position).getSpecial();
        if (null != special && !TextUtils.isEmpty(special)) {
            if (special.equals("1")) {//投票贴
                final SpannableStringBuilder builder = new SpannableStringBuilder(vote);
                builder.append(subject);
                String dc = builder.toString();
                Drawable drawable = parent.getContext().getResources().getDrawable(R.drawable.votesmall);

                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                ImageSpan ds = new ImageSpan(drawable);
                builder.setSpan(ds, dc.indexOf(vote), vote.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                viewHolder.tv_subject.setText(builder);
            } else if (special.equals("4")) {//活动帖
                final SpannableStringBuilder builder = new SpannableStringBuilder(ac);
                builder.append(subject);
                String dc = builder.toString();
                Drawable drawable = parent.getContext().getResources().getDrawable(R.drawable.activitysmall);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                ImageSpan ds = new ImageSpan(drawable);
                builder.setSpan(ds, dc.indexOf(ac), ac.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                viewHolder.tv_subject.setText(builder);
            } else {
                viewHolder.tv_subject.setText(subject);
            }
        } else {
            viewHolder.tv_subject.setText(subject);
        }
        viewHolder.tv_time.setText(Html.fromHtml(list.get(position).getDateline()));
        viewHolder.tv_views.setText(list.get(position).getViews());
        viewHolder.tv_comment.setText(list.get(position).getReplies());
        viewHolder.tv_zan.setText(list.get(position).getRecommend_add());
        List<EssenceBean.VariablesBean.DataBean.AttachlistBean> imglist = list.get(position).getAttachlist();
//        String name = list.get(position).getForumnames().getName();
//        if (name != null) {
//            viewHolder.tv_hot.setText("#" + name);
//        } else {
//            viewHolder.tv_hot.setText("");
//        }
        String authorid = list.get(position).getAuthorid();
        Object o = lvs.get(authorid);
        if (null != o) {
            boolean numeric = RednetUtils.isNumeric(o.toString());
            viewHolder.tv_leve.setVisibility(View.VISIBLE);
            if (numeric == true) {
                viewHolder.tv_leve.setText("Lv" + o.toString());
            } else {
                viewHolder.tv_leve.setText(RednetUtils.delHTMLTag(o.toString()));
            }
        } else {
            viewHolder.tv_leve.setVisibility(View.GONE);
        }

        viewHolder.avatar_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.getContext().startActivity(new Intent(activity, UserDetailActivity.class).putExtra("userId", list.get(position).getAuthorid()));
            }
        });
        int imageSetting = RedNetPreferences.getImageSetting();
        if (imageSetting == 0) {
            ImageDisplay.loadcircleImage(activity,viewHolder.avatar_author,list.get(position).getAvatar());
        } else {
            viewHolder.avatar_author.setImageResource(R.drawable.ic_header_def);
        }
        if (CacheUtils.getImgString(activity,"imageSetting").equals("1")){
            viewHolder.ll_img.setVisibility(View.GONE);
            viewHolder.gridView.setVisibility(View.GONE);
        }else {
            if (null != imglist && imglist.size() > 0 && imageSetting != 1) {
                viewHolder.ll_img.setVisibility(View.VISIBLE);
                if (imglist.size() == 1) {
                    showImage(parent, imglist.get(0).getAttachment(), viewHolder.iv_img1, imglist, 0);
                    viewHolder.iv_img1.setVisibility(View.VISIBLE);
                    viewHolder.iv_img2.setVisibility(View.GONE);
                    viewHolder.iv_img3.setVisibility(View.GONE);
                } else if (imglist.size() == 2) {
                    showImage(parent, imglist.get(0).getAttachment(), viewHolder.iv_img1, imglist, 0);
                    viewHolder.iv_img1.setVisibility(View.VISIBLE);
                    showImage(parent, imglist.get(1).getAttachment(), viewHolder.iv_img2, imglist, 1);
                    viewHolder.iv_img2.setVisibility(View.VISIBLE);
                    viewHolder.iv_img3.setVisibility(View.GONE);
                } else {
                    showImage(parent, imglist.get(0).getAttachment(), viewHolder.iv_img1, imglist, 0);
                    showImage(parent, imglist.get(1).getAttachment(), viewHolder.iv_img2, imglist, 1);
                    showImage(parent, imglist.get(2).getAttachment(), viewHolder.iv_img3, imglist, 2);
                    viewHolder.iv_img1.setVisibility(View.VISIBLE);
                    viewHolder.iv_img2.setVisibility(View.VISIBLE);
                    viewHolder.iv_img3.setVisibility(View.VISIBLE);
                }
            } else {
                viewHolder.ll_img.setVisibility(View.GONE);
            }
        }
        final String level = viewHolder.tv_leve.getText().toString();

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list == null || list.size() == 0) {
                    return;
                }
                if (TextUtils.isEmpty(Tag)) {
                    String tid1 = list.get(position).getTid();
                    long count = Modal.getInstance().getUserAccountDao().getCount();
                    List<ForumThreadlistBean> data = Modal.getInstance().getUserAccountDao().getScrollData(0, count);

                    if (null != data) {
                        if (data.size() > 0) {
                            tids = new ArrayList<String>();
                            for (int i = 0; i < data.size(); i++) {
                                String tid = data.get(i).getTid();
                                tids.add(tid);
                            }
                            if (!tids.contains(tid1)) {
                                isAdd = true;
                            } else {
                                isAdd = false;
                            }
                        } else {
                            isAdd = true;
                        }

                    } else {
                        isAdd = false;
                    }
                    if (isAdd == true) {
                        ForumThreadlistBean forumThreadlistBean = new ForumThreadlistBean();
                        forumThreadlistBean.setAuthor(list.get(position).getAuthor());
                        forumThreadlistBean.setAvatar(list.get(position).getAvatar());
                        forumThreadlistBean.setTid(tid1);
                        forumThreadlistBean.setSpecial(list.get(position).getSpecial());
                        forumThreadlistBean.setSubject(list.get(position).getSubject());
                        forumThreadlistBean.setViews(list.get(position).getViews());
                        forumThreadlistBean.setReplies(list.get(position).getReplies());
                        forumThreadlistBean.setDateline(list.get(position).getDateline());
                        forumThreadlistBean.setRecommend_add(list.get(position).getRecommend_add());
                        forumThreadlistBean.setDigest(list.get(position).getDigest());
                        forumThreadlistBean.setDisplayorder(list.get(position).getDisplayorder());
                        forumThreadlistBean.setImglist(list.get(position).getAttachlist());
                        forumThreadlistBean.setLevel(level);
                        Modal.getInstance().getUserAccountDao().addAccount(forumThreadlistBean);
                    }
                }

                String special = list.get(position).getSpecial();
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
                intent.putExtra("id", list.get(position).getTid());
                intent.putExtra("title", list.get(position).getSubject());
                intent.putExtra("fid", list.get(position).getFid());
                parent.getContext().startActivity(intent);
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -10001) {
                if (msg.obj instanceof String) {
                    RednetUtils.toast(activity, (String) msg.obj);
                } else {
                    RednetUtils.toast(activity, "请求失败");
                }
            } else if (msg.what == 6) {

                RednetUtils.toast(activity, String.valueOf(msg.obj));
            }
        }
    };

    private void praiseThread(final TextView tv_zan, String tid) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", CacheUtils.getString(activity, "cookiepre_auth") + ";" + CacheUtils.getString(activity, "cookiepre_saltkey") + ";")
                .url(AppNetConfig.PRAISETHREAD + "&tid=" + tid + "&hash=" + CacheUtils.getString(activity, "formhash1"))
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new com.squareup.okhttp.Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        mHandler.sendMessage(Message.obtain(mHandler, -10001, "请求失败"));

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
//                        Log.e("TAG", "点赞=" + response.body().string());
                        if (null != response) {
                            try {
                                PraiseBean praiseBean = new Gson().fromJson(response.body().string(), PraiseBean.class);
                                PraiseBean.MessageBean message = praiseBean.getMessage();
                                mHandler.sendMessage(Message.obtain(mHandler, message.getMessageval().equals("recommend_succeed") ? 6 : -10001, message.getMessagestr().toString()));
                                if (message.getMessageval().equals("recommend_succeed")) {
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Drawable drawable = activity.getResources().getDrawable(R.drawable.icon_zans);
                                            /// 这一步必须要做,否则不会显示.
                                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                            tv_zan.setCompoundDrawables(drawable, null, null, null);
                                            String s = tv_zan.getText().toString();
                                            int i = Integer.parseInt(s);
                                            tv_zan.setText(i + 1 + "");
                                        }
                                    });
                                }

                            } catch (JsonSyntaxException e) {
                                mHandler.sendMessage(Message.obtain(mHandler, -10001, "请求异常"));
                                e.printStackTrace();
                            }
                        } else {
                            mHandler.sendMessage(Message.obtain(mHandler, -10001, "点赞失败"));
                        }
                    }
                });
    }

    class ViewHolder {
        private ImageView avatar_author;
        private TextView tv_authorName, tv_leve, tv_gradle, tv_subject, tv_time, tv_hot, tv_views, tv_comment, tv_zan;
        private NoScrollGridView gridView;
        private LinearLayout ll_recommen;
        private LinearLayout ll_img;
        private ImageView iv_img1, iv_img2, iv_img3;
    }
}
