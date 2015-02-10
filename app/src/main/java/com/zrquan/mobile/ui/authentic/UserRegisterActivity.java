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
import android.widget.Toast;

import com.zrquan.mobile.R;
import com.zrquan.mobile.controller.AccountController;
import com.zrquan.mobile.event.AccountEvent;
import com.zrquan.mobile.support.util.RegUtils;
import com.zrquan.mobile.support.util.ScreenUtils;
import com.zrquan.mobile.ui.common.CommonActivity;

import java.util.regex.Matcher;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import de.greenrobot.event.EventBus;

public class UserRegisterActivity extends CommonActivity{
    public static final String TAG = "UserRegisterActivity";

    private Context context;
    private ProgressDialog mProgressDialog;

    @InjectView(R.id.titleText) TextView tvTitle;
    @InjectView(R.id.tv_btn_back) TextView tvBackBtn;
    @InjectView(R.id.tv_tips) TextView tvInputTips;
    @InjectView(R.id.btRegist) Button btRegist;
    @InjectView(R.id.phoneNum) EditText etPhoneNum;
    @InjectView(R.id.regist_phone_num_clear_btn) ImageView ivClearPhoneNumBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_user_register);
        ButterKnife.inject(this);
        initNavigationBar();
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

    public void onEvent(AccountEvent event){
        CharSequence ch = "短信验证码：" + event.verifyCode;
        Toast.makeText(context, ch, Toast.LENGTH_LONG).show();
        mProgressDialog.dismiss();
        Intent intent = new Intent(this, UserRegisterSetPasswordActivity.class);
        intent.putExtra("REGISTER_MOBILE", etPhoneNum.getText().toString());
        startActivity(intent);
        overridePendingTransition(R.anim.right2left_enter, R.anim.right2left_exit);
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
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getResources().getString(R.string.new_regist_confirm_registing));
        mProgressDialog.show();
        AccountController.sendVerifyCode(etPhoneNum.getText().toString());
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

    @OnClick(R.id.regist_phone_num_clear_btn)
    public void onClearPhoneNumBtnClick(View v) {
        etPhoneNum.setText("");
    }

    private void checkRegisterParams() {
        if(checkPhoneNum(etPhoneNum.getText().toString())) {
            btRegist.setEnabled(true);
        } else {
            btRegist.setEnabled(false);
        }
    }

    private boolean checkPhoneNum(String s) {
        Matcher matcher = RegUtils.getInstance().getMobilePattern().matcher(s);
        if(!matcher.matches()) {
            tvInputTips.setText(R.string.login_phone_number_error_tips);
            tvInputTips.setTextColor(getResources().getColor(R.color.main_highlight_text_color));
            return false;
        } else {
            tvInputTips.setText(R.string.register_phone_num_input_tips);
            tvInputTips.setTextColor(getResources().getColor(R.color.main_content_subtitle_text_color));
        }
        return true;
    }

    private void doBack() {
        ScreenUtils.hideSoftInput(this);
        finish();
        overridePendingTransition(R.anim.left2right_enter, R.anim.left2right_exit);
    }
}
