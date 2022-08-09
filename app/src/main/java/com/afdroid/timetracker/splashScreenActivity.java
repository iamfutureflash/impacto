package com.afdroid.timetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.afdroid.timetracker.screens.MainActivity;

public class splashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(1000);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{
                    Intent home = new Intent(splashScreenActivity.this, MainActivity.class);
                    startActivity(home);
                }
            }
        };thread.start();
    }
}