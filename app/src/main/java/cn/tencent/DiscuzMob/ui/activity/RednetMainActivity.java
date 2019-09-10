package cn.tencent.DiscuzMob.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.View;
import android.widget.RadioGroup;

import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.ui.Event.RefreshUserInfo;
import cn.tencent.DiscuzMob.ui.fragment.BaseFragment;
import cn.tencent.DiscuzMob.ui.fragment.CommunityFragment;
import cn.tencent.DiscuzMob.ui.fragment.ForumFragment;
import cn.tencent.DiscuzMob.ui.fragment.FristFragment;
import cn.tencent.DiscuzMob.ui.fragment.MeFragment;
import cn.tencent.DiscuzMob.ui.fragment.ReadingFragment;
import cn.tencent.DiscuzMob.utils.RednetUtils;

import static cn.tencent.DiscuzMob.utils.RednetUtils.REQUEST_CODE_LOGIN;

/**
 * Created by AiWei on 2016/4/18.
 */
public class RednetMainActivity extends ToolbarActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private RadioGroup mRadioGroup;
    private View mMe;
    private FragmentManager mFragmentManager;
    private BaseFragment mCurrentFragment;
    private int mCurrentId = -1;
    private SparseArray<BaseFragment> mStack = new SparseArray<>(4);
    private SparseArray<String> mKeys = new SparseArray<>(4);
    private String XGtid;
    private String XGtype;

    {
//        mKeys.put(R.id.index, IndexFragment.class.getName());
//        mKeys.put(R.id.index, HomePageFragment.class.getName());
        mKeys.put(R.id.index, FristFragment.class.getName());
        mKeys.put(R.id.community, CommunityFragment.class.getName());
        mKeys.put(R.id.forum, ForumFragment.class.getName());
        mKeys.put(R.id.guide, ReadingFragment.class.getName());
        mKeys.put(R.id.me, MeFragment.class.getName());
    }

    private boolean isReadyExit;

//    @Override
//    protected void onStart() {
//        super.onStart();
//        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
//        if (click != null) {
//            String customContent = click.getCustomContent();
//            if (customContent != null && customContent.length() != 0) {
//                    try {
//                        JSONObject json = new JSONObject(customContent);
//                        //从信鸽推送点进来的会有tid   type可有可无(type用来判断是那种类型的帖子,为空时默认为普通帖子)
//                        XGtid = json.optString("tid");
//                        XGtype = json.optString("type");
//                        Toast.makeText(RednetMainActivity.this,"tid1:   "+XGtid,Toast.LENGTH_LONG).show();
//                    }catch (Exception e){
//
//                    }
//                }
//            }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String uid = getIntent().getStringExtra("uid");
        if (uid != null) {
            removePageFlag();
            if (RedNetApp.getInstance().isLogin()) {
                mMe.performClick();
            }
            mRadioGroup.clearCheck();
            mCurrentId = -1;
            show(R.id.me);
            mMe.setSelected(true);
        }
        mToolbar = new Toolbar(this);
        mRadioGroup = (RadioGroup) findViewById(R.id.rg);
        mMe = findViewById(R.id.me);
        mRadioGroup.setOnCheckedChangeListener(this);
        mMe.setOnClickListener(this);
        mFragmentManager = getSupportFragmentManager();
        mRadioGroup.check(R.id.index);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
//        if(click!=null){
//            try {
//                JSONObject params = new JSONObject(click.getCustomContent());
//                XGtid = params.optString("tid");
//                XGtype = params.optString("type");
//                if(XGtid!=null && !XGtid.equals("")){
//                    if(XGtype !=null && !XGtype.equals("")){
//                        if(XGtype.equals("1")){//普通帖
//                            startActivity(new Intent(RednetMainActivity.this,ThreadDetailsActivity.class).putExtra("id", XGtid));
//                        }else if(XGtype.equals("2")){//投票贴
//                            startActivity(new Intent(RednetMainActivity.this,VoteThreadDetailsActivity.class).putExtra("id", XGtid));
//                        }else if(XGtype.equals("3")){//活动贴
//                            startActivity(new Intent(RednetMainActivity.this,ActivityThreadDetailsActivity.class).putExtra("id", XGtid));
//                        }
//                    }else {
//                        //type为空 跳至普通帖
//                        startActivity(new Intent(RednetMainActivity.this,ThreadDetailsActivity.class).putExtra("id", XGtid));
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }

    void show(int checkedId) {
        if (checkedId > 0 && mCurrentId != checkedId) {
            BaseFragment fragment = mStack.get(checkedId);
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            if (fragment == null) {
                fragment = (BaseFragment) BaseFragment.instantiate(this, mKeys.get(checkedId));
                mStack.put(checkedId, fragment);
                transaction.add(R.id.container, fragment).hide(fragment);
            }
            if (mCurrentFragment != null) {
                transaction.hide(mCurrentFragment);
            }
            transaction.show(fragment).commit();
            mCurrentFragment = fragment;
            mCurrentId = checkedId;
            mMe.setSelected(false);
            mRadioGroup.check(mCurrentId);
        }
        removePageFlag();
    }

    public void removeMePage() {
        mFragmentManager.beginTransaction().remove(mStack.get(R.id.me)).commit();
        mStack.remove(R.id.me);
    }

    public void addMePageFlag() {
        mStack.put(REQUEST_CODE_LOGIN, null);//添加个人中心展示标记
    }

    public void removePageFlag() {
        mStack.remove(REQUEST_CODE_LOGIN);//取消个人中心展示标记
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.me) {
            EventBus.getDefault().post(new RefreshUserInfo("0"));
            if (RednetUtils.hasLogin(this)) {
                mRadioGroup.clearCheck();
                mCurrentId = -1;
                show(R.id.me);
                mMe.setSelected(true);
            }

//            else {
//                startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_CODE_LOGIN);
//                mMe.setSelected(false);
//                show(mCurrentId);
//            }
            addMePageFlag();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOGIN && resultCode != RESULT_OK && mCurrentId == R.id.me) {
            show(R.id.index);
            mRadioGroup.check(R.id.index);
        } else {
            if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_OK
                    && mStack.indexOfKey(REQUEST_CODE_LOGIN) >= 0) {//显示个人中心页
                removePageFlag();
                if (RedNetApp.getInstance().isLogin()) {
                    mMe.performClick();
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        show(checkedId);
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (isReadyExit) {
            super.onBackPressed();
            RedNetApp.getInstance().exit();
        } else {
            RednetUtils.toast(this, "再按一次退出程序");
            isReadyExit = true;
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isReadyExit = false;
                }
            }, 3000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
