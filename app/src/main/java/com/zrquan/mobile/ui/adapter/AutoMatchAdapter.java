package com.zrquan.mobile.ui.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.zrquan.mobile.controller.AutoMatchController;

import java.util.ArrayList;

public class AutoMatchAdapter extends ArrayAdapter implements Filterable {
    private ArrayList<String> mData;

    //http://stackoverflow.com/questions/5023645/how-do-i-use-autocompletetextview-and-populate-it-with-data-from-a-web-api
    public AutoMatchAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        mData = new ArrayList<>();
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
                    mData.clear();
                    JsonObject response = AutoMatchController.match("School", constraint.toString());
                    JsonArray matches = response.get("matches").getAsJsonArray();
                    for(int i=0; i< matches.size(); i++) {
                        JsonObject matchObj = matches.get(i).getAsJsonObject();
                        mData.add(matchObj.get("value").getAsString());
                    }
                    filterResults.values = mData;
                    filterResults.count = mData.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
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
