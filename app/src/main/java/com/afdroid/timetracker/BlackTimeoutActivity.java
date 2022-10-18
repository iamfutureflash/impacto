package com.afdroid.timetracker;

import static android.os.SystemClock.sleep;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afdroid.timetracker.fragments.StatsFragment;
import com.afdroid.timetracker.screens.MainActivity;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class BlackTimeoutActivity extends AppCompatActivity {
    String eyesExcercise[] = {"Tightly close your eyes",
                              "Roll your eyes a few times to each side",
                              "Rotate your eyes in clockwise direction",
                              "Rotate your eyes in counter clockwise direction",
                              "Blink your eyes",
                              "Focus on a point in the far distance",
                              "Have some water"};
    TextView timer;
    TextView excercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_timeout);
        //Assigning variable
        timer = findViewById(R.id.time);
        Random random=new Random();
        excercise = findViewById(R.id.excersise);
        int nextExcersiseNumber = random.nextInt(8);
        excercise.setText(""+eyesExcercise[nextExcersiseNumber]);
        //initilize time durtation
//        long duration = TimeUnit.MINUTES.toMillis(1);


        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {

                int t = (int)(l/1000);
                if(t>=10){
                    timer.setText("00."+t);
                }else {
                    timer.setText("00.0" + t);
                }
                if(t<=0){
                    Intent intent = new Intent(BlackTimeoutActivity.this, QuestionActivity.class);
//                    Intent intent = new Intent(BlackTimeoutActivity.this, BlackTimeoutActivity.class);
//                    Toast.makeText(getApplicationContext(), "started intent StatsFragment.this.getActivity() --> BlackActivity.class", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
}