package com.example.ishtigupta.paritycubeassignment;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Ishti Gupta on 4/26/2015.
 */
public class DealsListAdapter extends CursorAdapter {
    public DealsListAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    class ViewHolder {
        TextView title;
        TextView detail;// imageUrl;
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
        holder.titleIndex = cursor.getColumnIndexOrThrow(DealsParser.DEAL_TITLE);
        holder.detailIndex = cursor.getColumnIndexOrThrow(DealsParser.DEAL_DETAIL);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.title.setText(cursor.getString(holder.titleIndex));
        Spanned s = Html.fromHtml(cursor.getString(holder.detailIndex));
        holder.detail.setText(s);

    }

}
