package com.zrquan.mobile.ui.popup;

import android.content.Context;
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
    private int currentSelParentIndustryPosition;
    private int currentSelChildIndustryPosition;
    private int currentVisitParentIndustryPosition;
    private OnSelectIndustry mOnSelectIndustry;

    @InjectView(R.id.lv_picker_left)
    ListView lvPickerLeft;
    @InjectView(R.id.lv_picker_right)
    ListView lvPickerRight;

    public SelIndustryPopup (Context context, int width, int height, int lastIndustryId) {
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

            //暂时打开时默认显示计算机类别（不勾选任何industry）
            //Todo:根据传入的lastIndustryId,初始化currentSelParentIndustryPosition和currentSelChildIndustryPosition
            int initPosition = 0;
//            if(lastIndustryId != -1) {
//                Industry mLastIndustry = mIndustryDao.query(mIndustryDao.queryBuilder().where()
//                        .eq(Industry.ID_FIELD_NAME, lastIndustryId).prepare()).get(0);
//            }

            ((ParentIndustryAdapter) lvPickerLeft.getAdapter()).setCurrentPosition(initPosition);
            parentIndustryAdapter.notifyDataSetChanged();
            initChildIndustry(initPosition);


            lvPickerLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    currentVisitParentIndustryPosition = position;
                    initChildIndustry(position);
                    ((ParentIndustryAdapter) lvPickerLeft.getAdapter()).setCurrentPosition(position);
                    ((ParentIndustryAdapter) lvPickerLeft.getAdapter()).notifyDataSetChanged();
                }
            });


            lvPickerRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    currentSelParentIndustryPosition = currentVisitParentIndustryPosition;
                    currentSelChildIndustryPosition = position;
                    ((ChildIndustryAdapter) lvPickerRight.getAdapter()).setCurrentPosition(position);
                    ((ChildIndustryAdapter) lvPickerRight.getAdapter()).notifyDataSetChanged();

                    if(mOnSelectIndustry != null) {
                        Industry parentIndustry =(Industry) lvPickerLeft.getAdapter().getItem(currentSelParentIndustryPosition);
                        Industry childIndustry = (Industry) lvPickerRight.getAdapter().getItem(currentSelChildIndustryPosition);
                        String displayText = parentIndustry.getName() + '/' + childIndustry.getName();
                        mOnSelectIndustry.OnSelectIndustry(displayText, childIndustry.getId());
                    }
                    dismiss();
                }
            });
        } catch (SQLException e) {
            LogUtils.d("Load parent industries from db crash..", e);
        }
    }

    public void setOnSelectIndustry(OnSelectIndustry onSelectIndustry) {
        this.mOnSelectIndustry = onSelectIndustry;
    }

    private void initChildIndustry(int position) {
        try {
            int parentIndustryId = mParentIndustryList.get(position).getId();
            List<Industry> childIndustryList = mIndustryDao.query(mIndustryDao.queryBuilder().where()
                    .eq(Industry.PARENT_INDUSTRY_ID_FIELD_NAME, parentIndustryId).prepare());
            ChildIndustryAdapter childIndustryAdapter = new ChildIndustryAdapter(context, childIndustryList);
            if(currentSelParentIndustryPosition == position) {
                childIndustryAdapter.setCurrentPosition(currentSelChildIndustryPosition);
            } else {
                childIndustryAdapter.setCurrentPosition(-1);
            }
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

    public static interface OnSelectIndustry {
        public void OnSelectIndustry(String displayText, int childIndustryId);
    }
}
