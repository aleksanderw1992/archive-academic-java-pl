package com.aleksanderwojcik.shoppinglist.core.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import com.aleksanderwojcik.shoppinglist.common.exceptions.DataIntegrityException;
import com.aleksanderwojcik.shoppinglist.common.helpers.CursorHelper;
import com.aleksanderwojcik.shoppinglist.common.utils.CursorUtils;
import com.aleksanderwojcik.shoppinglist.common.utils.Sb;
import com.aleksanderwojcik.shoppinglist.common.utils.StringUtils;
import com.aleksanderwojcik.shoppinglist.core.dao.Dao;
import com.aleksanderwojcik.shoppinglist.core.database.DatabaseAccessObject;
import com.aleksanderwojcik.shoppinglist.core.tables.SqlTable;
import com.aleksanderwojcik.shoppinglist.core.tables.impl.ProductTable;
import com.aleksanderwojcik.shoppinglist.domain.Product;

public class ProductDao extends Dao<Product> {

    public ProductDao(DatabaseAccessObject databaseAccessObject, SqlTable sqlTable) {
        super(databaseAccessObject, sqlTable);
    }
    public Product save(Product productOnShoppingList) {
        String measure = productOnShoppingList.getMeasure();
        String name = productOnShoppingList.getName();
        ContentValues values = new ContentValues();
        values.put(ProductTable.COLUMN_NAME, name);
        values.put(ProductTable.COLUMN_MEASURE, measure);
        long insertId = database.insert(ProductTable.TABLE_NAME, null,
                values);
        Cursor cursor = databaseAccessObject.query(sqlTable,
                ProductTable.COLUMN_ID.concat(" =? "),
                StringUtils.asArray(insertId));
        cursor.moveToFirst();
        Product newProduct = fromCursor(cursor);
        cursor.close();
        return newProduct;
    }

    public Product fromCursor(Cursor cursor) {

        CursorHelper helper = new CursorHelper(cursor);
        Product product = new Product();

        product.setId(helper.getLong(ProductTable.COLUMN_ID));
        product.setName(helper.getString(ProductTable.COLUMN_NAME));
        product.setMeasure(helper.getString(ProductTable.COLUMN_MEASURE));
        return product;
    }

    public void update(Product productOnShoppingList) {
        Long id = productOnShoppingList.getId();
        if (id == null) {
            throw new RuntimeException("ProductDao : update : idIsNull");
        }
        String name = productOnShoppingList.getName();
        String measure = productOnShoppingList.getMeasure();

        ContentValues cv = new ContentValues();
        cv.put(ProductTable.COLUMN_NAME, name);
        cv.put(ProductTable.COLUMN_MEASURE, measure);

        database.update(ProductTable.TABLE_NAME, cv, ProductTable.COLUMN_ID + " = ?",
                StringUtils.asArray(id));
    }

    @Override
    public Long findId(Product product) {
        Sb sb = new Sb();
        sb.equals(ProductTable.COLUMN_NAME, "?")
                .spaces("and")
                .equals(ProductTable.COLUMN_MEASURE, "?");
        Cursor cursor = databaseAccessObject.query(sqlTable,
                sb.toString(),
                StringUtils.asArray(product.getName(), product.getMeasure()));
        int count = cursor.getCount();
        cursor.moveToFirst();
        switch (count) {
            case 0:
                cursor.close();
                return null;
            case 1:
                return CursorUtils.getLongClose(cursor, ProductTable.COLUMN_ID);
            default:
                throw new DataIntegrityException("ProductDao : findId : should be unique" + product.toString());
        }
    }

}