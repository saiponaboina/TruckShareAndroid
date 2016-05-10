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
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import android.util.Base64;

import com.ritul.truckshare.R;
import com.ritul.truckshare.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfilePictureActivity extends AppCompatActivity {
    Uri selectedImageUri;
    String  selectedPath;
    ImageView preview;
    boolean isImage = false;
    Bitmap b;
    byte[] imageByte;
    String imagedata;
    String selectedpath;
    private Uri fileUri;
    public static String ProfileImageBase64String;
    public static final int MEDIA_TYPE_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preview = (ImageView)findViewById(R.id.imgProfile);
        Button b = (Button) findViewById(R.id.BtnBrowse);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                if (isImage) {
                    ProfileImageBase64String=imagedata;
                    Intent intent = new Intent(getApplicationContext(), BackgroundCheckActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ProfilePictureActivity.this, "Please Upload Image !", Toast.LENGTH_SHORT).show();
                }

            }
        });


        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //Image_Picker_Dialog();
                Intent cameraIntent1 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent1, 11);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 11:
                if (requestCode == 11 && resultCode == RESULT_OK) {
                    isImage = true;
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    preview.setImageBitmap(photo);
                    Bitmap bitmap = ((BitmapDrawable) preview.getDrawable()).getBitmap();
                    imagedata = Base64.encodeToString(getBytesFromBitmap(bitmap), Base64.DEFAULT);
                }
                break;

        }

    }

/*    public void Image_Picker_Dialog()
    {

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Pictures Option");
        myAlertDialog.setMessage("Select Picture Mode");

        myAlertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface arg0, int arg1)
            {

                Intent i7= new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i7, Utility.GALLERY_PICTURE );
            }
        });

        myAlertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                Intent cameraIntent1 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                startActivityForResult(cameraIntent1, Utility.CAMERA_PICTURE);

            }
        });
        myAlertDialog.show();
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "Check It");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }
    // After the selection of image you will retun on the main activity with bitmap image
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Utility.GALLERY_PICTURE && data != null)
        {
            isImage = true;
            Image_Selecting_Task(data);
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = ProfilePictureActivity.this.getContentResolver().query(selectedImage,
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
            Log.e("vipul",imagedata);

        } else if (requestCode == Utility.CAMERA_PICTURE && data != null)
        {
             isImage = true;
             //Image_Selecting_Task(data);
                if (requestCode == Utility.CAMERA_PICTURE && resultCode == RESULT_OK && data != null) {

                *//*BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
                selectedpath = fileUri.getPath();
                Log.e("camera", selectedpath);*//*

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                preview.setImageBitmap(photo);

                Bitmap bitmap = ((BitmapDrawable) preview.getDrawable()).getBitmap();
                imagedata = Base64.encodeToString(getBytesFromBitmap(bitmap), Base64.DEFAULT);

                //convertimagetobyte();
                //imagedata = Base64.encodeToString(imageByte, Base64.DEFAULT);
                //Log.e("vipul", imagedata);
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
                b = Utility.decodeFile(Utility.Paste_Target_Location);

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
            Log.e("onActivityResult" , "" + e);
        }
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
        ContextWrapper cw = new ContextWrapper(ProfilePictureActivity.this);
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
    }*/



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
