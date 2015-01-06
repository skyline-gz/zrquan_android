package com.zrquan.mobile.ui.feed;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zrquan.mobile.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

public class FeedFragment extends Fragment {
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentList;
    private TextView tvDiscussion, tvQuestion;
    private int currIndex;//当前页卡编号

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_feed);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        initNavBar(view);
        initViewPager(view);
        return view;
    }

    /*
	 * 初始化导航栏
	 */
    private void initNavBar(View view){
        tvDiscussion = (TextView)view.findViewById(R.id.tv_discussion);
        tvQuestion = (TextView)view.findViewById(R.id.tv_question);

        tvDiscussion.setOnClickListener(new TxListener(0));
        tvQuestion.setOnClickListener(new TxListener(1));
    }

    private class TxListener implements View.OnClickListener{
        private int index=0;

        public TxListener(int i) {
            index =i;
        }
        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }

    /*
     * 初始化ViewPager
     */
    private void initViewPager(View view){
        mPager = (ViewPager) view.findViewById(R.id.viewpager);
        fragmentList = new ArrayList<>();
        fragmentList.add(new DiscussionFragment());
        fragmentList.add(new QuestionFragment());

        //给ViewPager设置适配器
        mPager.setAdapter(new FeedPagerAdapter(getChildFragmentManager(), fragmentList));
        mPager.setCurrentItem(0);                                           //设置当前显示标签页为第一页
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());       //页面变化时的监听器
        selectTabDiscussion();
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageSelected(int position) {
            switch(position) {
                case 0:
                    selectTabDiscussion();
                    break;
                case 1:
                    selectTabQuestion();
                    break;
                default:
                    break;
            }
            Toast.makeText(getActivity(), "您选择了第" + (position + 1) + "个页卡", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectTabDiscussion() {
        tvDiscussion.setSelected(true);
        tvDiscussion.setBackgroundResource(R.drawable.bg_feed_tab_border_bottom);
        tvQuestion.setSelected(false);
        tvQuestion.setBackgroundResource(Color.TRANSPARENT);
    }

    private void selectTabQuestion() {
        tvDiscussion.setSelected(false);
        tvDiscussion.setBackgroundResource(Color.TRANSPARENT);
        tvQuestion.setSelected(true);
        tvQuestion.setBackgroundResource(R.drawable.bg_feed_tab_border_bottom);
    }
}
