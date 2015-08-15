package com.txcap.sanbanjia.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by liuhe on 15/8/13.
 */
public class MyUtils {
    public static Bitmap Bytes2Bimap(byte[] b){
        if(b.length!=0){
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }
}
