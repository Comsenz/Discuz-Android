package cn.tencent.DiscuzMob.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.model.Smiley;
import cn.tencent.DiscuzMob.R;

public class FaceAdapter extends BaseAdapter {

    private List<Smiley> data;

    private LayoutInflater inflater;

    private int size = 0;

    public FaceAdapter(Context context, List<Smiley> list) {
        this.inflater = LayoutInflater.from(context);
        this.data = list;
        this.size = list.size();
    }

    @Override
    public int getCount() {
        return this.size;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Smiley emoji = data.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_face, parent, false);
            viewHolder.iv_face = (AsyncRoundedImageView) convertView.findViewById(R.id.item_iv_face);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        viewHolder.iv_face.load(Config.SMEIL_PATH+emoji.getImage());
        viewHolder.iv_face.setImageResource(emoji.getResCode());
        return convertView;
    }

    class ViewHolder {
        public AsyncRoundedImageView iv_face;
    }
}