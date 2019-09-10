package cn.tencent.DiscuzMob.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.ui.fragment.thread.ActivityThreadDetailsFragment;
import cn.tencent.DiscuzMob.ui.fragment.thread.SimpleWebFragment;
import cn.tencent.DiscuzMob.ui.fragment.thread.ThreadDetailsFragment;
import cn.tencent.DiscuzMob.ui.fragment.thread.VoteThreadDetailsFragment;

/**
 * Created by AiWei on 2016/7/6.
 */
public class ThreadActivity extends BaseActivity implements View.OnClickListener {

    private SimpleWebFragment mContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.share).setOnClickListener(this);
        findViewById(R.id.options).setOnClickListener(this);
        /*getSupportFragmentManager().beginTransaction()
                .add(R.id.container, mContent = create(getIntent().getIntExtra("Type", 0)))
                .commit();*/

        //load url
    }

    private Fragment create(int type) {
        switch (type) {
            case 1:
                return new VoteThreadDetailsFragment();
            case 2:
                return new ActivityThreadDetailsFragment();
            default:
                return new ThreadDetailsFragment();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            finish();
        } else {
            mContent.onOptionsItemClick(id);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mContent != null)
            mContent.onActivityResult(requestCode, resultCode, data);
    }

}
