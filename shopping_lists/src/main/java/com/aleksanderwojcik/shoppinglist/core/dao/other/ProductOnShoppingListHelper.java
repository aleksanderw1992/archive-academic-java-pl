package com.aleksanderwojcik.shoppinglist.core.dao.other;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import com.aleksanderwojcik.shoppinglist.common.exceptions.DataIntegrityException;
import com.aleksanderwojcik.shoppinglist.common.utils.CursorUtils;
import com.aleksanderwojcik.shoppinglist.common.utils.Sb;
import com.aleksanderwojcik.shoppinglist.common.utils.StringUtils;
import com.aleksanderwojcik.shoppinglist.core.database.DatabaseAccessObject;
import com.aleksanderwojcik.shoppinglist.core.tables.impl.ProductOnShoppingListTable;
import com.aleksanderwojcik.shoppinglist.core.tables.impl.ProductTable;
import com.aleksanderwojcik.shoppinglist.domain.ProductOnShoppingList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AXELA on 2014-11-27.
 */
public class ProductOnShoppingListHelper {
    private DatabaseAccessObject databaseAccessObject;
    public ProductOnShoppingListHelper(DatabaseAccessObject databaseAccessObject) {
        this.databaseAccessObject = databaseAccessObject;
    }
    public List<ProductOnShoppingList> getAllProductsForShoppingListId(long shoppingListId) {
        Map<String, String> values = new HashMap<String ,String >();
        values.put(ProductOnShoppingListTable.COLUMN_SHOPPING_LIST_ID, shoppingListId + "");
        String sql = getSqlQuery(values);
        Cursor cursor = databaseAccessObject.getDatabase().rawQuery(sql, null);
        this.databaseAccessObject.open();
        List<ProductOnShoppingList> list = new ArrayList<ProductOnShoppingList>();
        for (Cursor c : CursorUtils.iterate(cursor)) {
            ProductOnShoppingList product = fromCursor(c);
            list.add(product);
        }
        /*cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ProductOnShoppingList product = fromCursor(cursor);
            list.add(product);
            cursor.moveToNext();
        }
        cursor.close();*/
        return list;
    }
    public String getSqlQuery(Map<String, String> values) {
        Sb sb = new Sb();
        sb.appendAndSpace("select * from")
                .appendAndSpace(ProductOnShoppingListTable.TABLE_NAME)
                .appendAndSpace("left outer join")
                .appendAndSpace(ProductTable.TABLE_NAME)
                .appendAndSpace("on")
                .equals(ProductOnShoppingListTable.COLUMN_PRODUCT_ID, ProductTable.COLUMN_ID);
        for (Map.Entry<String, String> entry : values.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.spaces("and")
                    .equals(key, value);
        }
        String sql = sb.toString();
        sql = sql.replaceFirst("and", "where");
        Log.w(this.getClass().getSimpleName(), sql);
        return sql;
    }
    public ProductOnShoppingList fromCursor(Cursor cursor) {
        if (cursor == null || cursor.isAfterLast()) {
            throw new RuntimeException("ProductOnShoppingListHelper : fromCursor : cursor after last");
        }
        long productId = CursorUtils.getLong(cursor, ProductOnShoppingListTable.COLUMN_PRODUCT_ID);
        String measure = CursorUtils.getString(cursor, ProductTable.COLUMN_MEASURE);
        String name = CursorUtils.getString(cursor, ProductTable.COLUMN_NAME);
        long quantity = CursorUtils.getLong(cursor, ProductOnShoppingListTable.COLUMN_QUANTITY);
        ProductOnShoppingList product = new ProductOnShoppingList();
        product.setName(name);
        product.setQuantity((int) quantity);
        product.setId(productId);
        product.setMeasure(measure);
        return product;
    }
    public void deleteAllFromShoppingList(long shoppingListId) {
        databaseAccessObject.getDatabase().delete(ProductOnShoppingListTable.TABLE_NAME,
                ProductOnShoppingListTable.COLUMN_SHOPPING_LIST_ID.concat("=?"),
                StringUtils.asArray(shoppingListId));
    }
    public void save(ProductOnShoppingList productOnShoppingList, long shoppingListId) {
        Long productId = productOnShoppingList.getId();
        if (productId == null) {
            throw new RuntimeException("ProductOnShoppingListHelper : save : ");
        }
        ContentValues values = new ContentValues();
        values.put(ProductOnShoppingListTable.COLUMN_PRODUCT_ID, productId);
        values.put(ProductOnShoppingListTable.COLUMN_QUANTITY, productOnShoppingList.getQuantity());
        values.put(ProductOnShoppingListTable.COLUMN_SHOPPING_LIST_ID, shoppingListId);
        databaseAccessObject.getDatabase().insert(ProductOnShoppingListTable.TABLE_NAME, null,
                values);
    }
    public void update(ProductOnShoppingList productOnShoppingList, long shoppingListId) {
        Long productId = productOnShoppingList.getId();
        if (productId == null) {
            throw new RuntimeException("ProductOnShoppingListHelper : update : ");
        }
        ContentValues contentValues = new ContentValues();

        contentValues.put(ProductOnShoppingListTable.COLUMN_QUANTITY, productOnShoppingList.getQuantity());
        Sb sb = new Sb();
        sb.append(ProductOnShoppingListTable.COLUMN_PRODUCT_ID).append("= ?")
                .spaces("and")
                .append(ProductOnShoppingListTable.COLUMN_SHOPPING_LIST_ID).append("=?");
        this.databaseAccessObject.getDatabase().update(ProductOnShoppingListTable.TABLE_NAME, contentValues,
                sb.toString(),
                StringUtils.asArray(productId, shoppingListId));
    }
    public boolean alreadyOnShoppingList(Long productOnShoppingListId, long shoppingListId) {
        if (productOnShoppingListId == null) {
            throw new RuntimeException("ProductOnShoppingListHelper : alreadyOnShoppingList : isNull");
        }
        Sb sb = new Sb();
        sb.appendAndSpace("select count(*) from")
                .appendAndSpace(ProductOnShoppingListTable.TABLE_NAME)
                .appendAndSpace("where")
                .appendAndSpace(ProductOnShoppingListTable.COLUMN_PRODUCT_ID)
                .appendAndSpace("= ?")
                .appendAndSpace("and")
                .appendAndSpace(ProductOnShoppingListTable.COLUMN_SHOPPING_LIST_ID)
                .appendAndSpace("= ? ");
        String detailMessage = new Sb()
                .appendAndSpace("ProductOnShoppingListHelper : alreadyOnShoppingList : should be unique")
                .newline()
                .append(StringUtils.equalsSing("productOnShoppingList", productOnShoppingListId))
                .comma()
                .append(StringUtils.equalsSing("shoppingListId", shoppingListId))
                .toString();
        return checkIfRecordExistsAndIsUnique(sb.toString(), StringUtils.asArray(productOnShoppingListId, shoppingListId), detailMessage);
    }
    private boolean checkIfRecordExistsAndIsUnique(String sql, String[] selectionArgs, String detailMessage) {
        Cursor cursor = this.databaseAccessObject.getDatabase().rawQuery(sql,
                selectionArgs);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        switch (count) {
            case 0:
                return false;
            case 1:
                return true;
            default:
                throw new DataIntegrityException(detailMessage);
        }
    }
    public void deleteFromShoppingList(Long productId, long shoppingListId) {
        String sql = new Sb()
                .append(ProductOnShoppingListTable.COLUMN_PRODUCT_ID).append("=?")
                .spaces("and")
                .append(ProductOnShoppingListTable.COLUMN_SHOPPING_LIST_ID).append("=?")
                .toString();
        databaseAccessObject.getDatabase().delete(ProductOnShoppingListTable.TABLE_NAME,
                sql,
                StringUtils.asArray(productId, shoppingListId));
    }
}
