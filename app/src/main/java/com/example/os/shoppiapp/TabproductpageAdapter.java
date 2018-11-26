package com.example.os.shoppiapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabproductpageAdapter extends FragmentPagerAdapter {
    int numoftabs;

    public TabproductpageAdapter(FragmentManager fm, int  mnumoftabs ) {
        super(fm);
        this.numoftabs = mnumoftabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Infoproductpage tab1 = new Infoproductpage();
                return tab1;

            case 1:
                Rateproductpage tab2 = new Rateproductpage();
                return tab2;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numoftabs;
    }
}

