package cn.tencent.DiscuzMob.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.model.Banere;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/4/29.
 */
public class BanerePagerAdapter extends PagerAdapter {

    private List<Banere> mBanere;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Object tag = v.getTag();
            if (tag instanceof Banere) {
                v.getContext().startActivity(RednetUtils.newWebIntent(v.getContext(), ((Banere) tag).getUrl())
                        .putExtra("title", ((Banere) tag).getName()));
            }
        }
    };

    public BanerePagerAdapter() {
    }

    public BanerePagerAdapter(List<Banere> baneres) {
        this.mBanere = baneres;
    }

    @Override
    public int getCount() {
        if (mBanere != null && !mBanere.isEmpty()) {
            int size = mBanere.size();
            return size / 5 + (size % 5 == 0 ? 0 : 1);
        } else {
            return 0;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter_banere_header, container, false);
        int index = 5 * position;
        ViewGroup group = (ViewGroup) view;
        for (int i = index, j = 0, size = Math.min(index + 5, mBanere.size()); j < 5; j++, i++) {
            ViewGroup vg = (ViewGroup) group.getChildAt(j);
            if (i < size) {
                vg.setVisibility(View.VISIBLE);
                Banere banere = mBanere.get(i);
                AsyncRoundedImageView cover = (AsyncRoundedImageView) vg.getChildAt(0);
                TextView title = (TextView) vg.getChildAt(1);
                title.setText(banere.getName());
                String icon = banere.getImage();
                cover.load(!TextUtils.isEmpty(icon) ? banere.getImage() : null);
//                vg.setTag(banere.getFid());
                vg.setTag(banere);
                vg.setOnClickListener(mOnClickListener);
            } else {
                vg.setVisibility(View.INVISIBLE);
                vg.setTag(null);
            }
        }
        container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
