package com.example.user.sijny;

import android.content.Context;
import android.graphics.Color;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SectionListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Section> mProductList;

    //Constructor

    public SectionListAdapter(Context mContext, List<Section> mProductList) {
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
        View v = View.inflate(mContext, R.layout.simple_list_item_1, null);
        TextView tvTitle = (TextView)v.findViewById(R.id.title);
        TextView tvDescription = (TextView)v.findViewById(R.id.tv_description);

        //Set text for TextView
        tvTitle.setText(mProductList.get(position).getName());
        tvDescription.setText(mProductList.get(position).getDescription());

        tvDescription.setMovementMethod(LinkMovementMethod.getInstance());
        tvDescription.setHighlightColor(Color.TRANSPARENT);



        return v;
    }
}
