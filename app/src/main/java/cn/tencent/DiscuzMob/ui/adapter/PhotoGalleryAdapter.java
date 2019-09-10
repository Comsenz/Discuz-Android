package cn.tencent.DiscuzMob.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import cn.tencent.DiscuzMob.widget.AsyncImageView;
import cn.tencent.DiscuzMob.R;


public class PhotoGalleryAdapter extends BaseAdapter {

    private ArrayList<String> photoPaths;
    private LayoutInflater mInflater;
    private Context mContext;

    public PhotoGalleryAdapter(Context mContext) {
        this.photoPaths = new ArrayList<>();
        this.mContext = mContext;
        mInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return photoPaths.size();
    }

    @Override
    public String getItem(int position) {
        return photoPaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void selected(String path) {
        if (!TextUtils.isEmpty(path) && !"null".equals(path)) {
            if (photoPaths.contains(path)) {
                photoPaths.remove(path);
            }
            photoPaths.add(path);
            notifyDataSetChanged();
        }
    }

    public ArrayList<String> getSelectedPhotos() {
        return photoPaths;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.photo_gallery_item, parent, false);
            holder.img = (AsyncImageView) convertView.findViewById(R.id.photo_gallery_item_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.img.load("file:///" + photoPaths.get(position));
        //Bitmap bit = BitmapUtil.decodeSampledBitmapFromResource(photoPaths.get(position), 100, 100);
        //holder.img.setImageBitmap(bit);
        return convertView;
    }

    static class ViewHolder {
        AsyncImageView img;
    }

}
