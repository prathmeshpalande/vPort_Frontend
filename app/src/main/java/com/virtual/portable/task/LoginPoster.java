package com.virtual.portable.task;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

public class LoginPoster extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... params) {
        Log.d("Threaded", "Inside Login Thread");
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("userName", (params[1]));
            jsonObject.put("password", (params[2]));

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
            Log.d("LoginResponse", "Response: " +  responseStr);

            // you can add an if statement here and do other actions based on the response
            if(responseStr.equals("true")) {
                Log.d("Success", " Login Successfull!");
                return "Success";
            }
        }

        Log.d("Failed", "User name or Password is incorrect");
        return "Failed";
    }
}
