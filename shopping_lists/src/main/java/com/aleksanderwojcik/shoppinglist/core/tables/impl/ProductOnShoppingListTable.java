package com.aleksanderwojcik.shoppinglist.core.tables.impl;

import com.aleksanderwojcik.shoppinglist.common.utils.Sb;
import com.aleksanderwojcik.shoppinglist.core.tables.SqlTable;

public class ProductOnShoppingListTable extends SqlTable {
    //    private static final String DATABASE_NAME = "PRODUCTS_ON_SHOPPING_LIST.db";
    public static final String TABLE_NAME = "products_on_shopping_list";
    public static final String COLUMN_ID = "pk_product_on_shopping_list_id";
    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_SHOPPING_LIST_ID = "shopping_list_id";
    public static final String COLUMN_QUANTITY = "quantity";
    @Override
    public String getCreateSql() {
        return new Sb().appendAndSpace("create table")
                .append(TABLE_NAME)
                .append("(")
                .append(COLUMN_ID).spaces("integer primary key autoincrement").comma()
                .append(COLUMN_QUANTITY).spaces("integer not null").comma()
                .append(COLUMN_PRODUCT_ID).spaces("integer not null").comma()
                .append(COLUMN_SHOPPING_LIST_ID).spaces("integer not null")
                .append(")")
                .append(";").toString();
    }
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    @Override
    public String[] getAllColumns() {
        return new String[]{
                COLUMN_ID,
                COLUMN_QUANTITY,
                COLUMN_PRODUCT_ID,
                COLUMN_SHOPPING_LIST_ID
        };
    }
    @Override
    public String getColumnId() {
        return COLUMN_ID;
    }

}
