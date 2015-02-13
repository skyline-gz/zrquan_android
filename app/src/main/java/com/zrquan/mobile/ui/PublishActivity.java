package com.zrquan.mobile.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zrquan.mobile.R;
import com.zrquan.mobile.support.util.ScreenUtils;
import com.zrquan.mobile.ui.common.CommonActivity;
import com.zrquan.mobile.ui.viewholder.EmojiPanelViewHolder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PublishActivity extends CommonActivity{
    protected Context context;
    protected EmojiPanelViewHolder emojiPanelViewHolder;

    @InjectView(R.id.common_publish_emoji_btn)
    Button btnCommonPublishEmoji;
    @InjectView(R.id.common_publish_content_txt)
    EditText etCommonPublishContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_publish);
        ButterKnife.inject(this);
        context = getApplicationContext();
        emojiPanelViewHolder = new EmojiPanelViewHolder((LinearLayout)findViewById(R.id.common_publish_emoji_panel));
        emojiPanelViewHolder.fillViews(context, etCommonPublishContent, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.up2down_exit);
    }

    @OnClick(R.id.common_publish_emoji_btn)
    public void onBtnCommonPublishEmojiClick(View v) {
        emojiPanelViewHolder.show();
        ScreenUtils.hideSoftInput(context, etCommonPublishContent);
    }

    @OnClick(R.id.common_publish_content_txt)
    public void onEditTextCommonPublishContent(View v) {
        emojiPanelViewHolder.hide();
    }
}
