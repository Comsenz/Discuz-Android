package cn.tencent.DiscuzMob.utils.glide_agent;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;

public class GlideConfiguration implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        GlideHelper.resetGlide(builder);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
