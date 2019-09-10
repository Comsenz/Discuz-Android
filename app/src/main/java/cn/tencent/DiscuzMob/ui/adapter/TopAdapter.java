package cn.tencent.DiscuzMob.ui.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.db.Modal;
import cn.tencent.DiscuzMob.model.ForumThreadlistBean;
import cn.tencent.DiscuzMob.ui.activity.ActivityThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.ThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.VoteThreadDetailsActivity;


/**
 * Created by cg on 2017/5/10.
 */

public class TopAdapter extends BaseAdapter {
    private List<JSONObject> list;
    private String name;
    private String fid;
    private boolean isAdd = false;

    public TopAdapter(List<JSONObject> list, String name, String fid) {
        this.list = list;
        this.name = name;
        this.fid = fid;
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

    private List<String> tids;

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.top_item, null);
            viewHolder.tv_subject = (TextView) convertView.findViewById(R.id.tv_subject);
            viewHolder.ll_top = (LinearLayout) convertView.findViewById(R.id.ll_top);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        String rc = null, dc = null;
//        ImageSpan ds = null;
//        Drawable drawable;
//        final SpannableStringBuilder builder = new SpannableStringBuilder();
        final JSONObject object = list.get(position);
//        String displayorder = object.getString("displayorder");
        final String subject = object.getString("subject");
//        if (!displayorder.equals("0")) {
//            rc = "[blue]";
//            String s = rc + " " + subject;
//            builder.append(s);
//            dc = builder.toString();
//            drawable = parent.getContext().getResources().getDrawable(R.drawable.icon_top);
//            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//            ds = new ImageSpan(drawable);
//
//
//        }
//        if (!TextUtils.isEmpty(rc)) {
//            builder.setSpan(ds, dc.indexOf(rc), rc.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
//        viewHolder.tv_subject.setText(builder);
        viewHolder.tv_subject.setText(subject);

        viewHolder.ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tid1 = object.getString("tid");

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

                }
                if (isAdd == true) {
                    ForumThreadlistBean forumThreadlistBean = new ForumThreadlistBean();
                    forumThreadlistBean.setAuthor(object.getString("author"));
                    forumThreadlistBean.setAvatar(object.getString("avatar"));
                    forumThreadlistBean.setTid(tid1);
                    forumThreadlistBean.setSpecial(object.getString("special"));
                    forumThreadlistBean.setSubject(subject);
                    forumThreadlistBean.setViews(object.getString("views"));
                    forumThreadlistBean.setReplies(object.getString("replies"));
                    forumThreadlistBean.setDateline(object.getString("dateline"));
                    forumThreadlistBean.setRecommend_add(object.getString("recommend_add"));
                    forumThreadlistBean.setDigest(object.getString("digest"));
                    forumThreadlistBean.setDisplayorder(object.getString("displayorder"));
//                    forumThreadlistBean.setImglist(list.get(position).getImglist());
                    Modal.getInstance().getUserAccountDao().addAccount(forumThreadlistBean);
                }
                String special = object.getString("special");
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
                intent.putExtra("id", object.getString("tid"));
                intent.putExtra("title", name);
                intent.putExtra("fid", fid);
                parent.getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_subject;
        LinearLayout ll_top;
    }
}
