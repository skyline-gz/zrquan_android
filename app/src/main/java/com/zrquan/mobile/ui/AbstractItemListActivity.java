package com.zrquan.mobile.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zrquan.mobile.R;
import com.zrquan.mobile.ui.common.CommonActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public abstract class AbstractItemListActivity extends CommonActivity {

    @InjectView(R.id.list)
    protected ListView list;

    @InjectView(R.id.tv_left)
    TextView tvBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        ButterKnife.inject(this);
        tvBack.setText(R.string.btn_header_bar_cancel);
    }

    @OnClick(R.id.tv_left)
    public void onTvBackClick(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.up2down_exit);
    }

    protected int getLayoutResourceId() {
        return R.layout.activity_item_list;
    }
}
