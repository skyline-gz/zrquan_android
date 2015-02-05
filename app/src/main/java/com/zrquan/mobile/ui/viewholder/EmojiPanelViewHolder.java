package com.zrquan.mobile.ui.viewholder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.DynamicDrawableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.zrquan.mobile.R;
import com.zrquan.mobile.support.util.RegUtils;
import com.zrquan.mobile.support.util.ScreenUtils;
import com.zrquan.mobile.ui.adapter.EmojiAdapter;
import com.zrquan.mobile.support.util.LogUtils;

import java.util.regex.Matcher;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class EmojiPanelViewHolder {

    private LinearLayout emojiPanel;
    private EditText editText;
    private EmojiAdapter emojiAdapter;

    @InjectView(R.id.emoji_del_btn)
    View delButton;
    @InjectView(R.id.emoji_grid)
    GridView emojiGrid;
    @InjectView(R.id.emoji_send_btn)
    Button sendButton;

    public EmojiPanelViewHolder(LinearLayout linearLayout) {
        ButterKnife.inject(this, linearLayout);
        emojiPanel = linearLayout;
        emojiPanel.setVisibility(View.GONE);
    }

    public void fillViews(final Context context, EditText editText, OnClickListener onClickListener) {
        sendButton.setOnClickListener(onClickListener);
        emojiAdapter = new EmojiAdapter(context);
        this.editText = editText;
        emojiGrid.setAdapter(emojiAdapter);

        emojiGrid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (parent == EmojiPanelViewHolder.this.emojiGrid) {
                    String str = (String) emojiAdapter.getItem(position);
                    int i = Math.max(EmojiPanelViewHolder.this.editText.getSelectionStart(), 0);
                    int j = Math.max(EmojiPanelViewHolder.this.editText.getSelectionEnd(), 0);
                    EmojiPanelViewHolder.this.editText.getText().replace(Math.min(i, j), Math.max(i, j), str, 0, str.length());
                    return;
                }
                ScreenUtils.hideSoftInput(context, EmojiPanelViewHolder.this.editText);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //Changes have been made, some characters have just been replaced. The text is un-editable.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Todo:当输入的Text文字数目为0的时候，将sendButton disable
                //EmojiPanelViewHolder.this.sendButton.setEnabled(false);

                Spannable text = EmojiPanelViewHolder.this.editText.getText();
                Matcher matcher = RegUtils.getInstance().getEmojiPattern().matcher(text.toString());

                //Find the starting point of the first 'Bond'
                while(matcher.find()) {
                    String matchedText = matcher.group();
                    int iconIndex = EmojiPanelViewHolder.this.emojiAdapter.getLabelIndex(matchedText);
                    if (iconIndex != -1) {
                        int startIndex = matcher.start();
                        int endIndex = startIndex + matchedText.length();

                        Object spansExit[] = text.getSpans(startIndex, endIndex, EmojiconSpan.class);
                        if(spansExit.length == 0) {
                            text.setSpan(new EmojiconSpan(context, R.drawable.zemoji_e001 + iconIndex,
                                    (int) ScreenUtils.dpToPx(context, 22f)), startIndex, endIndex
                                    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText.setText("测试文字1测试文字2测试文字3测试文字4测试文字5测试文字6测试文字7测试文字8测试文字9测试文字10测试文字11");
    }

    @OnClick(R.id.emoji_del_btn)
    public void onEmojiDelBtnClick(View v) {
        KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
        editText.dispatchKeyEvent(event);
    }

    public int getEmojiPanelScreenPositionY() {
        int[] arrayOfInt = new int[2];
        this.emojiPanel.getLocationInWindow(arrayOfInt);
        return arrayOfInt[1];
    }

    public void hide() {
        if (this.emojiPanel.getVisibility() != View.GONE) {
            this.emojiPanel.setVisibility(View.GONE);
            LogUtils.i("hide");
        }
    }

    public boolean isShowing() {
        return this.emojiPanel.getVisibility() == View.VISIBLE;
    }

    public void show() {
        if (this.emojiPanel.getVisibility() != View.VISIBLE) {
            this.emojiPanel.setVisibility(View.VISIBLE);
            LogUtils.i("show");
        }
    }

    class EmojiconSpan extends DynamicDrawableSpan {
        private final Context mContext;
        private final int mResourceId;
        private final int mSize;
        private Drawable mDrawable;

        public EmojiconSpan(Context context, int resourceId, int size) {
            super();
            mContext = context;
            mResourceId = resourceId;
            mSize = size;
        }

        public Drawable getDrawable() {
            if (mDrawable == null) {
                try {
                    mDrawable = mContext.getResources().getDrawable(mResourceId);
                    int size = mSize;
                    mDrawable.setBounds(0, 0, size, size);
                } catch (Exception e) {
                    // swallow
                }
            }
            return mDrawable;
        }
    }
}