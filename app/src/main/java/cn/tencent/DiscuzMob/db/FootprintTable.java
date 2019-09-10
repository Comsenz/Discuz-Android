package cn.tencent.DiscuzMob.db;

/**
 * Created by cg on 2017/5/22.
 */

public class FootprintTable {
    public static final String TAB_NAME = "tab_account";
    public static final String COL_NAME = "name";
    public static final String COL_PHOTO = "photo";
    public static final String COL_TID = "tid";
    public static final String COL_SUBJECT = "subject";
    public static final String COL_SPECIAL = "special";
    public static final String COL_VIEWS = "views";
    public static final String COL_REPLIES = "replies";
    public static final String COL_DATELINE = "dateline";
    public static final String COL_RECOMMEND_ADD = "recommend_add";
    public static final String COL_DISPLAYORDER = "displayorder";
    public static final String COL_DIGEST = "digest";
    public static final String COL_IMGLIST = "imglist";
    public static final String COL_LEVEL = "level";
    public static final String CREATE_TAB = "create table "
            + TAB_NAME + " ("
            + "id integer primary key autoincrement, "
            + COL_TID + " text, "
            + COL_NAME + " text,"
            + COL_SPECIAL + " text,"
            + COL_VIEWS + " text,"
            + COL_REPLIES + " text,"
            + COL_DATELINE + " text,"
            + COL_RECOMMEND_ADD + " text,"
            + COL_SUBJECT + " text,"
            + COL_DISPLAYORDER + " text,"
            + COL_DIGEST + " text,"
            + COL_IMGLIST + " text,"
            + COL_LEVEL + " text,"
            + COL_PHOTO + " text);";


}
