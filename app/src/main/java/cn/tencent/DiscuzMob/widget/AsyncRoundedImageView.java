package cn.tencent.DiscuzMob.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import cn.tencent.DiscuzMob.base.RedNetApp;

/**
 * Created by AiWei on 2015/5/16.
 */
public class AsyncRoundedImageView extends RoundedImageView implements Callback {

    private Picasso mPicasso;

    public AsyncRoundedImageView(Context context) {
        super(context);
        mPicasso = Picasso.with(context);
    }

    public AsyncRoundedImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AsyncRoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPicasso = Picasso.with(context);
    }

    public void load(String url) {
        load(url, 0);
    }

    public void load(String url, int placeHolderResID) {
        if (placeHolderResID == 0) {
            placeHolderResID = android.R.drawable.screen_background_light_transparent;
        }
        if (TextUtils.isEmpty(url)) {
            mPicasso.load(placeHolderResID).tag(RedNetApp.TAG_PICASSO).into(this, this);
        } else {
            mPicasso.load(url).tag(RedNetApp.TAG_PICASSO).placeholder(placeHolderResID).into(this, this);
        }
    }

    @Override
    public void onError() {

    }

    @Override
    public void onSuccess() {

    }

}
