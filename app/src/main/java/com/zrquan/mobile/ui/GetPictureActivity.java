package com.zrquan.mobile.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.zrquan.mobile.controller.ProfileController;
import com.zrquan.mobile.event.ProfileAvatarChangeEvent;
import com.zrquan.mobile.support.util.BitmapUtils;
import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.support.util.SDCardUtils;
import com.zrquan.mobile.ui.common.CommonActivity;

import java.io.File;

import de.greenrobot.event.EventBus;

public class GetPictureActivity extends CommonActivity {
    private final String LOG_TAG = "GetPictureActivity";

    public static final int REQUEST_CODE_CAMERA = 1;
    public static final int REQUEST_CODE_GALLERY = 2;
    public static final int REQUEST_CODE_CROP = 3;
    public static final int RESULT_NOT_FOUND = 100;

    //Indent 参数
    private int mSource;      //获取图片的来源，REQUEST_CODE_CAMERA　或　REQUEST_CODE_GALLERY
    private boolean mCrop;    //是否需要截图
    private int mHeight;      //图片高度
    private int mWidth;       //图片宽度
    
    private String mTempCameraPath = null;
    private String mTempCroppedPath = null;

    @Override
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CAMERA) {
            if (!TextUtils.isEmpty(this.mTempCameraPath)) {
                File cameraFile = new File(this.mTempCameraPath);
                if ((cameraFile.exists()) && (cameraFile.length() != 0L)) {
                    if (this.mCrop) {
                        crop(this.mTempCameraPath);
                        return;
                    } else {
                        String resizeImagePath = resizeForUpload(this.mTempCameraPath);
                        back(resizeImagePath);
                        return;
                    }
                }
                LogUtils.d(LOG_TAG, "Camera image is null");
            }
        }

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && (data != null)) {
            Uri localUri = data.getData();
            LogUtils.d(LOG_TAG, localUri.toString());
            Cursor cursor = getContentResolver().query(localUri, null, null, null, null);
            if ((cursor == null) || (cursor.getCount() < 1)) {
                LogUtils.d(LOG_TAG, "Gallery cursor is null");
                setResult(RESULT_NOT_FOUND);
                if ((cursor != null) && (!cursor.isClosed())) {
                    cursor.close();
                }
            } else {
                cursor.moveToFirst();
                String galleryImagePath = cursor.getString(1);
                cursor.close();
                if (this.mCrop) {
                    crop(galleryImagePath);
                    return;
                } else {
                    String resizeImagePath = resizeForUpload(galleryImagePath);
                    back(resizeImagePath);
                    return;
                }
            }
        }

        //见http://blog.csdn.net/floodingfire/article/details/8144615  Android大图片裁剪终极解决方案（中：从相册截图）
        //此处全部统一将图片指定MediaStore.EXTRA_OUTPUT uri，不使用indent的回传bitmap方式
        if (requestCode == REQUEST_CODE_CROP && resultCode == RESULT_OK ) {
            back(this.mTempCroppedPath);
        }

        finish();
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

    //返回获取的图片路径并退出本Activity
    private void back(String imagePath) {
        ProfileController.uploadAvatar(imagePath);
        EventBus.getDefault().post(new ProfileAvatarChangeEvent(imagePath));
        finish();
    }

    private void startCamera() {
        if (SDCardUtils.sdCardAvailable()) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            File localFile = new File(SDCardUtils.tempSendPictureDir(getApplicationContext())
                    , SDCardUtils.tempSendPictureName());
            this.mTempCameraPath = localFile.getAbsolutePath();
            intent.putExtra("output", Uri.fromFile(localFile));
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
            return;
        }
        LogUtils.w(LOG_TAG, "存储卡不可用.");
    }

    private void startGallery() {
        Intent intent = new Intent("android.intent.action.PICK", null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    private void crop(String paramString) {
        String str = SDCardUtils.tempSendPictureDir(getApplicationContext()) + "/" + System.currentTimeMillis() + ".png";
        this.mTempCroppedPath = str;
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
        //a content resolver Uri to be used to store the requested image or video.
        localIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(localFile2));
        localIntent.putExtra("return-data", false);
        try {
            startActivityForResult(localIntent, REQUEST_CODE_CROP);
        } catch (ActivityNotFoundException localActivityNotFoundException) {
            LogUtils.e(LOG_TAG, "Crop Activity Not Found");
            localActivityNotFoundException.printStackTrace();
            back(null);
        }
    }

    private String resizeForUpload(String imagePath) {
        File tempFile = new File(SDCardUtils.tempSendPictureDir(getApplicationContext())
        , SDCardUtils.tempSendPictureName());
        String tempFilePath = tempFile.getAbsolutePath();
        long beginTime = System.currentTimeMillis();
                Bitmap resizeImage2 = BitmapUtils.getSmallBitmap(imagePath, 100, 100);  //缩放到100像素
                BitmapUtils.saveBitmap(resizeImage2, tempFilePath);
        long duringTime = System.currentTimeMillis() - beginTime;
        LogUtils.d(LOG_TAG, "1图片缩小处理耗时" + duringTime / 1000L + "." + duringTime % 1000L + ", newPath=" + tempFilePath);
        return tempFilePath;
    }
}
