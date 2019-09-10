package cn.tencent.DiscuzMob.ui.adapter;

import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import cn.tencent.DiscuzMob.model.Article;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.widget.AsyncImageView;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/4/22.
 */
public class ArticleAdapter extends BaseAdapter {

    private List<Article> mArticleList;
    private static DecimalFormat mDecimalFormat = new DecimalFormat("0.0w");
    private int mArticleLayoutMinHeight;

    public ArticleAdapter() {
    }

    public void set(List<Article> articleList) {
        if (articleList != null && articleList.size() > 0) {
            this.mArticleList = articleList;
            notifyDataSetChanged();
        }
    }

    public void append(List<Article> articleList) {
        if (articleList != null && articleList.size() > 0) {
            if (mArticleList == null) {
                this.mArticleList = articleList;
            } else {
                mArticleList.addAll(articleList);
            }
            notifyDataSetChanged();
        }
    }

    public static String getFormat(long number) {
        return number > 9999 ? mDecimalFormat.format(number / 10000f) : String.valueOf(number);
    }

    @Override
    public int getCount() {
        return mArticleList != null ? mArticleList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mArticleList.get(position);
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
            holder.articleStyle = new ArticleStyle(convertView);
            holder.header = (AsyncRoundedImageView) convertView.findViewById(R.id.header);
            holder.username = (TextView) convertView.findViewById(R.id.username);
            holder.comment = (TextView) convertView.findViewById(R.id.comment);
            holder.zan = (TextView) convertView.findViewById(R.id.zan);
            holder.views = (TextView) convertView.findViewById(R.id.views);
            holder.articleStyle.layout.setVisibility(View.VISIBLE);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        boolean loadable = RedNetPreferences.getImageSetting() != 1;
        Article article = mArticleList.get(position);
        holder.username.setText(article.getAuthor());
        holder.comment.setText(String.valueOf(article.getReplies()));
        holder.zan.setText(String.valueOf(article.getRecommend_add()));
        holder.views.setText(getFormat(article.getViews()));
        holder.header.load(article.getAvatar(), R.drawable.ic_header_def2);
        holder.articleStyle.title.setText(Html.fromHtml(article.getSubject()));
        holder.articleStyle.dateline.setText(Html.fromHtml(article.getDateline()));
        holder.articleStyle.desc.setText(Html.fromHtml(article.getDesc()));
        String[] imgList = article.getImglist();
        if (imgList != null && imgList.length > 0) {
            holder.articleStyle.cover.load(!TextUtils.isEmpty(imgList[0]) && loadable ? imgList[0] : null);
            holder.articleStyle.cover.setVisibility(View.VISIBLE);
            holder.articleStyle.desc.setVisibility(TextUtils.isEmpty(article.getDesc()) ? View.INVISIBLE : View.VISIBLE);
            ((ViewGroup) holder.articleStyle.desc.getParent()).setMinimumHeight(mArticleLayoutMinHeight);
        } else {
            holder.articleStyle.cover.setVisibility(View.GONE);
            holder.articleStyle.desc.setVisibility(TextUtils.isEmpty(article.getDesc()) ? View.GONE : View.VISIBLE);
            ((ViewGroup) holder.articleStyle.desc.getParent()).setMinimumHeight(0);
        }
        return convertView;
    }


    static class ViewHolder {
        CoverStyle coverStyle;
        ArticleStyle articleStyle;
        AsyncRoundedImageView header;
        TextView username, comment, zan, views;
    }

    static class ArticleStyle {
        ViewGroup layout;
        TextView title, desc, dateline;
        AsyncImageView cover;

        ArticleStyle(View covertView) {
            layout = (ViewGroup) covertView.findViewById(R.id.style_article);
            cover = (AsyncImageView) layout.findViewById(R.id.cover);
            title = (TextView) layout.findViewById(R.id.title);
            desc = (TextView) layout.findViewById(R.id.desc);
            dateline = (TextView) layout.findViewById(R.id.dateline);
        }
    }

    static class CoverStyle {
        ViewGroup layout;
        TextView title, dateline;//dateline
        LinearLayout ics;

        CoverStyle(View covertView) {
            layout = (ViewGroup) covertView.findViewById(R.id.style_cover);
            title = (TextView) layout.findViewById(R.id.title);
            dateline = (TextView) layout.findViewById(R.id.dateline);
            ics = (LinearLayout) layout.findViewById(R.id.ics);
        }
    }


}
