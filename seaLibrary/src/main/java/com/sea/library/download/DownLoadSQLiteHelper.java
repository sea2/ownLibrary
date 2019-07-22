package com.sea.library.download;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;


/**
 * app账户相关的数据库类
 *
 * @author wangkunming03
 */
public class DownLoadSQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "DownLoadSQLiteHelper";

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "accountcache.db";

    private static DownLoadSQLiteHelper instance = null;
    private SQLiteDatabase db;

    public DownLoadSQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, VERSION);
    }

    public static DownLoadSQLiteHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DownLoadSQLiteHelper.class) {
                if (instance == null)
                    instance = new DownLoadSQLiteHelper(context);
            }
        }

        return instance;
    }

    public SQLiteDatabase open(Context context) throws SQLException {
        if (db == null || !db.isOpen()) {
            try {
                db = this.getWritableDatabase(context);
            } catch (SQLiteException e) {
                Log.d(TAG, "ec:" + e.getMessage());
            }
        }
        return db;
    }


    /**
     * 保持唯一一个长连接,不关闭,避免多线程操作时数据库被关闭
     */
    public void close() {
    }

    /**
     * 另写一个方法来关闭数据库
     */
    public void closeDataBase() {
        if (db != null && db.isOpen()) {
            db.close();
            db = null;
        }
    }

    @Override
    public File getDatabasePath(Context context,String name) {
        String dbFilePath = getAccountFilePath(context,name);
        if (TextUtils.isEmpty(dbFilePath)) {
            return super.getDatabasePath(context,name);
        }
        return new File(dbFilePath);
    }


    public static String getAccountFilePath(Context context,String fileName) {
        String filePathName = "";
        try {
            String appFileDir =context.getExternalFilesDir(null).getAbsolutePath();
            File fileDir = new File(appFileDir);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }

            String sAccountDir = appFileDir + "/Account";
            File AccountDir = new File(sAccountDir);
            if (!AccountDir.exists()) {
                AccountDir.mkdir();
            }


            filePathName = sAccountDir + "/" + fileName;
        } catch (Exception ex) {
            filePathName =context.getFilesDir().getAbsolutePath();
            Log.e("DownLoadSQLiteHelper","数据库存储路径异常"+ex.getMessage());
        }

        return filePathName;
    }


    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase arg0) {
        arg0.execSQL("CREATE TABLE IF NOT EXISTS " + DownLoadConstant.TABLE_ONLINECACHE +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, type INTEGER, data_content VARCHAR)");

        arg0.execSQL("CREATE TABLE "
                + "\"" + DownLoadConstant.TABLE_DOWNLOAD + "\" "
                + "([" + DownLoadConstant.TABLE_ID + "] INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,"
                + " [" + DownLoadConstant.TASK_ID + "] LONG NOT NULL,"
                + " [" + DownLoadConstant.TASK_TYPE + "] INT NOT NULL, "
                + " [" + DownLoadConstant.TASK_STATUS + "] INT, "
                + " [" + DownLoadConstant.TASK_TOTAL_LEN + "] LONG, "
                + " [" + DownLoadConstant.TASK_PROGRESS_LEN + "] LONG, "
                + " [" + DownLoadConstant.TASK_TITLE + "] VARCHAR2,"
                + " [" + DownLoadConstant.TASK_DES + "] VARCHAR2,"
                + "[" + DownLoadConstant.TASK_URL + "] VARCHAR2 NOT NULL, "
                + "[" + DownLoadConstant.TASK_LOCAL + "] VARCHAR2, "
                + "[" + DownLoadConstant.TASK_VIDEO_LOCAL_URL + "] VARCHAR2, "
                + "[" + DownLoadConstant.TASK_ICON_URL + "] VARCHAR2, "
                + "[" + DownLoadConstant.TASK_PACGENAME + "] VARCHAR2)");
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
    }
}
