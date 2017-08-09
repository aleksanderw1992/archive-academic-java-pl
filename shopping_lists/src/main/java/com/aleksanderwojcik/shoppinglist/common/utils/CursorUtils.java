package com.aleksanderwojcik.shoppinglist.common.utils;

import android.database.Cursor;

import java.util.Iterator;

/**
 * Created by AXELA on 2014-11-27.
 */
public class CursorUtils {
    public static String getStringClose(Cursor cursor, String columnName) {
        String string = getString(cursor, columnName);
        cursor.close();
        return string;
    }
    public static String getString(Cursor cursor, String columnName) {
        if (cursor.isAfterLast()) return null;
        int columnIndex = cursor.getColumnIndex(columnName);
        String cursorString = cursor.getString(columnIndex);
        return cursorString;
    }
    public static Long getLongClose(Cursor cursor, String columnName) {
        Long aLong = getLong(cursor, columnName);
        cursor.close();
        return aLong;
    }
    public static Long getLong(Cursor cursor, String columnName) {
        if (cursor.isAfterLast()) return null;
        int columnIndex = cursor.getColumnIndex(columnName);
        long cursorLong = cursor.getLong(columnIndex);
        return cursorLong;
    }
    public static Iterable<Cursor> iterate(Cursor cursor) {
        return new IterableWithObject<Cursor>(cursor) {
            @Override
            public Iterator<Cursor> iterator() {
                return new IteratorWithObject<Cursor>(t) {
                    @Override
                    public boolean hasNext() {
                        t.moveToNext();
                        if (t.isAfterLast()) {
                            t.close();
                            return false;
                        }
                        return true;
                    }
                    @Override
                    public Cursor next() {
                        return t;
                    }
                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("CursorUtils : remove : ");
                    }
                    @Override
                    protected void onCreate() {
                        t.moveToPosition(-1);
                    }
                };
            }
        };
    }

    private static abstract class IteratorWithObject<T> implements Iterator<T> {
        protected T t;
        public IteratorWithObject(T t) {
            this.t = t;
            this.onCreate();
        }
        protected abstract void onCreate();
    }

    private static abstract class IterableWithObject<T> implements Iterable<T> {
        protected T t;
        public IterableWithObject(T t) {
            this.t = t;
        }
    }
}
