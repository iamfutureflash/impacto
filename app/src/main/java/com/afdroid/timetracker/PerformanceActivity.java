package com.afdroid.timetracker;

import static android.os.SystemClock.sleep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.afdroid.timetracker.screens.MainActivity;

public class PerformanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);
        sleep(200);
     Intent intent = new Intent(PerformanceActivity.this, MainActivity.class);

//                    Intent intent = new Intent(BlackTimeoutActivity.this, BlackTimeoutActivity.class);
    //    Toast.makeText(getApplicationContext(), "Perfromance activity", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
}