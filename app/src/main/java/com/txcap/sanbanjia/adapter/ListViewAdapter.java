package com.txcap.sanbanjia.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.txcap.sanbanjia.R;
import com.txcap.sanbanjia.bean.InformationTitleBean;
import com.txcap.sanbanjia.utils.ImageDownloader;

import java.util.List;

/**
 * ListView适配器
 *
 * @author liuhe
 */
public class ListViewAdapter extends ArrayAdapter<InformationTitleBean> {

    private LayoutInflater inflater;
    ImageDownloader imageDownloader;
    private Context context;
    public ListViewAdapter(Context context, List<InformationTitleBean> list) {
        super(context, 0, list);
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InformationTitleBean info = getItem(position);
        final String img_url = info.getNews_pic();
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_listview, null);
            holder.news_pic = (ImageView) convertView.findViewById(R.id.iv_news_pic);
            holder.news_pic.setTag(img_url);
            holder.news_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.news_introduction = (TextView) convertView
                    .findViewById(R.id.tv_news_introduction);
            holder.created_at = (TextView) convertView
                    .findViewById(R.id.tv_created_at);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }
        holder.news_pic.setTag(img_url);
        if(imageDownloader == null){
            imageDownloader = new ImageDownloader();
        }
        holder.news_pic.setImageResource(R.drawable.ic_launcher);
        final ImageView imageView = (ImageView) convertView.findViewWithTag(img_url);
        if (imageDownloader != null) {
            //异步下载图片
            imageDownloader.imageDownload(img_url, holder.news_pic, "/img",context, new ImageDownloader.OnImageDownload() {
                @Override
                public void onDownloadSucc(Bitmap bitmap,
                                           String c_url,ImageView mimageView) {

                    if (imageView != null) {
                        imageView.setImageBitmap(bitmap);
                        imageView.setTag(img_url);
                    }
                }
            });
        }
        InformationTitleBean markerItem = getItem(position);
        if (null != markerItem) {
            holder.news_title.setText(info.getNews_title());
            holder.news_introduction.setText(info.getNews_introduction());
            holder.created_at.setText(info.getCreated_at());
        }
//        final ImageView news_pic = (ImageView) convertView.findViewById(R.id.iv_news_pic);
//        if (news_pic.getTag() != null && news_pic.getTag().equals(img_url)) {
//            HttpUtilsAsync.get(img_url, new AsyncHttpResponseHandler() {
//
//                @Override
//                public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                    Bitmap bitmap = MyUtils.Bytes2Bimap(bytes);
//                    holder.news_pic.setImageBitmap(bitmap);
//                }
//
//                @Override
//                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//
//                }
//            });
//        }else{
//            holder.news_pic.setImageBitmap(holder.news_pic.getDrawingCache());
//        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView news_pic;
        TextView news_title;
        TextView news_introduction;
        TextView created_at;
    }

}
