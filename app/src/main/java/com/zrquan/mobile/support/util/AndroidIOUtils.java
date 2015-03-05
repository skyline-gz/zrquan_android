package com.zrquan.mobile.support.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.webkit.MimeTypeMap;

import org.apache.commons.collections4.*;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * File Utils
 * <ul>
 * Read or write file
 * <li>{@link #readFile(String filePath, String charsetName)} read file</li>
 * </ul>
 * <ul>
 * Operate file
 * </ul>
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2012-5-12
 */
public class AndroidIOUtils {

    public final static String FILE_EXTENSION_SEPARATOR = ".";

    private AndroidIOUtils() {
        throw new AssertionError();
    }

    public static StringBuilder readFile(String filePath, String charsetName) {
        return readFile(filePath, charsetName, false, null);
    }

    /**
     * read file
     *
     * @param filePath
     * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static StringBuilder readFile(String filePath, String charsetName, Boolean isAssets, Context context) {
        StringBuilder fileContent = new StringBuilder("");
        BufferedReader reader = null;
        if (charsetName == null) {
            charsetName = "UTF-8";
        }
        try {
            InputStreamReader inputStreamReader;
            InputStream inputStream;
            if (isAssets) {
                //isAssets为true时，必须提供 context参数
                if (context == null) {
                    return null;
                }
                AssetManager assetManager = context.getAssets();
                inputStream = assetManager.open(filePath);
                inputStreamReader = new InputStreamReader(inputStream, charsetName);
            } else {
                File file = new File(filePath);
                if (file == null || !file.isFile()) {
                    return null;
                }
                inputStream = new FileInputStream(file);
                inputStreamReader = new InputStreamReader(inputStream, charsetName);
            }
            reader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    // http://stackoverflow.com/questions/8589645/how-to-determine-mime-type-of-file-in-android
    // url = file path or whatever suitable URL you want.
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static Boolean isGifType(String contentType) {
        return contentType.toLowerCase().contains("image/gif");
    }

    public static Boolean isGifFile(String url) {
        return isGifType(getMimeType(url));
    }
}
