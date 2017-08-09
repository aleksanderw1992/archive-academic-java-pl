package com.aleksanderwojcik.shoppinglist.common.helpers;

import android.database.Cursor;
import com.aleksanderwojcik.shoppinglist.common.utils.CursorUtils;

/**
 * Created by AXELA on 2014-11-27.
 */
public class CursorHelper {
    private Cursor cursor;
    public CursorHelper(Cursor cursor) {
        this.cursor = cursor;
    }
    public String getString(String columnName) {
        return CursorUtils.getString(cursor, columnName);
    }
    public Long getLong(String columnName) {
        return CursorUtils.getLong(cursor, columnName);
    }
}
