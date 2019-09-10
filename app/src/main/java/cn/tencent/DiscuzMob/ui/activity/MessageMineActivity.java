package cn.tencent.DiscuzMob.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.ui.fragment.BaseFragment;
import cn.tencent.DiscuzMob.ui.fragment.message.LetterFragment;
import cn.tencent.DiscuzMob.ui.fragment.message.RemindFragment;

/**
 * Created by kurt on 15-6-24.
 */
public class MessageMineActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_remind, tv_letter;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private int mCurrentId;
    private SparseArray<Fragment> mStack = new SparseArray<>(2);
    private SparseArray<String> mKeys = new SparseArray<>(2);
    private RelativeLayout rl_remind, rl_letter;
    private View view_remind, view_letter;

    {
        mKeys.put(R.id.tv_letter, LetterFragment.class.getName());
        mKeys.put(R.id.tv_remind, RemindFragment.class.getName());
    }

    private TextView tv_sendmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        mFragmentManager = getSupportFragmentManager();
        rl_remind = (RelativeLayout) findViewById(R.id.rl_remind);
        rl_letter = (RelativeLayout) findViewById(R.id.rl_letter);
        tv_remind = (TextView) findViewById(R.id.tv_remind);
        tv_letter = (TextView) findViewById(R.id.tv_letter);
        view_remind = findViewById(R.id.view_remind);
        view_letter = findViewById(R.id.view_letter);
        tv_sendmessage = (TextView) findViewById(R.id.tv_sendmessage);
        tv_sendmessage.setOnClickListener(this);
        tv_letter.setSelected(true);
        tv_letter.setOnClickListener(this);
        tv_remind.setOnClickListener(this);
        show(R.id.tv_letter);
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_remind:
                tv_remind.setSelected(true);
                tv_letter.setSelected(false);
                view_remind.setVisibility(View.VISIBLE);
                view_letter.setVisibility(View.INVISIBLE);
                show(R.id.tv_remind);
                break;
            case R.id.tv_letter:
                tv_remind.setSelected(false);
                tv_letter.setSelected(true);
                view_remind.setVisibility(View.INVISIBLE);
                view_letter.setVisibility(View.VISIBLE);
                show(R.id.tv_letter);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.tv_sendmessage:
                startActivity(new Intent(MessageMineActivity.this, SendMessageActivity.class));
                break;
        }

    }

    void show(int id) {
        if (id > 0 && mCurrentId != id) {
            Fragment fragment = mStack.get(id);
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            if (fragment == null) {
                fragment = BaseFragment.instantiate(this, mKeys.get(id));
                if (fragment != null) {
                    mStack.put(id, fragment);
                    transaction.add(R.id.frame, fragment).hide(fragment);
                } else {
                    return;
                }
            }
            if (mCurrentFragment != null) {
                transaction.hide(mCurrentFragment);
            }
            transaction.show(fragment).commit();
            mCurrentFragment = fragment;
            mCurrentId = id;
        }
    }

}
