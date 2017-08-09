package com.aleksanderwojcik.shoppinglist.core.tables.impl;

import com.aleksanderwojcik.shoppinglist.common.utils.Sb;
import com.aleksanderwojcik.shoppinglist.core.tables.SqlTable;

public class ShoppingListsTable extends SqlTable {
    public static final String TABLE_NAME = "shopping_lists";
    public static final String COLUMN_ID = "pk_shopping_list_id";
    public static final String COLUMN_NAME = "name";
//    private static final String DATABASE_NAME = "SHOPPING_LISTS.db";

    @Override
    public String getCreateSql() {
        return new Sb().append("create table ")
                .append(TABLE_NAME)
                .append("(")
                .append(COLUMN_ID).spaces("integer primary key autoincrement").comma()
                .append(COLUMN_NAME).spaces("text not null")
                .append(")")
                .append(";")
                .toString();

    }
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    @Override
    public String[] getAllColumns() {
        return new String[]{
                COLUMN_ID,
                COLUMN_NAME
        };
    }
    @Override
    public String getColumnId() {
        return COLUMN_ID;
    }

}
