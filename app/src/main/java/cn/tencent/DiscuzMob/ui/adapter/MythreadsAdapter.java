package cn.tencent.DiscuzMob.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.db.Modal;
import cn.tencent.DiscuzMob.model.ForumThreadlistBean;
import cn.tencent.DiscuzMob.ui.activity.VoteThreadDetailsActivity;
import cn.tencent.DiscuzMob.utils.ImageDisplay;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.StringUtil;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.model.MythreadBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.activity.ActivityThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.ThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.UserDetailActivity;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cg on 2017/7/17.
 */

public class MythreadsAdapter extends BaseAdapter {
    private List<MythreadBean.VariablesBean.ListBean> list;
    private String url;
    private Activity activity;
    private String tag;
    private ArrayList<String> tids;
    private boolean isAdd = false;

    public MythreadsAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setData(List<MythreadBean.VariablesBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void appendData(List<MythreadBean.VariablesBean.ListBean> list) {
        if (this.list != null) {
            this.list.addAll(list);
        } else {
            this.list = list;
        }
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

    public void getUrl(String ucenterurl) {
        this.url = ucenterurl;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.my_thread, null);
            viewHolder.avatar = (CircleImageView) convertView.findViewById(R.id.avatar);
            viewHolder.tv_dateline = (TextView) convertView.findViewById(R.id.tv_dateline);
            viewHolder.tv_note = (TextView) convertView.findViewById(R.id.tv_note);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String dateSub = RednetUtils.getDateSub(list.get(position).getDateline());
        viewHolder.tv_dateline.setText(dateSub);
        ImageDisplay.loadCirimageView(activity,viewHolder.avatar,url + "/avatar.php?uid=" + list.get(position).getAuthorid() + "&size=midden");
//        viewHolder.avatar.load(url + "/avatar.php?uid=" + list.get(position).getAuthorid() + "&size=midden");
        viewHolder.tv_note.setText(Html.fromHtml(list.get(position).getNote()));
        viewHolder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!list.get(position).getAuthorid().equals(RedNetApp.getInstance().getUid())) {
                    Intent intent = new Intent(activity, UserDetailActivity.class);
                    intent.putExtra("userId", list.get(position).getUid());
                    activity.startActivity(intent);
                }

            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.i(list.get(position).getNotevar()+"       aaaaaaaa");
                if (list.get(position).getNotevar()!=null) {
                    final String tid = list.get(position).getNotevar().getTid();
                    long count = Modal.getInstance().getUserAccountDao().getCount();
                    List<ForumThreadlistBean> data = Modal.getInstance().getUserAccountDao().getScrollData(0, count);
                    if (null != data) {
                        if (data.size() > 0) {
                            tids = new ArrayList<String>();
                            for (int i = 0; i < data.size(); i++) {
                                String tid1 = data.get(i).getTid();
                                tids.add(tid1);
                            }
                            if (!tids.contains(tid)) {
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

                    RedNet.mHttpClient.newCall(new Request.Builder()
                            .url(AppNetConfig.BASEURL + "?module=viewthread&tid=" + tid + "&submodule=checkpost&version=5&page=1" + "&width=360&height=480&checkavatar=1&submodule=checkpost")
                            .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                            .enqueue(new Callback() {
                                @Override
                                public void onFailure(Request request, IOException e) {
//                                mHandler.sendEmptyMessage(-10001);
//                                page = Math.max(1, --page);
                                }

                                @Override
                                public void onResponse(Response response) throws IOException {
                                    try {
                                        JSONObject json = new JSONObject(response.body().string());
                                        JSONObject jsonObject = json.getJSONObject("Variables").getJSONObject("thread");
                                        String special = jsonObject.getString("special");
                                        String fid = jsonObject.getString("fid");
                                        if (isAdd == true) {
                                            ForumThreadlistBean forumThreadlistBean = new ForumThreadlistBean();
                                            forumThreadlistBean.setAuthor(list.get(position).getAuthor());
                                            forumThreadlistBean.setAvatar(url + "/avatar.php?uid=" + list.get(position).getAuthorid() + "&size=midden");
                                            forumThreadlistBean.setTid(tid);
                                            forumThreadlistBean.setSpecial(special);
                                            forumThreadlistBean.setSubject(jsonObject.getString("subject"));
                                            forumThreadlistBean.setViews(jsonObject.getString("views"));
                                            forumThreadlistBean.setReplies(jsonObject.getString("replies"));
                                            forumThreadlistBean.setDateline(jsonObject.getString("dateline"));
                                            forumThreadlistBean.setRecommend_add(jsonObject.getString("recommend_add"));
                                            forumThreadlistBean.setDigest(jsonObject.getString("digest"));
                                            forumThreadlistBean.setDisplayorder(jsonObject.getString("displayorder"));
                                            forumThreadlistBean.setImglist(null);
                                            forumThreadlistBean.setLevel("");
                                            Modal.getInstance().getUserAccountDao().addAccount(forumThreadlistBean);
                                        }
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
                                        intent.putExtra("id", tid);
                                        intent.putExtra("title", "");
                                        intent.putExtra("fid", fid);
                                        parent.getContext().startActivity(intent);


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                }

            }
        });

        return convertView;
    }

    class ViewHolder {
        private CircleImageView avatar;
        private TextView tv_dateline;
        private TextView tv_note;
    }

}
