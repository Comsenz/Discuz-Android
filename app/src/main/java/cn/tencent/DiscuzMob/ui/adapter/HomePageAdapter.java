package cn.tencent.DiscuzMob.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.db.Modal;
import cn.tencent.DiscuzMob.model.EssenceBean;
import cn.tencent.DiscuzMob.model.ForumThreadlistBean;
import cn.tencent.DiscuzMob.model.HomePageBean;
import cn.tencent.DiscuzMob.model.ImglistBean;
import cn.tencent.DiscuzMob.model.PraiseBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.activity.ActivityThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.ThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.UserDetailActivity;
import cn.tencent.DiscuzMob.ui.activity.VoteThreadDetailsActivity;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.widget.NoScrollGridView;

/**
 * Created by cg on 2017/5/31.
 */

public class HomePageAdapter extends BaseAdapter {
    private final Gson mGson;
    private List<HomePageBean.VariablesBean.RecommonthreadListBean> digest_list = new ArrayList<>();
    private ArrayList<String> tids;
    private boolean isAdd;
    private String Tag = "";
    private Map<String, String> strings;
    private JSONArray list = new JSONArray();
    private Activity activity;
    private String vote = "[vote]";
    private String ac = "[ac]";
    private JSONObject types;
    private HashMap<String, String> map;
    private String s;

    public HomePageAdapter(Activity activity) {
        this.activity = activity;
        mGson = new Gson();
    }

    private String formhash;

    public void addData(JSONArray digest_list, Map<String, String> strings, String formhash, JSONObject types) {
//        this.digest_list.addAll(digest_list);
        this.list.addAll(digest_list);
        this.strings = strings;
        this.formhash = formhash;
        this.types = types;
        notifyDataSetChanged();
    }

    public void cleanData() {
        list.clear();
        notifyDataSetChanged();
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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.recommen_item
                    , null);
            viewHolder.avatar_author = (AsyncRoundedImageView) convertView.findViewById(R.id.avatar_author);
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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final JSONObject jsonObject = list.getJSONObject(position);
        viewHolder.tv_authorName.setText(jsonObject.getString("author"));
        final String displayorder = jsonObject.getString("displayorder");
        final String digest = jsonObject.getString("digest");
        String string = jsonObject.getJSONObject("forumnames").getString("name");
        viewHolder.tv_hot.setText("#" + string);
//        String displayorder = digest_list.get(position).getDisplayorder();
//        String digest = digest_list.get(position).getDigest();
        if (null != displayorder && !displayorder.equals("0")) {
            viewHolder.tv_gradle.setText("置顶");
        } else if (null != digest && !digest.equals("0")) {
            viewHolder.tv_gradle.setText("精华");
        } else {
            viewHolder.tv_gradle.setVisibility(View.GONE);
        }
        String recommend = jsonObject.getString("recommend");

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
        String special = jsonObject.getString("special");
        Log.e("TAG", "types=" + types);
        if (types != null) {
            map = new HashMap<>();
            for (Map.Entry<String, Object> entry : types.entrySet()) {
                Log.e("TAG", "entry.getKey()=" + entry.getKey() + ";entry.getValue().toString()=" + entry.getValue().toString());
                map.put(entry.getKey(), entry.getValue().toString());
            }
            String typeid = jsonObject.getString("typeid");
//            s = map.get(typeid);
            s = types.getString(typeid);
            Log.e("TAG", " s =" + s);

        }

        if (null != special && !TextUtils.isEmpty(special)) {
            if (special.equals("4")) {//投票贴
                final SpannableStringBuilder builder = new SpannableStringBuilder(vote);
                builder.append(" ");
                if (s != null && !TextUtils.isEmpty(s)) {
                    Log.e("TAG", "s=" + s);
                    builder.append("[" + s + "]");
                    builder.setSpan(new ForegroundColorSpan(Color.parseColor("#3c96d6")), vote.length() - 1, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                builder.append(jsonObject.getString("subject"));
                String dc = builder.toString();
                Drawable drawable = parent.getContext().getResources().getDrawable(R.drawable.activitysmall);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                ImageSpan ds = new ImageSpan(drawable);
                builder.setSpan(ds, dc.indexOf(vote), vote.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                viewHolder.tv_subject.setText(builder);
            } else if (special.equals("1")) {//活动帖
                final SpannableStringBuilder builder = new SpannableStringBuilder(ac);
                builder.append(" ");
                if (s != null && !TextUtils.isEmpty(s)) {
                    builder.append("[" + s + "]");
                    builder.setSpan(new ForegroundColorSpan(Color.parseColor("#3c96d6")), vote.length() - 1, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                builder.append(jsonObject.getString("subject"));
                String dc = builder.toString();
                Drawable drawable = parent.getContext().getResources().getDrawable(R.drawable.votesmall);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                ImageSpan ds = new ImageSpan(drawable);
                builder.setSpan(ds, dc.indexOf(ac), ac.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                viewHolder.tv_subject.setText(builder);
            } else {
                viewHolder.tv_subject.setText(jsonObject.getString("subject"));
            }
        } else {
            viewHolder.tv_subject.setText(jsonObject.getString("subject"));
        }


        viewHolder.tv_time.setText(Html.fromHtml(jsonObject.getString("dateline")));
        viewHolder.tv_views.setText(jsonObject.getString("views"));
        viewHolder.tv_comment.setText(jsonObject.getString("replies"));
        viewHolder.tv_zan.setText(jsonObject.getString("recommend_add"));
        String authorid = jsonObject.getString("authorid");
        String s = strings.get(authorid);
        boolean numeric = isNumeric(s);
        if (numeric == true) {
            viewHolder.tv_leve.setText("Lv" + s);
        } else {
            viewHolder.tv_leve.setText(RednetUtils.delHTMLTag(s));
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.tv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RednetUtils.hasLogin(activity)) {
                    praiseThread(finalViewHolder.tv_zan, jsonObject.getString("tid"));
                }
            }
        });
        JSONArray imagelist = jsonObject.getJSONArray("imagelist");
        Type type = new TypeToken<List<ImglistBean>>(){}.getType();
        final List<EssenceBean.VariablesBean.DataBean.AttachlistBean> imglistBeanList = mGson.fromJson(imagelist.toJSONString(), type);
        int imageSetting1 = RedNetPreferences.getImageSetting();
        if (imageSetting1 == 0) {
            viewHolder.avatar_author.load(jsonObject.getString("avatar"), R.drawable.ic_header_def);
        } else {
            viewHolder.avatar_author.setImageResource(R.drawable.ic_header_def);
        }
        if (null != imagelist && imagelist.size() > 0) {
            viewHolder.ll_img.setVisibility(View.VISIBLE);
            Log.e("TAG", "imagelist=" + imagelist.size());
            if (imagelist.size() == 1) {
                viewHolder.iv_img1.setVisibility(View.VISIBLE);
                JSONObject jsonObject0 = imagelist.getJSONObject(0);
                String url = jsonObject0.getString("url");
                String attachment = jsonObject0.getString("attachment");
                String imgUrl = AppNetConfig.IMGURL1 + url + attachment;
                int imageSetting = RedNetPreferences.getImageSetting();
                if (imageSetting == 0) {
                    Picasso.with(parent.getContext())
                            .load(imgUrl)
                            .into(viewHolder.iv_img1);
                } else {
                    viewHolder.iv_img1.setImageResource(R.drawable.wutu);
                }

                viewHolder.iv_img2.setVisibility(View.GONE);
                viewHolder.iv_img3.setVisibility(View.GONE);
            } else if (imagelist.size() == 2) {
                viewHolder.iv_img1.setVisibility(View.VISIBLE);
                viewHolder.iv_img2.setVisibility(View.VISIBLE);
                JSONObject jsonObject0 = imagelist.getJSONObject(0);
                String url = jsonObject0.getString("url");
                String attachment = jsonObject0.getString("attachment");
                String imgUrl0 = AppNetConfig.IMGURL1 + url + attachment;

                JSONObject jsonObject1 = imagelist.getJSONObject(1);
                String url1 = jsonObject1.getString("url");
                String attachment1 = jsonObject1.getString("attachment");
                String imgUrl1 = AppNetConfig.IMGURL1 + url1 + attachment1;


                int imageSetting = RedNetPreferences.getImageSetting();
                if (imageSetting == 0) {

                    Picasso.with(parent.getContext())
                            .load(imgUrl0)
                            .into(viewHolder.iv_img1);
                    Picasso.with(parent.getContext())
                            .load(imgUrl1)
                            .into(viewHolder.iv_img2);
                } else {
                    viewHolder.iv_img1.setImageResource(R.drawable.wutu);
                    viewHolder.iv_img2.setImageResource(R.drawable.wutu);
                }
                viewHolder.iv_img3.setVisibility(View.GONE);
            } else if (imagelist.size() == 3) {
                viewHolder.iv_img1.setVisibility(View.VISIBLE);
                viewHolder.iv_img2.setVisibility(View.VISIBLE);
                viewHolder.iv_img3.setVisibility(View.VISIBLE);
                JSONObject jsonObject0 = imagelist.getJSONObject(0);
                String url = jsonObject0.getString("url");
                String attachment = jsonObject0.getString("attachment");
                String imgUrl0 = AppNetConfig.IMGURL1 + url + attachment;

                JSONObject jsonObject1 = imagelist.getJSONObject(1);
                String url1 = jsonObject1.getString("url");
                String attachment1 = jsonObject1.getString("attachment");
                String imgUrl1 = AppNetConfig.IMGURL1 + url1 + attachment1;

                JSONObject jsonObject2 = imagelist.getJSONObject(2);
                String url2 = jsonObject2.getString("url");
                String attachment2 = jsonObject2.getString("attachment");
                String imgUrl2 = AppNetConfig.IMGURL1 + url2 + attachment2;


                int imageSetting = RedNetPreferences.getImageSetting();
                if (imageSetting == 0) {
                    Picasso.with(parent.getContext())
                            .load(imgUrl2)
                            .into(viewHolder.iv_img3);

                    Picasso.with(parent.getContext())
                            .load(imgUrl0)
                            .into(viewHolder.iv_img1);
                    Picasso.with(parent.getContext())
                            .load(imgUrl1)
                            .into(viewHolder.iv_img2);
                } else {
                    viewHolder.iv_img1.setImageResource(R.drawable.wutu);
                    viewHolder.iv_img2.setImageResource(R.drawable.wutu);
                    viewHolder.iv_img3.setImageResource(R.drawable.wutu);
                }


            }
        } else {
            viewHolder.ll_img.setVisibility(View.GONE);
        }
//
        final String leve = viewHolder.tv_leve.getText().toString();
        viewHolder.avatar_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.getContext().startActivity(new Intent(activity, UserDetailActivity.class).putExtra("userId", jsonObject.getString("authorid")));
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(Tag)) {
                    String tid1 = jsonObject.getString("tid");
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
                        forumThreadlistBean.setAuthor(jsonObject.getString("author"));
                        forumThreadlistBean.setAvatar(jsonObject.getString("avatar"));
                        forumThreadlistBean.setTid(tid1);
                        forumThreadlistBean.setSpecial(jsonObject.getString("special"));
                        forumThreadlistBean.setSubject(jsonObject.getString("subject"));
                        forumThreadlistBean.setViews(jsonObject.getString("views"));
                        forumThreadlistBean.setReplies(jsonObject.getString("replies"));
                        forumThreadlistBean.setDateline(jsonObject.getString("dateline"));
                        forumThreadlistBean.setRecommend_add(jsonObject.getString("recommend_add"));
                        forumThreadlistBean.setDigest(digest);
                        forumThreadlistBean.setDisplayorder(displayorder);
                        forumThreadlistBean.setLevel(leve);
                        forumThreadlistBean.setImglist(imglistBeanList);
                        Modal.getInstance().getUserAccountDao().addAccount(forumThreadlistBean);
                    }
                }

                Class claz;
                String special = jsonObject.getString("special");
                special = TextUtils.isEmpty(special) ? "0" : special;
                if ("1".equals(special)) {
                    claz = VoteThreadDetailsActivity.class;
                } else if ("4".equals(special)) {
                    claz = ActivityThreadDetailsActivity.class;
                } else {
                    claz = ThreadDetailsActivity.class;
                }
                Intent intent = new Intent(parent.getContext(), claz);
                intent.putExtra("id", jsonObject.getString("tid"));
                intent.putExtra("title", jsonObject.getJSONObject("forumnames").getString("name"));
                intent.putExtra("fid", jsonObject.getJSONObject("forumnames").getString("fid"));
                parent.getContext().startActivity(intent);
            }
        });
        return convertView;
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
                .url(AppNetConfig.PRAISETHREAD + "&tid=" + tid + "&hash=" + formhash)
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
        private AsyncRoundedImageView avatar_author;
        private TextView tv_authorName, tv_leve, tv_gradle, tv_subject, tv_time, tv_hot, tv_views, tv_comment, tv_zan;
        private NoScrollGridView gridView;
        private LinearLayout ll_recommen;
        private ImageView iv_img1, iv_img2, iv_img3;
        private LinearLayout ll_img;
    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
