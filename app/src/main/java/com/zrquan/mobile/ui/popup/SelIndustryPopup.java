package com.zrquan.mobile.ui.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.zrquan.mobile.R;

public class SelIndustryPopup extends PopupWindow {
    private Context context;

    public SelIndustryPopup (Context context, int width, int height) {
        super(width, height);
        this.context = context;
        init();
    }

    private void init() {
        //http://stackoverflow.com/questions/2212197/how-to-get-a-layout-inflater-given-a-context
        View popupView = LayoutInflater.from(context).inflate(R.layout.widget_cascade_list_picker, null);
        setContentView(popupView);
        setAnimationStyle(R.style.ComposePopupAnimation);
        setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setTouchable(true);
        setOutsideTouchable(true);
        setFocusable(true);
        update();

        popupView.findViewById(R.id.list_picker_edge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
