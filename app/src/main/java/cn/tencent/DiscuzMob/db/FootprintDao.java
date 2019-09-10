package cn.tencent.DiscuzMob.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.model.EssenceBean;
import cn.tencent.DiscuzMob.model.ForumThreadlistBean;
import cn.tencent.DiscuzMob.model.ImglistBean;
import cn.tencent.DiscuzMob.utils.LogUtils;

/**
 * Created by cg on 2017/5/22.
 */

public class FootprintDao {
    public static final String SQL_QUERY_FOOTPRINT = "select * from tab_account order by id desc limit ?,?";
    private final FootprintDB mHelper;
    private long count;

    //    private ForumThreadlistBean account;
//
    public FootprintDao(Context context) {
        mHelper = new FootprintDB(context);
//        mHelper = Modal.getInstance().getFootprintDB();
    }

    //添加数据到数据库
    public long addAccount(ForumThreadlistBean user) {
        LogUtils.i("添加足迹："+user.toString());
        //获取数据的链接
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //操作数据库
        ContentValues values = new ContentValues();
        values.put(FootprintTable.COL_TID, user.getTid());
        values.put(FootprintTable.COL_NAME, user.getAuthor());
        values.put(FootprintTable.COL_SPECIAL, user.getSpecial());
        values.put(FootprintTable.COL_VIEWS, user.getViews());
        values.put(FootprintTable.COL_REPLIES, user.getReplies());
        values.put(FootprintTable.COL_DATELINE, user.getDateline());
        values.put(FootprintTable.COL_RECOMMEND_ADD, user.getRecommend_add());
        values.put(FootprintTable.COL_SUBJECT, user.getSubject());
        values.put(FootprintTable.COL_DISPLAYORDER, user.getDisplayorder());
        values.put(FootprintTable.COL_DIGEST, user.getDigest());
        values.put(FootprintTable.COL_PHOTO, user.getAvatar());
        List<EssenceBean.VariablesBean.DataBean.AttachlistBean> imglist = user.getImglist();
        Type imglistType = new TypeToken<List<ImglistBean>>(){}.getType();
        String imgListStr = new Gson().toJson(imglist, imglistType);
        values.put(FootprintTable.COL_IMGLIST, imgListStr);
        values.put(FootprintTable.COL_LEVEL, user.getLevel());
//        db.replace(FootprintTable.TAB_NAME, null, values);
        long insert = db.insert(FootprintTable.TAB_NAME, null, values);
        count++;
        return insert;
    }

    //获取用户
    public ForumThreadlistBean getAccount(String name) {
        //获取数据库链接
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //操作数据库
        String spl = "selete * from " + FootprintTable.TAB_NAME + " where " + FootprintTable.COL_NAME + "=?";
        Cursor cursor = db.rawQuery(spl, new String[]{name});
        ForumThreadlistBean account = null;
        if (cursor.moveToNext()) {
            account = new ForumThreadlistBean();
            account = new ForumThreadlistBean();
            account.setAuthor(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_NAME)));
            account.setAvatar(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_PHOTO)));
            account.setTid(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_TID)));
            account.setSubject(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_SUBJECT)));
            account.setSpecial(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_SPECIAL)));
            account.setViews(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_VIEWS)));
            account.setReplies(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_REPLIES)));
            account.setDateline(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_DATELINE)));
            account.setRecommend_add(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_RECOMMEND_ADD)));
            account.setDisplayorder(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_DISPLAYORDER)));
            account.setDigest(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_DIGEST)));
        }
        //关闭资源
        cursor.close();

        //返回数据
        return account;
    }

    //根据不同的环信id获取所有的用户信息
    public ForumThreadlistBean getAccountByHxId() {
        //获取数据库链接
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //操作数据库
//        String sql = "selete * from "+ UserAccountTable.TAB_NAME + " where " + UserAccountTable.COL_NAME + "=? ";
        String sql = "select * from " + FootprintTable.TAB_NAME;
        Log.e("TAG", sql);
//        Cursor cursor = db.rawQuery(sql, new String[]{hxId});
        Cursor cursor = db.rawQuery(sql, null);
        ForumThreadlistBean account = null;
        if (cursor.moveToNext()) {
            account = new ForumThreadlistBean();
            account.setAuthor(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_NAME)));
            account.setAvatar(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_PHOTO)));
            account.setTid(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_TID)));
            account.setSubject(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_SUBJECT)));
            account.setSpecial(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_SPECIAL)));
            account.setViews(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_VIEWS)));
            account.setReplies(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_REPLIES)));
            account.setDateline(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_DATELINE)));
            account.setRecommend_add(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_RECOMMEND_ADD)));
            account.setDisplayorder(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_DISPLAYORDER)));
            account.setDigest(cursor.getString(cursor.getColumnIndex(FootprintTable.COL_DIGEST)));

        }
        //关闭资源
        cursor.close();

        //返回数据
        return account;
    }

    //关闭数据
    public void close() {
        mHelper.close();
    }

    public long getCount() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String sql = "select count(*) from tab_account";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }

    /**
     * 分页查找数据
     *
     * @param offset    跳过多少条数据
     * @param maxResult 每页多少条数据
     * @return
     */
    public List<ForumThreadlistBean> getScrollData(int offset, long maxResult) {
        List<ForumThreadlistBean> users = new ArrayList<ForumThreadlistBean>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL_QUERY_FOOTPRINT, new String[]{String.valueOf(offset), String.valueOf(maxResult)});
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String photo = cursor.getString(cursor.getColumnIndex("photo"));
            String tid = cursor.getString(cursor.getColumnIndex("tid"));
            String subject = cursor.getString(cursor.getColumnIndex("subject"));
            String special = cursor.getString(cursor.getColumnIndex("special"));
            String views = cursor.getString(cursor.getColumnIndex("views"));
            String replies = cursor.getString(cursor.getColumnIndex("replies"));
            String dateline = cursor.getString(cursor.getColumnIndex("dateline"));
            String recommend_add = cursor.getString(cursor.getColumnIndex("recommend_add"));
            String displayorder = cursor.getString(cursor.getColumnIndex("displayorder"));
            String digest = cursor.getString(cursor.getColumnIndex("digest"));
            String level = cursor.getString(cursor.getColumnIndex("level"));
            Type type = new TypeToken<List<ImglistBean>>(){}.getType();
            String imglistStr = cursor.getString(cursor.getColumnIndex("imglist"));
            List<EssenceBean.VariablesBean.DataBean.AttachlistBean> objects = new Gson().fromJson(imglistStr, type);
            ForumThreadlistBean user = new ForumThreadlistBean();
            user.setAuthor(name);
            user.setAvatar(photo);
            user.setTid(tid);
            user.setSubject(subject);
            user.setSpecial(special);
            user.setViews(views);
            user.setReplies(replies);
            user.setDateline(dateline);
            user.setRecommend_add(recommend_add);
            user.setDisplayorder(displayorder);
            user.setDigest(digest);
            user.setLevel(level);
            user.setImglist(objects);
            users.add(user);
        }
        return users;
    }

    private static final String SEP1 = "#";

    public static String ListToString(List<?> list) {
        StringBuffer sb = new StringBuffer();
        if (list == null || list.size() == 0) {
            return null;
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null || list.get(i) == "") {
                continue;
            }
            // 如果值是list类型则调用自己
            if (list.get(i) instanceof List) {
                sb.append(ListToString((List<?>) list.get(i)));
                sb.append(SEP1);
            } else {
                sb.append(list.get(i));
                sb.append(SEP1);
            }
        }

        return "L" + sb.toString();
    }

    public static List<ImglistBean> StringToList(String listText) {
        if (listText == null || listText.equals("")) {
            return null;
        }
        listText = listText.substring(1);

        listText = listText;

        List<ImglistBean> list = new ArrayList();
        String[] text = listText.split(SEP1);
        for (String str : text) {
            if (str != "" && 'L' == str.charAt(0)) {
                List<ImglistBean> lists = StringToList(str);
                list.addAll(lists);
            } else {
//                list.add(str);
            }
        }
        return list;
    }
}
