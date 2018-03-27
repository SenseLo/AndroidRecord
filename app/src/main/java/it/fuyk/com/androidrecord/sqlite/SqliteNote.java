package it.fuyk.com.androidrecord.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * author: senseLo
 * date: 2018/3/27
 */

public class SqliteNote extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "my.db";
    private static final String TABLE_NAME = "SenseLo";

    /*
    * 数据库操作：增删改查
    * */

    public SqliteNote(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /*
        * 创建表
        * */
        String sql = "create table if not exists " + TABLE_NAME + " (Id integer primary key, Param1 text, Param2 integer, Param3 text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        * 升级表
        * */
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);

        onCreate(db);
    }

    /*
    * 增
    * */
    public void insert() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        /*
        * 通过sql语句增
        * */
        db.execSQL("insert into " + TABLE_NAME + " (Id, Param1, Param2, Param3) values ('a', 1, 'b')");

        /*
        * 通过insert方法
        * */
        ContentValues values = new ContentValues();
        values.put("Id", 1);
        values.put("Param1", "aa");
        values.put("Param2", 2);
        values.put("Param3", "bb");
        db.insert(TABLE_NAME, null, values);

        db.setTransactionSuccessful();
    }

    /*
    * 删
    * */
    public void delete() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        /*
        * 通过sql语句增
        * */
        db.execSQL("delete from" + TABLE_NAME + " where Id = 1");

        /*
        * 通过delete方法
        * */
        db.delete(TABLE_NAME, "Id = ?", new String[]{String.valueOf(2)});

        db.setTransactionSuccessful();
    }

    /*
    * 改
    * */
    public void update() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        /*
        * 通过sql语句增
        * */
        db.execSQL("update " + TABLE_NAME + " set Parma1 = aaa where Id = 1");

        /*
        * 通过update方法
        * */
        ContentValues values = new ContentValues();
        values.put("Param1", "haha");

        db.update(TABLE_NAME, values, "Param1 = ?", new String[]{"haha"});
        db.setTransactionSuccessful();
    }

    /*
    * 查
    * */
    public void query() {
        SQLiteDatabase db = getReadableDatabase();

        /*
        * 通过sql语句查找
        * */
        db.execSQL("select * from " + TABLE_NAME + " where Param1 = aa");

        /*
        * 通过query(String table,String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit)
        * table：表名
        * columns：列名称数组
        * selection：条件字句 where
        * selectionArgs：条件字句 参数数组
        * groupBy：分组列
        * having：分组条件
        * orderBy：排序列
        * limit：分页查询限制
        * */

        Cursor cursor = db.query(TABLE_NAME, new String[]{"COUNT(Id)"}, "Param1 = ?", new String[]{"aa"}, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

            }
        }
    }
}
