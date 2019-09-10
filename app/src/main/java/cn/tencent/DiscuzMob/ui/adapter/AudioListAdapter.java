package cn.tencent.DiscuzMob.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.model.FileBean;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/6/19.
 */

public class AudioListAdapter extends BaseAdapter {
    private List<FileBean> audioPaths;

    public void setData(List<FileBean> audioPaths) {
        this.audioPaths = audioPaths;
    }

    @Override
    public int getCount() {
        return audioPaths == null ? 0 : audioPaths.size();
    }

    @Override
    public Object getItem(int position) {
        return audioPaths == null ? null : audioPaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return audioPaths == null ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.audioitem, null);
            viewHolder.tv_audiotime = (TextView) convertView.findViewById(R.id.tv_audiotime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_audiotime.setText(audioPaths.get(position).getFileLength()+"");
        return convertView;
    }

    class ViewHolder {
        private TextView tv_audiotime;
    }
}
