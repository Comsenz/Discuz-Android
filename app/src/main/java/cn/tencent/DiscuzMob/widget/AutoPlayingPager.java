package cn.tencent.DiscuzMob.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by AiWei on 2016/5/21.
 */
public class AutoPlayingPager extends ViewPager {

    private long mFlipInterval = 7000;

    private boolean mRunning = false;
    private boolean mStarted = false;
    private boolean mUserPresent = true;

    private PlayerScroller mPlayerScroller;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                mUserPresent = false;
                updateRunning();
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                mUserPresent = true;
                updateRunning();
            }
        }
    };

    public AutoPlayingPager(Context context) {
        super(context);
        init();
    }

    public AutoPlayingPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Field sInterpolator = ViewPager.class.getDeclaredField("sInterpolator");
            sInterpolator.setAccessible(true);
            mPlayerScroller = new PlayerScroller(getContext(), (Interpolator) sInterpolator.get(this));
            mScroller.set(this, mPlayerScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        final IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        getContext().registerReceiver(mReceiver, filter);
    }

    void startFlipping() {
        mStarted = true;
        updateRunning();
    }

    void stopFlipping() {
        mStarted = false;
        updateRunning();
    }

    private void updateRunning() {
        boolean running = getVisibility() == View.VISIBLE && mStarted && mUserPresent;
        if (running != mRunning) {
            if (running) {
                mHandler.sendMessageDelayed(mHandler.obtainMessage(FLIP_MSG), mFlipInterval);
            } else {
                mHandler.removeMessages(FLIP_MSG);
            }
            mRunning = running;
        }
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null && adapter.getCount() > 1) {
            startFlipping();
        } else {
            stopFlipping();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        stopFlipping();
        getContext().unregisterReceiver(mReceiver);
        super.onDetachedFromWindow();
    }

    private final class PlayerScroller extends Scroller {

        private int mDuration = 1000;

        PlayerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

    }

    private final int FLIP_MSG = 1;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == FLIP_MSG) {
                if (mRunning) {
                    int currentItem = getCurrentItem();
                    ++currentItem;
                    setCurrentItem(currentItem == getAdapter().getCount() ? 0 : currentItem, true);
                    msg = obtainMessage(FLIP_MSG);
                    sendMessageDelayed(msg, mFlipInterval);
                }
            }
        }
    };

}
