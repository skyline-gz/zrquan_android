package com.zrquan.mobile.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.zrquan.mobile.R;

public class UserRegisterActivity extends Activity{
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_user_register);

        initNavigationBar();
    }

    public void onBtnBackClick(View view) {
        doBack();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        doBack();
    }

    private void initNavigationBar() {
        TextView tvTitle = (TextView) findViewById(R.id.titleText);
        tvTitle.setText(R.string.account_regist);
        tvTitle.setVisibility(View.VISIBLE);

        TextView tvBtnBack = (TextView) findViewById(R.id.tv_btn_back);
        tvBtnBack.setVisibility(View.VISIBLE);
        tvBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBack();
            }
        });
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
