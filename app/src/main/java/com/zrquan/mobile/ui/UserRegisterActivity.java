package com.zrquan.mobile.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zrquan.mobile.R;
import com.zrquan.mobile.support.util.RegUtil;

import java.util.regex.Matcher;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class UserRegisterActivity extends Activity{
    private Context context;

    @InjectView(R.id.titleText) TextView tvTitle;
    @InjectView(R.id.tv_btn_back) TextView tvBackBtn;
    @InjectView(R.id.tv_tips) TextView tvInputTips;
    @InjectView(R.id.btRegist) Button btRegist;
    @InjectView(R.id.phoneNum) EditText etPhoneNum;
    @InjectView(R.id.regist_phone_num_clear_btn) ImageView ivClearPhoneNumBtn;
    @InjectView(R.id.password) EditText etPassword;
    @InjectView(R.id.regist_password_clear_btn) ImageView ivClearPasswordBtn;

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
        tvBackBtn.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btRegist)
    public void onRegisterBtnClick() {
        Toast.makeText(this, "点击了登陆按钮", Toast.LENGTH_LONG).show();
    }

    @OnTextChanged(R.id.phoneNum)
    public void onPhoneNumTextChanged(CharSequence s, int start, int before, int count) {
        if(s.toString().trim().length() > 0) {
            ivClearPhoneNumBtn.setVisibility(View.VISIBLE);
        } else {
            ivClearPhoneNumBtn.setVisibility(View.GONE);
        }
        checkRegisterParams();
    }

    @OnTextChanged(R.id.password)
    public void onPasswordTextChanged(CharSequence s, int start, int before, int count) {
        if(s.toString().trim().length() > 0) {
            ivClearPasswordBtn.setVisibility(View.VISIBLE);
        } else {
            ivClearPasswordBtn.setVisibility(View.GONE);
        }
        checkRegisterParams();
    }

    @OnClick(R.id.regist_phone_num_clear_btn)
    public void onClearPhoneNumBtnClick(View v) {
        etPhoneNum.setText("");
    }

    @OnClick(R.id.regist_phone_num_clear_btn)
    public void regist_password_clear_btn(View v) {
        etPassword.setText("");
    }

    private void checkRegisterParams() {
        if(checkPhoneNum(etPhoneNum.getText().toString())
                && checkPassword(etPassword.getText().toString())) {
            btRegist.setEnabled(true);
            btRegist.setTextColor(getResources().getColor(R.color.main_button_shadow_text_color_for_light_color_button));
        } else {
            btRegist.setEnabled(false);
            btRegist.setTextColor(getResources().getColor(R.color.main_button_disabled_text_color_for_light_color_button));
        }
    }

    private boolean checkPhoneNum(String s) {
        Matcher matcher = RegUtil.getInstance().getMobilePattern().matcher(s);
        if(!matcher.matches()) {
            tvInputTips.setText(R.string.login_phone_number_error_tips);
            tvInputTips.setTextColor(getResources().getColor(R.color.main_highlight_text_color));
            return false;
        } else {
            tvInputTips.setText(R.string.pwd_tips);
            tvInputTips.setTextColor(getResources().getColor(R.color.main_content_subtitle_text_color));
        }
        return true;
    }

    private boolean checkPassword(String s) {
        Matcher matcher = RegUtil.getInstance().getPasswordPattern().matcher(s);
        if(!matcher.matches()) {
            tvInputTips.setText(R.string.pwd_tips);
            tvInputTips.setTextColor(getResources().getColor(R.color.main_highlight_text_color));
            return false;
        } else {
            tvInputTips.setText(R.string.pwd_tips);
            tvInputTips.setTextColor(getResources().getColor(R.color.main_content_subtitle_text_color));
        }
        return true;
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
