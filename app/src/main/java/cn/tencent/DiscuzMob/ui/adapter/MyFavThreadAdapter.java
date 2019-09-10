package cn.tencent.DiscuzMob.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseRecyclerAdapter;
import cn.tencent.DiscuzMob.listeners.FavEventListener;
import cn.tencent.DiscuzMob.model.MyFavThreadBean;
import cn.tencent.DiscuzMob.ui.activity.ActivityThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.ThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.VoteThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.viewholder.LoadMoreHolder;

/**
 * Created by cg on 2017/4/18.
 */

public class MyFavThreadAdapter extends BaseRecyclerAdapter<MyFavThreadBean.VariablesBean.ListBean> {
    public static final int VIEW_TYPE_LOADMORE = 0;
    public static final int VIEW_TYPE_ITEM = 1;
    private FavEventListener<MyFavThreadBean.VariablesBean.ListBean> mFavEventListener;
    private List<MyFavThreadBean.VariablesBean.ListBean> mListBeans;

    public MyFavThreadAdapter(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                return new ItemHolder(getActivity());
            case VIEW_TYPE_LOADMORE:
                mLoadMoreHolder = new LoadMoreHolder(getActivity()).setListener(mLoadListener);
                return mLoadMoreHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Mode mode = getItem(position);
        switch (getItemViewType(position)) {
            case VIEW_TYPE_ITEM:
                ItemHolder itemHolder = (ItemHolder) holder;
                itemHolder.bind((MyFavThreadBean.VariablesBean.ListBean) mode.data, mode.hideDivider, mFavEventListener);
                break;
            case VIEW_TYPE_LOADMORE:
                bindLoadMore();
                break;
        }
    }

    @Override
    public boolean isShowEmpty() {
        return mListBeans == null || mListBeans.isEmpty();
    }


    public void setFavEventListener(FavEventListener<MyFavThreadBean.VariablesBean.ListBean> mFavEventListener) {
        this.mFavEventListener = mFavEventListener;
    }

    public void setData(List<MyFavThreadBean.VariablesBean.ListBean> datas, boolean isRefresh, boolean hasMore) {
        setHasMore(hasMore);
        finish();
        updateListBeans(datas, isRefresh);
        update();
    }

    public void remove(MyFavThreadBean.VariablesBean.ListBean bean) {
        mListBeans.remove(bean);
        update();
    }

    private void update() {
        mDataMode.clear();
        if (mListBeans != null) {
            for (int i = 0; i < mListBeans.size(); i++) {
                mDataMode.add(new Mode(VIEW_TYPE_ITEM, mListBeans.get(i)).setHideDivider(i == mListBeans.size() - 1));
            }
        }
        if (/*hasMore() &&*/ mListBeans != null && mListBeans.size() > 0) {
            mDataMode.add(new Mode(VIEW_TYPE_LOADMORE, null));
        }
        notifyDataSetChanged();
    }

    private void updateListBeans(List<MyFavThreadBean.VariablesBean.ListBean> datas, boolean isRefresh) {
        if (mListBeans == null) {
            mListBeans = new ArrayList();
        }
        if (isRefresh) {
            mListBeans.clear();
        }
        if (datas == null || datas.size() == 0) {
            return;
        }
        List<MyFavThreadBean.VariablesBean.ListBean> temp = new ArrayList<>();
        for (MyFavThreadBean.VariablesBean.ListBean bean : datas) {
            boolean needAdd = true;
            for (int i = 0, size = mListBeans.size(); i < size; i++) {
                if (mListBeans.get(i).getId().equals(bean.getId())) {
                    needAdd = false;
                    break;
                }
            }
            if (needAdd) {
                temp.add(bean);
            }
        }
        mListBeans.addAll(temp);
    }

    private static class ItemHolder extends BaseViewHolder {
        private TextView tv_subject;
        private TextView tv_authorName;
        private TextView tv_time;
        private TextView tv_comment;
        private TextView tv_look;
        private LinearLayout ll_item;
        private View divider;

        public ItemHolder(Activity activity) {
            super(activity, R.layout.myfavthread_item);
            tv_subject = (TextView) itemView.findViewById(R.id.tv_subject);
            tv_authorName = (TextView) itemView.findViewById(R.id.tv_authorName);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            tv_look = (TextView) itemView.findViewById(R.id.tv_look);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
            divider = itemView.findViewById(R.id.divider);
        }

        public void bind(final MyFavThreadBean.VariablesBean.ListBean bean, boolean hideDivider, final FavEventListener<MyFavThreadBean.VariablesBean.ListBean> listener) {
            divider.setVisibility(!hideDivider ? View.VISIBLE : View.GONE);
            tv_subject.setText(bean.getTitle());
            if (null == bean.getAuthor()) {
                tv_authorName.setVisibility(View.GONE);
            } else {
                tv_authorName.setVisibility(View.VISIBLE);
                tv_authorName.setText(bean.getAuthor() + "   ");
            }

            SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            @SuppressWarnings("unused")
            long lcc = Long.valueOf(bean.getDateline());
            int i = Integer.parseInt(bean.getDateline());
            String times = sdr.format(new Date(i * 1000L));

            tv_time.setText(times);
            tv_comment.setVisibility(View.GONE);
            tv_comment.setText(bean.getReplies());
            tv_look.setVisibility(View.GONE);
            ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Class claz;
                    String special = bean.getSpecial();
                    special = TextUtils.isEmpty(special) ? "0" : special;
                    if ("1".equals(special)) {
                        claz = VoteThreadDetailsActivity.class;
                    } else if ("4".equals(special)) {
                        claz = ActivityThreadDetailsActivity.class;
                    } else {
                        claz = ThreadDetailsActivity.class;
                    }
                    getActivity().startActivity(new Intent(getActivity(), claz).putExtra("id", bean.getId()));
                }
            });
        }
    }
}
