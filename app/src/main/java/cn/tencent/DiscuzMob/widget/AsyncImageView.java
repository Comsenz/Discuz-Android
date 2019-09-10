package cn.tencent.DiscuzMob.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

import cn.tencent.DiscuzMob.base.RedNetApp;

/**
 * Created by AiWei on 2015/5/16.
 */
public class AsyncImageView extends ImageView implements Callback {

    private Picasso mPicasso;
    private Callback mResultCallback;

    public AsyncImageView(Context context) {
        super(context);
        mPicasso = Picasso.with(context);
    }

    public AsyncImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AsyncImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPicasso = Picasso.with(context);
    }

    public void setResultCallback(Callback callback) {
        this.mResultCallback = callback;
    }

    public void load(String url) {
        load(url, null);
    }

    public void load(String url, int placeHolderResId) {
        load(url, placeHolderResId, null, false);
    }

    public void load(String url, Transformation transformation) {
        load(url, 0, transformation, false);
    }

    public void load(String url, int placeHolderResID, Transformation transformation, boolean fit) {
        RequestCreator creator;
        if (placeHolderResID == 0) {
            placeHolderResID = android.R.drawable.screen_background_light_transparent;
        }
        if (!TextUtils.isEmpty(url)) {
            creator = mPicasso.load(url).placeholder(placeHolderResID);
        } else {
            creator = mPicasso.load(placeHolderResID);
        }
        if (fit) {
            creator.fit();
        }
        if (transformation != null) {
            creator.transform(transformation);
        }
        creator.tag(RedNetApp.TAG_PICASSO).into(this, this);
    }

    @Override
    public void onSuccess() {
        if (mResultCallback != null) {
            mResultCallback.onSuccess();
        }
    }

    @Override
    public void onError() {
        if (mResultCallback != null) {
            mResultCallback.onError();
        }
    }
}
