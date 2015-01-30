package com.zrquan.mobile.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.zrquan.mobile.R;
import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.ui.common.CommonFragment;
import com.zrquan.mobile.ui.viewholder.UserProfileContentViewHolder;
import com.zrquan.mobile.ui.viewholder.VisitorContentViewHolder;
import com.zrquan.mobile.widget.scrollview.PullScrollView;

public class ProfileFragment extends CommonFragment implements PullScrollView.OnTurnListener {

    private Context context;
    private View rootView;
    private VisitorContentViewHolder mVisitorContentViewHolder;

    private PullScrollView mScrollView;
    private ImageView mHeadImg;
    private TableLayout mMainLayout;

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
                new UserProfileContentViewHolder(this, rootView);
                initView(rootView);
                showTable();
            }
        } else {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }

        setRetainInstance(true);
        return rootView;
    }

    protected void initView(View v) {
        mScrollView = (PullScrollView) v.findViewById(R.id.scroll_view);
        mHeadImg = (ImageView) v.findViewById(R.id.background_img);
        mMainLayout = (TableLayout) v.findViewById(R.id.table_layout);
        mScrollView.setHeader(mHeadImg);
        mScrollView.setOnTurnListener(this);
    }

    public void showTable() {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.leftMargin = 30;
        layoutParams.bottomMargin = 10;
        layoutParams.topMargin = 10;

        for (int i = 0; i < 30; i++) {
            TableRow tableRow = new TableRow(context);
            TextView textView = new TextView(context);
            textView.setText("Test pull down scroll view " + i);
            textView.setTextSize(20);
            textView.setPadding(15, 15, 15, 15);

            tableRow.addView(textView, layoutParams);
            if (i % 2 != 0) {
                tableRow.setBackgroundColor(Color.LTGRAY);
            } else {
                tableRow.setBackgroundColor(Color.WHITE);
            }

            final int n = i;
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Click item " + n, Toast.LENGTH_SHORT).show();
                }
            });

            mMainLayout.addView(tableRow);
        }
    }

    @Override
    public void onTurn() {

    }
}
