package com.aleksanderwojcik.shoppinglist.core.tables;

/**
 * Created by AXELA on 2014-11-26.
 */
public abstract class SqlTable {
    public abstract String getCreateSql();
    public abstract String getTableName();
    public abstract String[] getAllColumns();
    public abstract String getColumnId();

}
