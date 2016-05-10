package com.ritul.truckshare.RegisterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ritul.truckshare.R;
import com.ritul.truckshare.Server.RSMServer;
import com.ritul.truckshare.Util.Prog_Dialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class CraditCardActivity extends AppCompatActivity {
    Spinner spinner;
    EditText txtholder;
    EditText txtcreditcardno;
    EditText txtCardExpire;
    EditText txtCVV;
    Prog_Dialog ProgDialog;
    Snackbar snackbar;
    LinearLayout len;
    String text;

    EditText txtZipCode;
    public static String creditcardtype,nameoncard,creditno,expire,cvvcode,zipcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cradit_card);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ProgDialog = new Prog_Dialog(this);
        len = (LinearLayout) findViewById(R.id.len);

        spinner = (Spinner) findViewById(R.id.spinner);
        txtholder = (EditText)findViewById(R.id.txtholder);
        txtcreditcardno = (EditText)findViewById(R.id.txtcreditcardno);
        txtCardExpire = (EditText)findViewById(R.id.txtCardExpire);
        txtCVV = (EditText)findViewById(R.id.txtCVV);
        txtZipCode = (EditText)findViewById(R.id.txtZipCode);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                if (CheckValidation()) {
                    creditcardtype = spinner.getSelectedItem().toString();
                    nameoncard = txtholder.getText().toString();
                    creditno = txtcreditcardno.getText().toString();
                    expire = txtCardExpire.getText().toString();
                    cvvcode = txtCVV.getText().toString();
                    zipcode = txtZipCode.getText().toString();
                    /*Intent intent = new Intent(getApplicationContext(), SuccessLoginActivity.class);
                    startActivity(intent);*/
                }
            }
        });


        List<String> categories = new ArrayList<String>();
        categories.add("Visa");
        categories.add("MasterCard");
        categories.add("Discover");
        categories.add("American Express");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        text = spinner.getSelectedItem().toString();

        txtCardExpire.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                prevL = txtCardExpire.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 2 )) {
                    editable.append("/");
                }
            }
        });


    }

    public  boolean CheckValidation()
    {

        if (txtholder.getText().toString().equals("")) {
            Toast.makeText(this, "Please Fill Card Name.", Toast.LENGTH_SHORT).show();
            txtholder.requestFocus();
            return false;
        }

        if (txtcreditcardno.getText().toString().equals("")) {
            Toast.makeText(this,"Please Fill Card No.",Toast.LENGTH_SHORT).show();
            txtcreditcardno.requestFocus();
            return false;
        } else {
            if(text.equalsIgnoreCase("Visa")) {
                if (!Validator.validate(txtcreditcardno.getText().toString(), Validator.VISA)) {
                    // Toast.makeText(this,"Plaese Fill Correct Creadit Card Number",Toast.LENGTH_SHORT).show();
                    snackbar = Snackbar
                            .make(len, "Plaese Fill Correct Creadit Card Number", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return false;
                }
            }
            else if(text.equalsIgnoreCase("MasterCard")){
                if (!Validator.validate(txtcreditcardno.getText().toString(), Validator.MASTERCARD)) {
                    snackbar = Snackbar
                            .make(len, "Plaese Fill Correct Creadit Card Number", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return false;
                }
            }
            else if(text.equalsIgnoreCase("Discover")){
                if (!Validator.validate(txtcreditcardno.getText().toString(), Validator.DISCOVER)) {
                    snackbar = Snackbar
                            .make(len, "Plaese Fill Correct Creadit Card Number", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return false;
                }
            }
            else{
                if (!Validator.validate(txtcreditcardno.getText().toString(), Validator.AMEX)) {
                    snackbar = Snackbar
                            .make(len, "Plaese Fill Correct Creadit Card Number", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return false;
                }
            }

        }

        if (txtCardExpire.getText().toString().equals("")) {
            Toast.makeText(this,"Please Fill Card Expire.",Toast.LENGTH_SHORT).show();
            txtCardExpire.requestFocus();
            return false;
        }

        else
        {

            if (validateCardExpiryDate(txtCardExpire.getText().toString())==false)
            {
                Toast.makeText(this,"Please Fill Card Expire Date In Proper Format.",Toast.LENGTH_SHORT).show();
                txtCardExpire.requestFocus();
                return false;
            }
        }


        if (txtCVV.getText().toString().equals("")) {
            Toast.makeText(this,"Please Fill CVV.",Toast.LENGTH_SHORT).show();
            txtCVV.requestFocus();
            return false;
        }



        if (txtZipCode.getText().toString().equals("")) {
            Toast.makeText(this,"Please Fill ZipCode.",Toast.LENGTH_SHORT).show();
            txtZipCode.requestFocus();
            return false;
        }
        try {
            register();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

       /* Toast.makeText(this,"Please Fill All Detail",Toast.LENGTH_SHORT).show();*/
        return  true;
    }

    boolean validateCardExpiryDate(String expiryDate) {
        return expiryDate.matches("(?:0[1-9]|1[0-2])/[0-9]{2}");
    }


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

                .key("CreditCardType").value(spinner.getSelectedItem().toString())
                .key("FullNameOnCard").value(txtholder.getText().toString())
                .key("CreditCardNumber").value(txtcreditcardno.getText().toString())
                .key("Expiration").value(txtCardExpire.getText().toString())
                .key("CvvCode").value(txtCVV.getText().toString())
                .key("ZipCode").value(txtZipCode.getText().toString())
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
                        ProgDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CraditCardActivity.this, SuccessLoginActivity.class);
                        startActivity(intent);


                    } else {
                        ProgDialog.dismiss();

                        snackbar = Snackbar
                                .make(len, "Somthing Went to wrong with Server Please try after Sometime", Snackbar.LENGTH_LONG);
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
