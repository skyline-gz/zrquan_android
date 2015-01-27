package com.zrquan.mobile.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zrquan.mobile.R;
import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.ui.common.CommonFragment;
import com.zrquan.mobile.ui.viewholder.VisitorContentViewHolder;

public class MessageFragment extends CommonFragment {

    private Context context;
    private View rootView;
    private VisitorContentViewHolder mVisitorContentViewHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        boolean isLogin = ((ZrquanApplication) getActivity().getApplicationContext()).getAccount().isLogin();

        if (rootView == null) {
            context = getActivity().getApplicationContext();
            if(!isLogin) {
                rootView = inflater.inflate(R.layout.visitor_tab_msg_fragment, container, false);
                mVisitorContentViewHolder = new VisitorContentViewHolder(this, rootView);
                mVisitorContentViewHolder.initVisitorNavigationBar(R.string.main_news);
            } else {
//            rootView = inflater.inflate(R.layout.activity_message, container, false);
                rootView = new View(context);
            }
        } else {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }

        return rootView;
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        if (mVisitorContentViewHolder != null) {
//            mVisitorContentViewHolder.dispose();
//            mVisitorContentViewHolder = null;
//        }
//    }
}
