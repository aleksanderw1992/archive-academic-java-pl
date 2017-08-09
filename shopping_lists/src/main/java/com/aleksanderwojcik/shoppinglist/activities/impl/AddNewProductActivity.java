package com.aleksanderwojcik.shoppinglist.activities.impl;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.aleksanderwojcik.shoppinglist.R;
import com.aleksanderwojcik.shoppinglist.common.android.BundleKeys;
import com.aleksanderwojcik.shoppinglist.common.exceptions.business.impl.AlreadyExistsException;
import com.aleksanderwojcik.shoppinglist.common.utils.StringUtils;
import com.aleksanderwojcik.shoppinglist.domain.ProductOnShoppingList;

public class AddNewProductActivity extends com.aleksanderwojcik.shoppinglist.activities.AbstractActivity {

    private EditText nameET;
    private EditText measureET;
    private EditText quantityET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_product_activity);
        setShoppingListId();
        nameET = (EditText) findViewById(R.id.add_new_product_name_et);
        measureET = (EditText) findViewById(R.id.add_new_product_measure_et);
        quantityET = (EditText) findViewById(R.id.add_new_product_quantity_et);

        Button addNewProductButton = (Button) findViewById(R.id.add_new_product_button);
        Button backButton = (Button) findViewById(R.id.back_from_add_new_product);
        Button useExistingProductButton = (Button) findViewById(R.id.use_existing_product_button);

        addNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res = getResources();
                String measureString = measureET.getText().toString();
                String nameString = nameET.getText().toString();
                String quantityString = quantityET.getText().toString();
                if (measureString.equals(res.getString(R.string.product_measure))
                        && nameString.equals(res.getString(R.string.product_name))
                        && quantityString.equals(res.getString(R.string.default_quantity))) {
                    toastShort(R.string.gui_need_edit_at_least_one_field);
                    return;
                }
                if (StringUtils.isEmpty(nameString)
                        || StringUtils.isEmpty(quantityString)) {
                    toastShort(R.string.gui_fill_in_all_fields);
                    return;
                }
                try {
                    quantityString = quantityString.trim();
                    ProductOnShoppingList productOnShoppingList = new ProductOnShoppingList();
                    productOnShoppingList.setQuantity(Integer.valueOf(quantityString));
                    productOnShoppingList.setMeasure(measureString);
                    productOnShoppingList.setName(nameString);

                    productOnShoppingList = service.addNewProductToShoppingList(productOnShoppingList, shoppingListId);
                    Intent data = new Intent();
                    data.putExtra(BundleKeys.PRODUCT_ACTION,
                            R.string.gui_product_action_added);
                    data.putExtra(BundleKeys.PRODUCT_DESCRIPTION,
                            productOnShoppingList.description());
                    setResult(RESULT_OK, data);
                    finish();

                } catch (NumberFormatException e) {
                    toastShort(R.string.gui_incorrect_quantity);
                } catch (AlreadyExistsException e) {
                    log(e);
                    toastLong(e);
                }
            }

        });
        setBackButton(backButton);
        useExistingProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewProductActivity.this,
                        UseExistingProductActivity.class);
                intent.putExtra(BundleKeys.SHOPPING_LIST_ID, shoppingListId);
                startActivityForResult(intent, RESULT_FIRST_USER);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RESULT_FIRST_USER) {
            if (data.hasExtra(BundleKeys.PRODUCT_MEASURE)
                    && data.hasExtra(BundleKeys.PRODUCT_NAME)) {
                Bundle extras = data.getExtras();
                String name = extras
                        .getString(BundleKeys.PRODUCT_NAME);
                String measure = extras
                        .getString(BundleKeys.PRODUCT_MEASURE);
                nameET.setText(name);
                measureET.setText(measure);
                quantityET.setText(getResources().getString(R.string.default_quantity));
            }
        }
    }

}