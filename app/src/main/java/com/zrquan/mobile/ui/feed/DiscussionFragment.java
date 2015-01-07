package com.zrquan.mobile.ui.feed;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zrquan.mobile.R;
import com.viewpagerindicator.CirclePageIndicator;
import com.zrquan.mobile.ui.viewpager.AutoScrollViewPager;
import com.zrquan.mobile.ui.viewpager.ImagePagerAdapter;

import java.util.ArrayList;
import java.util.List;

//轻讨论 动态
public class DiscussionFragment extends Fragment {
    private Context context;
    private AutoScrollViewPager viewPager;
    private CirclePageIndicator   indicator;

    private int                   index;
//    private ScrollControlReceiver scrollControlReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();

        View v = inflater.inflate(R.layout.fragment_discussion, container, false);
        viewPager = (AutoScrollViewPager)v.findViewById(R.id.view_pager);
        indicator = (CirclePageIndicator)v.findViewById(R.id.indicator);

        List<Integer> imageIdList = new ArrayList<Integer>();
        imageIdList.add(R.drawable.banner1);
        imageIdList.add(R.drawable.banner2);
        imageIdList.add(R.drawable.banner3);
        imageIdList.add(R.drawable.banner4);
        viewPager.setAdapter(new ImagePagerAdapter(context, imageIdList));
        indicator.setViewPager(viewPager);

        viewPager.setInterval(2000);
//        viewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);

        Bundle bundle = getArguments();
//        if (bundle != null) {
//            index = bundle.getInt(AutoScrollViewPagerInnerDemo.EXTRA_INDEX);
//            if (index == AutoScrollViewPagerInnerDemo.DEFAULT_INDEX) {
        //Todo:切换页面时，停止播放
                viewPager.startAutoScroll();
//            }
//        }

        return v;
    }
}
