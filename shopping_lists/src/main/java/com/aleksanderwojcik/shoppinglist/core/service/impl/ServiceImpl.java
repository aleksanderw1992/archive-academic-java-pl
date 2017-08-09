package com.aleksanderwojcik.shoppinglist.core.service.impl;

import android.content.Context;
import android.database.Cursor;
import com.aleksanderwojcik.shoppinglist.R;
import com.aleksanderwojcik.shoppinglist.common.exceptions.business.impl.AlreadyExistsException;
import com.aleksanderwojcik.shoppinglist.common.utils.Sb;
import com.aleksanderwojcik.shoppinglist.common.utils.StringUtils;
import com.aleksanderwojcik.shoppinglist.core.dao.impl.ProductDao;
import com.aleksanderwojcik.shoppinglist.core.dao.impl.ShoppingListDao;
import com.aleksanderwojcik.shoppinglist.core.dao.other.ProductOnShoppingListHelper;
import com.aleksanderwojcik.shoppinglist.core.database.DatabaseAccessObject;
import com.aleksanderwojcik.shoppinglist.core.service.Service;
import com.aleksanderwojcik.shoppinglist.core.tables.impl.ProductOnShoppingListTable;
import com.aleksanderwojcik.shoppinglist.core.tables.impl.ProductTable;
import com.aleksanderwojcik.shoppinglist.core.tables.impl.ShoppingListsTable;
import com.aleksanderwojcik.shoppinglist.domain.Product;
import com.aleksanderwojcik.shoppinglist.domain.ProductOnShoppingList;
import com.aleksanderwojcik.shoppinglist.domain.ShoppingList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AXELA on 2014-11-26.
 */
public class ServiceImpl implements Service {
    private DatabaseAccessObject databaseAccessObject;
    private ShoppingListsTable shoppingListsTable;
    private ProductDao productDao;
    private ShoppingListDao shoppingListDao;
    private ProductOnShoppingListHelper productOnShoppingListHelper;

    public ServiceImpl(Context context) {
        this.shoppingListsTable = new ShoppingListsTable();
        ProductTable productTable = new ProductTable();
        ProductOnShoppingListTable productOnShoppingListTable = new ProductOnShoppingListTable();
        this.databaseAccessObject = new DatabaseAccessObject(context, productOnShoppingListTable, productTable,
                shoppingListsTable);
        this.databaseAccessObject.open();

        this.productDao = new ProductDao(databaseAccessObject, productTable);
        this.shoppingListDao = new ShoppingListDao(databaseAccessObject, shoppingListsTable);
        this.productOnShoppingListHelper = new ProductOnShoppingListHelper(databaseAccessObject);
    }
    @Override
    public void openDb() {
        this.databaseAccessObject.open();
    }
    @Override
    public void closeDb() {
        this.databaseAccessObject.close();
    }
    @Override
    public List<Product> getAllProducts() {
        return this.productDao.getAll();
    }
    @Override
    public List<ShoppingList> getAllShoppingLists() {
        return this.shoppingListDao.getAll();
    }
    @Override
    public ShoppingList save(ShoppingList shoppingList) {
        if (shoppingList.getId() == null)
            return this.shoppingListDao.save(shoppingList);
        else
            this.shoppingListDao.update(shoppingList);
        return shoppingList;
    }
    @Override
    @Deprecated
    public void deleteProduct(long id) {
//TODO to support deleting existing products new function
        this.productDao.delete(id);
        throw new UnsupportedOperationException("ServiceImpl : deleteProduct : ");
    }
    @Override
    public List<ProductOnShoppingList> getAllProductsForShoppingListId(long shoppingListId) {
        return this.productOnShoppingListHelper.getAllProductsForShoppingListId(shoppingListId);
    }
    @Override
    public void delete(ShoppingList shoppingList) {
        this.shoppingListDao.delete(shoppingList.getId());
    }
    @Override
    public void deleteFromShoppingList(long productId, long shoppingListId) {
        Sb sb = new Sb();
        sb.append(ProductOnShoppingListTable.COLUMN_PRODUCT_ID).append("=?");
        sb.spaces("and");
        sb.append(ProductOnShoppingListTable.COLUMN_SHOPPING_LIST_ID).append("=?");
        databaseAccessObject.getDatabase().delete(ProductOnShoppingListTable.TABLE_NAME,
                sb.toString(), StringUtils.asArray(productId, shoppingListId));
    }
    @Override
    public void deleteAllFromShoppingList(long shoppingListId) {
        this.productOnShoppingListHelper.deleteAllFromShoppingList(shoppingListId);
    }
    @Override
    public ProductOnShoppingList findProduct(long productId, long shoppingListId) {
        Map<String, String> values = new HashMap<String, String>();
        values.put(ProductOnShoppingListTable.COLUMN_PRODUCT_ID, productId + "");
        values.put(ProductOnShoppingListTable.COLUMN_SHOPPING_LIST_ID, shoppingListId + "");
        String sqlQuery = this.productOnShoppingListHelper.getSqlQuery(values);
        Cursor cursor = this.databaseAccessObject.getDatabase().rawQuery(sqlQuery, null);
        if (cursor.getCount() == 0) return null;
        cursor.moveToFirst();
        ProductOnShoppingList productOnShoppingList = this.productOnShoppingListHelper.fromCursor(cursor);
        cursor.close();
        return productOnShoppingList;
    }
    @Override
    public ShoppingList findShoppingList(String name) {
        Cursor cursor = this.databaseAccessObject.query(shoppingListsTable,
                StringUtils.equalsSing(
                        ShoppingListsTable.COLUMN_NAME, StringUtils.singleQuotation(name)),
                null);
        if (cursor.isAfterLast()) return null;
        cursor.moveToFirst();
        ShoppingList shoppingList = this.shoppingListDao.fromCursor(cursor);
        cursor.close();
        return shoppingList;
    }
    @Override
    public boolean changeProduct(ProductOnShoppingList from, ProductOnShoppingList to, long shoppingListId) throws AlreadyExistsException {
        boolean basicProductHasChanged =
                StringUtils.notEqualsTrim(from.getMeasure(), to.getMeasure()) ||
                        StringUtils.notEqualsTrim(from.getName(), to.getName());
        boolean quantityHasChanged = !(from.getQuantity().equals(to.getQuantity()));
        if (basicProductHasChanged) {
            this.addNewProductToShoppingList(to, shoppingListId);
            this.productOnShoppingListHelper.deleteFromShoppingList(from.getId(), shoppingListId);
        } else {
            if (quantityHasChanged) {
                this.productOnShoppingListHelper.update(to, shoppingListId);
            } else {
                return false;
            }
        }
        return true;
    }
    @Override
    public ProductOnShoppingList addNewProductToShoppingList(ProductOnShoppingList productOnShoppingList, long shoppingListId) throws AlreadyExistsException {
        Long productId = this.productDao.findId(productOnShoppingList);
        productOnShoppingList.setId(productId);
        if (productId != null) {
            boolean alreadyExists = this.productOnShoppingListHelper.alreadyOnShoppingList
                    (productId, shoppingListId);
            if (alreadyExists) {
                throw new AlreadyExistsException("ServiceImpl : addNewProductToShoppingList  ",
                        R.string.gui_exception_product_already_exists);
            } else {
                productOnShoppingListHelper.save(productOnShoppingList, shoppingListId);
            }
        } else {
            Product sourceOfId = this.productDao.save(productOnShoppingList);
            productOnShoppingList.setId(sourceOfId.getId());
            this.productOnShoppingListHelper.save(productOnShoppingList, shoppingListId);
        }
        return productOnShoppingList;
    }

}
