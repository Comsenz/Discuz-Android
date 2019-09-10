package cn.tencent.DiscuzMob.ui.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by tiantian on 2016/5/30.
 */
public class MyThreadAdapter extends CursorAdapter {

    public MyThreadAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @TargetApi(11)
    public MyThreadAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_thread_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView threadTitle = (TextView) view.findViewById(R.id.fav_thread_title);
        TextView forumName = (TextView) view.findViewById(R.id.forum_name);
        TextView postTime = (TextView) view.findViewById(R.id.post_time);
        threadTitle.setText(Html.fromHtml(cursor.getString(cursor.getColumnIndex("title"))));
        forumName.setText(cursor.getString(cursor.getColumnIndex("author")));
        postTime.setText(dateformat(cursor.getString(cursor.getColumnIndex("dateline"))));
    }

    static String dateformat(String dateline) {
        try {
            return RednetUtils.DateFormat.MILLI2.dateFormat.format(RednetUtils.covertMillSeconds(dateline));
        } catch (Exception e) {
            return dateline;
        }
    }

}
