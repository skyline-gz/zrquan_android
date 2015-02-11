package com.zrquan.mobile.ui.authentic;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zrquan.mobile.R;
import com.zrquan.mobile.controller.AccountController;
import com.zrquan.mobile.support.enums.IntentExtra;
import com.zrquan.mobile.support.util.RegUtils;
import com.zrquan.mobile.support.util.ScreenUtils;
import com.zrquan.mobile.support.util.ToastUtils;
import com.zrquan.mobile.ui.common.CommonActivity;

import java.util.regex.Matcher;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class UserResetPasswordActivity extends CommonActivity{
    private Context context;
    // see http://stackoverflow.com/questions/8771963/what-is-the-equivalent-of-settimeout-javascript-to-android
    private Handler mResendVerifyCounterHandler;
    private Runnable mResendVerifyCounterTask;
    private int mResendVerifyCounter = 60;

    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_back)
    TextView tvBack;

    @InjectView(R.id.et_mobile)
    EditText etMobile;
    @InjectView(R.id.btn_send_verify_code)
    Button btnSendVerifyCode;

    @InjectView(R.id.et_verify_code)
    EditText etVerifyCode;
    @InjectView(R.id.iv_verify_code_clear)
    ImageView ivVerifyCodeClear;

    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.iv_password_clear)
    ImageView ivPasswordClear;

    @InjectView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_user_reset_password);
        ButterKnife.inject(this);
        initNavigationBar();
        String mobile = getIntent().getStringExtra(IntentExtra.MOBILE.name());
        if(!TextUtils.isEmpty(mobile)) {
            etMobile.setText(mobile);
        }
        disableResendVerifyCode();
        mResendVerifyCounterHandler = new Handler();
        mResendVerifyCounterTask = new Runnable() {
            @Override
            public void run() {
                //do work
                countDownResendTime();
                mResendVerifyCounterHandler.postDelayed(this, 1000);
            }
        };
    }

    @OnClick(R.id.btn_send_verify_code)
    public void onResendVerifyCodeClick(View view) {
        String mobile = etMobile.getText().toString();
        Matcher matcher = RegUtils.getInstance().getMobilePattern().matcher(mobile);
        if(matcher.matches()) {
            disableResendVerifyCode();
            ToastUtils.show(context, "发送了验证码");
            mResendVerifyCounterHandler.postDelayed(mResendVerifyCounterTask, 1000);
            AccountController.sendVerifyCode(mobile);
        } else {
            ToastUtils.show(context, "手机号码格式不正确");
        }
    }

    @OnClick(R.id.tv_back)
    public void onBackClick(View view) {
        doBack();
    }

    private void initNavigationBar() {
        tvTitle.setText(R.string.account_register_set_password);
        tvTitle.setVisibility(View.VISIBLE);
        tvBack.setVisibility(View.VISIBLE);
    }

    private void disableResendVerifyCode() {
        btnSendVerifyCode.setEnabled(false);
        mResendVerifyCounter = 60;
        String msg = getResources().getString(R.string.new_regist_resent_verification_code_bt)
                .replace("%s", Integer.toString(mResendVerifyCounter));
        btnSendVerifyCode.setText(msg);
    }

    private void countDownResendTime() {
        mResendVerifyCounter --;
        if(mResendVerifyCounter >= 0) {
            String msg = getResources().getString(R.string.new_regist_resent_verification_code_bt)
                    .replace("%s", Integer.toString(mResendVerifyCounter));
            btnSendVerifyCode.setText(msg);
        } else {
            enableResendVerifyCode();
        }
    }

    private void enableResendVerifyCode() {
        btnSendVerifyCode.setEnabled(true);
        btnSendVerifyCode.setText(R.string.new_regist_resent_verification_content);
        mResendVerifyCounterHandler.removeCallbacks(mResendVerifyCounterTask);
    }

    private void doBack() {
        ScreenUtils.hideSoftInput(this);
        finish();
        overridePendingTransition(R.anim.left2right_enter, R.anim.left2right_exit);
    }
}
