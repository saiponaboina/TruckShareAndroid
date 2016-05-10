package com.ritul.truckshare.RegisterActivity;

import android.content.Context;
import android.content.ContextWrapper;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.ritul.truckshare.R;
import com.ritul.truckshare.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class SelectTruckActivity extends AppCompatActivity {

    int i=0;
    byte[] imageByte;
    String imagedata1,imagedata2,imagedata3,imagedata4,imagedata5,imagedata6,imagedata7,imagedata8 ;
    public static String ProfileImageBase64String1, ProfileImageBase64String2,ProfileImageBase64String3,ProfileImageBase64String4,
            ProfileImageBase64String5,ProfileImageBase64String6,ProfileImageBase64String7,ProfileImageBase64String8;
    String picturePath1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_truck);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                if (i>=6) {
                    ProfileImageBase64String1=imagedata1;
                    ProfileImageBase64String2=imagedata2;
                    ProfileImageBase64String3=imagedata3;
                    ProfileImageBase64String4=imagedata4;
                    ProfileImageBase64String5=imagedata5;
                    ProfileImageBase64String6=imagedata6;
                    ProfileImageBase64String7=imagedata7;
                    ProfileImageBase64String8=imagedata8;
                    Intent intent = new Intent(getApplicationContext(), TruckSizeActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SelectTruckActivity.this, "Please Select Atleast 6 Images.", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.BtnUpload1:
                Intent i1 = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i1, 1);
                break;
            case R.id.BtnUpload2:
                Intent i2 = new Intent (
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i2, 2);
                break;
            case R.id.BtnUpload3:
                Intent i3 = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i3, 3);
                break;
            case R.id.BtnUpload4:
                Intent i4 = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i4, 4);
                break;
            case R.id.BtnUpload5:
                Intent i5 = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i5, 5);
                break;
            case R.id.BtnUpload6:
                Intent i6 = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i6, 6);
                break;
            case R.id.BtnUpload7:
                Intent i7= new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i7, 7);
                break;
            case R.id.BtnUpload8:
                Intent i8 = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i8, 8);
                break;



            case R.id.BtnCapture1:
                Intent cameraIntent1 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent1, 11);
                break;
            case R.id.BtnCapture2:
                Intent cameraIntent2 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent2, 22);
                break;
            case R.id.BtnCapture3:
                Intent cameraIntent3 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent3, 33);
                break;
            case R.id.BtnCapture4:
                Intent cameraIntent4 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent4, 44);
                break;
            case R.id.BtnCapture5:
                Intent cameraIntent5 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent5, 55);
                break;
            case R.id.BtnCapture6:
                Intent cameraIntent6 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent6, 66);
                break;
            case R.id.BtnCapture7:
                Intent cameraIntent7 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent7, 77);
                break;
            case R.id.BtnCapture8:
                Intent cameraIntent8 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent8, 88);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 1:
                if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                    i++;
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath1 = cursor.getString(columnIndex);
                    cursor.close();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(picturePath1, options);
                    convertimagetobyte();
                    imagedata1 = Base64.encodeToString(imageByte, Base64.DEFAULT);
                    Log.e("vipul",imagedata1);

                    ImageView imageView = (ImageView) findViewById(R.id.imgTruck1);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath1));
                }

                break;

            case 2:
                if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
                    i++;
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath1 = cursor.getString(columnIndex);
                    cursor.close();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(picturePath1, options);
                    convertimagetobyte();
                    imagedata2 = Base64.encodeToString(imageByte, Base64.DEFAULT);

                    ImageView imageView = (ImageView) findViewById(R.id.imgTruck2);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath1));

                }
                break;
            case 3:
                if (requestCode == 3 && resultCode == RESULT_OK && null != data) {
                    i++;
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath1 = cursor.getString(columnIndex);
                    cursor.close();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(picturePath1, options);
                    convertimagetobyte();
                    imagedata3 = Base64.encodeToString(imageByte, Base64.DEFAULT);


                    ImageView imageView = (ImageView) findViewById(R.id.imgTruck3);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath1));

                }
                break;case 4:
                if (requestCode == 4 && resultCode == RESULT_OK && null != data) {
                    i++;
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath1 = cursor.getString(columnIndex);
                    cursor.close();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(picturePath1, options);
                    convertimagetobyte();
                    imagedata4 = Base64.encodeToString(imageByte, Base64.DEFAULT);

                    ImageView imageView = (ImageView) findViewById(R.id.imgTruck4);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath1));

                }
                break;case 5:
                if (requestCode == 5 && resultCode == RESULT_OK && null != data) {
                    i++;
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath1 = cursor.getString(columnIndex);
                    cursor.close();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(picturePath1, options);
                    convertimagetobyte();
                    imagedata5 = Base64.encodeToString(imageByte, Base64.DEFAULT);

                    ImageView imageView = (ImageView) findViewById(R.id.imgTruck5);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath1));

                }
                break;

                case 6:
                if (requestCode == 6 && resultCode == RESULT_OK && null != data) {
                    i++;
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath1 = cursor.getString(columnIndex);
                    cursor.close();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(picturePath1, options);
                    convertimagetobyte();
                    imagedata6 = Base64.encodeToString(imageByte, Base64.DEFAULT);

                    ImageView imageView = (ImageView) findViewById(R.id.imgTruck6);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath1));

                }
                break;case 7:
                if (requestCode == 7 && resultCode == RESULT_OK && null != data) {
                    i++;
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath1 = cursor.getString(columnIndex);
                    cursor.close();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(picturePath1, options);
                    convertimagetobyte();
                    imagedata7 = Base64.encodeToString(imageByte, Base64.DEFAULT);

                    ImageView imageView = (ImageView) findViewById(R.id.imgTruck7);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath1));

                }
                break;case 8:
                if (requestCode == 8 && resultCode == RESULT_OK && null != data) {
                    i++;
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath1 = cursor.getString(columnIndex);
                    cursor.close();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(picturePath1, options);
                    convertimagetobyte();
                    imagedata8 = Base64.encodeToString(imageByte, Base64.DEFAULT);

                    ImageView imageView = (ImageView) findViewById(R.id.imgTruck8);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath1));

                }
                break;

            case 11:
                if (requestCode == 11 && resultCode == RESULT_OK) {
                    i++;
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    ImageView imageView = (ImageView) findViewById(R.id.imgTruck1);
                    imageView.setImageBitmap(photo);

                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    imagedata1 = Base64.encodeToString(getBytesFromBitmap(bitmap), Base64.DEFAULT);
                }
                break;
            case 22:
                if (requestCode == 22 && resultCode == RESULT_OK) {
                    i++;
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    ImageView imageView = (ImageView) findViewById(R.id.imgTruck2);
                    imageView.setImageBitmap(photo);

                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    imagedata2 = Base64.encodeToString(getBytesFromBitmap(bitmap), Base64.DEFAULT);

                }
                break;
            case 33:
                if (requestCode == 33 && resultCode == RESULT_OK) {
                    i++;
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    ImageView imageView = (ImageView) findViewById(R.id.imgTruck3);
                    imageView.setImageBitmap(photo);

                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    imagedata3 = Base64.encodeToString(getBytesFromBitmap(bitmap), Base64.DEFAULT);

                }
                break;
            case 44:
                if (requestCode == 44 && resultCode == RESULT_OK) {
                    i++;
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    ImageView imageView = (ImageView) findViewById(R.id.imgTruck4);
                    imageView.setImageBitmap(photo);

                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    imagedata4 = Base64.encodeToString(getBytesFromBitmap(bitmap), Base64.DEFAULT);
                }
                break;
            case 55:
                if (requestCode == 55 && resultCode == RESULT_OK) {
                    i++;
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    ImageView imageView = (ImageView) findViewById(R.id.imgTruck5);
                    imageView.setImageBitmap(photo);

                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    imagedata5 = Base64.encodeToString(getBytesFromBitmap(bitmap), Base64.DEFAULT);
                }
                break;
            case 66:
                if (requestCode == 66 && resultCode == RESULT_OK) {
                    i++;
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    ImageView imageView = (ImageView) findViewById(R.id.imgTruck6);
                    imageView.setImageBitmap(photo);

                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    imagedata6 = Base64.encodeToString(getBytesFromBitmap(bitmap), Base64.DEFAULT);
                }
                break;
            case 77:
                if (requestCode == 77 && resultCode == RESULT_OK) {
                    i++;
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    ImageView imageView = (ImageView) findViewById(R.id.imgTruck7);
                    imageView.setImageBitmap(photo);

                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    imagedata7 = Base64.encodeToString(getBytesFromBitmap(bitmap), Base64.DEFAULT);
                }
                break;
            case 88:
                if (requestCode == 88 && resultCode == RESULT_OK) {
                    i++;
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    ImageView imageView = (ImageView) findViewById(R.id.imgTruck8);
                    imageView.setImageBitmap(photo);

                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    imagedata8 = Base64.encodeToString(getBytesFromBitmap(bitmap), Base64.DEFAULT);
                }
                break;
        }
    }



    private void convertimagetobyte() {

        if (picturePath1.length() > 0) {
            String picturePath = picturePath1;
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
        ContextWrapper cw = new ContextWrapper(SelectTruckActivity.this);
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
