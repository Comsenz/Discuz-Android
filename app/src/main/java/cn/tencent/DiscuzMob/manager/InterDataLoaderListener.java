package cn.tencent.DiscuzMob.manager;

/**
 * Created by kurt on 15-6-4.
 */
public interface InterDataLoaderListener<E> {
    void onLoadFinished(E object);

    void onError(E object);
}
