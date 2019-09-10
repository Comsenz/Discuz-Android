package cn.tencent.DiscuzMob.ui.adapter;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.model.ImglistBean;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/5/11.
 */

public class RecommendGlideAdapter extends BaseAdapter {
    private List<ImglistBean> imglist = new ArrayList<>();

    public void setData(List<ImglistBean> imglist) {
//        this.imglist = imglist;
        this.imglist.clear();
        if (imglist != null) {
            this.imglist.addAll(imglist);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return imglist == null ? 0 : imglist.size();
    }

    @Override
    public Object getItem(int position) {
        return imglist == null ? null : imglist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return imglist == null ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AsyncRoundedImageView iv_picture = null;
        ViewHoldwer viewHoldwer = null;
        if (convertView == null) {
            viewHoldwer = new ViewHoldwer();
            convertView = View.inflate(parent.getContext(), R.layout.img_item, null);
            iv_picture = new AsyncRoundedImageView(parent.getContext());
            iv_picture.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 160));//设置ImageView对象布局
            iv_picture.setAdjustViewBounds(false);//设置边界对齐
            iv_picture.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置刻度的类型
            iv_picture.setPadding(8, 8, 8, 8);//设置间距
            viewHoldwer.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            convertView.setTag(viewHoldwer);
        } else {
//            iv_picture = (AsyncRoundedImageView) convertView;
            viewHoldwer = (ViewHoldwer) convertView.getTag();
        }
        Log.i("07吾问无为谓无无无无03", "imglist.size[" + position + "]=" + (imglist == null ? 0 : imglist.size()));
//        imageView.setImageResource(imgs[position]);//为ImageView设置图片资源
        Log.e("TAG", "imglist.get(position)=" + imglist.get(position).toString());
//        if (null != imglist.get(position)) {
        String url = imglist.get(position).getAttachment();
        String attachment = imglist.get(position).getAttachment();
        viewHoldwer.iv_img.setVisibility(View.VISIBLE);
        String s = AppNetConfig.IMGURL1 + url + attachment;
//            Log.e("TAG", "s=" + s);
//        viewHoldwer.iv_img.load(s);
//        viewHoldwer.iv_img.setImageResource(R.drawable.ic_launcher);
        Uri uri = Uri.parse(s);
        Picasso.with(parent.getContext()).invalidate(uri);
        Picasso.with(parent.getContext()).load(s)
                .error(R.drawable.picture)
                .into(viewHoldwer.iv_img);
//        } else {
//            viewHoldwer.iv_img.setVisibility(View.GONE);
//        }

        return convertView;
    }


    class ViewHoldwer {
        private ImageView iv_img;
    }
}
