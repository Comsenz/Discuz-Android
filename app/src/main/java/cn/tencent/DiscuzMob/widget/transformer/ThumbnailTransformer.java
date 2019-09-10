package cn.tencent.DiscuzMob.widget.transformer;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;

import com.squareup.picasso.Transformation;

/**
 * Created by AiWei on 2016/7/8.
 */
public class ThumbnailTransformer implements Transformation {

    static final String key = "ThumbnailTransformer_96_96";

    @Override
    public String key() {
        return key;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        return ThumbnailUtils.extractThumbnail(source, 96, 96, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
    }

}
