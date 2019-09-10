package cn.tencent.DiscuzMob.ui.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.model.Star;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.ui.activity.MyPmActivity;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/6/21.
 */
public class FriendAdapter extends BaseAdapter {

    private Activity mActivity;
    private List<Star> mList;
    String formhash;

    public FriendAdapter(Activity activity) {
        this.mActivity = activity;
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() instanceof Star) {
                MyPmActivity.startActivityToMyPm(mActivity, ((Star) v.getTag()).getUid(), ((Star) v.getTag()).getUsername(), formhash);
            }
        }
    };

    public void set(List<Star> list, String formhash) {
        this.formhash = formhash;
        if (list != null && !list.isEmpty()) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    public void append(List<Star> list) {
        if (list != null && !list.isEmpty()) {
            if (mList == null) {
                mList = list;
            } else {
                mList.addAll(list);
            }
            notifyDataSetChanged();
        }
    }

    public void cleanData() {
        if (mList != null) {
            mList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_profile_item, parent, false);
            holder.header = (AsyncRoundedImageView) convertView.findViewById(R.id.friend_avatar_preview);
            holder.username = (TextView) convertView.findViewById(R.id.friend_name);
            holder.message = (TextView) convertView.findViewById(R.id.send_msg_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Star star = mList.get(position);
        Log.e("TAG", "");
        holder.username.setText(star.getUsername());
        int imageSetting = RedNetPreferences.getImageSetting();
        LogUtils.i(star.getAvatar());
        if (imageSetting == 0) {
            holder.header.load(star.getAvatar(), R.drawable.ic_header_def);
        } else {
            holder.header.setImageResource(R.drawable.ic_header_def);
        }

        holder.message.setTag(star);
        holder.message.setOnClickListener(mOnClickListener);
        return convertView;
    }

    static class ViewHolder {
        AsyncRoundedImageView header;
        TextView username;
        TextView message;
    }

}
