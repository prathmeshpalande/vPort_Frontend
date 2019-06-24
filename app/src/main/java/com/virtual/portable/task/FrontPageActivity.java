package com.virtual.portable.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.virtual.portable.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FrontPageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FrontPageActivity";

    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.btn_signup)
    Button _signupButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page);
        ButterKnife.bind(this);
        _loginButton = (Button) findViewById(R.id.btn_login);

        _loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent inent = new Intent(this, LoginActivity.class);
         startActivity(inent);

    }

}
