package com.zrquan.mobile.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zrquan.mobile.R;
import com.zrquan.mobile.ui.common.CommonActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public abstract class AbstractPublishActivity extends CommonActivity {

    @InjectView(R.id.txt_content)
    EditText txtContent;
    @InjectView(R.id.tv_right)
    TextView tvNext;
    @InjectView(R.id.tv_left)
    TextView tvBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        ButterKnife.inject(this);
        tvNext.setText(R.string.btn_header_bar_next);
        tvBack.setText(R.string.btn_header_bar_cancel);
        tvTitle.setText(getTitleStringRes());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.up2down_exit);
    }

    @OnClick(R.id.tv_right)
    public abstract void onTvNextClick(View v);

    @OnClick(R.id.tv_left)
    public void onTvBackClick(View v) {
        onBackPressed();
    }

    protected int getLayoutResourceId() {
        return R.layout.activity_common_publish;
    }

    protected abstract int getTitleStringRes();
}
