package cn.tencent.DiscuzMob.manager;

/**
 * Created by kurt on 15-6-4.
 */
public interface DataLoaderListener<E> {
    void onLoadCacheFinished(E object);

    void onLoadDataFinished(E object);

    void onError(E object);
}
