package com.virtual.portable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;

import com.virtual.portable.task.CoordinatePoster;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    URL ImageUrl;// = new URL("http://192.168.1.4:8080/grab_screen");
    InputStream is;
    Bitmap bmImg;

    private final Handler handler = new Handler();

    public MainActivity() throws MalformedURLException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        initializeWebView();

        Log.d("Resolution", "Posting resolution coordinates");
        postResolution();
//        try {
//            loadStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        new AsyncImageLoad().execute("http://192.168.1.4:8080/grab_screen");

        doTheAutoRefresh();
//        new AsyncImageLoad().execute("https://homepages.cae.wisc.edu/~ece533/images/airplane.png");


        Log.d("Load-up Done", "Finished Loading the app");
    }

    private void initializeWebView() {

        Log.d("Enter", "Initializing the WebView");

//        WebView myWebView = (WebView) findViewById(R.id.webview);
        //TODO: ScreenStream IP
        imageView = (ImageView) findViewById(R.id.imageview);
//        myWebView.loadUrl("http://192.168.1.199:8080");

        Log.d("Exit", "Finished Initializing the WebView");
    }

    private void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Write code for your refresh logic
                new AsyncImageLoad().execute("http://192.168.1.4:8080/grab_screen");
                doTheAutoRefresh();
            }
        }, 1);
    }

//    private void loadStream() throws IOException {
//
//        new Thread(() - > {
//
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        try {
//            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//            imageView.setImageBitmap(BitmapFactory.decodeStream(in));
//        } finally {
//            urlConnection.disconnect();
//        }
//        });
//
//
//    }

    private class AsyncImageLoad extends AsyncTask<String, String, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... strings) {

            try {
                ImageUrl = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) ImageUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                is = conn.getInputStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bmImg = BitmapFactory.decodeStream(is, null, options);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmImg;
//            Bitmap bmImg = null;
//            HttpURLConnection urlConnection = null;
//            try {
//                urlConnection = (HttpURLConnection) url.openConnection();
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//            try {
////                ImageUrl = new URL(strings[0]);
////                conn.setDoInput(true);
////                conn.connect();
//                urlConnection.setDoInput(true);
//                urlConnection.connect();
//
//                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//
////                BitmapFactory.Options options = new BitmapFactory.Options();
////                options.inPreferredConfig = Bitmap.Config.RGB_565;
////                bmImg = BitmapFactory.decodeStream(is, null, options);
//
//                bmImg = BitmapFactory.decodeStream(in);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            finally {
//                urlConnection.disconnect();
//            }
//            return bmImg;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(imageView!=null) {
//                p.hide();
                imageView.setImageBitmap(bitmap);
            }else {
//                p.show();
            }
        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
//            imageView.setImageBitmap(bitmap);
//        }
    }
    private void postResolution() {

        Integer Measuredwidth = 0;
        Integer Measuredheight = 0;
        Point size = new Point();
        WindowManager w = getWindowManager();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)    {
            w.getDefaultDisplay().getSize(size);
            Measuredwidth = size.x;
            Measuredheight = size.y;
        }else{
            Display d = w.getDefaultDisplay();
            Measuredwidth = d.getWidth();
            Measuredheight = d.getHeight();
        }

        CoordinatePoster coordinatePoster = new CoordinatePoster();

        coordinatePoster.execute("http://192.168.1.4:8080/register_resolution", Measuredwidth.toString(), Measuredheight.toString());

        Log.d("Success", "Resolution successfully posted!");
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        Integer x = (int)event.getX();
        Integer y = (int)event.getY();

        Log.d("Touched at", "Touched at: " + x + ", " + y);

        //TODO: Pass (x,y) to the API
        Log.d("Posting", "Posting Coordinates");
        CoordinatePoster coordinatePoster = new CoordinatePoster();
        coordinatePoster.execute("http://192.168.1.4:8080/post_coordinates", x.toString(), y.toString());


        return super.dispatchTouchEvent(event);
    }
}
