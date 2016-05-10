package com.ritul.truckshare.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ritul.truckshare.R;

/**
 * Created by Keval on 2/4/2016.
 */
public class CreditInfoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.credit_info_fragment,container,false);
    }
}
