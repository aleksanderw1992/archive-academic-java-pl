package com.aleksanderwojcik.shoppinglist.activities.impl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.aleksanderwojcik.shoppinglist.R;
import com.aleksanderwojcik.shoppinglist.common.android.BundleKeys;
import com.aleksanderwojcik.shoppinglist.domain.ShoppingList;

public class AddNewShoppingListActivity extends com.aleksanderwojcik.shoppinglist.activities.AbstractActivity {
    private EditText nameOfTheShoppingListET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_shopping_list_activity);

        nameOfTheShoppingListET = (EditText) findViewById(R.id.add_new_shopping_list_the_name_et);
        Button addShoppingList = (Button) findViewById(R.id.add_new_shopping_list_add_button);
        Button backButton = (Button) findViewById(R.id.back_from_add_new_shopping_list);

        addShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameOfTheShoppingListET.getText().toString().trim();
                if (name.contains(" ")) {
                    toastLong(R.string.gui_do_not_use_space);
                } else {
                    if (name.length() <= 1) {
                        toastLong(R.string.gui_name_should_contain_two_chars);
                    } else {
                        addList(name);
                    }
                }
            }

            private void addList(String name) {
                if (service.findShoppingList(name) == null) {
                    ShoppingList shoppingList = new ShoppingList();
                    shoppingList.setName(name);
                    shoppingList = service.save(shoppingList);
                    Intent data = new Intent();
                    data.putExtra(BundleKeys.NEW_SHOPPING_LIST_NAME, name);
                    data.putExtra(BundleKeys.NEW_SHOPPING_LIST_ID, shoppingList.getId());
                    setResult(RESULT_OK, data);
                    finish();

                } else {
                    toastLong(R.string.gui_list_already_exists);
                }
            }
        });
        setBackButton(backButton);
    }

}