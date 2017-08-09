package com.aleksanderwojcik.shoppinglist.core.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import com.aleksanderwojcik.shoppinglist.common.exceptions.DataIntegrityException;
import com.aleksanderwojcik.shoppinglist.common.helpers.CursorHelper;
import com.aleksanderwojcik.shoppinglist.common.utils.CursorUtils;
import com.aleksanderwojcik.shoppinglist.common.utils.StringUtils;
import com.aleksanderwojcik.shoppinglist.core.dao.Dao;
import com.aleksanderwojcik.shoppinglist.core.database.DatabaseAccessObject;
import com.aleksanderwojcik.shoppinglist.core.tables.SqlTable;
import com.aleksanderwojcik.shoppinglist.core.tables.impl.ShoppingListsTable;
import com.aleksanderwojcik.shoppinglist.domain.ShoppingList;

public class ShoppingListDao extends Dao<ShoppingList> {

    public ShoppingListDao(DatabaseAccessObject databaseAccessObject, SqlTable sqlTable) {
        super(databaseAccessObject, sqlTable);
    }
    public ShoppingList save(ShoppingList shoppingList) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListsTable.COLUMN_NAME, shoppingList.getName());
        long insertId = database.insert(ShoppingListsTable.TABLE_NAME, null,
                values);
        Cursor cursor = databaseAccessObject.query(sqlTable,
                ShoppingListsTable.COLUMN_ID + " = ?",
                StringUtils.asArray(insertId));
        cursor.moveToFirst();
        ShoppingList ShoppingList = fromCursor(cursor);
        cursor.close();
        log("Created ".concat(shoppingList.toString()));
        return ShoppingList;
    }
    public ShoppingList fromCursor(Cursor cursor) {
        CursorHelper helper = new CursorHelper(cursor);
        ShoppingList ShoppingList = new ShoppingList();
        ShoppingList.setId(helper.getLong(ShoppingListsTable.COLUMN_ID));
        ShoppingList.setName(helper.getString(ShoppingListsTable.COLUMN_NAME));
        return ShoppingList;
    }
    @Override
    public void update(ShoppingList shoppingList) {
        ContentValues contentValues = new ContentValues();
        String name = StringUtils.singleQuotation(shoppingList.getName());
        long id = shoppingList.getId();
        contentValues.put(ShoppingListsTable.COLUMN_NAME, name);
        database.update(ShoppingListsTable.TABLE_NAME, contentValues, ShoppingListsTable.COLUMN_ID.concat(" = ?"),
                StringUtils.asArray(id));
    }
    @Override
    public Long findId(ShoppingList shoppingList) {
        Cursor cursor = databaseAccessObject.query(sqlTable, ShoppingListsTable.COLUMN_NAME.concat("=?"),
                StringUtils.asArray(shoppingList.getName()));
        int count = cursor.getCount();
        cursor.moveToFirst();
        switch (count) {
            case 0:
                cursor.close();
                return null;
            case 1:
                return CursorUtils.getLongClose(cursor, ShoppingListsTable.COLUMN_ID);
            default:
                throw new DataIntegrityException("ShoppingListDao : alreadyExists : should be unique " + shoppingList.toString());
        }
    }

}