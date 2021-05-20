package com.example.shopii.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String  DB_Name = "sanpham";
    private static final  int DB_VERSION = 1;

    private static DBHelper instance = null;
    public synchronized  static DBHelper getInstance(Context context){
        if(instance == null){
            instance = new DBHelper((context));
        }
        return instance;
    }
    public DBHelper(@Nullable Context context) {
        super(context,  DB_Name, null, DB_VERSION);
    }
    // truy van k tra ket qua
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

//    truy van co tra ket qua
    private Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }
// create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateDatabaseLoaisp="CREATE TABLE loaisanpham(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hinhanhLoaiSp varchar(500)," +
                "tenloaisanpham varchar(50))";
        db.execSQL(sqlCreateDatabaseLoaisp);

        String sqlCreateDatabaseSp="CREATE TABLE loaisanpham(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenSp varchar(100)," +
                "idLoaiSp int," +
                "gia int," +
                "mota varchar(1000)," +
                "hinhanh varchar(300))";

        db.execSQL(sqlCreateDatabaseSp);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
