package com.txcap.sanbanjia.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhe on 15/8/14.
 */
public class ImageDownloader {

    private static final String TAG = "ImageDownloader";
    private HashMap<String, MyAsyncTask> map = new HashMap<String, MyAsyncTask>();
    private Map<String, SoftReference<Bitmap>> imageCaches = new HashMap<String, SoftReference<Bitmap>>();


    /**
     *
     * @param url 该mImageView对应的url
     * @param mImageView
     * @param path 文件存储路径
     * @param context
     * @param download OnImageDownload回调接口，在onPostExecute()中被调用
     */
    public void imageDownload(String url,ImageView mImageView,String path,Context context,OnImageDownload download){
        SoftReference<Bitmap> currBitmap = imageCaches.get(url);
        Bitmap softRefBitmap = null;
        if(currBitmap != null){
            softRefBitmap = currBitmap.get();
        }
        String imageName = "";
        if(url != null){
            imageName = ImageLoadUtils.getInstance().getImageName(url);
        }
        Bitmap bitmap = getBitmapFromFile(context,imageName,path);
        //先从软引用中拿数据
        if(currBitmap != null && mImageView != null && softRefBitmap != null && url.equals(mImageView.getTag())){
            mImageView.setImageBitmap(softRefBitmap);
        }
        //软引用中没有，从文件中拿数据
        else if(bitmap != null && mImageView != null && url.equals(mImageView.getTag())){
            mImageView.setImageBitmap(bitmap);
        }
        //文件中也没有，此时根据mImageView的tag，即url去判断该url对应的task是否已经在执行，如果在执行，本次操作不创建新的线程，否则创建新的线程。
        else if(url != null && needCreateNewTask(mImageView)){
            MyAsyncTask task = new MyAsyncTask(url, mImageView, path,context,download);
            if(mImageView != null){
                Log.i(TAG, "执行MyAsyncTask --> " + ImageLoadUtils.flag);
                ImageLoadUtils.flag ++;
                task.execute();
                //将对应的url对应的任务存起来
                map.put(url, task);
            }
        }
    }

    /**
     * 　　* 判断是否需要重新创建线程下载图片，如果需要，返回值为true。
     * 　　* @param url
     * 　　* @param mImageView
     * 　　* @return
     *
     */
    private boolean needCreateNewTask(ImageView mImageView) {
        boolean b = true;
        if (mImageView != null) {
            String curr_task_url = (String) mImageView.getTag();
            if (isTasksContains(curr_task_url)) {
                b = false;
            }
        }
        return b;
    }

    /**
     * 　　* 检查该url(最终反映的是当前的ImageView的tag，tag会根据position的不同而不同)对应的task是否存在
     * 　　* @param url
     * 　　* @return
     *
     */
    private boolean isTasksContains(String url) {
        boolean b = false;
        if (map != null && map.get(url) != null) {
            b = true;
        }
        return b;
    }

    /**
     * 　　* 删除map中该url的信息，这一步很重要，不然MyAsyncTask的引用会“一直”存在于map中
     * 　　* @param url
     *
     */
    private void removeTaskFormMap(String url) {
        if (url != null && map != null && map.get(url) != null) {
            map.remove(url);
            System.out.println("当前map的大小==" + map.size());
        }
    }

    /**
     * 　　* 从文件中拿图片
     * 　　* @param mActivity
     * 　　* @param imageName 图片名字
     * 　　* @param path 图片路径
     * 　　* @return
     *
     */
    private Bitmap getBitmapFromFile(Context context, String imageName, String
            path) {
        Bitmap bitmap = null;
        if (imageName != null) {
            File file = null;
            String real_path = "";
            try {
                if (ImageLoadUtils.getInstance().hasSDCard()) {
                    real_path = ImageLoadUtils.getInstance().getExtPath() + (path != null &&
                            path.startsWith("/") ? path : "/" + path);
                } else {
                    real_path = ImageLoadUtils.getInstance().getPackagePath(context) + (path != null
                            && path.startsWith("/") ? path : "/" + path);
                }
                file = new File(real_path, imageName);
                if (file.exists())
                    bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            } catch (Exception e) {
                e.printStackTrace();
                bitmap = null;
            }
        }
        return bitmap;
    }

    /**
     * 　　* 将下载好的图片存放到文件中
     * 　　* @param path 图片路径
     * 　　* @param mActivity
     * 　　* @param imageName 图片名字
     * 　　* @param bitmap 图片
     * 　　* @return
     *
     */
    private boolean setBitmapToFile(String path, Context context, String
            imageName, Bitmap bitmap) {
        File file = null;
        String real_path = "";
        try {
            if (ImageLoadUtils.getInstance().hasSDCard()) {
                real_path = ImageLoadUtils.getInstance().getExtPath() + (path != null &&
                        path.startsWith("/") ? path : "/" + path);
            } else {
                real_path = ImageLoadUtils.getInstance().getPackagePath(context) + (path != null
                        && path.startsWith("/") ? path : "/" + path);
            }
            file = new File(real_path, imageName);
            if (!file.exists()) {
                File file2 = new File(real_path + "/");
                file2.mkdirs();
            }
            file.createNewFile();
            FileOutputStream fos = null;
            if (ImageLoadUtils.getInstance().hasSDCard()) {
                fos = new FileOutputStream(file);
            } else {
                fos = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            }
            if (imageName != null && (imageName.contains(".png") ||
                    imageName.contains(".PNG"))) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            } else {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            }
            fos.flush();
            if (fos != null) {
                fos.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 　　* 辅助方法，一般不调用
     * 　　* @param path
     * 　　* @param mActivity
     * 　　* @param imageName
     *
     */
    private void removeBitmapFromFile(String path, Context context, String
            imageName) {
        File file = null;
        String real_path = "";
        try {
            if (ImageLoadUtils.getInstance().hasSDCard()) {
                real_path = ImageLoadUtils.getInstance().getExtPath() + (path != null &&
                        path.startsWith("/") ? path : "/" + path);
            } else {
                real_path = ImageLoadUtils.getInstance().getPackagePath(context) + (path != null
                        && path.startsWith("/") ? path : "/" + path);
            }
            file = new File(real_path, imageName);
            if (file != null)
                file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步下载图片的方法
     * @author yanbin
     *
     */
    private class MyAsyncTask extends AsyncTask<String, Void, Bitmap>{
        private ImageView mImageView;
        private String url;
        private OnImageDownload download;
        private String path;
        private Activity mActivity;

        public MyAsyncTask(String url,ImageView mImageView,String path,Context context,OnImageDownload download){
            this.mImageView = mImageView;
            this.url = url;
            this.path = path;
            this.mActivity = mActivity;
            this.download = download;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap data = null;
            if(url != null){
                try {
                    URL c_url = new URL(url);
                    InputStream bitmap_data = c_url.openStream();
                    data = BitmapFactory.decodeStream(bitmap_data);
                    String imageName = ImageLoadUtils.getInstance().getImageName(url);
                    if(!setBitmapToFile(path,mActivity,imageName, data)){
                        removeBitmapFromFile(path,mActivity,imageName);
                    }
                    imageCaches.put(url, new SoftReference<Bitmap>(data.createScaledBitmap(data, 100, 100, true)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            //回调设置图片
            if(download != null){
                download.onDownloadSucc(result,url,mImageView);
                //该url对应的task已经下载完成，从map中将其删除
                removeTaskFormMap(url);
            }
            super.onPostExecute(result);
        }

    }
    public interface OnImageDownload {

        void onDownloadSucc(Bitmap bitmap,String c_url,ImageView imageView);
        }
}