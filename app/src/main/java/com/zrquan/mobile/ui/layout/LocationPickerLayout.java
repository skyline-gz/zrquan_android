package com.zrquan.mobile.ui.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.zrquan.mobile.widget.picker.ScrollerNumberPicker;
import com.zrquan.mobile.R;

import java.util.ArrayList;

public class LocationPickerLayout extends LinearLayout {

    /** 滑动控件 */
    private ScrollerNumberPicker regionPicker;
    private ScrollerNumberPicker locationPicker;

    private Context context;

    public LocationPickerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public LocationPickerLayout(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.picker_location, this);

        regionPicker = (ScrollerNumberPicker) findViewById(R.id.region);
        locationPicker = (ScrollerNumberPicker) findViewById(R.id.location);

        ArrayList<String> regions = new ArrayList<>();
        regions.add("广东");
        regions.add("四川");
        regionPicker.setData(regions);
        regionPicker.setDefault(0);

        ArrayList<String> locations = new ArrayList<>();
        locations.add("广州");
        locations.add("成都");
        locationPicker.setData(locations);
        locationPicker.setDefault(0);
    }
}
