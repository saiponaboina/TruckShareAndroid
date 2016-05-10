package com.ritul.truckshare;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ritul.truckshare.Server.RSMServer;
import com.ritul.truckshare.Util.Prog_Dialog;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ForgotPasswordActivity extends AppCompatActivity {

    Prog_Dialog ProgDialog;
    TextView tv;
    EditText txtForgotEmail,etemail;
    Button BtnEmail,Btneid;
    Snackbar snackbar;
    LinearLayout len;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv = (TextView) findViewById(R.id.tv);
        txtForgotEmail = (EditText) findViewById(R.id.txtForgotEmail);
        etemail = (EditText) findViewById(R.id.etemail);
        BtnEmail = (Button) findViewById(R.id.BtnEmail);
        Btneid = (Button) findViewById(R.id.Btneid);
        ProgDialog = new Prog_Dialog(this);
        len = (LinearLayout) findViewById(R.id.len);

        BtnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtForgotEmail.getText().toString().equals("")){
                    snackbar = Snackbar
                            .make(len, "Please Fill User Name", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else
                try {
                    forget_password();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Btneid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(etemail.getText().toString().equals("")) {
                        snackbar = Snackbar
                                .make(len, "Please Fill Email Id", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    else{
                        try {
                        forget_password_email();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    }

            }
        });



    }
    public void forget_password() throws JSONException {

        ProgDialog.show();
        RequestParams params = new RequestParams();
        params.put("username", txtForgotEmail.getText().toString());

        RSMServer.get("ForgotPassword?", params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.e("Response", response + "");

                        try {
                            if (response.getString("success").equalsIgnoreCase("true")) {

                                String msg = response.getString("result");
                                tv.setVisibility(View.VISIBLE);
                                tv.setText(msg);
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


    public void forget_password_email() throws JSONException {

        ProgDialog.show();
        RequestParams params = new RequestParams();
        params.put("eMailId", etemail.getText().toString());

        RSMServer.get("ForgotSignId?", params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.e("Response", response + "");

                        try {
                            if (response.getString("success").equalsIgnoreCase("true"))  {

                                String msg = response.getString("result");
                                tv.setVisibility(View.VISIBLE);
                                tv.setText(msg);
                                ProgDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ProgDialog.dismiss();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            ProgDialog.dismiss();
                        }
                    }
                });
    }
}
