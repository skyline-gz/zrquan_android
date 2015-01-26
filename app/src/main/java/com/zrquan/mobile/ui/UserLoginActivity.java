package com.zrquan.mobile.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.zrquan.mobile.R;
import com.zrquan.mobile.support.util.ScreenUtils;
import com.zrquan.mobile.ui.common.CommonActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserLoginActivity extends CommonActivity {

    public static final String TAG = "UserLoginActivity";

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_user_login);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.btn_back)
    public void onBtnBackClick(View view) {
        doBack();
    }

    @OnClick(R.id.btn_login_register)
    public void onBtnRegisterClick(View view) {
        Intent myIntent = new Intent(this, UserRegisterActivity.class);
        startActivity(myIntent);
        overridePendingTransition(R.anim.right2left_enter, R.anim.right2left_exit);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        doBack();
    }

    private void doBack() {
        ScreenUtils.hideSoftInput(this);
        finish();
        overridePendingTransition(R.anim.left2right_enter, R.anim.left2right_exit);
    }
}
