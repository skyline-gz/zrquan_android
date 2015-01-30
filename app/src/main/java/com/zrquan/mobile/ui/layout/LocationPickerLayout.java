package com.zrquan.mobile.ui.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.j256.ormlite.dao.Dao;
import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.modal.Location;
import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.widget.picker.ScrollerNumberPicker;
import com.zrquan.mobile.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationPickerLayout extends LinearLayout {
    private static String LOG_TAG = "LocationPickerLayout";

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
        testDao();
    }

    private void testDao() {
        try {
            // get our dao
            Dao<Location, Integer> locationDao = ZrquanApplication.getInstance()
                    .getDatabaseHelper().getDao(Location.class);
            // query for all of the data objects in the database
            List<Location> list = locationDao.queryForAll();
            // our string builder for building the content-view
            StringBuilder sb = new StringBuilder();
            sb.append("location: got ").append(list.size()).append("\n");
            LogUtils.d(LOG_TAG, sb.toString());
        } catch (SQLException e) {
            LogUtils.d("Load Location from db crash..", e);
        }
    }
}
