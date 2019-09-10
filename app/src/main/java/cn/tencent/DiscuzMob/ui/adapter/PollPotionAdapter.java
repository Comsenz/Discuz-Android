package cn.tencent.DiscuzMob.ui.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.tencent.DiscuzMob.model.PollPotionVariables;
import cn.tencent.DiscuzMob.model.Polloption;
import cn.tencent.DiscuzMob.R;

/**
 * Created by kurt on 2015/8/11.
 * 投票选项列表
 */
public class PollPotionAdapter extends BaseAdapter {

    private ArrayList<PollPotionVariables.VariablesBean.ViewvoteBean.PolloptionsBean> mPolloptionList;

    public void setmPolloptions(ArrayList<PollPotionVariables.VariablesBean.ViewvoteBean.PolloptionsBean> mPolloptionList) {
        if (mPolloptionList != null ) {
            this.mPolloptionList = mPolloptionList;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mPolloptionList == null ? 0 : mPolloptionList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPolloptionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_item1, parent, false);
            tv = (TextView) convertView.findViewById(R.id.item1);
            convertView.setTag(tv);
        } else {
            tv = (TextView) convertView.getTag();
        }
        PollPotionVariables.VariablesBean.ViewvoteBean.PolloptionsBean polloption = mPolloptionList.get(position);
        final String html = "选择第" +(position+1)+"项的人";
        tv.setText(Html.fromHtml(html));
        LinearLayout item2 = (LinearLayout) convertView.findViewById(R.id.item2);
        item2.setVisibility(View.GONE);
        return convertView;
    }

}
