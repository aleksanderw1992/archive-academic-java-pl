package com.aleksanderwojcik.shoppinglist.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.aleksanderwojcik.shoppinglist.R;
import com.aleksanderwojcik.shoppinglist.common.android.BundleKeys;
import com.aleksanderwojcik.shoppinglist.common.exceptions.business.BusinessException;
import com.aleksanderwojcik.shoppinglist.common.utils.Sb;
import com.aleksanderwojcik.shoppinglist.core.service.Service;
import com.aleksanderwojcik.shoppinglist.core.service.impl.ServiceImpl;

/*
 * http://stackoverflow.com/questions/17529766/see-database-filecontent-via-android-studio
C:\Prog_Files\adt-bundle-windows-x86_64-20140702\sdk\platform-tools
adb -s emulator-5554 shell
sqlite3 data/data/com.example.lista4/databases/SHOPPING_LISTS.db

 \sdk\platform-tools
  adb devices
  adb -s emulator-xxxx shell
  cd data/data/<your-package-name>/databases/
  sqlite3 <your-db-name>.db

  .tables
  select * from shopping_lists ;
  select * from products;
  select * from products_on_shopping_list;

  adb kill-server
  adb start-server
 */
public abstract class AbstractActivity extends Activity {
    //    compile 'com.android.support:support-v4:20.+'
    private String tag = this.getClass().getSimpleName();
    protected long shoppingListId;
    protected Service service;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.service.closeDb();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("onCreate");
        setService();
        this.service.openDb();
    }
    @Override
    protected void onStart() {
        super.onStart();
        log("onStart");
        setService();//open
        this.service.openDb();
    }
    @Override
    protected void onStop() {
        super.onStop();
        log("onStop");//close
    }
    private void setService() {
        if (this.service == null)
            this.service = new ServiceImpl(this);
    }
    protected  void log(String text) {
        Log.e(tag, text);
    }
    protected void log(BusinessException e) {
        Sb sb = new Sb();
        sb.appendAndSpace(e.getMessage());
        sb.append(getResources().getString(e.getMessageResourceCode()));
        Log.e(tag, sb.toString());
    }
    protected void setShoppingListId() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            this.toastLong(R.string.gui_empty_bundle_warning);
            return;
        }
        this.shoppingListId = (Long) extras.get(BundleKeys.SHOPPING_LIST_ID);
    }
    protected void toastLong(Integer resourcesKey, String... concatenations) {
        this.toast(resourcesKey, concatenations, Toast.LENGTH_LONG);
    }
    private void toast(Integer resourcesKey, String[] concatenations, int duration) {
        StringBuilder text = new StringBuilder();
        if (resourcesKey != null) {
            text.append(getResources().getString(resourcesKey));
        }
        for (String concatenation : concatenations) {
            text.append(concatenation);
        }
        Toast.makeText(this,
                text.toString(),
                duration).show();
    }
    protected void toastShort(Integer resourcesKey, String... concatenations) {
        this.toast(resourcesKey, concatenations, Toast.LENGTH_SHORT);
    }
    protected void toastLong(BusinessException e) {
        this.toastLong(e.getMessageResourceCode());
    }
    protected void toastShort(String text) {
        this.toast(null, new String[]{text}, Toast.LENGTH_SHORT);
    }
    protected void toastLong(String text) {
        this.toast(null, new String[]{text}, Toast.LENGTH_LONG);
    }
    protected void setBackButton(Button backButton) {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                setResult(RESULT_CANCELED, data);
                finish();
            }
        });
    }
}
