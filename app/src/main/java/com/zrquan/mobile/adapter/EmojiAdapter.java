package com.zrquan.mobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.zrquan.mobile.R;
import com.zrquan.mobile.support.util.BitmapUtils;

public class EmojiAdapter extends BaseAdapter {
    int emoji_width;
    String[] labels;
    private Context mContext;
    int padding;

    public EmojiAdapter(Context paramContext) {
        this.mContext = paramContext;
        this.emoji_width = this.mContext.getResources().getDimensionPixelSize(R.dimen.emoji_in_panel_width);
        this.padding = (this.emoji_width / 8);
        this.labels = this.mContext.getResources().getStringArray(R.array.emoji_labels);
    }

    @Override
    public int getCount() {
        if (this.labels != null)
            return this.labels.length;
        return 0;
    }

    @Override
    public Object getItem(int paramInt) {
        if (paramInt < this.labels.length)
            return this.labels[paramInt];
        return null;
    }

    @Override
    public long getItemId(int paramInt) {
        return 0L;
    }

    @Override
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        ImageView imageView = (ImageView) paramView;
        if (imageView == null) {
            imageView = new ImageView(this.mContext);
            imageView.setPadding(this.padding, this.padding, this.padding, this.padding);
            imageView.setLayoutParams(new LayoutParams(this.emoji_width, this.emoji_width));
        }
        BitmapUtils.setImageResource(imageView, R.drawable.zemoji_e001 + paramInt);
        return imageView;
    }

    public int getLabelIndex(String text) {
       int index = 0;
       for (String label : labels) {
           if(text.equals(label)) {
               return index;
           }
           index ++;
       }
       return -1;
    }

}