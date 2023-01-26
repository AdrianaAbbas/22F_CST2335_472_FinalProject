package com.adya.guardiannewsapp;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DrawerItemAdapter extends ArrayAdapter<String> {

    Context mContext;
    int layoutResourceId;
    String[] data;

    public DrawerItemAdapter(Context mContext, int layoutResourceId, String[] data) {
        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = ((Activity) mContext).getLayoutInflater().inflate(layoutResourceId, parent, false);
        TextView textViewName = listItem.findViewById(R.id.textViewName);
        textViewName.setText(data[position]);
        return listItem;
    }
}
