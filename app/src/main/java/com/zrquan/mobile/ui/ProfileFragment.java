package com.zrquan.mobile.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import com.zrquan.mobile.R;
import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.event.ProfileEvent;
import com.zrquan.mobile.support.enums.EventType;
import com.zrquan.mobile.support.util.ToastUtils;
import com.zrquan.mobile.ui.common.CommonFragment;
import com.zrquan.mobile.ui.viewholder.UserProfileContentViewHolder;
import com.zrquan.mobile.ui.viewholder.VisitorContentViewHolder;

import de.greenrobot.event.EventBus;

public class ProfileFragment extends CommonFragment{

    private Context context;
    private View rootView;
    private VisitorContentViewHolder mVisitorContentViewHolder;
    private UserProfileContentViewHolder mUserProfileContentViewHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        boolean isLogin = ((ZrquanApplication) getActivity().getApplicationContext()).getAccount().isLogin();

        if (rootView == null) {
            context = getActivity().getApplicationContext();
            if(!isLogin) {
                rootView = inflater.inflate(R.layout.visitor_tab_profile_fragment, container, false);
                mVisitorContentViewHolder = new VisitorContentViewHolder(this, rootView);
                mVisitorContentViewHolder.initVisitorNavigationBar(R.string.main_me);
            } else {
                rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);
                mUserProfileContentViewHolder = new UserProfileContentViewHolder(this, rootView);
            }
        } else {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }

        setRetainInstance(true);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEvent(ProfileEvent profileEvent) {
        if(profileEvent.getEventType() == EventType.PE_NET_RELOAD_AVATAR_BEGIN){
            ToastUtils.show(context, "上传头像成功");
            ImageLoader.getInstance().loadImage(profileEvent.getAvatarPath() , new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    mUserProfileContentViewHolder.reloadAvatar(loadedImage);
                }
            });
        }
    }
}
