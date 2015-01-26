package com.zrquan.mobile.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zrquan.mobile.R;
import com.zrquan.mobile.support.util.RegUtils;
import com.zrquan.mobile.support.util.ScreenUtils;
import com.zrquan.mobile.ui.common.CommonActivity;

import java.util.regex.Matcher;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class UserRegisterSetPasswordActivity extends CommonActivity{
    private Context context;

    @InjectView(R.id.titleText)
    TextView tvTitle;
    @InjectView(R.id.tv_btn_back)
    TextView tvBackBtn;
    @InjectView(R.id.tv_tips)
    TextView tvInputTips;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.regist_password_clear_btn)
    ImageView ivClearPasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_user_register_set_password);
        ButterKnife.inject(this);
        initNavigationBar();
    }

    private void initNavigationBar() {
        tvTitle.setText(R.string.account_register_set_password);
        tvTitle.setVisibility(View.VISIBLE);
        tvBackBtn.setVisibility(View.VISIBLE);
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
        if(s.toString().trim().length() > 0) {
            ivClearPasswordBtn.setVisibility(View.VISIBLE);
        } else {
            ivClearPasswordBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        doBack();
    }

   private boolean checkPassword(String s) {
        Matcher matcher = RegUtils.getInstance().getPasswordPattern().matcher(s);
        if(!matcher.matches()) {
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

    private void doBack() {
        ScreenUtils.hideSoftInput(this);
        finish();
        overridePendingTransition(R.anim.left2right_enter, R.anim.left2right_exit);
    }
}
