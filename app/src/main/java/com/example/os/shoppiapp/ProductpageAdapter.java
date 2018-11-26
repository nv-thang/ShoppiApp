package com.example.os.shoppiapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class ProductpageAdapter extends FragmentStatePagerAdapter {
    public ProductpageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProductPage tab1 = new ProductPage();
                return tab1;
            case 1:
                ProductPage1 tab2 = new ProductPage1();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
