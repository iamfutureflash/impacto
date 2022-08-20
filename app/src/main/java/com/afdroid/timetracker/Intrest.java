package com.afdroid.timetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Intrest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intrest);
    }

    public void btnback(View view) {
        Intent intent = new Intent(Intrest.this,UserInformationActivity.class);
        startActivity(intent);
    }

}