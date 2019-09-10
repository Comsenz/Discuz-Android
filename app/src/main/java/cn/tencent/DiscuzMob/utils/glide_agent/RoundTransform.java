package cn.tencent.DiscuzMob.utils.glide_agent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.utils.CommonUtils;

public class RoundTransform extends CenterCrop {

    public enum RoundType {
        ALL("ALL"),
        TOP("TOP"),
        LEFT("LEFT"),
        RIGHT("RIGHT"),
        BOTTOM("BOTTOM"),
        TOP_LEFT("TOP_LEFT"),
        TOP_RIGHT("TOP_RIGHT"),
        BOTTOM_LEFT("BOTTOM_LEFT"),
        BOTTOM_RIGHT("BOTTOM_RIGHT");
        private final String type;

        RoundType(String type) {
            this.type = type;
        }
    }

    public static final int ROUND_DEFAULT_DP = 8;
    public static final int ROUND_SNAP_DP = 5;
    private int roundPx;
    private int width;
    private int height;
    private RoundType roundType = RoundType.ALL;

    public RoundTransform(int roundDp) {
        this(RedNetApp.getAppContext(), roundDp);
    }

    public RoundTransform(Context context, int roundDp) {
        super(context);
        this.roundPx = CommonUtils.dip2px(context, roundDp);
    }

    public RoundTransform setWidth(int width) {
        this.width = width;
        return this;
    }

    public RoundTransform setHeight(int height) {
        this.height = height;
        return this;
    }

    public RoundTransform setRoundType(RoundType roundType) {
        this.roundType = roundType;
        return this;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        int _roundPx = this.roundPx;
        if (_roundPx == 0) {
            return super.transform(pool, toTransform, outWidth, outHeight);
        } else if (width == 0 && height == 0) {
            return super.transform(pool, toTransform, outWidth, outHeight);
        } else {
            int _outWidth = outWidth;
            int _outHeight = outHeight;
            if (width == 0) {
                _outHeight = Math.min(toTransform.getHeight(), height);
                _outWidth = Math.round(1f * toTransform.getWidth() / (1f * toTransform.getHeight() / _outHeight));
                _roundPx = Math.round(1f * _outHeight / height * _roundPx);
            } else {
                if (height == 0) {
                    _outWidth = Math.min(toTransform.getWidth(), width);
                    _outHeight = Math.round(1f * toTransform.getHeight() / (1f * toTransform.getWidth() / _outWidth));
                    _roundPx = (int) Math.floor(1f * _outWidth / width * _roundPx);
                } else {
                    float scaleW = 1f * toTransform.getWidth() / width;
                    float scaleH = 1f * toTransform.getHeight() / height;
                    if (scaleW > scaleH) {
                        _outHeight = Math.min(height, toTransform.getHeight());
                        _outWidth = Math.round(1f * width * _outHeight / height);
                        _roundPx = (int) Math.floor(1f * _outHeight / height * _roundPx);
                    } else {
                        _outWidth = Math.min(width, toTransform.getHeight());
                        _outHeight = Math.round(1f * height * _outWidth / width);
                        _roundPx = (int) Math.floor(1f * _outWidth / width * _roundPx);
                    }
                }
            }
            Bitmap bitmap = super.transform(pool, toTransform, _outWidth, _outHeight);
            final Bitmap toReuse = pool.get(_outWidth, _outHeight, toTransform.getConfig() != null
                    ? toTransform.getConfig() : Bitmap.Config.ARGB_8888);
            if (toReuse != null && toReuse != bitmap && !pool.put(toReuse)) {
                toReuse.recycle();
            }
            Bitmap result = filletSmall(pool, bitmap, _outWidth, _outHeight, _roundPx, roundType);
            if (bitmap != null && bitmap != toTransform && !pool.put(bitmap)) {
                bitmap.recycle();
            }
            return result;
        }
    }

    @Override
    public String getId() {
        return new StringBuffer()
                .append(roundType.type).append("_")
                .append("round").append("_").append(roundPx)
                .append("width").append("_").append(width)
                .append("height").append("_").append(height)
                .toString();
    }

    /**
     * 指定图片的切边，对图片进行圆角处理
     *
     * @param pool
     * @param totransform 需要被切圆角的图片
     * @param outWidth
     * @param outHeight
     * @param roundPx     要切的像素大小
     * @param roundType
     * @return
     */
    public Bitmap filletSmall(BitmapPool pool, Bitmap totransform, int outWidth, int outHeight, int roundPx, RoundType roundType) {
        try {
            // 其原理就是：先建立一个与图片大小相同的透明的Bitmap画板
            // 然后在画板上画出一个想要的形状的区域。
            // 最后把源图片帖上。
            if (totransform == null) return null;
            Bitmap source;
            if (totransform.getWidth() < outWidth) {
                source = totransform;
            } else {
                source = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
                if (source == null) {
                    source = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
                }
                Canvas _canvas = new Canvas(source);
                final int width = totransform.getWidth();
                final int height = totransform.getHeight();
                final RectF _src = new RectF(0, 0, width, height);
                final RectF _dst = new RectF(0, 0, outWidth, outHeight);
                Matrix matrix = new Matrix();
                matrix.setRectToRect(_src, _dst, Matrix.ScaleToFit.CENTER);
                _canvas.drawBitmap(totransform, matrix, new Paint());
            }

            if (source == null) return null;
            final int width = source.getWidth();
            final int height = source.getHeight();
            Bitmap result = pool.get(width, height, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            canvas.save();
            canvas.drawARGB(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);
            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);

            final RectF rectF = new RectF(0, 0, width, height);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            final Rect rectLeftTop = new Rect(0, 0, roundPx, roundPx);
            final Rect rectRightTop = new Rect(width - roundPx, 0, width, roundPx);
            final Rect rectLeftBottom = new Rect(0, height - roundPx, roundPx, height);
            final Rect rectRightBottom = new Rect(width - roundPx, height - roundPx, width, height);

            switch (roundType) {
                case TOP:
                    canvas.drawRect(rectLeftBottom, paint);
                    canvas.drawRect(rectRightBottom, paint);
                    break;
                case LEFT:
                    canvas.drawRect(rectRightTop, paint);
                    canvas.drawRect(rectRightBottom, paint);
                    break;
                case RIGHT:
                    canvas.drawRect(rectLeftTop, paint);
                    canvas.drawRect(rectLeftBottom, paint);
                    break;
                case BOTTOM:
                    canvas.drawRect(rectLeftTop, paint);
                    canvas.drawRect(rectRightTop, paint);
                    break;
                case TOP_LEFT:
                    canvas.drawRect(rectRightTop, paint);
                    canvas.drawRect(rectLeftBottom, paint);
                    canvas.drawRect(rectRightBottom, paint);
                    break;
                case TOP_RIGHT:
                    canvas.drawRect(rectLeftTop, paint);
                    canvas.drawRect(rectLeftBottom, paint);
                    canvas.drawRect(rectRightBottom, paint);
                    break;
                case BOTTOM_LEFT:
                    canvas.drawRect(rectLeftTop, paint);
                    canvas.drawRect(rectRightTop, paint);
                    canvas.drawRect(rectRightBottom, paint);
                    break;
                case BOTTOM_RIGHT:
                    canvas.drawRect(rectLeftTop, paint);
                    canvas.drawRect(rectRightTop, paint);
                    canvas.drawRect(rectLeftBottom, paint);
                    break;
                case ALL:
                default:
                    break;
            }
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            //帖子图
            final Rect src = new Rect(0, 0, width, height);
            final Rect dst = src;
            canvas.drawBitmap(source, src, dst, paint);
            canvas.restore();
            if (source != totransform) {
                source.recycle();
            }
            return result;
        } catch (Exception exp) {
            return totransform;
        }

    }
}
