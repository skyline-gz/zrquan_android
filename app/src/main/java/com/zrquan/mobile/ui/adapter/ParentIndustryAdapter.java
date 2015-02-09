package com.zrquan.mobile.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zrquan.mobile.model.Industry;

import java.util.List;

public class ParentIndustryAdapter extends BaseAdapter{
    private Context context;
    private List<Industry> list;

    public ParentIndustryAdapter(Context context, List<Industry> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (this.list != null)
            return this.list.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (position < this.list.size())
            return this.list.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0L;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView t = new TextView(context);
//        if(convertView == null) {
//
//        }
        Industry industry = this.list.get(position);
        t.setText(industry.getName());

        return t;
    }
}
