package com.example.ishtigupta.paritycubeassignment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Ishti Gupta on 4/26/2015.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    Context mContext;
    public TabsPagerAdapter(Context context, FragmentManager fm) {

        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return HomeFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_section1).toUpperCase();
            case 1:
                return mContext.getString(R.string.title_section2).toUpperCase();
        }
        return null;
    }
}


