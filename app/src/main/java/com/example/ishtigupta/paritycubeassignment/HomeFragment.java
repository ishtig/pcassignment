package com.example.ishtigupta.paritycubeassignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public HomeFragment() {// makeHttpRequest("",new ArrayList<NameValuePair>());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<NameValuePair> params;
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("accept","application/json"));
                params.add(new BasicNameValuePair("accept","text/javascript"));
                new ApiJsonFetcher().makeHttpRequest(ApiJsonFetcher.DEALS_URL,new ArrayList<NameValuePair>());
            }
        } ).start();
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }



}
