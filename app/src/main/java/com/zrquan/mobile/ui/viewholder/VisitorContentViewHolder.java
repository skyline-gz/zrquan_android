package com.zrquan.mobile.ui.viewholder;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zrquan.mobile.R;
import com.zrquan.mobile.ui.authentic.UserLoginActivity;
import com.zrquan.mobile.ui.authentic.UserRegisterActivity;
import com.zrquan.mobile.ui.common.CommonFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class VisitorContentViewHolder{

    private View mVisitorContentView;
    private CommonFragment context;

    @InjectView(R.id.titleText)
    TextView tvTitle;
    @InjectView(R.id.tv_btn_register)
    TextView tvNavBtnRegister;
    @InjectView(R.id.tv_btn_login)
    TextView tvNavBtnLogin;

    public VisitorContentViewHolder(CommonFragment context, View view) {
        ButterKnife.inject(this, view);
        mVisitorContentView = view;
        this.context = context;
    }

    public void dispose() {
        ButterKnife.reset(this);
    }

    public void initVisitorNavigationBar(int Resource) {
        tvTitle.setText(Resource);
        tvTitle.setVisibility(View.VISIBLE);
        tvNavBtnRegister.setVisibility(View.GONE);
        tvNavBtnLogin.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_btn_content_register)
    public void onContentRegisterClick() {
        Intent myIntent = new Intent(context.getActivity(), UserRegisterActivity.class);
        context.getActivity().startActivity(myIntent);
        context.getActivity().overridePendingTransition(R.anim.right2left_enter, R.anim.right2left_exit);
    }

    @OnClick(R.id.tv_btn_content_login)
    public void onContentLoginCLick() {
        Intent myIntent = new Intent(context.getActivity(), UserLoginActivity.class);
        context.getActivity().startActivity(myIntent);
        context.getActivity().overridePendingTransition(R.anim.right2left_enter, R.anim.right2left_exit);
    }
}
