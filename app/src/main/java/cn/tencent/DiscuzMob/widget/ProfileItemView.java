package cn.tencent.DiscuzMob.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.tencent.DiscuzMob.R;


/**
 * Created by kurt on 15-6-17.
 */
public class ProfileItemView extends FrameLayout {

    private TextView mKeyView, mValueView;
    private ImageView user_group, management_Group, integral, prestige, money, contribution;
    private LinearLayout ll_view;
    private View view_line;

    public ProfileItemView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.profile_info_item, this, true);
        mKeyView = (TextView) findViewById(R.id.item_key);
        mValueView = (TextView) findViewById(R.id.item_value);
        user_group = (ImageView) findViewById(R.id.user_group);
        management_Group = (ImageView) findViewById(R.id.management_Group);
        integral = (ImageView) findViewById(R.id.integral);
        prestige = (ImageView) findViewById(R.id.prestige);
        money = (ImageView) findViewById(R.id.money);
        contribution = (ImageView) findViewById(R.id.contribution);
        ll_view = (LinearLayout) findViewById(R.id.ll_view);
        view_line = findViewById(R.id.view_line);
    }

    public ProfileItemView(Context context, String title, String value) {
        this(context);

        if (title.equals("用户组")) {
            user_group.setVisibility(VISIBLE);
        } else if (title.equals("管理组")) {
            management_Group.setVisibility(VISIBLE);
        }
//        else if (title.equals("积分")) {
//            title = "贡献";
//            contribution.setVisibility(VISIBLE);
//        }
        else if (title.equals("威望")) {
            prestige.setVisibility(VISIBLE);
        } else if (title.equals("金钱")) {
            money.setVisibility(VISIBLE);
        } else if (title.equals("贡献")) {
            contribution.setVisibility(VISIBLE);
        } else if (title.equals("等级")) {
            ll_view.setVisibility(GONE);
            view_line.setVisibility(GONE);
        } else if (title.equals("魅力")) {
            title = "金币";
            money.setVisibility(VISIBLE);
        } else if (title.equals("经验")) {
            ll_view.setVisibility(GONE);
        } else if (title.equals("积分")) {
            title = "积分";
            integral.setVisibility(VISIBLE);
        } else if (title.equals("牛币") || title.equals("红网币")) {
            ll_view.setVisibility(GONE);
            view_line.setVisibility(GONE);
        }
        mKeyView.setText(title);
        mValueView.setText(value);
    }

}
