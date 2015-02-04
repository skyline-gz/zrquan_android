package com.zrquan.mobile.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zrquan.mobile.R;
import com.zrquan.mobile.ui.common.CommonActivity;
import com.zrquan.mobile.ui.viewholder.EmojiPanelViewHolder;

public class PublishActivity extends CommonActivity{
    protected EmojiPanelViewHolder emojiPanelViewHolder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_publish);

        emojiPanelViewHolder = new EmojiPanelViewHolder((LinearLayout)findViewById(R.id.common_publish_emoji_panel));
        emojiPanelViewHolder.fillViews(getApplicationContext(), (EditText)findViewById(R.id.common_publish_content_txt),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

        emojiPanelViewHolder.show();
    }
}
