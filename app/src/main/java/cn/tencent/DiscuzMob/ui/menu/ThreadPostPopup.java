package cn.tencent.DiscuzMob.ui.menu;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import java.util.List;

import cn.tencent.DiscuzMob.ui.activity.SendActivityThreadActivity;
import cn.tencent.DiscuzMob.ui.activity.SendThreadActivity;
import cn.tencent.DiscuzMob.ui.activity.SendVoteThreadActivity;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/5/31.
 */
public class ThreadPostPopup extends PopupWindow implements View.OnClickListener {

    private SparseIntArray ids;
    private List<String> allowpostspecial;
    private OnMenuItemClickListener onItemClickListener;

    public ThreadPostPopup(Context context, List<String> allowpostspecial, OnMenuItemClickListener onItemClickListener) {
        super(LayoutInflater.from(context).inflate(R.layout.popup_window, null), RednetUtils.dp(context, 96),
                RednetUtils.dp(context, 2*32));// (allowpostspecial != null && !allowpostspecial.isEmpty() ? allowpostspecial.size() + 1 : 1) *
        this.allowpostspecial = allowpostspecial;
        this.onItemClickListener = onItemClickListener;
        setOutsideTouchable(true);
        setBackgroundDrawable(context.getResources().getDrawable(android.R.drawable.screen_background_light_transparent));
        setFocusable(true);
        initView();
    }

    void initView() {
        if (allowpostspecial != null && !allowpostspecial.isEmpty() && getContentView() != null) {
            View vote = getContentView().findViewById(R.id.popupwindow_item02);
            View activity = getContentView().findViewById(R.id.popupwindow_item03);
            int flag = (1 << 1) | (2 << 1);
            for (String str : allowpostspecial) {
                if ((flag & 2) != 0) {
                    if ("1".equals(str)) {
                        flag ^= (1 << 1);
                        vote.setVisibility(View.VISIBLE);
                    } else {
                        vote.setVisibility(View.GONE);
                    }
                }
                if ((flag & 4) != 0) {
                    if ("4".equals(str)) {
                        flag ^= (2 << 1);
                        //activity.setVisibility(View.VISIBLE);
                    } else {
                        activity.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    @Override
    public void setContentView(View contentView) {
        super.setContentView(contentView);
        if (contentView != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                setElevation(RednetUtils.dp(contentView.getContext(), 8));
            } else {
                ViewCompat.setElevation(contentView, RednetUtils.dp(contentView.getContext(), 8));
            }
            ids = new SparseIntArray(3);
            ids.append(0, R.id.popupwindow_item01_name);
            ids.append(1, R.id.popupwindow_item01_name);
            ids.append(2, R.id.popupwindow_item03_name);
            contentView.findViewById(R.id.popupwindow_item01_name).setOnClickListener(this);
            contentView.findViewById(R.id.popupwindow_item02_name).setOnClickListener(this);
            contentView.findViewById(R.id.popupwindow_item03_name).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            switch (v.getId()) {
                case R.id.popupwindow_item01_name:
                    onItemClickListener.onItemClick(SendThreadActivity.class, v
                            , ids.indexOfKey(R.id.popupwindow_item01_name), "");
                    break;
                case R.id.popupwindow_item02_name:
                    onItemClickListener.onItemClick(SendVoteThreadActivity.class, v
                            , ids.indexOfKey(R.id.popupwindow_item02_name), "");
                    break;
                default:
                    onItemClickListener.onItemClick(SendActivityThreadActivity.class, v
                            , ids.indexOfKey(v.getId()), "");
                    return;
            }
        }
    }

}