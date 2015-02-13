package com.zrquan.mobile.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zrquan.mobile.R;
import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.support.util.SDCardUtils;
import com.zrquan.mobile.support.util.ScreenUtils;
import com.zrquan.mobile.ui.common.CommonActivity;
import com.zrquan.mobile.ui.viewholder.EmojiPanelViewHolder;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PublishActivity extends CommonActivity{
    protected Context context;
    protected EmojiPanelViewHolder emojiPanelViewHolder;
    private String mTempCameraPath;

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

    @OnClick(R.id.common_publish_pick_picture_btn)
    public void onBtnCommonPublishPickPictureClick(View v) {
        AlertDialog.Builder selectPictureBuilder = new AlertDialog.Builder(this);
        selectPictureBuilder.setItems(new String[]{"拍照", "从相册中选择"} , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        startCamera();
                        break;
                    case 1:
                        Intent intent1 = new Intent("zrquan.action.MULTIPLE_PICTURE_PICK");
                        startActivity(intent1);
                        break;
                    default:
                        dialog.dismiss();
                }
            }
        });
        AlertDialog selectPictureDialog = selectPictureBuilder.create();
        selectPictureDialog.setCanceledOnTouchOutside(true);
        selectPictureDialog.show();
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

    private void startCamera() {
        if (SDCardUtils.sdCardAvailable()) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            File localFile = new File(SDCardUtils.tempSendPictureDir(getApplicationContext())
                    , SDCardUtils.tempSendPictureName());
            this.mTempCameraPath = localFile.getAbsolutePath();
            intent.putExtra("output", Uri.fromFile(localFile));
            startActivity(intent);
            return;
        }
        LogUtils.w(LOG_TAG, "存储卡不可用");
    }
}
