package comfranklicm.github.openmind.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.List;


/**
 * 数据库部分封装,数据库单例
 */
public class DataBaseUtil extends SQLiteOpenHelper{


    private static final String dataBaseName="opemmind.db";
    private List<String>tableList;
    private static  final int Version=1;
    private Context context;
    //为了实现每次使用该类时不创建新的对象而创建的静态对象
    private static DataBaseUtil DataBaseUtilInstance;
    //构造方法私有化
    /**
     * @param context 上下文
     */
    private DataBaseUtil(Context context) {
        super(context, dataBaseName, null, Version);
    }
    //实例化一次
    public synchronized static DataBaseUtil getInstance(Context context)
    {
        if (null == DataBaseUtilInstance) {
            DataBaseUtilInstance = new DataBaseUtil(context);
        }
        return DataBaseUtilInstance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {// 覆写onCreate方法，当数据库创建时就用SQL命令创建一个表
        // 创建一个t_users表，id主键，自动增长，字符类型的username和pass;
        db.execSQL("create table if not exists User(id integer primary key autoincrement,username varchar(200),password varchar(200),realname varchar(200),department varchar(200),signuptime varchar(200),projects varchar(200),vote_limit integer(15))");
        db.execSQL("create table if not exists ProjectInfo(id integer primary key,proj_name varchar(200),own_name varchar(200),pub_time varchar(200),labels varchar(200),links varchar(200),introduction varchar(200),shares varchar(200),comments varchar(200))");
        db.execSQL("create table if not exists ActiveInfo(username varchar(200) primary key,month varchar(200),active varchar(200) )");
        //"create table t_users(id integer primary key autoincrement,username varchar(200),pass varchar(200) )"
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
    /**
     * 删除数据库
     * @param context 上下文
     * @return 返回是否成功
     */
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(dataBaseName);
    }
    public String getDataBaseName() {
        return dataBaseName;
    }

    public List<String> getTableList() {
        return tableList;
    }

    public void setTableList(List<String> tableList) {
        this.tableList = tableList;
    }

    public int getVersion() {
        return Version;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

