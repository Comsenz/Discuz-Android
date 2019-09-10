package cn.tencent.DiscuzMob.utils.glide_agent;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.facebook.stetho.common.LogUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class GlideHelper {

    public static HwLruResourceCache mMemoryCache;

    public static void resetGlide(GlideBuilder builder) {
//        Context context = RedMallApplication.getContext();
//        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
//        mMemoryCache = new HwLruResourceCache(calculator.getMemoryCacheSize());
//        builder.setMemoryCache(mMemoryCache);
//        BitmapPool bitmapPool;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            int size = (int) (calculator.getBitmapPoolSize() * MemoryCategory.LOW.getMultiplier());
//            bitmapPool = new LruBitmapPool(size);
//        } else {
//            bitmapPool = new BitmapPoolAdapter();
//        }
//        builder.setBitmapPool(bitmapPool);
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

    }

    public static void resetGlide() {
//        Context context = RedMallApplication.getContext();
//        List<GlideModule> modules = new ManifestParser(context).parse();
//
//        GlideBuilder builder = new GlideBuilder(context);
//
//        final int cores = Math.max(1, Runtime.getRuntime().availableProcessors());
//        ExecutorService sourceService = new FifoPriorityThreadPoolExecutor(cores);
//        builder.setResizeService(sourceService);
//
//        ExecutorService cacheService = new FifoPriorityThreadPoolExecutor(1);
//        builder.setDiskCacheService(cacheService);
//
//
//        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
//        BitmapPool bitmapPool;
//        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            int size = calculator.getBitmapPoolSize();
//            bitmapPool = new LruBitmapPool(size);
//        } else {
//            bitmapPool = new BitmapPoolAdapter();
//        }
//        builder.setBitmapPool(bitmapPool);
//
//        MemoryCache memoryCache;
//        memoryCache = new LruResourceCache(calculator.getMemoryCacheSize());
//        builder.setMemoryCache(memoryCache);
//
//        DiskCache.Factory diskCacheFactory = new InternalCacheDiskCacheFactory(context);
//        builder.setDiskCache(diskCacheFactory);
//
//        builder.setDecodeFormat(DecodeFormat.DEFAULT);
//
//        for (GlideModule module : modules) {
//            module.applyOptions(context, builder);
//        }
//
//        Glide.setup(builder);
//        Glide glide = Glide.get(context);
//        for (GlideModule module : modules) {
//            module.registerComponents(context, glide);
//        }
//        Glide.get(context).setMemoryCategory(MemoryCategory.LOW);
//        Glide.get(context).trimMemory(ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN);
    }


    public static void clear(Target target) {
        logMemory();
        if (target != null) {
            try {
                Glide.clear(target);
            } catch (Exception e) {
            }
        }
        // Glide.get(RedMallApplication.getContext()).getBitmapPool().clearMemory();
    }

    private static void logMemory() {
        if (mMemoryCache == null) return;
    }

    private static class HwLruResourceCache extends LruResourceCache {
        /**
         * Constructor for LruResourceCache.
         *
         * @param size The maximum size in bytes the in memory cache can use.
         */
        public HwLruResourceCache(int size) {
            super(size);
        }

        @Override
        public Resource<?> put(Key key, Resource<?> item) {
            logMemory();
            return super.put(key, item);
        }

        @Override
        public void trimToSize(int size) {
            logMemory();
            super.trimToSize(size);
        }
    }


    public final static class TargetManager {
        private final Vector<Target> mImageTags = new Vector<>();

        public boolean addTarget(Target tag) {
            boolean flag = mImageTags.add(tag);
            return flag;
        }

        public void removeTarget(Target tag, boolean sameOrEquals) {
            if (sameOrEquals) {
                for (int i = 0, size = mImageTags.size(); i < size; i++) {
                    if (mImageTags.get(i) == tag) {
                        mImageTags.remove(i);
                        break;
                    }
                }
            } else {
                boolean flag = true;
                do {
                    flag = mImageTags.remove(tag);
                } while (flag);
            }
        }

        public boolean containsTarget(Target tag, boolean sameOrEquals) {
            if (sameOrEquals) {
                Iterator<Target> it = mImageTags.iterator();
                while (it.hasNext()) {
                    Target target = it.next();
                    if (target == tag) {
                        return true;
                    }
                }
                return false;
            } else {
                return mImageTags.contains(tag);
            }
        }

        public void clearAll() {
            while (mImageTags.size() > 0) {
                Target target = mImageTags.remove(0);
                GlideHelper.clear(target);
            }
        }

        public void clearDettachedImages() {
            List<Target> imageTags = new ArrayList<>();
            while (mImageTags.size() > 0) {
                Target target = mImageTags.remove(0);
                if (target instanceof ViewTarget) {
                    ViewTarget viewTarget = (ViewTarget) target;
                    if (viewTarget.getView().getTag() != target) {
                        //target 相关的view 里的tag被更新，清除掉当前target
                        GlideHelper.clear(target);
                        continue;
                    }
                }
                imageTags.add(target);
            }
            mImageTags.addAll(imageTags);
        }
    }

    /**
     * 图片回收
     */
    public interface ImageClearCallback {
        boolean addTarget(Target tag);

        void removeTarget(Target tag, boolean theOneOrEquals);

        boolean containsTarget(Target tag, boolean theOneOrEquals);
    }
}
