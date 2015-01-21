package com.zrquan.mobile.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zrquan.mobile.R;
import com.zrquan.mobile.ZrquanApplication;

public class MessageFragment extends Fragment {

    private Context context;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            context = getActivity().getApplicationContext();
            if(!((ZrquanApplication) getActivity().getApplicationContext()).getAccount().isLogin()) {
                rootView = inflater.inflate(R.layout.visitor_tab_msg_fragment, container, false);
                initVisitorNavigationBar(rootView);
                initVisitorContentView(rootView);
            }

//            rootView = inflater.inflate(R.layout.activity_message, container, false);
        } else {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }

        return rootView;
    }

    private void initVisitorNavigationBar(View v) {
        TextView tvTitle = (TextView) v.findViewById(R.id.titleText);
        tvTitle.setText(R.string.main_news);
        tvTitle.setVisibility(View.VISIBLE);

        TextView tvBtnRegister = (TextView) v.findViewById(R.id.tv_btn_register);
        tvBtnRegister.setVisibility(View.GONE);

        TextView tvBtnLogin = (TextView) v.findViewById(R.id.tv_btn_login);
        tvBtnLogin.setVisibility(View.GONE);
    }

    private void initVisitorContentView(View v) {
        TextView tvBtnRegister = (TextView) v.findViewById(R.id.tv_btn_content_register);
        tvBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), UserRegisterActivity.class);
                getActivity().startActivity(myIntent);
                getActivity().overridePendingTransition(R.anim.right2left_enter, R.anim.right2left_exit );
            }
        });

        TextView tvBtnLogin = (TextView) v.findViewById(R.id.tv_btn_content_login);
        tvBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), UserLoginActivity.class);
                getActivity().startActivity(myIntent);
                getActivity().overridePendingTransition(R.anim.right2left_enter, R.anim.right2left_exit );
            }
        });
    }
}
