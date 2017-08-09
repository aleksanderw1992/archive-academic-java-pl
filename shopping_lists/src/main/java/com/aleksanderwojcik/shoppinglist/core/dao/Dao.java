package com.aleksanderwojcik.shoppinglist.core.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.aleksanderwojcik.shoppinglist.common.utils.CursorUtils;
import com.aleksanderwojcik.shoppinglist.common.utils.StringUtils;
import com.aleksanderwojcik.shoppinglist.core.database.DatabaseAccessObject;
import com.aleksanderwojcik.shoppinglist.core.tables.SqlTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AXELA on 2014-11-23.
 */
public abstract class Dao<T> {
    protected SQLiteDatabase database;
    protected DatabaseAccessObject databaseAccessObject;
    protected SqlTable sqlTable;
    private String tag = this.getClass().getSimpleName();
    protected Dao(DatabaseAccessObject databaseAccessObject, SqlTable sqlTable) {
        this.databaseAccessObject = databaseAccessObject;
        this.sqlTable = sqlTable;
        this.database = databaseAccessObject.getDatabase();
    }
    protected  void log(String text) {
        Log.e(tag, text);
    }
    public abstract T save(T t);
    public final void delete(long id) {
        databaseAccessObject.getDatabase().delete(sqlTable.getTableName(),
                sqlTable.getColumnId().concat(" = ?"), StringUtils.asArray(id));
    }
    public final List<T> getAll() {
        List<T> list = new ArrayList<T>();
        Cursor cursor = this.databaseAccessObject.query(sqlTable, null, null);
        for (Cursor c : CursorUtils.iterate(cursor)) {
            T t = fromCursor(c);
            list.add(t);
        }
/*
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            T t = fromCursor(cursor);
            list.add(t);
            cursor.moveToNext();
        }
        cursor.close();
*/
        return list;
    }
    protected  abstract T fromCursor(Cursor cursor);
    public T find(long id) {
        Cursor cursor = this.databaseAccessObject.query(sqlTable,
                sqlTable.getColumnId().concat(" =? "), StringUtils.asArray(id));
        cursor.moveToFirst();
        T t = fromCursor(cursor);
        cursor.close();
        return t;
    }
    public abstract void update(T t);
    public boolean alreadyExistsIgnoreId(T t) {
        Long id = findId(t);
        return id != null;
    }
    public abstract Long findId(T t);
}
