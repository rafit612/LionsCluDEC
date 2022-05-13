package com.example.lionsclubdec;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {
    ImageView i1,i2;
    private static int TIME=3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

    }
    protected void onStart(){
        super.onStart();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent =new Intent(WelcomeActivity.this,RegLogActivity.class);
                startActivity(intent);
                finish();
            }
        },TIME);

    }
}