package cn.tencent.DiscuzMob.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.adapter.SlidePagerAdapter;
import cn.tencent.DiscuzMob.ui.dialog.CustomProgress;
import cn.tencent.DiscuzMob.utils.DensityUtil;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/*展示图片的大图*/
public class ShowPhotoActivity extends BaseActivity {

    private ViewPager viewPager_photo;
    private LinearLayout llyt_dots;
    private String cookiepre_auth;
    private String cookiepre_saltkey;
    private ArrayList<View> mList;
    private ImageView[] mImageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_photo);
        initViews();
    }

    private List<String> list = new ArrayList<>();
    int position;

    private void initViews() {
        CustomProgress.show(ShowPhotoActivity.this, "正在加载数据，请稍等...", true);
        viewPager_photo = (ViewPager) findViewById(R.id.viewPager);
        llyt_dots = (LinearLayout) findViewById(R.id.llyt_dots);
        cookiepre_auth = CacheUtils.getString(ShowPhotoActivity.this, "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(ShowPhotoActivity.this, "cookiepre_saltkey");
        Intent intent = getIntent();
        String form = intent.getStringExtra("form");
        position = intent.getIntExtra("position", 0);
        String jsonObject = intent.getStringExtra("jsonObject");
        JSONObject object = JSON.parseObject(jsonObject);
        JSONArray imagelist = object.getJSONArray("imagelist");
        JSONObject attachments = object.getJSONObject("attachments");
        JSONArray imagelists = object.getJSONArray("imagelists");
        list.clear();
        if (form.equals("live")) {
            for (int i = 0; i < imagelist.size(); i++) {
                JSONObject jsonObject1 = attachments.getJSONObject("" + imagelist.get(i));
                if (((String) imagelist.get(i)).contains("filename")) {
                    String attachment = jsonObject1.getString("attachment");
                    list.add(attachment);
                } else {

                    String url = jsonObject1.getString("url");
                    String attachment = jsonObject1.getString("attachment");
                    String imgUrl = AppNetConfig.IMGURL1 + url + attachment;
                    list.add(imgUrl);
                }

            }
        } else {
            for (int i = 0; i < imagelists.size(); i++) {
                JSONObject jsonObject1 = imagelists.getJSONObject(i);
                list.add(jsonObject1.getString("src"));
            }
        }

        initViewPager(list);
    }

    private void initViewPager(List<String> list) {
        mList = new ArrayList<View>();
        int j = 0;
        for (int i = 0; i < list.size(); i++) {
            PhotoView inflate = (PhotoView) View.inflate(ShowPhotoActivity.this, R.layout.image, null);
            String aid1 = list.get(i);
            String url = list.get(i);
            if (url.contains(AppNetConfig.IMGURL1)) {
                Picasso.with(ShowPhotoActivity.this)
                        .load(url)
                        .into(inflate);
            } else {
                inflate.setImageURI(Uri.parse(url));
            }

            mList.add(inflate);
            inflate.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    finish();
                }

                @Override
                public void onOutsidePhotoTap() {
                    finish();
                }
            });
        }
        // 底部点点实现
        mImageViews = new ImageView[mList.size()];
        for (int i = 0; i < mList.size(); i++) {
            mImageViews[i] = new ImageView(ShowPhotoActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(ShowPhotoActivity.this, 7), DensityUtil.dip2px(ShowPhotoActivity.this, 7));
            // 设置边界
            params.setMargins(7, 10, 7, 10);
            mImageViews[i].setLayoutParams(params);
            if (0 == i) {
                mImageViews[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                mImageViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
            llyt_dots.addView(mImageViews[i]);
        }

        viewPager_photo.setAdapter(new SlidePagerAdapter(mList, ShowPhotoActivity.this));
        viewPager_photo.addOnPageChangeListener(new onPageChangeListener());
        viewPager_photo.setCurrentItem(position);
        CustomProgress.cancle();
    }

    /**
     * 监听ViewPager滑动效果
     */
    private class onPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            // 更新小圆点图标
            for (int i = 0; i < mList.size(); i++) {
                if (position == i) {
                    mImageViews[i].setBackgroundResource(R.drawable.page_indicator_focused);
                } else {
                    mImageViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
