package cn.tencent.DiscuzMob.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;

/**
 * Created by cg on 2017/4/28.
 */

public class GridAdapter extends BaseAdapter {
    private List<String> imglist;

    public GridAdapter(List<String> imglist) {
        this.imglist = imglist;
    }

    @Override
    public int getCount() {
        return imglist.size();
    }

    @Override
    public Object getItem(int position) {
        return imglist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AsyncRoundedImageView iv_picture;
        if (convertView == null) {
            iv_picture = new AsyncRoundedImageView(parent.getContext());
            iv_picture.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 160));//设置ImageView对象布局
            iv_picture.setAdjustViewBounds(false);//设置边界对齐
            iv_picture.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置刻度的类型
            iv_picture.setPadding(8, 8, 8, 8);//设置间距
        } else {
            iv_picture = (AsyncRoundedImageView) convertView;
        }
//        imageView.setImageResource(imgs[position]);//为ImageView设置图片资源
        iv_picture.load(imglist.get(position));
        return iv_picture;
    }
}
