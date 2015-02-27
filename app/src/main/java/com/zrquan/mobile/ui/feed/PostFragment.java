package com.zrquan.mobile.ui.feed;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zrquan.mobile.R;
import com.viewpagerindicator.CirclePageIndicator;

import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.adapter.PostFeedAdapter;
import com.zrquan.mobile.controller.PostController;
import com.zrquan.mobile.event.Post.PullDownEvent;
import com.zrquan.mobile.event.Post.PullUpEvent;
import com.zrquan.mobile.model.Account;
import com.zrquan.mobile.model.PostFeed;
import com.zrquan.mobile.support.enums.SORT_TYPE;
import com.zrquan.mobile.support.util.ScreenUtils;
import com.zrquan.mobile.ui.common.CommonFragment;
import com.zrquan.mobile.widget.pulltorefresh.PullToRefreshBase;
import com.zrquan.mobile.widget.pulltorefresh.PullToRefreshListView;
import com.zrquan.mobile.widget.viewpager.AutoScrollViewPager;
import com.zrquan.mobile.widget.viewpager.ImagePagerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;

//轻讨论 动态
public class PostFragment extends CommonFragment {
    private Context context;
    private AutoScrollViewPager vpBanner;
    private CirclePageIndicator indicatorBanner;
    private ImageView ivCancelBanner;
    private RelativeLayout rlBanner;

    private Integer[] postIds;
    private int pullUpCounter = 0;

    private ListView postListView;
    private Parcelable postListViewState;                   //用于保存ListView状态
    private PullToRefreshListView pullListView;
    private PostFeedAdapter postFeedAdapter;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    private List<PostFeed> postList;

    private int index;
//    private ScrollControlReceiver scrollControlReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        if (pullListView == null) {
            context = getActivity().getApplicationContext();

            // 设置pullListView属性
            pullListView = new PullToRefreshListView(context);
            pullListView.setBackgroundColor(getResources().getColor(R.color.main_feed_background_color));
            pullListView.setPullLoadEnabled(false);
            pullListView.setScrollLoadEnabled(true);

            // 触发事件，抽取数据
            PostController.getIdsAndInitialList(1, SORT_TYPE.DEFAULT.value());
            // 获得实际的ListView
            postListView = pullListView.getRefreshableView();

            // 根据手册，addHeaderView要在setAdapter之前
            // 轮播banner
            View bannerView = getBannerView(inflater, container);
            // http://stackoverflow.com/questions/4393775/android-classcastexception-when-adding-a-header-view-to-expandablelistview
            bannerView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,
                    ListView.LayoutParams.WRAP_CONTENT));
            postListView.addHeaderView(bannerView);
            postListView.setDivider(null);
            postListView.setDividerHeight((int) ScreenUtils.dpToPx(context, 6.0f));
            postListView.setSelector(android.R.color.transparent);
            postListView.setCacheColorHint(Color.TRANSPARENT);

            postFeedAdapter = new PostFeedAdapter(context, postList);
            postListView.setAdapter(postFeedAdapter);

            postListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                    String text = postList.get(position) + ", index = " + (position + 1);
                    Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                }
            });
            pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                    Account account = ZrquanApplication.getInstance().getAccount();
                    if (account != null && account.getId() != null) {
                        PostController.getIdsAndInitialList(account.getId(), SORT_TYPE.DEFAULT.value());
                    } else {
                        postFeedAdapter.notifyDataSetChanged();
                        pullListView.onPullDownRefreshComplete();
                    }
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    // 如果已经上拉到24次，不再刷新数据
                    if (pullUpCounter < 24) {
                        pullUpCounter++;
                        Account account = ZrquanApplication.getInstance().getAccount();
                        if (account != null && account.getId() != null) {
                            Integer[] partialIds = Arrays.copyOfRange(
                                    postIds, pullUpCounter * 20, pullUpCounter * 20 + 20);
                            PostController.getPartialList(partialIds, SORT_TYPE.DEFAULT.value());
                        } else {
                            postFeedAdapter.notifyDataSetChanged();
                            pullListView.onPullDownRefreshComplete();
                        }
                    } else {
                        postFeedAdapter.notifyDataSetChanged();
                        pullListView.onPullDownRefreshComplete();
                    }
                }
            });
            setLastUpdateTime();
        } else {
            ((ViewGroup) pullListView.getParent()).removeView(pullListView);
            // Restore previous state (including selected item index and scroll position)
            postListView.onRestoreInstanceState(postListViewState);
        }

        vpBanner.startAutoScroll();

        return pullListView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // see http://stackoverflow.com/questions/3014089/maintain-save-restore-scroll-position-when-returning-to-a-listview/3035521#3035521
        // Save ListView state
        postListViewState = postListView.onSaveInstanceState();
        EventBus.getDefault().unregister(this);
        vpBanner.stopAutoScroll();
    }

    private View getBannerView(LayoutInflater inflater, ViewGroup container) {
        View v = inflater.inflate(R.layout.banner_feed, container, false);
        rlBanner = (RelativeLayout) v.findViewById(R.id.layout_banner);
        vpBanner = (AutoScrollViewPager) v.findViewById(R.id.view_pager_banner);
        indicatorBanner = (CirclePageIndicator) v.findViewById(R.id.indicator_banner);
        ivCancelBanner = (ImageView) v.findViewById(R.id.image_view_cancel_banner);

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
        //是否允许切换到最后一个banner时，切换到另一个fragment
        //viewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);

        return v;
    }

    // 下拉事件
    public void onEvent(PullDownEvent event){
        postList.clear();
        pullUpCounter = 0;      //重置 pullUpCounter
        postIds = event.getPostIds();

        postList = event.getInitialList();
        postFeedAdapter.notifyDataSetChanged();
        pullListView.onPullDownRefreshComplete();

        pullListView.setHasMoreData(true);
        setLastUpdateTime();
    }

    // 上拉事件
    public void onEvent(PullUpEvent event){
        List<PostFeed> dList = event.getPartialList();
        postList.addAll(dList);
        postFeedAdapter.notifyDataSetChanged();
        pullListView.onPullUpRefreshComplete();

        pullListView.setHasMoreData(true);
        setLastUpdateTime();
    }

    public void onEvent(ScrollBannerEvent scrollBannerEvent) {
        if(scrollBannerEvent.enableScroll) {
            vpBanner.startAutoScroll();
        } else {
            vpBanner.stopAutoScroll();
        }
    }

    private void setLastUpdateTime() {
        String text = formatDateTime(System.currentTimeMillis());
        pullListView.setLastUpdatedLabel(text);
    }

    private String formatDateTime(long time) {
        if (0 == time) {
            return "";
        }

        return mDateFormat.format(new Date(time));
    }

    //此事件仅在切换feedFragment的Viewpager时使用，用于暂停，开启滚动
    public static class ScrollBannerEvent {
        public boolean enableScroll = false;
        public ScrollBannerEvent (boolean enableScroll){
            this.enableScroll = enableScroll;
        }
    }
}
