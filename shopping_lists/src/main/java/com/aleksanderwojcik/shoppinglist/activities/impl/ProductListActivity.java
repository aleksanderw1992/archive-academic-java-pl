package com.aleksanderwojcik.shoppinglist.activities.impl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.aleksanderwojcik.shoppinglist.R;
import com.aleksanderwojcik.shoppinglist.common.android.BundleKeys;
import com.aleksanderwojcik.shoppinglist.common.helpers.ArrayAdapterInflater;
import com.aleksanderwojcik.shoppinglist.common.utils.Sb;
import com.aleksanderwojcik.shoppinglist.common.utils.StringUtils;
import com.aleksanderwojcik.shoppinglist.domain.ProductOnShoppingList;

import java.util.ArrayList;

public class ProductListActivity extends com.aleksanderwojcik.shoppinglist.activities.AbstractActivity {

    private ArrayAdapter<ProductOnShoppingList> adapter;
    private ArrayList<ProductOnShoppingList> productOnShoppingLists;
    private ListView listView;
    private boolean shoppingMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_activity);
        setShoppingListId();
        productOnShoppingLists = new ArrayList<ProductOnShoppingList>();
        updateAdapter();

        Button addNewProductButton = (Button) findViewById(R.id.add_new_product_button);
        addNewProductButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this,
                        AddNewProductActivity.class);
                intent.putExtra(BundleKeys.SHOPPING_LIST_ID, shoppingListId);
                startActivityForResult(intent, RESULT_FIRST_USER);
            }
        });
        Button clearProductListButton = (Button) findViewById(R.id.clear_product_list_button);
        clearProductListButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                service.deleteAllFromShoppingList(shoppingListId);
                adapter.clear();
                adapter.notifyDataSetChanged();
                toastLong(R.string.gui_cleared_lists);

            }
        });
        Button backButton = (Button) findViewById(R.id.back_from_product_list);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductListActivity.this, MainActivity.class));
            }
        });
    }
    private void updateAdapter() {
        if (productOnShoppingLists == null) {
            productOnShoppingLists = new ArrayList<ProductOnShoppingList>();
        }
        if (adapter == null) {
            adapter = new ArrayAdapterInflater<ProductOnShoppingList>(this, R.layout.row_view) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View rowView = convertView;
                    if (rowView == null) {
                        rowView = inflater.inflate(R.layout.row_view, parent, false);
                    }
                    TextView nameTV = (TextView) rowView.findViewById(R.id.textView1);
                    TextView measureTV = (TextView) rowView
                            .findViewById(R.id.textView2);
                    TextView quantityTV = (TextView) rowView.findViewById(R.id.textView3);

                    ProductOnShoppingList productOnShoppingList = getItem(position);
                    nameTV.setText(productOnShoppingList.getName());
                    measureTV.setText(productOnShoppingList.getMeasure());
                    quantityTV.setText(productOnShoppingList.getQuantity().toString());
                    return rowView;
                }
            };
        }
        if (listView == null) {
            listView = (ListView) findViewById(R.id.the_list_of_the_products);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                    if (shoppingMode) {
                        ProductOnShoppingList productOnShoppingList = adapter.getItem(position);
                        id = productOnShoppingList.getId();
                        adapter.remove(productOnShoppingList);
                        adapter.notifyDataSetChanged();
                        service.deleteFromShoppingList(id, shoppingListId);
                    } else {
                        Intent intent = new Intent(ProductListActivity.this, ChangeProductActivity.class);
                        ProductOnShoppingList productOnShoppingList = adapter.getItem(position);
                        id = productOnShoppingList.getId();
                        intent.putExtra(BundleKeys.PRODUCT_ID, id);
                        intent.putExtra(BundleKeys.SHOPPING_LIST_ID, shoppingListId);
                        startActivityForResult(intent, RESULT_FIRST_USER);
                    }
                }
            });
/*            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                    if (shoppingMode) {
                        ProductOnShoppingList productOnShoppingList = adapter.getItem(position);
                        long id = productOnShoppingList.getId();
                        adapter.remove(productOnShoppingList);
                        adapter.notifyDataSetChanged();
                        service.deleteFromShoppingList(id, shoppingListId);
                    } else {
                        toastLong(R.string.gui_you_clicked, "" + (position + 1));
                    }
                }
            });*/
        }
        adapter.clear();
        productOnShoppingLists.clear();
        productOnShoppingLists = (ArrayList<ProductOnShoppingList>) service.getAllProductsForShoppingListId(shoppingListId);
        for (ProductOnShoppingList productOnShoppingList : productOnShoppingLists) {
            adapter.add(productOnShoppingList);
        }
        listView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_product_list, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.if_shopping_mode_menu:

                if (item.isChecked()) {
                    shoppingMode = false;
                    item.setChecked(false);

                } else {
                    shoppingMode = true;
                    item.setChecked(true);
                }
                break;
            case R.id.send_email_menu:
                sendMail();
                break;
            case R.id.send_sms_menu:
                sendSms();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    private void sendSms() {
        /*SmsManager smsManager =     SmsManager.getDefault();
        smsManager.sendTextMessage(null, null,getSmsContent() , null, null);*/
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"));
        intent.putExtra("sms_body", getSmsContent());
        startActivity(intent);
    }
    private void sendMail() {
        String topic=getResources().getString(R.string.email_topic);//.concat(" ").concat()
        String emailContent=getEmailContent();
        if (StringUtils.isEmpty(emailContent)) {
            toastLong(R.string.gui_exception_empty_list_email);
            return;
        }

        Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
        // prompts emailIntent clients only
        emailIntent.setType("message/rfc822");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, topic);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailContent);

        try {
            startActivity(Intent.createChooser(emailIntent, "Choose an emailIntent client from..."));
        } catch (android.content.ActivityNotFoundException ex) {
            String message = getResources().getString(R.string.gui_exception_no_email_client);
            log(message);
            toastLong(message);
        }
    }
    private String getEmailContent() {
        Sb sb = new Sb();
        for (ProductOnShoppingList productOnShoppingList : productOnShoppingLists) {
            String name = productOnShoppingList.getName();
            Integer quantity = productOnShoppingList.getQuantity();
            String measure = productOnShoppingList.getMeasure();
            measure=StringUtils.trimNullToEmpty(measure);
            sb.append(name).space().append(quantity).append(measure).newline();
        }
        return sb.toString();
    }
    private String getSmsContent() {
        Sb sb = new Sb();
        for (ProductOnShoppingList productOnShoppingList : productOnShoppingLists) {
            String name = productOnShoppingList.getName();
            Integer quantity = productOnShoppingList.getQuantity();
            sb.append(name).space().append(quantity).append(",").space();
        }
        return sb.toString();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        log("OnActivityResult ");
        if (resultCode == RESULT_OK && requestCode == RESULT_FIRST_USER) {
            if (data.hasExtra(BundleKeys.PRODUCT_ACTION)) {
                int resourcesKey = data.getExtras().getInt(BundleKeys.PRODUCT_ACTION);
                String description = (String) data.getExtras().get(BundleKeys.PRODUCT_DESCRIPTION);
                toastLong(resourcesKey, StringUtils.trimNullToEmpty(description));
            }
        }
        updateAdapter();
    }
}

