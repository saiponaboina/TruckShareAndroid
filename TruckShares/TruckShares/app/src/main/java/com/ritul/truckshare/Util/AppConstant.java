package com.ritul.truckshare.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by CIS on 6/23/2015.
 */
public class AppConstant {
    public static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    private static View footer;
    public static SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMMM, yyyy");
    private static SimpleDateFormat sdfTime = new SimpleDateFormat("KK:mm a");
    public static String QRdetails = "No Details Avilable.";

    public static final String AwsAccessKey = "AKIAIDUBHRWZLAMJKG7Q";
    public static final String AwsSecretAccessKey = "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";
    public static final String platformApplicationArn = "arn:aws:sns:ap-southeast-1:171836179069:app/GCM/kgdiamonds";

    public static final String project_id = "367196111452";
    public static DecimalFormat localDecimalFormat = new DecimalFormat(
            "#,##,##,##,###.##");
    public static DecimalFormat dollarDecimalFormat = new DecimalFormat(
            "#,###,###,###.##");

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        SimpleDateFormat month_date = new SimpleDateFormat("dd MMMM,yyyy");
        String date = month_date.format(cal.getTime()).toString();
        return date;
    }

    public static String getTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");

        String date = sdf.format(cal.getTime()).toString();
        return date;
    }

    public static void showToastLong(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

    public static void showToastShort(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }


    public static long getCurrentTimeStamps() {
        Calendar c = Calendar.getInstance();
        TimeZone z = c.getTimeZone();
        int offset = z.getRawOffset();
        if (z.inDaylightTime(new Date())) {
            offset = offset + z.getDSTSavings();
        }
        int offsetHrs = offset / 1000 / 60 / 60;
        int offsetMins = offset / 1000 / 60 % 60;

        c.add(Calendar.HOUR_OF_DAY, (-offsetHrs));
        c.add(Calendar.MINUTE, (-offsetMins));
        return c.getTimeInMillis();
    }


    public static void showNetworkError(final Context context) {

        new AlertDialog.Builder(context)
                .setTitle("Network Error")
                .setMessage("Internet connection not found,\nPlease try again.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(true).show();

    }

    public static void showErrorAlert(final Context context, String msg) {

        new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage(""+msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(true).show();

    }

    public static void unableConnectServer(final Context context) {

        new AlertDialog.Builder(context)
                .setTitle("Network Error")
                .setMessage("Unable to connect server,\nPlease try again.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(true).show();
    }

    public static View getProgressFooter(Activity activity) {
        return footer;
    }

    public static void showSingleButtonAlertDialog(final Context context, String title, String message) {

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(true).show();
    }

    public static void setToolBarColor(Activity activity)
            throws NoSuchMethodException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));

        }
    }



    public static boolean isValidEmailAddress(String emailAddress) {
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int dpToPx(int dp, Context context) {
        return Math
                .round(dp
                        * (context.getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static SpannableString spanFont(String string, Context context) {
        SpannableString s;
        s = new SpannableString(string);
        s.setSpan(new TypefaceSpan("fonts/bold.otf"),0,s.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    public static void singleButtonAlertDialog(Context context,
                                               String strTitle, String strMsg) {
        try {
            new AlertDialog.Builder(context)
                    .setTitle(strTitle)
                    .setMessage(strMsg)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setCancelable(true).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static final String PROFILE_PIC_FILE_NAME = "profilePic.jpg";

    public static void saveProfilePic(Context context, InputStream is)
            throws IOException {
        File mFileTemp;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(),
                    AppConstant.PROFILE_PIC_FILE_NAME);
        } else {
            mFileTemp = new File(context.getFilesDir(),
                    AppConstant.PROFILE_PIC_FILE_NAME);
        }
        FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
        copyStream(is, fileOutputStream);
        fileOutputStream.close();
        is.close();

        // // sends broadcast to change profile pic image
        // Intent i = new Intent(SYNC_FINISHED);
        // context.sendBroadcast(i);
    }

    /**
     * Function to encode bitmap image to base64 format
     *
     * @param image
     * @return
     */
    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }

    public static Bitmap getBitmap(String base64) {
        byte[] decodedString = Base64.decode(base64, Base64.NO_WRAP);
        return BitmapFactory.decodeByteArray(decodedString, 0,
                decodedString.length);
    }

    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }


    public static String longToTime(String timeStamp) {
        try {
            if (!timeStamp.equals("")) {
                Date date = new Date(Long.parseLong(timeStamp)
                        + TimeZone.getDefault().getRawOffset());
                return sdfTime.format(date);

            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static void setTextViewTypeFace(Context context, TextView tv) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/regular.otf");
        tv.setTypeface(typeface);
    }

    public static final int getScreenWidth(Context context,
                                           WindowManager wManager) {
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            wManager.getDefaultDisplay().getSize(size);
            return size.x;
        } else {
            Display d = wManager.getDefaultDisplay();
            return d.getWidth();
        }
    }

    public static final int getScreenHeight(Context context,
                                            WindowManager wManager) {
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            wManager.getDefaultDisplay().getSize(size);
            return size.y;
        } else {
            Display d = wManager.getDefaultDisplay();
            return d.getHeight();
        }
    }

    public static void copyJSONArray(JSONArray src, ArrayList<String> dest) {
        if (src != null && dest != null) {
            dest.clear();
            for (int i = 0; i < src.length(); i++) {
                try {
                    dest.add(src.get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void bindJSONArray(JSONArray src, ArrayList<String> dest) {
        if (src != null && dest != null) {
            for (int i = 0; i < src.length(); i++) {
                try {
                    dest.add(src.get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void copyTrackJSONArray(JSONArray src, ArrayList<String> dest, HashMap<String, Boolean> selectedHashMapItems) {
        if (src != null && dest != null) {
            dest.clear();
            selectedHashMapItems.clear();
            JSONObject item;
            for (int i = 0; i < src.length(); i++) {
                try {
                    item = src.getJSONObject(i);
                    item.put("isSelected", true);
                    selectedHashMapItems.put(String.valueOf(i), true);
                    dest.add(item.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void bindTrackJSONArray(JSONArray src, ArrayList<String> dest, HashMap<String, Boolean> selectedHashMapItems) {
        if (src != null && dest != null) {
            JSONObject item;
            final int listSize = dest.size();
            for (int i = 0; i < src.length(); i++) {
                try {
                    item = src.getJSONObject(i);

                    selectedHashMapItems.put(String.valueOf(i + listSize), true);
                    dest.add(item.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(cal.getTime());
    }

    public static String longToDate(String timeStamp) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if (!timeStamp.equals("")) {
                Date date = new Date(Long.parseLong(timeStamp.substring(6,
                        timeStamp.length() - 2))
                        + TimeZone.getDefault().getRawOffset());
                return sdf.format(date);

            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static long getTimeDifference(String timeStamp) {
        String time = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Calendar cal = Calendar.getInstance();

        Date d = null;
        try {
            d = sdf.parse(longToDate(timeStamp));
            cal.setTimeInMillis(d.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calNow = Calendar.getInstance();
        String savedDate = sdf.format(cal.getTime());
        String now = sdf.format(calNow.getTime());
        Date d1 = null;
        Date d2 = null;
        long diffHours = 0;
        long diffMinutes = 0;
        long diffDays = 0;
        try {
            d1 = sdf.parse(savedDate);
            d2 = sdf.parse(now);
            long diff = d2.getTime() - d1.getTime();

            // long diffSeconds = diff / 1000 % 60;
            diffMinutes = diff / (60 * 1000) % 60;
            diffHours = diff / (60 * 60 * 1000) % 24;
            diffDays = diff / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diffDays;

    }

    public static long getTimeDifference(long savedTime) {
        String time = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(savedTime);

        String savedDate = sdf.format(cal.getTime());

        Date d1 = null;
        Date d2 = null;
        // long diffHours = 0;
        // long diffMinutes = 0;
        long diffDays = 0;
        try {
            d1 = sdf.parse(savedDate);
            d2 = sdf.parse(AppConstant.getCurrentDateTime());

            long diff = d2.getTime() - d1.getTime();

            // long diffSeconds = diff / 1000 % 60;
            // diffHours = diff / (60 * 60 * 1000) % 24;
            // diffMinutes = diff / (60 * 1000) % 60;
            diffDays = diff / (24 * 60 * 60 * 1000);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return diffDays;

    }

    public static String getCurrentDateTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        // SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(cal.getTime());

    }

    public static JSONArray getDataFromFile(Context context, String fileName) {
        InputStream is = null;
        JSONArray jsonArray;
        JSONObject jsonObject;
        try {
            is = new BufferedInputStream(context.getResources().getAssets().open(fileName));
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            jsonObject = new JSONObject(sb.toString());
            return jsonObject.getJSONArray("rows");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getRoundValue(Double value) {
        try {
            return String.valueOf(BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_UP));
        } catch (Exception e) {
            e.printStackTrace();
            return "0.0";
        }
    }

    public static double getRoundValueAmount(Double value) {
        try {
            return BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }


    public static BigDecimal setAmountScale(Double value) {
        return BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_UP);
    }



    public static void setCurrencyText(Context context, EditText editText, Double value) {

    }

    public static long JsonDateToDate(String jsonDate) {
        //  "/Date(1321867151710)/"
        int idx1 = jsonDate.indexOf("(");
        int idx2 = jsonDate.indexOf(")");
        String s = jsonDate.substring(idx1 + 1, idx2);
        long l = Long.valueOf(s);
        return l;
    }

    public static ArrayList<String> convertStringToArrayList(String str, String strSeparator) {
        String[] arr = str.split(strSeparator);
        ArrayList<String> data = new ArrayList<>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i].equals("")) {
                data.add(arr[i]);
            }
        }
        return data;
    }

    public static String getStringFromJson(JSONObject item, String key) {
        try {
            return item.has(key) ? (item.isNull(key) ? "" : item.getString(key)) : "";
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }



    public static void openUrl(Context context, String strUrl) {
        // TODO Auto-generated method stub
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(strUrl));
            context.startActivity(browserIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
