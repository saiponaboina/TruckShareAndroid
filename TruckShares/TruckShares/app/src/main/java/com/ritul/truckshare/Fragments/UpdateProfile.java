package com.ritul.truckshare.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ritul.truckshare.Adapter.StateSpinnerAdapter;
import com.ritul.truckshare.Adapter.Trucktype;
import com.ritul.truckshare.HomeActivity;
import com.ritul.truckshare.Pojo.DriverInformation;
import com.ritul.truckshare.Pojo.Insurence;
import com.ritul.truckshare.Pojo.StateSpinner;
import com.ritul.truckshare.Pojo.TruckDetail;
import com.ritul.truckshare.Pojo.TruckImages;
import com.ritul.truckshare.Pojo.Truckothertype;
import com.ritul.truckshare.Pojo.UpdateProfileDetail;
import com.ritul.truckshare.Pojo.UserInformation;
import com.ritul.truckshare.R;
import com.ritul.truckshare.RegisterActivity.DriverLicenseActivity;
import com.ritul.truckshare.RegisterActivity.InsuranceActivity;
import com.ritul.truckshare.RegisterActivity.SelectTruckActivity;
import com.ritul.truckshare.RegisterActivity.TruckActivity;
import com.ritul.truckshare.RegisterActivity.Validator;
import com.ritul.truckshare.Server.RSMIT;
import com.ritul.truckshare.Server.RSMServer;
import com.ritul.truckshare.Util.AppConstant;
import com.ritul.truckshare.Util.Prog_Dialog;
import com.ritul.truckshare.Utility;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.extras.Base64;

/**
 * Created by Vipul on 2/5/2016.
 */

public class UpdateProfile extends Fragment implements View.OnClickListener {


    HomeActivity homeActivity;
    public static UpdateProfile newInstance(String text) {
        UpdateProfile updateProfile = new UpdateProfile();
        Bundle bundle = new Bundle();
        bundle.putString("TabName", text);
        updateProfile.setArguments(bundle);
        return updateProfile;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        homeActivity = (HomeActivity) activity;
    }

    /*@Nullable*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_tabs_fragment, container, false);
        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onClick(View v) {
    }
}