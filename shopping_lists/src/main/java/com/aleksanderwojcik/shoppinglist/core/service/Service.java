package com.aleksanderwojcik.shoppinglist.core.service;

import com.aleksanderwojcik.shoppinglist.common.exceptions.business.impl.AlreadyExistsException;
import com.aleksanderwojcik.shoppinglist.domain.Product;
import com.aleksanderwojcik.shoppinglist.domain.ProductOnShoppingList;
import com.aleksanderwojcik.shoppinglist.domain.ShoppingList;

import java.util.List;

/**
 * Created by AXELA on 2014-11-26.
 */
public interface Service {
    public void openDb();
    public void closeDb();
    public List<Product> getAllProducts();
    public List<ShoppingList> getAllShoppingLists();
    public ShoppingList save(ShoppingList shoppingList);
    @Deprecated
    public void deleteProduct(long id);
    public List<ProductOnShoppingList> getAllProductsForShoppingListId(long id);
    public void delete(ShoppingList shoppingList);
    public void deleteFromShoppingList(long productId, long shoppingListId);
    public void deleteAllFromShoppingList(long shoppingListId);
    public ProductOnShoppingList findProduct(long productId, long shoppingListId);
    public ShoppingList findShoppingList(String name);
    boolean changeProduct(ProductOnShoppingList from, ProductOnShoppingList to, long shoppingListId) throws AlreadyExistsException;
    ProductOnShoppingList addNewProductToShoppingList(ProductOnShoppingList productOnShoppingList, long shoppingListId) throws AlreadyExistsException;
}
