package com.ritul.truckshare.RegisterActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ritul.truckshare.Adapter.Trucktype;
import com.ritul.truckshare.HomeActivity;
import com.ritul.truckshare.Pojo.Truckothertype;
import com.ritul.truckshare.Pojo.UserInformation;
import com.ritul.truckshare.R;
import com.ritul.truckshare.Server.RSMIT;
import com.ritul.truckshare.Server.RSMServer;
import com.ritul.truckshare.Util.AppConstant;
import com.ritul.truckshare.Util.JSONParser;
import com.ritul.truckshare.Util.Prog_Dialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class TruckSizeActivity extends AppCompatActivity {

    Spinner spinner;
    EditText txtTruckType;
    Prog_Dialog ProgDialog;
    Snackbar snackbar;
    LinearLayout len;
    List<Truckothertype> userHierarchy;
    String DriverDetailId,TruckDetailId;
    EditText txtTruckSize;
    String countryPosition="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_size);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ProgDialog = new Prog_Dialog(this);
        len = (LinearLayout) findViewById(R.id.len);

        txtTruckSize = (EditText)findViewById(R.id.txtTruckSize);
        spinner = (Spinner) findViewById(R.id.spinner);
        txtTruckType = (EditText) findViewById(R.id.txtTruckType);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckValidation()) {
                  /*  Intent intent = new Intent(getApplicationContext(), SuccessLoginActivity.class);
                    startActivity(intent);*/
                }
            }
        });

        userHierarchy = new ArrayList<>();
        if (AppConstant.isNetworkAvailable(this)) {
            try {
                trucktype();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            AppConstant.showNetworkError(this);
        }
        

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                countryPosition = userHierarchy.get(position).getTruckTypeId();
                if (position == 0) {
                    txtTruckType.setVisibility(View.VISIBLE);
                    countryPosition = "0";
                }
                else{
                    txtTruckType.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    public  boolean CheckValidation(){



        if (txtTruckSize.getText().toString().equals("")) {
            Toast.makeText(this, "Please Fill Size Of truck.", Toast.LENGTH_SHORT).show();
            txtTruckSize.requestFocus();
            return false;
        }
        if (AppConstant.isNetworkAvailable(this)) {
            try {
                register();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            AppConstant.showNetworkError(this);
        }
       /* Toast.makeText(this,"Please Fill All Detail",Toast.LENGTH_SHORT).show();*/
        return  true;
    }

    public void trucktype() throws JSONException, UnsupportedEncodingException {
        ProgDialog.show();
        String ServiceUrl = "http://trucksharelle.showdemonow.com/api/services/TruckShareSystem/TruckType/GetAllTruckTypes" ;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ServiceUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    if (response.getString("success").equalsIgnoreCase("true")) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.registerTypeAdapter(Truckothertype.class,
                                new Truckothertype());
                        Gson gson = gsonBuilder.create();
                        userHierarchy = gson.fromJson(response.get("result").toString(), new TypeToken<List<Truckothertype>>() {
                        }.getType());

                        Trucktype adapter = new Trucktype(getApplicationContext(), userHierarchy);
                        spinner.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        ProgDialog.dismiss();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("response_failure", responseString);
                ProgDialog.dismiss();
            }
        });
    }


//-------------------------------Register-----------------------------------------------------------

    public void register() throws JSONException, UnsupportedEncodingException {
        ProgDialog.show();
        JSONStringer jsonStringer = new JSONStringer().object()
                .key("AuthenticationSource").value("Internal")
                .key("FirstName").value(RegisterActivity.firstname)
                .key("LastName").value(RegisterActivity.lastname)
                .key("Password").value(RegisterActivity.psw)
                .key("EmailAddress").value(RegisterActivity.email)
                .key("SsnNumber").value(BackgroundCheckActivity.ssn)
                .key("MobileNumber").value(RegisterActivity.mobile)
                .key("ProfileImageBase64String").value(ProfilePictureActivity.ProfileImageBase64String)
                .key("ProfileImageFormat").value("jpg")
                .key("AppUserType").value(ConfirmActivity.AppUserType)

                .key("AccountType").value(BankInformation.acoounttype)
                .key("AccountHolderName").value(BankInformation.accountholder)
                .key("BankRoutingNumber").value(BankInformation.bankroute)
                .key("BankAccountNumber").value(BankInformation.bankno)

                .key("CreditCardType").value(CraditCardActivity.creditcardtype)
                .key("FullNameOnCard").value(CraditCardActivity.nameoncard)
                .key("CreditCardNumber").value(CraditCardActivity.creditno)
                .key("Expiration").value(CraditCardActivity.expire)
                .key("CvvCode").value(CraditCardActivity.cvvcode)
                .key("ZipCode").value(CraditCardActivity.zipcode)
                .endObject();

        AsyncHttpClient client = new AsyncHttpClient();
        StringEntity entity = new StringEntity(jsonStringer.toString());
        Log.e("jsonStringer", jsonStringer.toString());
        client.post(this, RSMServer.BASE_URL + "RegisterAppUser", entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getString("success").equalsIgnoreCase("true")) {

                        try {
                            userdetail();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        ProgDialog.dismiss();

                        snackbar = Snackbar
                                .make(len, "Somthing Went to wrong with Server Please try after Sometime", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("response_failure", responseString);
                ProgDialog.dismiss();
            }
        });
    }

    public void userdetail() throws JSONException {

        RequestParams params = new RequestParams();
        params.put("userName", RegisterActivity.email);
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

                                try {
                                    insert_driver();
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
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        snackbar = Snackbar
                                .make(len, "Somthing Went to wrong with Server Please try after Sometime", Snackbar.LENGTH_LONG);
                        Log.e("response_failure", responseString);
                        ProgDialog.dismiss();
                    }
                });
    }


//--------------------------------------Driver------------------------------------------------------

    public void insert_driver() throws JSONException, UnsupportedEncodingException {

        JSONStringer jsonStringer = new JSONStringer().object()
                .key("AppUserId").value(RSMIT.getInstance().getUser().getAppUserId())
                .key("LicenseNumber").value(DriverLicenseActivity.LicenseNumber)
                .key("LicenseIssuedStateId").value(DriverLicenseActivity.LicenseIssuedStateId)
                .key("LicenseExpiryDate").value(DriverLicenseActivity.LicenseExpiryDate)
                .key("LicenseImageBase64String").value(DriverLicenseActivity.LicenseImageBase64String)
                .key("LicenseImageFormat").value("jpg")
                .endObject();

        AsyncHttpClient client = new AsyncHttpClient();
        StringEntity entity = new StringEntity(jsonStringer.toString());
        Log.e("jsonStringer", jsonStringer.toString());
        client.post(this, RSMServer.BASE_URLT + "DriverDetail/InsertDriverDetails", entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getString("success").equalsIgnoreCase("true")) {

                        DriverDetailId = response.getString("result");
                        try {
                            truck_information();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                    } else {
                        ProgDialog.dismiss();

                        snackbar = Snackbar
                                .make(len, "Somthing Went to wrong with Server Please try after Sometime", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (Exception e) {
                    ProgDialog.dismiss();
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("response_failure", responseString);
                ProgDialog.dismiss();
            }
        });
    }

//------------------------------------------------Truck---------------------------------------------

    public void truck_information() throws JSONException, UnsupportedEncodingException {

        JSONStringer jsonStringer = new JSONStringer().object()
                .key("DriverDetailId").value(DriverDetailId)
                .key("TruckRegistrationNumber").value(TruckActivity.txtTruckRNo1)
                .key("TruckVinNumber").value(TruckActivity.txtVinNo1)
                .key("TruckRegisteredStateId").value(TruckActivity.TruckRegisteredStateId)
                .key("TruckTitleDocImageBase64String").value(TruckActivity.TruckTitleDocImageBase64String)
                .key("TruckTitleDocImageFormat").value("jpg")
                .key("TruckSize").value(txtTruckSize.getText().toString())
                .key("TruckTypeId").value(countryPosition)
                .key("TruckType").value(txtTruckType.getText().toString())
                .endObject();

        AsyncHttpClient client = new AsyncHttpClient();
        StringEntity entity = new StringEntity(jsonStringer.toString());
        Log.e("jsonStringer", jsonStringer.toString());
        client.post(this, RSMServer.BASE_URLT + "TruckDetail/InsertTruckDetail", entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getString("success").equalsIgnoreCase("true")) {
                        TruckDetailId = response.getString("result");
                        try {
                            truck_image();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                    } else {
                        ProgDialog.dismiss();

                        snackbar = Snackbar
                                .make(len, "Somthing Went to wrong with Server Please try after Sometime", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (Exception e) {
                    ProgDialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("response_failure", responseString);
                ProgDialog.dismiss();
            }
        });
    }

    public void truck_image() throws JSONException, UnsupportedEncodingException {

        JSONStringer jsonStringer = new JSONStringer().object()
                .key("TruckDetailId").value(TruckDetailId)
                .key("TruckImgBase64String1").value(SelectTruckActivity.ProfileImageBase64String1)
                .key("TruckImageFormat1").value("jpg")
                .key("TruckImgBase64String2").value(SelectTruckActivity.ProfileImageBase64String2)
                .key("TruckImageFormat2").value("jpg")
                .key("TruckImgBase64String3").value(SelectTruckActivity.ProfileImageBase64String3)
                .key("TruckImageFormat3").value("jpg")
                .key("TruckImgBase64String4").value(SelectTruckActivity.ProfileImageBase64String4)
                .key("TruckImageFormat4").value("jpg")
                .key("TruckImgBase64String5").value(SelectTruckActivity.ProfileImageBase64String5)
                .key("TruckImageFormat5").value("jpg")
                .key("TruckImgBase64String6").value(SelectTruckActivity.ProfileImageBase64String6)
                .key("TruckImageFormat6").value("jpg")
                .key("TruckImgBase64String7").value(SelectTruckActivity.ProfileImageBase64String7)
                .key("TruckImageFormat7").value("jpg")
                .key("TruckImgBase64String8").value(SelectTruckActivity.ProfileImageBase64String8)
                .key("TruckImageFormat8").value("jpg")
                .endObject();

        AsyncHttpClient client = new AsyncHttpClient();
        StringEntity entity = new StringEntity(jsonStringer.toString());
        Log.e("jsonStringer", jsonStringer.toString());
        client.post(this, RSMServer.BASE_URLT + "TruckImage/InsertTruckImageDetail", entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getString("success").equalsIgnoreCase("true")) {

                        try {
                            truck_insurence();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }


                    } else {
                        ProgDialog.dismiss();

                        snackbar = Snackbar
                                .make(len, "Somthing Went to wrong with Server Please try after Sometime", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (Exception e) {
                    ProgDialog.dismiss();
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("response_failure", responseString);
                ProgDialog.dismiss();
            }
        });
    }

    public void truck_insurence() throws JSONException, UnsupportedEncodingException {

        JSONStringer jsonStringer = new JSONStringer().object()
                .key("TruckDetailId").value(TruckDetailId)
                .key("InsuranceProvider").value(InsuranceActivity.InsuranceProvider)
                .key("PolicyNumber").value(InsuranceActivity.PolicyNumber)
                .key("DateOfExpiry").value(InsuranceActivity.DateOfExpiry)
                .key("InsuranceDocImageBase64String").value(InsuranceActivity.InsuranceDocImageBase64String)
                .key("InsuranceDocImageFormat").value("jpg")
                .endObject();

        AsyncHttpClient client = new AsyncHttpClient();
        StringEntity entity = new StringEntity(jsonStringer.toString());
        Log.e("jsonStringer", jsonStringer.toString());
        client.post(this, RSMServer.BASE_URLT + "TruckInsurance/InsertTruckInsuranceDetail", entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getString("success").equalsIgnoreCase("true")) {

                        Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TruckSizeActivity.this, SuccessLoginActivity.class);
                        startActivity(intent);
                        finish();
                        ProgDialog.dismiss();


                    } else {
                        ProgDialog.dismiss();

                        snackbar = Snackbar
                                .make(len, "Somthing Went to wrong with Server Please try after Sometime", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (Exception e) {
                    ProgDialog.dismiss();
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                snackbar = Snackbar
                        .make(len, "Somthing Went to wrong with Server Please try after Sometime", Snackbar.LENGTH_LONG);
                Log.e("response_failure", responseString);
                ProgDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
