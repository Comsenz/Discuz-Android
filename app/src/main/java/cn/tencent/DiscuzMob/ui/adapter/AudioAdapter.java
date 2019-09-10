package cn.tencent.DiscuzMob.ui.adapter;

import android.content.Context;

import java.util.List;

import cn.tencent.DiscuzMob.model.FileBean;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/6/26.
 */

public class AudioAdapter extends CommonAdapter<FileBean> {
    public AudioAdapter(Context mContext, List<FileBean> mDatas, int itemResId) {
        super(mContext, mDatas, itemResId);
    }

    @Override
    public void convert(ViewHolder holder, FileBean fileBean) {
        holder.setTextView(R.id.item_tv, "录音文件：" + fileBean.getFile().getAbsolutePath() + "\n录音时长：" + fileBean.getFileLength() + "s");
    }
}
