package com.afdroid.timetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
        Intent intent = new Intent(UserInformationActivity.this, Intrest.class);
        startActivity(intent);
    }
}