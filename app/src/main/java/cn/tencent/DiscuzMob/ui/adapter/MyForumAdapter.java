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

import cn.tencent.DiscuzMob.R;


/**
 * Created by AiWei on 2016/5/29.
 */
public class MyForumAdapter extends CursorAdapter {

    public MyForumAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @TargetApi(11)
    public MyForumAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView label = (TextView) view.findViewById(R.id.title);
        label.setText(Html.fromHtml(new StringBuilder(cursor.getString(cursor.getColumnIndex("title")))
                .append("<font color='#3c96d6'>(")
                .append(String.valueOf(cursor.getInt(cursor.getColumnIndex("todayposts"))))
                .append(")</font>").toString()));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.adapter_forum, parent, false);
    }

}
