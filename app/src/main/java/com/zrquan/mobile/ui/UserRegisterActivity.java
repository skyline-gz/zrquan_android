package com.zrquan.mobile.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.zrquan.mobile.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class UserRegisterActivity extends Activity{
    private Context context;

    @InjectView(R.id.titleText) TextView tvTitle;
    @InjectView(R.id.tv_btn_back) TextView tvBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_user_register);
        ButterKnife.inject(this);
        initNavigationBar();
    }

    @OnClick(R.id.tv_btn_back)
    public void onBtnBackClick(View view) {
        doBack();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        doBack();
    }

    private void initNavigationBar() {
        tvTitle.setText(R.string.account_regist);
        tvTitle.setVisibility(View.VISIBLE);
        tvBtnBack.setVisibility(View.VISIBLE);
    }

    private void doBack() {
        hideKeyboard();
        finish();
        overridePendingTransition(R.anim.left2right_enter, R.anim.left2right_exit);
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
