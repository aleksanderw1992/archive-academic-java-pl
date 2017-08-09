package com.aleksanderwojcik.shoppinglist.common.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by AXELA on 2014-12-04.
 */
public abstract class ArrayAdapterInflater<T> extends ArrayAdapter<T> {
    protected LayoutInflater inflater;
    protected int resource;

    public ArrayAdapterInflater(Context context, int resource) {
        super(context, resource);
        this.resource = resource;
        this.inflater = (LayoutInflater) (getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }
    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
