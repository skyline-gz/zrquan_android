package com.zrquan.mobile.ui.viewholder;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.zrquan.mobile.R;
import com.zrquan.mobile.support.util.ScreenUtils;
import com.zrquan.mobile.ui.adapter.EmojiAdapter;
import com.zrquan.mobile.support.util.LogUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EmojiPanelViewHolder {

    private LinearLayout emojiPanel;

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

    public void fillViews(final Context context, final EditText editText, OnClickListener onClickListener) {
        sendButton.setOnClickListener(onClickListener);
        final EmojiAdapter emojiAdapter = new EmojiAdapter(context);
        emojiGrid.setAdapter(emojiAdapter);
        emojiGrid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (parent == EmojiPanelViewHolder.this.emojiGrid) {
                    String str = (String) emojiAdapter.getItem(position);
                    int i = Math.max(editText.getSelectionStart(), 0);
                    int j = Math.max(editText.getSelectionEnd(), 0);
                    editText.getText().replace(Math.min(i, j), Math.max(i, j), str, 0, str.length());
                    return;
                }
                ScreenUtils.hideSoftInput(context, editText);
            }
        });
//        this.delButton.setOnClickListener(new OnClickListener() {
//            public void onClick(View paramAnonymousView) {
//                editText.getSelectionStart();
//                int i = Math.max(editText.getSelectionStart(), 0);
//                int j = Math.max(editText.getSelectionEnd(), 0);
//                String str1 = editText.getText().toString();
//                String str2 = str1.substring(0, Math.min(i, j));
//                String str3 = str1.substring(Math.max(i, j), str1.length());
//                int k = str2.lastIndexOf("[");
//                int m = str2.lastIndexOf("]");
//                StringBuilder localStringBuilder = new StringBuilder();
//                int n;
//                if ((k < m) && (m == -1 + str2.length()) && (i == j) && (k >= 0)) {
//                    localStringBuilder.append(str2.substring(0, k));
//                    n = k;
//                    int i1 = str3.indexOf("[");
//                    if ((i1 <= str3.indexOf("]")) || (i1 < 0) || (i1 >= str3.length()))
//                        break label313;
//                    localStringBuilder.append(str3.substring(i1));
//                }
//                while (true) {
//                    if ((localStringBuilder.length() != str1.length()) || (n <= 0))
//                        break label324;
//                    editText.setText(str1.substring(0, n - 1).concat(str1.substring(n)));
//                    editText.setSelection(n - 1);
//                    return;
//                    if ((k > m) && (m >= 0)) {
//                        localStringBuilder.append(str2.substring(0, m + 1));
//                        n = m + 1;
//                        break;
//                    }
//                    localStringBuilder.append(str2);
//                    n = str2.length();
//                    break;
//                    label313:
//                    localStringBuilder.append(str3);
//                }
//                label324:
//                editText.setText(localStringBuilder.toString());
//                editText.setSelection(n);
//            }
//        });
//        editText.addTextChangedListener(new TextWatcher() {
//            public void afterTextChanged(Editable paramAnonymousEditable) {
//            }
//
//            public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
//            }
//
//            public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
//                int i;
//                if ((paramAnonymousCharSequence != null) && (paramAnonymousCharSequence.length() > 0)) {
//                    i = 1;
//                    if ((i == 0) || (EmojiPanelViewHolder.this.sendButton.isEnabled()))
//                        break label52;
//                    EmojiPanelViewHolder.this.sendButton.setEnabled(true);
//                }
//                label52:
//                while ((i != 0) || (!EmojiPanelViewHolder.this.sendButton.isEnabled())) {
//                    return;
//                    i = 0;
//                    break;
//                }
//                EmojiPanelViewHolder.this.sendButton.setEnabled(false);
//            }
//        });
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
}