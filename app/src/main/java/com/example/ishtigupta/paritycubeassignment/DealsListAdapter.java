package com.example.ishtigupta.paritycubeassignment;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.widget.CursorAdapter;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Ishti Gupta on 4/26/2015.
 */
public class DealsListAdapter extends CursorAdapter {
    ImageLoader mImageLoader;

    DisplayImageOptions options;
    public DealsListAdapter(Context context, Cursor cursor, ImageLoader imageLoader) {
        super(context, cursor, 0);
        mImageLoader = imageLoader;
         options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.paritycube)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .showImageForEmptyUri(R.drawable.paritycube)
                .showImageOnFail(R.drawable.paritycube)
                .resetViewBeforeLoading(true)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .build();
    }

    class ViewHolder {
        TextView title;
        TextView detail;
        ImageView thumbnail;
        int imgUrlIndex;
        int titleIndex, detailIndex;
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.deals_list_item, null);
        ViewHolder holder = new ViewHolder();
        holder.title = (TextView) view.findViewById(R.id.deal_title);
        holder.detail = (TextView) view.findViewById(R.id.deal_details);
        holder.thumbnail = (ImageView) view.findViewById(R.id.deal_thumbnail);
        holder.titleIndex = cursor.getColumnIndexOrThrow(DealsParser.DEAL_TITLE);
        holder.detailIndex = cursor.getColumnIndexOrThrow(DealsParser.DEAL_DETAIL);
        holder.imgUrlIndex = cursor.getColumnIndexOrThrow(DealsParser.DEAL_IMG_URL);


        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.title.setText(cursor.getString(holder.titleIndex));
        Spanned s = Html.fromHtml(cursor.getString(holder.detailIndex));
        holder.detail.setText(s);

        mImageLoader.displayImage(cursor.getString(holder.imgUrlIndex), holder.thumbnail, options);

    }

}
