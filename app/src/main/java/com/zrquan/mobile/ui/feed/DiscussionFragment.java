package com.zrquan.mobile.ui.feed;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zrquan.mobile.R;
import com.viewpagerindicator.CirclePageIndicator;
import com.zrquan.mobile.ui.viewpager.AutoScrollViewPager;
import com.zrquan.mobile.ui.viewpager.ImagePagerAdapter;

import java.util.ArrayList;
import java.util.List;

//轻讨论 动态
public class DiscussionFragment extends Fragment {
    private Context context;
    private AutoScrollViewPager vpBanner;
    private CirclePageIndicator   indicatorBanner;
    private ImageView ivCancelBanner;
    private RelativeLayout rlBanner;

    private int                   index;
//    private ScrollControlReceiver scrollControlReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();

        View v = inflater.inflate(R.layout.fragment_discussion, container, false);
        rlBanner = (RelativeLayout)v.findViewById(R.id.layout_banner);
        vpBanner = (AutoScrollViewPager)v.findViewById(R.id.view_pager_banner);
        indicatorBanner = (CirclePageIndicator)v.findViewById(R.id.indicator_banner);
        ivCancelBanner = (ImageView)v.findViewById(R.id.image_view_cancel_banner);

        List<Integer> imageIdList = new ArrayList<Integer>();
        imageIdList.add(R.drawable.banner1);
        imageIdList.add(R.drawable.banner2);
        imageIdList.add(R.drawable.banner3);
        imageIdList.add(R.drawable.banner4);
        vpBanner.setAdapter(new ImagePagerAdapter(context, imageIdList));
        indicatorBanner.setViewPager(vpBanner);

        ivCancelBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlBanner.setVisibility(View.GONE);
            }
        });

        vpBanner.setInterval(2000);
//        viewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);

        Bundle bundle = getArguments();
//        if (bundle != null) {
//            index = bundle.getInt(AutoScrollViewPagerInnerDemo.EXTRA_INDEX);
//            if (index == AutoScrollViewPagerInnerDemo.DEFAULT_INDEX) {
        //Todo:切换页面时，停止播放
        vpBanner.startAutoScroll();
//            }
//        }

        return v;
    }
}
