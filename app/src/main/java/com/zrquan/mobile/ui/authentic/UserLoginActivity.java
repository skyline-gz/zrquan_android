package com.zrquan.mobile.ui.authentic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.zrquan.mobile.R;
import com.zrquan.mobile.support.util.ScreenUtils;
import com.zrquan.mobile.support.util.StringUtils;
import com.zrquan.mobile.ui.common.CommonActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class UserLoginActivity extends CommonActivity {
    private Context context;

    @InjectView(R.id.et_mobile)
    EditText etMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        ButterKnife.inject(this);
        context = getApplicationContext();
        Intent intent = getIntent();
        String mobile = intent.getStringExtra("REGISTER_MOBILE");
        if(!StringUtils.isEmpty(mobile)) {
            etMobile.setText(mobile);
        }
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
