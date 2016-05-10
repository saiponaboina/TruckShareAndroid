package com.ritul.truckshare.Server;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by vipulm on 07-Dec-15.
 */
public class RSMServer {
    public static String BASE_URL = "http://trucksharelle.showdemonow.com/api/services/TruckShareSystem/appUser/";
    public static String BASE_URLT = "http://trucksharelle.showdemonow.com/api/services/TruckShareSystem/"; //insert register
    public static String BASE_URLD = "http://trucksharelle.showdemonow.com/api/services/TruckShareSystem/"; //get

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        try {
            Log.e("URL", getAbsoluteUrl(url)  + params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.setTimeout(200000);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params,
                            AsyncHttpResponseHandler responseHandler) {
        Log.e("URL", getAbsoluteUrl(url) + " | " + params.toString());
        client.setTimeout(200000);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }


    public static void getd(String url, RequestParams params,
                            AsyncHttpResponseHandler responseHandler) {
        try {
            Log.e("URL", getAbsoluteUrld(url)  + params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.setTimeout(200000);
        client.get(getAbsoluteUrld(url), params, responseHandler);
    }

    private static String getAbsoluteUrld(String relativeUrl) {
        return BASE_URLD + relativeUrl;
    }

    public static final int EXTERNAL_WRITE_SD_CODE = 1;
}
