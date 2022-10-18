package com.afdroid.timetracker;

import static android.os.SystemClock.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.afdroid.timetracker.screens.MainActivity;

public class UserInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
    }

    public void changePhoto(View view) {
    }

    public void moveToIntrestActivity(View view) {
    }

    public void btnNext(View view) {
        Intent intent = new Intent(UserInformationActivity.this, BlackTimeoutActivity.class);
        sleep(2000);
//        Intent intent = new Intent(UserInformationActivity.this, MainActivity.class);
        startActivity(intent);
    }
}