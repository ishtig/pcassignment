package com.example.ishtigupta.paritycubeassignment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ishti Gupta on 4/26/2015.
 */
public class HomeFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    DealsListAdapter adapter;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private int sectionNumber;

    public HomeFragment() {// makeHttpRequest("",new ArrayList<NameValuePair>());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Bundle b = getArguments();
        sectionNumber = b.getInt(ARG_SECTION_NUMBER);

        ListView listView = (ListView) rootView.findViewById(R.id.deals_list_view);
        adapter = new DealsListAdapter(getActivity(), null, MainActivity.imageLoader);
        listView.setAdapter(adapter);

        new FetcherAsyncTask().execute(ApiJsonFetcher.DEALS_URL);

        return rootView;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    public class FetcherAsyncTask extends AsyncTask<String, Void, Boolean> {
        private final ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setIndeterminate(true);
            dialog.setMessage("Loading....");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                DealsParser parser = new DealsParser(MainActivity.allDealsJsonObject);
                if (sectionNumber == 1)
                    adapter.changeCursor(parser.getTopDealsCursor());
                else if (sectionNumber == 2)
                    adapter.changeCursor(parser.getPopularDealsCursor());
                adapter.notifyDataSetChanged();
            } else {
                Toast toast = Toast.makeText(getActivity(), "Internet connection not available!", Toast.LENGTH_SHORT);
                toast.show();
            }

            dialog.dismiss();
        }

        @Override
        protected Boolean doInBackground(String urls[]) {
            if (!isNetworkAvailable())
                return false;

            if (MainActivity.allDealsJsonObject == null) {
                List<NameValuePair> params;
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("accept", "application/json"));
                params.add(new BasicNameValuePair("accept", "text/javascript"));
                MainActivity.allDealsJsonObject = new ApiJsonFetcher().makeHttpRequest(urls[0], new ArrayList<NameValuePair>());
            }
            return true;
        }
    }
}
