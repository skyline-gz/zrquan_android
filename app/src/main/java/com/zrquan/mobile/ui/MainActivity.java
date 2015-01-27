package com.zrquan.mobile.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Optional;
import com.zrquan.mobile.R;
import com.zrquan.mobile.ui.common.CommonFragmentActivity;
import com.zrquan.mobile.ui.feed.FeedFragment;
import com.zrquan.mobile.widget.fragment.FragmentTabHost;

public class MainActivity extends CommonFragmentActivity {
    private LayoutInflater inflater;
    private Context context;

    private final int COMPOSE_TAB_INDEX = 2;

    //定义数组来存放Fragment界面
    private Class fragmentArray[] = {FeedFragment.class, FollowFragment.class, null,
            MessageFragment.class, ProfileFragment.class};

    //定义数组来存放按钮图片
    private int mImageViewArray[] = {R.drawable.tab_home_btn, R.drawable.tab_find_btn, -1,
            R.drawable.tab_message_btn, R.drawable.tab_myself_btn};

    //Tab选项卡的文字
    private String mTextViewArray[] = {"社区", "关注人", "发表", "消息", "我"};

    //Tab对象
    private FragmentTabHost mTabHost;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        inflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(getApplicationContext(), getSupportFragmentManager(), R.id.real_tab_content);

        //得到fragment的个数
        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {

            View indicator;
            TabHost.TabSpec tabSpec;
            if(i == COMPOSE_TAB_INDEX) {
                //设置写作Tab
                indicator = inflater.inflate(R.layout.tab_compose, null);
            } else {
                //为每一个Tab按钮设置图标、文字和内容
                indicator = getIndicatorView(mTextViewArray[i], mImageViewArray[i]);
            }
            tabSpec = mTabHost.newTabSpec(Integer.toString(i)).setIndicator(indicator);
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
        //当点击发表按钮时，弹出发表按钮的popupwindow,由于此处监听的是TabChange,因此当收回popupWindow时需要select之前的View
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId.equals(Integer.toString(COMPOSE_TAB_INDEX))) {
                    mPopupWindow.setAnimationStyle(R.style.ComposePopupAnimation);
                    mPopupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.TOP| Gravity.START, 0, 0);
                    mPopupWindow.update();
                    animRotateCancelBtn();
                    animPanelHolder();
                }
            }
        });

        initComposeWindow();
    }

    private void initComposeWindow() {
        View popupView = getLayoutInflater().inflate(R.layout.pop_up_window_compose, null);

        mPopupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);

        View ivCloseViewBtn = popupView.findViewById(R.id.pop_control_bar_front_holder);
        ivCloseViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPopupAndRestoreTab();
            }
        });

        View vDiscussBtn = popupView.findViewById(R.id.layout_item_discuss);
        vDiscussBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "点击了讨论按钮", Toast.LENGTH_LONG).show();
            }
        });

        View vQuestionBtn = popupView.findViewById(R.id.layout_item_question);
        vQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "点击了问答按钮", Toast.LENGTH_LONG).show();
            }
        });

        View ivPanelHolder = mPopupWindow.getContentView().findViewById(R.id.panel_holder);
        ivPanelHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPopupAndRestoreTab();
            }
        });
    }

    private void dismissPopupAndRestoreTab() {
        animPanelHolderExitAndDismissPopUp();
        String lastTabId = mTabHost.getLastTabId();
        mTabHost.setCurrentTabByTag(lastTabId);
    }

    private void animPanelHolderExitAndDismissPopUp() {
        View ivPanelHolder = mPopupWindow.getContentView().findViewById(R.id.panel_holder);
        TranslateAnimation t = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
        t.setInterpolator(AnimationUtils.loadInterpolator(context, android.R.interpolator.accelerate_quad));
        t.setDuration(150);
        t.setFillAfter(true);
        t.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mPopupWindow.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivPanelHolder.startAnimation(t);
    }

    private void animPanelHolder() {
        View ivPanelHolder = mPopupWindow.getContentView().findViewById(R.id.panel_holder);
        TranslateAnimation t = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0);
        t.setInterpolator(AnimationUtils.loadInterpolator(context, android.R.interpolator.overshoot));
        t.setDuration(300);
        t.setFillAfter(true);
        ivPanelHolder.startAnimation(t);
    }

    private void animRotateCancelBtn() {
        View ivCancelBtn = mPopupWindow.getContentView().findViewById(R.id.pop_control_bar_front_close_img);
        RotateAnimation r = new RotateAnimation(0.0f, 45.0f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        r.setInterpolator(AnimationUtils.loadInterpolator(context, android.R.interpolator.accelerate_quad));
        r.setDuration(200);
        r.setFillAfter(true);
        ivCancelBtn.startAnimation(r);
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
}
