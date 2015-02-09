package com.zrquan.mobile.ui.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.zrquan.mobile.controller.AutoMatchController;
import com.zrquan.mobile.support.util.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                    JSONObject response = AutoMatchController.match("School", constraint.toString());
                    try {
                        LogUtils.d("Response:" + response.toString(4));
                        JSONArray matches = response.getJSONArray("matches");
                        for(int i=0; i< matches.length(); i++) {
                            JSONObject matchObj = matches.getJSONObject(i);
                            mData.add(matchObj.getString("value"));
                        }
                    } catch (JSONException e) {
                        LogUtils.d("ParseJsonError:", e);
                    }
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
