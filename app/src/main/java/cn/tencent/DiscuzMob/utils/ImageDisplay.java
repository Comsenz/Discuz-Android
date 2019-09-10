package cn.tencent.DiscuzMob.utils;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;

import cn.tencent.DiscuzMob.utils.glide_agent.GlideCircleTransform;
import cn.tencent.DiscuzMob.utils.glide_agent.GlideRoundTransUtils;
import cn.tencent.DiscuzMob.utils.glide_agent.GlideRoundTransform;
import java.io.File;
import java.util.concurrent.ExecutionException;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.utils.glide_agent.RoundTransform;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Havorld
 */

public class ImageDisplay {

    private static Context mContext;

    public static Context getContext() {
        if (mContext == null) {
            mContext = RedNetApp.getAppContext();
        }
        return mContext;
    }

    public ImageDisplay() {
    }

    private static class ImageDisplayHolder {
        private final static ImageDisplay INSTANCE = new ImageDisplay();
    }

    public static ImageDisplay getInstance() {
        return ImageDisplayHolder.INSTANCE;
    }

    /**
     * 加载图片
     *
     * @param url       要加载的图片地址
     * @param imageView 要设置的控件
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void load(Context context, ImageView imageView, String url) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(url).placeholder(android.R.drawable.screen_background_light_transparent).error(android.R.drawable.screen_background_light_transparent).dontTransform().override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(imageView);
    }

    /**
     * 加载图片
     * 圆角
     * 解决方式： 自定义ImageView 后加载 圆角
     * 重新切图
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void load(Context context, ImageView imageView, String url, int round, int place, int error) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(url).placeholder(place).error(error)
                .transform(new CenterCrop(getContext()), new GlideRoundTransUtils(getContext(), round))
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param url       要加载的图片地址
     * @param imageView 要设置的控件
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void load(Context context, ImageView imageView, String url, int errorId) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(url).placeholder(errorId).error(errorId).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(imageView);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadVp(Context context, final ImageView imageView, String url) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context)
                .load(url)
                .into(new ImageTarget(imageView, true) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
                        if (!resource.isRunning()) {
                            resource.start();
                        }
                    }
                });
    }

    /**
     * 加载图片
     *
     * @param imageView  要设置的控件
     * @param resourceId 要加载的图片资源id
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void load(Context context, ImageView imageView, int resourceId) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(resourceId).placeholder(android.R.drawable.screen_background_light_transparent).error(android.R.drawable.screen_background_light_transparent).dontTransform().override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(imageView);
    }


    /**
     * @param imageView 要设置的控件
     * @param url       要加载的图片地址
     * @param width     设置图片的宽
     * @param height    设置图片的高
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void load_width_height(Context context, ImageView imageView, String url, int width, int height) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(url).placeholder(android.R.drawable.screen_background_light_transparent).error(android.R.drawable.screen_background_light_transparent).override(width, height).dontTransform().into(imageView);
    }


    /**
     * @param errorId   加载中图片
     * @param imageView 要设置的控件
     * @param url       要加载的图片地址
     * @param width     设置图片的宽
     * @param height    设置图片的高
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void load(Context context, ImageView imageView, String url, int width, int height, int errorId, int round) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(url).placeholder(errorId).error(errorId).override(width, height).transform(new GlideRoundTransform(getContext(), round)).into(imageView);
    }

    /**
     * @param imageView
     * @param file
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void load(Context context, ImageView imageView, File file) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(file).into(imageView);
    }

    /**
     * @param imageView
     * @param uri
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void load(Context context, ImageView imageView, Uri uri) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(uri).into(imageView);
    }

    /**
     * @param width  设置图片的宽
     * @param height 设置图片的高
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadGif(Context context, String url, int width, int height, RequestListener<String, GifDrawable> listener) {
        if (url == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(url).asGif().override(width, height).diskCacheStrategy(DiskCacheStrategy.NONE).listener(listener).into(width, height);
    }

    /**
     *
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadGif(Context context, String url, RequestListener<String, GifDrawable> listener, int loading) {
        if (url == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(url).asGif().placeholder(loading).error(loading).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).diskCacheStrategy(DiskCacheStrategy.NONE).listener(listener).into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
    }

    /**
     * @param width  设置图片的宽
     * @param height 设置图片的高
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadGif(Context context, final int resid, int width, int height, RequestListener<Integer, GifDrawable> listener) {
        if (getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        //diskCacheStrategy   不是用缓存 ALL 使用已有的缓存
        Glide.with(context).load(resid).asGif().override(width, height).diskCacheStrategy(DiskCacheStrategy.NONE).listener(listener).into(width, height);
    }

    /**
     * @param imageView 要设置的控件
     * @param resid
     * @param width     设置图片的宽
     * @param height    设置图片的高
     */
    public static void loadGif(Context context, final ImageView imageView, final int resid, int width, int height) {
        loadGif(context, resid, width, height, new RequestListener<Integer, GifDrawable>() {
            @Override
            public boolean onException(Exception e, Integer model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, Integer model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                imageView.setImageDrawable(resource.getCurrent());
                resource.start();
                return false;
            }
        });
    }

    /**
     * @param imageView 要设置的控件
     * @param url
     * @param width     设置图片的宽
     * @param height    设置图片的高
     */
    public static void loadGif(Context context, final ImageView imageView, final String url, int width, int height) {
        loadGif(context, url, width, height, new RequestListener<String, GifDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                imageView.setImageDrawable(resource.getCurrent());
                resource.start();
                return false;
            }
        });
    }

    /**
     * @param url
     * @param loading
     * @param listener
     */
    public static void loadGif(Context context, final String url, int loading, RequestListener<String, GifDrawable> listener) {
        loadGif(context, url, listener, loading);
    }

    /**
     * @param imageView 要设置的控件
     * @param url
     */
    public static void loadGif(Context context, final ImageView imageView, final String url, int loading) {
        loadGif(context, url, new RequestListener<String, GifDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                imageView.setImageDrawable(resource.getCurrent());
                resource.start();
                return false;
            }
        }, loading);
    }


    /**
     * @param url
     * @return
     */
    public static String downPic(Context context, String url) {
        String path = "";
        if (context == null) {
            context = getContext();
        }
        try {
            File file = Glide.with(context).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            path = file.getAbsolutePath();
        } catch (InterruptedException e) {
            e.printStackTrace();
            path = "";
        } catch (ExecutionException e) {
            e.printStackTrace();
            path = "";
        }
        return path;
    }

    /**
     * 设置圆形图片
     *
     * @param imageView 要设置的控件
     * @param url
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadcircleImage(Context context, final ImageView imageView, final String url) {
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(url).placeholder(getContext().getResources().getDrawable(R.drawable.ic_disable)).error(getContext().getResources().getDrawable(R.drawable.ic_disable)).transform(new GlideCircleTransform(getContext())).into(imageView);
    }

    /**
     * 加载图片
     * 发现瀑布流
     * 图片 上 圆角
     *
     * @param url       要加载的图片地址
     * @param imageView 要设置的控件
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadFalls(Context context, final ImageView imageView, String url, int color, int[] wh) {

        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        ColorDrawable colorDrawable = new ColorDrawable(color);
//        Glide.with(context).load(url).placeholder(colorDrawable).error(colorDrawable)
////                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
////                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                .transform(new GlideFallsTransform(getContext(), BitmapFillet.TOP, wh))
//                .into(imageView);
        Glide.with(context).load(url).placeholder(colorDrawable).error(colorDrawable)
//                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .transform(new RoundTransform(RoundTransform.ROUND_SNAP_DP).setWidth(wh[0]).setRoundType(RoundTransform.RoundType.TOP))
                .into(imageView);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadFalls(Context context, ImageView imageView, String url, int errorId, int round, int[] wh) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        ColorDrawable colorDrawable = new ColorDrawable(errorId);
        Glide.with(context).load(url)
                .placeholder(colorDrawable)
                .error(colorDrawable)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .transform(new RoundTransform(RoundTransform.ROUND_SNAP_DP).setWidth(wh[0]).setRoundType(RoundTransform.RoundType.TOP))
                .into(imageView);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadFalls(Context context, ImageView imageView, String url, int errorId, int round, boolean isimg) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(url).placeholder(getContext().getResources().getDrawable(errorId)).error(getContext().getResources().getDrawable(errorId)).transform(new GlideRoundTransform(getContext(), round)).into(imageView);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadCircle(Context context, ImageView imageView, String url, int errorId) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(url).placeholder(getContext().getResources().getDrawable(errorId)).error(getContext().getResources().getDrawable(errorId)).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).transform(new GlideCircleTransform(getContext())).into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param imageView imageView
     * @param url       图片链接
     * @param width     图片展示宽度
     * @param height    图片展示高度
     * @param round     圆角dp
     * @param placeId   默认图片id
     * @param errorId   错误图片id
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadWithRound(Context context, ImageView imageView, String url, int width, int height, int round, int placeId, int errorId) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        if (!(width > 0 && height > 0)) {
            width = Target.SIZE_ORIGINAL;
            height = Target.SIZE_ORIGINAL;
        }
        Glide.with(context).load(url).asBitmap().placeholder(placeId).error(errorId).override(width, height)
                .transform(new GlideRoundTransform(getContext(), round))
                .into(imageView);
    }

    public static void loadWithRound(Context context, ImageView imageView, String url, int width, int height, int round) {
        loadWithRound(context, imageView, url, width, height, round, android.R.drawable.screen_background_light_transparent, android.R.drawable.screen_background_light_transparent);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadWithCircle(Context context, ImageView imageView, String url) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(url).placeholder(android.R.drawable.screen_background_light_transparent).error(android.R.drawable.screen_background_light_transparent).transform(new GlideCircleTransform(getContext())).into(imageView);
    }

    /**
     * @param imageView
     * @param file
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadLocalImage(Context context, ImageView imageView, File file) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(file).placeholder(android.R.drawable.screen_background_light_transparent).error(android.R.drawable.screen_background_light_transparent).into(imageView);
    }

    public static void loadWithTarget(Context context, String url, int width, int height, ViewTarget target) {
        loadWithTarget(context, url, width, height, android.R.drawable.screen_background_light_transparent, android.R.drawable.screen_background_light_transparent, null, target);
    }

    public static void loadWithTarget(Context context, String url, ViewTarget target) {
        loadWithTarget(context, url, 0, 0, 0, 0, null, target);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadWithTarget(Context context, String url, int width, int height, int defaultId, int errorid, RequestListener<String, GlideDrawable> listener, ViewTarget target) {
        if (getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        if (!(width > 0 && height > 0)) {
            width = Target.SIZE_ORIGINAL;
            height = Target.SIZE_ORIGINAL;
        }
        Glide.with(context).load(url).placeholder(defaultId).error(errorid).override(width, height).listener(listener).into(target);
    }
    /**
     * 加载圆形图片
     *
     * @param url       要加载的图片地址
     * @param imageView 要设置的控件
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadCirimageView(Context context, CircleImageView imageView, String url) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(url).placeholder(mContext.getResources().getDrawable(R.drawable.ic_disable)).error(mContext.getResources().getDrawable(R.drawable.ic_disable)).dontTransform().override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(imageView);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadCircle(Context context, ImageView imageView, String url) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(url).placeholder(android.R.drawable.screen_background_light_transparent).error(android.R.drawable.screen_background_light_transparent).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).transform(new GlideCircleTransform(getContext())).into(imageView);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadPositiveAvatar(Context context, ImageView imageView, String url) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(url).placeholder(android.R.drawable.screen_background_light_transparent).error(android.R.drawable.screen_background_light_transparent).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).transform(new GlideCircleTransform(getContext())).dontAnimate().into(imageView);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadLeftAvatar(Context context, ImageView imageView, String url) {
        if (imageView == null || getContext() == null)
            return;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(url).placeholder(android.R.drawable.screen_background_light_transparent).error(android.R.drawable.screen_background_light_transparent).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).transform(new GlideCircleTransform(getContext())).dontAnimate().into(imageView);
    }

    /**
     * 获取bitmap
     *
     * @param url    图片链接
     * @param width  图片展示宽度
     * @param height 图片展示高度
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap getBitmap(Context context, String url, int width, int height) {
        Bitmap bm = null;
        if (context == null) {
            context = getContext();
        } else if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return null;
        }
        try {
            bm = Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .centerCrop()
                    .into(width, height)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return bm;
    }

    /**
     * 获取bitmap
     *
     * @param url 图片链接
     * @param
     */
    public static void getBitmap(String url, SimpleTarget target) {
        Glide.with(getContext()).load(url).asBitmap().into(target);
    }

    public static void cancle(View v) {
        Glide.clear(v);
    }

    static class ImageTarget extends SimpleTarget<GlideDrawable> {

        ImageView mImg;
        ObjectAnimator animator;
        RotateDrawable loadingDrawable;
        boolean isVp;

        public ImageTarget(ImageView imageView, boolean isVp) {
            mImg = imageView;
            this.isVp = isVp;

        }

        public ImageTarget(ImageView imageView) {
            mImg = imageView;
        }

        @Override
        public void onStart() {
            if (isVp) {
                mImg.setScaleType(ImageView.ScaleType.CENTER);
                final int loadResid =android.R.drawable.screen_background_light_transparent;
                loadingDrawable = (RotateDrawable) getContext().getResources()
                        .getDrawable(loadResid);
                animator = getRotateAnimator(loadingDrawable);
                mImg.setImageDrawable(loadingDrawable);
                animator.start();
            }
        }

        @Override
        public void onStop() {
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public void onLoadStarted(Drawable placeholder) {
            if (!isVp) {
                mImg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                mImg.setImageDrawable(placeholder);
                mImg.setBackgroundColor(getContext().getResources().getColor(R.color.gray));
            }
        }

        @Override
        public void onLoadFailed(Exception e, Drawable errorDrawable) {
        }

        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

            if (!isVp) {
                mImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImg.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
            } else {
                mImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }

            mImg.setImageDrawable(resource);
        }

        @Override
        public void onLoadCleared(Drawable placeholder) {

        }

        @Override
        public void setRequest(Request request) {

        }

        @Override
        public Request getRequest() {
            return null;
        }
    }

    public static class FallsTarget extends ImageViewTarget<GlideDrawable> {

        public FallsTarget(ImageView view) {
            super(view);
        }

        @Override
        public void onLoadStarted(Drawable placeholder) {
//             super.onLoadStarted(placeholder);
            ImageView imageView = getView();
            imageView.setBackground(placeholder);
//            imageView.setImageDrawable(placeholder);
//            imageView.setBackgroundColor(getContext().getResources().getColor(COLOR_BACKGROUND));
        }

        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
            super.onResourceReady(resource, glideAnimation);
            ImageView imageView = getView();
            imageView.setImageDrawable(resource);
            imageView.setBackground(null);
            if (!resource.isRunning()) {
                resource.start();
            }
        }

        @Override
        protected void setResource(GlideDrawable resource) {

        }

        @Override
        public void onLoadFailed(Exception e, Drawable errorDrawable) {
            //super.onLoadFailed(e, errorDrawable);
            ImageView imageView = getView();
            imageView.setBackground(errorDrawable);
        }

        @Override
        public void onLoadCleared(Drawable placeholder) {
//             super.onLoadCleared(placeholder);
            ImageView imageView = getView();
            imageView.setBackground(placeholder);
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * 下载中动画
     *
     * @param loadingDrawable
     * @return
     */
    private static ObjectAnimator getRotateAnimator(RotateDrawable loadingDrawable) {

        Property<RotateDrawable, Integer> ROTATE_DRAWABLE_PROPERTY = new Property<RotateDrawable, Integer>(
                Integer.class, "RotateDrawableProperty") {

            @Override
            public void set(RotateDrawable drawable, Integer value) {
                drawable.setLevel(value);
            }

            @Override
            public Integer get(RotateDrawable drawable) {
                return drawable.getLevel();
            }
        };
        final ObjectAnimator mAnimator = ObjectAnimator.ofInt(loadingDrawable, ROTATE_DRAWABLE_PROPERTY, 0, 10000);
        mAnimator.setDuration(600);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        return mAnimator;
    }
}
