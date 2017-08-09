package com.aleksanderwojcik.shoppinglist.activities.impl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.aleksanderwojcik.shoppinglist.R;
import com.aleksanderwojcik.shoppinglist.common.android.BundleKeys;
import com.aleksanderwojcik.shoppinglist.common.helpers.ArrayAdapterInflater;
import com.aleksanderwojcik.shoppinglist.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class UseExistingProductActivity extends com.aleksanderwojcik.shoppinglist.activities.AbstractActivity {
    private ArrayAdapter<Product> adapter;
    private ArrayList<Product> existingProducts;
    private ListView listView;
    //TODO add deleting products function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_existing_product_activity);
        Button backButton = (Button) findViewById(R.id.back_from_use_existing);
        setBackButton(backButton);

        existingProducts = new ArrayList<Product>();
        adapter = new ArrayAdapterInflater<Product>(this, R.layout.row_view_use_existing_product) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View rowView = convertView;
                if (rowView == null) {
                    rowView = inflater.inflate(this.resource, parent, false);
                }
                Product product =  getItem(position);
                if (product == null) {
                    log("product is null");
                }
                TextView nameTV = (TextView) rowView.findViewById(R.id.row_view_use_existing_product_name);
                TextView measureTV = (TextView) rowView.findViewById(R.id.row_view_use_existing_product_quantity_);

                nameTV.setText(product.getName());
                measureTV.setText(product.getMeasure());

                return rowView;
            }
        };
        listView = (ListView) findViewById(R.id.the_list_of_existing_products);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

                Product product = adapter.getItem(position);
                String name = product.getName();
                String measure = product.getMeasure();
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.PRODUCT_NAME,
                        name);
                intent.putExtra(BundleKeys.PRODUCT_MEASURE,
                        measure);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
        fillListWithExistingProducts();
        pinListToAdapter();

    }
    private void fillListWithExistingProducts() {
        List<Product> products = service.getAllProducts();
        if (products == null) log("List is empty");
        for (Product product : products) existingProducts.add(product);
    }
    private void pinListToAdapter() {
        adapter.clear();
        for (Product product : existingProducts) {
            adapter.add(product);
            listView.setAdapter(adapter);

        }
    }

}