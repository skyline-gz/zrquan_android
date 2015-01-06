package com.zrquan.mobile.ui.feed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zrquan.mobile.R;

import java.util.ArrayList;

public class FeedFragment extends Fragment {
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentList;
    private TextView barText;
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
        initViewPager(view);
        initNavBar(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Handle action bar item clicks here. The action bar will
//        automatically handle clicks on the Home/Up button, so long
//        as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        } else if (id == R.id.action_card) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // 取得该控件的实例
            LinearLayout.LayoutParams ll = (android.widget.LinearLayout.LayoutParams) barText
                    .getLayoutParams();

            if(currIndex == arg0){
                ll.leftMargin = (int) (currIndex * barText.getWidth() + arg1
                        * barText.getWidth());
            }else if(currIndex > arg0){
                ll.leftMargin = (int) (currIndex * barText.getWidth() - (1 - arg1)* barText.getWidth());
            }
            barText.setLayoutParams(ll);
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            currIndex = arg0;
            int i = currIndex + 1;
            Toast.makeText(getActivity(), "您选择了第" + i + "个页卡", Toast.LENGTH_SHORT).show();
        }
    }
}
