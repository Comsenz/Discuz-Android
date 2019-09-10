package cn.tencent.DiscuzMob.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.ui.activity.ActivityThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.ThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.ToolbarActivity;
import cn.tencent.DiscuzMob.ui.activity.VoteThreadDetailsActivity;

/**
 * Created by AiWei on 2016/4/26.
 */
public class BaseFragment extends Fragment {

    public ToolbarActivity.Toolbar getSupportToolbar() {
        if (getActivity() instanceof ToolbarActivity)
            return ((ToolbarActivity) getActivity()).getSupportToolbar();
        else
            return null;
    }

    public boolean hasLogin() {
        return RedNetApp.getInstance().isLogin();
    }

    public static void toThreadDetails(Context context, String fid, String tid, String special) {
        Class claz;
        special = TextUtils.isEmpty(special) ? "0" : special;
        if ("1".equals(special)) {
            claz = VoteThreadDetailsActivity.class;
        } else if ("4".equals(special)) {
            claz = ActivityThreadDetailsActivity.class;
        } else {
            claz = ThreadDetailsActivity.class;
        }
        context.startActivity(new Intent(context, claz).putExtra("fid", fid).putExtra("id", tid));
    }
    protected void runOnUiThread(Runnable runnable){
        if(getActivity()!=null){
            getActivity().runOnUiThread(runnable);
        }
    }

}
