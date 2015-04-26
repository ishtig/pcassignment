package com.example.ishtigupta.paritycubeassignment;

import android.database.MatrixCursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ishti Gupta on 4/26/2015.
 */
public class DealsParser {

    public static final String DEAL_ID = "id";
    public static final String DEAL_TITLE = "title";
    public static final String DEAL_DETAIL = "deal_detail";
    public static final String DEAL_IMG_URL = "pic_thumb";
    MatrixCursor topDealsCursor;
    MatrixCursor popularDealsCursor;
   // JSONObject mJsonObject;

    public DealsParser(JSONObject jsonObject) {
        try {
            jsonObject = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonObject.getJSONArray("top");
            topDealsCursor = getCursorFromArray(jsonArray);
            jsonArray = jsonObject.getJSONArray("popular");
            popularDealsCursor = getCursorFromArray(jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private MatrixCursor getCursorFromArray(JSONArray jsonArray) {
        try {

            Deal deal;
            MatrixCursor matrixCursor = new MatrixCursor(new String[]{"_"+DEAL_ID,DEAL_TITLE, DEAL_DETAIL, DEAL_IMG_URL});
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject dealJsonObject = jsonArray.getJSONObject(i);
                deal = new Deal();
                deal.setDealTitle(dealJsonObject.getString(DEAL_TITLE));
                deal.setDetail(dealJsonObject.getString(DEAL_DETAIL));
                deal.setImgUrl(dealJsonObject.getString(DEAL_IMG_URL));
                matrixCursor.addRow(new Object[]{dealJsonObject.getString(DEAL_ID), deal.getDealTitle(), deal.getDetail(), deal.getImgUrl()});
            }
            return matrixCursor;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public MatrixCursor getPopularDealsCursor() {
        return popularDealsCursor;
    }

    public MatrixCursor getTopDealsCursor() {
        return topDealsCursor;
    }
}
