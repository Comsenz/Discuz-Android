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
import cn.tencent.DiscuzMob.model.Image;
import cn.tencent.DiscuzMob.model.ImglistBean;
import cn.tencent.DiscuzMob.model.PraiseBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.activity.ActivityThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.ThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.UserDetailActivity;
import cn.tencent.DiscuzMob.ui.activity.VoteThreadDetailsActivity;
import cn.tencent.DiscuzMob.utils.ImageDisplay;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.widget.NoScrollGridView;

/**
 * Created by cg on 2017/5/10.
 */

public class RecommendAdapter extends BaseAdapter {
    List<ForumThreadlistBean> list;
    String name;
    String fid;
    private ArrayList<Integer> imgs = new ArrayList<>();
    private String Tag = "";
    private List<ImglistBean> strings = new ArrayList<>();
    private Map<String, Object> lvs;
    private Activity context;
    private String vote = "[vote]";
    private String ac = "[ac]";

    public RecommendAdapter(Activity context) {
        this.context = context;
    }

    public void cleanData() {
        if (list != null) {
            list.clear();
            notifyDataSetChanged();
        }

    }

    public void setData(List<ForumThreadlistBean> list, String name, String fid, Map<String, Object> strings) {
        this.list = list;
        this.name = name;
        this.fid = fid;
        this.lvs = strings;
        notifyDataSetChanged();
    }

    public void addData(List<ForumThreadlistBean> list, String name, String fid, Map<String, Object> strings) {
        if (this.list != null) {
            this.list.addAll(list);
        }

//        this.list = list;
//        this.name = name;
//        this.fid = fid;
//        this.lvs = strings;
        notifyDataSetChanged();
    }

    public void addIMG(ArrayList<Integer> imgs) {
        if (imgs != null) {
            this.imgs.addAll(imgs);
        }

    }

    public void setTag(String tag) {
        if (null != tag) {
            this.Tag = tag;
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

    private boolean isAdd = false;
    private List<String> tids;
    RecommendGlideAdapter gridAdapter;

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.recommen_item
                    , null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_authorName.setText(list.get(position).getAuthor());
        String displayorder = list.get(position).getDisplayorder();
        String digest = list.get(position).getDigest();
        String recommend = list.get(position).getRecommend();
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
                if (RednetUtils.hasLogin((Activity) context)) {
                    praiseThread(finalViewHolder.tv_zan, list.get(position).getTid());
                }

            }
        });
        final String subject = list.get(position).getSubject();
        String special = list.get(position).getSpecial();
        if (null != special && !TextUtils.isEmpty(special)) {
            if (special.equals("4")) {//投票贴
                final SpannableStringBuilder builder = new SpannableStringBuilder(vote);
                builder.append(subject);
                String dc = builder.toString();
                Drawable drawable = parent.getContext().getResources().getDrawable(R.drawable.activitysmall);
                drawable.setBounds(0,0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                ImageSpan ds = new ImageSpan(drawable);
                builder.setSpan(ds, dc.indexOf(vote), vote.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                viewHolder.tv_subject.setText(builder);
            } else if (special.equals("1")) {//活动帖
                final SpannableStringBuilder builder = new SpannableStringBuilder(ac);
                builder.append(subject);
                String dc = builder.toString();
                Drawable drawable = parent.getContext().getResources().getDrawable(R.drawable.votesmall);
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


        if (null != displayorder && !displayorder.equals("0")) {
            viewHolder.tv_gradle.setText("置顶");
        } else if (null != digest && !digest.equals("0")) {
            viewHolder.tv_gradle.setText("精华");
        } else {
            viewHolder.tv_gradle.setVisibility(View.GONE);
        }
        final String dateline = list.get(position).getDateline();
        viewHolder.tv_time.setText(Html.fromHtml(dateline));
        viewHolder.tv_views.setText(list.get(position).getViews());
        viewHolder.tv_comment.setText(list.get(position).getReplies());
        viewHolder.tv_zan.setText(list.get(position).getRecommend_add());
        String message = list.get(position).getMessage();
        if (null != message && !TextUtils.isEmpty(message)) {
            viewHolder.tv_message.setVisibility(View.VISIBLE);
            viewHolder.tv_message.setText(message);
        } else {
            viewHolder.tv_message.setVisibility(View.GONE);
        }

        viewHolder.tv_hot.setVisibility(View.GONE);
        List<EssenceBean.VariablesBean.DataBean.AttachlistBean> imglist = list.get(position).getImglist();
        String authorid = list.get(position).getAuthorid();
        Object o = lvs.get(authorid);
        if (null != o) {
            viewHolder.tv_leve.setVisibility(View.VISIBLE);
            boolean numeric = RednetUtils.isNumeric(o.toString());
            if (numeric == true) {
                viewHolder.tv_leve.setText("Lv" + o.toString());
            } else {
                viewHolder.tv_leve.setText(RednetUtils.delHTMLTag(o.toString()));
            }

        } else {
            viewHolder.tv_leve.setVisibility(View.GONE);
        }

        gridAdapter.setData(null);
        int imageSetting = RedNetPreferences.getImageSetting();
        if (imageSetting == 0) {
            ImageDisplay.loadcircleImage(context,viewHolder.avatar_author,list.get(position).getAvatar());
        } else {
            viewHolder.avatar_author.setImageResource(R.drawable.ic_header_def);
        }
        viewHolder.avatar_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, UserDetailActivity.class).putExtra("userId", list.get(position).getAuthorid()));
            }
        });
        if (CacheUtils.getImgString(context,"imageSetting").equals("1")){
            viewHolder.ll_img.setVisibility(View.GONE);
            viewHolder.gridView.setVisibility(View.GONE);
        }else {
            if (null != imglist && imglist.size() > 0 && imageSetting != 1) {
                viewHolder.ll_img.setVisibility(View.VISIBLE);
                if (imglist.size() == 1) {
                    showImage(parent,imglist.get(0).getAttachment() , viewHolder.iv_img1,imglist,0);
                    viewHolder.iv_img1.setVisibility(View.VISIBLE);
                    viewHolder.iv_img2.setVisibility(View.GONE);
                    viewHolder.iv_img3.setVisibility(View.GONE);
                } else if (imglist.size() == 2) {
                    viewHolder.iv_img1.setVisibility(View.VISIBLE);
                    showImage(parent,imglist.get(0).getAttachment() , viewHolder.iv_img1,imglist,0);
                    showImage(parent,imglist.get(1).getAttachment() , viewHolder.iv_img2,imglist,1);
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
//            viewHolder.gridView.setVisibility(View.VISIBLE);
//            gridAdapter.setData(imglist);

            } else {
                viewHolder.ll_img.setVisibility(View.GONE);

                viewHolder.gridView.setVisibility(View.GONE);
            }
        }

        final String level = viewHolder.tv_leve.getText().toString();

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        forumThreadlistBean.setSubject(subject);
                        forumThreadlistBean.setViews(list.get(position).getViews());
                        forumThreadlistBean.setReplies(list.get(position).getReplies());
                        forumThreadlistBean.setDateline(dateline);
                        forumThreadlistBean.setRecommend_add(list.get(position).getRecommend_add());
                        forumThreadlistBean.setDigest(list.get(position).getDigest());
                        forumThreadlistBean.setDisplayorder(list.get(position).getDisplayorder());
                        forumThreadlistBean.setImglist(list.get(position).getImglist());
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
                intent.putExtra("title", name);
                intent.putExtra("fid", fid);
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
                    RednetUtils.toast(context, (String) msg.obj);
                } else {
                    RednetUtils.toast(context, "请求失败");
                }
            } else if (msg.what == 6) {

                RednetUtils.toast(context, String.valueOf(msg.obj));
            }
        }
    };

    //点赞
    private void praiseThread(final TextView tv_zan, String tid) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", CacheUtils.getString(context, "cookiepre_auth") + ";" + CacheUtils.getString(context, "cookiepre_saltkey") + ";")
                .url(AppNetConfig.PRAISETHREAD + "&tid=" + tid + "&hash=" + CacheUtils.getString(context, "formhash1"))
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
                                    context.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Drawable drawable = context.getResources().getDrawable(R.drawable.icon_zans);
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
        private ImageView iv_img1, iv_img2, iv_img3;
        private LinearLayout ll_img;
        private TextView tv_message;

        public ViewHolder(View convertView) {
            avatar_author = (ImageView) convertView.findViewById(R.id.avatar_author);
            tv_authorName = (TextView) convertView.findViewById(R.id.tv_authorName);
            tv_leve = (TextView) convertView.findViewById(R.id.tv_leve);
            tv_gradle = (TextView) convertView.findViewById(R.id.tv_gradle);
            tv_subject = (TextView) convertView.findViewById(R.id.tv_subject);
            tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            tv_hot = (TextView) convertView.findViewById(R.id.tv_hot);
            tv_views = (TextView) convertView.findViewById(R.id.tv_views);
            tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            tv_message = (TextView) convertView.findViewById(R.id.tv_message);
            tv_zan = (TextView) convertView.findViewById(R.id.tv_zan);
            gridView = (NoScrollGridView) convertView.findViewById(R.id.gridView);
            ll_recommen = (LinearLayout) convertView.findViewById(R.id.ll_recommen);
            ll_img = (LinearLayout) convertView.findViewById(R.id.ll_img);
            iv_img1 = (ImageView) convertView.findViewById(R.id.iv_img1);
            iv_img2 = (ImageView) convertView.findViewById(R.id.iv_img2);
            iv_img3 = (ImageView) convertView.findViewById(R.id.iv_img3);
            gridAdapter = new RecommendGlideAdapter();
            gridView.setAdapter(gridAdapter);
//            iv_img1.setScaleType(ImageView.ScaleType.FIT_XY);
//            iv_img2.setScaleType(ImageView.ScaleType.FIT_XY);
//            iv_img3.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }
}
