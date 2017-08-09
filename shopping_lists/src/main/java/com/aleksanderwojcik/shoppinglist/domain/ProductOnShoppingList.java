package com.aleksanderwojcik.shoppinglist.domain;

import com.aleksanderwojcik.shoppinglist.common.utils.Sb;

public class ProductOnShoppingList extends Product {
    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return new Sb().append("Product{")
                .equals("id", id)
                .comma()
                .equals("name", name)
                .comma()
                .equals("quantity", quantity)
                .append("}").toString();
    }
    public String description() {
        return name.concat(" (").concat(measure).concat(")");
    }
}