package com.txcap.sanbanjia.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

/**
 * Created by liuhe on 15/8/13.
 */
public class ImageLoadUtils {
    private static ImageLoadUtils util;
    public static int flag = 0;

    private ImageLoadUtils() {
    }


    public static ImageLoadUtils getInstance() {
        if (util == null) {
            util = new ImageLoadUtils();
        }
        return util;
    }


    /**
     * 　　* 判断是否有sdcard
     * 　　* @return
     */


    public boolean hasSDCard() {
        boolean b = false;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            b = true;
        }
        return b;
    }


    /**
     * 　　* 得到sdcard路径
     * 　　* @return
     */


    public String getExtPath() {
        String path = "";
        if (hasSDCard()) {
            path = Environment.getExternalStorageDirectory().getPath();
        }
        return path;
    }

    /**
     * 　　* 得到/data/data/yanbin.imagedownload目录
     * 　　* @param mActivity
     * 　　* @return
     */


    public String getPackagePath(Context context) {
        return context.getFilesDir().toString();
    }

    /**
     * 　　* 根据url得到图片名
     * 　　* @param url
     * 　　* @return
     */


    public String getImageName(String url) {
        String imageName = "";
        if (url != null) {
            imageName = url.substring(url.lastIndexOf("/") + 1);
        }
        return imageName;
    }

}
