package com.afdroid.timetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.afdroid.timetracker.screens.MainActivity;

public class QuestionActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
    }

    public void move_to_main(View view) {
        try {
            Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
            Toast.makeText(this, "started intent QuestionActivity.this -> SettingsActivity.class", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } catch (Exception e) {
            Context context;
            Toast.makeText(this, " " + e, Toast.LENGTH_SHORT).show();
        }
    }
}