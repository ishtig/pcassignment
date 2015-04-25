package com.example.ishtigupta.paritycubeassignment;

import android.os.AsyncTask;

import org.json.JSONObject;

/**
 * Created by Ishti Gupta on 4/26/2015.
 */
public class FetcherAsyncTask extends AsyncTask<String, Void, JSONObject> {


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String[] params) {
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject o) {
        super.onPostExecute(o);
    }

}
