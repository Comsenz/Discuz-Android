package cn.tencent.DiscuzMob.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.tencent.DiscuzMob.db.Modal;
import cn.tencent.DiscuzMob.model.ForumThreadlistBean;
import cn.tencent.DiscuzMob.model.LiveThredBean;
import cn.tencent.DiscuzMob.ui.activity.LiveDetialActivity;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/5/10.
 */

public class HotLiveAdapter extends BaseAdapter {
    private List<LiveThredBean.VariablesBean.LivethreadListBean> list = new ArrayList<>();
    Map<String, Object> lvs;
    private String formhash;


    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public void setData(List<LiveThredBean.VariablesBean.LivethreadListBean> livethread_list, Map<String, Object> lvs, String formhash) {
        list.clear();
        if (null != livethread_list) {
            list.addAll(livethread_list);
        }
        this.formhash = formhash;
        this.lvs = lvs;
    }

    public void addData(List<LiveThredBean.VariablesBean.LivethreadListBean> livethread_list, Map<String, Object> lvs) {
        if (null != livethread_list) {
            livethread_list.addAll(livethread_list);
        }
        this.lvs = lvs;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private boolean isAdd = false;
    private List<String> tids;

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.hotlive_item, null);
            viewHolder.iv_hot = (ImageView) convertView.findViewById(R.id.iv_hot);
            viewHolder.tv_hot = (TextView) convertView.findViewById(R.id.tv_hot);
            viewHolder.tv_subject = (TextView) convertView.findViewById(R.id.tv_subject);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_leve = (TextView) convertView.findViewById(R.id.tv_leve);
            viewHolder.tv_replies = (TextView) convertView.findViewById(R.id.tv_replies);
            viewHolder.rl_live = (RelativeLayout) convertView.findViewById(R.id.rl_live);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(list.get(position).getAuthor());
        String authorid = list.get(position).getAuthorid();
        Object o = lvs.get(authorid);
        boolean numeric = RednetUtils.isNumeric(o.toString());
        if (numeric == true) {
            viewHolder.tv_leve.setText("Lv" + o.toString());
        } else {
            viewHolder.tv_leve.setText(RednetUtils.delHTMLTag(o.toString()));
        }



        viewHolder.tv_subject.setText(list.get(position).getSubject());
//        if (null != authorid && authorid.equals("1")) {
//            viewHolder.tv_leve.setText(o.toString());
//        } else {
//            viewHolder.tv_leve.setText("Lv" + o.toString());
//        }
        String name = list.get(position).getForumnames().getName();
        viewHolder.tv_replies.setText(list.get(position).getReplies());
        viewHolder.tv_hot.setText("#" + name);
//        Picasso.with(parent.getContext())
//                .load(list.get(position).getAvatar())
//                .into(viewHolder.iv_hot);
        final String level = viewHolder.tv_leve.getText().toString();
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.rl_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
//                    Log.e("TAG", "添加记录=" + list.get(position).getDisplayorder());
                    forumThreadlistBean.setDisplayorder(list.get(position).getDisplayorder());
                    forumThreadlistBean.setImglist(null);
                    forumThreadlistBean.setLevel(level);
                    Modal.getInstance().getUserAccountDao().addAccount(forumThreadlistBean);
                }


                Intent intent = new Intent(parent.getContext(), LiveDetialActivity.class);
                intent.putExtra("subject", list.get(position).getSubject());
                intent.putExtra("fid", list.get(position).getFid());
                intent.putExtra("author", list.get(position).getAuthor());
                intent.putExtra("avatar", list.get(position).getAvatar());
                intent.putExtra("authorid", list.get(position).getAuthorid());
                intent.putExtra("tid", list.get(position).getTid());
                intent.putExtra("formhash", formhash);
                intent.putExtra("level", finalViewHolder.tv_leve.getText().toString());
                parent.getContext().startActivity(intent);
//                Toast.makeText(RedNetApp.getInstance(), list.get(position).getSubject(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }


    class ViewHolder {
        ImageView iv_hot;
        TextView tv_subject, tv_name, tv_leve, tv_hot, tv_replies;
        RelativeLayout rl_live;
    }
}
