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
import com.zrquan.mobile.support.util.ScreenUtils;
import com.zrquan.mobile.support.util.UrlUtils;
import com.zrquan.mobile.ui.common.CommonFragment;
import com.zrquan.mobile.widget.pulltorefresh.PullToRefreshBase;
import com.zrquan.mobile.widget.pulltorefresh.PullToRefreshListView;
import com.zrquan.mobile.widget.viewpager.AutoScrollViewPager;
import com.zrquan.mobile.widget.viewpager.ImagePagerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
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

    private ListView mListView;
    private Parcelable mListViewState;                   //用于保存ListView状态
    private PullToRefreshListView mPullListView;
    private PostFeedAdapter postFeedAdapter;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    private List<PostFeed> postList;
    private boolean mIsStart = true;
    private int mCurIndex = 0;
    private static final int mLoadDataCount = 100;

    private int index;
//    private ScrollControlReceiver scrollControlReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        if (mPullListView == null) {
            context = getActivity().getApplicationContext();

            View bannerView = getBannerView(inflater, container);
            // http://stackoverflow.com/questions/4393775/android-classcastexception-when-adding-a-header-view-to-expandablelistview
            // ERROR/AndroidRuntime(421): Caused by:java.lang.ClassCastException: android.widget.LinearLayout$LayoutParams
            // 修复因HeadView不是ListView导致的运行时异常
            // So basically, if you are adding a view to another,
            // you MUST set the LayoutParams of the view to the LayoutParams type that the parent uses,
            // or you will get a runtime error.
            bannerView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,
                    ListView.LayoutParams.WRAP_CONTENT));

            mPullListView = new PullToRefreshListView(context);

            mPullListView.setBackgroundColor(getResources().getColor(R.color.main_feed_background_color));
            mCurIndex = mLoadDataCount;
            // 触发事件，抽取数据
            PostController.getIdsAndInitialList(1, UrlUtils.SORT_TYPE_DEFAULT);
            postFeedAdapter = new PostFeedAdapter(context, postList);
            mListView = mPullListView.getRefreshableView();

            // Note: When first introduced, this method could only be called before
            // setting the adapter with {@link #setAdapter(ListAdapter)}. Starting with
            mListView.addHeaderView(bannerView);
            mListView.setAdapter(postFeedAdapter);
            mListView.setDivider(null);
            mListView.setDividerHeight((int) ScreenUtils.dpToPx(context, 6.0f));
            mListView.setSelector(android.R.color.transparent);
            mListView.setCacheColorHint(Color.TRANSPARENT);

            mPullListView.setPullLoadEnabled(false);
            mPullListView.setScrollLoadEnabled(true);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                    String text = postList.get(position) + ", index = " + (position + 1);
                    Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                }
            });
            mPullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    mIsStart = true;

                    Account account = ZrquanApplication.getInstance().getAccount();
                    if (account != null && account.getId() != null) {
                        PostController.getIdsAndInitialList(account.getId(), UrlUtils.SORT_TYPE_DEFAULT);
                    } else {
                        postFeedAdapter.notifyDataSetChanged();
                        mPullListView.onPullDownRefreshComplete();
                    }
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    mIsStart = false;
                    // 如果已经上拉到24次，不再刷新数据
                    if (pullUpCounter < 24) {
                        pullUpCounter++;
                        Account account = ZrquanApplication.getInstance().getAccount();
                        if (account != null && account.getId() != null) {
                            Integer[] partialIds = Arrays.copyOfRange(
                                    postIds, pullUpCounter * 20, pullUpCounter * 20 + 20);
                            PostController.getPartialList(partialIds, UrlUtils.SORT_TYPE_DEFAULT);
                        } else {
                            postFeedAdapter.notifyDataSetChanged();
                            mPullListView.onPullDownRefreshComplete();
                        }
                    } else {
                        postFeedAdapter.notifyDataSetChanged();
                        mPullListView.onPullDownRefreshComplete();
                    }
                }
            });
            setLastUpdateTime();
        } else {
            ((ViewGroup) mPullListView.getParent()).removeView(mPullListView);
            // Restore previous state (including selected item index and scroll position)
            mListView.onRestoreInstanceState(mListViewState);
        }

        vpBanner.startAutoScroll();

        return mPullListView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // see http://stackoverflow.com/questions/3014089/maintain-save-restore-scroll-position-when-returning-to-a-listview/3035521#3035521
        // Save ListView state
        mListViewState = mListView.onSaveInstanceState();
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
        mPullListView.onPullDownRefreshComplete();

        mPullListView.setHasMoreData(true);
        setLastUpdateTime();
    }

    // 上拉事件
    public void onEvent(PullUpEvent event){
        List<PostFeed> dList = event.getPartialList();
        postList.addAll(dList);
        postFeedAdapter.notifyDataSetChanged();
        mPullListView.onPullUpRefreshComplete();

        mPullListView.setHasMoreData(true);
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
        mPullListView.setLastUpdatedLabel(text);
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
