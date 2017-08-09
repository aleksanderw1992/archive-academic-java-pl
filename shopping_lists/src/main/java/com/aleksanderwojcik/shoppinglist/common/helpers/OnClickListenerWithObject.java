package com.aleksanderwojcik.shoppinglist.common.helpers;

import android.view.View;

/**
 * Created by AXELA on 2014-12-04.
 */
public abstract class OnClickListenerWithObject<T> implements View.OnClickListener {
    protected T t;
    protected OnClickListenerWithObject(T t) {
        this.t = t;
    }
}
