package com.zrquan.mobile.ui.feed;

import java.util.ArrayList;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zrquan.mobile.ui.common.CommonFragment;

public class FeedPagerAdapter extends FragmentPagerAdapter {

    ArrayList<CommonFragment> list;

    public FeedPagerAdapter(FragmentManager fm, ArrayList<CommonFragment> list) {
        super(fm);
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CommonFragment getItem(int arg0) {
        return list.get(arg0);
    }

}