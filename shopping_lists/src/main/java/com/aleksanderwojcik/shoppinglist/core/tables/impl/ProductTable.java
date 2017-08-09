package com.aleksanderwojcik.shoppinglist.core.tables.impl;

import com.aleksanderwojcik.shoppinglist.common.utils.Sb;
import com.aleksanderwojcik.shoppinglist.core.tables.SqlTable;

public class ProductTable extends SqlTable {
    //    private static final String DATABASE_NAME = "PRODUCTS.db";
    public static final String TABLE_NAME = "products";
    public static final String COLUMN_ID = "pk_product_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MEASURE = "measure";
    @Override
    public String getCreateSql() {
        return new Sb().appendAndSpace("create table")
                .append(TABLE_NAME)
                .append("(")
                .append(COLUMN_ID)
                .spaces("integer primary key autoincrement").comma()
                .append(COLUMN_NAME)
                .spaces("text not null").comma()
                .append(COLUMN_MEASURE)
                .spaces("text not null")
                .append(")")
                .append(";").toString(
                );

    }
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    @Override
    public String[] getAllColumns() {
        return new String[]{
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_MEASURE
        };
    }
    @Override
    public String getColumnId() {
        return COLUMN_ID;
    }

}
