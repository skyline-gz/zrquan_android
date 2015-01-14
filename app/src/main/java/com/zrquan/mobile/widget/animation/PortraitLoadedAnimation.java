package com.zrquan.mobile.widget.animation;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

//头像加载完成后，显示的特效，从上到下的渐变显示
public class PortraitLoadedAnimation {
    public static PortraitLoadedAnimation getInstance() {
        return InnerClass.instance;
    }

    public Animation getMessage1PictureEnterAnimation() {
        AlphaAnimation localAlphaAnimation = new AlphaAnimation(0.0F, 1.0F);
        localAlphaAnimation.setDuration(300L);
        return localAlphaAnimation;
    }

    public Animation getPortraitEnterAnimation() {
        AlphaAnimation localAlphaAnimation = new AlphaAnimation(0.0F, 1.0F);
        localAlphaAnimation.setDuration(300L);
        ScaleAnimation localScaleAnimation = new ScaleAnimation(1.0F, 1.0F, 0.0F, 1.0F);
        localScaleAnimation.setDuration(200L);
        AnimationSet localAnimationSet = new AnimationSet(true);
        localAnimationSet.addAnimation(localAlphaAnimation);
        localAnimationSet.addAnimation(localScaleAnimation);
        return localAnimationSet;
    }

    private static class InnerClass {
        private static PortraitLoadedAnimation instance  = new PortraitLoadedAnimation();
    }
}