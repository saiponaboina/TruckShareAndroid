package com.ritul.truckshare.RegisterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.ritul.truckshare.R;

public class BackgroundCheckActivity extends AppCompatActivity {

    public static String ssn;
    EditText txtSecurityNo;
    CheckBox Chktems,cbShowPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_check);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtSecurityNo = (EditText)findViewById(R.id.txtSecurityNo);
        Chktems = (CheckBox)findViewById(R.id.Chktems);
        cbShowPwd = (CheckBox)findViewById(R.id.cbShowPwd);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                if (CheckValidation()) {
                    ssn = txtSecurityNo.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), DriverLicenseActivity.class);
                    startActivity(intent);
                }
            }
        });

        cbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    txtSecurityNo.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    txtSecurityNo.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

    }

    public  boolean CheckValidation()
    {

        if (txtSecurityNo.getText().toString().equals("")) {
            Toast.makeText(this, "Please Fill Security No.", Toast.LENGTH_SHORT).show();
            txtSecurityNo.requestFocus();
            return false;
        }

        if (Chktems.isChecked()==false) {
            Toast.makeText(this,"Please Accept Agreement.",Toast.LENGTH_SHORT).show();
            Chktems.requestFocus();
            return false;
        }


       /* Toast.makeText(this,"Please Fill All Detail",Toast.LENGTH_SHORT).show();*/
        return  true;
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
