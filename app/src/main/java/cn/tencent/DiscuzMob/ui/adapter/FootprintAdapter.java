package cn.tencent.DiscuzMob.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseRecyclerAdapter;
import cn.tencent.DiscuzMob.model.AllForumBean;
import cn.tencent.DiscuzMob.ui.activity.ForumListActivity;
import cn.tencent.DiscuzMob.ui.viewholder.LoadMoreHolder;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;

/**
 * Created by cg on 2017/6/16.
 */
public class FootprintAdapter extends BaseRecyclerAdapter<AllForumBean.VariablesBean.ForumlistBean> {
    public static final int VIEW_TYPE_LOADMORE = 0;
    public static final int VIEW_TYPE_SUB_LINE = 1;
    private List<AllForumBean.VariablesBean.ForumlistBean> mListBeans;
    private int mColumnCount = 3;

    public FootprintAdapter(Activity mActivity, int mColumnCount) {
        super(mActivity);
        this.mColumnCount = mColumnCount;
    }

    @Override
    public boolean isShowEmpty() {
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_SUB_LINE:
                return new ItemHolder(getActivity(), mColumnCount);
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
            case VIEW_TYPE_SUB_LINE:
                ItemHolder itemHolder = (ItemHolder) holder;
                itemHolder.bind((List<AllForumBean.VariablesBean.ForumlistBean>) mode.data, mColumnCount);
                break;
            case VIEW_TYPE_LOADMORE:
                bindLoadMore();
                break;
        }
    }

    public void setData(List<AllForumBean.VariablesBean.ForumlistBean> datas, boolean isRefresh, boolean hasMore) {
        setHasMore(hasMore);
        finish();
        updateListBeans(datas, isRefresh);
        update();
    }

    private void updateListBeans(List<AllForumBean.VariablesBean.ForumlistBean> datas, boolean isRefresh) {
        if (mListBeans == null) {
            mListBeans = new ArrayList();
        }
        if (isRefresh) {
            mListBeans.clear();
        }
        if (datas == null || datas.size() == 0) {
            return;
        }
        List<AllForumBean.VariablesBean.ForumlistBean> temp = new ArrayList<>();
        for (AllForumBean.VariablesBean.ForumlistBean bean : datas) {
            boolean needAdd = true;
            for (int i = 0, size = mListBeans.size(); i < size; i++) {
                if (mListBeans.get(i).getFid().equals(bean.getFid())) {
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

    private void update() {
        mDataMode.clear();
        if (mListBeans != null) {
            for (int i = 0, size = mListBeans.size(); i < size; i += mColumnCount) {
                List<AllForumBean.VariablesBean.ForumlistBean> subBeans = mListBeans.subList(i, Math.min(i + mColumnCount, size));
                mDataMode.add(new Mode(VIEW_TYPE_SUB_LINE, subBeans));
            }
        }
        if (hasMore() && mListBeans != null && mListBeans.size() > 0) {
            mDataMode.add(new Mode(VIEW_TYPE_LOADMORE, null));
        }
        notifyDataSetChanged();
    }

    private static class SubItemViewHolder {
        private Activity activity;
        private View itemView;
        private TextView forumName;
        private TextView tv_themecount;
        private TextView tv_todaycount;
        private ImageView iv_picture;

        public SubItemViewHolder(Activity mActivity, ViewGroup parent) {
            activity = mActivity;
            itemView = LayoutInflater.from(mActivity.getApplicationContext()).inflate(R.layout.item_sub_column, parent, false);
            forumName = (TextView) itemView.findViewById(R.id.tv_forumName);
            tv_themecount = (TextView) itemView.findViewById(R.id.tv_themecount);
            tv_todaycount = (TextView) itemView.findViewById(R.id.tv_todaycount);
            iv_picture = (ImageView) itemView.findViewById(R.id.iv_picture);
            itemView.setTag(this);
        }

        public void bind(final AllForumBean.VariablesBean.ForumlistBean bean) {
            iv_picture.setPadding(8, 0, 8, 0);//设置间距
            forumName.setText(bean.getName());
            final String threads = bean.getThreads();
            int threadsInt = 0;
            if (!TextUtils.isEmpty(threads)) {
                threadsInt = Integer.parseInt(threads);
            }
            if (threadsInt > 9999 && threadsInt < 100000000) {
                int i1 = threadsInt / 10000;
                tv_themecount.setText("主题:" + i1 + "万");
            } else if (threadsInt > 99999999) {
                int i1 = threadsInt / 100000000;
                tv_themecount.setText("主题:" + i1 + "亿");
            } else {
                tv_themecount.setText("主题:" + threads);
            }
//        tv_forumcount.setText(data.get(position).getPosts());
            String icon = bean.getIcon();
            final String todayposts = bean.getTodayposts();
            int imageSetting = RedNetPreferences.getImageSetting();
            if (null != icon && !TextUtils.isEmpty(icon) && imageSetting != 1) {
                Picasso.with(activity.getApplicationContext())
                        .load(icon)
                        .into(iv_picture);
            } else {
                if (null != todayposts && !todayposts.equals("0")) {
                    iv_picture.setImageResource(R.drawable.newforum);
                } else {
                    iv_picture.setImageResource(R.drawable.oldforum);
                }
            }
            if (null != todayposts && Integer.parseInt(todayposts) > 0) {
                tv_todaycount.setVisibility(View.VISIBLE);
                if (Integer.parseInt(todayposts) > 9999 && Integer.parseInt(todayposts) < 100000000) {
                    int i1 = Integer.parseInt(todayposts) / 10000;
                    tv_todaycount.setText(i1 + "万");
                } else if (Integer.parseInt(todayposts) > 99999999) {
                    int i1 = Integer.parseInt(todayposts) / 100000000;
                    tv_todaycount.setText(i1 + "亿");
                } else {
                    tv_todaycount.setText("(" + todayposts + ")");
                }
            } else {
                tv_todaycount.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ForumListActivity.class);
                    intent.putExtra("threads", threads);
                    intent.putExtra("todayposts", todayposts);
                    intent.putExtra("fid", bean.getFid());
                    intent.putExtra("name", bean.getName());
                    activity.startActivity(intent);
                }
            });
        }
    }

    private static class ItemHolder extends BaseViewHolder {
        private final List<SubItemViewHolder> subItemViewHolders;
        private LinearLayout subContainer;

        public ItemHolder(Activity mActivity, int columnCount) {
            super(mActivity, R.layout.item_sub_lines);
            subContainer = (LinearLayout) itemView.findViewById(R.id.sub_container);
            subItemViewHolders = new ArrayList<>();
            for (int i = 0; i < columnCount; i++) {
                SubItemViewHolder subHolder = new SubItemViewHolder(getActivity(), subContainer);
                subItemViewHolders.add(subHolder);
                subContainer.addView(subHolder.itemView);
            }
        }

        public void bind(List<AllForumBean.VariablesBean.ForumlistBean> sublist, int columnCount) {
            if (sublist == null || sublist.isEmpty()) {
                subContainer.setVisibility(View.GONE);
            } else {
                subContainer.setVisibility(View.VISIBLE);
                for (int i = subItemViewHolders.size(); i < columnCount; i++) {
                    SubItemViewHolder subHolder = new SubItemViewHolder(getActivity(), subContainer);
                    subItemViewHolders.add(subHolder);
                    subContainer.addView(subHolder.itemView);
                }
                for (int i = 0, subsize = sublist.size(), size = subItemViewHolders.size(); i < size; i++) {
                    SubItemViewHolder holder = subItemViewHolders.get(i);
                    if (i < subsize) {
                        holder.itemView.setVisibility(View.VISIBLE);
                        holder.bind(sublist.get(i));
                    } else {
                        holder.itemView.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }

    }

}
