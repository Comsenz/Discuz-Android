package cn.tencent.DiscuzMob.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.model.CatlistBean;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/4/7.
 */

public class OftenAdapter extends BaseAdapter {
    private List<CatlistBean> catlist;

    public OftenAdapter(List<CatlistBean> catlist) {
        this.catlist = catlist;
//        Log.e("TAG", "catlist.size()" + catlist.size());
    }

    @Override
    public int getCount() {
        return catlist == null ? 0 : catlist.size();
    }

    @Override
    public Object getItem(int position) {
        return catlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler = null;
        if (convertView == null) {
            viewHoler = new ViewHoler();
            convertView = View.inflate(parent.getContext(), R.layout.often_item, null);
            viewHoler.forumName = (TextView) convertView.findViewById(R.id.tv_forumName);
            viewHoler.tv_themecount = (TextView) convertView.findViewById(R.id.tv_themecount);
            viewHoler.tv_forumcount = (TextView) convertView.findViewById(R.id.tv_forumcount);
            viewHoler.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) convertView.getTag();
        }

        viewHoler.forumName.setText(catlist.get(position).getName());
        return convertView;
    }

    class ViewHoler {
        private TextView forumName,tv_themecount,tv_forumcount,tv_time;
    }
}
