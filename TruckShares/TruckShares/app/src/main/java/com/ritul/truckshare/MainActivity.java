package com.ritul.truckshare;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ritul.truckshare.Pojo.DriverInformation;
import com.ritul.truckshare.Pojo.Insurence;
import com.ritul.truckshare.Pojo.TruckDetail;
import com.ritul.truckshare.Pojo.TruckImages;
import com.ritul.truckshare.RegisterActivity.RegisterActivity;
import com.ritul.truckshare.Server.RSMIT;
import com.ritul.truckshare.Server.RSMServer;
import com.ritul.truckshare.Util.AppConstant;
import com.ritul.truckshare.Util.Prog_Dialog;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.ritul.truckshare.Pojo.UserInformation;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import io.fabric.sdk.android.Fabric;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        ConnectionCallbacks, OnConnectionFailedListener {

    TextView txtforgotpass;
    Button BtnLogin;
    EditText txtUserName;
    EditText txtPassword;
    Prog_Dialog ProgDialog;
    Snackbar snackbar;
    LinearLayout len;
    String UserType="";

    private UiLifecycleHelper uiHelper;
    private LoginButton loginBtn;

    private SignInButton btnSignIn;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;
    private static final int RC_SIGN_IN = 0;
    boolean gp = false;

    private static final String TWITTER_KEY = "nWaaux7Kf6EIvQFO57hwqSVgn";
    private static final String TWITTER_SECRET = "wMu2AdMb9IIMag2Y6Tuo2WdEbHL5LjYf2qDvGGnymxtjH3dG4M";

    Long userid;
    TwitterSession session;

    private TwitterLoginButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ProgDialog = new Prog_Dialog(this);
        len = (LinearLayout) findViewById(R.id.len);
        txtUserName = (EditText)findViewById(R.id.txtUserName);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtforgotpass=(TextView)findViewById(R.id.tvForgotPassword);
        BtnLogin=(Button)findViewById(R.id.BtnLogin);
        loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);



        if(!AppConstant.isNetworkAvailable(this)){
            //Toast.makeText(this,"No Network Avilable",Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(this)
                    .setTitle("Network Error")
                    .setMessage("Internet connection not found,\nPlease try again.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setCancelable(true).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        txtforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });



        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                if (CheckValidation()) {
                   /* Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);*/
                }
            }
        });


//-----------------------------------facebook-------------------------------------------------------
        uiHelper = new UiLifecycleHelper(this, statusCallback);
        uiHelper.onCreate(savedInstanceState);
        loginBtn.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                if (user != null) {

                } else {

                }
            }
        });


//-----------------------------------------Google plus----------------------------------------------
        btnSignIn.setOnClickListener(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }

//------------------------------Facebook----------------------------------------------------------

    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            if (state.isOpened()) {

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);

            } else if (state.isClosed()) {
                session.closeAndClearTokenInformation();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();

        Session session = Session.getActiveSession();
        if (session != null) {

            if (!session.isClosed()) {
                session.closeAndClearTokenInformation();
                //clear your preferences if saved
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
            if (requestCode == RC_SIGN_IN) {
                    if (resultCode != RESULT_OK) {
                        mSignInClicked = false;
                    }

                    mIntentInProgress = false;

                    if (!mGoogleApiClient.isConnecting()) {
                        mGoogleApiClient.connect();
                    }
                }

    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        uiHelper.onSaveInstanceState(savedState);
    }


//------------------------------------Google Plus Sign in-------------------------------------------
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Method to resolve any signin errors
     * */

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }
        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;
            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }

    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
       // Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
    }

    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    public void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
    }




    public  boolean CheckValidation() {

        if (txtUserName.getText().toString().equals("")) {
            Toast.makeText(this, "Please Fill User Name.", Toast.LENGTH_SHORT).show();
            txtUserName.requestFocus();
            return false;
        }

        if (txtPassword.getText().toString().equals("")) {
            Toast.makeText(this,"Please Fill Password.",Toast.LENGTH_SHORT).show();
            txtPassword.requestFocus();
            return false;
        }
        if (AppConstant.isNetworkAvailable(this)) {
            try {
                userdetail();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            AppConstant.showNetworkError(this);
        }
        return  true;
    }


    public void userdetail() throws JSONException {
        ProgDialog.show();
        RequestParams params = new RequestParams();
        params.put("userName", txtUserName.getText().toString());
        RSMServer.get("GetUserProfileDetailByUserName?", params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.e("Response", response + "");

                        try {
                            if (response.getString("success").equalsIgnoreCase("true")) {

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                gsonBuilder.registerTypeAdapter(UserInformation.class,
                                        new UserInformation());
                                Gson gson = gsonBuilder.create();
                                UserInformation user = gson.fromJson(response.get("result").toString(),
                                        new TypeToken<UserInformation>() {
                                        }.getType());

                                RSMIT.getInstance().setUser(user);
                                UserType = RSMIT.getInstance().getUser().getAppUserType();
                                if (UserType == null) {
                                    ProgDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Username Is Not Authenticate.", Toast.LENGTH_SHORT).show();
                                }

                                else if(UserType.equalsIgnoreCase("D")){
                                    try {
                                        get_driver_detail();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else{
                                    try {
                                        login();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                }

                            } else {
                                ProgDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Please try After Sometime", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ProgDialog.dismiss();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            ProgDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        ProgDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Please try After Sometime", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void get_driver_detail() throws JSONException {

        RequestParams params = new RequestParams();
        params.put("appUserId", RSMIT.getInstance().getUser().getAppUserId());
        RSMServer.getd("DriverDetail/GetDriverDetailByAppUserId?", params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        super.onSuccess(statusCode, headers, response);
                        Log.e("Response", response + "");
                        try {
                            if (response.getString("success").equalsIgnoreCase("true")) {

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                gsonBuilder.registerTypeAdapter(DriverInformation.class,
                                        new DriverInformation());

                                Gson gson = gsonBuilder.create();
                                DriverInformation user = gson.fromJson(response.get("result").toString(),
                                        new TypeToken<DriverInformation>() {
                                        }.getType());

                                RSMIT.getInstance().setDriver(user);
                                //Toast.makeText(getApplicationContext(), RSMIT.getInstance().getDriver().getDriverDetailId(), Toast.LENGTH_SHORT).show();

                                try {
                                    truck_detail();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                ProgDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Please try After Sometime", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            ProgDialog.dismiss();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            ProgDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        ProgDialog.dismiss();

                        Toast.makeText(getApplicationContext(), "Please try After Sometime", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void truck_detail() throws JSONException {

        RequestParams params = new RequestParams();
        params.put("driverDetailId", RSMIT.getInstance().getDriver().getDriverDetailId());
        RSMServer.getd("TruckDetail/GetTruckDetailByDriverDetailId?", params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.e("Response", response + "");

                        try {
                            if (response.getString("success").equalsIgnoreCase("true")) {

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                gsonBuilder.registerTypeAdapter(TruckDetail.class,
                                        new TruckDetail());

                                Gson gson = gsonBuilder.create();
                                TruckDetail user = gson.fromJson(response.get("result").toString(),
                                        new TypeToken<TruckDetail>() {
                                        }.getType());

                                RSMIT.getInstance().setTruck(user);

                                try {
                                    truckimage();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                ProgDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Please try After Someitme", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            ProgDialog.dismiss();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            ProgDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        ProgDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Please try After Sometime", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void truckimage() throws JSONException {
        RequestParams params = new RequestParams();
        params.put("truckDetailId", RSMIT.getInstance().getTruck().getTruckDetailId());
        RSMServer.getd("TruckImage/GetTruckImagesByTrcuckDetailId?", params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.e("Response", response + "");

                        try {
                            if (response.getString("success").equalsIgnoreCase("true")) {
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                gsonBuilder.registerTypeAdapter(TruckImages.class,
                                        new TruckImages());
                                Gson gson = gsonBuilder.create();
                                TruckImages user = gson.fromJson(response.get("result").toString(),
                                        new TypeToken<TruckImages>() {
                                        }.getType());

                                RSMIT.getInstance().setTimage(user);
                                try {
                                    insurence();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                ProgDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Please try After Sometime", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            ProgDialog.dismiss();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            ProgDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        ProgDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Please try After Sometime", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void insurence() throws JSONException {
        RequestParams params = new RequestParams();
        params.put("truckDetailId", RSMIT.getInstance().getTruck().getTruckDetailId());
        RSMServer.getd("TruckInsurance/GetTruckInsuranceByTrcuckDetailId?", params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.e("Response", response + "");

                        try {
                            if (response.getString("success").equalsIgnoreCase("true")) {
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                gsonBuilder.registerTypeAdapter(Insurence.class,
                                        new Insurence());
                                Gson gson = gsonBuilder.create();
                                Insurence user = gson.fromJson(response.get("result").toString(),
                                        new TypeToken<Insurence>() {
                                        }.getType());

                                RSMIT.getInstance().setInsurence(user);

                                try {
                                    login();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                ProgDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Please try After Sometime", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            ProgDialog.dismiss();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            ProgDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        ProgDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Please try After Sometime", Toast.LENGTH_SHORT).show();

                    }
                });
    }


    public void login() throws JSONException, UnsupportedEncodingException {
        JSONStringer jsonStringer = new JSONStringer().object()
                .key("UserName").value(txtUserName.getText().toString())
                .key("Password").value(txtPassword.getText().toString())
                .endObject();

        AsyncHttpClient client = new AsyncHttpClient();
        StringEntity entity = new StringEntity(jsonStringer.toString());
        client.post(this, RSMServer.BASE_URL + "IsValidUser", entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getString("result").equalsIgnoreCase("true")) {
                        Toast.makeText(getApplicationContext(), "Login Sucess", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        ProgDialog.dismiss();
                        finish();
                    } else {
                        ProgDialog.dismiss();
                        snackbar = Snackbar
                                .make(len, "Wrong User id And Password", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ProgDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("response_failure", responseString);
                ProgDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Please try After Sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                gp = true;
                // Signin button clicked
                signInWithGplus();
                break;
        }
    }
}