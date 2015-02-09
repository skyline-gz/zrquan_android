package com.zrquan.mobile.ui.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

public class AutoMatchAdapter extends ArrayAdapter implements Filterable {
    private ArrayList mData ;

    public AutoMatchAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        mData = new ArrayList();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int index) {
        return mData.get(index);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new Filter.FilterResults();
                if(constraint != null) {
                    // A class that queries a web API, parses the data and returns an ArrayList<Style>
//                    StyleFetcher fetcher = new StyleFetcher();
//                    try {
//                        mData = fetcher.retrieveResults(constraint.toString());
//                    }
//                    catch(Exception e) {
//                        LogUtils.d("myException", e.getMessage());
//                    }
                    // Now assign the values and count to the FilterResults object
                    mData = new ArrayList();
                    mData.add("你好");
                    mData.add("我们");
                    mData.add("他们");
                    filterResults.values = mData;
                    filterResults.count = mData.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence contraint, FilterResults results) {
                if(results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
