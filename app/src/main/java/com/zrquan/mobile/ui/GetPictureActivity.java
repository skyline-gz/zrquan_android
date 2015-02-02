package com.zrquan.mobile.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.support.util.SDCardUtils;
import com.zrquan.mobile.ui.common.CommonActivity;

import java.io.File;

public class GetPictureActivity extends CommonActivity {
    private final String LOG_TAG = "GetPictureActivity";

    public static final int REQUEST_CODE_CAMERA = 1;
    public static final int REQUEST_CODE_GALLERY = 2;
    public static final int REQUEST_CODE_CROP = 3;
    public static final int RESULT_NOT_FOUND = 100;

    private boolean mCrop;
    private int mHeight;
    private int mSource;
    private String mTempCameraPath = null;
    private String mTempCropedPath = null;
    private int mWidth;

    private void back(String paramString) {
        Intent localIntent = new Intent();
        localIntent.putExtra("path", paramString);
        setResult(-1, localIntent);
        finish();
    }

    private void crop(String paramString) {
        String str = SDCardUtils.tempSendPictureDir(getApplicationContext()) + "/" + System.currentTimeMillis() + ".png";
        this.mTempCropedPath = str;
        File localFile1 = new File(paramString);
        Uri localUri = Uri.fromFile(localFile1);
        Intent localIntent = new Intent("com.android.camera.action.CROP");
        localIntent.setDataAndType(localUri, "image/*");
        localIntent.putExtra("crop", "true");
        localIntent.putExtra("aspectX", this.mWidth);
        localIntent.putExtra("aspectY", this.mHeight);
        localIntent.putExtra("outputX", this.mWidth);
        localIntent.putExtra("outputY", this.mHeight);
        localIntent.putExtra("scale", true);
        localIntent.putExtra("noFaceDetection", true);
        localIntent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        File localFile2 = new File(str);
        localIntent.putExtra("output", Uri.fromFile(localFile2));
        localIntent.putExtra("return-data", false);
        try {
            startActivityForResult(localIntent, 3);
        } catch (ActivityNotFoundException localActivityNotFoundException) {
            LogUtils.e(LOG_TAG, "Crop Activity Not Found");
            localActivityNotFoundException.printStackTrace();
            back(null);
        }
    }

    private void getIntentData() {
        Intent localIntent = getIntent();
        if (localIntent != null) {
            this.mWidth = localIntent.getIntExtra("width", 0);
            this.mHeight = localIntent.getIntExtra("height", 0);
            this.mSource = localIntent.getIntExtra("source", 0);
            this.mCrop = localIntent.getBooleanExtra("crop", false);
        }
    }

    private void startCamera() {
        if (SDCardUtils.sdCardAvailable()) {
            Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
            File localFile = new File(SDCardUtils.tempSendPictureDir(getApplicationContext())
                    , SDCardUtils.tempSendPictureName());
            this.mTempCameraPath = localFile.getAbsolutePath();
            localIntent.putExtra("output", Uri.fromFile(localFile));
            startActivityForResult(localIntent, 1);
            return;
        }
        LogUtils.w(LOG_TAG, "存储卡不可用.");
//        showToast(2131361985);
    }

    private void startGallery() {
        Intent localIntent = new Intent("android.intent.action.PICK", null);
        localIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(localIntent, 2);
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        Cursor localCursor;
        if (paramInt1 == 1) {
            if (!TextUtils.isEmpty(this.mTempCameraPath)) {
                File localFile1 = new File(this.mTempCameraPath);
                if ((!localFile1.exists()) || (localFile1.length() == 0L)) {
                    finish();
                    return;
                }
                long l1 = System.currentTimeMillis();
                String str3 = this.mTempCameraPath;
                File localFile2 = new File(this.mTempCameraPath);
//                String str4 = ImageUtil.zoomOutAndSave4Send(str3, localFile2.getParent());
//                long l2 = System.currentTimeMillis() - l1;
//                StringBuilder localStringBuilder4 = new StringBuilder();
//                LogUtils.d(LOG_TAG, "2图片缩小处理耗时" + l2 / 1000L + "." + l2 % 1000L + ", newPath=" + str4);
//                if (this.mCrop) {
//                    crop(str4);
//                    return;
//                }
//                back(str4);
                return;
            }
            finish();
            return;
        }
        if (paramInt1 == 2 && (paramInt2 == -1) && (paramIntent != null)) {
            Uri localUri = paramIntent.getData();
            localCursor = getContentResolver().query(localUri, null, null, null, null);
            if ((localCursor == null) || (localCursor.getCount() < 1)) {
                LogUtils.d(LOG_TAG, "Gallery cursor is null");
                setResult(100);
                if ((localCursor != null) && (!localCursor.isClosed()))
                    localCursor.close();
            }
        }

        if (paramInt1 == 3) {
            String str1 = paramIntent.getDataString();
            StringBuilder localStringBuilder1 = new StringBuilder();
            LogUtils.i(LOG_TAG, "after crop, uriString=" + str1);
            if (TextUtils.isEmpty(str1)) {
                LogUtils.w(LOG_TAG, "uriString is null, usr saved path=" + this.mTempCropedPath);
            }
            for (String str2 = this.mTempCropedPath; ; str2 = Uri.parse(str1).getPath()) {
                LogUtils.i(LOG_TAG, "after crop, path=" + str2);
                back(str2);
                return;
            }
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        getIntentData();
        if (this.mSource == REQUEST_CODE_CAMERA) {
            startCamera();
        } else if (this.mSource == REQUEST_CODE_GALLERY) {
            startGallery();
        }
    }
}
