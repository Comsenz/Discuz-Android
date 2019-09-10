package cn.tencent.DiscuzMob.ui.adapter;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.LetterBean;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/4/19.
 */
//class AppAdapter extends BaseAdapter {


public class LetterAdapter extends BaseAdapter {
    List<LetterBean.VariablesBean.ListBean> list;


    public void setData(List<LetterBean.VariablesBean.ListBean> list1) {
        this.list = list1;
        notifyDataSetChanged();
    }

    public void appendData(List<LetterBean.VariablesBean.ListBean> list1) {
        if (this.list == null) {
            this.list = list1;
        } else {
            this.list.addAll(list1);
        }
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
        if (convertView == null) {
            convertView = View.inflate(RedNetApp.getInstance(),
                    R.layout.sample_message_item, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        String message = list.get(position).getMessage();
        String subject = list.get(position).getSubject();
        String vdateline = list.get(position).getVdateline();
        holder.text.setText(Html.fromHtml(subject));
        holder.header.load(list.get(position).getFromavatar(), R.drawable.ic_header_def);
        holder.title.setText(list.get(position).getTousername());
        holder.dateline.setText(Html.fromHtml(vdateline));
//        holder.item0.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                parent.getContext().startActivity(new Intent(parent.getContext(), MyPmActivity.class).putExtra("touid", list.get(position).getTouid()).putExtra("tousername", list.get(position).getTousername()));
//            }
//        });
        return convertView;
    }

    class ViewHolder {
        TextView title, dateline, text, tv_name;
        AsyncRoundedImageView header;
        LinearLayout item0;
        ImageView iv_icon;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.title);
            dateline = (TextView) view.findViewById(R.id.dateline);
            text = (TextView) view.findViewById(R.id.text);
            header = (AsyncRoundedImageView) view.findViewById(R.id.header);
            item0 = (LinearLayout) view.findViewById(R.id.item0);

            view.setTag(this);
        }
    }
}
