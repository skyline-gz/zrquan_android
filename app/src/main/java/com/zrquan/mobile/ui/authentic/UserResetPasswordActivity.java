package com.zrquan.mobile.ui.authentic;

import android.content.Context;
import android.content.Intent;
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
import com.zrquan.mobile.event.AccountEvent;
import com.zrquan.mobile.event.ActivityFinishEvent;
import com.zrquan.mobile.support.enums.EventCode;
import com.zrquan.mobile.support.enums.EventType;
import com.zrquan.mobile.support.enums.IntentExtra;
import com.zrquan.mobile.support.enums.ServerCode;
import com.zrquan.mobile.support.util.RegUtils;
import com.zrquan.mobile.support.util.ScreenUtils;
import com.zrquan.mobile.support.util.ToastUtils;
import com.zrquan.mobile.support.util.ViewUtils;
import com.zrquan.mobile.ui.common.CommonActivity;

import java.util.regex.Matcher;

import de.greenrobot.event.EventBus;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class UserResetPasswordActivity extends CommonActivity {
    private Context context;
    // see http://stackoverflow.com/questions/8771963/what-is-the-equivalent-of-settimeout-javascript-to-android
    private Handler mResendVerifyCounterHandler;
    private Runnable mResendVerifyCounterTask;
    private int mResendVerifyCounter = 60;

    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_left)
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
        mResendVerifyCounterHandler = new Handler();
        mResendVerifyCounterTask = new Runnable() {
            @Override
            public void run() {
                //do work
                countDownResendTime();
                mResendVerifyCounterHandler.postDelayed(this, 1000);
            }
        };
        enableResendVerifyCode();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @OnClick(R.id.btn_send_verify_code)
    public void onResendVerifyCodeClick(View view) {
        String mobile = etMobile.getText().toString();
        Matcher matcher = RegUtils.getInstance().getMobilePattern().matcher(mobile);
        if(matcher.matches()) {
            disableResendVerifyCode();
            ToastUtils.show(context, "发送了验证码");
            mResendVerifyCounterHandler.postDelayed(mResendVerifyCounterTask, 1000);
            AccountController.sendVerifyCode(mobile, true);
        } else {
            ToastUtils.show(context, "手机号码格式不正确");
        }
    }

    @OnTextChanged(R.id.et_verify_code)
    public void onVerifyCodeTextChanged(CharSequence s, int start, int before, int count) {
        if(s.toString().trim().length() > 0) {
            ivVerifyCodeClear.setVisibility(View.VISIBLE);
        } else {
            ivVerifyCodeClear.setVisibility(View.GONE);
        }
        checkSubmitBtnEnable();
    }

    @OnClick(R.id.iv_verify_code_clear)
    public void onVerifyCodeClearClick(View view) {
        etVerifyCode.setText("");
    }

    @OnTextChanged(R.id.et_password)
    public void onPasswordTextChanged(CharSequence s, int start, int before, int count) {
        if(s.toString().trim().length() > 0) {
            ivPasswordClear.setVisibility(View.VISIBLE);
        } else {
            ivPasswordClear.setVisibility(View.GONE);
        }
        checkSubmitBtnEnable();
    }

    @OnClick(R.id.iv_password_clear)
    public void onPasswordClearClick(View view) {
        etPassword.setText("");
    }

    @OnClick(R.id.btn_submit)
    public void onBtnSubmitClick(View view) {
        String mobile = etMobile.getText().toString();
        Matcher matcher = RegUtils.getInstance().getMobilePattern().matcher(mobile);
        if(!matcher.matches()) {
            ToastUtils.show(context, "手机号码格式不正确");
            return;
        }

        String verifyCode = etVerifyCode.getText().toString();
        matcher = RegUtils.getInstance().getVerifyCodePattern().matcher(verifyCode);
        if(!matcher.matches()) {
            ToastUtils.show(context, "请输入六位数字的验证码");
            return;
        }

        String password = etPassword.getText().toString();
        matcher = RegUtils.getInstance().getPasswordPattern().matcher(password);
        if(!matcher.matches()) {
            ToastUtils.show(context, "请输入８～２０位字母和数字组合的密码");
            return;
        }

        AccountController.resetPassword(mobile, password, verifyCode);
    }

    public void onEvent(AccountEvent accountEvent){
        if(accountEvent.getEventType() == EventType.AE_NET_SEND_VERIFY_CODE) {
            if (accountEvent.getEventCode() == EventCode.S_OK) {
                CharSequence ch = "短信验证码：" + accountEvent.getVerifyCode();
                ToastUtils.show(context, ch);
            }
        } else if(accountEvent.getEventType() == EventType.AE_NET_RESET_PASSWORD) {
            if (accountEvent.getEventCode() == EventCode.S_OK) {
                //关闭注册第一步的Activity
                ActivityFinishEvent activityFinishEvent = new ActivityFinishEvent();
                activityFinishEvent.setTagName(UserRegisterActivity.class.getName());
                EventBus.getDefault().post(activityFinishEvent);
                //切换到登陆Activity
                ToastUtils.show(context, "重设密码成功，请重新登陆");
                finish();
                Intent intent = new Intent(this, UserLoginActivity.class);
                String mobile = etMobile.getText().toString();
                intent.putExtra(IntentExtra.MOBILE.name(), mobile);
                startActivity(intent);
                overridePendingTransition(R.anim.right2left_enter, R.anim.right2left_exit);
            } else if(accountEvent.getEventCode() == EventCode.FA_SERVER_ERROR) {
                if (accountEvent.getServerCode() == ServerCode.FA_NEED_VERIFY_CODE) {
                    ToastUtils.show(context, "请输入验证码");
                } else if (accountEvent.getServerCode() == ServerCode.FA_VERIFY_CODE_EXPIRED) {
                    ToastUtils.show(context, "验证码已经过期，请重新获取");
                } else if (accountEvent.getServerCode() == ServerCode.FA_USER_NOT_EXIT) {
                    ToastUtils.show(context, "该用户不存在");
                } else {
                    ToastUtils.show(context, "请求参数错误");
                }
            }
        }
    }

    @OnClick(R.id.tv_left)
    public void onBackClick(View view) {
        doBack();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        doBack();
    }

    private void checkSubmitBtnEnable() {
        if(ViewUtils.checkTextEmpty(etMobile) || ViewUtils.checkTextEmpty(etPassword)
                || ViewUtils.checkTextEmpty(etVerifyCode)) {
            btnSubmit.setEnabled(false);
        } else {
            btnSubmit.setEnabled(true);
        }
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
