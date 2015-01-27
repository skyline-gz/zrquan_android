package com.zrquan.mobile.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.zrquan.mobile.R;
import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.ui.common.CommonFragment;
import com.zrquan.mobile.ui.viewholder.VisitorContentViewHolder;

public class FollowFragment extends CommonFragment {

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
                rootView = inflater.inflate(R.layout.visitor_tab_follow_fragment, container, false);
                mVisitorContentViewHolder = new VisitorContentViewHolder(this, rootView);
                mVisitorContentViewHolder.initVisitorNavigationBar(R.string.main_follows);
            } else {
                rootView = new View(context);
            }
        } else {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        if (!isLogin) {
            animRotateHomeDash(rootView);
        }
        return rootView;
    }

    private void animRotateHomeDash(View v) {
        ImageView ivVisitorHomeDash = (ImageView)v.findViewById(R.id.ivVisitorHomeDash);
        RotateAnimation r = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        r.setInterpolator(AnimationUtils.loadInterpolator(context, android.R.interpolator.linear));
        r.setRepeatCount(Animation.INFINITE);
        r.setDuration(10000);
        ivVisitorHomeDash.startAnimation(r);
    }
}
