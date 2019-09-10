package cn.tencent.DiscuzMob.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import cn.tencent.DiscuzMob.R;


/**
 * Created by AiWei on 2015/4/22.
 */
public class ProgressDialog extends Dialog {

    private TextView label;

    public ProgressDialog(Context context) {
        this(context, R.style.Theme_Rednet_Dialog);
    }

    /**
     * @param context
     * @param theme
     */
    public ProgressDialog(Context context, int theme) {
        super(context, theme);
        setCanceledOnTouchOutside(false);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(context.getResources().getColor(android.R.color.transparent));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tip);
        label = (TextView) findViewById(R.id.label);
    }

    public void show(String label) {
        super.show();
        setLabel(label);
    }

    /**
     * called must be after {@link #show}
     *
     * @param text
     */
    public void setLabel(String text) {
        if (label != null && !TextUtils.isEmpty(text)) {
            label.setText(text);
        } else {
            label.setText("");
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (getWindow() != null && isShowing()) {
            dismiss();
        }
    }

}
