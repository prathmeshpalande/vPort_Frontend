package com.virtual.portable.task;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CoordinatePoster extends AsyncTask<String, String, String> {


    @Override
    protected String doInBackground(String... params) {

        Log.d("Threaded", "Inside PostCoordinates Thread");
//        String urlString = params[0]; // URL to call
//        String data = "{ \"x\": " + params[1] + ", \"y\": " + params[2] + "}"; //data to post
//        Log.d("Var Values", "URL: " + params[0] + ", Data: " + data);
//        OutputStream out = null;
//
//        try {
//            URL url = new URL(urlString);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("POST");
//            out = new BufferedOutputStream(urlConnection.getOutputStream());
//
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
//            writer.write(data);
//            writer.flush();
//            writer.close();
//            out.close();
//
//            urlConnection.connect();
//
//            Log.d("Success", "Coordinates Posted Successfully!");
//            return "Success";
//        } catch (Exception e) {
//            Log.d("Failure",e.getMessage());
//            return e.getMessage();
//        }


//        String data = "{'x_coor':'" + params[1] + "','y_coor':" + params[2] + "'}"; //data to post

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("x_coor", Integer.parseInt(params[1]));
            jsonObject.put("y_coor", Integer.parseInt(params[2]));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String data = jsonObject.toString();

        Log.d("Var Values", "URL: " + params[0] + ", Data: " + data);

        // HttpClient
        HttpClient httpClient = new DefaultHttpClient();

        // post header
        HttpPost httpPost = new HttpPost(params[0]);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(new StringEntity(data, Charset.forName("UTF-8")));

//        // add your data
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//        nameValuePairs.add(new BasicNameValuePair("x", params[1]));
//        nameValuePairs.add(new BasicNameValuePair("y", params[2]));
//
//        try {
//            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        // execute HTTP post request
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity resEntity = response.getEntity();

        if (resEntity != null) {

            String responseStr = null;
            try {
                responseStr = EntityUtils.toString(resEntity).trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("POSTCoorResponse", "Response: " +  responseStr);

            // you can add an if statement here and do other actions based on the response
        }

        Log.d("Success", "Coordinates Posted Successfully!");
        return "Success";
    }
}
