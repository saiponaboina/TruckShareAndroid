package com.ritul.truckshare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ritul.truckshare.Pojo.Truckothertype;
import com.ritul.truckshare.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vipul Mangukiya on 14-Feb-16.
 */
public class Trucktype extends BaseAdapter {

    private Context activity;
    private List<Truckothertype> data;
    LayoutInflater inflater;

    public Trucktype(
            Context activitySpinner,
            List<Truckothertype> objects) {

        this.activity = activitySpinner;
        this.data = objects;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Truckothertype getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.indexOf(data.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_row, parent, false);
        TextView label = (TextView) row.findViewById(R.id.textView2);
        label.setText(data.get(position).getTruckTypeName().toString());
        return row;
    }
}