package com.zrquan.mobile.ui.authentic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zrquan.mobile.R;
import com.zrquan.mobile.controller.AccountController;
import com.zrquan.mobile.event.AccountEvent;
import com.zrquan.mobile.support.enums.EventCode;
import com.zrquan.mobile.support.enums.EventType;
import com.zrquan.mobile.support.enums.ServerCode;
import com.zrquan.mobile.support.util.RegUtils;
import com.zrquan.mobile.support.util.ScreenUtils;
import com.zrquan.mobile.support.util.ToastUtils;
import com.zrquan.mobile.ui.adapter.AutoMatchAdapter;
import com.zrquan.mobile.ui.common.CommonActivity;
import com.zrquan.mobile.ui.popup.SelIndustryPopup;

import java.util.regex.Matcher;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import de.greenrobot.event.EventBus;

public class UserRegisterSetPasswordActivity extends CommonActivity {
    private Context context;
    // see http://stackoverflow.com/questions/8771963/what-is-the-equivalent-of-settimeout-javascript-to-android
    private Handler mResendVerifyCounterHandler;
    private Runnable mResendVerifyCounterTask;
    private int mResendVerifyCounter = 60;
    private String mPhoneNum;
    private SelIndustryPopup mSelIndustryPopup;
    private int mSelectedIndustryId = -1;

    @InjectView(R.id.titleText)
    TextView tvTitle;
    @InjectView(R.id.tv_btn_back)
    TextView tvBackBtn;
    @InjectView(R.id.tv_content)
    TextView tvContent;
    @InjectView(R.id.ll_verify_code)
    LinearLayout llVerifyCode;
    @InjectView(R.id.et_verify_code)
    EditText etVerifyCode;
    @InjectView(R.id.btn_resend_verify_code)
    Button btnResendVerifyCode;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.iv_password_clear)
    ImageView ivPasswordClear;
    @InjectView(R.id.et_true_name)
    EditText etTrueName;
    @InjectView(R.id.iv_true_name_clear)
    ImageView ivTrueNameClear;
    @InjectView(R.id.rg_register_type)
    RadioGroup rgRegisterType;
    @InjectView(R.id.rb_worker)
    RadioButton rbWorker;
    @InjectView(R.id.rb_student)
    RadioButton rbStudent;
    @InjectView(R.id.ll_industry)
    LinearLayout llIndustry;
    @InjectView(R.id.tv_industry)
    TextView tvIndustry;
    @InjectView(R.id.ll_school)
    LinearLayout llSchool;
    @InjectView(R.id.tv_school)
    AutoCompleteTextView tvSchool;
    @InjectView(R.id.iv_school_clear)
    ImageView ivSchoolClear;
    @InjectView(R.id.tv_tips)
    TextView tvInputTips;
    @InjectView(R.id.btn_regist)
    Button btnRegist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_user_register_set_password);
        ButterKnife.inject(this);
        initNavigationBar();
        String mobile = getIntent().getStringExtra("REGISTER_MOBILE");
        tvContent.setText(tvContent.getText().toString().replace("%s", mobile));
        mPhoneNum = mobile;
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

        rgRegisterType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_worker) {
                    llIndustry.setVisibility(View.VISIBLE);
                    llSchool.setVisibility(View.GONE);
                } else {
                    llSchool.setVisibility(View.VISIBLE);
                    llIndustry.setVisibility(View.GONE);
                }
                validateInputAndShowError();
            }
        });

        tvSchool.setAdapter(new AutoMatchAdapter(this, android.R.layout.simple_dropdown_item_1line));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        mResendVerifyCounterHandler.postDelayed(mResendVerifyCounterTask, 1000);
    }

    //at some point in your program you will probably want the handler to stop (in onStop is a good place)
    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        mResendVerifyCounterHandler.removeCallbacks(mResendVerifyCounterTask);
    }

    public void onEvent(AccountEvent accountEvent){
        if(accountEvent.getEventType() == EventType.AE_NET_SEND_VERIFY_CODE) {
            if (accountEvent.getEventCode() == EventCode.S_OK) {
                CharSequence ch = "短信验证码：" + accountEvent.getVerifyCode();
                ToastUtils.show(context, ch);
            }
        } else if(accountEvent.getEventType() == EventType.AE_NET_REGISTER) {
            if (accountEvent.getEventCode() == EventCode.S_OK) {
                ToastUtils.show(context, "注册成功");
                finish();
                Intent intent = new Intent(this, UserLoginActivity.class);
                intent.putExtra("REGISTER_MOBILE", mPhoneNum);
                startActivity(intent);
                overridePendingTransition(R.anim.right2left_enter, R.anim.right2left_exit);
            } else if(accountEvent.getEventCode() == EventCode.FA_SERVER_ERROR) {
                if (accountEvent.getServerCode() == ServerCode.FA_USER_ALREADY_EXIT) {
                    ToastUtils.show(context, "该用户已经存在");
                } else if (accountEvent.getServerCode() == ServerCode.FA_INVALID_VERIFY_CODE) {
                    ToastUtils.show(context, "验证码不正确");
                }
            }
        }
    }

    private void initNavigationBar() {
        tvTitle.setText(R.string.account_register_set_password);
        tvTitle.setVisibility(View.VISIBLE);
        tvBackBtn.setVisibility(View.VISIBLE);
    }

    @OnTextChanged(R.id.et_verify_code)
    public void onVerifyCodeTextChanged(CharSequence s, int start, int before, int count) {
        validateInputAndShowError();
    }

    @OnClick(R.id.btn_resend_verify_code)
    public void onResendVerifyCodeClick(View view) {
        disableResendVerifyCode();
        ToastUtils.show(context, "发送了验证码");
        mResendVerifyCounterHandler.postDelayed(mResendVerifyCounterTask, 1000);
        AccountController.sendVerifyCode(mPhoneNum);
    }

    @OnClick(R.id.tv_btn_back)
    public void onBtnBackClick(View view) {
        doBack();
    }

    @OnClick(R.id.iv_password_clear)
    public void onPasswordClearClick(View v) {
        etPassword.setText("");
    }

    @OnTextChanged(R.id.et_password)
    public void onPasswordTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().trim().length() > 0) {
            ivPasswordClear.setVisibility(View.VISIBLE);
        } else {
            ivPasswordClear.setVisibility(View.GONE);
        }
        validateInputAndShowError();
    }

    @OnClick(R.id.iv_true_name_clear)
    public void onTrueNameClearClick(View v) {
        etTrueName.setText("");
    }

    @OnTextChanged(R.id.et_true_name)
    public void onTrueNameTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().trim().length() > 0) {
            ivTrueNameClear.setVisibility(View.VISIBLE);
        } else {
            ivTrueNameClear.setVisibility(View.GONE);
        }
        validateInputAndShowError();
    }

    @OnClick(R.id.iv_school_clear)
    public void onSchoolClearClick(View v) {
        tvSchool.setText("");
    }

    @OnTextChanged(R.id.tv_school)
    public void onSchoolTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().trim().length() > 0) {
            ivSchoolClear.setVisibility(View.VISIBLE);
        } else {
            ivSchoolClear.setVisibility(View.GONE);
        }
        validateInputAndShowError();
    }

    @OnClick(R.id.tv_industry)
    public void onIndustryLayoutClick(View v) {
        Rect location = ScreenUtils.locateView(llVerifyCode);
        if (mSelIndustryPopup == null) {
            Point screenSize = ScreenUtils.getScreenSize(context);
            int popUpHeight = screenSize.y - location.top;
            mSelIndustryPopup = new SelIndustryPopup(context, WindowManager.LayoutParams.MATCH_PARENT, popUpHeight, mSelectedIndustryId);
            mSelIndustryPopup.setOnSelectIndustry(new SelIndustryPopup.OnSelectIndustry() {
                @Override
                public void OnSelectIndustry(String displayText, int childIndustryId) {
                    tvIndustry.setText(displayText);
                    mSelectedIndustryId = childIndustryId;
                    validateInputAndShowError();
                }
            });
        }
        mSelIndustryPopup.showAtLocation(findViewById(android.R.id.content), Gravity.NO_GRAVITY, 0, location.top);
    }

    @OnClick(R.id.btn_regist)
    public void onBtnRegisterClick(View v){
        if(validateInputAndShowError()){
            AccountController.registerAccount(etVerifyCode.getText().toString(), mPhoneNum, etPassword.getText().toString(),
                    etTrueName.getText().toString(), mSelectedIndustryId, tvSchool.getText().toString());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        doBack();
    }

    private boolean validateInputAndShowError() {
        if(checkVerifyCode() && checkPassword() && checkTrueName() && checkAddictionInfo()) {
            tvInputTips.setText("");
            tvInputTips.setVisibility(View.GONE);
            btnRegist.setEnabled(true);
            return true;
        } else {
            tvInputTips.setVisibility(View.VISIBLE);
            btnRegist.setEnabled(false);
            return false;
        }
    }

    private boolean checkVerifyCode() {
        Matcher matcher = RegUtils.getInstance().getVerifyCodePattern().matcher(etVerifyCode.getText());
        if (!matcher.matches()) {
            tvInputTips.setText("验证码必须为六位数字");
            return false;
        }
        return true;
    }

    private boolean checkPassword() {
        Matcher matcher = RegUtils.getInstance().getPasswordPattern().matcher(etPassword.getText());
        if (!matcher.matches()) {
            tvInputTips.setText(R.string.pwd_tips);
            return false;
        }
        return true;
    }

    private boolean checkTrueName() {
        Matcher matcher = RegUtils.getInstance().getTrueNamePattern().matcher(etTrueName.getText());
        if (!matcher.matches()) {
            tvInputTips.setText("真实姓名必须为汉字、字母、数字及下划线的组合");
            return false;
        }
        return true;
    }

    private boolean checkAddictionInfo() {
        if(rbWorker.isChecked()) {
            if(mSelectedIndustryId == -1) {
                tvInputTips.setText("请选择所在行业");
                return false;
            }
        } else {
            Matcher matcher = RegUtils.getInstance().getSchoolNamePattern().matcher(tvSchool.getText());
            if (!matcher.matches()) {
                tvInputTips.setText("学校名称必须为汉字、字母、数字及下划线的组合");
                return false;
            }
        }
        return true;
    }

    private void enableResendVerifyCode() {
        btnResendVerifyCode.setEnabled(true);
        btnResendVerifyCode.setText(R.string.new_regist_resent_verification_content);
        mResendVerifyCounterHandler.removeCallbacks(mResendVerifyCounterTask);
    }

    private void disableResendVerifyCode() {
        btnResendVerifyCode.setEnabled(false);
        mResendVerifyCounter = 60;
        String msg = getResources().getString(R.string.new_regist_resent_verification_code_bt)
                .replace("%s", Integer.toString(mResendVerifyCounter));
        btnResendVerifyCode.setText(msg);
    }

    private void countDownResendTime() {
        mResendVerifyCounter --;
        if(mResendVerifyCounter >= 0) {
            String msg = getResources().getString(R.string.new_regist_resent_verification_code_bt)
                    .replace("%s", Integer.toString(mResendVerifyCounter));
            btnResendVerifyCode.setText(msg);
        } else {
            enableResendVerifyCode();
        }
    }

    private void doBack() {
        ScreenUtils.hideSoftInput(this);
        finish();
        overridePendingTransition(R.anim.left2right_enter, R.anim.left2right_exit);
    }
}
