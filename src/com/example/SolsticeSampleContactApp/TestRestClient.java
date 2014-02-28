package com.example.SolsticeSampleContactApp;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by neel on 2/22/14.
 */
public class TestRestClient {

    public static final String BASE_URL = "SAMPLE URL";
    public static final String BASIC_URL = "/contacts.json";
    public static final String DETAIL_URL = "/Contacts/id/";



    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    private static String getAbsoluteUrl(String relativeUrl, int id) {
        return BASE_URL + relativeUrl + id + ".json";
    }

    public static JSONObject getDetailedContact(String url){

        FakeTrustManager.allowAllSSL();

        String responseBody = null;
        JSONObject responseJSON = null;

        HttpClient req = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(url);
        getRequest.addHeader("accept", "application/json");

        HttpResponse response = null;

        try {
            response = req.execute(getRequest);
        } catch (IOException e) {

        }

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatusLine().getStatusCode());
        }

        HttpEntity entity = response.getEntity();

        if (entity != null) {

            //convert response to String
            try {
                responseBody = EntityUtils.toString(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //parse response to JSON
            try {
                responseJSON = new JSONObject(responseBody);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return responseJSON;

    }



}
