package cn.tencent.DiscuzMob.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.model.Profile;
import cn.tencent.DiscuzMob.model.VoterVariables;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.ui.activity.MyPmActivity;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2015/5/11.
 * 板块列表
 */
public class SampleProfileAdapter extends BaseAdapter {

    private List<VoterVariables.VariablesBean.ViewvoteBean.VoterlistBean> mProfileList;
    private Context mContext;
    private String formhash;

    public SampleProfileAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<VoterVariables.VariablesBean.ViewvoteBean.VoterlistBean> mProfileList, String formhash) {
        this.formhash = formhash;
        if (mProfileList != null) {
            this.mProfileList = mProfileList;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mProfileList == null ? 0 : mProfileList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProfileList.get(position).getUid();
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_profile_item, parent, false);
            holder.userName = (TextView) convertView.findViewById(R.id.friend_name);
            holder.preview = (AsyncRoundedImageView) convertView.findViewById(R.id.friend_avatar_preview);
            holder.sendBtn = (TextView) convertView.findViewById(R.id.send_msg_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final VoterVariables.VariablesBean.ViewvoteBean.VoterlistBean profile = mProfileList.get(position);
        holder.userName.setText(profile.getUsername());
        holder.preview.load(profile.getAvatar(), R.drawable.ic_header_def);
        holder.sendBtn.setVisibility(profile.getUid().equals(RedNetApp.getInstance().getUid()) ? View.GONE : View.VISIBLE);
        holder.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPmActivity.startActivityToMyPm(mContext, profile.getUid(), profile.getUsername(), formhash);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView userName;
        AsyncRoundedImageView preview;
        TextView sendBtn;
    }

}
