package com.example.ishtigupta.paritycubeassignment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

    public class FetcherAsyncTask extends AsyncTask<String, Void, Void> {
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
        protected Void doInBackground(String urls[]) {
            if (MainActivity.allDealsJsonObject == null) {
                List<NameValuePair> params;
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("accept", "application/json"));
                params.add(new BasicNameValuePair("accept", "text/javascript"));
                MainActivity.allDealsJsonObject = new ApiJsonFetcher().makeHttpRequest(urls[0], new ArrayList<NameValuePair>());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void o) {
            super.onPostExecute(o);
            DealsParser parser = new DealsParser(MainActivity.allDealsJsonObject);
            if (sectionNumber == 1)
                adapter.changeCursor(parser.getTopDealsCursor());
            else if (sectionNumber == 2)
                adapter.changeCursor(parser.getPopularDealsCursor());
            dialog.dismiss();
            adapter.notifyDataSetChanged();
        }

    }


}
