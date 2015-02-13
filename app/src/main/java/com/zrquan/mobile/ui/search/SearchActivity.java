package com.zrquan.mobile.ui.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.viewpagerindicator.TabPageIndicator;
import com.zrquan.mobile.controller.SearchController;
import com.zrquan.mobile.support.util.ScreenUtils;
import com.zrquan.mobile.ui.common.CommonFragmentActivity;
import com.zrquan.mobile.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends CommonFragmentActivity {

    private static final String[] SUBJECTS = new String[] { "讨论", "问答", "职人", "主题"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);

        FragmentPagerAdapter adapter = new GoogleMusicAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        final EditText editText = (EditText) findViewById(R.id.tv_search_keyword);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    SearchController.performSearch(editText.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });
    }

    @OnClick(R.id.tv_search_cancel)
    public void onSearchCancelClick() {
        doBack();
    }

    class GoogleMusicAdapter extends FragmentPagerAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new SearchDiscussionFragment();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return SUBJECTS[position % SUBJECTS.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return SUBJECTS.length;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        doBack();
    }

    private void doBack() {
        ScreenUtils.hideSoftInput(this);
        finish();
        overridePendingTransition(R.anim.right2left_enter, R.anim.right2left_exit);
    }
}
