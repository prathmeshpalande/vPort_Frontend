package com.virtual.portable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;

import com.virtual.portable.task.CoordinatePoster;

public class MainActivity extends AppCompatActivity {

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

        Log.d("Load-up Done", "Finished Loading the app");
    }

    private void initializeWebView() {

        Log.d("Enter", "Initializing the WebView");

        WebView myWebView = (WebView) findViewById(R.id.webview);
        //TODO: ScreenStream IP
        myWebView.loadUrl("http://192.168.1.199:8080");

        Log.d("Exit", "Finished Initializing the WebView");
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

        coordinatePoster.execute("http://192.168.1.135:8080/register_resolution", Measuredwidth.toString(), Measuredheight.toString());

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
        coordinatePoster.execute("http://192.168.1.135:8080/post_coordinates", x.toString(), y.toString());


        return super.dispatchTouchEvent(event);
    }
}
