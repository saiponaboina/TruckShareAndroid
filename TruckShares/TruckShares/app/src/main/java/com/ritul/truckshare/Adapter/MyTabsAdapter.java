package com.ritul.truckshare.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by keval on 2/4/2016.
 */
public class MyTabsAdapter extends FragmentPagerAdapter {

    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public MyTabsAdapter(FragmentManager fm) {
        super(fm);
        mFragmentTitleList.add("Register");
        mFragmentTitleList.add("Credit Card");
        mFragmentTitleList.add("Bank");
        mFragmentTitleList.add("Truck");
    }

    @Override
    public Fragment getItem(int position) {
       /* switch (position) {
            case 0:
                // Top Rated fragment activity
                return new RegisterTabFragment();
            case 1:
                // Games fragment activity
                return new CreditCardTabFragment();
            case 2:
                // Movies fragment activity
                return new BankTabFragment();
            case 3:
                // Movies fragment activity
                return new TruckTabFragment();
        }*/
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

}
