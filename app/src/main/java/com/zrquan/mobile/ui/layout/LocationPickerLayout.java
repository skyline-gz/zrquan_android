package com.zrquan.mobile.ui.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.j256.ormlite.dao.Dao;
import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.model.Location;
import com.zrquan.mobile.model.Region;
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
    //缓存全部可选的location 和region
    private List<Location> locationList;
    private List<Region>  regionList;

    //缓存当前滚动盘中可选的Location和Region,由于Region是不变的，因此regionCacheList 就是regionList
    private List<Location> locationCacheList;
    private List<Region> regionCacheList;

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
        regionPicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                Region currentRegion = regionCacheList.get(id);
                buildLocationPicker(currentRegion.getId());
            }

            @Override
            public void selecting(int id, String text) {

            }
        });
        loadData();
        buildRegionPicker();
        buildLocationPicker(regionCacheList.get(0).getId());
    }

    private void loadData() {
        StringBuilder sb;
        try {
            // get our dao
            Dao<Location, Integer> locationDao = ZrquanApplication.getInstance()
                    .getDatabaseHelper().getDao(Location.class);
            Dao<Region, Integer> regionDao = ZrquanApplication.getInstance()
                    .getDatabaseHelper().getDao(Region.class);
            // query for all of the data objects in the database
            locationList = locationDao.queryForAll();
            locationCacheList = new ArrayList<>();
            regionCacheList = regionList = regionDao.queryForAll();

        } catch (SQLException e) {
            LogUtils.d("Load Location from db crash..", e);
        }
    }

    private void buildRegionPicker() {
        ArrayList<String> regions = new ArrayList<>();
        for(Region region: regionList) {
            regions.add(region.getName());
        }
        regionPicker.setData(regions);
        regionPicker.setDefault(0);
    }

    private void buildLocationPicker(int regionId) {
        ArrayList<String> locations = new ArrayList<>();
        locationCacheList.clear();
        for(Location location: locationList) {
            if(location.getRegionId() == regionId) {
                locations.add(location.getName());
                locationCacheList.add(location);
            }
        }
        locationPicker.setData(locations);
        locationPicker.setDefault(0);
    }
}
