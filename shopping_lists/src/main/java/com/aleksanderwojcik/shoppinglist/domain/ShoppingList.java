package com.aleksanderwojcik.shoppinglist.domain;

import java.util.List;

public class ShoppingList {
    private Long id;
    private String name;
    private List<ProductOnShoppingList> products;

    public Long getId() {
        return id;
    }
    public void setId(Long
                              id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return new StringBuilder().append("ShoppingList{").append("id=").append(id).append(", name='").append(name).append('\'').append('}').toString();
    }
    public List<ProductOnShoppingList> getProducts() {
        return products;
    }
    public void setProducts(List<ProductOnShoppingList> products) {
        this.products = products;
    }
}
