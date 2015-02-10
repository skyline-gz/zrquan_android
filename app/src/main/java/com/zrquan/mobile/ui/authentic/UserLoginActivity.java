package com.zrquan.mobile.ui.authentic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zrquan.mobile.R;
import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.controller.AccountController;
import com.zrquan.mobile.dao.AccountDao;
import com.zrquan.mobile.event.AccountEvent;
import com.zrquan.mobile.model.Account;
import com.zrquan.mobile.support.enums.EventCode;
import com.zrquan.mobile.support.enums.EventType;
import com.zrquan.mobile.support.enums.ServerCode;
import com.zrquan.mobile.support.util.ScreenUtils;
import com.zrquan.mobile.support.util.StringUtils;
import com.zrquan.mobile.support.util.ToastUtils;
import com.zrquan.mobile.ui.MainActivity;
import com.zrquan.mobile.ui.common.CommonActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import de.greenrobot.event.EventBus;

public class UserLoginActivity extends CommonActivity {
    private Context context;
    private ProgressDialog mProgressDialog;

    @InjectView(R.id.tv_user_icon)
    TextView tvUserIcon;
    @InjectView(R.id.et_mobile)
    EditText etMobile;
    @InjectView(R.id.iv_mobile_clear)
    ImageView ivMobileClear;

    @InjectView(R.id.tv_password_icon)
    TextView tvPasswordIcon;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.iv_password_clear)
    ImageView ivPasswordClear;

    @InjectView(R.id.btn_login)
    Button btnLogin;

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

    @OnTextChanged(R.id.et_mobile)
    public void onMobileTextChanged(CharSequence s, int start, int before, int count) {
        if(s.toString().trim().length() > 0) {
            tvUserIcon.setBackgroundResource(R.drawable.login_user_hightlighted);
            ivMobileClear.setVisibility(View.VISIBLE);
        } else {
            tvUserIcon.setBackgroundResource(R.drawable.login_user);
            ivMobileClear.setVisibility(View.GONE);
        }
        checkLoginBtnEnable();
    }

    @OnTextChanged(R.id.et_password)
    public void onPasswordTextChanged(CharSequence s, int start, int before, int count) {
        if(s.toString().trim().length() > 0) {
            tvPasswordIcon.setBackgroundResource(R.drawable.login_key_hightlighted);
            ivPasswordClear.setVisibility(View.VISIBLE);
        } else {
            tvPasswordIcon.setBackgroundResource(R.drawable.login_key);
            ivPasswordClear.setVisibility(View.GONE);
        }
        checkLoginBtnEnable();
    }

    @OnClick(R.id.iv_mobile_clear)
    public void onMobileClearClick(View view) {
        etMobile.setText("");
    }

    @OnClick(R.id.iv_password_clear)
    public void onPasswordClearClick(View view) {
        etMobile.setText("");
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

    @OnClick(R.id.btn_login)
    public void onBtnLoginClick(View view) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("正在登陆...");
        mProgressDialog.show();
        AccountController.loginAccount(etMobile.getText().toString(), etPassword.getText().toString());
    }

    public void onEvent(AccountEvent accountEvent) {
        if(accountEvent.getEventType() == EventType.AE_NET_LOGIN) {
            if(accountEvent.getEventCode() == EventCode.S_OK) {
                //生成Account对象，并存储到SharedPreferences
                String token = accountEvent.getToken();
                Account account = new Account();
                account.setPhoneNum(etMobile.getText().toString());
                account.setAccessToken(token);
                new AccountDao().saveAccount(context, account);
                account.setVerified(true);
                ZrquanApplication.getInstance().setAccount(account);
                //重新打开主页面
                Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                //http://stackoverflow.com/questions/16217917/close-an-specific-activity-android
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.right2left_enter, R.anim.right2left_exit);
            } else if(accountEvent.getEventCode() == EventCode.FA_SERVER_ERROR) {
                if (accountEvent.getServerCode() == ServerCode.FA_USER_NOT_EXIT) {
                    ToastUtils.show(context, "用户不存在");
                } else if (accountEvent.getServerCode() == ServerCode.FA_INVALID_MOBILE_FORMAT) {
                    ToastUtils.show(context, "手机号码格式不正确");
                } else if (accountEvent.getServerCode() == ServerCode.FA_INVALID_PASSWORD_FORMAT) {
                    ToastUtils.show(context, "密码格式不正确");
                } else if (accountEvent.getServerCode() == ServerCode.FA_PASSWORD_ERROR) {
                    ToastUtils.show(context, "密码错误");
                }
            }
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        doBack();
    }

    private void checkLoginBtnEnable() {
        if(etMobile.getText().toString().trim().length() > 0 && etPassword.getText().toString().trim().length() > 0) {
            btnLogin.setEnabled(true);
        } else {
            btnLogin.setEnabled(false);
        }
    }

    private void doBack() {
        ScreenUtils.hideSoftInput(this);
        finish();
        overridePendingTransition(R.anim.left2right_enter, R.anim.left2right_exit);
    }
}
