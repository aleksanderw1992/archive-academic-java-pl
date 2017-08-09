package com.aleksanderwojcik.shoppinglist.activities.impl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.aleksanderwojcik.shoppinglist.R;
import com.aleksanderwojcik.shoppinglist.common.android.BundleKeys;
import com.aleksanderwojcik.shoppinglist.common.exceptions.business.impl.AlreadyExistsException;
import com.aleksanderwojcik.shoppinglist.common.utils.StringUtils;
import com.aleksanderwojcik.shoppinglist.domain.ProductOnShoppingList;

public class ChangeProductActivity extends com.aleksanderwojcik.shoppinglist.activities.AbstractActivity {
    private EditText nameET;
    private EditText measureET;
    private EditText quantityET;

    private long productId;
    private ProductOnShoppingList productOnShoppingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_product_activity);
        setShoppingListId();
        Bundle extras = getIntent().getExtras();
        productId = extras.getLong(BundleKeys.PRODUCT_ID);

        nameET = (EditText) findViewById(R.id.change_product_name_et);
        measureET = (EditText) findViewById(R.id.change_product_measute_et);
        quantityET = (EditText) findViewById(R.id.change_product_quantity_et);

        log("productId=".concat("" + productId).concat("; shoppingListId=").concat("" + shoppingListId));
        this.productOnShoppingList = service.findProduct(productId, shoppingListId);

        nameET.setText(this.productOnShoppingList.getName());
        measureET.setText(this.productOnShoppingList.getMeasure());
        quantityET.setText(this.productOnShoppingList.getQuantity() + "");

        Button editProductButton = (Button) findViewById(R.id.change_product_edit_label_button);
        Button deleteProductButton = (Button) findViewById(R.id.change_product_delete_label_button);
        Button backButton = (Button) findViewById(R.id.back_from_change_product_activity);

        editProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishEditProduct();
            }
        });
        deleteProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishDeleteProduct();
            }
        });
        setBackButton(backButton);
    }

    protected void finishEditProduct() {
        Intent data = new Intent();
        String measure = measureET.getText().toString();
        String name = nameET.getText().toString();
        String quantity = quantityET.getText().toString();

        if (StringUtils.isEmpty(measure)
                || StringUtils.isEmpty(name)
                || StringUtils.isEmpty(quantity)) {
            toastShort(R.string.gui_fill_in_all_fields);
            return;
        }

        data.putExtra(BundleKeys.PRODUCT_ACTION, R.string.gui_product_action_changed);
        data.putExtra(BundleKeys.PRODUCT_DESCRIPTION, name.concat(" (").concat(quantity).concat(")"));
        try {
            int quantityInt = Integer.parseInt(quantity);
            ProductOnShoppingList productOnShoppingListAfterEdit = new ProductOnShoppingList();
            productOnShoppingListAfterEdit.setQuantity(quantityInt);
            productOnShoppingListAfterEdit.setMeasure(measure);
            productOnShoppingListAfterEdit.setName(name);
            productOnShoppingListAfterEdit.setId(productId);
            boolean productHasBeenChanged = service.changeProduct(this.productOnShoppingList, productOnShoppingListAfterEdit, shoppingListId);
            if (!productHasBeenChanged) {
                toastLong(R.string.gui_no_changes);
                return;
            }
            setResult(RESULT_OK, data);
            super.finish();
        } catch (NumberFormatException e) {
            toastShort(R.string.gui_incorrect_quantity);
        } catch (AlreadyExistsException e) {
            log(e);
            toastLong(e);
        }
    }

    protected void finishDeleteProduct() {
        Intent data = new Intent();
        String name = productOnShoppingList.getName();
        data.putExtra(BundleKeys.PRODUCT_ACTION, R.string.gui_product_action_deleted);
        data.putExtra(BundleKeys.PRODUCT_DESCRIPTION, name);
        service.deleteFromShoppingList(productId, shoppingListId);
        setResult(RESULT_OK, data);
        super.finish();
    }

}