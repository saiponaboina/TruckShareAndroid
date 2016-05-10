package com.ritul.truckshare.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ritul.truckshare.HomeActivity;
import com.ritul.truckshare.R;
import com.ritul.truckshare.Server.RSMIT;

/**
 * Created by keval on 2/4/2016.
 */
public class ProfileTabsFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    HomeActivity mainActivity;
    private UpdateProfile updateProfile;
    private String[] TITLES;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (HomeActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_tabs,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(RSMIT.getInstance().getUser().getAppUserType().equalsIgnoreCase("D")){
            TITLES = new String[9];
            TITLES[0] = "Register";
            TITLES[1] = "Bank Info";
            TITLES[2] = "Profile picture";
            TITLES[3] = "Security No.";
            TITLES[4] = "Driver License";
            TITLES[5] = "Truck Info";
            TITLES[6] = "Insurance";
            TITLES[7] = "Select Truck";
            TITLES[8] = "Truck Size";
        }
        else if (RSMIT.getInstance().getUser().getAppUserType().equalsIgnoreCase("R")){
            TITLES = new String[2];
            TITLES[0] = "Register";
            TITLES[1] = "Credit Card";
        }

        tabLayout = (TabLayout) getView().findViewById(R.id.tabs);
        viewPager = (ViewPager) getView().findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(mainActivity.getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {

            switch (TITLES[position]) {
                case "Register":
                    /*RegisterTabFragment registerTabFragment = new RegisterTabFragment();
                    return registerTabFragment;*/
                    return UpdateProfile.newInstance(TITLES[position]);
                case "Credit Card":
                    /*CreditCardTabFragment creditCardTabFragment = new CreditCardTabFragment();
                    return creditCardTabFragment;*/
                    return UpdateProfile.newInstance(TITLES[position]);
                case "Bank Info":
                    /*BankTabFragment bankTabFragment = new BankTabFragment();
                    return bankTabFragment;*/
                    return UpdateProfile.newInstance(TITLES[position]);
                case "Profile picture":
                    /*TruckTabFragment truckTabFragment = new TruckTabFragment();
                    return truckTabFragment;*/
                    return UpdateProfile.newInstance(TITLES[position]);
                case "Security No.":
                    /*TruckTabFragment truckTabFragment = new TruckTabFragment();
                    return truckTabFragment;*/
                    return UpdateProfile.newInstance(TITLES[position]);
                case "Driver License":
                    /*TruckTabFragment truckTabFragment = new TruckTabFragment();
                    return truckTabFragment;*/
                    return UpdateProfile.newInstance(TITLES[position]);
                case "Truck Info":
                    /*TruckTabFragment truckTabFragment = new TruckTabFragment();
                    return truckTabFragment;*/
                    return UpdateProfile.newInstance(TITLES[position]);
                case "Insurance":
                    /*TruckTabFragment truckTabFragment = new TruckTabFragment();
                    return truckTabFragment;*/
                    return UpdateProfile.newInstance(TITLES[position]);
                case "Select Truck":
                    /*TruckTabFragment truckTabFragment = new TruckTabFragment();
                    return truckTabFragment;*/
                    return UpdateProfile.newInstance(TITLES[position]);
                case "Truck Size":
                    /*TruckTabFragment truckTabFragment = new TruckTabFragment();
                    return truckTabFragment;*/
                    return UpdateProfile.newInstance(TITLES[position]);
                default:
                    break;
            }
            return null;

        }

    }
}
