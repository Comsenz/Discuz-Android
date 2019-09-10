package cn.tencent.DiscuzMob.ui.dialog;

/**
 * Created by cg on 2017/8/11.
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import cn.tencent.DiscuzMob.R;


/**
 * Created by Wind on 2016/coolmonkey010/18.
 * 通用的圆形进度条
 */
public class CustomProgress extends Dialog {

    private static CustomProgress dialog;

    public CustomProgress(Context context) {
        super(context);
    }

    public CustomProgress(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
        // 获取ImageView上的动画背景
        AnimationDrawable spinner = (AnimationDrawable) imageView
                .getBackground();
        // 开始动画
        spinner.start();
    }

    /**
     * 给Dialog设置提示信息
     *
     * @param message
     */
    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

    /**
     * 弹出自定义ProgressDialog
     *
     * @param context    上下文
     * @param message    提示
     * @param cancelable 是否按返回键取消
     * @return
     */
    public static CustomProgress show(Context context, CharSequence message,
                                      boolean cancelable) {
        try {

            // if(dialog==null){
            dialog = new CustomProgress(context, R.style.Custom_Progress);
            // }
            dialog.setTitle("");
            dialog.setContentView(R.layout.progress_custom);
            if (message == null || message.length() == 0) {
                dialog.findViewById(R.id.message).setVisibility(View.GONE);
            } else {
                TextView txt = (TextView) dialog.findViewById(R.id.message);
                txt.setText(message);
            }
            // 按返回键是否取消
            dialog.setCancelable(cancelable);
            // 设置居中
            dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            // 设置背景层透明度
            lp.dimAmount = 0.2f;
            dialog.getWindow().setAttributes(lp);
            // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dialog;
    }

    public static void cancle() {
        try {
            if (dialog != null) {
                dialog.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
