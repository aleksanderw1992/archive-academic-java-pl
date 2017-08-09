package com.aleksanderwojcik.shoppinglist.core.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.aleksanderwojcik.shoppinglist.core.tables.SqlTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AXELA on 2014-11-27.
 */
public class DatabaseAccessObject extends SQLiteOpenHelper {
    private  static final String DATABASE_NAME = "SHOPPING_LISTS.db";
    private  static final int DEFAULT_DATABASE_VERSION = 1;
    private  SQLiteDatabase database;
    private List<SqlTable> tables;

    public DatabaseAccessObject(Context context, SqlTable... sqlTables) {
        super(context, DATABASE_NAME, null, DEFAULT_DATABASE_VERSION);
        tables = new ArrayList<SqlTable>();
        for (SqlTable sqlTable : sqlTables) tables.add(sqlTable);
    }
    public void close() {
        if (this.database.isOpen())
            this.database.close();
    }
    @Override
    public final void onCreate(SQLiteDatabase database) {
        for (SqlTable sqlTable : tables) {
            database.execSQL("drop table if exists ".concat(sqlTable.getTableName()));
            database.execSQL(sqlTable.getCreateSql());
            Log.e("DatabaseAccessObject", sqlTable.getCreateSql());
        }
    }
    @Override
    public final void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(),
                new StringBuilder().
                        append("Upgrading database from version ").
                        append(oldVersion).append(" to ").
                        append(newVersion).append(", which will destroy all old data").toString());
        for (SqlTable table : tables) db.execSQL("DROP TABLE IF EXISTS ".concat(table.getTableName()));
        onCreate(db);
    }
    public android.database.Cursor query(SqlTable sqlTable, String selection, String[] selectionArg) {
        return this.database.query(sqlTable.getTableName(), sqlTable.getAllColumns(), selection, selectionArg,
                null, null, null);
    }
    public SQLiteDatabase getDatabase() {
        if (database == null || !database.isOpen()) {
            Log.e("DatabaseAccessObject#getDatabase", database == null ? "null" : "closed");
            throw new RuntimeException("DatabaseAccessObject : getDatabase : ");
        }

        this.open();
        return database;
    }
    public void open() throws SQLException {
        if (database == null || !database.isOpen()) {
            this.database = this.getWritableDatabase();
        }
//        for (SqlTable sqlTable : tables){
//            database.execSQL("drop table "+sqlTable.getTableName());
//            database.execSQL(sqlTable.getCreateSql());
//            Log.e("DatabaseAccessObject",sqlTable.getCreateSql());
//        }
    }
}
