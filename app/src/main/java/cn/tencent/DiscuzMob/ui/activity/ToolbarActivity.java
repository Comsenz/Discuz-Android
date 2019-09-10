package cn.tencent.DiscuzMob.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.utils.RednetUtils;

/**
 * Created by AiWei on 2016/4/26.
 */
public class ToolbarActivity extends BaseActivity {

    Toolbar mToolbar;

    public Toolbar getSupportToolbar() {
        return mToolbar;
    }

    public static class Toolbar implements View.OnClickListener {
        public static final int SHOW_LOGO = 1 << 1;
        public static final int SHOW_TITLE = 1 << 2;
        public static final int SHOW_SPINNER = 1 << 3;
        public static final int SHOW_CUSTOM = 1 << 4;
        public static final int SHOW_SET = 1 << 5;
        public static final int DEFAULT = SHOW_LOGO | SHOW_TITLE | SHOW_CUSTOM;
        public static final int DEFAULTS = SHOW_LOGO | SHOW_TITLE | SHOW_SET;

        public ImageView message, settings, arrow, discovery;
        public TextView title;

        private int mCurrentFlag = -1;
        ToolbarActivity mActivity;

        Toolbar(ToolbarActivity activity) {
            mActivity = activity;
            title = (TextView) activity.findViewById(R.id.title);
            arrow = (ImageView) activity.findViewById(R.id.arrow);
            message = (ImageView) activity.findViewById(R.id.message);
            settings = (ImageView) activity.findViewById(R.id.settings);
            discovery = (ImageView) activity.findViewById(R.id.discovery);
            message.setVisibility(View.GONE);
        }

        public void show(int flag) {

            if (flag != mCurrentFlag) {
//                message.setVisibility((flag & SHOW_LOGO) != 0 ? View.VISIBLE : View.GONE);
                discovery.setVisibility((flag & SHOW_CUSTOM) != 0 ? View.VISIBLE : View.GONE);
//                arrow.setVisibility((flag & SHOW_SPINNER) != 0 ? View.VISIBLE : View.GONE);
                title.setVisibility((flag & SHOW_TITLE) != 0 ? View.VISIBLE : View.GONE);
                settings.setVisibility((flag & SHOW_SET) != 0 ? View.VISIBLE : View.GONE);

                message.setOnClickListener(this);
                settings.setOnClickListener(this);
                discovery.setOnClickListener(this);
                mCurrentFlag = flag;
            }
            title.setText("发现");
            title.setOnClickListener(null);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.message:
                    if (RednetUtils.hasLogin(mActivity)) {
//                        startActivity(new Intent(getActivity(), MessageMineActivity.class));
                        mActivity.startActivity(new Intent(mActivity, MessageMineActivity.class));
                    }
                    break;
                case R.id.discovery:
//                    mActivity.startActivity(new Intent(mActivity, GeneralSettingsActivity.class));
                    mActivity.startActivity(new Intent(mActivity, SearchActivity.class));
                    break;
                case R.id.settings:
                    mActivity.startActivity(new Intent(mActivity, GeneralSettingsActivity.class));
                    break;
            }
        }

    }

}
