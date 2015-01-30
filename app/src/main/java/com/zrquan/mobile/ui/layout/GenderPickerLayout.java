package com.zrquan.mobile.ui.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.zrquan.mobile.R;
import com.zrquan.mobile.widget.picker.ScrollerNumberPicker;

import java.util.ArrayList;

public class GenderPickerLayout extends LinearLayout {

    /** 滑动控件 */
    private ScrollerNumberPicker genderPicker;

    private Context context;

    public GenderPickerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public GenderPickerLayout(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.picker_gender, this);

        genderPicker = (ScrollerNumberPicker) findViewById(R.id.gender);

        ArrayList<String> gender = new ArrayList<>();
        gender.add("男");
        gender.add("女");
        genderPicker.setData(gender);
        genderPicker.setDefault(0);
    }
}
