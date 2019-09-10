package cn.tencent.DiscuzMob.ui.adapter;

import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.model.ArticleGuide;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.widget.AsyncImageView;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/5/4.
 */
public class GuideArticleAdapter extends BaseAdapter {

    List<ArticleGuide> mData;
    private int mArticleLayoutMinHeight;

    public GuideArticleAdapter() {
    }

    public void setData(List<ArticleGuide> list) {
        if (list != null && !list.isEmpty()) {
            mData = list;
            notifyDataSetChanged();
        }
    }

    public void append(List<ArticleGuide> list) {
        if (list != null && !list.isEmpty()) {
            if (mData == null) {
                mData = list;
            } else {
                mData.addAll(list);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_article, parent, false);
            if (mArticleLayoutMinHeight == 0)
                mArticleLayoutMinHeight = RednetUtils.dp(parent.getContext(), 80f);
            holder = new ViewHolder();
            holder.styleArticle = convertView.findViewById(R.id.style_article);
            holder.cover = (AsyncImageView) holder.styleArticle.findViewById(R.id.cover);
            holder.saTitle = (TextView) holder.styleArticle.findViewById(R.id.title);
            holder.saDesc = (TextView) holder.styleArticle.findViewById(R.id.desc);
            holder.saDateline = (TextView) holder.styleArticle.findViewById(R.id.dateline);
            holder.saDesc.setVisibility(View.INVISIBLE);
            holder.styleArticle.setVisibility(View.VISIBLE);

            holder.styleCover = convertView.findViewById(R.id.style_cover);
            holder.ics = convertView.findViewById(R.id.ics);
            holder.scTitle = (TextView) holder.styleCover.findViewById(R.id.title);
            holder.scDateline = (TextView) holder.styleCover.findViewById(R.id.dateline);

            holder.header = (AsyncRoundedImageView) convertView.findViewById(R.id.header);
            holder.username = (TextView) convertView.findViewById(R.id.username);
            holder.comment = (TextView) convertView.findViewById(R.id.comment);
            holder.zan = (TextView) convertView.findViewById(R.id.zan);
            holder.views = (TextView) convertView.findViewById(R.id.views);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        boolean loadable = RedNetPreferences.getImageSetting() != 1;
        ArticleGuide article = mData.get(position);
        holder.header.load(article.getAvatar(), R.drawable.ic_header_def);
        holder.username.setText(article.getAuthor());
        holder.comment.setText(String.valueOf(article.getReplies()));
        holder.zan.setText(String.valueOf(article.getRecommend_add()));
        holder.views.setText(ArticleAdapter.getFormat(article.getViews()));
        holder.saTitle.setText(article.getSubject());
        holder.saDateline.setText(Html.fromHtml(article.getDateline()));
        if (article.getAttachment() != 2 || TextUtils.isEmpty(article.getAttachment_url())) {
            holder.cover.setVisibility(View.GONE);
            holder.saDesc.setVisibility(View.GONE);
            ((ViewGroup) holder.saDesc.getParent()).setMinimumHeight(0);
        } else {
            holder.cover.load(loadable ? article.getAttachment_url() : null);
            holder.cover.setVisibility(View.VISIBLE);
            holder.saDesc.setVisibility(View.INVISIBLE);
            ((ViewGroup) holder.saDesc.getParent()).setMinimumHeight(mArticleLayoutMinHeight);
        }
        return convertView;
    }

    static class ViewHolder {
        View styleArticle, styleCover, ics;
        AsyncImageView cover;
        TextView saTitle, saDesc, saDateline, scTitle, scDateline;
        AsyncRoundedImageView header;
        TextView username, comment, zan, views;
    }


}
