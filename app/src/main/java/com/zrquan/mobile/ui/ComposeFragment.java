package com.zrquan.mobile.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zrquan.mobile.R;
import com.zrquan.mobile.ZrquanApplication;

import butterknife.ButterKnife;

public class ComposeFragment extends Fragment {
    private Context context;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        boolean isLogin = ((ZrquanApplication) getActivity().getApplicationContext()).getAccount().isLogin();

        if (rootView == null) {
            context = getActivity().getApplicationContext();
            if(!isLogin) {
                rootView = inflater.inflate(R.layout.pop_up_window_compose, container, false);
                ButterKnife.inject(this, rootView);
                initCloseBtn(rootView);
            }
//            rootView = inflater.inflate(R.layout.visitor_tab_follow_fragment, container, false);
//            initVisitorNavigationBar(rootView);
        } else {
            ((ViewGroup) rootView.getParent()).removeView(rootView);

        }

//        if (!isLogin) {
//            animRotateHomeDash(rootView);
//        }
        return rootView;
    }

    public void initCloseBtn(View view) {

    }
}
