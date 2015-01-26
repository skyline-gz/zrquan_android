package com.zrquan.mobile.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.zrquan.mobile.R;
import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.ui.common.CommonFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FollowFragment extends CommonFragment {

    private Context context;
    private View rootView;
    @InjectView(R.id.titleText) TextView tvTitle;
    @InjectView(R.id.tv_btn_register) TextView tvNavBtnRegister;
    @InjectView(R.id.tv_btn_login) TextView tvNavBtnLogin;
    @InjectView(R.id.ivVisitorHomeDash) ImageView ivVisitorHomeDash;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        boolean isLogin = ((ZrquanApplication) getActivity().getApplicationContext()).getAccount().isLogin();

        if (rootView == null) {
            context = getActivity().getApplicationContext();
            if(!isLogin) {
                rootView = inflater.inflate(R.layout.visitor_tab_follow_fragment, container, false);
                ButterKnife.inject(this, rootView);
                initVisitorNavigationBar(rootView);
            }
//            rootView = inflater.inflate(R.layout.visitor_tab_follow_fragment, container, false);
//            initVisitorNavigationBar(rootView);
        } else {
            ((ViewGroup) rootView.getParent()).removeView(rootView);

        }

        if (!isLogin) {
            animRotateHomeDash(rootView);
        }
        return rootView;
    }

    private void initVisitorNavigationBar(View v) {
        tvTitle.setText(R.string.main_follows);
        tvTitle.setVisibility(View.VISIBLE);
        tvNavBtnRegister.setVisibility(View.GONE);
        tvNavBtnLogin.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_btn_content_register)
    public void onContentRegisterClick() {
        Intent myIntent = new Intent(getActivity(), UserRegisterActivity.class);
        getActivity().startActivity(myIntent);
        getActivity().overridePendingTransition(R.anim.right2left_enter, R.anim.right2left_exit );
    }

    @OnClick(R.id.tv_btn_content_login)
    public void onContentLoginCLick() {
        Intent myIntent = new Intent(getActivity(), UserLoginActivity.class);
        getActivity().startActivity(myIntent);
        getActivity().overridePendingTransition(R.anim.right2left_enter, R.anim.right2left_exit);
    }

    private void animRotateHomeDash(View v) {
        RotateAnimation r = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        r.setInterpolator(AnimationUtils.loadInterpolator(context, android.R.interpolator.linear));
        r.setRepeatCount(Animation.INFINITE);
        r.setDuration(10000);
        ivVisitorHomeDash.startAnimation(r);
    }
}
