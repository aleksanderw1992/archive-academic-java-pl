package com.aleksanderwojcik.shoppinglist.activities.impl;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.aleksanderwojcik.shoppinglist.R;
import com.aleksanderwojcik.shoppinglist.common.android.BundleKeys;
import com.aleksanderwojcik.shoppinglist.common.helpers.ArrayAdapterInflater;
import com.aleksanderwojcik.shoppinglist.common.helpers.OnClickListenerWithObject;
import com.aleksanderwojcik.shoppinglist.common.utils.StringUtils;
import com.aleksanderwojcik.shoppinglist.domain.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends com.aleksanderwojcik.shoppinglist.activities.AbstractActivity {
    private ListView listView;
    private ArrayList<ShoppingList> shoppingLists;
    private ArrayAdapter<ShoppingList> adapter;
    private boolean deletingShoppingListMode = false;
    private String defaultShoppingListName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        defaultShoppingListName = getResources().getString(R.string.default_list_name);

        Button addListButton = (Button) findViewById(R.id.add_new_shopping_list_button);

        addListButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!deletingShoppingListMode) {
                    Intent intent = new Intent(MainActivity.this, AddNewShoppingListActivity.class);
                    startActivityForResult(intent, RESULT_FIRST_USER);
                } else {
                    toastLong(R.string.gui_cannot_delete_this_button);
                }
            }
        });
        Button toDefaultList = (Button) findViewById(R.id.to_default_shopping_list_button);
        ShoppingList shoppingList = getDefaultShoppingList();
        toDefaultList.setOnClickListener(new GoToProductListOnShoppingListAdapter(shoppingList));
        listView = (ListView) findViewById(R.id.the_list_of_shopping_lists);
//        reloadShoppingListsAndAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        reloadShoppingListsAndAdapter();
    }
    private void reloadShoppingListsAndAdapter() {
        shoppingLists = new ArrayList<ShoppingList>();
        adapter = new ArrayAdapterInflater<ShoppingList>(this, R.layout.row_view_shopping_list) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View rowView = convertView;
                if (rowView == null) {
                    rowView = inflater.inflate(R.layout.row_view_shopping_list, parent,
                            false);
                }
                ShoppingList shoppingList = getItem(position);
                Button buttonRowView = (Button) rowView.findViewById(R.id.row_view_shopping_list_default);
                buttonRowView.setOnClickListener(new GoToProductListOnShoppingListAdapter(shoppingList));
                buttonRowView.setText(shoppingList.getName());
                return rowView;
            }
        };
        loadShoppingLists();
        updateAdapter();
    }
    private void loadShoppingLists() {
        List<ShoppingList> allShoppingLists = service.getAllShoppingLists();
        for (ShoppingList shoppingList : allShoppingLists) {
            if (!shoppingList.getName().equals(defaultShoppingListName))//TODO replace with query
                shoppingLists.add(shoppingList);
        }
    }
    private void updateAdapter() {
        adapter.clear();
        for (ShoppingList shoppingList : shoppingLists) adapter.add(shoppingList);
        listView.setAdapter(adapter);
    }
    private ShoppingList getDefaultShoppingList() {
        ShoppingList shoppingList = service.findShoppingList(defaultShoppingListName);
        if (shoppingList == null) {
            shoppingList = new ShoppingList();
            shoppingList.setName(defaultShoppingListName);
            shoppingList = service.save(shoppingList);
        }
        return shoppingList;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.if_remove_shopping_list_menu:
                if (item.isChecked()) {
                    deletingShoppingListMode = false;
                    item.setChecked(false);
                } else {
                    deletingShoppingListMode = true;
                    item.setChecked(true);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_FIRST_USER && resultCode == RESULT_OK) {
            if (data.getExtras() != null) {
                String name = data.getStringExtra(BundleKeys.NEW_SHOPPING_LIST_NAME);
                Long newId = (Long) data.getExtras().get(BundleKeys.NEW_SHOPPING_LIST_ID);
                log(StringUtils.equalsSing("name", name).concat(", ").concat(StringUtils.equalsSing("newId", newId)));
                if (name != null) {
                    ShoppingList shoppingList = new ShoppingList();
                    shoppingList.setId(newId);
                    shoppingList.setName(name);
                    shoppingLists.add(shoppingList);
                    updateAdapter();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private class GoToProductListOnShoppingListAdapter extends OnClickListenerWithObject<ShoppingList> {
        public GoToProductListOnShoppingListAdapter(ShoppingList shoppingList) {
            super(shoppingList);
        }

        @Override
        public void onClick(View v) {
            if (t.getName().equals(defaultShoppingListName) && deletingShoppingListMode) {
                toastLong(R.string.gui_cannot_delete_default_list);
                return;
            }
            if (deletingShoppingListMode) {
                adapter.remove(t);
                service.deleteAllFromShoppingList(t.getId());
                service.delete(t);
                adapter.notifyDataSetChanged();
            } else {
                Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                intent.putExtra(BundleKeys.SHOPPING_LIST_ID, t.getId());
                startActivity(intent);
            }

        }
    }

}
