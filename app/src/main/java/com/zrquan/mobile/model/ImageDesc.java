package com.zrquan.mobile.model;

import android.graphics.Bitmap;

public class ImageDesc {
    //图片文件名
    private String fileName;
    //图片存储路径
    private String originalPath;
    //图片的Content Type
    private String contentType;
    //非gif使用，生成缩略的bitmap用于显示和上传
    private Bitmap bitmap;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
