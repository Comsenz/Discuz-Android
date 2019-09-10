package cn.tencent.DiscuzMob.listeners;

/**
 * Created by Feng on 2017/12/27.
 */

public interface FavEventListener<T> {
    public void onFavChange(T t,boolean addOrDelete);
}
