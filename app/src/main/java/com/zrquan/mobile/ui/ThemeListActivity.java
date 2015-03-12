package com.zrquan.mobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zrquan.mobile.R;
import com.zrquan.mobile.controller.PostController;
import com.zrquan.mobile.event.Post.SaveEvent;
import com.zrquan.mobile.model.Post;
import com.zrquan.mobile.support.enums.ServerCode;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class ThemeListActivity extends AbstractItemListActivity {

    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_right)
    TextView tvPublish;

    private String content;
    private int anonymousFlag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        content = intent.getStringExtra("content");
        anonymousFlag = intent.getIntExtra("anonymousFlag", 0);
        tvTitle.setText(R.string.title_add_themes);
        tvPublish.setText(R.string.btn_header_bar_publish);
    }

    @OnClick(R.id.tv_right)
    public void onTvPublishClick(View v) {
        Post post = new Post();
        post.setAnonymousFlag(anonymousFlag);
        post.setContent(content);
        int[] themeIds = new int[]{1, 2};
        PostController.create(post, themeIds);
    }

    public void onEvent(SaveEvent saveEvent) {
        if (saveEvent.getServerCode() != ServerCode.S_OK) {
            // TODO show error message
        } else {
            finish();
        }
    }

}
