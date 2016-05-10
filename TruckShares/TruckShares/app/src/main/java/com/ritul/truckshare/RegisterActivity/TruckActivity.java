package com.ritul.truckshare.RegisterActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.ArrayAdapter;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ritul.truckshare.Adapter.StateSpinnerAdapter;
import com.ritul.truckshare.Pojo.StateSpinner;
import com.ritul.truckshare.R;
import com.ritul.truckshare.Util.AppConstant;
import com.ritul.truckshare.Util.Prog_Dialog;
import com.ritul.truckshare.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TruckActivity extends AppCompatActivity {
    Uri selectedImageUri;
    String  selectedPath;
    ImageView preview;
    boolean isImage = false;
    Spinner staticSpinner;
    Prog_Dialog ProgDialog;
    List<StateSpinner> userHierarchy;
    public static String txtTruckRNo1,txtVinNo1,TruckTitleDocImageBase64String,TruckRegisteredStateId;
    EditText txtTruckRNo;
    EditText txtVinNo;
    byte[] imageByte;
    String imagedata;
    String selectedpath;
    String countryPosition="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ProgDialog = new Prog_Dialog(this);
        staticSpinner = (Spinner) findViewById(R.id.txtRegistaedState);
        preview = (ImageView)findViewById(R.id.imgTruck);
        Button b = (Button) findViewById(R.id.BtnTruckBrowse);

        userHierarchy = new ArrayList<> ();
        if (AppConstant.isNetworkAvailable(this)) {
            try {
                state_spiner();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            AppConstant.showNetworkError(this);
        }
        staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                countryPosition = userHierarchy.get(position).getStateId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                if (CheckValidation() ) {
                    TruckRegisteredStateId=countryPosition;
                    txtTruckRNo1 = txtTruckRNo.getText().toString();
                    txtVinNo1 = txtVinNo.getText().toString();
                    TruckTitleDocImageBase64String = imagedata;
                    Intent intent = new Intent(getApplicationContext(), InsuranceActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(TruckActivity.this, "Please Upload Image !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
              /*  Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);*/
                Image_Picker_Dialog();
            }
        });
    }
    public void state_spiner() throws JSONException, UnsupportedEncodingException {
        ProgDialog.show();
        String ServiceUrl = "http://trucksharelle.showdemonow.com/api/services/TruckShareSystem/State/GetAllStates";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ServiceUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    if (response.getString("success").equalsIgnoreCase("true")) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.registerTypeAdapter(StateSpinner.class,
                                new StateSpinner());
                        Gson gson = gsonBuilder.create();
                        userHierarchy = gson.fromJson(response.get("result").toString(), new TypeToken<List<StateSpinner>>() {
                        }.getType());

                        StateSpinnerAdapter adapter = new StateSpinnerAdapter(getApplicationContext(), userHierarchy);
                        staticSpinner.setAdapter(adapter);
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
    public void Image_Picker_Dialog()
    {

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Pictures Option");
        myAlertDialog.setMessage("Select Picture Mode");

        myAlertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
              /*  Utility.pictureActionIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
                Utility.pictureActionIntent.setType("image*//*");
                Utility.pictureActionIntent.putExtra("return-data", true);
                startActivityForResult(Utility.pictureActionIntent, Utility.GALLERY_PICTURE);*/
                Intent i7 = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i7, Utility.GALLERY_PICTURE);
            }
        });

        myAlertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                /*Utility.pictureActionIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(Utility.pictureActionIntent, Utility.CAMERA_PICTURE);*/
                Intent cameraIntent1 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent1, Utility.CAMERA_PICTURE);
            }
        });
        myAlertDialog.show();

    }

    // After the selection of image you will retun on the main activity with bitmap image
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Utility.GALLERY_PICTURE  && data!=null)
        {
            isImage = true;
            // data contains result
            // Do some task
            Image_Selecting_Task(data);
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = TruckActivity.this.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            selectedpath = cursor.getString(columnIndex);
            cursor.close();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(selectedpath, options);
            convertimagetobyte();
            imagedata = Base64.encodeToString(imageByte, Base64.DEFAULT);
        } else if (requestCode == Utility.CAMERA_PICTURE && data!=null)
        {
            isImage = true;
            // Do some task
            //Image_Selecting_Task(data);
            if (requestCode == Utility.CAMERA_PICTURE && resultCode == RESULT_OK && data != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                preview.setImageBitmap(photo);
                Bitmap bitmap = ((BitmapDrawable) preview.getDrawable()).getBitmap();
                imagedata = Base64.encodeToString(getBytesFromBitmap(bitmap), Base64.DEFAULT);
            }
        }
    }

    public void Image_Selecting_Task(Intent data)
    {
        try
        {
            Utility.uri = data.getData();
            if (Utility.uri != null)
            {
                // User had pick an image.
                Cursor cursor = getContentResolver().query(Utility.uri, new String[]
                        { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
                cursor.moveToFirst();
                // Link to the image
                final String imageFilePath = cursor.getString(0);

                //Assign string path to File
                Utility.Default_DIR = new File(imageFilePath);

                // Create new dir MY_IMAGES_DIR if not created and copy image into that dir and store that image path in valid_photo
                Utility.Create_MY_IMAGES_DIR();

                // Copy your image
                Utility.copyFile(Utility.Default_DIR, Utility.MY_IMG_DIR);

                // Get new image path and decode it
                Bitmap b = Utility.decodeFile(Utility.Paste_Target_Location);

                // use new copied path and use anywhere
                String valid_photo = Utility.Paste_Target_Location.toString();
                b = Bitmap.createScaledBitmap(b, 150, 150, true);

                //set your selected image in image view
                preview.setImageBitmap(b);
                cursor.close();

            } else
            {
                Toast toast = Toast.makeText(this, "Sorry!!! You haven't selecet any image.", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (Exception e)
        {
            // you get this when you will not select any single image
            Log.e("onActivityResult", "" + e);

        }
    }


    public  boolean CheckValidation()
    {

       // EditText txtRegistaedState;


        txtTruckRNo = (EditText)findViewById(R.id.txtTruckRNo);
        txtVinNo = (EditText)findViewById(R.id.txtVinNo);
       // txtRegistaedState = (EditText)findViewById(R.id.txtRegistaedState);

        if (txtTruckRNo.getText().toString().equals("")) {
            Toast.makeText(this, "Please Fill Truck Registration No.", Toast.LENGTH_SHORT).show();
            txtTruckRNo.requestFocus();
            return false;
        }

        if (txtVinNo.getText().toString().equals("")) {
            Toast.makeText(this,"Please Fill VIN Number.",Toast.LENGTH_SHORT).show();
            txtVinNo.requestFocus();
            return false;
        }

        return  true;
    }
    private void convertimagetobyte() {

        if (selectedpath.length() > 0) {
            String picturePath = selectedpath;
            int MAX_IMAGE_SIZE = 200 * 1024; // max final file size
            Bitmap bmpPic = BitmapFactory.decodeFile(picturePath);
            picturePath = saveToInternalSorage(bmpPic);
            bmpPic = BitmapFactory.decodeFile(picturePath);
            if ((bmpPic.getWidth() >= 100) && (bmpPic.getHeight() >= 100)) {
                BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
                bmpOptions.inSampleSize = 1;
                while ((bmpPic.getWidth() >= 100) && (bmpPic.getHeight() >= 100)) {
                    bmpOptions.inSampleSize++;
                    bmpPic = BitmapFactory.decodeFile(picturePath, bmpOptions);
                }
                //Log.d(, "Resize: " + bmpOptions.inSampleSize);
            }
            int compressQuality = 104; // quality decreasing by 5 every loop. (start from 99)
            int streamLength = MAX_IMAGE_SIZE;
            boolean _isComp = false;
            while (streamLength >= MAX_IMAGE_SIZE) {
                ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
                compressQuality -= 5;
                //Log.d(TAG, "Quality: " + compressQuality);
                bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
                imageByte = bmpStream.toByteArray();
                streamLength = imageByte.length;
                _isComp = true;
                //  Log.d(TAG, "Size: " + streamLength);
            }
            if (!_isComp) {
                try {
                    FileOutputStream bmpFile = new FileOutputStream(picturePath);
                    ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
                    bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
                    imageByte = bmpStream.toByteArray();
                    bmpFile.flush();
                    bmpFile.close();
                } catch (Exception e) {
                    // Log.e(TAG, "Error on saving file");
                }
            }
        }
    }
    private String saveToInternalSorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(TruckActivity.this);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "tmpimage.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mypath.getPath();
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
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
