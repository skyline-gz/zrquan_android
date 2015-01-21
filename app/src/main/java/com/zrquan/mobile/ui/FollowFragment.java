package com.zrquan.mobile.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zrquan.mobile.R;

public class FollowFragment extends Fragment {
    private Context context;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            context = getActivity().getApplicationContext();
            rootView = inflater.inflate(R.layout.visitor_tab_follow_fragment, container, false);
            initVisitorNavigationBar(rootView);
        } else {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }

        return rootView;
    }

    public void initVisitorNavigationBar(View view) {

    }
}
