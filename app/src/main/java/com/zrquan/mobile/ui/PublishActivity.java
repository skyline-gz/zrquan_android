package com.zrquan.mobile.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zrquan.mobile.R;
import com.zrquan.mobile.model.ImageDesc;
import com.zrquan.mobile.support.util.AndroidIOUtils;
import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.support.util.SDCardUtils;
import com.zrquan.mobile.support.util.ScreenUtils;
import com.zrquan.mobile.ui.common.CommonActivity;
import com.zrquan.mobile.ui.viewholder.EmojiPanelViewHolder;
import com.zrquan.mobile.widget.multipleimagepick.MultipleImagePickActivity;

import java.io.File;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PublishActivity extends CommonActivity{
    protected Context context;
    protected EmojiPanelViewHolder emojiPanelViewHolder;
    private String mTempCameraPath;
    private ArrayList<ImageDesc> uploadImages;

    public static final int REQUEST_CODE_MULTIPLE_IMAGE_PICK = 1;
    public static final int REQUEST_CODE_CAMERA_IMAGE_PICK = 2;

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
        uploadImages = new ArrayList<>();
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
                        Intent intent = new Intent(MultipleImagePickActivity.ACTION_MULTIPLE_PICK);
                        startActivityForResult(intent, REQUEST_CODE_MULTIPLE_IMAGE_PICK);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_MULTIPLE_IMAGE_PICK) {
            if(resultCode == RESULT_OK) {
                String[] selectedImagePaths = data.getStringArrayExtra(MultipleImagePickActivity.IMAGES_PATH_EXTRA);
                for(String selectedImagePath : selectedImagePaths) {
                    prepareForUploadAndDisplay(selectedImagePath);
                }
            }
        } else if (requestCode == REQUEST_CODE_CAMERA_IMAGE_PICK) {
            if(resultCode == RESULT_OK) {
                if(mTempCameraPath != null) {
                    prepareForUploadAndDisplay(mTempCameraPath);
                    mTempCameraPath = null;
                }
            }
        }
    }

    private void prepareForUploadAndDisplay(String imagePath) {
        String contentType = AndroidIOUtils.getMimeType(imagePath);
        File imageFile = new File(imagePath);
        String fileName = imageFile.getName();

        ImageDesc imageDesc = new ImageDesc();
        imageDesc.setOriginalPath(imagePath);
        imageDesc.setContentType(contentType);
        imageDesc.setFileName(fileName);

        //如果不是Gif 类型，则根据屏幕高宽生成缩略图
        if(!AndroidIOUtils.isGifType(contentType)) {
            Point screenSize = ScreenUtils.getScreenSize(context);
        }

        //Todo 生成用于上传的缩略图
    }

    private void startCamera() {
        if (SDCardUtils.sdCardAvailable()) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            File localFile = new File(SDCardUtils.tempSendPictureDir(getApplicationContext())
                    , SDCardUtils.tempSendPictureName());
            this.mTempCameraPath = localFile.getAbsolutePath();
            intent.putExtra("output", Uri.fromFile(localFile));
            startActivityForResult(intent, REQUEST_CODE_CAMERA_IMAGE_PICK);
            return;
        }
        LogUtils.w(LOG_TAG, "存储卡不可用");
    }
}
