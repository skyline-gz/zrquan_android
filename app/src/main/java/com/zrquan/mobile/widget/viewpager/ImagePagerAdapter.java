/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.zrquan.mobile.widget.viewpager;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.zrquan.mobile.widget.salvage.RecyclingPagerAdapter;
import com.zrquan.mobile.widget.view.RoundedImageView;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * ImagePagerAdapter
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter {

    private Context context;
    private List<Integer> imageIdList;

    private int size;
    private boolean isInfiniteLoop;

    public ImagePagerAdapter(Context context, List<Integer> imageIdList) {
        this.context = context;
        this.imageIdList = imageIdList;
        this.size = CollectionUtils.size(imageIdList);
        isInfiniteLoop = false;
    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : CollectionUtils.size(imageIdList);
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new RoundedImageView(context);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        float roundPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources().getDisplayMetrics());

        holder.imageView.setCornerRadius((float) roundPx);
        holder.imageView.mutateBackground(true);
        holder.imageView.setImageResource(imageIdList.get(getPosition(position)));
        holder.imageView.setScaleType(RoundedImageView.ScaleType.FIT_XY);
        return view;
    }

    private static class ViewHolder {

        RoundedImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}
