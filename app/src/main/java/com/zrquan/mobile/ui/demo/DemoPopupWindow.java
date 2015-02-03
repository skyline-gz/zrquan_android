package com.zrquan.mobile.ui.demo;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

import com.zrquan.mobile.R;

public class DemoPopupWindow extends PopupWindow implements View.OnClickListener{
    private Context context;

    public DemoPopupWindow(Context context, View contentView, int width, int height) {
        super(contentView, width, height);
        this.context = context;
        init();
    }

    private void init() {
        setFocusable(true);
        setClippingEnabled(true);
        setOutsideTouchable(false);
        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.popover_background_right));
        update();
    }

    @Override
    public void onClick(View v) {

    }
}
