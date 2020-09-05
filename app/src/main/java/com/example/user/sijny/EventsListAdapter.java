package com.example.user.sijny;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class EventsListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Events> mProductList;

    //Constructor

    public EventsListAdapter(Context mContext, List<Events> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.simple_list_item_2, null);
        TextView tvTitle = (TextView)v.findViewById(R.id.title2);
        //Set text for TextView
        tvTitle.setText(mProductList.get(position).getName());

        return v;
    }
}
