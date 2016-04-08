package com.plickers.android.network;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.plickers.android.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * This class handles calls to the api
 * In this dummy App there is only one
 * possible call but it's still a good
 * idea to have it in one separate class
 * for the future.
 */

public class Api {

    private static final String TAG = "Api";
    private static final String BASE_URL = Constants.API_URL;
    private static AsyncHttpClient client = new AsyncHttpClient();

    private Api(){}

    public static void getPolls(ApiCallback callback){
        Log.d(TAG, "Fetching polls...");
        get("/polls", null, callback);
    }

    public static void get(String url, RequestParams params, final ApiCallback callback) {
        client.get(getAbsoluteUrl(url), params, getJsonHttpResponseHandler(callback));
    }

    public static void post(String url, RequestParams params, ApiCallback callback) {
        client.post(getAbsoluteUrl(url), params, getJsonHttpResponseHandler(callback));
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    private static JsonHttpResponseHandler getJsonHttpResponseHandler(final ApiCallback callback){
        return new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray objects) {
                super.onSuccess(statusCode,headers,objects);
                callback.onSuccess(objects);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                super.onSuccess(statusCode,headers,object);
                callback.onSuccess(object);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode,headers, throwable, errorResponse);
                callback.onError(statusCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode,headers, throwable, errorResponse);
                callback.onError(statusCode);
            }
        };
    }
}
