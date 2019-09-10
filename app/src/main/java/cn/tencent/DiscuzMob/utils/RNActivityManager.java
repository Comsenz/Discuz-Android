package cn.tencent.DiscuzMob.utils;

import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;

/**
 * Activity管理类 kurt create on 2014-6-27
 */
public class RNActivityManager {

	private static RNActivityManager manager = null;

	private List<Activity> activityList = new LinkedList<Activity>();

	public static RNActivityManager getInstance() {
		if (manager == null) {
			manager = new RNActivityManager();
		}
		return manager;
	}

	/**
	 * 将一个activity添加到管理器。
	 * 
	 * @param activity
	 */
	public synchronized boolean putActivity(String name, Activity activity) {
		return activityList.add(activity);
	}

	/**
	 * 返回管理器的Activity是否为空。
	 * 
	 * @return 当且当管理器中的Activity对象为空时返回true，否则返回false。
	 */
	public boolean isEmpty() {
		return activityList.isEmpty();
	}

	/**
	 * 返回管理器中Activity对象的个数。
	 * 
	 * @return 管理器中Activity对象的个数。
	 */
	public int size() {
		return activityList.size();
	}

	/**
	 * 关闭所有活动的Activity。
	 */
	@SuppressLint("NewApi")
	public void closeAllActivity() {
		while(activityList.size() > 0) {
			activityList.remove(0).finish();;
		}
    }
	
	/**
	 * 从堆栈顶移出多个Activity
	 * 
	 * @param number
	 *            要移除的层数
	 */
	public synchronized void finishActivity(int number) {
		while(number > 0 && activityList.size() > 0) {
			number--;
			activityList.remove(activityList.size() - 1).finish();
		}
	}

	/**
	 * 获得当前活动的顶层Activity
	 */
	public Activity getTopActivity() {
		if (activityList.isEmpty()) {
			return null;
		}
		while(activityList.size() > 0) {
			return activityList.get(activityList.size() - 1);
		}
		return null;
	}

	public synchronized void removeActivity(Activity activity) {
		activityList.remove(activity);
	}

}
