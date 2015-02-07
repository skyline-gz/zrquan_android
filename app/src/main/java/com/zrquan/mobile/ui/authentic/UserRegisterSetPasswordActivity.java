package com.zrquan.mobile.ui.authentic;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class UserRegisterSetPasswordActivity extends CommonActivity {
    private Context context;
    // see http://stackoverflow.com/questions/8771963/what-is-the-equivalent-of-settimeout-javascript-to-android
    private Handler mResendVerifyCounterHandler;
    private Runnable mResendVerifyCounterTask;
    private int mResendVerifyCounter = 60;
    private String mPhoneNum;
    private PopupWindow mSelIndustryPopup;

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
    @InjectView(R.id.regist_password_clear_btn)
    ImageView ivClearPasswordBtn;
    @InjectView(R.id.rg_register_type)
    RadioGroup rgRegisterType;
    @InjectView(R.id.rb_worker)
    RadioButton cbWorker;
    @InjectView(R.id.rb_student)
    RadioButton cbStudent;
    @InjectView(R.id.ll_industry)
    LinearLayout llIndustry;
    @InjectView(R.id.ll_school)
    LinearLayout llSchool;
    @InjectView(R.id.tv_tips)
    TextView tvInputTips;

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
            }
        });
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

    public void onEvent(AccountEvent event){
        CharSequence ch = "短信验证码：" + event.verifyCode;
        Toast.makeText(context, ch, Toast.LENGTH_LONG).show();
    }

    private void initNavigationBar() {
        tvTitle.setText(R.string.account_register_set_password);
        tvTitle.setVisibility(View.VISIBLE);
        tvBackBtn.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_resend_verify_code)
    public void onResendVerifyCodeClick(View view) {
        disableResendVerifyCode();
        Toast.makeText(this, "又发送了验证码", Toast.LENGTH_LONG).show();
        mResendVerifyCounterHandler.postDelayed(mResendVerifyCounterTask, 1000);
        AccountController.sendVerifyCode(mPhoneNum);
    }

    @OnClick(R.id.tv_btn_back)
    public void onBtnBackClick(View view) {
        doBack();
    }

    @OnClick(R.id.regist_password_clear_btn)
    public void regist_password_clear_btn(View v) {
        etPassword.setText("");
    }

    @OnTextChanged(R.id.et_password)
    public void onPasswordTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().trim().length() > 0) {
            ivClearPasswordBtn.setVisibility(View.VISIBLE);
        } else {
            ivClearPasswordBtn.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tv_industry)
    public void onIndustryLayoutClick(View v) {
        if (mSelIndustryPopup == null) {
            initSelIndustryPopup();
        }
        Rect location = ScreenUtils.locateView(llVerifyCode);
        mSelIndustryPopup.setAnimationStyle(R.style.ComposePopupAnimation);
        mSelIndustryPopup.showAtLocation(findViewById(android.R.id.content), Gravity.NO_GRAVITY, 0, location.top);
        mSelIndustryPopup.update();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        doBack();
    }

    private void initSelIndustryPopup() {
        View popupView = getLayoutInflater().inflate(R.layout.widget_cascade_list_picker, null);
        Rect location = ScreenUtils.locateView(llVerifyCode);
        Point screenSize = ScreenUtils.getScreenSize(context);
        int popUpHeight = screenSize.y - location.top;
        mSelIndustryPopup = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, popUpHeight, true);
        mSelIndustryPopup.setBackgroundDrawable(new BitmapDrawable());
        mSelIndustryPopup.setTouchable(true);
        mSelIndustryPopup.setOutsideTouchable(true);

        popupView.findViewById(R.id.list_picker_edge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelIndustryPopup.dismiss();
            }
        });
    }

    private boolean checkPassword(String s) {
        Matcher matcher = RegUtils.getInstance().getPasswordPattern().matcher(s);
        if (!matcher.matches()) {
            tvInputTips.setText(R.string.pwd_tips);
            tvInputTips.setTextColor(getResources().getColor(R.color.main_highlight_text_color));
            return false;
        }
//        else {
//            tvInputTips.setText(R.string.pwd_tips);
//            tvInputTips.setTextColor(getResources().getColor(R.color.main_content_subtitle_text_color));
//        }
        return true;
    }

    private void enableResendVerifyCode() {
        btnResendVerifyCode.setEnabled(true);
        btnResendVerifyCode.setText(R.string.new_regist_resent_verification_content);
        btnResendVerifyCode.setTextColor(getResources().getColor(R.color.main_button_shadow_text_color_for_light_color_button));
        mResendVerifyCounterHandler.removeCallbacks(mResendVerifyCounterTask);
    }

    private void disableResendVerifyCode() {
        btnResendVerifyCode.setEnabled(false);
        mResendVerifyCounter = 60;
        String msg = getResources().getString(R.string.new_regist_resent_verification_code_bt)
                .replace("%s", Integer.toString(mResendVerifyCounter));
        btnResendVerifyCode.setText(msg);
        btnResendVerifyCode.setTextColor(getResources().getColor(R.color.main_button_disabled_text_color_for_light_color_button));
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
