package com.ritul.truckshare.RegisterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ritul.truckshare.R;

public class BankInformation extends AppCompatActivity {
    EditText txtaccountname;
    EditText txtbankrouting;
    EditText txtBankAccount;
    EditText txtConfirm;
    RadioGroup rg;
    RadioButton rb1, rb2;
    public static String acoounttype,accountholder,bankroute,bankno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_information);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rg = (RadioGroup)findViewById(R.id.rg);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);

        txtaccountname = (EditText)findViewById(R.id.txtaccountname);
        txtbankrouting = (EditText)findViewById(R.id.txtbankrouting);
        txtBankAccount = (EditText)findViewById(R.id.txtBankAccount);
        txtConfirm = (EditText)findViewById(R.id.txtConfirm);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (CheckValidation()) {
                    String radiovalue = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                    acoounttype = radiovalue;

                    accountholder = txtaccountname.getText().toString();
                    bankroute = txtbankrouting.getText().toString();
                    bankno = txtbankrouting.getText().toString();



                    Intent intent = new Intent(getApplicationContext(), ProfilePictureActivity.class);
                    startActivity(intent);
                }
            }
        });




    }


    public boolean CheckValidation()
    {



        if (rb1.isChecked() || rb2.isChecked()) {

        } else {
            Toast.makeText(getApplicationContext(), "Please select Payment Option.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtaccountname.getText().toString().equals("")) {
            Toast.makeText(this, "Please Fill Card Holder Name.", Toast.LENGTH_SHORT).show();
            txtaccountname.requestFocus();
            return false;
        }

        if (txtbankrouting.getText().toString().equals("")) {
            Toast.makeText(this,"Please Fill Bank Routing No.",Toast.LENGTH_SHORT).show();
            txtbankrouting.requestFocus();
            return false;
        }
        if (txtBankAccount.getText().toString().equals("")) {
            Toast.makeText(this,"Please Fill Bank Account No.",Toast.LENGTH_SHORT).show();
            txtBankAccount.requestFocus();
            return false;
        }



        if (txtConfirm.getText().toString().equals("")) {
            Toast.makeText(this,"Please Fill Confirm Account No.",Toast.LENGTH_SHORT).show();
            txtConfirm.requestFocus();
            return false;
        }
        else if(!txtConfirm.getText().toString().equals(txtBankAccount.getText().toString()) ) {
            Toast.makeText(this,"Confirm Account Number. Same as Bank Account Number",Toast.LENGTH_SHORT).show();
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
