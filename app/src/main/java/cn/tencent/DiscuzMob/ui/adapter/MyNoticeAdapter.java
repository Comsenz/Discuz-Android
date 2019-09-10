package cn.tencent.DiscuzMob.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.db.User;
import cn.tencent.DiscuzMob.model.MyMessage;
import cn.tencent.DiscuzMob.model.MyNotice;
import cn.tencent.DiscuzMob.ui.activity.UserDetailActivity;
import cn.tencent.DiscuzMob.ui.dialog.ProgressDialog;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/6/16.
 */
public class MyNoticeAdapter extends BaseAdapter {

    private Context mContext;
    private ProgressDialog mDialog;
    private List mData;
    private View.OnClickListener mOnOptionsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() instanceof MyNotice) {
                UserDetailActivity.makeFriend(mContext, mDialog, ((MyNotice) v.getTag()).getFrom_id(),"2");
            }
        }
    };

    public MyNoticeAdapter(Context context) {
        this.mContext = context;
        this.mDialog = new ProgressDialog(context);
    }

    public void set(List list) {
        if (list != null && !list.isEmpty()) {
            mData = list;
            notifyDataSetChanged();
        }
    }

    public void append(List list) {
        if (list != null && !list.isEmpty()) {
            if (mData == null)
                mData = list;
            else
                mData.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_message_item, parent, false);
            holder.item2 = convertView.findViewById(R.id.item2);
            holder.item3 = convertView.findViewById(R.id.item3);
            holder.header = (AsyncRoundedImageView) convertView.findViewById(R.id.header);
            holder.title2 = (TextView) holder.item2.findViewById(R.id.title);
            holder.title3 = (TextView) holder.item3.findViewById(R.id.title);
            holder.dateline2 = (TextView) holder.item2.findViewById(R.id.dateline);
            holder.dateline3 = (TextView) holder.item3.findViewById(R.id.dateline);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.options = (TextView) convertView.findViewById(R.id.options);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Object data = mData.get(position);
        if (data instanceof MyMessage) {
            User user = RedNetApp.getInstance().getUserLogin(false);
            MyMessage message = (MyMessage) data;
            holder.dateline2.setText(RednetUtils.DateFormat.MILLI2.dateFormat
                    .format(RednetUtils.covertMillSeconds(String.valueOf(message.getDateline()))));
            if (!message.getMsgfromid().equals(user.getMember_uid())) {
                holder.header.load(null, R.drawable.ic_message_sys);
                holder.title2.setText("系统消息");
                holder.text.setText(Html.fromHtml(new StringBuilder("<font color=\"#3c96d6\">").append(message.getMsgfrom()).append("</font>")
                        .append("回复了你:").append(message.getSubject()).toString()));
            } else {
                holder.header.load(null, R.drawable.ic_header_def);
                holder.title2.setText(message.getTousername());
                holder.text.setText(Html.fromHtml(message.getSubject()));
            }
            holder.item2.setVisibility(View.VISIBLE);
            holder.item3.setVisibility(View.GONE);
        } else if (data instanceof MyNotice) {
            MyNotice notice = (MyNotice) data;
            holder.header.load(null, R.drawable.ic_message_sys);
            holder.dateline3.setText(RednetUtils.DateFormat.MILLI2.dateFormat.format(RednetUtils.covertMillSeconds(String.valueOf(notice.getDateline()))));
            if ("friend".equals(notice.getType()) && "friendrequest".equals(notice.getFrom_idtype())) {
                holder.options.setTag(notice);
                holder.options.setOnClickListener(mOnOptionsClickListener);
                holder.options.setVisibility(View.VISIBLE);
            } else {
                holder.options.setTag(null);
                holder.options.setOnClickListener(null);
                holder.options.setVisibility(View.GONE);
            }
            String note = notice.getNote().replaceAll(notice.getAuthor()
                    , new StringBuilder("<font color=\"#3c96d6\">").append(notice.getAuthor()).append("</font>").toString());
            if ("post".equals(notice.getType()) && notice.getNotevar().containsKey("subject")) {
                String subject = notice.getNotevar().get("subject");
                note = note.replaceAll(subject, new StringBuilder("<font color=\"#3c96d6\">").append(subject).append("</font>").toString());
            }
            holder.title3.setText(Html.fromHtml(note));
            holder.item2.setVisibility(View.GONE);
            holder.item3.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    static class ViewHolder {
        View item2, item3;
        AsyncRoundedImageView header;
        TextView title2, title3;
        TextView dateline2, dateline3;
        TextView text, options;
    }

}
