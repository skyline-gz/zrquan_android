package com.zrquan.mobile.ui.feed;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zrquan.mobile.R;
import com.viewpagerindicator.CirclePageIndicator;

import com.zrquan.mobile.widget.viewpager.AutoScrollViewPager;
import com.zrquan.mobile.widget.viewpager.ImagePagerAdapter;

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

                Animation slideUpAndFadeOut = AnimationUtils.loadAnimation(context, R.anim.anim_slide_up);

                slideUpAndFadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // Called when the Animation starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Called when the Animation ended
                        // Since we are fading a View out we set the visibility
                        // to GONE once the Animation is finished
                        rlBanner.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // This is called each time the Animation repeats
                    }
                });

                rlBanner.startAnimation(slideUpAndFadeOut);
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
