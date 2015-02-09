package com.zrquan.mobile.ui.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.j256.ormlite.dao.Dao;
import com.zrquan.mobile.R;
import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.model.Industry;
import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.support.util.ViewUtils;
import com.zrquan.mobile.ui.adapter.ChildIndustryAdapter;
import com.zrquan.mobile.ui.adapter.ParentIndustryAdapter;

import java.sql.SQLException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SelIndustryPopup extends PopupWindow {
    private Context context;
    private List<Industry> mParentIndustryList;
    private Dao<Industry, Integer> mIndustryDao;

    @InjectView(R.id.lv_picker_left)
    ListView lvPickerLeft;
    @InjectView(R.id.lv_picker_right)
    ListView lvPickerRight;

    public SelIndustryPopup (Context context, int width, int height) {
        super(width, height);
        this.context = context;
        init();

        try {
            mIndustryDao = ZrquanApplication.getInstance()
                    .getDatabaseHelper().getDao(Industry.class);

            mParentIndustryList = mIndustryDao.query(mIndustryDao.queryBuilder().where()
                    .isNull(Industry.PARENT_INDUSTRY_ID_FIELD_NAME)
                    .prepare());

            ParentIndustryAdapter parentIndustryAdapter = new ParentIndustryAdapter(context, mParentIndustryList);
            lvPickerLeft.setAdapter(parentIndustryAdapter);
            ((ParentIndustryAdapter) lvPickerLeft.getAdapter()).setCurrentPosition(0);
            parentIndustryAdapter.notifyDataSetChanged();

            //默认选取第一个
            initChildIndustry(mParentIndustryList.get(0).getId());

            lvPickerLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    initChildIndustry(mParentIndustryList.get(position).getId());
                    ((ParentIndustryAdapter) lvPickerLeft.getAdapter()).setCurrentPosition(position);
                    ((ParentIndustryAdapter) lvPickerLeft.getAdapter()).notifyDataSetChanged();
                }
            });
        } catch (SQLException e) {
            LogUtils.d("Load parent industries from db crash..", e);
        }
    }

    private void initChildIndustry(int parentIndustryId) {
        try {
            List<Industry> childIndustryList = mIndustryDao.query(mIndustryDao.queryBuilder().where()
                    .eq(Industry.PARENT_INDUSTRY_ID_FIELD_NAME, parentIndustryId).prepare());
            ChildIndustryAdapter childIndustryAdapter = new ChildIndustryAdapter(context, childIndustryList);
            lvPickerRight.setAdapter(childIndustryAdapter);
            childIndustryAdapter.notifyDataSetChanged();
        } catch (SQLException e) {
            LogUtils.d("Load child industries from db crash..", e);
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
