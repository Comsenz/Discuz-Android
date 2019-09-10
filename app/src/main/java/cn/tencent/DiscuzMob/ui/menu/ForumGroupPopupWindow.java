package cn.tencent.DiscuzMob.ui.menu;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/5/31.
 */
public class ForumGroupPopupWindow extends PopupWindow implements View.OnClickListener {

    private OnMenuItemClickListener onItemClickListener;
    private SparseIntArray ids;

    public ForumGroupPopupWindow(Context context, OnMenuItemClickListener onItemClickListener) {
        super(LayoutInflater.from(context).inflate(R.layout.popup_forum_detail_window, null)
                , RednetUtils.dp(context, 72), RednetUtils.dp(context, 72));
        this.onItemClickListener = onItemClickListener;
        setOutsideTouchable(true);
        setBackgroundDrawable(context.getResources().getDrawable(android.R.drawable.screen_background_light_transparent));
        setFocusable(true);
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
            ids = new SparseIntArray(2);
            ids.append(0, R.id.popupwindow_item01_name);
            ids.append(1, R.id.popupwindow_item02_name);
            contentView.findViewById(R.id.popupwindow_item01_name).setOnClickListener(this);
            contentView.findViewById(R.id.popupwindow_item02_name).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(null, v, ids.indexOfKey(v.getId())
                    , v.getId() == R.id.popupwindow_item01_name ? "top" : "common");
        }
    }

}
