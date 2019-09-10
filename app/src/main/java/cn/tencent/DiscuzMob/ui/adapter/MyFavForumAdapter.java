package cn.tencent.DiscuzMob.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseRecyclerAdapter;
import cn.tencent.DiscuzMob.listeners.FavEventListener;
import cn.tencent.DiscuzMob.model.MyFavForumBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.activity.ForumListActivity;
import cn.tencent.DiscuzMob.ui.viewholder.LoadMoreHolder;
import cn.tencent.DiscuzMob.utils.ImageDisplay;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cg on 2017/4/18.
 */

public class MyFavForumAdapter extends BaseRecyclerAdapter<MyFavForumBean.VariablesBean.ListBean> {
    public static final int VIEW_TYPE_LOADMORE = 0;
    public static final int VIEW_TYPE_ITEM = 1;
    private FavEventListener<MyFavForumBean.VariablesBean.ListBean> mFavEventListener;
    private List<MyFavForumBean.VariablesBean.ListBean> mListBeans;

    public MyFavForumAdapter(Activity mActivity) {
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
                itemHolder.bind((MyFavForumBean.VariablesBean.ListBean) mode.data, mode.hideDivider, mFavEventListener);
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

    public void setFavEventListener(FavEventListener<MyFavForumBean.VariablesBean.ListBean> mFavEventListener) {
        this.mFavEventListener = mFavEventListener;
    }

    public void setData(List<MyFavForumBean.VariablesBean.ListBean> datas, boolean isRefresh, boolean hasMore) {
        setHasMore(hasMore);
        finish();
        updateListBeans(datas, isRefresh);
        update();
    }

    public void remove(MyFavForumBean.VariablesBean.ListBean bean) {
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

    private void updateListBeans(List<MyFavForumBean.VariablesBean.ListBean> datas, boolean isRefresh) {
        if (mListBeans == null) {
            mListBeans = new ArrayList();
        }
        if (isRefresh) {
            mListBeans.clear();
        }
        if (datas == null || datas.size() == 0) {
            return;
        }
        List<MyFavForumBean.VariablesBean.ListBean> temp = new ArrayList<>();
        for (MyFavForumBean.VariablesBean.ListBean bean : datas) {
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
        private TextView tv_forumName;
        private LinearLayout ll_fav;
        private ImageView iv_collection_selete;
        private CircleImageView iv_picture;
        private View divider;

        public ItemHolder(Activity activity) {
            super(activity, R.layout.myfavforum_item);
            tv_forumName = (TextView) itemView.findViewById(R.id.tv_forumName);
            ll_fav = (LinearLayout) itemView.findViewById(R.id.ll_fav);
            iv_collection_selete = (ImageView) itemView.findViewById(R.id.iv_collection_selete);
            iv_picture = (CircleImageView) itemView.findViewById(R.id.iv_picture);
            divider = itemView.findViewById(R.id.divider);
        }

        public void bind(final MyFavForumBean.VariablesBean.ListBean bean, boolean hideDivider, final FavEventListener<MyFavForumBean.VariablesBean.ListBean> listener) {
            divider.setVisibility(!hideDivider ? View.VISIBLE : View.GONE);
            tv_forumName.setText(bean.getTitle());
            SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            @SuppressWarnings("unused")
            long lcc = Long.valueOf(bean.getDateline());
            int i = Integer.parseInt(bean.getDateline());
            String times = sdr.format(new Date(i * 1000L));
            final String todayposts = bean.getTodayposts();
            String icon = bean.getIcon();
            int imageSetting = RedNetPreferences.getImageSetting();
            LogUtils.e("TAG", "icon=" + icon);
            if (null != icon && !TextUtils.isEmpty(icon)) {
//                Picasso.with(getActivity().getApplicationContext()).load(AppNetConfig.IMGURL1 + icon).into(iv_picture);
                ImageDisplay.loadCirimageView(getActivity(),iv_picture,AppNetConfig.IMGURL1 + icon);
            } else if (null != todayposts && !TextUtils.isEmpty(todayposts) && Integer.parseInt(todayposts) > 0) {
                iv_picture.setImageResource(R.drawable.newp);
            } else {
                iv_picture.setImageResource(R.drawable.old);
            }

            iv_collection_selete.setVisibility(View.VISIBLE);
            ll_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ForumListActivity.class);
                    intent.putExtra("threads", bean.getPosts());
                    intent.putExtra("todayposts", todayposts);
                    intent.putExtra("fid", bean.getId());
                    intent.putExtra("name", bean.getTitle());
                    getActivity().startActivity(intent);
                }
            });
            iv_collection_selete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFavChange(bean, false);
                }
            });
        }
    }
}
