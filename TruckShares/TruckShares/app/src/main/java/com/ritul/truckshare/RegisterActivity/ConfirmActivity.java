package com.ritul.truckshare.RegisterActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ritul.truckshare.MainActivity;
import com.ritul.truckshare.R;

public class ConfirmActivity extends AppCompatActivity {

    Button btnyes,btnno;
    public static String AppUserType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnyes=(Button)findViewById(R.id.BtnYes);
        btnno=(Button)findViewById(R.id.BtnNo);
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUserType = "D";
                Intent intent=new Intent(getApplicationContext(),BankInformation.class);
                startActivity(intent);
            }
        });

        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUserType = "R";
                Intent intent=new Intent(getApplicationContext(),CraditCardActivity.class);
                startActivity(intent);
            }
        });


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to Quit Registration ?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
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
