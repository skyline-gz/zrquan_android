package com.zrquan.mobile.ui.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.j256.ormlite.dao.Dao;
import com.zrquan.mobile.R;
import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.model.Industry;
import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.ui.adapter.ParentIndustryAdapter;

import java.sql.SQLException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SelIndustryPopup extends PopupWindow {
    private Context context;
    private List<Industry> industryList;
    @InjectView(R.id.lv_picker_left)
    ListView lvPickerLeft;
    @InjectView(R.id.lv_picker_right)
    ListView lvPickerRight;

    public SelIndustryPopup (Context context, int width, int height) {
        super(width, height);
        this.context = context;
        init();

        try {
                // get our dao
            Dao<Industry, Integer> industryDao = ZrquanApplication.getInstance()
                    .getDatabaseHelper().getDao(Industry.class);
            // query for all of the data objects in the database
//            industryList = industryDao.queryForAll();

            List<Industry> parentIndustryList = industryDao.query(industryDao.queryBuilder().where()
                    .isNull(Industry.PARENT_INDUSTRY_ID_FIELD_NAME)
                    .prepare());

//            List<Industry> parentIndustryList = industryDao.queryForAll();


            ParentIndustryAdapter parentIndustryAdapter = new ParentIndustryAdapter(context, parentIndustryList);
            lvPickerLeft.setAdapter(parentIndustryAdapter);
            parentIndustryAdapter.notifyDataSetChanged();
        } catch (SQLException e) {
            LogUtils.d("Load Industry from db crash..", e);
        }
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

        ButterKnife.inject(this, popupView);
    }
}
