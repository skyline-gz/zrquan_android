package com.zrquan.mobile.ui;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Optional;
import com.zrquan.mobile.R;

public class MainActivity extends ActionBarActivity {
    private LayoutInflater inflater;
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        addCard ();

        inflater = LayoutInflater.from(this);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(getApplicationContext(), getSupportFragmentManager(), R.id.real_tab_content);

        // 添加tab名称和图标
        View indicator = getIndicatorView("首页", R.drawable.tab_home_btn);
        TabHost.TabSpec firstSpec = mTabHost.newTabSpec("tab_home").setIndicator(indicator);
        mTabHost.addTab(firstSpec, FeedFragment.class, null);

        // 添加tab名称和图标
        View indicator2 = getIndicatorView("消息", R.drawable.tab_message_btn);
        TabHost.TabSpec secondSpec = mTabHost.newTabSpec("tab_message").setIndicator(indicator2);
        mTabHost.addTab(secondSpec, MessageFragment.class, null);

        // 添加tab名称和图标
        View indicator3 = getIndicatorView("发现", R.drawable.tab_find_btn);
        TabHost.TabSpec thirdSpec = mTabHost.newTabSpec("tab_find").setIndicator(indicator3);
        mTabHost.addTab(thirdSpec, FeedFragment.class, null);

        // 添加tab名称和图标
        View indicator4 = getIndicatorView("我", R.drawable.tab_myself_btn);
        TabHost.TabSpec forthSpec = mTabHost.newTabSpec("tab_myself").setIndicator(indicator4);
        mTabHost.addTab(forthSpec, MessageFragment.class, null);
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
