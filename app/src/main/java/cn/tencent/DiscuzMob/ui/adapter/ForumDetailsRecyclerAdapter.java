package cn.tencent.DiscuzMob.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.ui.dialog.ShareDialog;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.base.Config;
import cn.tencent.DiscuzMob.manager.SmileManager;
import cn.tencent.DiscuzMob.model.HotTThread;
import cn.tencent.DiscuzMob.model.Reply;
import cn.tencent.DiscuzMob.ui.fragment.BaseFragment;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.widget.AsyncImageView;
import cn.tencent.DiscuzMob.widget.FooterForRecycler;
import cn.tencent.DiscuzMob.widget.ILoadListener;
import cn.tencent.DiscuzMob.R;

public class ForumDetailsRecyclerAdapter extends RecyclerView.Adapter {

    private RecyclerView mRecyclerView;
    private FooterForRecycler mFooter;
    private ShareDialog mShareDialog;
    private ILoadListener mLoadListener;

    private ArrayList<HotTThread> threads;
    private ArrayList<HotTThread> mTypeList;
    private Context context;
    private String mFid;
    private String mType = "common";

    public ForumDetailsRecyclerAdapter(Context context, RecyclerView recyclerView, String fid, ILoadListener loadListener) {
        this.context = context;
        this.mRecyclerView = recyclerView;
        this.mFid = fid;
        this.mLoadListener = loadListener;
        this.threads = new ArrayList<>();
    }

    //置顶帖viewholder
    private class HotPostsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        TextView title, author, zan, comment,views, all;
/*        GridLayout replyView;
        TextView[] reply = new TextView[3];*/

        public HotPostsHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            title = (TextView) itemView.findViewById(R.id.title);
            author = (TextView) itemView.findViewById(R.id.author);
            zan = (TextView) itemView.findViewById(R.id.zan);
            comment = (TextView) itemView.findViewById(R.id.comment);
            views= (TextView) itemView.findViewById(R.id.views);
            all = (TextView) itemView.findViewById(R.id.all);
            //cover = itemView.findViewById(R.id.cover);
            //replyView = (GridLayout) itemView.findViewById(R.id.reply_view);
            //reply[0] = (TextView) itemView.findViewById(R.id.reply1);
            //reply[1] = (TextView) itemView.findViewById(R.id.reply2);
            //reply[2] = (TextView) itemView.findViewById(R.id.reply3);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            HotTThread post = mTypeList.get(getLayoutPosition());
            BaseFragment.toThreadDetails(context, mFid, post.getTid(), String.valueOf(post.getSpecial()));
        }

    }

    //普通帖viewholderS
    private class PostsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        private AsyncRoundedImageView header;
        private View cover;
        private TextView author, date, title, zan, comment,views, share, all;
        private GridLayout replyView;
        private ImageView typeTag;

        public PostsHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            header = (AsyncRoundedImageView) itemView.findViewById(R.id.header);
            cover = itemView.findViewById(R.id.cover);
            author = (TextView) itemView.findViewById(R.id.author);
            date = (TextView) itemView.findViewById(R.id.date);
            title = (TextView) itemView.findViewById(R.id.title);
            zan = (TextView) itemView.findViewById(R.id.zan);
            comment = (TextView) itemView.findViewById(R.id.comment);
            views= (TextView) itemView.findViewById(R.id.views);
            share = (TextView) itemView.findViewById(R.id.share);
            all = (TextView) itemView.findViewById(R.id.all);
            replyView = (GridLayout) itemView.findViewById(R.id.reply_view);
            typeTag = (ImageView) itemView.findViewById(R.id.thread_type_tag);
            itemView.setOnClickListener(this);
            share.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final HotTThread post = mTypeList.get(getLayoutPosition());
            if (v.getId() == R.id.share) {
                if (mShareDialog == null) {
                    mShareDialog = new ShareDialog(context) {
                        @Override
                        public Builder onShare() {
                            return ShareDialog.Builder.create(context)
                                    .setText(post.getSubject(), post.getAuthor() + "发表于" + post.getDateline())
                                    .setImageUrl(!TextUtils.isEmpty(post.optThumburl(0))?new StringBuilder(Config.HOST).append("/").append(post.optThumburl(0)).toString():null)
                                    .setUrl(Config.SHARE_URL + post.getTid());
                        }
                    };
                }
                mShareDialog.show();
            } else {
                BaseFragment.toThreadDetails(context, mFid, post.getTid(), String.valueOf(post.getSpecial()));
            }
        }
    }

    // 加载更多viewholder
    private class FooterHolder extends RecyclerView.ViewHolder {
        FooterForRecycler footer;

        public FooterHolder(View itemView) {
            super(itemView);
            footer = new FooterForRecycler(mRecyclerView, itemView);
            footer.setOnLoadListener(mLoadListener);
        }
    }

    public FooterForRecycler getFooter() {
        return mFooter;
    }

    public void setData(ArrayList<HotTThread> data) {
        threads = data;
        makeType(mType);
    }

    public void setAppendData(ArrayList<HotTThread> data) {
        if (data != null && !data.isEmpty()) {
            threads.addAll(data);
            makeType(mType);
        }
    }

    public void makeType(String type) {
        this.mType = type;
        mTypeList = getTypeThread(threads, type);
        notifyDataSetChanged();
    }

    private ArrayList<HotTThread> getTypeThread(ArrayList<HotTThread> threads, String type) {
        if (threads == null || threads.isEmpty()) {
            return null;
        } else {
            ArrayList<HotTThread> data = new ArrayList<>();
            if (type.equals("top")) {
                for (int i = 0; i < threads.size(); i++) {
                    HotTThread thread = threads.get(i);
                    if (!thread.getDisplayorder().equals("0")) {
                        data.add(thread);
                    }
                }
            } else if (type.equals("common")) {
                for (int i = 0; i < threads.size(); i++) {
                    HotTThread thread = threads.get(i);
                    if (thread.getDisplayorder().equals("0")) {
                        data.add(thread);
                    }
                }
            }
            return data;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mTypeList != null && position < mTypeList.size() ? Integer.valueOf(mTypeList.get(position).getDisplayorder()) : 10;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == 3 || viewType == 1 || viewType == 2) {
            viewHolder = new HotPostsHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.forum_details_sticky, viewGroup, false));
        } else if (viewType == 0) {
            viewHolder = new PostsHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.forum_details_posts, viewGroup, false));
        } else if (viewType == 10) {
            FooterHolder fh;
            viewHolder = fh = new FooterHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.z_footer_view, viewGroup, false));
            mFooter = fh.footer;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof HotPostsHolder) {
            HotTThread post = mTypeList.get(position);
            HotPostsHolder holder = (HotPostsHolder) viewHolder;
            holder.title.setText(Html.fromHtml(post.getSubject()));
            holder.author.setText(post.getAuthor());
            holder.zan.setText(post.getRecommend_add());
            holder.comment.setText(String.valueOf(post.getReplies()));
            holder.views.setText(ArticleAdapter.getFormat(post.getViews()));
           /* boolean loadable = RedNetPreferences.getImageSetting() != 1;
            List<String> thumbs = post.getThumburl();
            if (loadable && !RednetUtils.isEmpty(thumbs)) {
                for (int i = 0, size = thumbs.size(); i < 2; i++) {
                    ViewGroup cover = ((ViewGroup) ((ViewGroup) holder.cover).getChildAt(i));
                    if (i < size) {
                        ((AsyncImageView) cover.getChildAt(0)).load(Config.HOST + "/" + thumbs.get(i));
                        cover.setVisibility(View.VISIBLE);
                    } else {
                        cover.setVisibility(View.INVISIBLE);
                    }
                }
                holder.cover.setVisibility(View.VISIBLE);
            } else {
                holder.cover.setVisibility(View.GONE);
            }
            List<Reply> replyList = post.getReply();
            for (int i = 0, size = replyList != null ? replyList.size() : 0; i < 3; i++) {
                if (i < size) {
                    Reply reply = replyList.get(i);
                    holder.reply[i].setText(Html.fromHtml("<font color=\"#3c96d6\">" + reply.getAuthor()
                            + ":</font>" + reply.getMessage(), imgGetter, null));
                    holder.reply[i].setVisibility(View.VISIBLE);
                } else {
                    holder.reply[i].setText("");
                    holder.reply[i].setVisibility(View.GONE);
                }
            }
            holder.all.setText(post.getReplies() > 3 ? "点击查看全部" + post.getReplies() + "条回复" : "");
            holder.all.setVisibility(post.getReplies() > 3 ? View.VISIBLE : View.GONE);
            holder.replyView.setVisibility(replyList == null || replyList.isEmpty() ? View.GONE : View.VISIBLE);*/
        } else if (viewHolder instanceof PostsHolder) {
            HotTThread post = mTypeList.get(position);
            PostsHolder holder = (PostsHolder) viewHolder;
            holder.header.load(post.getAvatar(), R.drawable.ic_header_def2);
            holder.author.setText(post.getAuthor());
            holder.date.setText(Html.fromHtml(post.getDateline()));
            holder.title.setText(Html.fromHtml(post.getSubject()));
            holder.zan.setText(post.getRecommend_add());
            holder.comment.setText(String.valueOf(post.getReplies()));
            holder.views.setText(ArticleAdapter.getFormat(post.getViews()));
            boolean loadable = RedNetPreferences.getImageSetting() != 1;
            List<String> thumbs = post.getThumburl();
            if (loadable && !RednetUtils.isEmpty(thumbs)) {
                for (int i = 0, size = thumbs.size(); i < 2; i++) {
                    ViewGroup cover = ((ViewGroup) ((ViewGroup) holder.cover).getChildAt(i));
                    if (i < size) {
                        ((AsyncImageView) cover.getChildAt(0)).load(Config.HOST + "/" + thumbs.get(i));
                        cover.setVisibility(View.VISIBLE);
                    } else {
                        cover.setVisibility(View.INVISIBLE);
                    }
                }
                holder.cover.setVisibility(View.VISIBLE);
            } else {
                holder.cover.setVisibility(View.GONE);
            }

            List<Reply> replyList = post.getReply();
            for (int i = 0, size = replyList != null ? replyList.size() : 0; i < 3; i++) {
                ViewGroup item = (ViewGroup) holder.replyView.getChildAt(i);
                if (i < size) {
                    Reply reply = replyList.get(i);
                    ((AsyncRoundedImageView) item.getChildAt(0)).load(reply.getAvatar(), R.drawable.ic_header_def);
                    ((TextView) item.getChildAt(1)).setText(Html.fromHtml("<font color=\"#3c96d6\">" + reply.getAuthor()
                            + ":</font>" + reply.getMessage(), imgGetter, null));
                    item.setVisibility(View.VISIBLE);
                } else {
                    item.setVisibility(View.GONE);
                }
            }
            holder.all.setText(post.getReplies() > 3 ? "点击查看全部" + post.getReplies() + "条回复" : "");
            holder.all.setVisibility(post.getReplies() > 3 ? View.VISIBLE : View.GONE);
            holder.replyView.setVisibility(replyList == null || replyList.isEmpty() ? View.GONE : View.VISIBLE);

            if (post.getSpecial() == 1) {
                holder.typeTag.setImageResource(R.drawable.ic_vote);
                holder.typeTag.setVisibility(View.VISIBLE);
            } else if (post.getSpecial() == 4) {
                holder.typeTag.setImageResource(R.drawable.ic_activity);
                holder.typeTag.setVisibility(View.VISIBLE);
            } else {
                holder.typeTag.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mTypeList == null ? 1 : mTypeList.size() + 1;
    }

    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
            int resId = android.R.drawable.screen_background_light_transparent;
            if (!TextUtils.isEmpty(source)) {
                for (int i = 0; i < SmileManager.CODE_DECODE.length; i++) {
                    if (source.contains(SmileManager.CODE_DECODE[i])) {
                        resId = SmileManager.EMOJ[i];
                        break;
                    }
                }
            }
            Drawable drawable = null;
            if (resId != 0) {
                Bitmap bit = BitmapFactory.decodeResource(context.getResources(), resId);
                drawable = resizeImage(bit, 140, 140);
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                            .getIntrinsicHeight());
                }
            }
            return drawable;
        }
    };

    public static Drawable resizeImage(Bitmap bitmap, int w, int h) {
        if (bitmap == null) {
            return null;
        }
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return new BitmapDrawable(resizedBitmap);
    }
}
