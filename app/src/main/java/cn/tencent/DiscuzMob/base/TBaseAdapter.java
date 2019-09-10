package cn.tencent.DiscuzMob.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class TBaseAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mList;
    public LayoutInflater mLayoutInflater;

    public TBaseAdapter(Context context, List<T> list) {
        super();
        this.mContext = context;
        this.mList = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public TBaseAdapter() {
        super();
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (mList != null) {
            ret = mList.size();
        }
        return ret;
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
