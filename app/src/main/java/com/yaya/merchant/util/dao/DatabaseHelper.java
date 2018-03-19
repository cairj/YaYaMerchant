package com.yaya.merchant.util.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "yaya_merchant.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    private static final String ASSETS_SQL_PATH = "sql/";

    private static DatabaseHelper instance;
    private Map<String, Dao> daoMap = new HashMap<String, Dao>();

    public DatabaseHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        createTableFromAssets(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    /**
     * 单例获取该Helper
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    instance = new DatabaseHelper(context);
                }
            }
        }
        return instance;
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daoMap.containsKey(className)) {
            dao = daoMap.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daoMap.put(className, dao);
        }
        return dao;
    }

    /**
     * 创建新表
     */
    private void createTableFromAssets(SQLiteDatabase sqLiteDatabase){
        try {
            /*executeAssetsSQL(sqLiteDatabase, "tb_industry.sql");*/
            executeAssetsSQL(sqLiteDatabase, "tb_region.sql");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 读取数据库文件（.sql），并执行sql语句
     * @param database
     * @param schemaName
     */
    private void executeAssetsSQL(SQLiteDatabase database, String schemaName) {
        try {
            InputStream in = context.getAssets().open(ASSETS_SQL_PATH + schemaName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            String line;
            String buffer = "";
            while ((line = bufferedReader.readLine()) != null) {
                buffer += line;
                if (line.trim().endsWith(";")) {
                    database.execSQL(buffer.replace(";", ""));
                    buffer = "";
                }
            }
            bufferedReader.close();
            in.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
