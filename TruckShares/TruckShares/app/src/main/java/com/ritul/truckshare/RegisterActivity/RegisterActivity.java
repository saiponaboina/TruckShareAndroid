package com.ritul.truckshare.RegisterActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ritul.truckshare.R;
import com.ritul.truckshare.Server.RSMServer;
import com.ritul.truckshare.Util.Prog_Dialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {

    Prog_Dialog ProgDialog;
    EditText txtFirstName;
    EditText txtLastName;
    EditText txtEmail;
    EditText txtPassWord;
    EditText txtMobile;
    Snackbar snackbar;
    LinearLayout len;

    public static String firstname,lastname,email,psw,mobile,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtFirstName = (EditText)findViewById(R.id.txtFirstName);
        txtLastName = (EditText)findViewById(R.id.txtLastName);
        txtMobile = (EditText)findViewById(R.id.txtMobile);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPassWord = (EditText)findViewById(R.id.txtRPassword);

        ProgDialog = new Prog_Dialog(this);
        len = (LinearLayout) findViewById(R.id.len);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if (CheckValidation()) {

                   firstname = txtFirstName.getText().toString();
                   lastname = txtLastName.getText().toString();
                   email = txtEmail.getText().toString();
                   psw = txtPassWord.getText().toString();
                   mobile = txtMobile.getText().toString();

                   /*Intent intent = new Intent ( getApplicationContext(), ConfirmActivity.class );
                   startActivity(intent);*/
               }
            }
        });
    }

    public  boolean CheckValidation() {

        if (txtFirstName.getText().toString().equals("")) {
            Toast.makeText(this,"Please Fill First Name.",Toast.LENGTH_SHORT).show();
            txtFirstName.requestFocus();
            return false;
        }

        if (txtLastName.getText().toString().equals("")) {
            Toast.makeText(this,"Please Fill Last Name.",Toast.LENGTH_SHORT).show();
            txtLastName.requestFocus();
            return false;
        }


        if (txtEmail.getText().toString().equals("")) {
            Toast.makeText(this,"Please Fill Email.",Toast.LENGTH_SHORT).show();
            txtEmail.requestFocus();
            return false;
        }
        else {
            if (!isValidEmail(txtEmail.getText().toString())) {
                txtEmail.setError("Invalid Email");
                txtEmail.requestFocus();
                return false;
            }
        }


        if (txtPassWord.getText().toString().equals("")) {
            Toast.makeText(this,"Please Fill Password.",Toast.LENGTH_SHORT).show();
            txtPassWord.requestFocus();
            return false;
        }


        if (txtMobile.getText().toString().equals("")) {
            Toast.makeText(this,"Please Fill Mobile.",Toast.LENGTH_SHORT).show();
            txtMobile.requestFocus();
            return false;
        }
        else {
            if (!isIs_mob_number(txtMobile.getText().toString())) {
                txtMobile.setError("Mobile should be 10 digit ");
                txtMobile.requestFocus();
                return false;
            }
        }

        try {
            Email_register();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  true;
    }

    public boolean isIs_mob_number(String psw) {
        Pattern pattern = Pattern.compile("\\d{10}");
        Matcher matchr = pattern.matcher(psw);

        return matchr.matches();
    }


    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void Email_register() throws JSONException {

        ProgDialog.show();
        RequestParams params = new RequestParams();
        params.put("eMailId", txtEmail.getText().toString());

        RSMServer.get("IsEmailAlreadyExist?", params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                        super.onSuccess(statusCode, headers, response);
                        Log.e("Response", response + "");

                        try {
                            if (response.getString("result").equalsIgnoreCase("true")) {
                               /* Log.e("Response", response.getString("success"));
                                Intent i = new Intent(RegisterActivity.this, ConfirmActivity.class);
                                startActivity(i);
                                finish();
                                ProgDialog.show();*/
                                ProgDialog.dismiss();
                                snackbar = Snackbar
                                        .make(len, "Email id Is Already Register Plaese try new one", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            } else {
                                Log.e("Response", response.getString("success"));
                                Intent i = new Intent(RegisterActivity.this, ConfirmActivity.class);
                                startActivity(i);
                                finish();
                                ProgDialog.dismiss();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ProgDialog.dismiss();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
//                            dialog.dismiss();
                            ProgDialog.dismiss();
                        }
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
