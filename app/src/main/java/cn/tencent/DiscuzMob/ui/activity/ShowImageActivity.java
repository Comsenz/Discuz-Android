package cn.tencent.DiscuzMob.ui.activity;

import android.os.Bundle;
import android.view.View;

import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.widget.AsyncImageView;
import cn.tencent.DiscuzMob.R;

/**
 * Created by kurt on 15/9/2.
 */
public class ShowImageActivity  extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_show);
        String url = getIntent().getStringExtra("url");
        AsyncImageView preview = (AsyncImageView) findViewById(R.id.show_img_view);
        preview.load(url);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImageActivity.this.finish();
            }
        });
    }

}
