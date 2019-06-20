//package com.virtual.portable.task;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import java.io.IOException;
//import java.net.HttpURLConnection;
//
//public class ImageGrabber extends AsyncTask<String, String, String> {
//    @Override
//    protected String doInBackground(String... strings) {
//        Log.d("Threaded", "Inside PostCoordinates Thread");
//        try {
//            ImageUrl = new URL(strings[0]);
//            HttpURLConnection conn = (HttpURLConnection) ImageUrl.openConnection();
//            conn.setDoInput(true);
//            conn.connect();
//            is = conn.getInputStream();
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inPreferredConfig = Bitmap.Config.RGB_565;
//            bmImg = BitmapFactory.decodeStream(is, null, options);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "Success";
//    }
//}
