package ir.shahabazimi.omidanasansor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import ir.shahabazimi.omidanasansor.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(()->{
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            SplashActivity.this.finish();
        },1500);
    }
}