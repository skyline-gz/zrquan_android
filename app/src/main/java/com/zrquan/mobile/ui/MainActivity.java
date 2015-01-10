package com.zrquan.mobile.ui;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Optional;
import com.zrquan.mobile.R;
import com.zrquan.mobile.ui.feed.FeedFragment;

public class MainActivity extends FragmentActivity {
    private LayoutInflater inflater;

    //定义数组来存放Fragment界面
    private Class fragmentArray[] = {FeedFragment.class, MessageFragment.class,
            FeedFragment.class,MessageFragment.class};

    //定义数组来存放按钮图片
    private int mImageViewArray[] = {R.drawable.tab_home_btn, R.drawable.tab_find_btn,
            R.drawable.tab_message_btn, R.drawable.tab_myself_btn};

    //Tab选项卡的文字
    private String mTextViewArray[] = {"首页", "关注的", "消息", "我"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        addCard ();

        inflater = LayoutInflater.from(this);
        FragmentTabHost mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(getApplicationContext(), getSupportFragmentManager(), R.id.real_tab_content);

        //得到fragment的个数
        int count = fragmentArray.length;

        for(int i = 0; i < count; i++){
            View indicator = getIndicatorView(mTextViewArray[i], mImageViewArray[i]);
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextViewArray[i]).setIndicator(indicator);
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            //设置Tab按钮的背景
//            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.bg_border_top);
        }

        //默认选取显示首页
        mTabHost.setCurrentTab(0);
        //去掉tab icon下面横线
        mTabHost.getTabWidget().setStripEnabled(false);
        //去掉tab icon之间的间隔线
        mTabHost.getTabWidget().setDividerDrawable(null);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            showToast();
//            return true;
//        } else if (id == R.id.action_card) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * 设置底部选项卡
     */
    private View getIndicatorView(String name, int iconId) {
        View view = inflater.inflate(R.layout.tab_main, null);
        ImageView ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        ivIcon.setImageResource(iconId);
        tvTitle.setText(name);
        return view;
    }

    private void showToast() {
        //Guava test
        Optional<Integer> possible = Optional.of(5);
        possible.isPresent(); // returns true
        int id = possible.get(); // returns 5

        CharSequence cs = "possible value " + id;
        Toast.makeText(this, cs, Toast.LENGTH_LONG).show();
    }

//    private void addCard() {
//        //Create a Card
//        Card card = new Card(this);
//
//        //Create a CardHeader
//        CardHeader header = new CardHeader(this);
//
//        //Add Header to card
//        card.addCardHeader(header);
//
//        //Set the card inner text
//        card.setTitle("My Title");
//
//        CardViewNative cardView = (CardViewNative) findViewById(R.id.carddemo);
//        cardView.setCard(card);
//    }
}
