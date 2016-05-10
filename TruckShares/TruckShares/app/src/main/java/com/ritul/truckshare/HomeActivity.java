package com.ritul.truckshare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ritul.truckshare.Fragments.CreditInfoFragment;
import com.ritul.truckshare.Fragments.DriverFragment;
import com.ritul.truckshare.Fragments.MyJobFragment;
import com.ritul.truckshare.Fragments.ProfileTabsFragment;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.ritul.truckshare.Fragments.RequestFragment;
import com.ritul.truckshare.Pojo.DriverInformation;
import com.ritul.truckshare.Pojo.Insurence;
import com.ritul.truckshare.Pojo.TruckDetail;
import com.ritul.truckshare.Pojo.TruckImages;
import com.ritul.truckshare.Pojo.UserInformation;
import com.ritul.truckshare.Server.RSMIT;

import java.util.zip.Inflater;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.extras.Base64;
import com.ritul.truckshare.Fragments.MapFragment;
import com.ritul.truckshare.Server.RSMServer;
import com.ritul.truckshare.Util.Prog_Dialog;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,
        ConnectionCallbacks, OnConnectionFailedListener {
    String UserType;
    private NavigationView navigationView;
    public static DrawerLayout drawerLayout;
    String backStateName;
    private GoogleApiClient mGoogleApiClient;
    ImageView profile_image;
    TextView username;
    Prog_Dialog ProgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ProgDialog = new Prog_Dialog(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();


        UserType = RSMIT.getInstance().getUser().getAppUserType();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View header = LayoutInflater.from(this).inflate(R.layout.layout_drawer_header, null);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.addHeaderView(header);
        profile_image = (ImageView) header.findViewById(R.id.profile_image);
        username = (TextView) header.findViewById(R.id.username);

        if (RSMIT.getInstance().getUser().getProfileImageBase64String() != null) {
            byte[] imageAsBytes21 = Base64.decode(RSMIT.getInstance().getUser().getProfileImageBase64String(), Base64.DEFAULT);
            profile_image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes21, 0, imageAsBytes21.length));
        }

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RSMIT.getInstance().getUser().getProfileImageBase64String() != null) {
                    byte[] imageAsBytes100 = Base64.decode(RSMIT.getInstance().getUser().getProfileImageBase64String(), Base64.DEFAULT);
                    profile_image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes100, 0, imageAsBytes100.length));
                }
            }
        });

        Menu menu = navigationView.getMenu();
        if (UserType.equalsIgnoreCase("D")) {
            menu.findItem(R.id.Request).setVisible(false);
            menu.findItem(R.id.home).setVisible(false);
            makeFragmentVisible(new DriverFragment(), "Driver");
            username.setText(RSMIT.getInstance().getUser().getFirstName());
        }
        else {
            menu.findItem(R.id.MyJobs).setVisible(false);
            menu.findItem(R.id.driver).setVisible(false);
            username.setText(RSMIT.getInstance().getUser().getFirstName() + " " + RSMIT.getInstance().getUser().getLastName());
//          makeFragmentVisible(new HomeFragment(), "home");
            makeFragmentVisible(new MapFragment(), "MAP");
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                0, 0);
        drawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                drawerLayout.closeDrawers();
                if (item.getTitle().toString().equalsIgnoreCase("Profile")) {
                    ProfileTabsFragment fragment = new ProfileTabsFragment();
                    if (!fragment.isVisible()) {
                        makeFragmentVisible(fragment, "Profile");
                    }
                }
                else if (item.getTitle().toString().equalsIgnoreCase("Home ")) {
                    DriverFragment driverFragment = new DriverFragment();
                    if (!driverFragment.isVisible()) {
                        makeFragmentVisible(driverFragment, "Home");
                    }
                }

                else if (item.getTitle().toString().trim().equalsIgnoreCase("Home")) {
//                    HomeFragment homeFragment = new HomeFragment();
//                    if (!homeFragment.isVisible()) {
//                          makeFragmentVisible(homeFragment, "home");
//                    }
                    makeFragmentVisible(new MapFragment(), "MAP");
                } else if (item.getTitle().toString().equalsIgnoreCase("My Jobs")) {
                    MyJobFragment myJobFragment = new MyJobFragment();
                    if (!myJobFragment.isVisible()) {
                        makeFragmentVisible(myJobFragment, "My Jobs");
                    }
                }


                else if (item.getTitle().toString().equalsIgnoreCase("Request")) {
                    RequestFragment requestFragment = new RequestFragment();
                    if (!requestFragment.isVisible()) {
                        makeFragmentVisible(requestFragment, "Request");
                    }
                }
                else if (item.getTitle().toString().equalsIgnoreCase("Credit Info")) {
                    CreditInfoFragment creditInfoFragment = new CreditInfoFragment();
                    if (!creditInfoFragment.isVisible()) {
                        makeFragmentVisible(creditInfoFragment, "Credit Info");
                    }
                } else if (item.getTitle().toString().equalsIgnoreCase("Logout")) {

                    UserInformation user = new UserInformation();
                    Session session = Session.getActiveSession();
                    if (session != null) {
                        if (!session.isClosed()) {
                            session.closeAndClearTokenInformation();
                        }
                    }
                    signOutFromGplus();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                return true;
            }
        });

    }

    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
    }
    private void makeFragmentVisible(Fragment fragment, String strName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
        fragmentManager.beginTransaction()
                .replace(R.id.frame, fragment).addToBackStack(strName).commit();
    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
