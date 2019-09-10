package cn.tencent.DiscuzMob.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.widget.NoScrollGridView;
import cn.tencent.DiscuzMob.R;


/**
 * Created by cg on 2017/5/17.
 */

public class InteractionAadpter extends BaseAdapter {
//    List<JSONObject> jsonObjects = new ArrayList<>();
    JSONArray jsonObjects = new JSONArray();
    private String authorid;
    public void clearData() {
        jsonObjects.clear();
        notifyDataSetChanged();
    }
    public InteractionAadpter(String authorid) {
        this.authorid = authorid;
    }

    public void addData(JSONArray jsonObjects) {
        this.jsonObjects.addAll(jsonObjects);
    }

    public void setData(JSONArray jsonObjects) {
        this.jsonObjects.clear();
        this.jsonObjects.addAll(jsonObjects);
    }

    @Override
    public int getCount() {
        return jsonObjects == null ? 0 : jsonObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return jsonObjects == null ? null : jsonObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.interaction_item, null);
            viewHolder.avatar_author = (AsyncRoundedImageView) convertView.findViewById(R.id.avatar_author);
            viewHolder.tv_authorName = (TextView) convertView.findViewById(R.id.tv_authorName);
            viewHolder.tv_leve = (TextView) convertView.findViewById(R.id.tv_leve);
            viewHolder.tv_tiem = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_subject = (TextView) convertView.findViewById(R.id.tv_subject);
            viewHolder.gridView = (NoScrollGridView) convertView.findViewById(R.id.gridView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        JSONObject jsonObject = (JSONObject) jsonObjects.get(position);
//        if(!authorid.equals(jsonObject.getString("authorid"))) {
            String author = jsonObject.getString("author");
            String avatar = jsonObject.getString("avatar");
            String dateline = jsonObject.getString("dateline");
            String message = jsonObject.getString("message");
            viewHolder.tv_authorName.setText(author);
            viewHolder.tv_subject.setText(message);
            if(avatar.contains("src")) {
                int i = avatar.indexOf("=");
                int i1 = avatar.indexOf("/>");
                avatar = avatar.substring(i,i1);
            }
            viewHolder.avatar_author.load(avatar,R.drawable.ic_header_def);
            if (dateline.contains("title")) {
                int i2 = dateline.indexOf("=");
                int i3 = dateline.indexOf(">");
                dateline = dateline.substring(i2 + 2, i3 - 1);
            }
            viewHolder.tv_tiem.setText(dateline);
//        }
        return convertView;
    }


    class ViewHolder {
        AsyncRoundedImageView avatar_author;
        TextView tv_authorName;
        TextView tv_leve;
        TextView tv_tiem;
        TextView tv_subject;
        NoScrollGridView gridView;
    }
}
