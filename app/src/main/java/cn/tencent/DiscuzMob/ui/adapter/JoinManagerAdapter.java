package cn.tencent.DiscuzMob.ui.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.model.JoinManagerBean;
import cn.tencent.DiscuzMob.ui.activity.JoinManagerActivity;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/7/27.
 */

public class JoinManagerAdapter extends BaseAdapter {
    private Activity activity;
    private List<JoinManagerBean.VariablesBean.ActivityapplylistBean.ApplylistBean> list;

    public JoinManagerAdapter(JoinManagerActivity joinManagerActivity) {
        activity = joinManagerActivity;
    }

    public void appendData(List<JoinManagerBean.VariablesBean.ActivityapplylistBean.ApplylistBean> list) {
        if (this.list == null) {
            this.list = list;
        } else {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void setData(List<JoinManagerBean.VariablesBean.ActivityapplylistBean.ApplylistBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void cleanData() {
        if (this.list != null) {
            this.list.clear();
        }
        notifyDataSetChanged();
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(activity, R.layout.joinmanager_item, null);
            viewHolder.tv_join_name = (TextView) convertView.findViewById(R.id.tv_join_name);
            viewHolder.tv_join_time = (TextView) convertView.findViewById(R.id.tv_join_time);
            viewHolder.tv_tv_join_status = (TextView) convertView.findViewById(R.id.tv_tv_join_status);
            viewHolder.iv_correct = (ImageView) convertView.findViewById(R.id.iv_correct);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        JoinManagerBean.VariablesBean.ActivityapplylistBean.ApplylistBean applylistBean = list.get(position);
        String username = applylistBean.getUsername();
        String dateline = applylistBean.getDateline();
        Log.e("TAG", "username=" + username);
        String verified = applylistBean.getVerified();
        viewHolder.tv_join_name.setText(username);
        viewHolder.tv_join_time.setText(dateline);
        //verified字段 0：尚未审核  1：允许参加 2：等待完善
        if (verified.equals("0")) {
            viewHolder.tv_tv_join_status.setText("尚未审核");
            viewHolder.iv_correct.setVisibility(View.GONE);
        } else if (verified.equals("1")) {
            viewHolder.tv_tv_join_status.setText("允许参加");
            viewHolder.iv_correct.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tv_tv_join_status.setText("等待完善");
            viewHolder.iv_correct.setVisibility(View.GONE);
        }
        return convertView;
    }


    class ViewHolder {
        private TextView tv_join_name, tv_join_time, tv_tv_join_status;
        private ImageView iv_correct;
    }
}
